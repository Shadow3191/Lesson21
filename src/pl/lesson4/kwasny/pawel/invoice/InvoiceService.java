package pl.lesson4.kwasny.pawel.invoice;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class InvoiceService {
    private Scanner scanner = new Scanner(System.in);
    private InvoiceDao invoiceDao;

    public InvoiceService(Connection connection) {
        invoiceDao = new InvoiceDao(connection);
    }

    public void show() throws SQLException {
        for (Invoice showInvoice : invoiceDao.find()) {
            System.out.println(showInvoice.getId() + " | " + showInvoice.getNumber() + " | " + showInvoice.getCustomerID() + " | " +
                    showInvoice.getPriceNetSum() + " | " + showInvoice.getPriceGossSum());
        }
    }

    public void add(Invoice invoice) {
        invoiceDao.add(invoice);
    }

    public void edit() throws SQLException {
        System.out.println("Enter the invoice number :");
        String number = scanner.nextLine();
        System.out.println("Enter customer id :");
        int customerId = scanner.nextInt();
        System.out.println("Enter the netto price :");
        BigDecimal netPrice = scanner.nextBigDecimal();
        System.out.println("Enter the gross price :");
        BigDecimal grossPrice = scanner.nextBigDecimal();
        System.out.println("Enter id number of invoice who you want to edit :");
        int id = scanner.nextInt();

        invoiceDao.edit(new Invoice(id, number, customerId, netPrice, grossPrice));
    }

    public void delete() throws SQLException {
        System.out.println("Enter the invoice id number to be removed from the database:");
        int id = scanner.nextInt();

        invoiceDao.delete(new Invoice(id, null, null, null, null));
    }
    //TODO tutaj w cenie brutto powinno mnożyć netto razy 23% ale jak to napisac ?
}
