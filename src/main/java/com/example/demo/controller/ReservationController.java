package com.example.demo.controller;


import com.example.demo.model.dto.ReservationDTO;
import com.example.demo.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity create(@RequestBody ReservationDTO dto) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date date = formatter.parse(dto.getDate());
            LocalDate today = LocalDate.now();
            if (date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(today)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation doesnt have a valid date!");
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        if (dto.getStartTime() < 0 || dto.getStartTime() >23) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Reservation doesnt have valid time!");
        }

            if (reservationService.insertReservation(dto)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("New reservation added!");
            } else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("This reservation already exists");
        }
    }

