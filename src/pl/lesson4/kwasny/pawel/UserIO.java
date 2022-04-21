package pl.lesson4.kwasny.pawel;

import pl.lesson4.kwasny.pawel.customer.Customer;
import pl.lesson4.kwasny.pawel.invoice.Invoice;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItem;
import pl.lesson4.kwasny.pawel.product.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class UserIO {
    Scanner scanner = new Scanner(System.in);


    public void showCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println(customer.getId() + " | " + customer.getName() + " | " + customer.getNipNumber());
        }
    }

    // TODO change all names to correct
    public Customer prepareCustomerToAdd() {
        System.out.println("Enter the name of customer :");
        String name = scanner.nextLine();
        System.out.println("Enter the nip number of customer :");
        String nipNumber = scanner.nextLine();
        return new Customer(name, nipNumber);
    }

    public Customer editCustomer() {
        System.out.println("Enter the customer id number to edit :");
        int id = scanner.nextInt();
        System.out.println("Enter name :");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Enter nip number :");
        String nipNumber = scanner.nextLine();
        return new Customer(id, name, nipNumber);
    }

    public Customer deleteCustomer() {
        System.out.println("Enter the customer id number to be removed from the database:");
        int id = scanner.nextInt();
        return new Customer(id, null, null);
    }

    public void showProduct(List<Product> products) {
        for (Product product : products) {
            System.out.println(product.getId() + " | " + product.getEanCode() + " | " + product.getName() + " | " +
                    product.getNetPrice() + " | " + product.getTaxPercent());
        }
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
        return new Product(ean, name, netPrice, taxPercent);
    }

    public Product editProduct() {
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
        return new Product(id, ean, name, netPrice, taxPercent);
    }

    public Product deleteProduct() {
        System.out.println("Enter the product id number to be removed from the database: ");
        int id = scanner.nextInt();
        return new Product(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public void showInvoices(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            System.out.println(invoice.getId() + " | " + invoice.getNumber() + " | " + invoice.getCustomerID() + " | "
                    + invoice.getPriceNetSum() + " | " + invoice.getPriceGossSum());
        }
    }

    public Invoice addInvoice() {
        System.out.println("Enter number :");
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
        return new Invoice(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public void showInvoiceItem(List<InvoiceItem> invoiceItems) {
        for (InvoiceItem showInvoiceItem : invoiceItems) {
            System.out.println(showInvoiceItem.getId() + " | " + showInvoiceItem.getProductId() + " | " + showInvoiceItem.getInvoiceId() +
                    " | " + showInvoiceItem.getQuantity() + " | " + showInvoiceItem.getProductName() + " | " + showInvoiceItem.getNetPrice()
                    + " | " + showInvoiceItem.getTaxPercent() + " | " + showInvoiceItem.getGrossPrice());
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
        System.out.println("Enter id from invoice item what you whant to edit :");
        int id = scanner.nextInt();
        System.out.println("Enter product id who you want to edit :");
        int productId = scanner.nextInt();
        System.out.println("Enter invoice id tho you want to edit :");
        int invoiceId = scanner.nextInt();
        System.out.println("Enter quantity :");
        int quantity = scanner.nextInt();
        System.out.println("Enter product name :");
        scanner.nextLine();
        String productName = scanner.nextLine();
        System.out.println("Enter net price :");
        BigDecimal netPrice = scanner.nextBigDecimal();
        System.out.println("Enter tax percent :");
        BigDecimal taxPercent = scanner.nextBigDecimal();
        BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice).multiply(BigDecimal.valueOf(quantity));

        return new InvoiceItem(id, productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }
}
// IO input/output klasa do wejscia i wyjscia ma gadac z uzytkownikiem