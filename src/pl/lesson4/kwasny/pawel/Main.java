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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
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

    private static void runOperation(Scanner scanner, Connection connection) {
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
                        System.out.println("Products :");
                        userIO.showProduct(productService.find());
                    } else if (choose == 2) {
                        productService.add(userIO.prepareProductToAdd());
                    } else if (choose == 3) {
                        System.out.println("Products :");
                        List<Product> products = productService.find();
                        userIO.showProduct(products);
                        if (products.isEmpty()) { // TODO jak to ogarnać aby nie sprawdzać 2 razy ? - tak jak wczesniej
                            choose = 0;
                            System.out.println("You have no one to edit, add some product first");
                            break;
                        }
                        product = getProductUntilIsNotValid(userIO,productService);
                        productService.edit(userIO.prepareProductToEdit(product));
                    } else if (choose == 4) {
                        System.out.println("Products :");
                        userIO.showProduct(productService.find());
                        product = getProductUntilIsNotValid(userIO,productService);
                        productService.delete(userIO.deleteProduct(product.getId()));
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }
                    System.out.println("What you want to do :\n1. Show products \n2. Add product \n" +
                            "3. Edit product \n4. Delete product \n5. Back to menu \n9. Close program");
                    boolean correctChose = true;
                    do {
                       try {
                           choose = scanner.nextInt();
                           correctChose = true;
                       } catch (InputMismatchException ex) {
                           scanner.nextLine();
                           System.out.println("This is not a number, please enter the number correctly!");
                           correctChose = false;
//                           choose = 0;
                       }
                   } while (correctChose == false);
                } catch (InputMismatchException ex) { // TODO check that
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
                try { // TODO check that
                    CustomerService customerService = new CustomerService(connection);
                    Customer customer;
//                    Customer customerName;
                    Customer customerNip;

                    if (choose == 1) { // show customer
                        userIO.showCustomers(customerService.find());
                    } else if (choose == 2) { // add customer
                        customerService.add(userIO.preparedCustomerToAdd());
                    } else if (choose == 3) { // edit customer
                        List<Customer> customers = customerService.find();
                        userIO.showCustomers(customers);
                        if (customers.isEmpty()) { // TODO jak to zrobić zeby 2 raz nie sprawdzało customerSerwive.find()  - tak jak wyzej
                            choose = 0;
                            System.out.println("You have no one to edit, add some customer first.");
                            break;
                        }
// =============================================== skonczylem ponizej ogarnac jak to dziala przy walidacji i poatrzec na ten kod
                        getCustomerUntilIsNotValid(userIO, customerService);
                        getCustomerNameUntilIsNotValid(userIO, customerService);

                        do {
                            customerNip = customerService.getNip(userIO.getNipToEditCustomer());
                            if (customerNip != null) {
                                System.out.println("This nip already exist in database.");
                            }
                        } while (customerNip != null);


                        customerService.edit(userIO.prepareCustomerToEdit(customer));
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
                    boolean correctChose = true;
                    do {
                        try {
                            choose = scanner.nextInt();
                            correctChose = true;
                        } catch (InputMismatchException ex) {
                            scanner.nextLine();
                            System.out.println("This is not a number, please enter the number correctly!");
                            correctChose = false;
                        }
                    } while (correctChose == false);
                } catch (InputMismatchException ex) {
                    System.out.println("This is not a correct number, please try again.");
                }
                if (choose < 0 || choose > 5 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
            } while (choose != 9);
        }

        if (choose == 3) {
            choose = 0;
            do {
                try {
                    InvoiceService invoiceService = new InvoiceService(connection);
                    CustomerService customerService = new CustomerService(connection);
                    Invoice invoice = null;
                    Customer customer;
                    if (choose == 1) {
                        userIO.showInvoices(invoiceService.find());
                    } else if (choose == 2) {
                        System.out.println("Customers list:");
                        userIO.showCustomers(customerService.find());
                        do {
                            customer = customerService.get(userIO.getCustomerIdToAddInvoice());
                            if (customer == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (customer == null);
                        invoiceService.add(userIO.prepareInvoiceToAdd(customer.getId()));
                    } else if (choose == 3) {
                        List<Invoice> invoices = invoiceService.find();
                        userIO.showInvoices(invoices);
                        if (invoices.isEmpty()) {
                            choose = 0;
                            System.out.println("You have no one to edit, add some invoice first");
                            break;
                        }
                        do {
                            invoice = invoiceService.get(userIO.getIdToEditInvoice());
                            if (invoice == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (invoice == null);
                        System.out.println("Customers list :");
                        userIO.showCustomers(customerService.find());
                        do {
                            customer = customerService.get(userIO.getCustomerId());
                            if (customer == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (customer == null);
                        invoiceService.edit(userIO.prepareInvoiceToEdit(invoice.getId(), customer.getId()));
                    } else if (choose == 4) {
                        userIO.showInvoices(invoiceService.find());
                        do {
                            invoice = invoiceService.get(userIO.getIdToDeleteInvoice());
                            if (invoice == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (invoice == null);
                        invoiceService.delete(userIO.deleteInvoice(invoice.getId()));
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }

                    System.out.println("What you want to do :\n1. Show invoices \n2. Add invoice \n" +
                            "3. Edit invoice \n4. Delete invoice \n5. Back to menu \n9. Close program");
                    boolean correctChose = true;
                    do {
                        try {
                            choose = scanner.nextInt();
                            correctChose = true;
                        } catch (InputMismatchException ex) {
                            scanner.nextLine();
                            System.out.println("This is not a number, please enter the number correctly!");
                            correctChose = false;
                        }
                    } while (correctChose == false);
                } catch (InputMismatchException ex) {
                    System.out.println("This is not a correct number, please try again:\n");
                }
                if (choose < 0 || choose > 4 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
            } while (choose != 9);
        }
        if (choose == 4) {
            choose = 0;

            InvoiceItemService invoiceItemService = new InvoiceItemService(connection);
            ProductService productService = new ProductService(connection);
            InvoiceService invoiceService = new InvoiceService(connection);
            Product product;
            Invoice invoice;
            InvoiceItem invoiceItem = null;
            do {
                try {
                    if (choose == 1) {
                        userIO.showInvoiceItem(invoiceItemService.find());
                    } else if (choose == 2) {
                        System.out.println("Products list :");
                        List<Product> products = productService.find();
                        userIO.showProduct(products);
                        if (products.isEmpty()) {
                            choose = 0;
                            System.out.println("You have no one invoice to add, add some invoice first");
                            break;
                        }
                        do {
                            product = productService.get(userIO.getProductIdToAddInvoiceItem());
                            if (product == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (product == null);
                        System.out.println("Invoices list:");
                        List<Invoice> invoices = invoiceService.find(); // TODO Ther's we have correct !!!
                        userIO.showInvoices(invoices);
                        if (invoices.isEmpty()) {
                            choose = 0; // TODO change chis choose on enum !!!
                            System.out.println("You have no one invoice to add, add some invoice first");
                            break;
                        }
                        do {
                            invoice = invoiceService.get(userIO.getInvoiceIdToAddInvoiceItem());
                            if (invoice == null) {
                                System.out.println("This id number don't exist in base.");
                            }
                        } while (invoice == null);
                        invoiceItemService.add(userIO.prepareInvoiceItemToAdd(product.getId(),invoice.getId(), product.getName(),
                                product.getNetPrice(), product.getTaxPercent())); // TODO skoro wcześniej już była podawana cene i nazwa produktu to czy powinno być tak - przekazywac invoice itp jak wczeniej
                    } else if (choose == 3) {
                        System.out.println("Invoice item list :");
                        userIO.showInvoiceItem(invoiceItemService.find()); // change names
                        getInvoiceItemUntilIsNotValid(userIO, invoiceItemService);
                        System.out.println("Products list :");
                        userIO.showProduct(productService.find());
                        product = getProductUntilIsNotValid(userIO, productService);
                        System.out.println("Invoice list :");
                        userIO.showInvoices(invoiceService.find());
                        invoice = getInvoiceUntilIsNotValid(userIO, invoiceService);
                        invoiceItemService.edit(userIO.preparedToEditInvoiceItem(invoiceItem, product, invoice));
                    } else if (choose == 4) {
                        userIO.showInvoiceItem(invoiceItemService.find());//TODO I download by get after id, if it returned something, I don't go in, if it passes, I remove it
                        invoiceItemService.delete(userIO.deleteInvoiceItem());
                    } else if (choose == 5) {
                        break;
                    } else if (choose == 9) {
                        System.out.println("You close program, see you next time !");
                        System.exit(0);
                    }
                    System.out.println("What you want to do :\n1. Show invoice item \n2. Add invoice item \n" +
                            "3. Edit invoice item \n4. Delete invoice item \n5. Back to menu \n9. Close program");
                    boolean correctChose = true;
                    do {
                        try {
                            choose = scanner.nextInt();
                            correctChose = true;
                        } catch (InputMismatchException ex) {
                            scanner.nextLine();
                            System.out.println("This is not a number, please enter the number correctly!");
                            correctChose = false;
                        }
                    } while (correctChose == false);
                } catch (InputMismatchException ex) {
                    System.out.println("This is not a correct number, please try again:\n");
                }
                if (choose < 0 || choose > 4 && choose != 9) {
                    System.out.println("Enter a number from 1 - 5 or press 9 to exit the program. :\n");
                }
            } while (choose != 9);
        }
        if (choose == 9) {
            System.out.println("You close program, see you next time !");
            System.exit(0);
        }
    }

    private static void getCustomerNameUntilIsNotValid(UserIO userIO, CustomerService customerService) {
        Customer customerName;
        do {
            customerName = customerService.getName(userIO.getCustomerName());
            if (customerName != null) {
                System.out.println("This customer already exist in database.");
            }
        } while (customerName != null);
    }

    private static Customer getCustomerUntilIsNotValid(UserIO userIO, CustomerService customerService) {
        Customer customer;
        do {
            customer = customerService.get(userIO.getCustomerId());
            if (customer == null) {
                System.out.println("This id number don't exist in base.");
            }
        } while (customer == null);
        return customer;
    }

    private static Invoice getInvoiceUntilIsNotValid(UserIO userIO, InvoiceService invoiceService) {
        Invoice invoice;
        do {
            invoice = invoiceService.get(userIO.getInvoiceIdToEditInvoiceItem());
            if (invoice == null) {
                System.out.println("This number don't exist in base.");
            }
        } while (invoice == null);
        return invoice;
    }

    private static Product getProductUntilIsNotValid(UserIO userIO, ProductService productService) {
        Product product;
        do {
            product = productService.get(userIO.getProductId());
            if (product == null) {
                System.out.println("This number don't exist in base.");
            }
        } while (product == null);
        return product;
    }

    private static void getInvoiceItemUntilIsNotValid(UserIO userIO, InvoiceItemService invoiceItemService) {
        InvoiceItem invoiceItem;
        do {
            invoiceItem = invoiceItemService.get(userIO.getInvoiceItemId());
            if (invoiceItem == null){
                System.out.println("This id number don't exist in base.");
            }
        } while (invoiceItem == null);
    }
}