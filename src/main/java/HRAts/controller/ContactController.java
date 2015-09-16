package HRAts.controller;

import HRAts.model.Attachment;
import HRAts.model.Department;
import HRAts.model.Role;
import HRAts.model.User;
import HRAts.service.AttachmentService;
import HRAts.service.DepartmentService;
import HRAts.utils.FileUpload;
import HRAts.service.UserService;
import HRAts.utils.EntityTypeEnum;
import HRAts.utils.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/protected/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory
            .getLogger(ContactController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("contactsList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<User> listContacts(@RequestParam(value = "search", required = false) String search,
                                       @RequestParam(value = "id", required = false) Integer id){
        Role role = Role.ROLE_MANAGER;

        if (search != null && id != null) {
            switch (search) {
                case "company": return userService.findAllByDepartmentManagerWhereCompanyId(id);
                default: return userService.findByRole(role);
            }
        }
        return userService.findByRole(role);
    }

    @RequestMapping(value = "/perVacancy", method = RequestMethod.GET, produces = "application/json")
    public User findManagerByVacancy(@RequestParam(value = "id") Integer vacancyId) {
        return userService.findManagerByVacancyId(vacancyId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView details(@PathVariable int id) {
        return new ModelAndView("contactDetails").addObject("pathId", id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getContactById(@PathVariable int id){
        return userService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody User createContact(@RequestBody final User contact){
        Role role = Role.ROLE_MANAGER;
        contact.setRole(role);
        return userService.save(contact);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json","multipart/mixed","multipart/form-data"}, produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> uploadContactWithFiles(@RequestPart("data") User contact,
                                             @RequestPart(value = "file", required = false) MultipartFile[] files) throws IOException  {

        Role role = Role.ROLE_MANAGER;
        contact.setRole(role);

        List<Department> departmentList = new ArrayList<>();

        for (Department department : contact.getDepartmentList()) {
            Department updatedDepartment = departmentService.findById(department.getId());
            updatedDepartment.setManager(contact);
            departmentList.add(updatedDepartment);
        }

        contact.setDepartmentList(departmentList);

        User savedContact = userService.save(contact);

        if (files != null) {
            FileUpload fileUpload = new FileUpload();
            List<Attachment> contactAttachmentList = new ArrayList<>();

            List<Attachment> uploadedFilesList = fileUpload.uploadFiles(files, EntityTypeEnum.CONTACT, savedContact.getId());

            for (Attachment currentFile : uploadedFilesList) {
                currentFile.setOwner(savedContact.getOwner());
                currentFile.setUser(savedContact);

                contactAttachmentList.add(currentFile);
            }

            savedContact.setAttachmentList(contactAttachmentList);

            savedContact = userService.save(savedContact);
        }

        return new ResponseEntity<>(savedContact, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/{file_name:.+}", method = RequestMethod.GET)
    public void getFile(
            @PathVariable("id") int contactId,
            @PathVariable("file_name") String fileName,
            HttpServletResponse response) throws IOException {

        Attachment candidatesAttachment = attachmentService.findByContactIdAndName(contactId, fileName);

        try {
            // get your file as InputStream
            FileInputStream storedFile = new FileInputStream(candidatesAttachment.getFilePath());

            InputStream is = storedFile;
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateContact(@PathVariable("id") int contactId,
                                           @RequestBody User contact) {
        if (contactId != contact.getId()){
            return new ResponseEntity<>(new GenericResponse(-1, "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.update(contact), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteContact(@PathVariable("id") int contactId) {
        userService.delete(contactId);
    }
}