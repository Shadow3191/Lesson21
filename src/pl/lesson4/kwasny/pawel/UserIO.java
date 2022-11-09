package pl.lesson4.kwasny.pawel;

import pl.lesson4.kwasny.pawel.customer.Customer;
import pl.lesson4.kwasny.pawel.customer.CustomerDao;
import pl.lesson4.kwasny.pawel.customer.CustomerService;
import pl.lesson4.kwasny.pawel.invoice.Invoice;
import pl.lesson4.kwasny.pawel.invoice.InvoiceService;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItem;
import pl.lesson4.kwasny.pawel.product.Product;
import pl.lesson4.kwasny.pawel.product.ProductService;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserIO {
    private Scanner scanner = new Scanner(System.in);
    private Pattern nipNumberPattern = Pattern.compile("^[1-9]\\d{2}-\\d{2}-\\d{2}-\\d{3}$");
    private Pattern namePattern = Pattern.compile("^[A-Z][a-z]*");
    private Pattern correctEanPattern = Pattern.compile("^\\d{13}$");


    public void showCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.format("%3s| %20s| %14s|", customer.getId(), customer.getName(), customer.getNipNumber());
            System.out.println();
        }
        System.out.println();
    }

    // TODO dodać takie w innych klasach
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

    public Customer preparedCustomerToAdd() {
        String name = getTheCustomerNameToAdd();
        String nipNumber = getTheCustomerNipNumberToAdd();
        return new Customer(name, nipNumber);
    }

// na tej zasadzie wszystko ma byc zrobione NIE PRZEKAZUJEMY TU ZADNYCH SERWISÓW !!!!
    // jak chcesz wywolac metode to przed zmiennymi dodajesz typy parametrow, podajesz je tylko przy nagłówku metody
    // w lini 86 w naglowku metody mam typy a pozniej w kodzie podaje juz jedynie zmienne tych typów
    public int getIdToEditCustomer() {
        int customerIdToEdit;
        System.out.println("Enter the customer id number to edit :");
        try {
            customerIdToEdit = scanner.nextInt();
        } catch (InputMismatchException ex) {
              System.out.println("You must enter id number :");
              scanner.nextLine();
              customerIdToEdit = scanner.nextInt();
            return customerIdToEdit;
        }
        return customerIdToEdit;
    }

// lepiej zrobic zpaytanie
//    public int getCustomerIdToCheck(List<Customer> customers, int customerIdToEdit) {
//        int checkedId = 0;
//        try {
//            for (Customer idFromList : customers) {
//                checkedId = idFromList.getId();
//                if (checkedId == customerIdToEdit) {
//                    checkedId = customerIdToEdit;
//                    break;
//                } else {
//                    checkedId = 0;
//                }
//            }
//        } catch (Exception exception) {
//            System.out.println("Something went wrong.");
//        }
//        if (checkedId == 0) {
//            System.out.println("There is no such ID in the database.");
//        }
//        return checkedId;
//    }

    public String getNameToEditCustomer() {
        System.out.println("Enter name :");
        scanner.nextLine();
        String name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }
        Boolean correctName = isCorrectValue(name, namePattern);
        while (!correctName) {
            System.out.println("You must enter the first name, which will be in capital letters");
            System.out.println("Enter name :");
            name = scanner.nextLine();
            correctName = isCorrectValue(name, namePattern);
        }
        return name;
    }

    public String getNipToEditCustomer() {
        System.out.println("Enter nip number in configuration 3-2-2-3 :");
        String nipNumber = scanner.nextLine();
        Boolean correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
        do {
            System.out.println("You must write here nip number ! \nEnter nip number in configuration 3-2-2-3 :");
            nipNumber = scanner.nextLine();
            correctNipNumber = isCorrectValue(nipNumber, nipNumberPattern);
        } while (!correctNipNumber);
        return nipNumber;
    }

    private boolean isCorrectValue(String nipNumber, Pattern nipNumberPattern) {
        return nipNumberPattern.matcher(nipNumber).matches();
    }

    public Customer prepareCustomerToEdit() {
        int id = getIdToEditCustomer(); // jak tu przekazac wartosc z tej metody zeby nie dublowac zapytania ?!
        String name = getNameToEditCustomer();
        String nipNumber = getNipToEditCustomer();
        return new Customer(id, name, nipNumber);
    }

    int customerIdToDelete;

    public Customer deleteCustomer(CustomerService customerService) {
        do {
            System.out.println("Enter customer id number to delete:");
            customerIdToDelete = scanner.nextInt();
        } while (checkCustomerIdToDelete(customerService) == 0);
        int id = customerIdToDelete;
        return new Customer(id, null, null);
    }

    public int checkCustomerIdToDelete(CustomerService customerService) {
        int checkedIdToDelete = 0;
        try {
            for (Customer customerId : customerService.find()) {
                checkedIdToDelete = customerId.getId();
                if (checkedIdToDelete == customerIdToDelete) {
                    checkedIdToDelete = customerIdToDelete;
                    break;
                } else {
                    checkedIdToDelete = 0;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        if (checkedIdToDelete == 0) {
            System.out.println("There is no such ID in the database.");
        }
        return checkedIdToDelete;
    }

    //TODO poprawić cały kod w product !
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

    String eanCode;

    public String getEanToAddProduct(ProductService productService) {
        do {
            System.out.println("Enter the 13-digit EAN code :");
            eanCode = scanner.nextLine();
            boolean correctEanCode = isCorrectEanValue(eanCode, correctEanPattern);
            do {
                if (!correctEanCode) {
                    System.out.println("You must enter 13-digit code !");
                    eanCode = scanner.nextLine();
                    correctEanCode = isCorrectEanValue(eanCode, correctEanPattern);
                }
            } while (correctEanCode == false);
        } while (checkEan(productService) == null);
        return eanCode;
    }

    public String checkEan(ProductService productService) {
        String existInBase = null;
        try {
            for (Product eanFromList : productService.find()) {
                existInBase = eanFromList.getEanCode();
                if (eanCode.equals(existInBase)) {
                    System.out.println("This EAN number already exist in base.");
                    existInBase = null;
                    break;
                } else {
                    existInBase = eanCode;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong!");
        }
        return existInBase;
    }


    String name;

    public String getProductNameToAdd(ProductService productService) {
        do {
            System.out.println("Enter name of product :");
            name = scanner.nextLine();
            while (name.length() == 0) {
                System.out.println("Name can't be null, wright product name :");
                name = scanner.nextLine();
            }
        } while (checkTheName(productService) == null);
        return name;
    }

    public String checkTheName(ProductService productService) {
        String chelpPoint = null;
        try {
            for (Product checkedName : productService.find()) {
                String existInBase = checkedName.getName().toLowerCase();
                if (name.toLowerCase().equals(existInBase)) {
                    System.out.println("This name of the product already exist in the base.");
                    chelpPoint = null;
                    break;
                } else {
                    chelpPoint = name;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        return chelpPoint;
    }

    public BigDecimal getProductNetPriceToAdd() {
        BigDecimal netPrice = null;
        int helpPoint = 0;
        System.out.println("Enter product price :");
        while (helpPoint != 1) {
            helpPoint = 1;
            try {
                netPrice = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It isn't a price ! Enter correct price :");
                helpPoint = 0;
                scanner.nextLine();
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

    public Product prepareProductToAdd(ProductService productService) {
        String eanCode = getEanToAddProduct(productService);
        String name = getProductNameToAdd(productService);
        BigDecimal netPrice = getProductNetPriceToAdd();
        BigDecimal taxPercent = getTaxPercentToAdd();
        return new Product(eanCode, name, netPrice, taxPercent);
    }

    private boolean isCorrectEanValue(String eanCode, Pattern correctEanPattern) {
        return correctEanPattern.matcher(eanCode).matches();
    }

    int productIdToEdit;

    public int getIdToEditProduct(ProductService productService) {
        do {
            System.out.println("Enter the id number you want to edit :");
            try {
                productIdToEdit = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter id number :");
                scanner.nextLine();
                productIdToEdit = scanner.nextInt();
            }
        } while (checkedProductId(productService) == 0);
        return productIdToEdit;
    }

    public int checkedProductId(ProductService productService) {
        int checkedId = 0;
        try {
            for (Product idFromList : productService.find()) {
                checkedId = idFromList.getId();
                if (checkedId == productIdToEdit) {
                    checkedId = productIdToEdit;
                    break;
                } else {
                    checkedId = 0;
                }
            }
            if (checkedId == 0) {
                System.out.println("There is no such ID in the database.");
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        return checkedId;
    }


    public String getEanToEditProduct() {
        scanner.nextLine();
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

    String productNameToEdit;

    public String getProductNameToEdit(ProductService productService) {
        do {
            System.out.println("Enter name of product :");
            productNameToEdit = scanner.nextLine();
            while (productNameToEdit.length() == 0) {
                System.out.println("Name can't be null, wright product name :");
                productNameToEdit = scanner.nextLine();
            }
        } while (checkProductNameToEdit(productService) == null);
        return productNameToEdit;
    }

    public String checkProductNameToEdit(ProductService productService) {
        String chelpPoint = null;
        try {
            for (Product checkedName : productService.find()) {
                String existInBase = checkedName.getName().toLowerCase();

                if (productNameToEdit.toLowerCase().equals(existInBase)) {
                    if (productIdToEdit == checkedName.getId()) {
                        System.out.println("You have changed the product name as it was before.");
                        chelpPoint = productNameToEdit;
                    } else {
                        System.out.println("This name of the product already exist in the base.");
                        chelpPoint = null;
                        break;
                    }
                } else {
                    chelpPoint = productNameToEdit;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        return chelpPoint;
    }

    BigDecimal netPrice = null;

    public BigDecimal getProductNetPriceToEdit() {
        int helpPoint1 = 0;
        System.out.println("Enter net price :");
        while (helpPoint1 != 1) {
            helpPoint1 = 1;
            try {
                netPrice = scanner.nextBigDecimal();
            } catch (Exception exception) {
                System.out.println("It isn't a price ! Enter correct price :");
                helpPoint1 = 0;
                scanner.nextLine();
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

    public Product prepareProductToEdit(ProductService productService) {
        int id = getIdToEditProduct(productService);
        String eanCode = getEanToEditProduct();
        String name = getProductNameToEdit(productService);
        BigDecimal netPrice = getProductNetPriceToEdit();
        BigDecimal taxPercent = getProductTaxPercentToEdit();
        return new Product(id, eanCode, name, netPrice, taxPercent);
    }

    int productIdToDelete;

    public Product deleteProduct(ProductService productService) {
        do {
            System.out.println("Enter product id number to delete:");
            productIdToDelete = scanner.nextInt();
        } while (checkProductIdToDelete(productService) == 0);
        int id = productIdToDelete;
        return new Product(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public int checkProductIdToDelete(ProductService productService) {
        int checkedIdToDelete = 0;
        try {
            for (Product productId : productService.find()) {
                checkedIdToDelete = productId.getId();
                if (checkedIdToDelete == productIdToDelete) {
                    checkedIdToDelete = productIdToDelete;
                    break;
                } else {
                    checkedIdToDelete = 0;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        if (checkedIdToDelete == 0) {
            System.out.println("There is no such ID in the database.");
        }
        return checkedIdToDelete;
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

    public String getInvoiceNumberToAdd() {
        System.out.println("Enter invoice number :");
        String number = scanner.nextLine();
        return number;
    }

    public int getCustomerIdToAddInvoice() {
        System.out.println("Enter the customer id from the list above :");
        int customerId = scanner.nextInt();
        return customerId;
    }

    public Invoice prepareInvoiceToAdd() {
        String number = getInvoiceNumberToAdd();
        int customerId = getCustomerIdToAddInvoice();
        return new Invoice(number, customerId, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    int invoiceIdToEdit;

    public int getInvoiceIdToEdit(InvoiceService invoiceService) {
        do {
            System.out.println("Enter id number of invoice who you want to edit :");
            try {
                invoiceIdToEdit = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter id number :");
                scanner.nextLine();
                invoiceIdToEdit = scanner.nextInt();
            }
        } while (checkInvoiceIdToEdit(invoiceService) == 0);
        return invoiceIdToEdit;
    }

    public int checkInvoiceIdToEdit(InvoiceService invoiceService) {
        int checkedId = 0;
        try {
            for (Invoice idFromList : invoiceService.find()) {
                checkedId = idFromList.getId();
                if (checkedId == invoiceIdToEdit) {
                    checkedId = invoiceIdToEdit;
                    break;
                } else {
                    checkedId = 0;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        if (checkedId == 0) {
            System.out.println("There is no such ID in the database.");
        }
        return checkedId;
    }

    String invoiceNumberToEdit;

    public String getInvoiceNumberToEdit(InvoiceService invoiceService) {
        scanner.nextLine();
        do {
            System.out.println("Enter the invoice number :");
            try {
                invoiceNumberToEdit = scanner.nextLine();
            } catch (Exception exception) {
                System.out.println("Something went wrong.");
            }
        } while (checkInvoiceNumberToEdit(invoiceService) == null);
        return invoiceNumberToEdit;
    }

    public String checkInvoiceNumberToEdit(InvoiceService invoiceService) {
        String checkedId = null;
        try {
            for (Invoice idFromList : invoiceService.find()) {
                String existInBase = idFromList.getNumber().toLowerCase();
                if (invoiceNumberToEdit.toLowerCase().equals(existInBase)) {
                    if (invoiceIdToEdit == idFromList.getId()) {
                        System.out.println("You have changed the invoice number as it was before.");
                        checkedId = invoiceNumberToEdit;
                        break;
                    } else {
                        System.out.println("This number of the invoice already exist in the base.");
                        checkedId = null;
                        break;
                    }
                } else {
                    checkedId = invoiceNumberToEdit;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        return checkedId;
    }

    int customerId;

    public int getCustomerIdToInvoiceEdit(CustomerService customerService) {
        do {
            System.out.println("Enter customer id edit invoice :");
            try {
                customerId = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("You must enter id number :");
                scanner.nextLine();
                customerId = scanner.nextInt();
            }
        } while (checkedCustomerId(customerService) == 0);
        return customerId;
    }

    public int checkedCustomerId(CustomerService customerService) {
        int existInBase = 0;
        try {
            for (Customer idFromList : customerService.find()) {
                existInBase = idFromList.getId();
                if (existInBase == customerId) {
                    if (idFromList.getId() == existInBase)
                        existInBase = customerId;
                    break;
                } else {
                    existInBase = 0;
                }
            }
            if (existInBase == 0) {
                System.out.println("There is no such ID in the database.");
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        return existInBase;
    }

    public Invoice prepareInvoiceToEdit(InvoiceService invoiceService, CustomerService customerService) {
        int id = getInvoiceIdToEdit(invoiceService);
        String number = getInvoiceNumberToEdit(invoiceService);
        int customerId = getCustomerIdToInvoiceEdit(customerService);
        return new Invoice(id, number, customerId, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    int invoiceIdToDelete;

    public Invoice deleteInvoice(InvoiceService invoiceService) {
        do {
            System.out.println("Enter invoice id to delete:");
            invoiceIdToDelete = scanner.nextInt();
        } while (checkInvoiceIdToDelete(invoiceService) == 0);
        int id = invoiceIdToDelete;
        return new Invoice(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public int checkInvoiceIdToDelete(InvoiceService invoiceService) {
        int checkedIdToDelete = 0;
        try {
            for (Invoice invoiceId : invoiceService.find()) {
                checkedIdToDelete = invoiceId.getId();
                if (checkedIdToDelete == invoiceIdToDelete) {
                    checkedIdToDelete = invoiceIdToDelete;
                    break;
                } else {
                    checkedIdToDelete = 0;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        if (checkedIdToDelete == 0) {
            System.out.println("There is no such ID in the database.");
        }
        return checkedIdToDelete;
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

    int productIdNumber;

    public int getProductIdToAddInvoiceItem(ProductService productService) {
        do {
            System.out.println("Enter invoice number:");
            productIdNumber = scanner.nextInt();
        } while (checkIdToAddInvoiceItem(productService) == 0);
        return productIdNumber;
    }

    public int checkIdToAddInvoiceItem(ProductService productService) {
        int checkId = 0;
        try {
            for (Product idFromList : productService.find()) {
                checkId = idFromList.getId();
                if (checkId == productIdNumber) {
                    checkId = productIdNumber;
                    break;
                } else {
                    checkId = 0;
                }
            }
        } catch (Exception exception) {
            System.out.println("Something went wrong.");
        }
        if (checkId == 0) {
            System.out.println("There is no such ID in the database.");
        }
        return checkId;
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

    public InvoiceItem prepareInvoiceItemToAdd(ProductService productService) {
        int productId = getProductIdToAddInvoiceItem(productService);
        int invoiceId = getInvoiceIdToAddInvoiceItem();
        int quantity = getProductQuantityToAddInvoiceItem();
        String productName = getProductNameToAddInvoiceItem();
        BigDecimal netPrice = getNetPriceToAddInvoiceItem();
        BigDecimal taxPercent = getTaxPercentToAddInvoiceItem();
        BigDecimal grossPrice = getGrossPrice(netPrice, taxPercent, quantity);
        return new InvoiceItem(productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }


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

