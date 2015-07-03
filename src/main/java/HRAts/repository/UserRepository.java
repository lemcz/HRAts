package HRAts.repository;

import HRAts.model.Role;
import HRAts.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(int id);

    User findByEmail(String email);

    Iterable<User> findByRole(Role role);
}
