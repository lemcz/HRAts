package HRAts.controller;

import HRAts.model.Department;
import HRAts.model.User;
import HRAts.service.DepartmentService;
import HRAts.service.UserService;
import HRAts.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/protected/departments")
public class DepartmentController implements Serializable {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Department> listDepartments(@RequestParam(value = "search", required = false) String search,
                                                @RequestParam(value = "id", required = false) Integer id){

        if (search != null && id != null) {
            switch (search) {
                case "company": return departmentService.findByCompany_Id(id);
                case "manager": return departmentService.findByManagerId(id);
                case "managerEmpty": return departmentService.findByCompany_IdAndManagerIsNull(id);
                default: return departmentService.findAll();
            }
        }

        return departmentService.findAll();
    }

    @RequestMapping(value = "/perCompanies", headers={"type=list"}, method = RequestMethod.PUT, produces = "application/json")
    public @ResponseBody
    Iterable<Department> getDepartmentByCompaniesIds(@RequestBody(required = false) List<Integer> companiesIds){
        if(companiesIds.isEmpty()) {
            return new ArrayList<>();
        }
        return departmentService.findByCompanyIdIn(companiesIds);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity createDepartments(@RequestBody List<Department> departmentList){

        List<Department> savedDepartments = new ArrayList<>();

        for (Department department : departmentList) {
            if (department.getCompany() == null) {
                return new ResponseEntity<>(new GenericResponse(-1, "Missing company"), HttpStatus.BAD_REQUEST);
            }
            Department createdDepartment = departmentService.save(department);
            savedDepartments.add(createdDepartment);
        }

        return new ResponseEntity<List>(savedDepartments, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.GET, produces = "application/json")
    public Department getDepartmentById(@PathVariable int id){
        return departmentService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity createDepartment(@RequestBody final Department department){

        if (department.getManager() != null) {

            User manager = userService.findById(department.getManager().getId());

            if (manager.getRole().toString().equals("ROLE_MANAGER")) {
                return new ResponseEntity<>(new GenericResponse(-1, "Provided user is not a manager"), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(departmentService.save(department), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateDepartment(@PathVariable("id") int departmentId,
                                              @RequestBody Department department) {
        if (departmentId != department.getId()){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(departmentService.save(department), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id:[\\d]+}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteCompany(@PathVariable("id") int departmentId) {
        departmentService.delete(departmentId);
    }
}