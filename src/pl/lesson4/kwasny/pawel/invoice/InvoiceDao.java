package pl.lesson4.kwasny.pawel.invoice;

import pl.lesson4.kwasny.pawel.DatabaseException;
import pl.lesson4.kwasny.pawel.customer.Customer;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class InvoiceDao {
    private PreparedStatement preparedStatement;
    private String sql;
    private Connection connection;

    public InvoiceDao(Connection connection) {
        this.connection = connection;
    }

    public List<Invoice> find()  {
        Statement selectStmt = null;
        ResultSet resultSet = null;
        List<Invoice> invoices = new LinkedList<>();
        try {
            selectStmt = connection.createStatement();
            resultSet = selectStmt.executeQuery("select * from invoice;");
            while (resultSet.next()) {
                invoices.add(new Invoice(resultSet.getInt("id"),
                        resultSet.getString("number"),
                        resultSet.getInt("customer_id"),
                        resultSet.getBigDecimal("price_net_sum"),
                        resultSet.getBigDecimal("price_gross_sum")));
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
        return invoices;
    }

    public Invoice get(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from invoice where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Invoice(resultSet.getInt("id"),
                        resultSet.getString("number"),
                        resultSet.getInt("customer_id"),
                        resultSet.getBigDecimal("price_net_sum"),
                        resultSet.getBigDecimal("price_gross_sum"));
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

    public void add(Invoice invoice) {
        sql = "insert into invoice(number, customer_id, price_net_sum,price_gross_sum) values (?, ?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, invoice.getNumber());
            preparedStatement.setInt(2, invoice.getCustomerID());
            preparedStatement.setBigDecimal(3, invoice.getPriceNetSum());
            preparedStatement.setBigDecimal(4, invoice.getPriceGrossSum());
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

    public void edit(Invoice invoice) {
        sql = "update invoice set number = ?, customer_id = ?, price_net_sum = ?,price_gross_sum = ? where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, invoice.getNumber());
            preparedStatement.setInt(2, invoice.getCustomerID());
            preparedStatement.setBigDecimal(3, invoice.getPriceNetSum());
            preparedStatement.setBigDecimal(4, invoice.getPriceGrossSum());
            preparedStatement.setInt(5, invoice.getId());
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

    public void delete(Invoice invoice) {
        deleteByInvoiceId(invoice);
        sql = "delete from invoice where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoice.getId());
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

    // poniższ metoda usuwa w powyzszej odpowiednio numer invoice item invoice id abym mogl usunac invoice
    public void deleteByInvoiceId(Invoice invoice) {
        sql = "delete from invoice_item where invoice_id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoice.getId());
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