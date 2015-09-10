package HRAts.controller;

import HRAts.service.ActivityTypeService;
import HRAts.service.ContractTypeService;
import HRAts.service.StatusTypeService;
import HRAts.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/protected/")
public class LookupController {

    @Autowired
    ActivityTypeService activityTypeService;
    @Autowired
    ContractTypeService contractTypeService;
    @Autowired
    StatusTypeService statusTypeService;

    @RequestMapping(value = "{lookupName}TypeLkp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fetchItems(@PathVariable("lookupName") String lookupName) {

        switch(lookupName) {
            case "activity":
                return new ResponseEntity<>(activityTypeService.findAll(), HttpStatus.OK);
            case "contract":
                return new ResponseEntity<>(contractTypeService.findAll(), HttpStatus.OK);
            case "status":
                return new ResponseEntity<>(statusTypeService.findAll(), HttpStatus.OK);
            default:
                return new ResponseEntity<>(new GenericResponse(-1, "Bad request"), HttpStatus.BAD_REQUEST);
        }
    }
}
