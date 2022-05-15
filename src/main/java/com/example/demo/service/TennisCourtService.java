package com.example.demo.service;

import com.example.demo.model.Area;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.TennisCourtManager;
import com.example.demo.model.dto.TennisCourtDTO;
import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.repository.TennisCourtRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 *Tennis court service.
 */
@Service
public class TennisCourtService {
        private final AreaRepository areaRepository;
        private final ManagerRepository managerRepository;
        private final TennisCourtRepository tennisCourtRepository;

    Logger logger = Logger.getLogger(TennisCourtService.class.getName());
    /**
     * Instantiates a new Tennis court service.
     *
     * @param areaRepository        the area repository
     * @param managerRepository     the manager repository
     * @param tennisCourtRepository the tennis court repository
     */
    public TennisCourtService(AreaRepository areaRepository, ManagerRepository managerRepository, TennisCourtRepository tennisCourtRepository) {
        this.areaRepository = areaRepository;
        this.managerRepository = managerRepository;
        this.tennisCourtRepository = tennisCourtRepository;
    }

    /**
     * Gets list of areas.
     *
     * @return the list of areas in the DB
     */
    public List<Area> getListOfAreas() {
        logger.info("retrieving all areas from the DB");
        return areaRepository.findAll();
    }

    /**
     * Gets list of tennis courts.
     *
     * @return the list of tennis courts in the DB
     */
    public List<TennisCourt> getListOfTennisCourts() {
        logger.info("retrieving all tennis courts from the DB");
        return tennisCourtRepository.findAll();
    }


    /**
     * Inserts tennis court in the DB.
     *
     * @param dto the  tennis court dto
     * @return boolean - true if insertion was made successfully
     */
    public boolean insertTennisCourt(TennisCourtDTO dto){
        Optional<TennisCourtManager> manager = managerRepository.findManagerByUsername(dto.getManager());
        logger.info("inserting tennis court in the db");
        Optional<TennisCourt> tennisCourt = tennisCourtRepository.findTennisCourtByManager_Username(dto.getManager());

        if (tennisCourt.isPresent()){
            logger.warning("Tennis court already exists in the db");
            return false;
        }

        Optional<Area> area = areaRepository.findByName(dto.getArea());

        TennisCourt newTennisCourt = new TennisCourt();

        newTennisCourt.setName(dto.getName());
        newTennisCourt.setLocation(dto.getLocation());
        newTennisCourt.setPricePerHour(dto.getRentingPrice());
        newTennisCourt.setDescription(dto.getDescription());
        newTennisCourt.setArea(area.get());
        newTennisCourt.setManager(manager.get());

        tennisCourtRepository.save(newTennisCourt);
        return true;

    }

    /**
     * Get tennis court for manager.
     *
     * @param managerUsername the manager username
     * @return the tennis court object, if it exists
     */
    public Optional<TennisCourt> getTennisCourtForManager (String managerUsername){
        logger.info("Retrieving tennis court for manager" + managerUsername);
        return tennisCourtRepository.findTennisCourtByManager_Username(managerUsername);
    }

    /**
     * Delete tennis court.
     *
     * @param name the name of the tennis court
     */
    @Transactional
    public void deleteTennisCourt(String name)
    {
        logger.info("Deleting tennis court from the DB");

        tennisCourtRepository.deleteByName(name);
    }

}