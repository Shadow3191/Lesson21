package pl.lesson4.kwasny.pawel.invoice;

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

    public void edit(Invoice invoice) throws SQLException {
        invoiceDao.edit(invoice);
    }

    public void delete(Invoice invoice) throws SQLException {
        invoiceDao.delete(invoice);
    }
}
//TODO tutaj w cenie brutto powinno mnożyć netto razy 23% ale jak to napisac ?
