package HRAts.service;

import HRAts.model.Department;
import HRAts.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

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
        return departmentRepository.save(department);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int sectorId) {
        departmentRepository.delete(sectorId);
    }
}