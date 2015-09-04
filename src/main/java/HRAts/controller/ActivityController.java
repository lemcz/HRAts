package HRAts.controller;

import HRAts.model.*;
import HRAts.service.ActivityService;
import HRAts.service.StatusService;
import HRAts.service.VacancyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/protected/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private VacancyUserService vacancyUserService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("activitiesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Activity> listActivities(){
        return activityService.findAll();
    }

    @RequestMapping(value = "/perCandidate/{id}", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Activity> getActivityByCandidateId(@PathVariable int id){
        return activityService.findByCandidateId(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Activity getActivityById(@PathVariable int id){
        return activityService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ResponseEntity<GenericResponse> createActivity(@RequestBody ActivityStatusContext activityStatusContext){

        Status savedStatus = new Status();

        Activity savedActivity = activityService.save(activityStatusContext.getActivity());

        Status persistedStatus = activityStatusContext.getStatus();

        if (persistedStatus.getStatusType() != null) {
            VacancyUser vacancyUser = vacancyUserService.findByVacancyIdAndCandidateId(savedActivity.getVacancy().getId(), savedActivity.getCandidate().getId());
            if (vacancyUser == null) {
                return new ResponseEntity<>(new GenericResponse(-1, "Candidate is not linked with provided vacancy"), HttpStatus.BAD_REQUEST);
            }
            persistedStatus.setVacancyUser(vacancyUser);
            savedStatus = statusService.save(persistedStatus);
        }

        activityStatusContext = new ActivityStatusContext(savedActivity, savedStatus);
        return new ResponseEntity<>(new GenericResponse(0, activityStatusContext), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteActivity(@PathVariable("id") int activityId) {
        activityService.delete(activityId);
    }
}