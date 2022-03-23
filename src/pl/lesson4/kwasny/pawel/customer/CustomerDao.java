package pl.lesson4.kwasny.pawel.customer;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDao {
    private PreparedStatement preparedStatement;
    private String sql;
    private Connection connection;

    public CustomerDao(Connection connection) {
        this.connection = connection;
    }

    public List<Customer> find() throws SQLException {

        Statement selectStmt = connection.createStatement();
        ResultSet resultSet = selectStmt.executeQuery("select * from customer;");
        List<Customer> customers = new LinkedList<>();
        while (resultSet.next()) {
            customers.add(new Customer(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("nip_number")));
        }
        resultSet.close();
        selectStmt.close();
        return customers;
    }

    public void add(Customer customer) throws SQLException {
        sql = "insert into customer(name, nip_number) values (?, ?);";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getNipNumber());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void edit(Customer customer) throws SQLException {
        sql = "update customer set name = ?, nip_number = ? where id = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getNipNumber());
        preparedStatement.setInt(3, customer.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void delete(Customer customer) throws SQLException {
        sql = "delete from customer where id = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, customer.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
