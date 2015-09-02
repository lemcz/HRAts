package HRAts.service;

import HRAts.model.GenericResponse;
import HRAts.model.User;
import HRAts.model.VerificationToken;
import HRAts.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Transactional
    public VerificationToken save(VerificationToken verificationToken) {

        VerificationToken existingToken = this.findByUser(verificationToken.getUser());

        if (existingToken != null) {
            existingToken.setExpiryDate(verificationToken.getExpiryDate());
            existingToken.setToken(verificationToken.getToken());
            return verificationTokenRepository.save(existingToken);
        }

        return verificationTokenRepository.save(verificationToken);
    }

    public GenericResponse verifyUser(String token) {
        VerificationToken verificationToken = this.findByToken(token);

        if (verificationToken == null) {
            return new GenericResponse(-1, "Does not exist");
        }

        if (verificationToken.isExpired()) {
            return new GenericResponse(-1, "Expired");
        }

        if (verificationToken.isVerified()) {
            return new GenericResponse(-1, "Already verified");
        }

        User verifiedUser = userService.findById(verificationToken.getUser().getId());
        verifiedUser.setEnabled(true);

        userService.save(verifiedUser);
        verificationToken.setVerified(true);
        this.save(verificationToken);

        return new GenericResponse(0, "Success");
    }
}