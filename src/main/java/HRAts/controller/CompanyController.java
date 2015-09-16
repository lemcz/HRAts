package HRAts.controller;

import HRAts.model.Attachment;
import HRAts.model.Company;
import HRAts.model.Department;
import HRAts.model.Vacancy;
import HRAts.service.CompanyService;
import HRAts.utils.EntityTypeEnum;
import HRAts.utils.FileUpload;
import HRAts.utils.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/protected/companies")
public class CompanyController {

    private static final Logger logger = LoggerFactory
            .getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("companiesList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Company> listCompanies(){
        return companyService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable int id) {
        return new ModelAndView("companyDetails").addObject("pathId", id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Company getCompanyById(@PathVariable int id){
        return companyService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity createCompany(@RequestPart("data") Company company,
                                                      @RequestPart(value = "file", required = false) MultipartFile[] files) throws IOException{

        Company savedCompany = companyService.save(company);

        if (files != null) {
            FileUpload fileUpload = new FileUpload();
            List<Attachment> contactAttachmentList = new ArrayList<>();

            List<Attachment> uploadedFilesList = fileUpload.uploadFiles(files, EntityTypeEnum.VACANCY, savedCompany.getId());

            for (Attachment currentFile : uploadedFilesList) {
                currentFile.setCompany(savedCompany);
                currentFile.setOwner(savedCompany.getDepartmentList().get(0).getOwner());

                contactAttachmentList.add(currentFile);
            }

            savedCompany.setAttachmentList(contactAttachmentList);

            savedCompany = companyService.save(savedCompany);
        }

        return new ResponseEntity<>(new GenericResponse(0, savedCompany), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCompany(@PathVariable("id") int companyId,
                                           @RequestBody Company company) {
        if (companyId != company.getId()){
            return new ResponseEntity<>(new GenericResponse(-1, "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(companyService.save(company), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<?> deleteCompany(@PathVariable("id") int companyId) {

        Company company = companyService.findById(companyId);

        List<Department> departmentList = company.getDepartmentList();
        for (Department department : departmentList) {
            if (department.getManager() != null) {
                logger.info("Cannot DELETE requested company with id : " + companyId);
                return new ResponseEntity<>("Other objects depend on this company, cannot delete", HttpStatus.FORBIDDEN);
            }
        }
        logger.info("Will delete company with ID: " + companyId);
        companyService.delete(companyId);
        return new ResponseEntity<>("Delete successful", HttpStatus.OK);
    }
}