package org.example.homeassigmentdblocal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageSelectionApp extends Application {
    private TextField firstNameField, lastNameField, emailField;
    private Label firstNameLabel, lastNameLabel, emailLabel, selectLanguageLabel;
    private Button saveButton;
    private ComboBox<String> languageSelector;
    private ResourceBundle messages;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management");

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll("English", "Farsi", "Japanese");
        languageSelector.setValue("English");
        languageSelector.setOnAction(e -> updateLabels());

        selectLanguageLabel = new Label();
        firstNameLabel = new Label();
        firstNameField = new TextField();

        lastNameLabel = new Label();
        lastNameField = new TextField();

        emailLabel = new Label();
        emailField = new TextField();

        saveButton = new Button();
        saveButton.setOnAction(e -> saveEmployee());

        grid.add(selectLanguageLabel, 0, 0);
        grid.add(languageSelector, 1, 0);

        grid.add(firstNameLabel, 0, 1);
        grid.add(firstNameField, 1, 1);

        grid.add(lastNameLabel, 0, 2);
        grid.add(lastNameField, 1, 2);

        grid.add(emailLabel, 0, 3);
        grid.add(emailField, 1, 3);

        grid.add(saveButton, 1, 4);

        updateLabels(); // Initialize labels

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateLabels() {
        String language = languageSelector.getValue();
        Locale locale;

        switch (language) {
            case "Farsi":
                locale = new Locale("fa");
                break;
            case "Japanese":
                locale = new Locale("ja");
                break;
            default:
                locale = new Locale("en");
                break;
        }

        messages = ResourceBundle.getBundle("Messages", locale);

        selectLanguageLabel.setText(messages.getString("selectLanguage"));
        firstNameLabel.setText(messages.getString("firstName"));
        lastNameLabel.setText(messages.getString("lastName"));
        emailLabel.setText(messages.getString("email"));
        saveButton.setText(messages.getString("save"));
    }

    private void saveEmployee() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();

        String language = languageSelector.getValue();
        String tableName;

        // Determine the table based on the selected language
        switch (language) {
            case "Farsi":
                tableName = "employee_fa";
                break;
            case "Japanese":
                tableName = "employee_ja";
                break;
            default:
                tableName = "employee_en";
                break;
        }

        // SQL insert statement
        String sql = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for SQL statement
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);

            // Execute the insert
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, messages.getString("save") + " successful!");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save employee: " + e.getMessage());
            alert.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
