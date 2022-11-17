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
import pl.lesson4.kwasny.pawel.invoice.InvoiceService;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItemService;
import pl.lesson4.kwasny.pawel.product.Product;
import pl.lesson4.kwasny.pawel.product.ProductService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost/invoices?user=patryk&password=patryk");

            do {
                runOperation(scanner, connection);
            } while (choose != 9);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    private static void runOperation(Scanner scanner, Connection connection) throws SQLException {
        UserIO userIO = new UserIO();
        System.out.println("Select category :\n 1) Product \n 2) Customer \n 3) Invoice \n 4) Invoice Item \n 9) Close program");
        int choose = 0;
        boolean helpPoint = false;
        do {
            helpPoint = false;
            try {
                choose = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("It's not a number! Enter correct number :");
                helpPoint = true;
            }
            if (choose < 0 || choose > 4 && choose != 9) {
                System.out.println("Enter a number from 1 - 4 or press 9 to exit the program. :\n");
            }
            scanner.nextLine();
        } while (helpPoint == true);


        if (choose == 1) { // - start product
            choose = 0;
            do {
                try {
                    ProductService productService = new ProductService(connection);
                    Product product;
                    if (choose == 1) {
                        userIO.showProduct(productService.find());
                    } else if (choose == 2) {
                        productService.add(userIO.prepareProductToAdd());
                    } else if (choose == 3) {
                        userIO.showProduct(productService.find());
                        if (productService.find().isEmpty()) { // TODO jak to ogarnać aby nie sprawdzać 2 razy ?
                            choose = 0;
                            System.out.println("You have no one to edit, add some product first");
                            break;
                        }
                        do {
                            product = productService.get(userIO.getIdToEditProduct());
                            if (product == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (product == null);
                        productService.edit(userIO.prepareProductToEdit(product.getId()));
                    } else if (choose == 4) {
                        userIO.showProduct(productService.find());
                        do {
                            product = productService.get(userIO.getIdToDeleteProduct());
                            if (product == null) {
                                System.out.println("This id don't exist in base.");
                            }
                        } while (product == null);
                        productService.delete(userIO.deleteProduct(product.getId()));
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }
                    System.out.println("What you want to do :\n1. Show products \n2. Add product \n" +
                            "3. Edit product \n4. Delete product \n5. Back to menu \n9. Close program");
                    try {
                        choose = scanner.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("This is not a number, please enter the number correctly!");
                        choose = 0;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("This is not a correct number, please try again:\n");
                }
                if (choose < 0 || choose > 5 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
            } while (choose != 9);
        } // finish product


        if (choose == 2) {
            choose = 0;
            do {
                try {
                    CustomerService customerService = new CustomerService(connection);
                    Customer customer;
//                    Customer customerName;
//                    Customer customerNip;

                    if (choose == 1) { // show customer
                        userIO.showCustomers(customerService.find());

//                        userIO.checkingEmptyItemsInCustomer(customerService.find()); // TODO tak sprawdzać wolne miejsca czy w inny sposób ?! tak jak e metodie get ?
                    } else if (choose == 2) { // add customer
                        customerService.add(userIO.preparedCustomerToAdd());
                    } else if (choose == 3) { // edit customer
                        userIO.showCustomers(customerService.find());
                        if (customerService.find().isEmpty()) { // TODO jak to zrobić zeby 2 raz nie sprawdzało customerSerwive.find() ??
                            choose = 0;
                            System.out.println("You have no one to edit, add some customer first.");
                            break;
                        }
                        do {
                            customer = customerService.get(userIO.getIdToEditCustomer()); // sprawdzenie czy jest juz takie id w bazie
                            if (customer == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (customer == null);

//                        do {
//                            customerName = customerService.getName(userIO.getNameToEditCustomer());
//                            if (customerName != null) {
//                                System.out.println("This customer already exist in database.");
//                            }
//                        } while (customerName != null);

//                        do {
//                            customerNip = customerService.getNip(userIO.getNipToEditCustomer());
//                            if (customerNip != null) {
//                                System.out.println("This nip already exist in database.");
//                            }
//                        } while (customerNip != null);


                        customerService.edit(userIO.prepareCustomerToEdit(customer.getId()));
                    } else if (choose == 4) { // delete customer
                        userIO.showCustomers(customerService.find());
                        do {
                            customer = customerService.get(userIO.getIdToDeleteCustomer());
                            if (customer == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (customer == null);
                        customerService.delete(userIO.deleteCustomer(customer.getId()));
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }
                    System.out.println("What you want to do :\n1. Show customers \n2. Add customer \n" +
                            "3. Edit customer \n4. Delete customer \n5. Back to menu \n9. Close program");
                    choose = scanner.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("This is not a correct number, please try again.");
                }
                if (choose < 0 || choose > 5 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
            } while (choose != 9);
//            scanner.nextInt();
        }

        if (choose == 3) {
            choose = 0;
            do {
                try {
                    InvoiceService invoiceService = new InvoiceService(connection);
                    CustomerService customerService = new CustomerService(connection);
                    if (choose == 1) {
                        userIO.showInvoices(invoiceService.find());
                    } else if (choose == 2) {
                        userIO.showCustomers(customerService.find());
                        invoiceService.add(userIO.prepareInvoiceToAdd());
                    } else if (choose == 3) {
                        userIO.showInvoices(invoiceService.find());
                        invoiceService.edit(userIO.prepareInvoiceToEdit(invoiceService, customerService));
                    } else if (choose == 4) {
                        userIO.showInvoices(invoiceService.find());
//                        invoiceService.delete(userIO.deleteInvoice());
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }

                    System.out.println("What you want to do :\n1. Show invoices \n2. Add invoice \n" +
                            "3. Edit invoice \n4. Delete invoice \n5. Back to menu \n9. Close program");
                    choose = scanner.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("This is not a correct number, please try again:\n");
                }
                if (choose < 0 || choose > 4 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
//                scanner.nextLine();
            } while (choose != 9);
        }
        if (choose == 4) {
            choose = 0;

            InvoiceItemService invoiceItemService = new InvoiceItemService(connection);
            ProductService productService = new ProductService(connection);
            InvoiceService invoiceService = new InvoiceService(connection);
            do {
                try {
                    if (choose == 1) {
                        userIO.showInvoiceItem(invoiceItemService.find());
                    } else if (choose == 2) {
                        userIO.showProduct(productService.find());
                        userIO.showInvoices(invoiceService.find());
                        invoiceItemService.add(userIO.prepareInvoiceItemToAdd(productService));
                    } else if (choose == 3) {
                        userIO.showInvoiceItem(invoiceItemService.find());
                        userIO.showProduct(productService.find());
                        userIO.showInvoices(invoiceService.find());
                        invoiceItemService.edit(userIO.preparedToEditInvoiceItem());
                    } else if (choose == 4) {
                        userIO.showInvoiceItem(invoiceItemService.find());
                        invoiceItemService.delete(userIO.deleteInvoiceItem());
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }
                    System.out.println("What you want to do :\n1. Show invoice item \n2. Add invoice item \n" +
                            "3. Edit invoice item \n4. Delete invoice item \n5. Back to menu \n9. Close program");
                    choose = scanner.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("This is not a correct number, please try again:\n");
                }
                if (choose < 0 || choose > 4 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
                scanner.nextLine();
            } while (choose != 9);
        }
        if (choose == 9) {
            System.out.println("You close program, see you next time !");
            System.exit(0);
        }
    }
}