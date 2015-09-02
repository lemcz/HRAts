package HRAts.service;

import HRAts.model.Department;
import HRAts.model.User;
import HRAts.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Department findById(int departmentId) {
        return departmentRepository.findById(departmentId);
    }

    @Transactional(readOnly = true)
    public Department findByName(Department departmentName){
        return departmentRepository.findByName(departmentName);
    }

    @Transactional(readOnly = true)
    public Iterable<Department> findByCompanyId(int companyId) {
        return departmentRepository.findByCompany_Id(companyId);
    }

    @Transactional(readOnly = true)
        public Iterable<Department> findByCompanyIdIn(List<Integer> companiesIds) {
        return departmentRepository.findByCompany_IdIn(companiesIds);
    }

    @Transactional(readOnly = true)
    public Iterable<Department> findByCompany_IdAndManagerIsNull(int companyId) {
        return departmentRepository.findByCompany_IdAndManagerIsNull(companyId);
    }

    @Transactional(readOnly = true)
    public Iterable<Department> findByCompany_IdAndVacancyListIsNull(int companyId) {
        return departmentRepository.findByCompany_IdAndVacancyListIsNull(companyId);
    }

    @Transactional
    public Department save(Department department) {
        Department savedDepartment = departmentRepository.save(department);

        //Log activity
        //todo set department for the activity, might be better organised tho
        //activityLog.setDepartment(savedDepartment);
        String savedDepartmentName = savedDepartment.getName();
        String savedDepartmentCompanyName = savedDepartment.getCompany().getName();
        User departmentOwner = savedDepartment.getOwner();
        String savedDepartmentOwner = userService.findById(departmentOwner.getId()).getEmail();
        System.out.println("OWNERS EMAIL : " +savedDepartmentOwner);
        String logString = savedDepartmentOwner+" added "+savedDepartmentName+" to company "+ savedDepartmentCompanyName;
        activityService.logUserActivity(logString, savedDepartment.getOwner());

        return savedDepartment;
    }

    @Transactional
    public Department update(Department department) {
        //TODO add update logic
        Department updatedDepartment = departmentRepository.save(department);
        return updatedDepartment;
    }

    @Secured("ROLE_ADMIN")
    public void delete(int sectorId) {
        departmentRepository.delete(sectorId);
    }

}