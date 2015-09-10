package HRAts.controller;

import HRAts.model.PasswordResetToken;
import HRAts.model.User;
import HRAts.model.VerificationToken;
import HRAts.service.UserService;
import HRAts.service.VerificationTokenService;
import HRAts.utils.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@RestController
@RequestMapping(value = "/protected/users")
public class UserController {

    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUserById(@PathVariable int id){
        return userService.findById(id);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ResponseEntity<Serializable> createUser(@RequestBody final User user,
                                               HttpServletRequest request){

        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {

            if (existingUser.getEnabled()) {
                logger.error("User with email: "+user.getEmail()+" already exists and is enabled");
                return new ResponseEntity<Serializable>(new GenericResponse(-1, "User with email: "+user.getEmail()+" already exists and is active"),
                                                        HttpStatus.BAD_REQUEST);
            }

            VerificationToken verificationToken = verificationTokenService.findByUser(existingUser);
            if (verificationToken != null && !verificationToken.isExpired()) {
                return new ResponseEntity<Serializable>(new GenericResponse(-1, "Verification token exists and did not expire"), HttpStatus.BAD_REQUEST);
            }
        }

        String appUrl = "http://"+ request.getServerName()
                        +":"+ request.getServerPort()
                        + request.getContextPath();

        try {
            User registered = existingUser != null ? userService.registerUser(existingUser, appUrl) : userService.registerUser(user, appUrl);
            return new ResponseEntity<Serializable>(registered, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Serializable>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable("id") int userId,
                                        @RequestBody User user) {
        if (userId != user.getId()){
            return new ResponseEntity<>(new GenericResponse(-1, "Action forbidden"), HttpStatus.FORBIDDEN);
        }

        User relatedUser = userService.findById(user.getId());
        if (!relatedUser.getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>(new GenericResponse(-2, "Cannot update email"), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> registrationConfirm(@RequestParam(value = "token") String token, HttpServletRequest request) {

        logger.info(token);
        GenericResponse verificationInfo = verificationTokenService.verifyUser(token);

        if (verificationInfo.getData().equals("Success")) {
            return new ResponseEntity<>(verificationInfo, HttpStatus.OK);
        }

        return new ResponseEntity<>(verificationInfo, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> resetPassword(@RequestParam(value = "email") String userEmail,
                                           HttpServletRequest request) {

        User user = userService.findByEmailAndEnabledIsTrue(userEmail);
        if (user == null) {
            return new ResponseEntity<>(new GenericResponse(-1, "User with such name is not enabled or does not exist"), HttpStatus.BAD_REQUEST);
        }

        String appUrl =
                "http://" + request.getServerName() +
                        ":" + request.getServerPort() +
                        request.getContextPath();
        PasswordResetToken token = userService.createPasswordResetToken(user);

        SimpleMailMessage email = this.constructResetTokenEmail(appUrl, token, user);
        mailSender.send(email);

        return new ResponseEntity<Object>(new GenericResponse(0, "Reset token sent via email"), HttpStatus.OK);
    }

    @RequestMapping(value = "/passwordReset", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> passwordReset(@RequestParam(value = "id") int userId,
                                            @RequestParam(value = "token") String token,
                                            @RequestBody User passedUser) {
        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(new GenericResponse(-1, "User does not exist"), HttpStatus.BAD_REQUEST);
        }

        PasswordResetToken resetToken = userService.getPasswordResetToken(user);

        if (!resetToken.getToken().equals(token)) {
            return new ResponseEntity<>(new GenericResponse(-2, "Token is invalid"), HttpStatus.BAD_REQUEST);
        }

        user = userService.changePassword(user, passedUser.getPassword());

        return new ResponseEntity<Object>(new GenericResponse(0, user), HttpStatus.OK);
    }

    @RequestMapping(value="/changePassword", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> changePassword(@RequestBody User user,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword){

        User storedUser = userService.findById(user.getId());
        if(storedUser == null) {
            return new ResponseEntity<>(new GenericResponse(-1, "User does not exists"), HttpStatus.BAD_REQUEST);
        }

        boolean passwordMatch = BCrypt.checkpw(oldPassword, storedUser.getPassword());
        if(!passwordMatch) {
            return new ResponseEntity<>(new GenericResponse(-2, "Old password is not valid"), HttpStatus.BAD_REQUEST);
        }

        storedUser = userService.changePassword(storedUser, newPassword);

        return new ResponseEntity<>(new GenericResponse(0, storedUser), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteUser(@PathVariable("id") int userId) {
        userService.delete(userId);
    }

    private SimpleMailMessage constructResetTokenEmail(String contextPath, PasswordResetToken token, User user) {

        String url = contextPath+"/passwordReset?id="+user.getId()+"&token="+token.getToken();
        String emailContent = url + "\r\n" + "Link will expire on " + token.getExpiryDate()
                                  + "\r\n" + "If you did not request this email, simply ignore this message";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Password reset");
        email.setText(emailContent);
        return email;
    }
}