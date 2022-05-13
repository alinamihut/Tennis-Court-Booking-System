package com.example.demo.service;


import com.example.demo.model.TennisCourtManager;
import com.example.demo.model.dto.TennisCourtManagerDTO;
import com.example.demo.model.mapper.ManagerMapper;
import com.example.demo.repository.ManagerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    Logger logger = Logger.getLogger(ManagerService.class.getName());

    /**
     * Instantiates a new Tennis Court Manager service.
     *
     * @param managerRepository the tennis court manager repository
     */
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    /**
     * Insert tc manager.
     *
     * @param dto the tc manager dto
     * @return boolean true if tennis court manager was inserted successfully
     */
    public Boolean insertManager(TennisCourtManagerDTO dto) {
        ManagerMapper managerMapper = new ManagerMapper();
        TennisCourtManager manager = managerMapper.convertFromDTO(dto);

        if (checkIfUsernameExists(manager.getUsername())){
            logger.warning("Could not insert tennis court manager");
            return false;
        }
        String hashedPassword = BCrypt.hashpw(manager.getPassword(), BCrypt.gensalt());
        manager.setPassword(hashedPassword);
        logger.info("Inserting manager in the db");
        managerRepository.save(manager);
        return true;
    }

    /**
     * Gets list of tennis court managers in the DB.
     *
     * @return the list of tennis court managers
     **/
    public List<TennisCourtManager> getListOfTennisCourtManagers() {
        logger.info("Retrieving all tennis court managers from the DB");
        return managerRepository.findAll();
    }

    /**
     * Find tennis court manager by username.
     *
     * @param username the username
     * @return mnager object or null if the tennis court managers with doesnt't exist in the DB
     */
    public Optional<TennisCourtManager> findByUsername(String username) {
        logger.info("Found manager with username " + username);
        return managerRepository.findManagerByUsername(username);
    }

    /**
     * Login tennis court manager.
     *
     * @param username the username
     * @param password the password
     * @return boolean - true if tc manager was logged in or false if password is not correct or user doesn't exist in the DB
     */
    public Boolean loginManager(String username, String password) {
        Optional<TennisCourtManager> managerFromDB = managerRepository.findManagerByUsername(username);
        if (!managerFromDB.isPresent()) {
            logger.warning("TC Manager with username " + username + "doesn't exist in the DB");
            return false;
        } else {
            logger.info("TC Manager with username " + username + "logged in successfully");
            if (BCrypt.checkpw(password, managerFromDB.get().getPassword()))
                return true;

        }
        logger.warning("TC Manager with username " + username + "couldn't be logged in");
        return false;
    }

    /**
     * Check if username exists.
     *
     * @param username the username
     * @return boolean - true if tc manager exists in the DB or false if it doesn't
     */

    public boolean checkIfUsernameExists( String username){
        List<TennisCourtManager> allTennisCourtManagers = getListOfTennisCourtManagers();
        for (TennisCourtManager manager: allTennisCourtManagers){
            if (manager.getUsername().equals(username)){
                logger.info("Found tc manager with username " + username);
                return true;
            }
        }
        logger.warning("Couldn't find tc manager with username " + username);
        return false;

    }
    /**
     * Find tc manager by id.
     *
     * @param id the manager id
     * @return ctc manager object or null if the admin with doesn't exist in the DB
     */
    public Optional<TennisCourtManager> findById(Integer id) {
        logger.info("Retrieving tc managers with id " + id + "from the DB");
        return managerRepository.findManagerByIdManager(id);
    }
}
