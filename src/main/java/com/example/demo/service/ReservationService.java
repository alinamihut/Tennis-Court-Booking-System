package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Reservation;
import com.example.demo.model.TennisCourt;
import com.example.demo.model.dto.ReservationDTO;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.TennisCourtRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


/**
 *  Reservation service class.
 */
@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private TennisCourtRepository tennisCourtRepository;
    private CustomerRepository customerRepository;

    Logger logger = Logger.getLogger(ReservationService.class.getName());
    /**
     * Instantiates a new Reservation service.
     *
     * @param reservationRepository the reservation repository
     * @param tennisCourtRepository the tennis court repository
     * @param customerRepository    the customer repository
     */
    public ReservationService(ReservationRepository reservationRepository, TennisCourtRepository tennisCourtRepository, CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.tennisCourtRepository = tennisCourtRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Find all reservations list.
     *
     * @return the list of reservation objects
     */
    public List<Reservation> findAllReservations(){
        logger.info("Retrieving all reservations from the DB");
        return reservationRepository.findAll();
    }

    /**
     * Find all reservations for a tennis court
     *
     * @param name the name of the tennis court
     * @return the list of reservations
     */
    public List<Reservation> findAllReservationsForTennisCourt(String name){
        logger.info("Retrieving all restaurants from a tennis court");
        return reservationRepository.findAllByTennisCourt_Name(name);
    }

    /**
     * Check if reservation already exists in the DB
     *
     * @param newReservation the reservation object
     * @return the boolean - true if reservation exists in the DB and false if not
     */
    public boolean checkIfReservationAlreadyExists(Reservation newReservation){
        List<Reservation> allReservations = findAllReservations();

        for (Reservation r:allReservations) {
            if (r.getTennisCourt().equals(newReservation.getTennisCourt()) &&
                    r.getDate().equals(newReservation.getDate()) &&
                    r.getStartHour().equals(newReservation.getStartHour()))
                logger.warning("Reservation with the selected time frame already exists");
                return true;
        }
        logger.info("Reservation with the selected time frame doesnt exist in the DB");
        return false;
    }

    /**
     * Insert reservation.
     *
     * @param dto the reservation dto
     * @return boolean - true if insertion was made successfully
     */
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

        logger.info("Inserting reservation in the DB");
        reservationRepository.save(newReservation);
        return true;

    }


    /**
     * Export reservations for a tennis court as pdf.
     *
     * @param reservations the reservations list
     * @param tennisCourt  the tennis court name
     */
    public void exportReservationsAsPDF (List<Reservation> reservations, String tennisCourt){
        Document document = new Document();
        logger.info("Exporting reservations' list as pdf");
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Reservations_For_TennisCourt_" + tennisCourt+ ".pdf"));
            document.open();
            Font redFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, new CMYKColor(0, 255, 255, 0));
            Paragraph title = new Paragraph("Upcoming reservations for tennis court " + tennisCourt, redFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            for (Reservation r: reservations) {

                Font blackFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL, new CMYKColor(0, 0, 0, 255));
                Font blackFont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new CMYKColor(0, 0, 0, 255));
                String date = "Date: " + r.getDate().toString();
                String time = "Time :" + r.getStartHour() + " - " + r.getEndHour();
                String customername= "Customer: " + r.getCustomer().getFirstName() + r.getCustomer().getLastName();
                String customer= "Customer username: " + r.getCustomer().getUsername();
                Paragraph paragraphDate = new Paragraph(date, blackFont2);
                document.add(paragraphDate);
                Paragraph paragraphTime = new Paragraph(time, blackFont2);
                document.add(paragraphTime);
                Paragraph paragraphCustomer1 = new Paragraph(customername, blackFont2);
                document.add(paragraphCustomer1);
                Paragraph paragraphCustomer = new Paragraph(customer, blackFont2);
                document.add(paragraphCustomer);
                Paragraph p = new Paragraph("----------------------------------------------------------------------", blackFont3);
                document.add(p);
            }
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}
