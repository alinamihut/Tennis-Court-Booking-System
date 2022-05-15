package com.example.demo.controller;


import com.example.demo.model.Reservation;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.dto.ReservationDTO;
import com.example.demo.model.dto.ReservationWithTimeSlotDTO;
import com.example.demo.service.ReservationService;
import com.example.demo.service.TennisCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;
    private TennisCourtService tennisCourtService;

    @Autowired
    public ReservationController(ReservationService reservationService, TennisCourtService tennisCourtService) {
        this.reservationService = reservationService;
        this.tennisCourtService = tennisCourtService;
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
    @GetMapping(path="/getall/{managername}")
    public List<ReservationWithTimeSlotDTO> findAllReservationsforTennisCourt(@PathVariable String managername) {
        Optional<TennisCourt> t = tennisCourtService.getTennisCourtForManager(managername);
        List<Reservation> reservationsList = reservationService.findAllReservationsForTennisCourt(t.get().getName());
        List<ReservationWithTimeSlotDTO> dtos = new ArrayList();
        for (Reservation r: reservationsList){
            ReservationWithTimeSlotDTO dto = new ReservationWithTimeSlotDTO(r.getTennisCourt().getName(),
                    r.getCustomer().getUsername(), r.getDate().toString(), r.getStartHour(), r.getEndHour());

            dtos.add(dto);

        }
        return dtos;
    }

    @GetMapping(path = "/exportreservations/{name}")
    public ResponseEntity exportReservations(@PathVariable String name) {
        List<Reservation> reservationsList = reservationService.findAllReservationsForTennisCourt(name);

        reservationService.exportReservationsAsPDF(reservationsList, name);
        return ResponseEntity.status(HttpStatus.CREATED).body("Menu exported successfully!");

    }

    }

