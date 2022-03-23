package pl.lesson4.kwasny.pawel.invoice;

import java.math.BigDecimal;

public class Invoice {
    private Integer id;
    private String number;
    private Integer customerID;
    private BigDecimal priceNetSum;
    private BigDecimal priceGossSum;

    public Invoice(Integer id, String number, Integer customerID, BigDecimal priceNetSum, BigDecimal priceGossSum) {
        this.id = id;
        this.number = number;
        this.customerID = customerID;
        this.priceNetSum = priceNetSum;
        this.priceGossSum = priceGossSum;
    }

    public Invoice(String number, Integer customerID, BigDecimal priceNetSum, BigDecimal priceGossSum) {
        this.number = number;
        this.customerID = customerID;
        this.priceNetSum = priceNetSum;
        this.priceGossSum = priceGossSum;
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

    public BigDecimal getPriceGossSum() {
        return priceGossSum;
    }


}
