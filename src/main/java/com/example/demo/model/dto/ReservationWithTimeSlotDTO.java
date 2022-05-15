package com.example.demo.model.dto;

public class ReservationWithTimeSlotDTO {
    private String tennisCourt;
    private String customer;
    private String date;
    private Integer startTime;
    private Integer endTime;

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

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public ReservationWithTimeSlotDTO(String tennisCourt, String customer, String date, Integer startTime, Integer endTime) {
        this.tennisCourt = tennisCourt;
        this.customer = customer;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
