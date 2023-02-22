package main.java.api;

import main.java.model.Customer;
import main.java.model.Room;
import main.java.model.Reservation;
import main.java.service.CustomerService;
import main.java.service.ReservationService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HotelResource {
    private static HotelResource hotelResource = null;

    private HotelResource() {
    }

    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) throws SQLException {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) throws SQLException {
        customerService.addCustomer(firstName, lastName, email);
    }

    public Room getRoom(Integer roomNumber) throws SQLException {
        return reservationService.getARoom(roomNumber);
    }


    public void bookARoom(Customer customer, Room room, Date checkInDate, Date CheckOutDate) throws SQLException {
        reservationService.reserveARoom(customer, room, checkInDate, CheckOutDate);
    }


    public List<Reservation> getCustomersReservations(String customerEmail) throws SQLException {
        return reservationService.getCustomerReservation(customerEmail);
    }

    public List<Room> findARoom(Date checkIn, Date checkOut) throws SQLException {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
