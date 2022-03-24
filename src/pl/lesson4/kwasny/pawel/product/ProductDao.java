package pl.lesson4.kwasny.pawel.product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductDao {
    private PreparedStatement preparedStatement;
    private String sql;
    private Connection connection;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    public List<Product> find() throws SQLException {
        Statement selectStmt = connection.createStatement();
        ResultSet resultSet = selectStmt.executeQuery("Select * from product");
        List<Product> products = new LinkedList<>();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("ean_code"),
                    resultSet.getString("name"),
                    resultSet.getBigDecimal("price_net"),
                    resultSet.getBigDecimal("tax_percent")));
        }
        resultSet.close();
        selectStmt.close();
        return products;
    }

    public void add(Product product) throws SQLException {
        sql = "insert into product(ean_code, name, price_net, tax_percent) values (?, ?, ?, ?);";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, product.getEanCode());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setBigDecimal(3, product.getPriceNet());
        preparedStatement.setBigDecimal(4, product.getTaxPercent());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void edit(Product product) throws SQLException {
        sql = "update product set ean_code = ?, name = ?, price_net = ?, tax_percent = ? where id = ?;";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, product.getEanCode());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setBigDecimal(3, product.getPriceNet());
        preparedStatement.setBigDecimal(4, product.getTaxPercent());
        preparedStatement.setInt(5, product.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void delete(Product product) throws SQLException {
        sql = "delete from product where id = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, product.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


}