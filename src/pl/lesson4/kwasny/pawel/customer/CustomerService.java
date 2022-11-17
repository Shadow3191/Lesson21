package pl.lesson4.kwasny.pawel.customer;

import java.sql.Connection;
import java.util.List;

public class CustomerService {
    private CustomerDao customerDao;

    public CustomerService(Connection connection) {
        customerDao = new CustomerDao(connection);
    }

    public List<Customer> find() {
        return customerDao.find();
    }

    public Customer get(Integer id) {
        return customerDao.get(id);
    }

//    public Customer getName(String name) {
//        return customerDao.getName(name);
//    }
//    public Customer getNip(String nipNumber) {
//        return customerDao.getNip(nipNumber);
//    }

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
