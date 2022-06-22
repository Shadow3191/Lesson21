package pl.lesson4.kwasny.pawel.invoice;

import pl.lesson4.kwasny.pawel.DatabaseException;

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

    public List<Invoice> find() throws SQLException {
        Statement selectStmt = connection.createStatement();
        ResultSet resultSet = selectStmt.executeQuery("select * from invoice;");
        List<Invoice> invoices = new LinkedList<>();
        try {
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
                return invoices;
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
            preparedStatement.setBigDecimal(4, invoice.getPriceGossSum());
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
            preparedStatement.setBigDecimal(4, invoice.getPriceGossSum());
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

    // TODO poniższ metoda usuwa w powyzszej odpowiednio numer invoice item invoice id abym mogl usunac invoice
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

// TODO w invoice trzeba wyswietlic liste uzytkownikow zeby wiedzial jakie id ma customer zeby wiedzial co ma dodac