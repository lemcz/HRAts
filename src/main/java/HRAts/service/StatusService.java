package HRAts.service;

import HRAts.model.Status;
import HRAts.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private VacancyUserService vacancyUserService;

    @Transactional(readOnly = true)
    public Iterable<Status> findAll() {
        return statusRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Status findById(int statusId) {
        return statusRepository.findById(statusId);
    }

    @Transactional(readOnly = true)
    public Iterable<Status> findByVacancyUserId(int vacancyUserId) {
        return statusRepository.findByVacancyUser_Id(vacancyUserId);
    }

    @Transactional
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int statusId) {
        statusRepository.delete(statusId);
    }
}