package HRWAATS.repository;

import org.springframework.data.repository.CrudRepository;
import HRWAATS.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
