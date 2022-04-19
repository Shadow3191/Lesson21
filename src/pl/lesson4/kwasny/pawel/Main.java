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

import pl.lesson4.kwasny.pawel.customer.CustomerService;
import pl.lesson4.kwasny.pawel.invoice.InvoiceService;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItemService;
import pl.lesson4.kwasny.pawel.product.ProductService;

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


            System.out.println("Select category :\n 1) Product \n 2) Customer \n 3) Invoice \n 4) Invoice Item");
            choose = scanner.nextInt();
            UserIO userIO = new UserIO();

            if (choose == 1) {
                System.out.println("1. Show products \n2. Add product \n3. Edit product \n4. Delete product");
                choose = scanner.nextInt();
                ProductService productService = new ProductService(connection);

                if (choose == 1) {
                    userIO.showProduct(productService.find());
                }
                if (choose == 2) {
                    productService.add(userIO.addProduct());
                }
                if (choose == 3) {
                    productService.edit(userIO.editProduct());
                }
                if (choose == 4) {
//                    System.out.println("Enter the product id number to be removed from the database: ");
//                    int id = scanner.nextInt();
//                    products.delete(new Product(id, null, null, null, null));
                    productService.delete(userIO.deleteProduct());
                }
                choose = 0;
            }
            if (choose == 2) {
                System.out.println("1. Show customers \n2. Add customer \n3. Edit customer \n4. Delete customer");
                choose = scanner.nextInt();

                CustomerService customerService = new CustomerService(connection);
//                CustomerDao customerDao = new CustomerDao(connection);
                if (choose == 1) {
                    userIO.showCustomers(customerService.find());
                }
                if (choose == 2) {
                    customerService.add(userIO.prepareCustomerToAdd());
                }
                if (choose == 3) {
                    customerService.edit(userIO.editCustomer());
                }
                if (choose == 4) {
                    customerService.delete(userIO.deleteCustomer());
                    // TODO powiazanie w bazie jest nie tak
                }
                choose = 0;
            }
            if (choose == 3) {
                System.out.println("1. Show invoices \n2. Add invoice \n3. Edit invoice \n4. Delete invoice");
                choose = scanner.nextInt();
                InvoiceService invoiceService = new InvoiceService(connection);
//                InvoiceItemService invoiceItemService = new InvoiceItemService(connection);
//                UserIO userIO = new UserIO();

                if (choose == 1) {
                    userIO.showInvoices(invoiceService.find());
                }
                if (choose == 2) {
                    invoiceService.add(userIO.addInvoice());
                }
                if (choose == 3) {
                    invoiceService.edit(userIO.editInvoice());
                }
                if (choose == 4) {
//                    invoiceItemService.delete() tutaj zrobic zeby sie usuwalo we wskazanym miejscu wszystko z invoiceItem a nastepnie w tym nizej
//                    invoiceItemService.
                    invoiceService.delete(userIO.deleteInvoice());
                }
            }
            if (choose == 4) {
                System.out.println("1. Show invoice items \n2. Add invoice \n3. Edit invoice \n4. Delete invoice");
                choose = scanner.nextInt();
                InvoiceItemService invoiceItemService = new InvoiceItemService(connection);

                if (choose == 1) {
                    userIO.showInvoiceItem(invoiceItemService.find());
                }
                if (choose == 2) {
                    invoiceItemService.add(userIO.addInvoiceItem());
                }
                if (choose == 3) {
                    invoiceItemService.edit(userIO.editInvoiceItem());
                }
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
