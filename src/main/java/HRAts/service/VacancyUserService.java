package HRAts.service;

import HRAts.model.VacancyUser;
import HRAts.repository.VacancyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VacancyUserService {

    @Autowired
    private VacancyUserRepository vacancyUserRepository;

    @Autowired
    private ActivityService activityService;

    @Transactional(readOnly = true)
    public Iterable<VacancyUser> findAll() {
        return vacancyUserRepository.findAll();
    }

    @Transactional
    public VacancyUser save(VacancyUser vacancyUser) {

        //TODO extend activity logging
        String activityNote = "New activity logged per user and vacancy";
        activityService.logUserActivity(activityNote, vacancyUser.getOwner());

        return vacancyUserRepository.save(vacancyUser);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int vacancyId) {
        vacancyUserRepository.delete(vacancyId);
    }
}
