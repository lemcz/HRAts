package HRAts.repository;

import HRAts.model.User;
import HRAts.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Integer> {

    VerificationToken findById(int id);

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}