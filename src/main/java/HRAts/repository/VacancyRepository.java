package HRAts.repository;

import HRAts.model.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository extends CrudRepository<Vacancy, Integer> {
    Vacancy findById(@Param("id") int id);

    Iterable<Vacancy> findByDepartment_IdIn(List<Integer> departmentsIds);

}
