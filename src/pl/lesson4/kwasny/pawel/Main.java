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

import pl.lesson4.kwasny.pawel.customer.Customer;
import pl.lesson4.kwasny.pawel.customer.CustomerService;
import pl.lesson4.kwasny.pawel.invoice.Invoice;
import pl.lesson4.kwasny.pawel.invoice.InvoiceService;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItem;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItemService;
import pl.lesson4.kwasny.pawel.product.Product;
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
                ProductService products = new ProductService(connection);
                if (choose == 1) {
                    products.show();
                }
                if (choose == 2) {
                    System.out.println("Enter ean code :");
                    scanner.nextLine();
                    String ean = scanner.nextLine();
                    System.out.println("Enter name of product :");
                    String name = scanner.nextLine();
                    System.out.println("Enter net price :");
                    BigDecimal netPrice = scanner.nextBigDecimal();
                    System.out.println("Enter tax percent :");
                    BigDecimal taxPercent = scanner.nextBigDecimal();
                    products.add(new Product(ean, name, netPrice, taxPercent));
                }
                if (choose == 3) {
                    System.out.println("Enter the id number you want to edit ");
                    int id = scanner.nextInt();
                    System.out.println("Enter ean code :");
                    scanner.nextLine();
                    String ean = scanner.nextLine();
                    System.out.println("Enter name of product :");
                    String name = scanner.nextLine();
                    System.out.println("Enter net price :");
                    BigDecimal netPrice = scanner.nextBigDecimal();
                    System.out.println("Enter tax percent :");
                    BigDecimal taxPercent = scanner.nextBigDecimal();
                    products.edit(new Product(id, ean, name, netPrice, taxPercent));
                }
                if (choose == 4) {
                    System.out.println("Enter the product id number to be removed from the database: ");
                    int id = scanner.nextInt();
                    products.delete(new Product(id, null, null, null, null));
                }
                choose = 0;
            }
            if (choose == 2) {
                System.out.println("1. Show customers \n2. Add customer \n3. Edit customer \n4. Delete customer");
                choose = scanner.nextInt();
                CustomerService customerService = new CustomerService(connection);
                if (choose == 1) {
                    customerService.show();
                }
                if (choose == 2) {
                    System.out.println("Enter the name of customer :");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Enter the nip number of customer :");
                    String nipNumber = scanner.nextLine();
                    customerService.add(new Customer(name, nipNumber));
                }
                if (choose == 3) {
                    System.out.println("Enter name :");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Enter nip number :");
                    String nipNumber = scanner.nextLine();
                    System.out.println("Enter customer id :");
                    int id = scanner.nextInt();
                    customerService.edit(new Customer(id, name, nipNumber));
                    choose = 0;
                }
                if (choose == 4) {
                    System.out.println("Enter the customer id number to be removed from the database:");
                    int id = scanner.nextInt();
                    customerService.delete(new Customer(id, null, null));
                }
            }
            if (choose == 3) {
                System.out.println("1. Show invoices \n2. Add invoice \n3. Edit invoice \n4. Delete invoice");
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
                    System.out.println("Enter id number of invoice who you want to edit :");
                    int id = scanner.nextInt();
                    System.out.println("Enter the invoice number :");
                    scanner.nextLine();
                    String number = scanner.nextLine();
                    System.out.println("Enter customer id :");
                    int customerId = scanner.nextInt();
                    invoiceService.edit(new Invoice(id, number, customerId, BigDecimal.ZERO, BigDecimal.ZERO));
                }
                if (choose == 4) {
                    System.out.println("Enter the invoice id number to be removed from the database:");
                    int id = scanner.nextInt();
                    invoiceService.delete(new Invoice(id, null, null, null, null));
                }
            }
            if (choose == 4) {
                System.out.println("1. Show invoice items \n2. Add invoice \n3. Edit invoice \n4. Delete invoice");
                choose = scanner.nextInt();
                InvoiceItemService invoiceItemService = new InvoiceItemService(connection);

                if (choose == 1) {
                    invoiceItemService.show();
                }

                if (choose == 2) {
                    System.out.println("Enter the product id :");
                    int prductId = scanner.nextInt();
                    System.out.println("Enter the invoide id :");
                    int invoiceId = scanner.nextInt();
                    System.out.println("Enter the product quantity :");
                    int quantity = scanner.nextInt();
                    System.out.println("Enter the product name :");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.println("Enter netto price :");
                    BigDecimal netPrice = scanner.nextBigDecimal();
                    System.out.println("Enter the tax percent :");
                    BigDecimal taxPercent = scanner.nextBigDecimal();
                    BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice);
//               TODO nie mnoży przez ilosc dodanych produktow DOKONCZYC


                    invoiceItemService.add(new InvoiceItem(prductId, invoiceId, quantity, name, netPrice, taxPercent, grossPrice));
                }
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    // W SERWISIE NIE MOZE BYC SOUTA ANI SCANEROW !
}
