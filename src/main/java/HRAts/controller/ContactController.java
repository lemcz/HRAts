package HRAts.controller;

import HRAts.model.*;
import HRAts.service.*;
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

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityTypeService activityTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("contactsList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<User> listContacts(){
        Role role = Role.ROLE_MANAGER;
        return userService.findByRole(role);
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

        List<Attachment> contactAttachmentList = new ArrayList<>();

        Role role = Role.ROLE_MANAGER;
        contact.setRole(role);

        String message = "";
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String name = file.getOriginalFilename();
            try {
                // Creating the directory to store file
                File usersDirectory = createUsersDirectory("ContactsFiles", contact.getEmail());

                // Create the file on server
                File serverFile = createFileOnServer(file, usersDirectory, name);

                message = message + "You successfully uploaded file = "  + name + "</br>";

                Attachment currentFile = new Attachment();
                currentFile.setName(name);
                currentFile.setFilePath(serverFile.getAbsolutePath());
                currentFile.setOwner(contact.getOwner());
                currentFile.setContact(contact);

                contactAttachmentList.add(currentFile);
            } catch (Exception e) {
                return new ResponseEntity<String>("You failed to upload " + name + " => " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            contact.setContactAttachmentList(contactAttachmentList);
        }

        List<Department> departmentList = new ArrayList<>();

        for (Department department : contact.getDepartmentList()) {
            Department updatedDepartment = departmentService.findById(department.getId());
            updatedDepartment.setManager(contact);
            departmentList.add(updatedDepartment);
        }

        contact.setDepartmentList(departmentList);

        //Log activity
        Activity activityLog = new Activity();
        activityLog.setOwner(contact.getOwner());
        activityLog.setNote("New contact added to the repository");

        ActivityTypeLkp activityLogType = activityTypeService.findByName(activityTypeService.ACTIVITY_TYPE_CREATE_RECORD);

        activityLog.setActivityType(activityLogType);

        User savedContact = userService.save(contact);

        activityLog.setCandidate(savedContact);
        activityService.save(activityLog);

        return new ResponseEntity<User>(savedContact, HttpStatus.OK);
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
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(userService.save(contact), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteContact(@PathVariable("id") int contactId) {
        userService.delete(contactId);
    }

    private File createFileOnServer(MultipartFile file, File usersDirectory, String name) throws IOException {
        byte[] bytes = file.getBytes();
        File serverFile = new File(usersDirectory.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("Server File Location = " + serverFile.getAbsolutePath());
        return serverFile;
    }

    private File createUsersDirectory(String attachmentCategory, String usersIdDirectory) {

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "UploadedFiles" + File.separator + attachmentCategory + File.separator + usersIdDirectory);
        if (!dir.exists())
            dir.mkdirs();

        return dir;
    }
}