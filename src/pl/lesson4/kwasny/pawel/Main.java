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

import com.mysql.cj.exceptions.MysqlErrorNumbers;
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
            } catch (Exception exception) {
                System.out.println("It's not a number! Enter correct number :");
                helpPoint = true;
            }
            if (choose < 0 || choose > 4 && choose != 9) {
                System.out.println("Enter a number from 1 - 4 or press 9 to exit the program. :\n");
            }
            scanner.nextLine();
        } while (helpPoint == true);


        if (choose == 1) {
//            helpPoint = false;
            do {
                helpPoint = false;
                try {
                    choose = 0;
                } catch (Exception exception) {
                    System.out.println("It's not a number! Enter correct number :");
                    helpPoint = true;
                }
            } while (helpPoint == true);

            do {
                try {
                    ProductService productService = new ProductService(connection);

                    if (choose == 1) {
                        userIO.showProduct(productService.find());
                    } else if (choose == 2) {
                        boolean error;
                        do {
                            error = false;
                            try {
                                productService.add(userIO.prepareProductToAdd(productService));
                            } catch (SQLException sqlException) {
                                if (sqlException.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                                    System.out.println("This EAN code or product name is already in the database, check " +
                                            "the data and enter it again:");
                                    error = true;
                                } else {
                                    System.out.println("This EAN code has already been added to the database, re-enter another EAN code.");
                                    error = true;
                                }
                            }
                        } while (error == true);

                    } else if (choose == 3) {
                        userIO.showProduct(productService.find());
                        productService.edit(userIO.prepareProductToEdit());
                    } else if (choose == 4) {
                        userIO.showProduct(productService.find());
                        productService.delete(userIO.deleteProduct());
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
                    } catch (Exception exception) {
                        System.out.println("This is not a number, please enter the number correctly!");
                    }
                } catch (Exception exception) {
                    System.out.println("The EAN code or product name is already in the database, re-enter the data:\n");
                }
                if (choose < 0 || choose > 4 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
                scanner.nextLine();
            } while (choose != 9);
        }


        if (choose == 2) {
            choose = 0;
            do {
                try {
                    CustomerService customerService = new CustomerService(connection);
                    if (choose == 1) {
                        userIO.showCustomers(customerService.find());
                        userIO.checkingEmptyItems(customerService.find());
                    } else if (choose == 2) {
                        customerService.add(userIO.preparaCustomerToAdd());
                    } else if (choose == 3) {
                        userIO.showCustomers(customerService.find());
                        customerService.edit(userIO.prepareCustomerToEdit(customerService));
                    } else if (choose == 4) {
                        userIO.showCustomers(customerService.find());
                        customerService.delete(userIO.deleteCustomer());
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }
                    System.out.println("What you want to do :\n1. Show customers \n2. Add customer \n" +
                            "3. Edit customer \n4. Delete customer \n5. Back to menu \n9. Close program");
                    choose = scanner.nextInt();
                } catch (Exception exception) {
                    System.out.println("This is not a correct number, please try again:\n");
                }
                if (choose < 0 || choose > 4 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
//                scanner.nextLine();
            } while (choose != 9);
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
                        invoiceService.add(userIO.addInvoice());
                    } else if (choose == 3) {
                        userIO.showInvoices(invoiceService.find());
                        invoiceService.edit(userIO.prepareInvoiceToEdit());
                    } else if (choose == 4) {
                        userIO.showInvoices(invoiceService.find());
                        invoiceService.delete(userIO.deleteInvoice());
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }

                    System.out.println("What you want to do :\n1. Show invoices \n2. Add invoice \n" +
                            "3. Edit invoice \n4. Delete invoice \n5. Back to menu \n9. Close program");
                    choose = scanner.nextInt();
                } catch (Exception exception) {
                    System.out.println("This is not a correct number, please try again:\n");
                }
                if (choose < 0 || choose > 4 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
                scanner.nextLine();
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
                        invoiceItemService.add(userIO.prepareInvoiceItemToAdd());
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
                } catch (Exception exception) {
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