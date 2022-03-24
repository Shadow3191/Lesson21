package pl.lesson4.kwasny.pawel.invoiceItem;

import java.math.BigDecimal;

public class InvoiceItem {
    private Integer id;
    private Integer productId;
    private Integer invoiceId;
    private Integer quantity;
    private String productName;
    private BigDecimal netPrice;
    private BigDecimal taxPercent;
    private BigDecimal grossPrice;

    public InvoiceItem(Integer id, Integer productId, Integer invoiceId, Integer quantity, String productName,
                       BigDecimal netPrice, BigDecimal taxPercent, BigDecimal grossPrice) {
        this.id = id;
        this.productId = productId;
        this.invoiceId = invoiceId;
        this.quantity = quantity;
        this.productName = productName;
        this.netPrice = netPrice;
        this.taxPercent = taxPercent;
        this.grossPrice = grossPrice;
    }

    public InvoiceItem(Integer productId, Integer invoiceId, Integer quantity, String productName,
                       BigDecimal netPrice, BigDecimal taxPercent, BigDecimal grossPrice) {
        this.productId = productId;
        this.invoiceId = invoiceId;
        this.quantity = quantity;
        this.productName = productName;
        this.netPrice = netPrice;
        this.taxPercent = taxPercent;
        this.grossPrice = grossPrice;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }
}
