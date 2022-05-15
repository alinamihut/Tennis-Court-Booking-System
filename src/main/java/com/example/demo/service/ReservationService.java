package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Reservation;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.dto.ReservationDTO;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.TennisCourtRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private TennisCourtRepository tennisCourtRepository;
    private CustomerRepository customerRepository;

    public ReservationService(ReservationRepository reservationRepository, TennisCourtRepository tennisCourtRepository, CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.tennisCourtRepository = tennisCourtRepository;
        this.customerRepository = customerRepository;
    }

    public List<Reservation> findAllReservations(){
        return reservationRepository.findAll();
    }


    public boolean checkIfReservationAlreadyExists(Reservation newReservation){
        List<Reservation> allReservations = findAllReservations();

        for (Reservation r:allReservations) {
            if (r.getTennisCourt().equals(newReservation.getTennisCourt()) &&
                    r.getDate().equals(newReservation.getDate()) &&
                    r.getStartHour().equals(newReservation.getStartHour()))
                return true;
        }
        return false;
    }
    public boolean insertReservation (ReservationDTO dto){

        Reservation newReservation = new Reservation();
        Optional<Customer> customer = customerRepository.findCustomerByUsername(dto.getCustomer());
        Optional<TennisCourt> tennisCourt = tennisCourtRepository.findTennisCourtByName(dto.getTennisCourt());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date date = formatter.parse(dto.getDate());
            System.out.println(date);
            System.out.println(formatter.format(date));
            newReservation.setDate(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        newReservation.setCustomer(customer.get());
        newReservation.setTennisCourt(tennisCourt.get());
        newReservation.setStartHour(dto.getStartTime());
        newReservation.setEndHour(dto.getStartTime()+1);


        if (checkIfReservationAlreadyExists(newReservation)){
            return false;
        }


        reservationRepository.save(newReservation);
        return true;

    }
}
