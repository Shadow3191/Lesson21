package pl.lesson4.kwasny.pawel.customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private CustomerDao customerDao;

    public CustomerService(Connection connection) {
        customerDao = new CustomerDao(connection);
    }

    public List<Customer> find() throws SQLException {
        return customerDao.find();
    }

    public void add(Customer customer) {
        customerDao.add(customer);
    }

    public void edit(Customer customer) {
        customerDao.edit(customer);
    }

    public void delete(Customer customer) {
        customerDao.delete(customer);
    }
}
