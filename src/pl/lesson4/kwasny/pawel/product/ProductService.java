package pl.lesson4.kwasny.pawel.product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductService {
    private ProductDao productDao = new ProductDao();
    private Scanner scanner = new Scanner(System.in);

    public void show(Connection connection) throws SQLException {
        for (Product showProduct : productDao.find(connection)) {
            System.out.println(showProduct.getId() + " | " + showProduct.getEanCode() + " | " + showProduct.getName() + " | " + showProduct.getPriceNet() + " | "
                    + showProduct.getTaxPercent());
        }
    }

    public void add(Connection connection) throws SQLException {
        System.out.println("Enter ean code :");
        String ean = scanner. nextLine();
        System.out.println("Enter name of product :");
        String name = scanner.nextLine();
        System.out.println("Enter net price :");
        BigDecimal netPrice = scanner.nextBigDecimal();
        System.out.println("Enter tax percent :");
        BigDecimal taxPercent = scanner.nextBigDecimal();

        productDao.add(new Product(ean,name,netPrice,taxPercent),connection);
    }

    public void edit(Connection connection) throws SQLException {
        System.out.println("Enter ean code :");
        String ean = scanner. nextLine();
        System.out.println("Enter name of product :");
        String name = scanner.nextLine();
        System.out.println("Enter net price :");
        BigDecimal netPrice = scanner.nextBigDecimal();
        System.out.println("Enter tax percent :");
        BigDecimal taxPercent = scanner.nextBigDecimal();
        System.out.println("Enter the id number you want to edit ");
        int id = scanner.nextInt();

        productDao.edit(new Product(id, ean, name, netPrice,taxPercent), connection);
    }

    public void delete(Connection connection) throws SQLException {
        System.out.println("Enter the product id number to be removed from the database: ");
        int id = scanner.nextInt();
        productDao.delete(new Product(id,null,null,null,null),connection);
    }
}
