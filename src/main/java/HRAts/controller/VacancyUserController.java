package HRAts.controller;

import HRAts.model.VacancyUser;
import HRAts.service.VacancyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/protected/vacancyUser")
public class VacancyUserController {

    @Autowired
    private VacancyUserService vacancyUserService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<VacancyUser> listActivities(){
        return vacancyUserService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody VacancyUser createUserService(@RequestBody final VacancyUser vacancyUser){
        return vacancyUserService.save(vacancyUser);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteUserService(@PathVariable("id") int activityId) {
        vacancyUserService.delete(activityId);
    }
}