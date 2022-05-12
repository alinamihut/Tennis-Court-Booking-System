package com.example.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tennis_court")
public class TennisCourt {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTennisCourt;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer pricePerHour;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "idManager")
    private TennisCourtManager manager;

    @OneToMany(mappedBy = "tennisCourt")
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Area area;


}
