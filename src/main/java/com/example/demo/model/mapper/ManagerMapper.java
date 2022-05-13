package com.example.demo.model.mapper;

import com.example.demo.model.Administrator;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.TennisCourtManager;
import com.example.demo.model.dto.AdminDTO;
import com.example.demo.model.dto.TennisCourtManagerDTO;

public class ManagerMapper {

    public TennisCourtManager convertFromDTO (TennisCourtManagerDTO managerDTO){
        TennisCourtManager manager = new TennisCourtManager();
        manager.setFirstName(managerDTO.getFirstName());
        manager.setLastName(managerDTO.getLastName());
        manager.setUsername(managerDTO.getUsername());
        manager.setPassword(managerDTO.getPassword());
        return manager;
    }

    public TennisCourtManagerDTO convertToDTO (TennisCourtManager tennisCourtManager){
        TennisCourtManagerDTO dto = new TennisCourtManagerDTO();
        dto.setFirstName(tennisCourtManager.getFirstName());
        dto.setLastName(tennisCourtManager.getLastName());
        dto.setUsername(tennisCourtManager.getUsername());
        dto.setPassword(tennisCourtManager.getPassword());
        return dto;
    }
}
