package com.example.demo.model.mapper;

import com.example.demo.model.Customer;
import com.example.demo.model.dto.CustomerDTO;

public class CustomerMapper {

    public Customer convertFromDTO (CustomerDTO dto){
        Customer customer = Customer.builder().firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                username(dto.getUsername()).
                password(dto.getPassword()).
                build();

        return customer;
    }

    public CustomerDTO convertToDTO (Customer customer){
        CustomerDTO dto = CustomerDTO.builder().firstName(customer.getFirstName()).
                lastName(customer.getLastName()).username(customer.getUsername()).
                password(customer.getPassword()).build();

        return dto;
    }

}
