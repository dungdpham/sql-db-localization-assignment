package com.example.homeassignment020424;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private Label languageLbl;
    @FXML
    private ComboBox languageSwitcher;

    @FXML
    private Label firstNameLbl;
    @FXML
    private Label lastNameLbl;
    @FXML
    private Label emailLbl;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;

    @FXML
    private Button saveBtn;
    @FXML
    private Label message;

    private ResourceBundle bundle;

    @FXML
    protected void initialize() {
        languageSwitcher.getSelectionModel().selectFirst();
        loadLanguage(languageSwitcher.getValue().toString());

        firstName.focusedProperty().addListener((observable, oldValue, newValue) -> message.setText(""));
        lastName.focusedProperty().addListener((observable, oldValue, newValue) -> message.setText(""));
        email.focusedProperty().addListener((observable, oldValue, newValue) -> message.setText(""));
    }

    @FXML
    protected void onLanguageSelection(ActionEvent actionEvent) {
        loadLanguage(languageSwitcher.getValue().toString());
    }

    @FXML
    protected void onSaveBtnClick(ActionEvent actionEvent) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/employee_management",
                    "dbuser", "password");

            String table;
            switch (languageSwitcher.getValue().toString()) {
                case "English":
                    table = "employee_en";
                    break;
                case "Persian":
                    table = "employee_fa";
                    break;
                case "Japanese":
                    table = "employee_ja";
                    break;
                default:
                    table = "employee_en";
            }

            String query = "INSERT INTO " + table + " (first_name, last_name, email) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, firstName.getText());
            statement.setString(2, lastName.getText());
            statement.setString(3, email.getText());
            statement.executeUpdate();

            message.setText(bundle.getString("message"));
            firstName.setText("");
            lastName.setText("");
            email.setText("");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadLanguage(String language) {
        switch (language) {
            case "English":
                bundle = ResourceBundle.getBundle("texts", new Locale("en", "UK"));
                break;
            case "Persian":
                bundle = ResourceBundle.getBundle("texts", new Locale("fa", "IR"));
                break;
            case "Japanese":
                bundle = ResourceBundle.getBundle("texts", new Locale("ja", "JP"));
                break;
        }

        firstNameLbl.setText(bundle.getString("label.firstName"));
        lastNameLbl.setText(bundle.getString("label.lastName"));
        emailLbl.setText(bundle.getString("label.email"));

        saveBtn.setText(bundle.getString("button.save"));
        message.setText("");
    }

}