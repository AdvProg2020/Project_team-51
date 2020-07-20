package message.Messages.ServerToClient;

import model.People.Customer;

public class GetCustomerMessage {

    private Customer customer;

    public GetCustomerMessage(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
