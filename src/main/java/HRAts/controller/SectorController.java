package HRAts.controller;

import HRAts.model.Sector;
import HRAts.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/protected/sectors")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("sectorsList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Iterable<Sector> listSectors() {
        return sectorService.findAll();
    }

    @RequestMapping(value="/byName", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Sector> listSectorsByName(){
        return sectorService.findAllByName();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Sector getSectorById(@PathVariable int id){
        return sectorService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Sector createSector(@RequestBody Sector sector){
        return sectorService.save(sector);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateSector(@PathVariable("id") int sectorId,
                                           @RequestBody Sector sector) {
        if (sectorId != sector.getId()){
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Sector>(sectorService.save(sector), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void deleteSector(@PathVariable("id") int sectorId) {
        sectorService.delete(sectorId);
    }
}