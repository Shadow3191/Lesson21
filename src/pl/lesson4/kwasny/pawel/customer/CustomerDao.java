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

    public List<Customer> find() {
        Statement selectStmt = null;
        ResultSet resultSet = null;
        List<Customer> customers = new LinkedList<>();
        try {
            selectStmt = connection.createStatement();
            resultSet = selectStmt.executeQuery("select * from customer;");
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
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
        return customers;
    }

    //
    public Customer get(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from customer where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
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
                resultSet.close(); // TODO czy też ma być zamknięty ?
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }

//    public Customer getName(String name) {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        String sql = "select * from customer where name = ?;";
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, name);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return new Customer(resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("nip_number"));
//            } else {
//                return null;
//            }
//        } catch (SQLException sqlException) {
//            throw new DatabaseException(sqlException.getMessage(), sqlException);
//        } finally {
//            try {
//                preparedStatement.close();
//                resultSet.close(); // TODO czy też ma być zamknięty ?
//            } catch (SQLException sqlException) {
//                throw new DatabaseException(sqlException.getMessage(), sqlException);
//            }
//        }
//    }
//
//    public Customer getNip(String nipNumber) {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        String sql = "select * from customer where nip_number = ?;";
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, nipNumber);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return new Customer(resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("nip_number"));
//            } else {
//                return null;
//            }
//        } catch (SQLException sqlException) {
//            throw new DatabaseException(sqlException.getMessage(), sqlException);
//        } finally {
//            try {
//                preparedStatement.close();
//                resultSet.close(); // TODO czy też ma być zamknięty ?
//            } catch (SQLException sqlException) {
//                throw new DatabaseException(sqlException.getMessage(), sqlException);
//            }
//        }
//    }

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