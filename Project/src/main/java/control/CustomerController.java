package control;

import control.Exceptions.InsufficientBalanceException;
import control.Exceptions.InvalidProductIdException;
import control.Exceptions.WrongFormatException;
import model.ItemOfOrder;
import model.OffCode;
import model.OrderLog.Order;
import model.People.Account;
import model.People.Customer;
import model.Product;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerController extends Controller {

    public CustomerController(Account currentAccount) {
        super(currentAccount);
    }

    public static void editPhoneNumber(String phoneNumber) throws InstanceAlreadyExistsException, IllegalArgumentException, WrongFormatException {
        if (currentAccount.getPhoneNumber().equals(phoneNumber))
            throw new InstanceAlreadyExistsException();
        var phoneNumbers = getAllPhoneNumbers();
        if (phoneNumbers.contains(phoneNumber))
            throw new IllegalArgumentException();
        if (phoneNumber.length() != 11 || !phoneNumber.startsWith("09"))
            throw new WrongFormatException("");
        currentAccount.setPhoneNumber(phoneNumber);
    }

    private static List<String> getAllPhoneNumbers() {
        return Account.getAllAccounts().stream().map(Account::getPhoneNumber).collect(Collectors.toList());
    }

    private static List<String> getAllEmails() {
        return Account.getAllAccounts().stream().map(Account::getEmail).collect(Collectors.toList());
    }

    public List<Order> getOrders() {
        return ((Customer) currentAccount).getHistoryOfOrders();
    }

    public List<OffCode> viewDiscountCodes() {
        var customer = ((Customer) currentAccount);
        return OffCode.getAllOffCodes().stream()
                .filter(a -> a.getAppliedAccounts().contains(customer))
                .collect(Collectors.toList());
    }

    public void editFirstName(String firstName) throws InstanceAlreadyExistsException {
        if (currentAccount.getFirstName().equals(firstName))
            throw new InstanceAlreadyExistsException();
        currentAccount.setFirstName(firstName);
    }

    public void editLastName(String lastName) throws InstanceAlreadyExistsException {
        if (currentAccount.getLastName().equals(lastName))
            throw new InstanceAlreadyExistsException();
        currentAccount.setLastName(lastName);
    }

    public void editEmail(String email) throws InstanceAlreadyExistsException {
        if (currentAccount.getEmail().equals(email))
            throw new InstanceAlreadyExistsException();
        var emails = getAllEmails();
        if (emails.contains(email))
            throw new IllegalArgumentException();

        currentAccount.setEmail(email);
    }

    private ItemOfOrder getItemOfOrderByProduct(Product product) throws InvalidProductIdException {
        for (ItemOfOrder itemOfOrder : cart) {
            if (itemOfOrder.getProduct().equals(product))
                return itemOfOrder;
        }

        throw new InvalidProductIdException();
    }

    public String viewPersonalInfo() {
        Customer customer = (Customer) currentAccount;
        return customer.getUserName() + "\n" +
                "First Name : " + customer.getFirstName() + "\n" +
                "Last Name : " + customer.getLastName() + "\n" +
                "Email : " + customer.getEmail() + "\n" +
                "Phone : " + customer.getPhoneNumber() + "\n";
    }


    public void increaseProduct(Product product) throws InvalidProductIdException {
        getItemOfOrderByProduct(product).incrementQuantity();
    }

    public void decreaseProduct(Product product) throws InvalidProductIdException {
        getItemOfOrderByProduct(product).decrementQuantity();
    }

    public Double showTotalPrice() {
        return cart.stream().map(ItemOfOrder::getTotalPrice).reduce(0.00, (a, b) -> a + b);
    }

    public Double showTotalPriceWithDiscount() {
        return cart.stream().map(ItemOfOrder::getTotalPriceWithDiscount).reduce(0.00, (a, b) -> a + b);
    }

    public void purchase() throws InsufficientBalanceException {
        var customer = (Customer) currentAccount;
        if (showTotalPrice() <= viewBalance())
            customer.setBalance(viewBalance() - showTotalPrice());
        throw new InsufficientBalanceException();
    }

    public void emptyCard() {
        cart.clear();
    }

    public Double viewBalance() {
        return currentAccount.getBalance();
    }

    public void rateProduct(Product product, int rate) throws InvalidProductIdException {
        if (isBuyerOfProduct(product))
            throw new InvalidProductIdException();
        if (rate < 0 || rate > 5)
            throw new IllegalArgumentException();
        product.addRate(rate);
    }

    public void changePassword(String newPass) {
        currentAccount.setPassword(newPass);
    }

    private boolean isBuyerOfProduct(Product product) {
        List<Order> logs = ((Customer) currentAccount).getHistoryOfOrders();
        return logs.stream()
                .flatMap(a -> a.getItems().stream())
                .map(b -> b.getProduct())
                .anyMatch(p -> p.equals(product));
    }

}
