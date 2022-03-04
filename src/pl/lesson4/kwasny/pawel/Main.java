package pl.lesson4.kwasny.pawel;
/*
Program do rejestrowania FV obejmujacy 3 rzeczy :

1) Produkty
- kod EAN
- nazwa produtu
- cena produktu

2) FV :
- numer FV
- suma netto
- suma butto

3) Pozycje FV
- produkt - tu zrobic powiazanie do jedynki i przepisywac nazwe produktu jakby sie zmienila w katalogu
- cena netto
- % podatku
- cena brutto

4) Kontrahent
- Nazwa
- NIP
 */
//TODO Zrobione wszystko w customer i product teraz robić dalej

import pl.lesson4.kwasny.pawel.customer.CustomerService;
import pl.lesson4.kwasny.pawel.invoice.Invoice;
import pl.lesson4.kwasny.pawel.invoice.InvoiceService;
import pl.lesson4.kwasny.pawel.product.ProductService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver loaded");
        } catch (Exception ex) {
            System.out.println("Not loaded : " + ex.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int choose;
        Connection connection;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/invoices?user=patryk&password=patryk");


            System.out.println("Selec category :\n 1) Product \n 2) Customer \n 3) Invoice \n 4) Invoice Item");
            choose = scanner.nextInt();
            if (choose == 1) {
                System.out.println("1. Show products \n2. Add product \n3. Edit product \n4. Delete product");
                choose = scanner.nextInt();
                ProductService products = new ProductService();
                if (choose == 1) {
                    products.show(connection);
                }
                if (choose == 2) {
                    products.add(connection);
                }
                if (choose == 3) {
                    products.edit(connection);
                }
                if (choose == 4) {
                    products.delete(connection);
                }
            }
            if (choose == 2) {
                System.out.println("1. Show customers \n2. Add customer \n3. Edit customer \n4. Delete customer");
                choose = scanner.nextInt();
                CustomerService customerService = new CustomerService();
                if (choose == 1) {
                    customerService.show(connection);
                }
                if (choose == 2) {
                    customerService.add(connection);
                }
                if (choose == 3) {
                    customerService.edit(connection);
                }
                if (choose == 4) {
                    customerService.delete(connection);
                }
            }
            if (choose == 3) {
                System.out.println("1. Show invoices \n2. Add invoice \n3. Edit incoice \n4. Delete invoice");
                choose = scanner.nextInt();
                InvoiceService invoiceService = new InvoiceService(connection);


                if (choose == 1) {
                    invoiceService.show();
                }
                if (choose == 2) {
                    System.out.println("Enter number :");
                    scanner.nextLine();
                    String number = scanner.nextLine();
                    System.out.println("Enter customer id :");
                    int customerId = scanner.nextInt();


                    invoiceService.add(new Invoice(number, customerId, BigDecimal.ZERO, BigDecimal.ZERO));
                }
                if (choose == 3) {
                    invoiceService.edit();
                }
                if (choose == 4) {
                    invoiceService.delete();
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
