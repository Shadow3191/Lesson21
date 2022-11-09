package pl.lesson4.kwasny.pawel.customer;

import pl.lesson4.kwasny.pawel.DatabaseException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDao {
    private Connection connection;

    public CustomerDao(Connection connection) {
        this.connection = connection;
    }

    public List<Customer> find() throws SQLException {
        Statement selectStmt = connection.createStatement();
        ResultSet resultSet = selectStmt.executeQuery("select * from customer;");
        List<Customer> customers = new LinkedList<>();
        try {
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("nip_number")));
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                resultSet.close();
                selectStmt.close();
                return customers;
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }

    //
    public Customer get(Integer id) {
//        String sql;
        PreparedStatement preparedStatement = null;
        String sql = "select * from customer where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Customer(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("nip_number"));
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }

    public void add(Customer customer) {
        String sql = "insert into customer(name, nip_number) values (?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getNipNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }

    public void edit(Customer customer) {
        String sql = "update customer set name = ?, nip_number = ? where id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getNipNumber());
            preparedStatement.setInt(3, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }

    public void delete(Customer customer) {
        deleteInvoicesContainingTheCustomerIdToDeleted(customer);
        String sql = "delete from customer where id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }

    public void deleteInvoicesContainingTheCustomerIdToDeleted(Customer customer) {
        String sql = "delete from invoice where customer_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }
}