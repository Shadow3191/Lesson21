package pl.lesson4.kwasny.pawel.invoice;

import java.math.BigDecimal;

public class Invoice {
    private Integer id;
    private String number;
    private Integer customerID;
    private BigDecimal priceNetSum;
    private BigDecimal priceGrossSum;

    public Invoice(Integer id, String number, Integer customerID, BigDecimal priceNetSum, BigDecimal priceGrossSum) {
        this.id = id;
        this.number = number;
        this.customerID = customerID;
        this.priceNetSum = priceNetSum;
        this.priceGrossSum = priceGrossSum;
    }

    public Invoice(String number, Integer customerID, BigDecimal priceNetSum, BigDecimal priceGrossSum) {
        this.number = number;
        this.customerID = customerID;
        this.priceNetSum = priceNetSum;
        this.priceGrossSum = priceGrossSum;
    }

    public Integer getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public BigDecimal getPriceNetSum() {
        return priceNetSum;
    }

    public BigDecimal getPriceGrossSum() {
        return priceGrossSum;
    }

}
