package pl.lesson4.kwasny.pawel.invoiceItem;

import pl.lesson4.kwasny.pawel.product.Product;

import java.sql.Connection;
import java.sql.SQLException;

public class InvoiceItemService {
    private InvoiceItemDao invoiceItemDao;

    public InvoiceItemService(Connection connection) {
        invoiceItemDao = new InvoiceItemDao(connection);
    }

    public void show() throws SQLException {
        for (InvoiceItem showInvoiceItem : invoiceItemDao.find()) {
            System.out.println(showInvoiceItem.getId() + " | " + showInvoiceItem.getProductId() + " | " + showInvoiceItem.getInvoiceId() +
                    " | " + showInvoiceItem.getQuantity() + " | " + showInvoiceItem.getProductName() + " | " + showInvoiceItem.getNetPrice()
            + " | " + showInvoiceItem.getTaxPercent() + " | " + showInvoiceItem.getGrossPrice());
        }
    }


    public void add(InvoiceItem invoiceItem) throws SQLException {
        invoiceItemDao.add(invoiceItem);
    }

}
