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

}
