package api;

import model.Customer;
import model.Reservation;
import model.Room;
import service.CustomerService;
import service.ReservationService;

import java.sql.SQLException;
import java.util.List;

public class AdminResource {
    private static AdminResource adminResource = null;

    private  AdminResource() {
    }

    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    CustomerService customerService = CustomerService.getInstance();
    ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) throws SQLException {
        return customerService.getCustomer(email);
    }

    public void addRoom(Room room) throws SQLException {
        reservationService.addRoom(room);
    }

    public List<Room> getAllRooms() throws SQLException {
        return reservationService.getAllRooms();
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerService.getAllCustomers();
    }

    public List<Reservation> displayAllReservations() throws SQLException {
        return reservationService.getAllReservation();
    }
}
