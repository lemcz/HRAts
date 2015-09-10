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

    @Transactional
    public Iterable<Department> findByCompany_Id(int companyId) {
        return departmentRepository.findByCompany_Id(companyId);
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
        String savedDepartmentName = savedDepartment.getName();
        String savedDepartmentCompanyName = savedDepartment.getCompany().getName();
        User departmentOwner = savedDepartment.getOwner();
        String savedDepartmentOwner = userService.findById(departmentOwner.getId()).getEmail();
        String logString = savedDepartmentOwner+" added "+savedDepartmentName+" to company "+ savedDepartmentCompanyName;
        activityService.logUserActivity(logString, savedDepartment.getOwner());

        return savedDepartment;
    }

    @Transactional
    public Department update(Department department) {
        return departmentRepository.save(department);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int sectorId) {
        departmentRepository.delete(sectorId);
    }

    @Transactional
    public Iterable<Department> findByManagerId(int managerId) {
        return departmentRepository.findByManager_Id(managerId);
    }
}