package HRAts.controller;

import HRAts.model.Department;
import HRAts.model.Role;
import HRAts.model.User;
import HRAts.service.UserService;
import HRAts.utils.GenericResponse;
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
    public Iterable<User> listCandidates(@RequestParam(value = "search", required = false) String search,
                                         @RequestParam(value = "id", required = false) Integer id){

        if (search != null && id != null) {
            switch (search) {
                case "vacancy": return userService.findAllCandidatesByVacancyId(id);
                default: return userService.findByRole(Role.ROLE_CANDIDATE);
            }
        }

        return userService.findByRole(Role.ROLE_CANDIDATE);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable int id) {
        return new ModelAndView("candidateDetails").addObject("pathId", id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getCandidateById(@PathVariable int id){
        return userService.findById(id);
    }

    @RequestMapping(value = "/notInVacancy/{id}", method = RequestMethod.GET, produces = "application/json")
    public Iterable<User> getCandidatesNotInVacancy(@PathVariable int id){
        return userService.findCandidatesNotAssignedToVacancyId(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity createCandidate(@RequestBody final User candidate){

        List<User> contactList = new ArrayList<>();

        candidate.setRole(Role.ROLE_CANDIDATE);
        candidate.setContactList(contactList);
        candidate.setDepartmentList(new ArrayList<Department>());

        if(userService.findByEmail(candidate.getEmail()) != null) {
            new ResponseEntity<>(new GenericResponse(-1, "User with provided email already exists"), HttpStatus.BAD_REQUEST);
        }

        try {
            User savedUser = userService.save(candidate);
            return new ResponseEntity<>(new GenericResponse(0, savedUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new GenericResponse(-2, e), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity updateCandidate(@PathVariable("id") int candidateId,
                                             @RequestBody User candidate) {
        if (candidateId != candidate.getId()){
            return new ResponseEntity<>(new GenericResponse(-1, "Bad Request"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.update(candidate), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity deleteCandidate(@PathVariable("id") int candidateId) {

        try{
            userService.delete(candidateId);
        } catch (Exception e) {
            return new ResponseEntity<>(new GenericResponse(-1, e), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new GenericResponse(0, "Candidate deleted successfully"), HttpStatus.OK);
    }
}