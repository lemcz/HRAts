package HRAts.controller;

import HRAts.model.Activity;
import HRAts.model.ActivityTypeLkp;
import HRAts.model.Company;
import HRAts.model.Department;
import HRAts.service.ActivityService;
import HRAts.service.ActivityTypeService;
import HRAts.service.CompanyService;
import HRAts.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/protected/companies")
public class CompanyController {

    private static final Logger logger = LoggerFactory
            .getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityTypeService activityTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("companiesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Company> listCompanies(){
        return companyService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Company getCompanyById(@PathVariable int id){
        return companyService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity createCompany(@RequestBody Company company){

        Company savedCompany = companyService.save(company);

        if(company.getDepartmentList() != null) {

            for (Department department : company.getDepartmentList()) {
                department.setCompany(savedCompany);
                departmentService.save(department);
            }
        }

        //Log activity
        Activity activityLog = new Activity();
        //todo add owner of activity
        //activityLog.setOwner(company.getOwner());
        activityLog.setNote("New company added to the repository");

        ActivityTypeLkp activityLogType = activityTypeService.findByName(activityTypeService.ACTIVITY_TYPE_CREATE_RECORD);

        activityLog.setActivityType(activityLogType);

        //todo set company for the activity
        //activityLog.setCompany(savedCompany);

        activityService.save(activityLog);

        return new ResponseEntity<Company>(savedCompany, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCompany(@PathVariable("id") int companyId,
                                           @RequestBody Company company) {
        if (companyId != company.getId()){
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Company>(companyService.save(company), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<?> deleteCompany(@PathVariable("id") int companyId) {

        Company company = companyService.findById(companyId);

        List<Department> departmentList = company.getDepartmentList();
        for (Department department : departmentList) {
            if (department.getManager() != null) {
                logger.info("Cannot DELETE requested company with id : " + companyId);
                return new ResponseEntity<String>("Other objects depend on this company, cannot delete", HttpStatus.BAD_REQUEST);
            }
        }
        logger.info("Will delete company with ID: " + companyId);
        companyService.delete(companyId);
        return new ResponseEntity<String>("Delete successful", HttpStatus.OK);
    }
}