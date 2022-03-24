package pl.lesson4.kwasny.pawel.product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductService {
    private ProductDao productDao;

    public ProductService(Connection connection) {
        productDao = new ProductDao(connection);
    }

    public void show() throws SQLException {
        for (Product showProduct : productDao.find()) {
            System.out.println(showProduct.getId() + " | " + showProduct.getEanCode() + " | " + showProduct.getName() +
                    " | " + showProduct.getPriceNet() + " | " + showProduct.getTaxPercent());
        }
    }

    public void add(Product product) throws SQLException {
        productDao.add(product);
    }

    public void edit(Product product) throws SQLException {
        productDao.edit(product);
    }

    public void delete(Product product) throws SQLException {

        productDao.delete(product);
    }
}
