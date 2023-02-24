package main.java.model;

import java.util.Date;

public class Reservation {
    private Integer reservationId;
    private Customer customer;
    private main.java.model.Room room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation() {

    }

    public Reservation(Customer customer, Room room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return String.format("\nReservation Id:%s\ncustomer:%s\nroom:%s\ncheckInDate:%s\ncheckOutDate:%s\n", reservationId, customer, room, checkInDate, checkOutDate);
    }

}
