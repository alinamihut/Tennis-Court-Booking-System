package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="tennnis_court_manager")
public class TennisCourtManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idManager;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column (name = "username", unique = true, nullable = false)
    private String username;

    @Column ( nullable = false)
    private String password;

    @OneToOne(mappedBy = "manager", fetch = FetchType.EAGER)
    private TennisCourt tennisCourt;
}
