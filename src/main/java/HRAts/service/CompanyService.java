package HRAts.service;

import HRAts.model.Company;
import HRAts.model.Sector;
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
@Transactional
public class CompanyService implements Serializable{

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SectorRepository sectorRepository;

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

        return companyRepository.save(company);
    }

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