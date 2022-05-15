package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.TennisCourtRepository;
import com.example.demo.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private TennisCourtRepository tennisCourtRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ReservationService reservationService;
    private TennisCourt t1 = new TennisCourt();
    private TennisCourt t2 = new TennisCourt();
    private TennisCourt t3 = new TennisCourt();
    private Area area1 = new Area();
    private Area area2 = new Area();
    private List<Area> areas = new ArrayList<>();


    private TennisCourtManager m1 = new TennisCourtManager();
    private TennisCourtManager m2 = new TennisCourtManager();

    private Customer c1 = new Customer();
    private Customer c2 = new Customer();

    private Reservation r1 = new Reservation();
    private Reservation r2 = new Reservation();
    private List<Reservation> reservations = new ArrayList();

    @Before
    public void setup() {
        area1.setId(1);
        area1.setName("GHEORGHENI");

        area2.setId(2);
        area2.setName("ZORILOR");
        areas.add(area1);
        areas.add(area2);

        m1.setIdManager(1);
        m1.setFirstName("Oana");
        m1.setLastName("Moisa");
        m1.setUsername("oanamanager");

        t1.setIdTennisCourt(1);
        t1.setName("TennisCourt1");
        t1.setLocation("Cluj");
        t1.setDescription("grass");
        t1.setPricePerHour(23);
        t1.setArea(area1);
        t1.setManager(m1);

        m2.setIdManager(2);
        m2.setFirstName("Ileana");
        m2.setLastName("Pop");
        m2.setUsername("ileanamanager");

        t2.setIdTennisCourt(2);
        t2.setName("TennisCourt2");
        t2.setLocation("Cluj");
        t2.setDescription("grass");
        t2.setPricePerHour(25);
        t2.setArea(area2);
        t2.setManager(m2);

        c1.setIdCustomer(1);
        c1.setFirstName("Oana");
        c1.setLastName("Moisa");
        c1.setUsername("oanam");



        c2.setIdCustomer(2);
        c2.setFirstName("Ileana");
        c2.setLastName("Pop");
        c2.setUsername("ileanapop");

        r1.setId(1);
        r1.setDate(new Date("2022/05/21"));
        r1.setStartHour(14);
        r1.setEndHour(15);
        r1.setCustomer(c1);
        r1.setTennisCourt(t2);

        r2.setId(2);
        r2.setDate(new Date("2022/05/22"));
        r2.setStartHour(14);
        r2.setEndHour(15);
        r2.setCustomer(c2);
        r2.setTennisCourt(t2);

        reservations.add(r1);
        reservations.add(r2);

    }

    @Test
    public void findAllReservationsTest() {

        Mockito.when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> foundReservations= reservationService.findAllReservations();
        assertNotNull(foundReservations);
        assertEquals(2, foundReservations.size());
    }

    @Test
    public void findAllReservationsForTennisCourtTest() {

        Mockito.when(reservationRepository.findAllByTennisCourt_Name("TennisCourt2")).thenReturn(reservations);
        List<Reservation> foundReservations= reservationService.findAllReservationsForTennisCourt("TennisCourt2");
        assertNotNull(foundReservations);
        assertEquals(2, foundReservations.size());
    }
}
