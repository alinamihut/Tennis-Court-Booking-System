package com.example.demo.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
@Entity
@Table(name="customer")
public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idCustomer;

        @Column(nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        @Column (name = "username", unique = true, nullable = false)
        private String username;

        @Column ( nullable = false)
        private String password;

        @OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
        private Reservation reservation;

    public Customer(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }




}
