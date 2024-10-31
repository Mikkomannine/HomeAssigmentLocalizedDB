
## Features

- **Language Selection**: Users can choose between English, Farsi, and Japanese. UI labels update according to the selected language.
- **Employee Data Entry**: Users can enter the first name, last name, and email of an employee.
- **Database Integration**: The entered data is saved to a language-specific table in a MariaDB database.

## Classes and Key Methods

### `LanguageSelectionApp`

This is the main class that sets up the user interface and handles user interactions.

- **`start(Stage primaryStage)`**: Initializes and displays the JavaFX UI components, setting up the layout and controls for the language selector, data entry fields, and the save button.

- **`updateLabels()`**: Updates the text labels in the UI according to the selected language. Uses a `ResourceBundle` to load language-specific strings from `Messages_{language_code}.properties` files.

- **`saveEmployee()`**: Saves the entered employee data to the database in a language-specific table. The table name is chosen based on the selected language:
    - English entries are saved in `employee_en`
    - Farsi entries are saved in `employee_fa`
    - Japanese entries are saved in `employee_ja`

  The method builds an SQL `INSERT` statement and executes it to save the data. If the save is successful, an alert is shown to the user; otherwise, an error alert is displayed.

### `DatabaseConnection`

This helper class is used to establish a connection to the MariaDB database.

- **`getConnection()`**: Creates and returns a database connection using the `mariadb` JDBC driver and connection URL. This method should ensure the driver is loaded and throw an appropriate exception if a connection fails.
