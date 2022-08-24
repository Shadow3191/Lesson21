package pl.lesson4.kwasny.pawel;

import pl.lesson4.kwasny.pawel.customer.Customer;
import pl.lesson4.kwasny.pawel.invoice.Invoice;
import pl.lesson4.kwasny.pawel.invoiceItem.InvoiceItem;
import pl.lesson4.kwasny.pawel.product.Product;

import java.math.BigDecimal;
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
        if (customers != null && customers.isEmpty()) {
            System.out.println("This database is empty - don't have added any position\n");
        }
        System.out.println();
    }

    // TODO change all names to correct
    public Customer prepareCustomerToAdd() {
        System.out.println("Enter the name of customer :");
        String name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }
        System.out.println("Enter customer nip number in configuration 3-2-2-3 :");
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
        return new Customer(name, nipNumber);
    }

    public int editId() {
        boolean goNext;
        int id = 0;
        do {
            System.out.println("Enter the customer id number to edit :");
            goNext = true;
            try {
                id = scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("You must enter an integer.\n");
                goNext = false;
            }
            scanner.nextLine();
        } while (goNext != true);
        return id;
    }

    public String editName() {
        System.out.println("Enter name :");
        String name = scanner.nextLine();
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

    public String editNipNumber() {
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


    public Customer editCustomer() {
        int id = editId();
        String name = editName();
        String nipNumber = editNipNumber();
        return new Customer(id, name, nipNumber);
    }

    public Customer deleteCustomer() {
        System.out.println("Enter the customer id number to be removed from the database:");
        int id = scanner.nextInt();
        System.out.println("You delete id number : " + id);
        return new Customer(id, null, null);
    }

    public void showProduct(List<Product> products) {
        System.out.println("Products :");
        for (Product product : products) {
//            System.out.println(product.getId() + " | " + product.getEanCode() + " | " + product.getName() + " | " +
//                    product.getNetPrice() + " | " + product.getTaxPercent());
            // TODO DODANE FORMATOWANIE TEKSTU
            System.out.format("%3s| %13s| %19s| %6s| %6s|", product.getId(), product.getEanCode(), product.getName(),
                    product.getNetPrice(), product.getTaxPercent());
            System.out.println();
//
        }
        if (products != null && products.isEmpty()) {
            System.out.println("This database is empty - don't have added any position\n");
        }
        System.out.println();
    }

    public Product prepareProductToAdd() {
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

        System.out.println("Enter name of product :");
        String name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }


        BigDecimal netPrice = null;
        int helpPoint = 0;
        System.out.println("Enter product price :");
        while (helpPoint != 1) {
            helpPoint = 1;
            try {
                // TODO czy tutaj da się jakoś zabezpieczyć przed enterem przed brakiem wpisania ceny czy program nie pusci ?
                netPrice = scanner.nextBigDecimal();
//                if (netPrice == null) {
//                    System.out.println("Price can't be null! Write the price :");
//                    netPrice = scanner.nextBigDecimal();
//                }
            } catch (Exception exception) {
                System.out.println("It isn't a price ! Enter correct price :");
                helpPoint = 0;
                scanner.nextLine();
                // TODO JAK TUTAJ DODAć DODATKOWE ODBłUZENIE ZEBY POKAZYWALO ZE TEN TOWAR JUZ JEST I WPISUJEMY KOLEJNY RAZ NAZWE TOWARU ?
            }
//            catch (SQLException sqlException){
//                throw new DatabaseException(sqlException.getMessage(), sqlException);
//            }
        }


//        BigDecimal netPrice = scanner.nextBigDecimal();
//        Boolean correctNetPrice = isCorrectNetPrice(netPrice, correctNetPricePattern);
//        do {
//            if (!correctNetPrice) {
//                try {
//                    System.out.println("You must enter price !");
//                    BigDecimal netPrice = scanner.nextBigDecimal();
//                    correctNetPrice = isCorrectNetPrice(netPrice, correctNetPricePattern);
//                } catch (Exception exception) {
//                    System.out.println("It isn't a price ! Enter correct price.");
//                }
//            }
//        } while (!correctNetPrice);
//        do {
//            if(helpPoint != 1) {
//                helpPoint = 1;
//                System.out.println("Podaj poprawną cenę.");
////                netPrice = scanner.nextBigDecimal();
//                try {
////                    System.out.println("Podaj poprawną cenę.");
//                    netPrice = scanner.nextBigDecimal();
//                   scanner.nextBigDecimal();
//                } catch (Exception exception) {
//                    System.out.println("To nie jest cena głąbie");
//                    helpPoint = 0;
//                }
//            }
//       }while (helpPoint != 1);

//        System.out.println("Enter tax percent :");
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

        return new Product(eanCode, name, netPrice, taxPercent);
    }

    private boolean isCorrectEanValue(String eanCode, Pattern correctEanPattern) {
        return correctEanPattern.matcher(eanCode).matches();
    }

    // TODO dlaczego tutaj trzeba dać CharSequence przy BigDecimalu i czy da sie to patternem obskoczyć ?
    private boolean isCorrectNetPrice(BigDecimal netPrice, Pattern correctNetPrice) {
        return correctNetPrice.matcher((CharSequence) netPrice).matches();
    }

    public Product editProduct(List<Product> products) {
        showProduct(products);
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


        System.out.println("Enter name of product :");
        String name = scanner.nextLine();
        while (name.length() == 0) {
            System.out.println("Name can't be null, wright product name :");
            name = scanner.nextLine();
        }

//        System.out.println("Enter net price :");
        BigDecimal netPrice = null;
        int helpPoint1 = 0;
        System.out.println("Enter net price :");
        while (helpPoint1 != 1) {
            helpPoint1 = 1;
            try {
                // TODO czy tutaj da się jakoś zabezpieczyć przed enterem przed brakiem wpisania ceny czy program nie pusci ?
                netPrice = scanner.nextBigDecimal();
//                if (netPrice == null) {
//                    System.out.println("Price can't be null! Write the price :");
//                    netPrice = scanner.nextBigDecimal();
//                }
            } catch (Exception exception) {
                System.out.println("It isn't a price ! Enter correct price :");
                helpPoint1 = 0;
                scanner.nextLine();
                // TODO JAK TUTAJ DODAć DODATKOWE ODBłUZENIE ZEBY POKAZYWALO ZE TEN TOWAR JUZ JEST I WPISUJEMY KOLEJNY RAZ NAZWE TOWARU ?
            }
        }


//        System.out.println("Enter tax percent :");
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
        return new Product(id, eanCode, name, netPrice, taxPercent);
    }

    public Product deleteProduct() {
        int id = 0;
        int helpPoint = 0;
        while (helpPoint != 1) {
            System.out.println("Enter the product id number to be removed from the database: ");
            try {
                id = scanner.nextInt();
                helpPoint = 1;
            } catch (Exception exception) {
                System.out.println("Your value isn't a number !");
                helpPoint = 0;
            }
            scanner.nextLine();
        }
        System.out.println("You delete id number : " + id + "\n");
        return new Product(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }


    public void showInvoices(List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            System.out.format("%3s| %15s| %3s| %5s| %5s|", invoice.getId(), invoice.getNumber(), invoice.getCustomerID(), invoice.getPriceNetSum(), invoice.getPriceGossSum());
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

    public Invoice editInvoice() {
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

        System.out.println("Enter the invoice number :");
        scanner.nextLine();
        String number = scanner.nextLine();

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
        System.out.println();
        return new Invoice(id, number, customerId, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public Invoice deleteInvoice() {
        System.out.println("Enter the invoice id number to be removed from the database:");
        int id = scanner.nextInt();
        System.out.println("You delete id number : " + id);
        return new Invoice(id, null, null, BigDecimal.ZERO, BigDecimal.ZERO);
    }

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
        }
    }

    public InvoiceItem addInvoiceItem() {
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

        System.out.println("Enter the product name :");
        String productName = scanner.nextLine();

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

        BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice).multiply(BigDecimal.valueOf(quantity));
        return new InvoiceItem(productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }

    public InvoiceItem editInvoiceItem() {
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

        System.out.println("Enter quantity :");
        int quantity = 0;
        boolean helpPoint4 = false;
        do{
            helpPoint4 = false;
            try {
                quantity = scanner.nextInt();
            } catch (Exception exception) {
                System.out.println("It's not a number! Enter correct quantity number :");
                helpPoint4 = true;
            }
            scanner.nextLine();
        } while (helpPoint4 == true);

        System.out.println("Enter product name :");
        String productName = scanner.nextLine();

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
        System.out.println();
        BigDecimal grossPrice = netPrice.multiply(taxPercent).divide(BigDecimal.valueOf(100)).add(netPrice).multiply(BigDecimal.valueOf(quantity));
        return new InvoiceItem(id, productId, invoiceId, quantity, productName, netPrice, taxPercent, grossPrice);
    }

    public InvoiceItem deleteInvoiceItem() {
        System.out.println("Enter the invoice item id number to be removed from the database:");
        int id = scanner.nextInt();
        System.out.println("You delete id nr : " + id);
        return new InvoiceItem(id, null, null, null, null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
// IO input/output klasa do wejscia i wyjscia ma gadac z uzytkownikiem
// TODO PRODUCK I CUSTOMER ZROBIONE POPRAWNIE (MOZNA SPRAWDZIC JESZCZE CO W PRODUCT DA SIE ZROBIC LEPIEJ Z OBSLUGA NIPU)
