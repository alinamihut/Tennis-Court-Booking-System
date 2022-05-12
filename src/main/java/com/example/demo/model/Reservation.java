package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    private Integer startHour;


    @Column(nullable = false)
    private Integer endHour;

    @OneToOne
    @JoinColumn(name="idCustomer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="idTennisCourt")
    private TennisCourt tennisCourt;












}
