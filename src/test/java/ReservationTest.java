import main.java.model.Customer;
import main.java.model.Reservation;
import main.java.model.Room;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Test
    void getCustomerTest() {
        Reservation reservation = new Reservation();
        reservation.setCustomer(new Customer("Ross","Geller","ross@gmail.com"));
        assertEquals("Ross", reservation.getCustomer().getFirstName());
        assertEquals("Geller", reservation.getCustomer().getLastName());
        assertEquals("ross@gmail.com", reservation.getCustomer().getEmail());
    }

    @Test
    void getRoomTest() {
        Reservation reservation = new Reservation();
        reservation.setRoom(new Room(120,"SINGLE",119.99));
        assertEquals(120, reservation.getRoom().getRoomNumber());
        assertEquals("SINGLE", reservation.getRoom().getRoomType());
        assertEquals(119.99, reservation.getRoom().getRoomPrice());
    }

    @Test
    void getCheckInDateTest() throws ParseException {
        Reservation reservation = new Reservation();
        Date checkInDate = dateFormat.parse("2023-08-09");
        reservation.setCheckInDate(checkInDate);
        assertEquals(checkInDate, reservation.getCheckInDate());
    }

    @Test
    void getCheckOutDateTest() throws ParseException {
        Reservation reservation = new Reservation();
        Date checkOutDate = dateFormat.parse("2023-08-10");
        reservation.setCheckOutDate(checkOutDate);
        assertEquals(checkOutDate, reservation.getCheckOutDate());
    }

    @Test
    void setCustomerTest() throws ParseException {
        Reservation reservation = new Reservation(new Customer("Ross","Geller","ross@gmail.com"),
                new Room(120,"SINGLE",119.99),dateFormat.parse("2023-08-09"),
                dateFormat.parse("2023-08-10"));
        reservation.setCustomer(new Customer("Ross","White","ross@gmail.com"));
        assertEquals("White", reservation.getCustomer().getLastName());
    }

    @Test
    void setRoomTest() throws ParseException {
        Reservation reservation = new Reservation(new Customer("Ross","Geller","ross@gmail.com"),
                new Room(120,"SINGLE",119.99),dateFormat.parse("2023-08-09"),
                dateFormat.parse("2023-08-10"));
        reservation.setRoom(new Room(120, "SINGLE", 99.99));
        assertEquals(99.99, reservation.getRoom().getRoomPrice());
    }

    @Test
    void setCheckInDateTest() throws ParseException {
        Reservation reservation = new Reservation(new Customer("Ross", "Geller", "ross@gmail.com"),
                new Room(120, "SINGLE", 119.99), dateFormat.parse("2023-08-09"),
                dateFormat.parse("2023-08-10"));

        Date checkInDate = dateFormat.parse("2023-08-06");
        reservation.setCheckInDate(checkInDate);
        assertEquals(checkInDate, reservation.getCheckInDate());
    }

    @Test
    void setCheckOutDateTest() throws ParseException {
        Reservation reservation = new Reservation(new Customer("Ross", "Geller", "ross@gmail.com"),
                new Room(120, "SINGLE", 119.99), dateFormat.parse("2023-08-09"),
                dateFormat.parse("2023-08-10"));

        Date checkOutDate = dateFormat.parse("2023-08-13");
        reservation.setCheckOutDate(checkOutDate);
        assertEquals(checkOutDate, reservation.getCheckOutDate());
    }

}