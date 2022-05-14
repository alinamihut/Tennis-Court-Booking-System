package com.example.demo.repository;


import com.example.demo.model.Area;
import com.example.demo.model.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{

    Optional<Area> findByName(String name);
}
