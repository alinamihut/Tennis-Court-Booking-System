package com.example.demo.repository;

import com.example.demo.model.Customer;
import com.example.demo.model.TennisCourtManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<TennisCourtManager, Integer> {
    Optional<TennisCourtManager> findManagerByUsername(String username);

    Optional<TennisCourtManager> findManagerByIdManager(Integer id);
}
