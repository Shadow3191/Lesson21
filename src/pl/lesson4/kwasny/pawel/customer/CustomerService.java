package pl.lesson4.kwasny.pawel.customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerService {
    private CustomerDao customerDao;
    private Scanner scanner = new Scanner(System.in);

    public CustomerService(Connection connection) {
        customerDao = new CustomerDao(connection);
    }

    public void show() throws SQLException {
        for (Customer showCustomers : customerDao.find()) {
            System.out.println(showCustomers.getId() + " | " + showCustomers.getName() + " | " + showCustomers.getNipNumber());
        }
    }

    public void add(Customer customer) throws SQLException {
        customerDao.add(customer);
    }

    public void edit(Customer customer) throws SQLException {
        customerDao.edit(customer);
    }

    public void delete(Customer customer) throws SQLException {
        customerDao.delete(customer);
    }
}
