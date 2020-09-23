package sample;

import sample.datamodel.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextArea notesTextArea;

    public Contact processResults() {
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String phoneNumber = phoneNumberTextField.getText().trim();
        String notes = notesTextArea.getText().trim();
        return new Contact(firstName, lastName, phoneNumber, notes);

    }

    public void editContact(Contact contact) {
        firstNameTextField.setText(contact.getFirstName());
        lastNameTextField.setText(contact.getLastName());
        phoneNumberTextField.setText(contact.getPhoneNumber());
        notesTextArea.setText(contact.getNotes());
    }

    public void updateContact(Contact contact) {
        contact.setFirstName(firstNameTextField.getText());
        contact.setLastName(lastNameTextField.getText());
        contact.setPhoneNumber(phoneNumberTextField.getText());
        contact.setNotes(notesTextArea.getText());
    }
}
