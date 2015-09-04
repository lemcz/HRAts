package HRAts.service;

import HRAts.model.StatusTypeLkp;
import HRAts.repository.StatusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusTypeService {

    @Autowired
    private StatusTypeRepository statusTypeRepository;

    @Transactional(readOnly = true)
    public Iterable<StatusTypeLkp> findAll() {
        return statusTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public StatusTypeLkp findById(int statusTypeId) {
        return statusTypeRepository.findById(statusTypeId);
    }

    @Transactional
    public StatusTypeLkp save(StatusTypeLkp statusType) {
        return statusTypeRepository.save(statusType);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int statusTypeId) {
        statusTypeRepository.delete(statusTypeId);
    }
}