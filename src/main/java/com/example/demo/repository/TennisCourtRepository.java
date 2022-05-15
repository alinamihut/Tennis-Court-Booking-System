package com.example.demo.repository;


import com.example.demo.model.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TennisCourtRepository extends JpaRepository<TennisCourt, Integer> {

    Optional<TennisCourt> findTennisCourtByManager_Username(String username);

    void deleteByName(String name);

    Optional<TennisCourt> findTennisCourtByName(String name);
}
