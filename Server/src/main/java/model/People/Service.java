package model.People;

public class Service extends Account {

    public Service(String username, String password, String firstName,
                   String lastName, String email, String phoneNumber) {
        super(username, password, firstName, lastName, 0.00, email, phoneNumber);
    }

}
