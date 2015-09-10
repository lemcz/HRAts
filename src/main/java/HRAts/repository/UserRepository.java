package HRAts.repository;

import HRAts.model.Role;
import HRAts.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(int id);

    User findByEmail(String email);

    User findByEmailAndEnabledIsTrue(String email);

    @Query("SELECT u FROM User u, Department d WHERE u.id = d.manager.id and d.company.id = ?1")
    Iterable<User> findByDepartmentListCompany_Id(int id);

    Iterable<User> findByRole(Role role);

    @Query("SELECT u FROM User u, VacancyUser vu  WHERE u.id = vu.candidate.id and vu.vacancy.id = ?1")
    Iterable<User> findByVacancyUserCandidateListVacancy_Id(int id);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT d.manager.id FROM Department d, Vacancy v WHERE d.manager.id = v.id and v.id = ?1)")
    User findManagerByVacancyId(int id);

    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_CANDIDATE' AND u.id NOT IN (SELECT vu.candidate.id FROM VacancyUser vu WHERE vu.vacancy.id = ?1)")
    Iterable<User> findCandidatesByVacancyUserVacancy_IdIsNot(int id);
}
