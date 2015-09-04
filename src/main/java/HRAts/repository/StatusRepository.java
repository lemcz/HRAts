package HRAts.repository;

import HRAts.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    Status findById(@Param("id") int id);

    Iterable<Status> findByVacancyUser_Id(@Param("vacancy_user_id") int vacancyUserId);
}