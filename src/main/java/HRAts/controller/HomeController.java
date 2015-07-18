package HRAts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class HomeController {

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
}
