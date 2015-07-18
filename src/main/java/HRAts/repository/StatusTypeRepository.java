package HRAts.repository;

import HRAts.model.StatusTypeLkp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StatusTypeRepository extends CrudRepository<StatusTypeLkp, Integer> {
    StatusTypeLkp findById(@Param("id") int id);

}