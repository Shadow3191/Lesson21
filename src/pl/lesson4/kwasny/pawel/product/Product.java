package pl.lesson4.kwasny.pawel.product;

import java.math.BigDecimal;

public class Product {
    private Integer id;
    private String eanCode;
    private String name;
    private BigDecimal priceNet;
    private BigDecimal taxPercent;

    public Product(Integer id, String eanCode, String name, BigDecimal priceNet, BigDecimal taxPercent) {
        this.id = id;
        this.eanCode = eanCode;
        this.name = name;
        this.priceNet = priceNet;
        this.taxPercent = taxPercent;
    }

    public Product(String eanCode, String name, BigDecimal priceNet, BigDecimal taxPercent) {
        this.eanCode = eanCode;
        this.name = name;
        this.priceNet = priceNet;
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

    public BigDecimal getPriceNet() {
        return priceNet;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }
}
