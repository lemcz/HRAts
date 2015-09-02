package HRAts.service;

import HRAts.constants.ActivityTypeEnum;
import HRAts.model.Activity;
import HRAts.model.ActivityTypeLkp;
import HRAts.model.User;
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

    @Autowired
    private ActivityTypeService activityTypeService;

    @Transactional(readOnly = true)
    public Iterable<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Activity findById(int activityId) {
        return activityRepository.findById(activityId);
    }

    @Transactional(readOnly = true)
    public Activity findByCandidateId(int candidateId) {
        return activityRepository.findByCandidate_Id(candidateId);
    }

    @Transactional
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Transactional
    public Activity logUserActivity(String activityNote, User owner) {

        Activity activity = new Activity();

        ActivityTypeLkp activityTypeLkp = activityTypeService.findByName(ActivityTypeEnum.ADD.toString());

        activity.setActivityType(activityTypeLkp);
        activity.setNote(activityNote);
        activity.setOwner(owner);

        return activityRepository.save(activity);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int activityId) {
        activityRepository.delete(activityId);
    }
}