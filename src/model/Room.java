package model;

import java.security.PublicKey;
import java.util.Objects;

public class Room {
    public Integer roomNumber;
    public Double price;
    public String roomType;

    public Room() {
    }

    public Room(Integer roomNumber, String roomType,Double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public Double getRoomPrice() {
        return price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return String.format("Room\nroomNumber:%s\nprice:%s\nroomType:%s\n", roomNumber, price, roomType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber &&
                Double.compare(room.price, price) == 0 &&
                Objects.equals(roomType, room.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomType, price);
    }
}
