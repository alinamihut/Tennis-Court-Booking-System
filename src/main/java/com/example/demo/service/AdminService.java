package com.example.demo.service;

import com.example.demo.model.Administrator;
import com.example.demo.repository.AdminRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Admin service class.
 */
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    Logger logger = Logger.getLogger(AdminService.class.getName());
    /**
     * Instantiates a new Admin service.
     *
     * @param adminRepository the admin repository
     */
    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Gets list of admins in the DB.
     *
     * @return the list of admins
     */
    public List<Administrator> getListOfAdmins() {
        logger.info("Retrieving all admins from the DB");
        return adminRepository.findAll();
    }

    /**
     * Create administrator object.
     *
     * @param administrator - the administrator
     * @return the administrator saved in the DB
     */
    public  Administrator create(Administrator administrator) {
        logger.info("Inserting admin in the db");
        return adminRepository.save(administrator);
    }

    /**
     * Insert admin.
     *
     * @param admin the admin
     * @return boolean true if admin was inserted successfully
     */
    public Boolean insertAdmin(Administrator admin) {

        if (checkIfUsernameExists(admin.getUsername())){
            return false;
        }
        String hashedPassword = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(hashedPassword);
        logger.info("Inserting admin in the db");
        adminRepository.save(admin);
        return true;
    }

    /**
     * Check if username exists.
     *
     * @param username the username
     * @return boolean - true if admin exists in the DB or false if it doesn't
     */
    public boolean checkIfUsernameExists( String username){
        List<Administrator> allAdmins = getListOfAdmins();
        for (Administrator admin: allAdmins){
            if (admin.getUsername().equals(username)){
                logger.info("Found admin with username " + username);
                return true;
            }
        }
        logger.warning("Couldn't find admin with username " + username);
        return false;

    }

    /**
     * Login admin.
     *
     * @param username the username
     * @param password the password
     * @return boolean - true if admin was logged in or false if password is not correct or user doesn't exist in the DB
     */
    public Boolean loginAdmin(String username, String password) {
        Optional<Administrator> adminFromDB = adminRepository.findByUsername(username);
        if (!adminFromDB.isPresent()) {
            logger.warning("Admin with username " + username + "doesn't exist in the DB");
            return false;
        } else {
            if (BCrypt.checkpw(password, adminFromDB.get().getPassword())) {
                logger.info("Admin with username " + username + "logged in successfully");
                return true;
            }
        }
        logger.warning("Admin with username " + username + "couldn't be logged in");
        return false;
    }

    /**
     * Find admin by username.
     *
     * @param username the username
     * @return admin object or null if the admin with doesnt't exist in the DB
     */
    public Optional<Administrator> findByUsername(String username) {
        logger.info("Retrieving admin " + username + "from the DB");
        return adminRepository.findByUsername(username);
    }

    /**
     * Find admin by id.
     *
     * @param id the admin id
     * @return admin object or null if the admin with doesn't exist in the DB
     */
    public Optional<Administrator> findById(Integer id) {
        logger.info("Retrieving admin with id " + id + "from the DB");
        return adminRepository.findByIdAdmin(id);
    }
}
