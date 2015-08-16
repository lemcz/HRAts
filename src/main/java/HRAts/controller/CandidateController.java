package HRAts.controller;

import HRAts.model.*;
import HRAts.service.ActivityService;
import HRAts.service.ActivityTypeService;
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
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityTypeService activityTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("candidatesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<User> listCandidates(){
        return userService.findByRole(Role.ROLE_CANDIDATE);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getCandidateViewById(@PathVariable int id){
        return new ModelAndView("candidateById");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getCandidateById(@PathVariable int id){
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody User createCandidate(@RequestBody final User candidate){

        List<Department> departmentList= new ArrayList<>();
        List<User> contactList = new ArrayList<>();

        candidate.setRole(Role.ROLE_CANDIDATE);
        candidate.setContactList(contactList);
        candidate.setDepartmentList(departmentList);

        User owner = candidate.getOwner();
        Activity insertCandidateActivity = new Activity();
        insertCandidateActivity.setOwner(owner);
        insertCandidateActivity.setNote("New candidate <a>added </a>to the repository");

        ActivityTypeLkp insertCandidateActivityType = activityTypeService.findById(4);

        insertCandidateActivity.setActivityType(insertCandidateActivityType);

        User savedUser = userService.save(candidate);

        if(savedUser != null) {
            insertCandidateActivity.setCandidate(savedUser);
            activityService.save(insertCandidateActivity);
        }

        return savedUser;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCandidate(@PathVariable("id") int candidateId,
//                                             @RequestParam(value = "type", required = false) String type,
                                             @RequestBody User candidate) {
        if (candidateId != candidate.getId()){
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }

        //TODO dodac typy edycji
/*        if (type == null){
            return new ResponseEntity<User>(userService.save(candidate), HttpStatus.OK);
        }*/

        return new ResponseEntity<User>(userService.save(candidate), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteCandidate(@PathVariable("id") int candidateId) {
        userService.delete(candidateId);
    }
}