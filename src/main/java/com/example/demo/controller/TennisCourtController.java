package com.example.demo.controller;

import com.example.demo.model.Area;
import com.example.demo.model.Customer;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.dto.TennisCourtDTO;
import com.example.demo.service.TennisCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tenniscourt")
public class TennisCourtController {

    private TennisCourtService tennisCourtService;

    Logger logger = Logger.getLogger(TennisCourtController.class.getName());
    @Autowired
    public TennisCourtController(TennisCourtService tennisCourtService) {
        this.tennisCourtService = tennisCourtService;
    }

    @GetMapping(path="/getareas")
    public List<String> findAllAreas() {
        logger.info("GET method for retrieving all areas");
        List<Area> allAreas = tennisCourtService.getListOfAreas();
        List<String> zones = new ArrayList();
        for (Area a:allAreas){
            zones.add(a.getName());

        }
        return zones;
    }

    @GetMapping(path="/get")
    public List<String> findAllTennisCourtNames() {
        logger.info("GET method for retrieving all tennis court names");
        List<TennisCourt> allCourts = tennisCourtService.getListOfTennisCourts();
        List<String> courts = new ArrayList();
        for (TennisCourt t:allCourts){
            courts.add(t.getName());

        }
        return courts;
    }

    @GetMapping(path="/getall/{area}")
    public List<TennisCourtDTO> findAllTennisCourtsInArea(@PathVariable("area") String area) {
        logger.info("GET method for retrieving all tennis courts in an area");
        List<TennisCourt> allCourts = tennisCourtService.getListOfTennisCourts();
        List<TennisCourtDTO> courtsDTOS = new ArrayList();
        for (TennisCourt t:allCourts){
            if (t.getArea().getName().equals(area)) {
                TennisCourtDTO dto = new TennisCourtDTO(t.getName(), t.getLocation(), t.getPricePerHour(),
                        t.getDescription(), t.getManager().getUsername(), t.getArea().getName());

                courtsDTOS.add(dto);
            }
        }
        return courtsDTOS;
    }
    @GetMapping(path="/getall")
    public List<TennisCourtDTO> findAllTennisCourts() {
        logger.info("GET method for retrieving all tennis courts");
        List<TennisCourt> allCourts = tennisCourtService.getListOfTennisCourts();
        List<TennisCourtDTO> courtsDTOS = new ArrayList();
        for (TennisCourt t:allCourts){
            TennisCourtDTO dto = new TennisCourtDTO(t.getName(), t.getLocation(),t.getPricePerHour(),
                    t.getDescription(), t.getManager().getUsername(), t.getArea().getName());

            courtsDTOS.add(dto);

        }
        return courtsDTOS;
    }

    @PostMapping(path = "/add")
    public ResponseEntity create(@RequestBody TennisCourtDTO dto) {
        logger.info("POST method for creating a tennis court entity");
        if (dto.getRentingPrice() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tennis court couldn't be added!");
        }
        if (tennisCourtService.insertTennisCourt(dto)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New tennis court added!");
        } else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("This manager already has a tennis court registered");
    }

    @DeleteMapping(path="/delete/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity  delete(@PathVariable("name") String name) {
        System.out.println(name);
        logger.info("DELETE method for deleting a tennis court");
        tennisCourtService.deleteTennisCourt(name);
        return ResponseEntity.status(HttpStatus.OK).body("Tennis Court deleted successfully!");
    }


    @GetMapping(path = "/{managername}")
    public TennisCourtDTO getTennisCourtByManager(@PathVariable String managername) {
        logger.info("GET method for finding tennis court for manager "  + managername);
        Optional<TennisCourt> tennisCourt = tennisCourtService.getTennisCourtForManager(managername);
        System.out.println(tennisCourt.get().getName());
        if (tennisCourt.isPresent()) {
            TennisCourtDTO dto = new TennisCourtDTO(tennisCourt.get().getName(), tennisCourt.get().getLocation(),
                    tennisCourt.get().getPricePerHour(), tennisCourt.get().getDescription(), tennisCourt.get().getManager().getUsername(),
                    tennisCourt.get().getArea().getName());
            return dto;
        }

        return null;
    }
}
