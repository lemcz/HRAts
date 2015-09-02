package HRAts.service;

import HRAts.model.Vacancy;
import HRAts.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private ActivityService activityService;

    @Transactional(readOnly = true)
    public Iterable<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vacancy findById(int vacancyId) {
        return vacancyRepository.findById(vacancyId);
    }

    @Transactional(readOnly = true)
    public Iterable<Vacancy> findByDepartmentIdIn(List<Integer> departmentsIds) {
        return vacancyRepository.findByDepartment_IdIn(departmentsIds);
    }

/*    @Transactional(readOnly = true)
    public Iterable<Vacancy> findByVacancyUserListCandidate_IdIn(List<Integer> candidatesIds) {
        return vacancyRepository.findByVacancyUserListCandidate_IdIn(candidatesIds);
    }*/

    @Transactional
    public Vacancy save(Vacancy vacancy) {
        //TODO add owner to activity logging
        //activityLog.setOwner(vacancy.getOwner());
        String activityNote = "New vacancy added to the repository";

        activityService.logUserActivity(activityNote, vacancy.getDepartment().getOwner());

        return vacancyRepository.save(vacancy);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int vacancyId) {
        vacancyRepository.delete(vacancyId);
    }
}