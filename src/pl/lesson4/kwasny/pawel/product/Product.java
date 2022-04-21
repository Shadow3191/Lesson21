package pl.lesson4.kwasny.pawel.product;

import java.math.BigDecimal;

public class Product {
    private Integer id;
    private String eanCode;
    private String name;
    private BigDecimal netPrice;
    private BigDecimal taxPercent;

    public Product(Integer id, String eanCode, String name, BigDecimal netPrice, BigDecimal taxPercent) {
        this.id = id;
        this.eanCode = eanCode;
        this.name = name;
        this.netPrice = netPrice;
        this.taxPercent = taxPercent;
    }

    public Product(String eanCode, String name, BigDecimal netPrice, BigDecimal taxPercent) {
        this.eanCode = eanCode;
        this.name = name;
        this.netPrice = netPrice;
        this.taxPercent = taxPercent;
    }

    public Integer getId() {
        return id;
    }

    public String getEanCode() {
        return eanCode;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }
}
