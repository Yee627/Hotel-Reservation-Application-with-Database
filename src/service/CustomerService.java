package service;

import jdbc.JDBCHelper;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private static CustomerService customerService = null;

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    List<Customer> customers;

    //Add a customer
    public void addCustomer(String firstName, String lastName, String email) throws SQLException {
        Customer customer = new Customer(firstName, lastName, email);
        String INSERT_SQL_QUERY = "INSERT INTO CUSTOMERS (CUSTOMER_ID, FIRST_NAME,LAST_NAME,EMAIL) VALUES(null,?,?,?)";
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
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);

            stmt.execute();
            System.out.println("Customer " + firstName + " " + lastName + " is inserted successfully!");
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

    //Retrieve one customer information based on customer email
    public Customer getCustomer(String customerEmail) throws SQLException {
        String SELECT_SQL_QUERY = "SELECT CUSTOMER_ID,FIRST_NAME,LAST_NAME,EMAIL FROM CUSTOMERS WHERE EMAIL=?";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Customer customer = new Customer();
        try {
            connection = JDBCHelper.getConnection();
            if (connection == null) {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return customer;
            }
            stmt = connection.prepareStatement(SELECT_SQL_QUERY);
            stmt.setString(1, customerEmail);
            rs = stmt.executeQuery();
            while (rs.next()) {
                customer.setId(rs.getInt("CUSTOMER_ID"));
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setLastName(rs.getString("LAST_NAME"));
                customer.setEmail(rs.getString("EMAIL"));
            }
        } catch (SQLException se){
            throw se;
        } finally {
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }
        return customer;
    }

    //Retrieve all customer
    public List<Customer> getAllCustomers() throws SQLException {
        String SELECT_ALL_SQL_QUERY = "SELECT CUSTOMER_ID,FIRST_NAME,LAST_NAME,EMAIL FROM CUSTOMERS";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        customers = new ArrayList<>();
        try {
            connection = JDBCHelper.getConnection();
            if ( connection == null )
            {
                System.out.println( "Error getting the connection. Please check if the DB server is running" );
                return customers;
            }
            stmt = connection.prepareStatement(SELECT_ALL_SQL_QUERY);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("CUSTOMER_ID"));
                customer.setFirstName(rs.getString("FIRST_NAME"));
                customer.setLastName(rs.getString("LAST_NAME"));
                customer.setEmail(rs.getString("EMAIL"));
                customers.add(customer);
            }
        } catch (SQLException se){
            throw se;
        } finally {
            JDBCHelper.closeResultSet(rs);
            JDBCHelper.closePreparedStatement(stmt);
            JDBCHelper.closeConnection(connection);
        }

        return customers;
    }
}
