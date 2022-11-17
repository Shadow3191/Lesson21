package pl.lesson4.kwasny.pawel.product;

import pl.lesson4.kwasny.pawel.customer.Customer;

import java.sql.Connection;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(Connection connection) {
        productDao = new ProductDao(connection);
    }

    public List<Product> find() {
        return productDao.find();
    }

    public Product get(Integer id) {
        return productDao.get(id);
    }

    public void add(Product product) {
        productDao.add(product);
    }

    public void edit(Product product) {
        productDao.edit(product);
    }

    public void delete(Product product) {
        productDao.delete(product);
    }
}
