package HRAts.service;

import HRAts.model.Vacancy;
import HRAts.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Transactional(readOnly = true)
    public Iterable<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vacancy findById(int vacancyId) {
        return vacancyRepository.findById(vacancyId);
    }

    @Transactional
    public Vacancy save(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int vacancyId) {
        vacancyRepository.delete(vacancyId);
    }
}