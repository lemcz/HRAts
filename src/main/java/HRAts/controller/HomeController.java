package HRAts.controller;

import HRAts.model.User;
import HRAts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class HomeController {

    @Autowired
    UserService userService;

    @RequestMapping("favicon.ico")
    @ResponseBody
    void favicon() {
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
    public String redirect(){
        return "redirect:/protected/home";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT}, produces = "application/json")
    public ResponseEntity<?> doGetAjax() {
        return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/protected/settings", method = RequestMethod.GET)
    public ModelAndView getSettingsView() {
        return new ModelAndView("settings");
    }

    @RequestMapping(value = "/protected/settings/{userId:[\\d]+}", method = RequestMethod.GET, produces = "application/json")
    public User getUserDetails(@PathVariable("userId") int userId) {
        return userService.findById(userId);
    }

}
