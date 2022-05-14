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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Integer pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TennisCourtManager getManager() {
        return manager;
    }

    public void setManager(TennisCourtManager manager) {
        this.manager = manager;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
