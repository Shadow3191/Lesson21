package pl.lesson4.kwasny.pawel.invoiceItem;

import pl.lesson4.kwasny.pawel.product.Product;

import java.sql.Connection;
import java.util.List;

public class InvoiceItemService {
    private InvoiceItemDao invoiceItemDao;

    public InvoiceItemService(Connection connection) {
        invoiceItemDao = new InvoiceItemDao(connection);
    }

    public List<InvoiceItem> find() {
        return invoiceItemDao.find();
    }

    public InvoiceItem get(Integer id) {
        return invoiceItemDao.get(id);
    }

    public void add(InvoiceItem invoiceItem) {
        invoiceItemDao.add(invoiceItem);
    }

    public void edit(InvoiceItem invoiceItem) {
        invoiceItemDao.edit(invoiceItem);
    }

    public void delete(InvoiceItem invoiceItem) {
        invoiceItemDao.delete(invoiceItem);
    }

}
