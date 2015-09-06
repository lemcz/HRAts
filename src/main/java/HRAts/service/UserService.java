package HRAts.service;

import HRAts.model.PasswordResetToken;
import HRAts.model.Role;
import HRAts.model.User;
import HRAts.model.VerificationToken;
import HRAts.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory
            .getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private JavaMailSender mailSender;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findByEmailAndEnabledIsTrue(String email){
        return userRepository.findByEmailAndEnabledIsTrue(email);
    }

    @Transactional(readOnly = true)
    public Iterable<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public Iterable<User> findAllByDepartmentManagerWhereCompanyId(int companyId) {
        return userRepository.findByDepartmentListCompany_Id(companyId);
    }

    @Transactional
    public Iterable<User> findAllCandidatesByVacancyId(int vacancyId) {
        return userRepository.findByVacancyUserCandidateListVacancy_Id(vacancyId);
    }

    @Transactional
    public User findManagerByVacancyId(int vacancyId) {
        return userRepository.findManagerByVacancyId(vacancyId);
    }

    @Transactional
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public User save(User user) {

        User savedUser = userRepository.save(user);

        String activityNote = "New user added to the repository";
        activityService.logUserActivity(activityNote, savedUser.getOwner());

        return savedUser;
    }

    @Transactional(readOnly = true)
    public User registerUser(User user, String appUri) {

        Role role = Role.ROLE_ADMIN;
        user.setRole(role);

        user.setEnabled(false);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        User registeredUser = userRepository.save(user);
        VerificationToken verificationToken = new VerificationToken(user);
        VerificationToken persistedToken = verificationTokenService.save(verificationToken);
        this.sendConfirmRegistrationMail(registeredUser, persistedToken, appUri);

        return registeredUser;
    }

    @Transactional(readOnly = true)
    public User update(User user) {
        return userRepository.save(user);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int userId) {
        userRepository.delete(userId);
    }

    @Transactional
    public User changePassword(User user, String password) {
        String encodedPassword = this.encodePassword(password);
        user.setPassword(encodedPassword);
        return this.update(user);
    }

    private void sendConfirmRegistrationMail(User user, VerificationToken verificationToken, String applicationPath){

        String expiryInfo = "Activation URL will expire on " + verificationToken.getExpiryDate().toString();

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "/protected/users/registrationConfirm?token=" + verificationToken.getToken();
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(applicationPath + confirmationUrl + " \r\n" + expiryInfo);
        mailSender.send(email);
    }

    public PasswordResetToken createPasswordResetToken(User user) {
        PasswordResetToken token = new PasswordResetToken(user);
        passwordResetTokenService.save(token);
        return token;
    }

    public PasswordResetToken getPasswordResetToken(User user) {
        return passwordResetTokenService.findByUser(user);
    }

    public String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        password = encoder.encode(password);
        return password;
    }
}
