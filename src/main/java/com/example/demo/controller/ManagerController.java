package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.TennisCourtManager;
import com.example.demo.model.dto.CustomerDTO;
import com.example.demo.model.dto.TennisCourtManagerDTO;
import com.example.demo.service.CustomerService;
import com.example.demo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    Logger logger = Logger.getLogger(ManagerController.class.getName());
    public ManagerController(CustomerService customerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public List<TennisCourtManager> findAll() {
        logger.info("GET method for finding all existing managers");
        return managerService.getListOfTennisCourtManagers();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<TennisCourtManager> loginManager(@RequestBody TennisCourtManagerDTO dto){

        Boolean loginManagerMade = managerService.loginManager(dto.getUsername(), dto.getPassword());

        logger.info("POST method for logging in a customer");
        if(loginManagerMade) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping( path = "/create", consumes = {"application/json"})
    public ResponseEntity create(@RequestBody TennisCourtManagerDTO resource) {
        logger.info("POST method for creating a tennis court manager");
        if( managerService.insertManager(resource)){
            return  ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");
        }
        else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
        }
    }
}
