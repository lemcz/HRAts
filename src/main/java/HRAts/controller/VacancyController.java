package HRAts.controller;

import HRAts.model.Vacancy;
import HRAts.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public Iterable<Vacancy> listVacancies(){
        return vacancyService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Vacancy getVacancyById(@PathVariable int id){
        return vacancyService.findById(id);
    }
    //w przypadku wielu(listy) trzeba stworzyc obiekt Iterable<Vacancy>

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Vacancy createVacancy(@RequestBody Vacancy vacancy){
        return vacancyService.save(vacancy);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateVacancy(@PathVariable("id") int vacancyId,
                                           @RequestBody Vacancy vacancy) {
        if (vacancyId != vacancy.getId()){
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Vacancy>(vacancyService.save(vacancy), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteVacancy(@PathVariable("id") int vacancyId) {
        vacancyService.delete(vacancyId);
    }
}