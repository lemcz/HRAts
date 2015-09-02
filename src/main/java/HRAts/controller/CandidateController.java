package HRAts.controller;

import HRAts.model.Department;
import HRAts.model.Role;
import HRAts.model.User;
import HRAts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/protected/candidates")
public class CandidateController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("candidatesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<User> listCandidates(){
        return userService.findByRole(Role.ROLE_CANDIDATE);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable int id) {
        return new ModelAndView("candidateDetails");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getCandidateById(@PathVariable int id){
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity createCandidate(@RequestBody final User candidate){

        List<User> contactList = new ArrayList<>();

        candidate.setRole(Role.ROLE_CANDIDATE);
        candidate.setContactList(contactList);
        candidate.setDepartmentList(new ArrayList<Department>());

        User savedUser = userService.save(candidate);

        return new ResponseEntity<User>(savedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCandidate(@PathVariable("id") int candidateId,
                                             @RequestBody User candidate) {
        if (candidateId != candidate.getId()){
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<User>(userService.update(candidate), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity deleteCandidate(@PathVariable("id") int candidateId) {

        userService.delete(candidateId);

        return new ResponseEntity<User>(HttpStatus.OK);
    }
}