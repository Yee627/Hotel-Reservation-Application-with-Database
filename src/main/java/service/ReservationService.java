package main.java.service;

import main.java.jdbc.JDBCHelper;
import main.java.model.Customer;
import main.java.model.Room;
import main.java.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationService {
    private static ReservationService reservationService = null;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    //Add a room
    public static void addRoom(Room room) throws SQLException {
        String INSERT_SQL_QUERY = "INSERT INTO ROOMS (ROOM_ID,ROOM_TYPE,PRICE) VALUES(?,?,?)";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = JDBCHelper.getConnection();
            if ( connection == null )
            {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return;
            }
            connection.setAutoCommit(false);
            /* setAutoCommit(false) will allow you to group multiple subsequent Statement s under the same
            transaction. This transaction will be committed when connection. commit() is invoked,
            as opposed to after each execute() call on individual Statement s (which happens if autocommit is enabled). */
            stmt = connection.prepareStatement(INSERT_SQL_QUERY);
            stmt.setInt(1, room.getRoomNumber());
            stmt.setString(2, room.getRoomType());
            stmt.setDouble(3, room.getRoomPrice());

            stmt.execute();
            System.out.println("Room " + room.getRoomNumber() + " is inserted successfully!");
            System.out.println();
            connection.commit();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }
    }

    //Retrieve all rooms
    public static List<Room> getAllRooms() throws SQLException {
        String SELECT_ALL_SQL_QUERY = "SELECT ROOM_ID,ROOM_TYPE,PRICE FROM ROOMS";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Room> rooms = new ArrayList<>();

        try {
            connection = JDBCHelper.getConnection();
            if ( connection == null )
            {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return rooms;
            }
            stmt = connection.prepareStatement(SELECT_ALL_SQL_QUERY);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomNumber(rs.getInt("ROOM_ID"));
                room.setRoomType(rs.getString("ROOM_TYPE"));
                room.setPrice(rs.getDouble("PRICE"));
                rooms.add(room);
            }
        } catch (SQLException se){
            throw se;
        } finally {
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }
        return rooms;
    }

    //Retrieve a room with room_id
    public static Room getARoom(Integer roomId) throws SQLException {
        String SELECT_SQL_QUERY = "SELECT * FROM ROOMS WHERE ROOM_ID = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Room room = new Room();

        try {
            connection = JDBCHelper.getConnection();
            if (connection == null) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return room;
            }
            stmt = connection.prepareStatement(SELECT_SQL_QUERY);
            stmt.setInt(1, roomId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                room.setRoomNumber(rs.getInt("ROOM_ID"));
                room.setRoomType(rs.getString("ROOM_TYPE"));
                room.setPrice(rs.getDouble("PRICE"));
            }
        } catch (SQLException se){
            throw se;
        } finally {
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }
        return room;
    }

    //Add a reservation
    public static void reserveARoom(Customer customer, Room room, Date checkInDate, Date checkOutDate) throws SQLException {
        String INSERT_SQL_QUERY = "INSERT INTO RESERVATIONS " +
                "(RESERVATION_ID,CUSTOMER_ID,ROOM_ID,CHECK_IN,CHECK_OUT) " +
                "VALUES(null,?,?,?,?)";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = JDBCHelper.getConnection();
            if ( connection == null )
            {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return;
            }
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(INSERT_SQL_QUERY);
            stmt.setInt(1, customer.getId());
            stmt.setInt(2, room.getRoomNumber());
            stmt.setDate(3, new java.sql.Date(checkInDate.getTime()));
            stmt.setDate(4, new java.sql.Date(checkOutDate.getTime()));

            stmt.execute();
            connection.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }

//
    }

    //Retrieve all reservations
    public static List<Reservation> getAllReservation() throws SQLException {
        String SELECT_ALL_SQL_QUERY = "SELECT RESERVATION_ID, CUSTOMERS.CUSTOMER_ID, FIRST_NAME, " +
                "LAST_NAME, EMAIL,ROOMS.ROOM_ID, ROOM_TYPE, PRICE, CHECK_IN, CHECK_OUT From RESERVATIONS " +
                "JOIN CUSTOMERS ON CUSTOMERS.CUSTOMER_ID = RESERVATIONS.CUSTOMER_ID " +
                "JOIN ROOMS ON ROOMS.ROOM_ID = RESERVATIONS.ROOM_ID;";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Reservation> reservations = new ArrayList<>();

        try {
            connection = JDBCHelper.getConnection();
            if ( connection == null )
            {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return reservations;
            }
            stmt = connection.prepareStatement(SELECT_ALL_SQL_QUERY);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationId(rs.getInt("RESERVATION_ID"));
                reservation.setCheckInDate(rs.getDate("CHECK_IN"));
                reservation.setCheckOutDate(rs.getDate("CHECK_OUT"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("CUSTOMERS.CUSTOMER_ID"));
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setLastName(rs.getString("LAST_NAME"));
                customer.setEmail(rs.getString("EMAIL"));

                Room room = new Room();
                room.setRoomNumber(rs.getInt("ROOMS.ROOM_ID"));
                room.setRoomType(rs.getString("ROOM_TYPE"));
                room.setPrice(rs.getDouble("PRICE"));

                reservation.setCustomer(customer);
                reservation.setRoom(room);
                reservations.add(reservation);
            }
        } catch (SQLException se){
            throw se;
        } finally {
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }

        return reservations;
    }

    //Retrieve reservations of one specific customer through customer email
    public static List<Reservation> getCustomerReservation(String customerEmail) throws SQLException {
        List<Reservation> reservationsAccordingToCustomerEmail = new ArrayList<>();
        String SELECT_SQL_QUERY = "SELECT RESERVATION_ID, CUSTOMERS.CUSTOMER_ID, FIRST_NAME, " +
                "LAST_NAME, EMAIL,ROOMS.ROOM_ID, ROOM_TYPE, PRICE, CHECK_IN, CHECK_OUT From RESERVATIONS " +
                "JOIN CUSTOMERS ON CUSTOMERS.CUSTOMER_ID = RESERVATIONS.CUSTOMER_ID " +
                "JOIN ROOMS ON ROOMS.ROOM_ID = RESERVATIONS.ROOM_ID " +
                "WHERE EMAIL = ?";

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = JDBCHelper.getConnection();
            if (connection == null) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return reservationsAccordingToCustomerEmail;
            }
            stmt = connection.prepareStatement(SELECT_SQL_QUERY);
            stmt.setString(1, customerEmail);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationId(rs.getInt("RESERVATION_ID"));
                reservation.setCheckInDate(rs.getDate("CHECK_IN"));
                reservation.setCheckOutDate(rs.getDate("CHECK_OUT"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("CUSTOMER_ID"));
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setLastName(rs.getString("LAST_NAME"));
                customer.setEmail(rs.getString("EMAIL"));

                Room room = new Room();
                room.setRoomNumber(rs.getInt("ROOM_ID"));
                room.setRoomType(rs.getString("ROOM_TYPE"));
                room.setPrice(rs.getDouble("PRICE"));

                reservation.setCustomer(customer);
                reservation.setRoom(room);
                reservationsAccordingToCustomerEmail.add(reservation);
            }
        } catch (SQLException se){
            throw se;
        } finally {
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }
        return reservationsAccordingToCustomerEmail;
    }

    //Retrieve available rooms based on check-in and check-out date
    public static List<Room> findRooms(Date checkInDate, Date checkOutDate) throws SQLException {
        List<Room> availableRooms = new ArrayList<>();

        String SELECT_SQL_QUERY = "SELECT * FROM ROOMS WHERE ROOM_ID NOT IN ( SELECT ROOM_ID FROM RESERVATIONS " +
                "WHERE (CHECK_IN <= ? AND CHECK_OUT >= ?) OR " +
                "(CHECK_IN >= ? AND CHECK_IN <= ?) OR " +
                "(CHECK_OUT >= ? AND CHECK_OUT <= ?))";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = JDBCHelper.getConnection();
            if (connection == null) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return availableRooms;
            }
            stmt = connection.prepareStatement(SELECT_SQL_QUERY);

            stmt.setDate(1, new java.sql.Date(checkInDate.getTime()));
            stmt.setDate(2, new java.sql.Date(checkOutDate.getTime()));
            stmt.setDate(3, new java.sql.Date(checkInDate.getTime()));
            stmt.setDate(4, new java.sql.Date(checkOutDate.getTime()));
            stmt.setDate(5, new java.sql.Date(checkInDate.getTime()));
            stmt.setDate(6, new java.sql.Date(checkOutDate.getTime()));

            rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomNumber(rs.getInt("ROOM_ID"));
                room.setRoomType(rs.getString("ROOM_TYPE"));
                room.setPrice(rs.getDouble("PRICE"));
                availableRooms.add(room);
            }
        } catch (SQLException se){
            throw se;
        } finally {
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }
        return availableRooms;
    }

}
