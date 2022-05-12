package com.example.demo.repository;

import com.example.demo.model.Administrator;
import com.example.demo.model.TennisCourtManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Integer>  {
}
