import main.java.model.Customer;
import main.java.model.Reservation;
import main.java.model.Room;
import main.java.service.CustomerService;
import main.java.service.ReservationService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void addAndGetRoomTest() throws SQLException {
        Room expectedRoom = new Room(100, "SINGLE",99.99);
        //Add the room to the database
        ReservationService.addRoom(expectedRoom);

        //Query the database for the newly added room
        Room actualRoom = ReservationService.getARoom(100);

        //Check if the data in the database matches the test data
        assertEquals(100, actualRoom.getRoomNumber());
        assertEquals("SINGLE", actualRoom.getRoomType());
        assertEquals(99.99, actualRoom.getRoomPrice());
    }

    @Test
    void getAllRoomsTest() throws SQLException {
        //Create a test room list with the expected data
        List<Room> expectedRooms = List.of(new Room(100, "SINGLE",99.99));

        //Get the list of rooms from the database
        List<Room> actualRooms = ReservationService.getAllRooms();

        //Assert that the number of rooms in the list matches the number of rooms in the database
        assertEquals(expectedRooms.size(), actualRooms.size());

        // Assert that the data in the list matches the data in the database
        for (int i = 0; i < expectedRooms.size(); i++) {
            assertEquals(expectedRooms.get(i).getRoomNumber(), actualRooms.get(i).getRoomNumber());
            assertEquals(expectedRooms.get(i).getRoomType(), actualRooms.get(i).getRoomType());
            assertEquals(expectedRooms.get(i).getRoomPrice(), actualRooms.get(i).getRoomPrice());
        }
    }

    @Test
    void addAndGetReservationsTest() throws SQLException, ParseException {
        //Add a new reservation to the database
        CustomerService.addCustomer("Thom","Yorke","thom@yahoo.com");
        Customer customer = CustomerService.getCustomer("thom@yahoo.com");

        Room room = new Room(110,"DOUBLE",199.99);
        ReservationService.addRoom(room);

        Date checkInDate = dateFormat.parse("2023-08-09");
        Date checkOutDate = dateFormat.parse("2023-08-10");

        ReservationService.reserveARoom(customer, room, checkInDate, checkOutDate);

        //Query the database for all reservations of the customer
        List<Reservation> reservations = ReservationService.getCustomerReservation("thom@yahoo.com");

        //Check if the test reservation is contained in reservation list of the customer
        boolean found = false;
        for (Reservation reservation : reservations) {
            if ( reservation.getCustomer().equals(customer) &&
                    reservation.getRoom().equals(room) &&
                    reservation.getCheckInDate().equals(checkInDate) &&
                    reservation.getCheckOutDate().equals(checkOutDate)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void getAllReservations() throws SQLException, ParseException {
        //Create a test reservation list with the expected data
        List<Reservation> expectedReservations = List.of(new Reservation(
                new Customer("Thom","Yorke","thom@yahoo.com"),
                new Room(110,"DOUBLE",199.99),
                dateFormat.parse("2023-08-09"),dateFormat.parse("2023-08-10")));

        //Get the list of all reservations from the database
        List<Reservation> reservations = ReservationService.getAllReservation();

        //Assert that the number of reservations in the list matches the number of reservations in the database
        assertEquals(expectedReservations.size(), reservations.size());

        // Assert that the data in the list matches the data in the database
        for (int i = 0; i < expectedReservations.size(); i++) {
            assertEquals(expectedReservations.get(i).getCustomer().getFirstName(), reservations.get(i).getCustomer().getFirstName());
            assertEquals(expectedReservations.get(i).getCustomer().getLastName(), reservations.get(i).getCustomer().getLastName());
            assertEquals(expectedReservations.get(i).getCustomer().getEmail(), reservations.get(i).getCustomer().getEmail());
            assertEquals(expectedReservations.get(i).getRoom().getRoomNumber(), reservations.get(i).getRoom().getRoomNumber());
            assertEquals(expectedReservations.get(i).getRoom().getRoomType(), reservations.get(i).getRoom().getRoomType());
            assertEquals(expectedReservations.get(i).getRoom().getRoomPrice(), reservations.get(i).getRoom().getRoomPrice());
            assertEquals(expectedReservations.get(i).getCheckInDate(), reservations.get(i).getCheckInDate());
            assertEquals(expectedReservations.get(i).getCheckOutDate(), reservations.get(i).getCheckOutDate());
        }
    }

    @Test
    void findRoomsTest() throws ParseException, SQLException {
        //Get the list of available rooms from the database
        Date checkInDate = dateFormat.parse("2023-08-09");
        Date checkOutDate = dateFormat.parse("2023-08-10");
        List<Room> availableRoom = ReservationService.findRooms(checkInDate, checkOutDate);

        //Get all reservations from database
        List<Reservation> reservations = ReservationService.getAllReservation();

        //Assert that the room of the reservation list with specific date arrangement is not in the available room list
        for (Reservation reservation : reservations) {
            if ((reservation.getCheckInDate().before(checkInDate) && reservation.getCheckOutDate().after(checkOutDate))
            || (checkInDate.after(reservation.getCheckInDate()) && checkInDate.before(reservation.getCheckOutDate()))
            || (checkOutDate.after(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckOutDate()))) {
                assertFalse(availableRoom.contains(reservation.getRoom()));
            }
        }
    }
}