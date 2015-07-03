package HRAts.controller;

import HRAts.model.Role;
import HRAts.model.User;
import HRAts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/protected/contacts")
public class ContactController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("contactsList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<User> listContacts(){
        Role role = Role.ROLE_MANAGER;
        return userService.findByRole(role);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getContactById(@PathVariable int id){
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody User createContact(@RequestBody final User contact){
        Role role = Role.ROLE_MANAGER;
        contact.setRole(role);
        return userService.save(contact);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateContact(@PathVariable("id") int contactId,
                                           @RequestBody User contact) {
        if (contactId != contact.getId()){
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(userService.save(contact), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteContact(@PathVariable("id") int contactId) {
        userService.delete(contactId);
    }
}