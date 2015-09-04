package HRAts.repository;

import HRAts.model.VacancyUser;
import org.springframework.data.repository.CrudRepository;

public interface VacancyUserRepository extends CrudRepository<VacancyUser, Integer> {

    VacancyUser findByVacancy_IdAndCandidate_Id(int vacancyId, int candidateId);
}
