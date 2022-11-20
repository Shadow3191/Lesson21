package pl.lesson4.kwasny.pawel.invoiceItem;

import pl.lesson4.kwasny.pawel.DatabaseException;
import pl.lesson4.kwasny.pawel.product.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class InvoiceItemDao {
    private PreparedStatement preparedStatement;
    private String sql;
    private Connection connection;

    public InvoiceItemDao(Connection connection) {
        this.connection = connection;
    }

    public List<InvoiceItem> find() {
        ResultSet resultSet = null;
        Statement selectStmt = null;
        List<InvoiceItem> invoiceItems;
        try {
            selectStmt = connection.createStatement();
            resultSet = selectStmt.executeQuery("Select * from invoice_item");
            invoiceItems = new LinkedList<>();
            while (resultSet.next()) {
                invoiceItems.add(new InvoiceItem(
                        resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("invoice_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("product_name"),
                        resultSet.getBigDecimal("price_net"),
                        resultSet.getBigDecimal("tax_percent"),
                        resultSet.getBigDecimal("price_gross")));
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
        return invoiceItems;
    }

    public InvoiceItem get(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from invoice_item where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new InvoiceItem(resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("invoice_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("product_name"),
                        resultSet.getBigDecimal("price_net"),
                        resultSet.getBigDecimal("tax_percent"),
                        resultSet.getBigDecimal("price_gross"));
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }

    public void add(InvoiceItem invoiceItem) {
        sql = "insert into invoice_item(product_id, invoice_id, quantity, product_name, price_net, " +
                "tax_percent, price_gross) values (?,?,?,?,?,?,?);";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoiceItem.getProductId());
            preparedStatement.setInt(2, invoiceItem.getInvoiceId());
            preparedStatement.setInt(3, invoiceItem.getQuantity());
            preparedStatement.setString(4, invoiceItem.getProductName());
            preparedStatement.setBigDecimal(5, invoiceItem.getNetPrice());
            preparedStatement.setBigDecimal(6, invoiceItem.getTaxPercent());
            preparedStatement.setBigDecimal(7, invoiceItem.getGrossPrice());
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

    public void edit(InvoiceItem invoiceItem) {
        sql = "update invoice_item set product_id = ?, invoice_id = ?, quantity = ?, product_name = ?, price_net = ?, " +
                "tax_percent = ?, price_gross = ? where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoiceItem.getProductId());
            preparedStatement.setInt(2, invoiceItem.getInvoiceId());
            preparedStatement.setInt(3, invoiceItem.getQuantity());
            preparedStatement.setString(4, invoiceItem.getProductName());
            preparedStatement.setBigDecimal(5, invoiceItem.getNetPrice());
            preparedStatement.setBigDecimal(6, invoiceItem.getTaxPercent());
            preparedStatement.setBigDecimal(7, invoiceItem.getGrossPrice());
            preparedStatement.setInt(8, invoiceItem.getId());
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

    public void delete(InvoiceItem invoiceItem) {
        sql = "delete from invoice_item where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoiceItem.getId());
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
// create method whos delete invoiceItem before delete invoice

// HTML, JAVASCRIPT (biblioteka JQUERY), hybernate, springboot, thymeleaf <- to bedzie wykorzystywane w projekcie