import main.java.model.Customer;
import main.java.service.CustomerService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    @Test
    void addAndGetCustomerTest() throws SQLException {
        //Add the customer to the database
        CustomerService.addCustomer("Patti","Smith","patti@gmail.com");

        //Query the database for the newly added customer
        Customer customer = CustomerService.getCustomer("patti@gmail.com");

        //Check if the data in the database matches the test data
        assertEquals("Patti", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("patti@gmail.com", customer.getEmail());
    }

    @Test
    void getAllCustomerTest() throws SQLException {
        //Create a test customer list with the expected data
        List<Customer> expectedCustomers = List.of(new Customer("Patti","Smith","patti@gmail.com"));

        //Get the list of customers from the database
        List<Customer> actualCustomers = CustomerService.getAllCustomers();

        //Assert that the number of customers in the list matches the number of customers in the database
        assertEquals(expectedCustomers.size(), actualCustomers.size());

        // Assert that the data in the list matches the data in the database
        for (int i = 0; i < expectedCustomers.size(); i++) {
            assertEquals(expectedCustomers.get(i).getFirstName(), actualCustomers.get(i).getFirstName());
            assertEquals(expectedCustomers.get(i).getLastName(), actualCustomers.get(i).getLastName());
            assertEquals(expectedCustomers.get(i).getEmail(), actualCustomers.get(i).getEmail());
        }
    }
}