package pl.lesson4.kwasny.pawel.invoice;

import pl.lesson4.kwasny.pawel.product.Product;

import java.sql.Connection;
import java.util.List;

public class InvoiceService {
    private InvoiceDao invoiceDao;

    public InvoiceService(Connection connection) {
        invoiceDao = new InvoiceDao(connection);
    }

    public List<Invoice> find() {
        return invoiceDao.find();
    }

    public Invoice get(Integer id) {
        return invoiceDao.get(id);
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
