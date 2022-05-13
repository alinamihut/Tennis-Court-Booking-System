package com.example.demo.controller;

import com.example.demo.model.Administrator;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.mapper.AdminMapper;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    Logger logger = Logger.getLogger(AdminController.class.getName());
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;

    }


    @GetMapping
    public List<Administrator> findAll() {
        logger.info("GET method for finding all existing admins");
        return adminService.getListOfAdmins();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Administrator> findById(@PathVariable("id") Integer id){
        Optional<Administrator> admin = adminService.findById(id);
        logger.info("GET method for finding an admin");
        if(admin.isPresent()) {
            return ResponseEntity.ok().body(admin.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity loginAdmin(@RequestBody AdminDTO resource){
        Boolean loginAdminMade = adminService.loginAdmin(resource.getUsername(), resource.getPassword());

        logger.info("POST method for logging in an admin");
        if(loginAdminMade) {
            Optional<Administrator> admin = adminService.findByUsername(resource.getUsername());

            return new ResponseEntity<>( HttpStatus.OK);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping( path = "/create", consumes = {"application/json"})
    public ResponseEntity create(@RequestBody AdminDTO resource) {
        AdminMapper mapper = new AdminMapper();

        Administrator newAdministrator = mapper.convertFromDTO(resource);
        logger.info("POST method for adding an admin in the DB");
        if( adminService.insertAdmin(newAdministrator)){

            return  ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully!");
        }
        else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
        }
    }
}
