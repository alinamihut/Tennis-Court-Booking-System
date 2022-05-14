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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TennisCourt getTennisCourt() {
        return tennisCourt;
    }

    public void setTennisCourt(TennisCourt tennisCourt) {
        this.tennisCourt = tennisCourt;
    }
}
