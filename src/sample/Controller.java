package sample;

import javafx.scene.layout.BorderPane;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<Contact> contactTableView;

    private ContactData contactData;

    Dialog<ButtonType> dialog;


    public void initialize() {
        contactData = new ContactData();
        contactData.loadContacts();
        contactTableView.setItems(contactData.getContacts());
    }

    @FXML
    public void showNewContactDialog() {
        dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("New Contact");
        dialog.setHeaderText("Use This Dialog to Add New Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            DialogController dc = fxmlLoader.getController();
            Contact newContact = dc.processResults();
            contactData.addContact(newContact);
            contactData.saveContacts();
        }
    }

    @FXML
    public void showEditContactDialog() {
        Contact contact = contactTableView.getSelectionModel().getSelectedItem();
        dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        dialog.setHeaderText("Use this dialog to edit a contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));

        if(contact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setContentText("No contact was selected");
            alert.show();
            return;
        }

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        DialogController dc = fxmlLoader.getController();
        dc.editContact(contact);

        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            dc.updateContact(contact);
            contactData.saveContacts();
            contactTableView.refresh();
        }


    }

    @FXML
    public void handleDeleteContact() {
        Alert alert;
        Contact contact  = contactTableView.getSelectionModel().getSelectedItem();
        if(contact == null) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No contact selected");
            alert.show();
            return;
        }
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Contact");
        alert.setContentText("Are you sure you want to delete contact: " + contact.getFirstName());
        Optional<ButtonType> result = alert.showAndWait();


        if(result.isPresent() && result.get() == ButtonType.OK) {
            contactData.deleteContact(contact);
            contactData.saveContacts();
        } else  {
            System.out.println("delete cancelled");
        }
    }
}
