package HRAts.repository;

import HRAts.model.ActivityTypeLkp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActivityTypeRepository extends CrudRepository<ActivityTypeLkp, Integer> {
    ActivityTypeLkp findById(@Param("id") int id);

}