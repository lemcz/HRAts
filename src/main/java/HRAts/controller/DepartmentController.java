package HRAts.controller;

import HRAts.model.Activity;
import HRAts.model.ActivityTypeLkp;
import HRAts.model.Department;
import HRAts.model.User;
import HRAts.service.ActivityService;
import HRAts.service.ActivityTypeService;
import HRAts.service.DepartmentService;
import HRAts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/protected/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityTypeService activityTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("companiesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Department> listDepartments(@RequestParam(value="companyId", required = false) Integer companyId){

        if (companyId == null) {
            return departmentService.findAll();
        }

        return departmentService.findByCompany_IdAndManagerIsNull(companyId);
    }

    @RequestMapping(value = "/perCompanies", headers={"type=list"}, method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody
    Iterable<Department> getDepartmentByCompaniesIds(@RequestBody(required = false) List<Integer> companiesIds){
        if(companiesIds.isEmpty()) {
            return new ArrayList<>();
        }
        return departmentService.findByCompanyIdIn(companiesIds);
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.POST, produces = "application/json")
    public Department getDepartmentById(@PathVariable int id){
        return departmentService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity createDepartment(@RequestBody final Department department){

        if (department.getManager() != null) {

            User manager = userService.findById(department.getManager().getId());

            if (manager.getRole().toString() != "ROLE_MANAGER") {
                return new ResponseEntity<String>("Provided user is not a manager", HttpStatus.BAD_REQUEST);
            }
        }

        //Log activity
        Activity activityLog = new Activity();
        activityLog.setOwner(department.getOwner());
        activityLog.setNote("New department added to the repository");

        ActivityTypeLkp insertCandidateActivityType = activityTypeService.findByName(activityTypeService.ACTIVITY_TYPE_CREATE_RECORD);

        activityLog.setActivityType(insertCandidateActivityType);

        //todo set department for the activity
        //activityLog.setDepartment(savedDepartment);

        activityService.save(activityLog);

        return new ResponseEntity<Department>(departmentService.save(department), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCompany(@PathVariable("id") int departmentId,
                                           @RequestBody Department department) {


        if (departmentId != department.getId()){
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Department>(departmentService.save(department), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteCompany(@PathVariable("id") int departmentId) {
        departmentService.delete(departmentId);
    }
}