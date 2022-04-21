package pl.lesson4.kwasny.pawel.invoice;

import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItem;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItemDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InvoiceService {
    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;

    public InvoiceService(Connection connection) {
        invoiceDao = new InvoiceDao(connection);
    }

    public List<Invoice> find() throws SQLException {
        return invoiceDao.find();
    }

    public void add(Invoice invoice) {
        invoiceDao.add(invoice);
    }

    public void edit(Invoice invoice) {
        invoiceDao.edit(invoice);
    }

    public void delete(Invoice invoice) {
        invoiceDao.delete(invoice);
    }
}
