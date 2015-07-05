package HRAts.repository;

import HRAts.model.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    Activity findById(@Param("id") int id);

}