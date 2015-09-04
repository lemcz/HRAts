package HRAts.service;

import HRAts.model.ActivityTypeLkp;
import HRAts.repository.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityTypeService {

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Transactional(readOnly = true)
    public Iterable<ActivityTypeLkp> findAll() {
        return activityTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ActivityTypeLkp findById(int activityTypeId) {
        return activityTypeRepository.findById(activityTypeId);
    }

    @Transactional
    public ActivityTypeLkp findByName(String name) {
        return activityTypeRepository.findByName(name);
    }

    @Transactional
    public ActivityTypeLkp save(ActivityTypeLkp activityType) {
        return activityTypeRepository.save(activityType);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int activityTypeId) {
        activityTypeRepository.delete(activityTypeId);
    }
}