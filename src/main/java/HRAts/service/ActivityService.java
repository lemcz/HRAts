package HRAts.service;

import HRAts.model.Activity;
import HRAts.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Transactional(readOnly = true)
    public Iterable<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Activity findById(int activityId) {
        return activityRepository.findById(activityId);
    }

    @Transactional
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int activityId) {
        activityRepository.delete(activityId);
    }
}