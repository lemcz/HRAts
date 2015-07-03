package HRAts.service;

import HRAts.model.Sector;
import HRAts.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Transactional(readOnly = true)
    public Iterable<Sector> findAll() {
        return sectorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Iterable<Sector> findAllByName() {
        return sectorRepository.getSector();
    }

    @Transactional(readOnly = true)
    public Sector findById(int sectorId) {
        return sectorRepository.findById(sectorId);
    }

    @Transactional(readOnly = true)
    public Sector findByName(String sectorName){
        return sectorRepository.findByName(sectorName);
    }

    @Transactional
    public Sector save(Sector sector) {
        return sectorRepository.save(sector);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int sectorId) {
        sectorRepository.delete(sectorId);
    }
}