package HRAts.repository;

import HRAts.model.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeprecatedCandidateRepository extends PagingAndSortingRepository<Candidate, Integer> {
    Page<Candidate> findByNameLike(Pageable pageable, String name);
}