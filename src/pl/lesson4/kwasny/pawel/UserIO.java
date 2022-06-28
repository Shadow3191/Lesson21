package pl.lesson4.kwasny.pawel;

import pl.lesson4.kwasny.pawel.customer.Customer;
import pl.lesson4.kwasny.pawel.invoice.Invoice;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItem;
import pl.lesson4.kwasny.pawel.product.Product;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserIO {
    private Scanner scanner = new Scanner(System.in);
    private Pattern nipNumberPattern = Pattern.compile("^[1-9]\\d{2}-\\d{2}-\\d{2}-\\d{3}$");
    private Pattern namePattern = Pattern.compile("[A-Za-z]*");


    public void showCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println(customer.getId() + " | " + customer.getName() + " | " + customer.getNipNumber());
        }
        if (customers != null && customers.isEmpty()) {
            System.out.println("This database is empty - don't have added any position\n");
        }
        System.out.println();
    }

    // TODO change all names to correct
    public Customer prepareCustomerToAdd() {
        System.out.println("Enter the name of customer :");
        String name = scanner.nextLine();
        System.out.println("Enter customer nip number in configuration 3-2-2-3 :");
        String nipNumber = scanner.nextLine();
        Boolean correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
        do {
            if (!correctNipNumber) {
                System.out.println("You must write here nip number !");
                System.out.println("Enter nip number in configuration 3-2-2-3 :");
                nipNumber = scanner.nextLine();
                correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
            }
        } while (!correctNipNumber);
        return new Customer(name, nipNumber);
    }

    // TODO ogarnac jak powinny dzialac te wyrazenia reguladne
    public int editId() {
        boolean goNext;
        int id = 0;
        do {
            System.out.println("Enter the customer id number to edit :");
            goNext = true;
            try {
                id = scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("You must enter an integer.\n");
                goNext = false;
            }
            scanner.nextLine();
        } while (goNext != true);
        return id;
    }

    public String editName() {
        System.out.println("Enter name :");
        String name = scanner.nextLine();
        Boolean correctName = isCorrectValue(name, namePattern);
        while (!correctName) {
            if (correctName) {
                System.out.println("You must write here name.\n");
                System.out.println("Enter name :");
                name = scanner.nextLine();
            }
        }
        return name;
    }

    public String editNipNumber() {
        System.out.println("Enter nip number in configuration 3-2-2-3 :");
        String nipNumber = scanner.nextLine();
        Boolean correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
        do {
            if (!correctNipNumber) {
                System.out.println("You must write here nip number !");
                System.out.println("Enter nip number in configuration 3-2-2-3 :");
                nipNumber = scanner.nextLine();
                correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
            }
        } while (!correctNipNumber);
        return nipNumber;
    }

    private boolean isCorrectValue(String nipNumber, Pattern nipNumberPattern) {
        return nipNumberPattern.matcher(nipNumber).matches();
    }


    public Customer editCustomer() {
        int id = editId();
        String name = editName();
        String nipNumber = editNipNumber();
        return new Customer(id, name, nipNumber);
    }

    public Customer deleteCustomer() {
        System.out.println("Enter the customer id number to be removed from the database:");
        int id = scanner.nextInt();
        System.out.println("You delete id number : " + id);
        return new Customer(id, null, null);
    }

    public void showProduct(List<Product> products) {
        System.out.println("Products :");
        for (Product product : products) {
            System.out.println(product.getId() + " | " + product.getEanCode() + " | " + product.getName() + " | " +
                    product.getNetPrice() + " | " + product.getTaxPercent());
        }
        if (products != null && products.isEmpty()) {
            System.out.println("This database is empty - don't have added any position\n");
        }
        System.out.println();
    }

    public Product addProduct() {
        System.out.println("Enter ean code :");
        String ean = scanner.nextLine();
        System.out.println("Enter name of product :");
        String name = scanner.nextLine();
        System.out.println("Enter net price :");
        BigDecimal netPrice = scanner.nextBigDecimal();
        System.out.println("Enter tax percent :");
        BigDecimal taxPercent = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.println();
        return new Product(ean, name, netPrice, taxPercent);
    }

    public Product editProduct(List<Product> products) {
        showProduct(products);
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
        System.out.println();
        return new Product(id, ean, name, netPrice, taxPercent);
    }

    public Product deleteProduct() {
        System.out.println("Enter the product id number to be removed from the database: ");
        int id = scanner.nextInt();
        System.out.println("You delete id number : " + id);
        return new Product(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public void showInvoices(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            System.out.println(invoice.getId() + " | " + invoice.getNumber() + " | " + invoice.getCustomerID() + " | "
                    + invoice.getPriceNetSum() + " | " + invoice.getPriceGossSum());
        }
        if (invoices != null && invoices.isEmpty()) {
            System.out.println("This database is empty - don't have added any position.\n");
        }
        System.out.println();

    }

    public Invoice addInvoice() {
        System.out.println("Enter invoice number :");
        String number = scanner.nextLine();
        System.out.println("Enter customer id :");
        int customerId = scanner.nextInt();
        return new Invoice(number, customerId, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public Invoice editInvoice() {
        System.out.println("Enter id number of invoice who you want to edit :");
        int id = scanner.nextInt();
        System.out.println("Enter the invoice number :");
        scanner.nextLine();
        String number = scanner.nextLine();
        System.out.println("Enter customer id :");
        int customerId = scanner.nextInt();
        return new Invoice(id, number, customerId, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public Invoice deleteInvoice() {
        System.out.println("Enter the invoice id number to be removed from the database:");
        int id = scanner.nextInt();
        System.out.println("You delete id number : " + id);
        return new Invoice(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public void showInvoiceItem(List<InvoiceItem> invoiceItems) {
        for (InvoiceItem showInvoiceItem : invoiceItems) {
            System.out.println(showInvoiceItem.getId() + " | " + showInvoiceItem.getProductId() + " | " + showInvoiceItem.getInvoiceId() +
                    " | " + showInvoiceItem.getQuantity() + " | " + showInvoiceItem.getProductName() + " | " + showInvoiceItem.getNetPrice()
                    + " | " + showInvoiceItem.getTaxPercent() + " | " + showInvoiceItem.getGrossPrice());
        }
        if (invoiceItems != null && invoiceItems.isEmpty()) {
            System.out.println("This database is empty - don't have added any position.\n");
        }
    }

    public InvoiceItem addInvoiceItem() {
        System.out.println("Enter the product id :");
        int productId = scanner.nextInt();
        System.out.println("Enter the invoice id :");
        int invoiceId = scanner.nextInt();
        System.out.println("Enter the product quantity :");
        int quantity = scanner.nextInt();
        System.out.println("Enter the product name :");
        scanner.nextLine();
        String productName = scanner.nextLine();
        System.out.println("Enter net price :");
        BigDecimal netPrice = scanner.nextBigDecimal();
        System.out.println("Enter the tax percent :");
        BigDecimal taxPercent = scanner.nextBigDecimal();
        BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice).multiply(BigDecimal.valueOf(quantity));
        return new InvoiceItem(productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }

    public InvoiceItem editInvoiceItem() {

        System.out.println("Enter id from invoice item what you want to edit :");
        int id = scanner.nextInt();
        System.out.println("Enter product id who you want to edit :");
        int productId = scanner.nextInt();
        System.out.println("Enter invoice id tho you want to edit :");
        int invoiceId = scanner.nextInt();
        System.out.println("Enter quantity :");
        int quantity = scanner.nextInt();
        System.out.println("Enter product name :");
        scanner.nextLine();
        scanner.nextBigDecimal();
        String productName = scanner.nextLine();
        System.out.println("Enter net price :");
        BigDecimal netPrice = scanner.nextBigDecimal();
        System.out.println("Enter tax percent :");
        BigDecimal taxPercent = scanner.nextBigDecimal();
        BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice).multiply(BigDecimal.valueOf(quantity));
        return new InvoiceItem(id, productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }

    public InvoiceItem deleteInvoiceItem() {
        System.out.println("Enter the invoice item id number to be removed from the database:");
        int id = scanner.nextInt();
        System.out.println("You delete id nr : " + id);
        return new InvoiceItem(id, null, null, null, null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
// IO input/output klasa do wejscia i wyjscia ma gadac z uzytkownikiem