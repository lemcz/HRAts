package HRAts.repository;

import HRAts.model.Vacancy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository extends CrudRepository<Vacancy, Integer> {
    Vacancy findById(@Param("id") int id);

    Iterable<Vacancy> findByDepartment_IdIn(List<Integer> departmentsIds);

    Iterable<Vacancy> findByDepartment_IdInAndVacancyUserListCandidate_IdIsNot(List<Integer> departmentsIds, Integer candidateId);

    Iterable<Vacancy> findByDepartment_Manager_Id(int id);

    @Query("SELECT v FROM Vacancy v, VacancyUser vu WHERE v.id = vu.vacancy.id and vu.candidate.id = ?1")
    Iterable<Vacancy> findByVacancy_UserCandidate_Id(int id);
}
