package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdmin;

    @Column (name = "username", unique = true, nullable = false)
    private String username;

    @Column ( nullable = false)
    private String password;



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

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Administrator() {
    }

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
