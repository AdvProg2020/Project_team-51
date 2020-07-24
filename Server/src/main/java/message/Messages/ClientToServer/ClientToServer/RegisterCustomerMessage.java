package message.Messages.ClientToServer.ClientToServer;

import model.People.Customer;

public class RegisterCustomerMessage {

    private Customer customer;

    public RegisterCustomerMessage(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
