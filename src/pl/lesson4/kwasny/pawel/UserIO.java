package pl.lesson4.kwasny.pawel;

import pl.lesson4.kwasny.pawel.customer.Customer;
import pl.lesson4.kwasny.pawel.customer.CustomerService;
import pl.lesson4.kwasny.pawel.invoice.Invoice;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItem;
import pl.lesson4.kwasny.pawel.product.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserIO {
    private Scanner scanner = new Scanner(System.in);
    private Pattern nipNumberPattern = Pattern.compile("^[1-9]\\d{2}-\\d{2}-\\d{2}-\\d{3}$");
    private Pattern namePattern = Pattern.compile("[A-Za-z]*");
    private Pattern correctEanPattern = Pattern.compile("^\\d{13}$");
    private Pattern correctNetPricePattern = Pattern.compile("^\\d+");


    public void showCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.format("%3s| %20s| %14s|", customer.getId(), customer.getName(), customer.getNipNumber());
            System.out.println();
        }
        System.out.println();
    }

    public void checkingEmptyItems(List<Customer> customers) {
        if (customers != null && customers.isEmpty()) {
            System.out.println("No customers have been added yet.\n");
        }
    }

    public String getTheCustomerNameToAdd() {
        System.out.println("Enter the name of customer :");
        String name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }
        return name;
    }

    public String getTheCustomerNipNumberToAdd() {
        System.out.println("Enter customer nip number in configuration 3-2-2-3 :");
        String nipNumber = scanner.nextLine();
        Boolean correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
        do {
            if (!correctNipNumber) {
                System.out.println("You must write here nip number ! Enter nip number in configuration 3-2-2-3 :");
                nipNumber = scanner.nextLine();
                correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
            }
        } while (!correctNipNumber);
        return nipNumber;
    }

    public Customer preparaCustomerToAdd() {
        String name = getTheCustomerNameToAdd();
        String nipNumber = getTheCustomerNipNumberToAdd();
        return new Customer(name, nipNumber);
    }

    // TODO JAK ZABEZPIECZYĆ TEN KOD PRZED PODANIEM NIEPOPRAWNEGO ID ? TU I W KAŻDEJ INNEJ EDYCJI ?
    int id;

    public int checkId(CustomerService customerService) {
        System.out.println("Enter the customer id number to edit :");
        id = scanner.nextInt();
        int checkedId = 0;
        try {
            for (Customer idFromList : customerService.find()) {
                checkedId = idFromList.getId();
                if (checkedId == id) {
                    checkedId = 10;
                    break;
                } else {
                    checkedId = 20;
                }
            }
        } catch (Exception exception) {
            System.out.println("COS POSZLO NIE TAK");
        }
        return checkedId;
    }

    int isEmpty;

    public int getIdToEditCustomer(CustomerService customerService) {
        boolean goNext;
        do {
            goNext = true;
            try {
                if (checkId(customerService) == 20) {
                    isEmpty = 20;
                } else {
                    isEmpty = 10;
                }
            } catch (InputMismatchException exception) {
                System.out.println("You must enter an integer.\n");
                goNext = false;
            }
            scanner.nextLine();
        } while (goNext != true);
        if (isEmpty == 10) {
            return id;
        } else {
            return isEmpty;
        }
    }

    // TODO dlaczego tutaj przy podaniu inta w stringu puszcza dalej ?
    public String getNameToEditCustomer() {
        System.out.println("Enter name :");
        String name = null;
        name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }
        Boolean correctName = isCorrectValue(name, namePattern);
        while (!correctName) {
            if (correctName) {
                System.out.println("You must write here name.\n");
                System.out.println("Enter name :");
                name = scanner.nextLine();
            }
        }
        return name;
    }

    public String getNipToEditCustomer() {
        System.out.println("Enter nip number in configuration 3-2-2-3 :");
        String nipNumber = scanner.nextLine();
        Boolean correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
        do {
            if (!correctNipNumber) {
                System.out.println("You must write here nip number !");
                System.out.println("Enter nip number in configuration 3-2-2-3 :");
                nipNumber = scanner.nextLine();
                correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
            }
        } while (!correctNipNumber);
        return nipNumber;
    }

    private boolean isCorrectValue(String nipNumber, Pattern nipNumberPattern) {
        return nipNumberPattern.matcher(nipNumber).matches();
    }

    public Customer prepareCustomerToEdit(CustomerService customerService) {
        int id = getIdToEditCustomer(customerService);
        if (isEmpty == 10) {
            String name = getNameToEditCustomer();
            String nipNumber = getNipToEditCustomer();
            return new Customer(id, name, nipNumber);
        } else {
            return new Customer(null, null, null);
        }
    }

    // TODO wszedzie gdzie trzeba podac id nie sprawdza i po prostu przeskakuje dalej jak to obsluzyc ?
    // TODO Aby obsłużyć usunięcie Customera muszę najpierw usunac z Invoice pozycję gdzie jest dany customer
    public Customer deleteCustomer() {
        System.out.println("Enter the customer id number to be removed from the database:");
        int id = 0;
        boolean helpPoint = false;
        do {
            helpPoint = false;
            try {
                id = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("It's not a correct number! Enter correct id number:");
                helpPoint = true;
            }
            scanner.nextLine();
        } while (helpPoint == true);

        System.out.println("You delete id number : " + id);
        return new Customer(id, null, null);
    }

    public void showProduct(List<Product> products) {
        System.out.println("Products :");
        for (Product product : products) {
            System.out.format("%3s| %13s| %19s| %6s| %6s|", product.getId(), product.getEanCode(), product.getName(),
                    product.getNetPrice(), product.getTaxPercent());
            System.out.println();
        }
        if (products != null && products.isEmpty()) {
            System.out.println("This database is empty - don't have added any position\n");
        }
        System.out.println();
    }


    public String getEanToAddProduct() {
        System.out.println("Enter the 13-digit EAN code :");
        String eanCode = scanner.nextLine();
        Boolean correctEanCode = isCorrectEanValue(eanCode, correctEanPattern);
        do {
            if (!correctEanCode) {
                try {
                    System.out.println("You must enter 13-digit code !");
                    eanCode = scanner.nextLine();
                    correctEanCode = isCorrectEanValue(eanCode, correctEanPattern);
                } catch (Exception exception) {
                    System.out.println("This EAN code already exists in the database, enter the new code :");
                }
            }
        } while (!correctEanCode);
        return eanCode;
    }

    public String getProductNameToAdd() {
        System.out.println("Enter name of product :");
        String name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }
        return name;
    }

    public BigDecimal getProductNetPriceToAdd() {
        BigDecimal netPrice = null;
        int helpPoint = 0;
        System.out.println("Enter product price :");
        while (helpPoint != 1) {
            helpPoint = 1;
            try {
                // TODO czy tutaj da się jakoś zabezpieczyć przed enterem przed brakiem wpisania ceny czy program nie pusci ?
                netPrice = scanner.nextBigDecimal();
                if (netPrice == null) {
                    System.out.println("Price can't be null!");
                }
            } catch (Exception exception) {
                System.out.println("It isn't a price ! Enter correct price :");
                helpPoint = 0;
                scanner.nextLine();
                // TODO JAK TUTAJ DODAć DODATKOWE ODBłUZENIE ZEBY POKAZYWALO ZE TEN TOWAR JUZ JEST I WPISUJEMY KOLEJNY RAZ NAZWE TOWARU ?
            }
        }
        return netPrice;
    }

    public BigDecimal getTaxPercentToAdd() {
        BigDecimal taxPercent = null;
        int helpPointTax = 0;
        while (helpPointTax != 1) {
            helpPointTax = 1;
            System.out.println("Enter tax percent :");

            try {
                taxPercent = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It isn't a tax percent, enter correct value !");
                helpPointTax = 0;
            }
            scanner.nextLine();
        }
        System.out.println();
        return taxPercent;
    }

    public Product prepareProductToAdd() {
        String eanCode = getEanToAddProduct();
        String name = getProductNameToAdd();
        BigDecimal netPrice = getProductNetPriceToAdd();
        BigDecimal taxPercent = getTaxPercentToAdd();
        return new Product(eanCode, name, netPrice, taxPercent);
    }

    private boolean isCorrectEanValue(String eanCode, Pattern correctEanPattern) {
        return correctEanPattern.matcher(eanCode).matches();
    }

    public int getIdToEditProduct() {
        int id = 0;
        int helpPoint = 0;
        while (helpPoint != 1) {
            System.out.println("Enter the id number you want to edit :");
            helpPoint = 1;
            try {
                id = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("Id number isn't correct.");
                helpPoint = 0;
            }
            scanner.nextLine();
        }
        return id;
    }

    public String getEanToEditProduct() {
        System.out.println("Enter the 13-digit EAN code :");
        String eanCode = scanner.nextLine();
        Boolean correctEanCode = isCorrectEanValue(eanCode, correctEanPattern);
        do {
            if (!correctEanCode) {
                System.out.println("You must enter 13-digit code !");
                eanCode = scanner.nextLine();
                correctEanCode = isCorrectEanValue(eanCode, correctEanPattern);
            }
        } while (!correctEanCode);
        return eanCode;
    }

    public String getProductNameToEdit() {
        System.out.println("Enter name of product :");
        String name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }
        return name;
    }

    public BigDecimal getProductNetPriceToEdit() {
        BigDecimal netPrice = null;
        int helpPoint1 = 0;
        System.out.println("Enter net price :");
        while (helpPoint1 != 1) {
            helpPoint1 = 1;
            try {
                // TODO czy tutaj da się jakoś zabezpieczyć przed enterem przed brakiem wpisania ceny ?
                netPrice = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It isn't a price ! Enter correct price :");
                helpPoint1 = 0;
                scanner.nextLine();
                // TODO JAK TUTAJ DODAć DODATKOWE ODBłUZENIE ZEBY POKAZYWALO ZE TEN TOWAR JUZ JEST I WPISUJEMY KOLEJNY RAZ NAZWE TOWARU ?
            }
        }
        return netPrice;
    }

    public BigDecimal getProductTaxPercentToEdit() {
        BigDecimal taxPercent = null;
        int helpPointTax = 0;
        while (helpPointTax != 1) {
            helpPointTax = 1;
            System.out.println("Enter tax percent :");
            try {
                taxPercent = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It isn't a tax percent, enter correct value !");
                helpPointTax = 0;
            }
            scanner.nextLine();
        }
        return taxPercent;
    }

    public Product prepareProductToEdit() {
        int id = getIdToEditProduct();
        String eanCode = getEanToEditProduct();
        String name = getProductNameToEdit();
        BigDecimal netPrice = getProductNetPriceToEdit();
        BigDecimal taxPercent = getProductTaxPercentToEdit();
        return new Product(id, eanCode, name, netPrice, taxPercent);
    }

    public Product deleteProduct() {
        System.out.println("Enter the product id number to be removed from the database:");
        int id = 0;
        boolean helpPoint;
        do {
            try {
                id = scanner.nextInt();
                helpPoint = false;
            } catch (Exception exception) {
                System.out.println("It's not a correct number! Enter correct id number:");
                helpPoint = true;
            }
            scanner.nextLine();
        } while (helpPoint == true);

        System.out.println("You delete id number : " + id + "\n");
        return new Product(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }


    public void showInvoices(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            System.out.format("%3s| %15s| %3s| %5s| %5s|", invoice.getId(), invoice.getNumber(), invoice.getCustomerID(), invoice.getPriceNetSum(), invoice.getPriceGrossSum());
            System.out.println();
        }
        if (invoices != null && invoices.isEmpty()) {
            System.out.println("This database is empty - don't have added any position.\n");
        }
        System.out.println();

    }

    public Invoice addInvoice() {
        System.out.println("Enter invoice number :");
        String number = scanner.nextLine();
        System.out.println("Enter the customer id from the list above :");
        int customerId = scanner.nextInt();
        return new Invoice(number, customerId, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    //    public Invoice editInvoice() {
    public int getInvoiceIdToEdit() {
        System.out.println("Enter id number of invoice who you want to edit :");
        int id = 0;
        boolean helpPoint;
        do {
            helpPoint = false;
            try {
                id = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter a number! Enter correct number:");
                helpPoint = true;
                scanner.nextLine();
            }
        } while (helpPoint == true);
        return id;
    }

    public String getInvoiceNumberToEdit() {
        System.out.println("Enter the invoice number :");
        scanner.nextLine();
        String number = scanner.nextLine();
        return number;
    }

    public int getCustomerIdToInvoiceEdit() {
        System.out.println("Enter customer id :");
        int customerId = 0;
        boolean helpPoint2 = false;
        do {
            try {
                customerId = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter a number! Enter correct number:");
                helpPoint2 = true;
            }
            scanner.nextLine();
        } while (helpPoint2 == true);
        return customerId;
    }

    public Invoice prepareInvoiceToEdit() {
        int id = getInvoiceIdToEdit();
        String number = getInvoiceNumberToEdit();
        int customerId = getCustomerIdToInvoiceEdit();
        return new Invoice(id, number, customerId, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public Invoice deleteInvoice() {
        System.out.println("Enter the invoice id number to be removed from the database:");
        int id = 0;
        boolean helpPoint = false;
        do {
            helpPoint = false;
            try {
                id = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("It's not a correct number! Enter correct invoice number:");
                helpPoint = true;
            }
            scanner.nextLine();
        } while (helpPoint == true);

        System.out.println("You delete id number : " + id);
        return new Invoice(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    boolean emptyDatabase = false;

    public void showInvoiceItem(List<InvoiceItem> invoiceItems) {
        for (InvoiceItem showInvoiceItem : invoiceItems) {
            System.out.format("%3s| %3s| %3s| %3s| %20s| %7s| %7s| %7s|", showInvoiceItem.getId(), showInvoiceItem.getProductId()
                    , showInvoiceItem.getInvoiceId(), showInvoiceItem.getQuantity(), showInvoiceItem.getProductName(),
                    showInvoiceItem.getNetPrice(), showInvoiceItem.getTaxPercent(), showInvoiceItem.getGrossPrice());
            System.out.println();
        }
        System.out.println();
        if (invoiceItems != null && invoiceItems.isEmpty()) {
            System.out.println("This database is empty - don't have added any position.\n");
            emptyDatabase = true;
        }
    }

    //    public InvoiceItem addInvoiceItem() {
    public int getProductIdToAddInvoiceItem() {
        System.out.println("Enter the product id from the list above:");
        int productId = 0;
        boolean helpPoint = false;
        do {
            helpPoint = false;
            try {
                productId = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter a number ! Enter correct product id number :");
                helpPoint = true;
            }
            scanner.nextLine();
        } while (helpPoint == true);
        return productId;
    }

    public int getInvoiceIdToAddInvoiceItem() {
        System.out.println("Enter the invoice id from the list above :");
        int invoiceId = 0;
        boolean helpPoint2 = false;
        do {
            helpPoint2 = false;
            try {
                invoiceId = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter a number ! Enter correct invoice id number :");
                helpPoint2 = true;
            }
            scanner.nextLine();
        } while (helpPoint2 == true);
        return invoiceId;
    }

    public int getProductQuantityToAddInvoiceItem() {
        System.out.println("Enter the product quantity :");
        int quantity = 0;
        boolean helpPoint3 = false;
        do {
            helpPoint3 = false;
            try {
                quantity = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter a number ! Enter correct quantity number :");
                helpPoint3 = true;
            }
            scanner.nextLine();
        } while (helpPoint3 == true);
        return quantity;
    }

    public String getProductNameToAddInvoiceItem() {
        System.out.println("Enter the product name :");
        String productName = scanner.nextLine();
        return productName;
    }

    public BigDecimal getNetPriceToAddInvoiceItem() {
        System.out.println("Enter net price :");
        BigDecimal netPrice = null;
        boolean helpPoint4 = false;
        do {
            helpPoint4 = false;
            try {
                netPrice = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It's not a price ! Enter correct net price:");
                helpPoint4 = true;
            }
            scanner.nextLine();
        } while (helpPoint4 == true);
        return netPrice;
    }

    public BigDecimal getTaxPercentToAddInvoiceItem() {
        System.out.println("Enter the tax percent :");
        BigDecimal taxPercent = null;
        boolean helpPoint5 = false;
        do {
            helpPoint5 = false;
            try {
                taxPercent = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It's not a tax percent ! Enter correct tax percent:");
                helpPoint5 = true;
            }
            scanner.nextLine();
        } while (helpPoint5 == true);
        return taxPercent;
    }

    public BigDecimal getGrossPrice(BigDecimal netPrice, BigDecimal taxPercent, int quantity) {
        BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice)
                .multiply(BigDecimal.valueOf(quantity));
        return grossPrice;
    }

    public InvoiceItem prepareInvoiceItemToAdd() {
        int productId = getProductIdToAddInvoiceItem();
        int invoiceId = getInvoiceIdToAddInvoiceItem();
        int quantity = getProductQuantityToAddInvoiceItem();
        String productName = getProductNameToAddInvoiceItem();
        BigDecimal netPrice = getNetPriceToAddInvoiceItem();
        BigDecimal taxPercent = getTaxPercentToAddInvoiceItem();
        BigDecimal grossPrice = getGrossPrice(netPrice, taxPercent, quantity);
        return new InvoiceItem(productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }


    //    public InvoiceItem editInvoiceItem() {
    public int getIdToEditInvoiceItem() {
        System.out.println("Enter id from invoice item what you want to edit from the list above:");
        int id = 0;
        boolean helpPoint = false;
        do {
            helpPoint = false;
            try {
                id = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("It's not a a number ! Enter correct id invoice item number:");
                helpPoint = true;
            }
            scanner.nextLine();
        } while (helpPoint == true);
        return id;
    }

    public int getProductIdToEditInvoiceItem() {
        System.out.println("Enter product id who you want to edit from the list above:");
        int productId = 0;
        boolean helpPoint2 = false;
        do {
            helpPoint2 = false;
            try {
                productId = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("It's not a number! Enter correct product id number :");
                helpPoint2 = true;
            }
            scanner.nextLine();
        } while (helpPoint2 == true);
        return productId;
    }

    public int getInvoiceIdToEditInvoiceItem() {
        System.out.println("Enter invoice id who you want to edit from the list above:");
        int invoiceId = 0;
        boolean helpPoint3 = false;
        do {
            helpPoint3 = false;
            try {
                invoiceId = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("It's not a number! Enter correct invoice id number :");
                helpPoint3 = true;
            }
            scanner.nextLine();
        } while (helpPoint3 == true);
        return invoiceId;
    }

    public int getQuantityToEditInvoiceItem() {
        System.out.println("Enter quantity :");
        int quantity = 0;
        boolean helpPoint4 = false;
        do {
            helpPoint4 = false;
            try {
                quantity = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("It's not a number! Enter correct quantity number :");
                helpPoint4 = true;
            }
            scanner.nextLine();
        } while (helpPoint4 == true);
        return quantity;
    }

    public String getProductNameToEditInvoiceItem() {
        System.out.println("Enter product name :");
        String productName = scanner.nextLine();
        return productName;
    }

    public BigDecimal getNetPriceToEditInvoiceItem() {
        System.out.println("Enter net price :");
        BigDecimal netPrice = null;
        boolean helpPoint5 = false;
        do {
            helpPoint5 = false;
            try {
                netPrice = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It's not a number! Enter correct net price:");
                helpPoint5 = true;
            }
            scanner.nextLine();
        } while (helpPoint5 == true);
        return netPrice;
    }

    public BigDecimal getTaxPercentToEditInvoiceItem() {
        System.out.println("Enter tax percent :");
        BigDecimal taxPercent = null;
        boolean helpPoint6 = false;
        do {
            helpPoint6 = false;
            try {
                taxPercent = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It's not a number! Enter correct tax percent:");
                helpPoint6 = true;
            }
            scanner.nextLine();
        } while (helpPoint6 == true);
        return taxPercent;
    }

    public BigDecimal getGrossPriceToEditInvoiceItem(BigDecimal netPrice, BigDecimal taxPercent, int quantity) {
        BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice)
                .multiply(BigDecimal.valueOf(quantity));
        return grossPrice;
    }

    public InvoiceItem preparedToEditInvoiceItem() {
        int id = getIdToEditInvoiceItem();
        int productId = getProductIdToEditInvoiceItem();
        int invoiceId = getInvoiceIdToEditInvoiceItem();
        int quantity = getQuantityToEditInvoiceItem();
        String productName = getProductNameToEditInvoiceItem();
        BigDecimal netPrice = getNetPriceToEditInvoiceItem();
        BigDecimal taxPercent = getTaxPercentToEditInvoiceItem();
        BigDecimal grossPrice = getGrossPriceToEditInvoiceItem(netPrice, taxPercent, quantity);

        return new InvoiceItem(id, productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }

    // TODO jak obzłużyć jeśli baza danych jest pusta zeby nie dalo się takiej wybrać do wpisania ?
    public InvoiceItem deleteInvoiceItem() {
        int id = 0;
        if (emptyDatabase == false) {
            System.out.println("Enter the invoice item id number to be removed from the database:");
            boolean helpPoint = false;
            do {
                helpPoint = false;
                try {
                    id = scanner.nextInt();
                } catch (Exception exception) {
                    System.out.println("It's not a correct number! Enter correct invoice item number:");
                    helpPoint = true;
                }
                scanner.nextLine();
            } while (helpPoint == true);
        }
        return new InvoiceItem(id, null, null, null, null, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
// IO input/output klasa do wejscia i wyjscia ma gadac z uzytkownikiem

