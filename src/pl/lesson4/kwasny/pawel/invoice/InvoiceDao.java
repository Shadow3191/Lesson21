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
        while (resultSet.next()) {
            invoices.add(new Invoice(resultSet.getInt("id"),
                    resultSet.getString("number"),
                    resultSet.getInt("customer_id"),
                    resultSet.getBigDecimal("price_net_sum"),
                    resultSet.getBigDecimal("price_gross_sum")));
        }
        resultSet.close();
        selectStmt.close();
        return invoices;
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

    public void edit(Invoice invoice) throws SQLException {
        sql = "update invoice set number = ?, customer_id = ?, price_net_sum = ?,price_gross_sum = ? where id = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, invoice.getNumber());
        preparedStatement.setInt(2, invoice.getCustomerID());
        preparedStatement.setBigDecimal(3, invoice.getPriceNetSum());
        preparedStatement.setBigDecimal(4, invoice.getPriceGossSum());
        preparedStatement.setInt(5, invoice.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void delete(Invoice invoice) throws SQLException {
        sql = "delete from invoice where id = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, invoice.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

}
