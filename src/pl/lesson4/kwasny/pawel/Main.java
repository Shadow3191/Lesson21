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
        int choose = 0;
        Connection connection;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/invoices?user=root&password=Patryk");

            do {
                System.out.println("Select category :\n 1) Product \n 2) Customer \n 3) Invoice \n 4) Invoice Item \n 9) Close program");
                runOperation(scanner, connection);
            } while (choose != 9);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private static void runOperation(Scanner scanner, Connection connection) throws SQLException {
        int choose = 0;
        choose = scanner.nextInt();
        UserIO userIO = new UserIO();

        if (choose == 1) {
            choose = 0;
            do {
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
                    productService.delete(userIO.deleteProduct());
                }
                if (choose == 5) {
                    break;
                }
                if (choose == 9) {
                    System.out.println("You close program, see you next time !");
                    System.exit(0);
                }

                System.out.println("What you whant to do :\n1. Show products \n2. Add product \n3. Edit product \n4. Delete product \n5. Back to menu \n9. Close program");
                choose = scanner.nextInt();
            } while (choose != 9);
        }

        if (choose == 2) {
            choose = 0;

            do {
                CustomerService customerService = new CustomerService(connection);
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
                }
                if (choose == 5) {
                    break;
                }
                if (choose == 9) {
                    System.out.println("You close program, see you next time !");
                    System.exit(0);
                }
                System.out.println("What you want to do :\n1. Show customers \n2. Add customer \n3. Edit customer \n4. Delete customer \n5. Back to menu \n9. Close program");
                choose = scanner.nextInt();
            } while (choose != 9);
        }

        if (choose == 3) {
            System.out.println("What you want to do :\n1. Show invoices \n2. Add invoice \n3. Edit invoice \n4. Delete invoice \n5. Back to menu \n9. Close program");
            choose = scanner.nextInt();
            do {
                InvoiceService invoiceService = new InvoiceService(connection);
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
                    invoiceService.delete(userIO.deleteInvoice());
                }
                if (choose == 5) {
                    break;
                }
                if (choose == 9) {
                    System.out.println("You close program, see you next time !");
                    System.exit(0);
                }
                System.out.println("What you want to do :\n1. Show invoices \n2. Add invoice \n3. Edit invoice \n4. Delete invoice \n5. Back to menu \n9. Close program");
                choose = scanner.nextInt();
            } while (choose != 9);
        }
        if (choose == 4) {
            System.out.println("What you want to do :\n1. Show invoice item \n2. Add invoice item \n3. Edit invoice item \n4. Delete invoice item \n5. Back to menu \n9. Close program");
            choose = scanner.nextInt();
            InvoiceItemService invoiceItemService = new InvoiceItemService(connection);
            do {
                if (choose == 1) {
                    userIO.showInvoiceItem(invoiceItemService.find());
                }
                if (choose == 2) {
                    invoiceItemService.add(userIO.addInvoiceItem());
                }
                if (choose == 3) {
                    invoiceItemService.edit(userIO.editInvoiceItem());
                }
                if (choose == 4) {
                    invoiceItemService.delete(userIO.deleteInvoiceItem());
                }
                if (choose == 5) {
                    break;
                }
                if (choose == 9) {
                    System.out.println("You close program, see you next time !");
                    System.exit(0);
                }
                System.out.println("What you want to do :\n1. Show invoice item \n2. Add invoice item \n3. Edit invoice item \n4. Delete invoice item \n5. Back to menu \n9. Close program");
                choose = scanner.nextInt();
            } while (choose != 9);
        }
    }
}