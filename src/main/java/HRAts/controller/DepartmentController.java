package HRAts.controller;

import HRAts.model.Department;
import HRAts.service.DepartmentService;
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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("companiesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Department> listDepartments(){
        return departmentService.findAll();
    }

    @RequestMapping(value = "/", params = {"companyId"}, method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Iterable<Department> getDepartmentsByCompanyId(@RequestParam(value = "companyId") int companyId) {
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

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.GET, produces = "application/json")
    public Department getDepartmentById(@PathVariable int id){
        return departmentService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Department createDepartment(@RequestBody final Department department){
          return departmentService.save(department);
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