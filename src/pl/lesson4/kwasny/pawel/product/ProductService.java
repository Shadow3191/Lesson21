package pl.lesson4.kwasny.pawel.product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(Connection connection) {
        productDao = new ProductDao(connection);
    }

    public List<Product> find() throws SQLException {

        return productDao.find();
    }

    public void add(Product product) throws SQLException {
        try {
            productDao.add(product);
        } catch (SQLException sqlException){
            throw sqlException;
        }
    }

    public void edit(Product product) {
        productDao.edit(product);
    }

    public void delete(Product product) {
        productDao.delete(product);
    }
}
