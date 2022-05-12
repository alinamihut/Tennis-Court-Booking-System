package com.example.demo.repository;

import com.example.demo.model.Customer;
import com.example.demo.model.TennisCourtManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<TennisCourtManager, Integer> {
}
