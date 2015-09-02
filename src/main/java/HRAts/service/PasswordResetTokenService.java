package HRAts.service;

import HRAts.model.PasswordResetToken;
import HRAts.model.User;
import HRAts.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public PasswordResetToken findByUser(User user) {
        return passwordResetTokenRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Transactional
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {

        PasswordResetToken existingToken = this.findByUser(passwordResetToken.getUser());

        if (existingToken != null) {
            existingToken.setExpiryDate(passwordResetToken.getExpiryDate());
            existingToken.setToken(passwordResetToken.getToken());
            return passwordResetTokenRepository.save(existingToken);
        }

        return passwordResetTokenRepository.save(passwordResetToken);
    }
}