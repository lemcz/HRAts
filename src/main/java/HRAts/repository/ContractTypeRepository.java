package HRAts.repository;

import HRAts.model.ContractTypeLkp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContractTypeRepository extends CrudRepository<ContractTypeLkp, Integer> {
    ContractTypeLkp findById(@Param("id") int id);
}