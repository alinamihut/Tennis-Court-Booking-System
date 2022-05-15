package com.example.demo.model.dto;

public class TennisCourtDTO {
    private String name;
    private String location;
    private Integer rentingPrice;
    private String description;
    private String manager;
    private String area;

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

    public Integer getRentingPrice() {
        return rentingPrice;
    }

    public void setRentingPrice(Integer rentingPrice) {
        this.rentingPrice = rentingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setAdmin(String manager) {
        this.manager = manager;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public TennisCourtDTO() {
    }

    public TennisCourtDTO(String name, String location, Integer rentingPrice, String description, String manager, String area) {
        this.name = name;
        this.location = location;
        this.rentingPrice = rentingPrice;
        this.description = description;
        this.manager = manager;
        this.area = area;
    }
}
