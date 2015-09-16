package HRAts.controller;

import HRAts.model.Attachment;
import HRAts.model.Vacancy;
import HRAts.service.VacancyService;
import HRAts.utils.EntityTypeEnum;
import HRAts.utils.FileUpload;
import HRAts.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/protected/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("vacanciesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Vacancy> listVacancies(@RequestParam(value = "search", required = false) String search,
                                           @RequestParam(value = "id", required = false) Integer id){

        if (search != null && id != null) {
            switch (search) {
                case "manager": return vacancyService.findByManagerId(id);
                case "candidate": return vacancyService.findByCandidateId(id);
                default: return vacancyService.findAll();
            }
        }

        return vacancyService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable int id) {
        return new ModelAndView("vacancyDetails").addObject("pathId", id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Vacancy getVacancyById(@PathVariable int id){
        return vacancyService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json", "multipart/mixed", "multipart/form-data"}, produces = "application/json")
    public @ResponseBody ResponseEntity createVacancy(@RequestPart("data") Vacancy vacancy,
                                                      @RequestPart(value = "file", required = false) MultipartFile[] files) throws IOException {

        Vacancy savedVacancy = vacancyService.save(vacancy);

        if (files != null) {
            FileUpload fileUpload = new FileUpload();
            List<Attachment> contactAttachmentList = new ArrayList<>();

            List<Attachment> uploadedFilesList = fileUpload.uploadFiles(files, EntityTypeEnum.VACANCY, savedVacancy.getId());

            for (Attachment currentFile : uploadedFilesList) {
                currentFile.setVacancy(savedVacancy);
                currentFile.setOwner(savedVacancy.getOwner());

                contactAttachmentList.add(currentFile);
            }

            savedVacancy.setAttachmentList(contactAttachmentList);

            savedVacancy = vacancyService.save(savedVacancy);
        }

        return new ResponseEntity<>(savedVacancy, HttpStatus.OK);
    }

    @RequestMapping(value = "/perDepartments", headers={"type=list"}, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Iterable<Vacancy> getVacanciesByDepartmentsIds(@RequestBody(required = false) List<Integer> departmentsIds){
        if(departmentsIds.isEmpty()) {
            return new ArrayList<>();
        }
        return vacancyService.findByDepartmentIdIn(departmentsIds);
    }

    @RequestMapping(value = "/perDepartments", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Iterable<Vacancy> getVacanciesByDepartmentsIdsPerCandidate(@RequestBody(required = true) List<Integer> departmentsIds,
                                                               @RequestParam(value="candidateId") int candidateId){
        return vacancyService.findByDepartmentIdInAndCandidateIdEqual(departmentsIds, candidateId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateVacancy(@PathVariable("id") int vacancyId,
                                           @RequestBody Vacancy vacancy) {
        if (vacancyId != vacancy.getId()){
            return new ResponseEntity<>(new GenericResponse(-1, "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(vacancyService.save(vacancy), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteVacancy(@PathVariable("id") int vacancyId) {
        vacancyService.delete(vacancyId);
    }
}