package HRAts.repository;

import HRAts.model.Role;
import HRAts.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(int id);

    User findByEmail(String email);

    User findByEmailAndEnabledIsTrue(String email);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT d.manager.id FROM Department d WHERE d.company.id = ?1)")
    Iterable<User> findByDepartmentListCompany_Id(int id);

    Iterable<User> findByRole(Role role);
}
