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

@Service
public class TennisCourtService {
        private final AreaRepository areaRepository;
        private final ManagerRepository managerRepository;
        private final TennisCourtRepository tennisCourtRepository;
    public TennisCourtService(AreaRepository areaRepository, ManagerRepository managerRepository, TennisCourtRepository tennisCourtRepository) {
        this.areaRepository = areaRepository;
        this.managerRepository = managerRepository;
        this.tennisCourtRepository = tennisCourtRepository;
    }

    public List<Area> getListOfAreas() {
        return areaRepository.findAll();
    }
    public List<TennisCourt> getListOfTennisCourts() {
        return tennisCourtRepository.findAll();
    }


    public boolean insertTennisCourt(TennisCourtDTO dto){
        Optional<TennisCourtManager> manager = managerRepository.findManagerByUsername(dto.getManager());

        Optional<TennisCourt> tennisCourt = tennisCourtRepository.findTennisCourtByManager_Username(dto.getManager());

        if (tennisCourt.isPresent()){
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

    @Transactional
    public void deleteTennisCourt(String name)
    {

        tennisCourtRepository.deleteByName(name);
    }

}