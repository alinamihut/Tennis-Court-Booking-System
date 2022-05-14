package com.example.demo.controller;

import com.example.demo.model.Area;
import com.example.demo.model.Customer;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.dto.TennisCourtDTO;
import com.example.demo.service.TennisCourtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tenniscourt")
public class TennisCourtController {

    private TennisCourtService tennisCourtService;

    Logger logger = Logger.getLogger(TennisCourtController.class.getName());
    public TennisCourtController(TennisCourtService tennisCourtService) {
        this.tennisCourtService = tennisCourtService;
    }

    @GetMapping(path="/getareas")
    public List<String> findAllAreas() {
        List<Area> allAreas = tennisCourtService.getListOfAreas();
        List<String> zones = new ArrayList();
        for (Area a:allAreas){
            zones.add(a.getName());

        }
        return zones;
    }

    @GetMapping(path="/get")
    public List<String> findAllTennisCourts() {
        List<TennisCourt> allCourts = tennisCourtService.getListOfTennisCourts();
        List<String> courts = new ArrayList();
        for (TennisCourt t:allCourts){
            courts.add(t.getName());

        }
        return courts;
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

}
