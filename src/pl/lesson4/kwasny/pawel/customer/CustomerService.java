package pl.lesson4.kwasny.pawel.customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerService {
    private CustomerDao customerDao = new CustomerDao();
    private Scanner scanner = new Scanner(System.in);


    public void show(Connection connection) throws SQLException {
        for (Customer showCustomers : customerDao.find(connection)) {
            System.out.println(showCustomers.getId() + " | " + showCustomers.getName() + " | " + showCustomers.getNipNumber());
        }
    }

    public void add(Connection connection) throws SQLException {
        System.out.println("Enter the name of customer :");
        String name = scanner.nextLine();
        System.out.println("Enter the nip number of customer :");
        String nipNumber = scanner.nextLine();

        customerDao.add(new Customer(name, nipNumber), connection);
    }

    public void edit(Connection connection) throws SQLException {
        System.out.println("Enter the name :");
        String name = scanner.nextLine();
        System.out.println("Enter the nip number :");
        String nipNumber = scanner.nextLine();
        System.out.println("Enter id number of customer who you want to edit :");
        int id = scanner.nextInt();

        customerDao.edit(new Customer(id, name, nipNumber), connection);
    }

    public void delete(Connection connection) throws SQLException {
        System.out.println("Enter the customer id number to be removed from the database:");
        int id = scanner.nextInt();

        customerDao.delete(new Customer(id, null, null), connection);
    }
}
