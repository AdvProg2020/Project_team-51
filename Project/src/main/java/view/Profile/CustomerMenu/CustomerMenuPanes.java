package view.Profile.CustomerMenu;

import control.Controller;
import control.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OffCode;
import model.People.Customer;
import javafx.scene.control.TableView;
import java.util.List;

public class CustomerMenuPanes {
    CustomerController customerController = new CustomerController(Controller.getCurrentAccount());

    public TableView getCustomerDiscountCodes(Customer customer){
        List<OffCode> offCodes = customerController.viewDiscountCodes();
        TableView<OffCode> table = new TableView<>();
        ObservableList<OffCode> data
                = FXCollections.observableArrayList(
                offCodes);

        TableColumn productName = new TableColumn("code");
        productName.setCellValueFactory(new PropertyValueFactory<>("offCode"));

        TableColumn beginDate = new TableColumn("begin date");
        beginDate.setCellValueFactory(new PropertyValueFactory<>("beginDateString"));

        TableColumn endDate = new TableColumn("end date");
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDateString"));

        table.setItems(data);
        table.getColumns().addAll(productName , beginDate , endDate);
        return table;
    }

}
