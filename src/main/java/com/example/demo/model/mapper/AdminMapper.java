package com.example.demo.model.mapper;

import com.example.demo.model.Administrator;
import com.example.demo.model.dto.AdminDTO;

public class AdminMapper {
    public Administrator convertFromDTO (AdminDTO adminDTO){
        Administrator admin = new Administrator();
        admin.setUsername( adminDTO.getUsername());
        admin.setPassword(adminDTO.getPassword());
        return admin;
    }

    public AdminDTO convertToDTO (Administrator administrator){
        AdminDTO dto = new AdminDTO();
        dto.setUsername(administrator.getUsername());
        dto.setPassword(administrator.getPassword());
        return dto;
    }
}
