package HRAts.service;

import HRAts.model.ContractTypeLkp;
import HRAts.repository.ContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContractTypeService {

    @Autowired
    private ContractTypeRepository contractTypeRepository;

    @Transactional(readOnly = true)
    public Iterable<ContractTypeLkp> findAll() {
        return contractTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ContractTypeLkp findById(int contractTypeId) {
        return contractTypeRepository.findById(contractTypeId);
    }

    @Transactional
    public ContractTypeLkp save(ContractTypeLkp contractType) {
        return contractTypeRepository.save(contractType);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int contractTypeId) {
        contractTypeRepository.delete(contractTypeId);
    }
}