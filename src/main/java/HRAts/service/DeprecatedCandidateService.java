package HRAts.service;

import HRAts.model.Candidate;
import HRAts.repository.DeprecatedCandidateRepository;
import HRAts.vo.CandidateListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeprecatedCandidateService {

    @Autowired
    private DeprecatedCandidateRepository deprecatedCandidateRepository;

    @Transactional(readOnly = true)
    public CandidateListVO findAll(int page, int maxResults) {
        Page<Candidate> result = executeQueryFindAll(page, maxResults);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindAll(lastPage, maxResults);
        }

        return buildResult(result);
    }

    public void save(Candidate candidate) {
        deprecatedCandidateRepository.save(candidate);
    }

    @Secured("ROLE_ADMIN")
    public void delete(int candidateId) {
        deprecatedCandidateRepository.delete(candidateId);
    }

    @Transactional(readOnly = true)
    public CandidateListVO findByNameLike(int page, int maxResults, String name) {
        Page<Candidate> result = executeQueryFindByName(page, maxResults, name);

        if(shouldExecuteSameQueryInLastPage(page, result)){
            int lastPage = result.getTotalPages() - 1;
            result = executeQueryFindByName(lastPage, maxResults, name);
        }

        return buildResult(result);
    }

    private boolean shouldExecuteSameQueryInLastPage(int page, Page<Candidate> result) {
        return isUserAfterOrOnLastPage(page, result) && hasDataInDataBase(result);
    }

    private Page<Candidate> executeQueryFindAll(int page, int maxResults) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return deprecatedCandidateRepository.findAll(pageRequest);
    }

    private Sort sortByNameASC() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    private CandidateListVO buildResult(Page<Candidate> result) {
        return new CandidateListVO(result.getTotalPages(), result.getTotalElements(), result.getContent());
    }

    private Page<Candidate> executeQueryFindByName(int page, int maxResults, String name) {
        final PageRequest pageRequest = new PageRequest(page, maxResults, sortByNameASC());

        return deprecatedCandidateRepository.findByNameLike(pageRequest, "%" + name + "%");
    }

    private boolean isUserAfterOrOnLastPage(int page, Page<Candidate> result) {
        return page >= result.getTotalPages() - 1;
    }

    private boolean hasDataInDataBase(Page<Candidate> result) {
        return result.getTotalElements() > 0;
    }
}