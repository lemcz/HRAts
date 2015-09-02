package HRAts.service;

import HRAts.constants.ActivityTypeEnum;
import HRAts.model.*;
import HRAts.repository.CompanyRepository;
import HRAts.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService implements Serializable{

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    ActivityTypeService activityTypeService;

    @Transactional(readOnly = true)
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Company findById(int companyId) {
        return companyRepository.findById(companyId);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int companyId) {
        companyRepository.delete(companyId);
    }

    @Transactional
    public Company save(Company company) {

        List<Sector> sectorList = company.getSectorList();

        if (sectorList != null) {
            sectorList = updateSectors(sectorList);
            company.setSectorList(sectorList);
        }

        Company savedCompany = companyRepository.save(company);

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

        ActivityTypeLkp activityLogType = activityTypeService.findByName(ActivityTypeEnum.OTHER.toString());

        activityLog.setActivityType(activityLogType);

        //todo set company for the activity
        //activityLog.setCompany(savedCompany);

        activityService.save(activityLog);

        return savedCompany;
    }

    @Transactional
    public Company update(Company company) {

        //TODO add update logic
        Company savedCompany = companyRepository.save(company);

        return savedCompany;
    }

    @Transactional
    private List<Sector> updateSectors(List<Sector> existingList) {

        List<Sector> newList = new ArrayList<>();

        int listSize = existingList.size();

        for (int i = 0; i < listSize; i++) {
            Sector currentItem = existingList.get(i);
            String currentItemName = currentItem.getName();
            Sector existingItem = sectorRepository.findByName(currentItemName);

            if (existingItem == null) {
                existingItem = sectorRepository.save(currentItem);
            }

            newList.add(existingItem);
        }

        return newList;
    }

}