package com.example.demo.repository;


import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    Optional<Customer> findByIdCustomer(Integer integer);

    Optional<Customer> findCustomerByUsername(String username);

    Optional<Customer> findCustomerByIdCustomer(Integer id);

}
