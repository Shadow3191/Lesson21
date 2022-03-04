package pl.lesson4.kwasny.pawel.customer;

public class Customer {
    private Integer id;
    private String name;
    private String nipNumber;

    public Customer(Integer id, String name, String nipNumber) {
        this.id = id;
        this.name = name;
        this.nipNumber = nipNumber;
    }

    public Customer(String name, String nipNumber) {
        this.name = name;
        this.nipNumber = nipNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNipNumber() {
        return nipNumber;
    }
}
