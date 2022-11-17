package pl.lesson4.kwasny.pawel.product;

import pl.lesson4.kwasny.pawel.DatabaseException;

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

    public List<Product> find() {
        ResultSet resultSet = null;
        Statement selectStmt = null;
        List<Product> products = new LinkedList<>();
        try {
            selectStmt = connection.createStatement();
            resultSet = selectStmt.executeQuery("Select * from product");
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("ean_code"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price_net"),
                        resultSet.getBigDecimal("tax_percent")));
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
        return products;
    }

    public Product get(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select * from product where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Product(resultSet.getInt("id"),
                        resultSet.getString("ean_code"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price_net"),
                        resultSet.getBigDecimal("tax_percent"));
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage(), sqlException);
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();// TODO czy też ma być zamknięte ?
            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage(), sqlException);
            }
        }
    }


    public void add(Product product) {
        sql = "insert into product(ean_code, name, price_net, tax_percent) values (?, ?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getEanCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setBigDecimal(3, product.getNetPrice());
            preparedStatement.setBigDecimal(4, product.getTaxPercent());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("The EAN code or product name is already in the database.\n");
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

    public void edit(Product product) {
        sql = "update product set ean_code = ?, name = ?, price_net = ?, tax_percent = ? where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getEanCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setBigDecimal(3, product.getNetPrice());
            preparedStatement.setBigDecimal(4, product.getTaxPercent());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("The EAN code or product name is already in the database.\n");
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

    public void delete(Product product) {
        deleteProductIdFromInvoiceItem(product);
        sql = "delete from product where id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, product.getId());
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

    public void deleteProductIdFromInvoiceItem(Product product) {
        sql = "delete from invoice_item where product_id = ?;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, product.getId());
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
