import main.java.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void getFirstNameTest() {
        var customer = new Customer();
        customer.setFirstName("Patti");
        String firstName = customer.getFirstName();
        assertEquals("Patti",firstName);
    }

    @Test
    void getLastNameTest() {
        var customer = new Customer();
        customer.setLastName("Smith");
        String lastName = customer.getLastName();
        assertEquals("Smith",lastName);
    }

    @Test
    void getEmailTest() {
        var customer = new Customer();
        customer.setEmail("patti@gmail.com");
        String email = customer.getEmail();
        assertEquals("patti@gmail.com",email);
    }

    @Test
    void validateEmailTest() {
        var customer = new Customer();
        assertThrows(IllegalArgumentException.class,
                () -> {
            customer.validateEmail("patti@yahoo.ie");
                });
    }

    @Test
    void setFirstNameTest() {
        var customer = new Customer("Patti","Smith","patti@gmail.com");
        customer.setFirstName("Ketty");
        String firstName = customer.getFirstName();
        assertEquals("Ketty",firstName);
    }

    @Test
    void setLastNameTest() {
        var customer = new Customer("Patti","Smith","patti@gmail.com");
        customer.setLastName("Brown");
        String lastName = customer.getLastName();
        assertEquals("Brown",lastName);
    }

    @Test
    void setEmailTest() {
        var customer = new Customer("Patti","Smith","patti@gmail.com");
        customer.setEmail("patti@outlook.com");
        String email = customer.getEmail();
        assertEquals("patti@outlook.com",email);
    }

}