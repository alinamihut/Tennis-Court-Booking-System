package com.example.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "area", fetch = FetchType.EAGER)
    private List<TennisCourt> tennisCourts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TennisCourt> getTennisCourts() {
        return tennisCourts;
    }

    public void setTennisCourts(List<TennisCourt> tennisCourts) {
        this.tennisCourts = tennisCourts;
    }
}
