package com.example.demo.model.dto;

public class ReservationDTO {
    private String tennisCourt;
    private String customer;
    private String date;
    private Integer startTime;

    public String getTennisCourt() {
        return tennisCourt;
    }

    public void setTennisCourt(String tennisCourt) {
        this.tennisCourt = tennisCourt;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public ReservationDTO() {
    }

    public ReservationDTO(String tennisCourt, String customer, String date, Integer startTime) {
        this.tennisCourt = tennisCourt;
        this.customer = customer;
        this.date = date;
        this.startTime = startTime;
    }
}
