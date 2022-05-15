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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TennisCourt getTennisCourt() {
        return tennisCourt;
    }

    public void setTennisCourt(TennisCourt tennisCourt) {
        this.tennisCourt = tennisCourt;
    }
}
