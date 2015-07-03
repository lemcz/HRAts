package HRAts.controller;

import HRAts.model.Candidate;
import HRAts.service.DeprecatedCandidateService;
import HRAts.vo.CandidateListVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
@RequestMapping(value = "/protected/candidates")
public class CandidatesController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private DeprecatedCandidateService deprecatedCandidateService;

    @Autowired
    private MessageSource messageSource;

    @Value("100")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("candidatesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
        return createListAllResponse(page, locale);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@ModelAttribute("candidate") Candidate candidate,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        deprecatedCandidateService.save(candidate);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }

        return createListAllResponse(page, locale, "message.create.success");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int candidateId,
                                    @RequestBody Candidate candidate,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (candidateId != candidate.getId()) {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }

        deprecatedCandidateService.save(candidate);

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.update.success");
        }

        return createListAllResponse(page, locale, "message.update.success");
    }

    @RequestMapping(value = "/{candidateId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("candidateId") int candidateId,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        try {
            deprecatedCandidateService.delete(candidateId);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.delete.success");
        }

        return createListAllResponse(page, locale, "message.delete.success");
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@PathVariable("name") String name,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(name, page, locale, null);
    }

    private ResponseEntity<?> search(String name, int page, Locale locale, String actionMessageKey) {
        CandidateListVO candidateListVO = deprecatedCandidateService.findByNameLike(page, maxResults, name);

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToVO(candidateListVO, locale, actionMessageKey, null);
        }

        Object[] args = {name};

        addSearchMessageToVO(candidateListVO, locale, "message.search.for.active", args);

        return new ResponseEntity<CandidateListVO>(candidateListVO, HttpStatus.OK);
    }

    private CandidateListVO listAll(int page) {
        return deprecatedCandidateService.findAll(page, maxResults);
    }

    private ResponseEntity<CandidateListVO> returnListToUser(CandidateListVO candidateList) {
        return new ResponseEntity<CandidateListVO>(candidateList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        CandidateListVO candidateListVO = listAll(page);

        addActionMessageToVO(candidateListVO, locale, messageKey, null);

        return returnListToUser(candidateListVO);
    }

    private CandidateListVO addActionMessageToVO(CandidateListVO candidateListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return candidateListVO;
        }

        candidateListVO.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return candidateListVO;
    }

    private CandidateListVO addSearchMessageToVO(CandidateListVO candidateListVO, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return candidateListVO;
        }

        candidateListVO.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return candidateListVO;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
}