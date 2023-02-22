import main.java.model.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    @Test
    void getRoomNumberTest() {
        var room = new Room();
        room.setRoomNumber(123);
        Integer roomNumber = room.getRoomNumber();
        assertEquals(Integer.valueOf(123),roomNumber);
    }

    @Test
    void getRoomTypeTest() {
        var room = new Room();
        room.setRoomType("SINGLE");
        String roomType = room.getRoomType();
        assertEquals("SINGLE", roomType);
    }

    @Test
    void getRoomPriceTest() {
        var room = new Room();
        room.setPrice(98.99);
        Double roomPrice = room.getRoomPrice();
        assertEquals(98.99, roomPrice);
    }

    @Test
    void setRoomNumberTest() {
        var room = new Room(100,"SINGLE",120.00);
        room.setRoomNumber(110);
        assertEquals(110, room.getRoomNumber());
    }

    @Test
    void setRoomTypeTest() {
        var room = new Room(100,"SINGLE",120.00);
        room.setRoomType("DOUBLE");
        assertEquals("DOUBLE", room.getRoomType());
    }

    @Test
    void setRoomPriceTest() {
        var room = new Room(100,"SINGLE",120.00);
        room.setPrice(99.99);
        assertEquals(99.99, room.getRoomPrice());
    }
}