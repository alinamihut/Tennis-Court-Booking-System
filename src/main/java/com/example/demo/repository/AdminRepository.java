package com.example.demo.repository;

import com.example.demo.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Integer>  {
    Optional<Administrator> findByUsername(String username);

    Optional<Administrator> findByIdAdmin(Integer id);

}
