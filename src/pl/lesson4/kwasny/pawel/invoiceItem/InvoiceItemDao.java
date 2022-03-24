package pl.lesson4.kwasny.pawel.invoiceItem;

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

    public List<InvoiceItem> find() throws SQLException {
        Statement selectStmt = connection.createStatement();
        ResultSet resultSet = selectStmt.executeQuery("Select * from invoice_item");
        List<InvoiceItem> invoiceItems = new LinkedList<>();
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
        resultSet.close();
        selectStmt.close();
        return invoiceItems;
    }

    public void add(InvoiceItem invoiceItem) throws SQLException {
        sql = "insert into invoice_item(product_id, invoice_id, quantity, product_name, price_net, " +
                "tax_percent, price_gross) values (?,?,?,?,?,?,?);";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, invoiceItem.getProductId());
        preparedStatement.setInt(2, invoiceItem.getInvoiceId());
        preparedStatement.setInt(3, invoiceItem.getQuantity());
        preparedStatement.setString(4, invoiceItem.getProductName());
        preparedStatement.setBigDecimal(5, invoiceItem.getNetPrice());
        preparedStatement.setBigDecimal(6, invoiceItem.getTaxPercent());
        preparedStatement.setBigDecimal(7, invoiceItem.getGrossPrice());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}