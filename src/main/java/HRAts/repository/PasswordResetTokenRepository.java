package HRAts.repository;

import HRAts.model.PasswordResetToken;
import HRAts.model.User;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {

    PasswordResetToken findById(int id);

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

}