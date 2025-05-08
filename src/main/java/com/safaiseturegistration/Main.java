package com.safaiseturegistration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    private BorderPane root;
    private final String primaryColor = "#4CAF50"; // Green
    private final String secondaryColor = "#2196F3"; // Blue
    private final String accentColor = "#FFC107"; // Amber
    private final String backgroundColor = "#f4f4f4";
    private final String textColor = "#333333";
    private final Font titleFont = Font.font("Segoe UI", FontWeight.BOLD, 18);
    private final Font labelFont = Font.font("Segoe UI", FontWeight.NORMAL, 14);
    private final Font buttonFont = Font.font("Segoe UI", FontWeight.MEDIUM, 14);

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.web(backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));

        // Left Navigation Bar
        VBox navBar = new VBox(15);
        navBar.setPadding(new Insets(20));
        navBar.setStyle("-fx-background-color: #e0e0e0; -fx-border-right: 1px solid #ccc;");
        navBar.setPrefWidth(180);

        Button registerUserBtn = createStyledButton("Register User");
        Button logGarbageBtn = createStyledButton("Log Garbage");
        Button dustbinsBtn = createStyledButton("Dustbins");
        Button wardsBtn = createStyledButton("Wards");
        Button cleanlinessReportsBtn = createStyledButton("Reports");
        Button adminButton = createStyledButton("Admin Panel", accentColor);
        Button cleanlinessVisualizationBtn = createStyledButton("Visualize Cleanliness");

        VBox.setVgrow(registerUserBtn, Priority.ALWAYS);
        VBox.setVgrow(logGarbageBtn, Priority.ALWAYS);
        VBox.setVgrow(dustbinsBtn, Priority.ALWAYS);
        VBox.setVgrow(wardsBtn, Priority.ALWAYS);
        VBox.setVgrow(cleanlinessReportsBtn, Priority.ALWAYS);
        VBox.setVgrow(adminButton, Priority.ALWAYS);
        VBox.setVgrow(cleanlinessVisualizationBtn, Priority.ALWAYS);

        navBar.getChildren().addAll(registerUserBtn, logGarbageBtn, dustbinsBtn, wardsBtn, cleanlinessReportsBtn,cleanlinessVisualizationBtn, adminButton);
        root.setLeft(navBar);

        // Default pane
        root.setCenter(createStyledPane(getRegisterUserPane(), "Register New User"));

        // Navigation logic
        registerUserBtn.setOnAction(e -> root.setCenter(createStyledPane(getRegisterUserPane(), "Register New User")));
        logGarbageBtn.setOnAction(e -> root.setCenter(createStyledPane(getGarbageLogPane(), "Log Garbage")));
        dustbinsBtn.setOnAction(e -> root.setCenter(createStyledPane(getDustbinsPane(), "Register Dustbin")));
        wardsBtn.setOnAction(e -> root.setCenter(createStyledPane(getWardsPane(), "Wards Management")));
        cleanlinessReportsBtn.setOnAction(e -> root.setCenter(createStyledPane(getCleanlinessReportPane(), "Submit Report")));
        adminButton.setOnAction(e -> openAdminDashboard());
        cleanlinessVisualizationBtn.setOnAction(e -> root.setCenter(createStyledPane(getCleanlinessVisualizationPane(), "Cleanliness Visualization")));
        
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SafaiSetu Dashboard");
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setFont(buttonFont);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px; -fx-cursor: hand;", primaryColor));
        button.setOnMouseEntered(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px; -fx-cursor: hand;", Color.web(primaryColor).darker())));
        button.setOnMouseExited(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px; -fx-cursor: hand;", primaryColor)));
        return button;
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setFont(buttonFont);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s; -fx-padding: 10px; -fx-cursor: hand;", color, textColor));
        button.setOnMouseEntered(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s; -fx-padding: 10px; -fx-cursor: hand;", Color.web(color).darker(), textColor)));
        button.setOnMouseExited(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s; -fx-padding: 10px; -fx-cursor: hand;", color, textColor)));
        return button;
    }

    private VBox createStyledPane(VBox content, String titleText) {
        Label title = new Label(titleText);
        title.setFont(titleFont);
        title.setTextFill(Color.web(textColor));
        VBox wrapper = new VBox(20, title, content);
        wrapper.setPadding(new Insets(20));
        return wrapper;
    }

    private GridPane createStyledGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);
        return grid;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(labelFont);
        label.setTextFill(Color.web(textColor));
        return label;
    }

    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle(String.format("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 8px;"));
        return textField;
    }

    private PasswordField createStyledPasswordField(String promptText) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        passwordField.setStyle(String.format("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 8px;"));
        return passwordField;
    }

    private <T> ComboBox<T> createStyledComboBox(String promptText) {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.setPromptText(promptText);
        comboBox.setStyle(String.format("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 8px;"));
        return comboBox;
    }

    private DatePicker createStyledDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle(String.format("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 8px;"));
        return datePicker;
    }

    private TextArea createStyledTextArea(String promptText, int rows) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(promptText);
        textArea.setPrefRowCount(rows);
        textArea.setStyle(String.format("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 8px;"));
        return textArea;
    }

    private CheckBox createStyledCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setFont(labelFont);
        checkBox.setTextFill(Color.web(textColor));
        return checkBox;
    }

    private Button createSubmitButton(String text) {
        Button submitButton = new Button(text);
        submitButton.setFont(buttonFont);
        submitButton.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5; -fx-cursor: hand;", secondaryColor));
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5; -fx-cursor: hand;", Color.web(secondaryColor).darker())));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5; -fx-cursor: hand;", secondaryColor)));
        return submitButton;
    }

    private VBox getRegisterUserPane() {
        GridPane grid = createStyledGrid();

        TextField nameField = createStyledTextField("Full Name");
        TextField emailField = createStyledTextField("example@domain.com");
        PasswordField passwordField = createStyledPasswordField("Enter Password");
        TextField phoneField = createStyledTextField("10-digit number");
        ComboBox<String> roleBox = createStyledComboBox("Select Role");
        roleBox.getItems().addAll("citizen", "admin", "worker");
        CheckBox isActiveBox = createStyledCheckBox("Is Active?");
        isActiveBox.setSelected(true);
        Button submitButton = createSubmitButton("Register");

        grid.add(createStyledLabel("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(createStyledLabel("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(createStyledLabel("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(createStyledLabel("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(createStyledLabel("Role:"), 0, 4);
        grid.add(roleBox, 1, 4);
        grid.add(isActiveBox, 1, 5);
        grid.add(submitButton, 1, 6);
        GridPane.setColumnSpan(submitButton, 2);
        grid.setAlignment(Pos.CENTER);

        submitButton.setOnAction(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO users (name, email, password, phone, role, is_active) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nameField.getText());
                stmt.setString(2, emailField.getText());
                stmt.setString(3, passwordField.getText());
                stmt.setString(4, phoneField.getText());
                stmt.setString(5, roleBox.getValue());
                stmt.setBoolean(6, isActiveBox.isSelected());

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "User successfully registered!");

                nameField.clear();
                emailField.clear();
                passwordField.clear();
                phoneField.clear();
                roleBox.setValue(null);
                isActiveBox.setSelected(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
            }
        });

        VBox wrapper = new VBox(20, grid);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }

    // private VBox getGarbageLogPane() {
    //     GridPane grid = createStyledGrid();

    //     TextField binIdField = createStyledTextField("Enter Bin ID");
    //     TextField workerIdField = createStyledTextField("Enter Worker ID");
    //     ComboBox<String> binStatusBox = createStyledComboBox("Select Bin Status");
    //     binStatusBox.getItems().addAll("Full", "Half", "Empty");
    //     TextArea notesArea = createStyledTextArea("Additional notes or comments...", 3);
    //     Button submitBtn = createSubmitButton("Submit Log");

    //     grid.add(createStyledLabel("Bin ID:"), 0, 0);
    //     grid.add(binIdField, 1, 0);
    //     grid.add(createStyledLabel("Worker ID:"), 0, 1);
    //     grid.add(workerIdField, 1, 1);
    //     grid.add(createStyledLabel("Bin Status:"), 0, 2);
    //     grid.add(binStatusBox, 1, 2);
    //     grid.add(createStyledLabel("Notes:"), 0, 3);
    //     grid.add(notesArea, 1, 3);
    //     grid.add(submitBtn, 1, 4);
    //     GridPane.setColumnSpan(submitBtn, 2);
    //     grid.setAlignment(Pos.CENTER);

    //     submitBtn.setOnAction(e -> {
    //         try (Connection conn = DatabaseConnection.getConnection()) {
    //             String sql = "INSERT INTO garbage_collection_logs (bin_id, collected_by_worker_id, bin_status, notes) VALUES (?, ?, ?, ?)";
    //             PreparedStatement stmt = conn.prepareStatement(sql);
    //             stmt.setInt(1, Integer.parseInt(binIdField.getText()));
    //             stmt.setInt(2, Integer.parseInt(workerIdField.getText()));
    //             stmt.setString(3, binStatusBox.getValue());
    //             stmt.setString(4, notesArea.getText());

    //             stmt.executeUpdate();
    //             showAlert(Alert.AlertType.INFORMATION, "Garbage log successfully submitted!");

    //             binIdField.clear();
    //             workerIdField.clear();
    //             binStatusBox.setValue(null);
    //             notesArea.clear();
    //         } catch (Exception ex) {
    //             ex.printStackTrace();
    //             showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
    //         }
    //     });

    //     VBox wrapper = new VBox(20, grid);
    //     wrapper.setAlignment(Pos.CENTER);
    //     return wrapper;
    // }
    
    private VBox getGarbageLogPane() {
        GridPane grid = createStyledGrid();
    
        ComboBox<Integer> binIdComboBox =  createStyledComboBox("Select Bin ID");
        Label loadingBinsLabel = createStyledLabel("Loading Bin IDs...");
        TextField workerIdField = createStyledTextField("Enter Worker ID");
        ComboBox<String> binStatusBox = createStyledComboBox("Select Bin Status");
        binStatusBox.getItems().addAll("Full", "Half", "Empty");
        TextArea notesArea = createStyledTextArea("Additional notes or comments...", 3);
        Button submitBtn = createSubmitButton("Submit Log");
    
        grid.add(createStyledLabel("Bin ID:"), 0, 0);
        grid.add(binIdComboBox, 1, 0);
        grid.add(loadingBinsLabel, 1, 0);
        grid.add(createStyledLabel("Worker ID:"), 0, 1);
        grid.add(workerIdField, 1, 1);
        grid.add(createStyledLabel("Bin Status:"), 0, 2);
        grid.add(binStatusBox, 1, 2);
        grid.add(createStyledLabel("Notes:"), 0, 3);
        grid.add(notesArea, 1, 3);
        grid.add(submitBtn, 1, 4);
        GridPane.setColumnSpan(submitBtn, 2);
        grid.setAlignment(Pos.CENTER);
    
        // Load Bin IDs from the database and populate the ComboBox
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT bin_id FROM dustbins";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
    
            ObservableList<Integer> binIds = FXCollections.observableArrayList();
            while (rs.next()) {
                binIds.add(rs.getInt("bin_id"));
            }
            binIdComboBox.setItems(binIds);
            loadingBinsLabel.setVisible(false);
            binIdComboBox.setVisible(true);
            if (binIds.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "No Bins are available. Please add a Dustbin first.");
                submitBtn.setDisable(true);
            }
    
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading Bin IDs: " + ex.getMessage());
            loadingBinsLabel.setText("Error loading Bin IDs...");
            binIdComboBox.setVisible(false);
        }
    
        submitBtn.setOnAction(e -> {
            if (binIdComboBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Please select a Bin ID");
                return;
            }
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO garbage_collection_logs (bin_id, collected_by_worker_id, bin_status, notes) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, binIdComboBox.getValue());
                stmt.setInt(2, Integer.parseInt(workerIdField.getText()));
                stmt.setString(3, binStatusBox.getValue());
                stmt.setString(4, notesArea.getText());
    
                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Garbage log successfully submitted!");
    
                binIdComboBox.setValue(null);
                workerIdField.clear();
                binStatusBox.setValue(null);
                notesArea.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
            }
        });
    
        VBox wrapper = new VBox(20, grid);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }
    
    
    private VBox getDustbinsPane() {
        GridPane grid = createStyledGrid();

        ComboBox<String> wardBox = createStyledComboBox("Select Ward");
        Label loadingWardsLabel = createStyledLabel("Loading wards...");
        TextField locationField = createStyledTextField("Location");
        ComboBox<String> binTypeBox = createStyledComboBox("Bin Type");
        binTypeBox.getItems().addAll("Wet", "Dry", "Mixed");
        TextField capacityField = createStyledTextField("Capacity (liters)");
        DatePicker lastCollectedDatePicker = createStyledDatePicker();
        Button submitBtn = createSubmitButton("Register Bin");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String wardQuery = "SELECT ward_id, name FROM wards WHERE deleted_at IS NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(wardQuery);

            while (rs.next()) {
                String wardIdAndName = rs.getInt("ward_id") + " - " + rs.getString("name");
                wardBox.getItems().add(wardIdAndName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            loadingWardsLabel.setText("Error loading wards: " + ex.getMessage());
        }

        grid.add(createStyledLabel("Ward:"), 0, 0);
        grid.add(wardBox, 1, 0);
        grid.add(createStyledLabel("Location:"), 0, 1);
        grid.add(locationField, 1, 1);
        grid.add(createStyledLabel("Bin Type:"), 0, 2);
        grid.add(binTypeBox, 1, 2);
        grid.add(createStyledLabel("Capacity:"), 0, 3);
        grid.add(capacityField, 1, 3);
        grid.add(createStyledLabel("Last Collected Date:"), 0, 4);
        grid.add(lastCollectedDatePicker, 1, 4);
        grid.add(submitBtn, 1, 5);
        GridPane.setColumnSpan(submitBtn, 2);
        grid.setAlignment(Pos.CENTER);

        submitBtn.setOnAction(e -> {
            if (wardBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Please select a ward");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                int wardId = Integer.parseInt(wardBox.getValue().split(" - ")[0]);

                String sql = "INSERT INTO dustbins (ward_id, location, bin_type, capacity, last_collected_date) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, wardId);
                stmt.setString(2, locationField.getText());
                stmt.setString(3, binTypeBox.getValue());
                stmt.setInt(4, Integer.parseInt(capacityField.getText()));

                if (lastCollectedDatePicker.getValue() != null) {
                    stmt.setDate(5, java.sql.Date.valueOf(lastCollectedDatePicker.getValue()));
                } else {
                    stmt.setNull(5, java.sql.Types.DATE);
                }

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Dustbin successfully registered!");

                wardBox.setValue(null);
                locationField.clear();
                binTypeBox.setValue(null);
                capacityField.clear();
                lastCollectedDatePicker.setValue(null);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
            }
        });

        VBox wrapper = new VBox(20, grid);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }

    private VBox getWardsPane() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));

        Label title = new Label("Wards Management");
        title.setFont(titleFont);
        title.setTextFill(Color.web(textColor));

        HBox tabButtons = new HBox(15);
        Button addWardBtn = createStyledButton("Add Ward", secondaryColor);
        Button viewWardsBtn = createStyledButton("View Wards", secondaryColor);
        tabButtons.setAlignment(Pos.CENTER_LEFT);

        VBox contentContainer = new VBox();
        contentContainer.getChildren().add(getAddWardForm());

        addWardBtn.setOnAction(e -> {
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(getAddWardForm());
            addWardBtn.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px; -fx-cursor: hand;", secondaryColor));
            viewWardsBtn.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px; -fx-cursor: hand;", Color.web(secondaryColor).darker()));
        });

        viewWardsBtn.setOnAction(e -> {
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(getViewWardsPane());
            viewWardsBtn.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px; -fx-cursor: hand;", secondaryColor));
            addWardBtn.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-padding: 10px; -fx-cursor: hand;", Color.web(secondaryColor).darker()));
        });

        mainContainer.getChildren().addAll(title, tabButtons, contentContainer);
        return mainContainer;
    }

    private GridPane getAddWardForm() {
        GridPane grid = createStyledGrid();

        TextField nameField = createStyledTextField("Ward Name");
        TextField zoneNumberField = createStyledTextField("Zone Number (optional)");
        TextField populationField = createStyledTextField("Population (optional)");
        Button submitButton = createSubmitButton("Add Ward");

        grid.add(createStyledLabel("Ward Name:*"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(createStyledLabel("Zone Number:"), 0, 1);
        grid.add(zoneNumberField, 1, 1);
        grid.add(createStyledLabel("Population:"), 0, 2);
        grid.add(populationField, 1, 2);
        grid.add(submitButton, 1, 3);
        GridPane.setColumnSpan(submitButton, 2);
        grid.setAlignment(Pos.CENTER);

        submitButton.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ward name is required");
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO wards (name, zone_number, population) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nameField.getText().trim());

                if (zoneNumberField.getText().trim().isEmpty()) {
                    stmt.setNull(2, java.sql.Types.INTEGER);
                } else {
                    try {
                        int zoneNumber = Integer.parseInt(zoneNumberField.getText().trim());
                        stmt.setInt(2, zoneNumber);
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Zone number must be a valid integer");
                        return;
                    }
                }

                if (populationField.getText().trim().isEmpty()) {
                    stmt.setNull(3, java.sql.Types.INTEGER);
                } else {
                    try {
                        int population = Integer.parseInt(populationField.getText().trim());
                        stmt.setInt(3, population);
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Population must be a valid integer");
                        return;
                    }
                }

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Ward successfully added!");

                nameField.clear();
                zoneNumberField.clear();
                populationField.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
            }
        });

        return grid;
    }

    private VBox getCleanlinessVisualizationPane() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);
    
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        Tab wardRatingsTab = new Tab("Ward Ratings");
        wardRatingsTab.setContent(createWardRatingsChart());
        
        Tab trendAnalysisTab = new Tab("Trend Analysis");
        trendAnalysisTab.setContent(createTrendAnalysisChart());
        
        Tab scatterPlotTab = new Tab("Scatter Analysis");
        scatterPlotTab.setContent(createScatterPlotChart());
        
        Tab advancedAnalysisTab = new Tab("Advanced Analysis");
        advancedAnalysisTab.setContent(createAdvancedScatterPlotChart());
        
        tabPane.getTabs().addAll(wardRatingsTab, trendAnalysisTab, scatterPlotTab, advancedAnalysisTab);
        
        mainContainer.getChildren().add(tabPane);
        return mainContainer;
    }

    private BorderPane createScatterPlotChart() {
    BorderPane chartContainer = new BorderPane();
    chartContainer.setPadding(new Insets(15));
    
    // Create axes for the scatter plot
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis(0, 5, 1);
    xAxis.setLabel("Population Density");
    yAxis.setLabel("Cleanliness Rating");
    
    // Create the scatter chart
    ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
    scatterChart.setTitle("Cleanliness Rating vs Population Density");
    
    // Create filter options
    HBox filterOptions = new HBox(15);
    filterOptions.setAlignment(Pos.CENTER_LEFT);
    filterOptions.setPadding(new Insets(0, 0, 10, 0));
    
    Label timeFilterLabel = new Label("Time Period:");
    ComboBox<String> timeFilterBox = createStyledComboBox("Select Period");
    timeFilterBox.getItems().addAll("Last Week", "Last Month", "Last 3 Months", "All Time");
    timeFilterBox.setValue("All Time");
    
    Button refreshButton = createStyledButton("Refresh Data", secondaryColor);
    
    filterOptions.getChildren().addAll(timeFilterLabel, timeFilterBox, refreshButton);
    
    // Function to load scatter plot data
    Runnable loadScatterData = () -> {
        scatterChart.getData().clear();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String timeFilter = "";
            if (timeFilterBox.getValue().equals("Last Week")) {
                timeFilter = " AND cr.created_at >= DATE_SUB(NOW(), INTERVAL 1 WEEK)";
            } else if (timeFilterBox.getValue().equals("Last Month")) {
                timeFilter = " AND cr.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH)";
            } else if (timeFilterBox.getValue().equals("Last 3 Months")) {
                timeFilter = " AND cr.created_at >= DATE_SUB(NOW(), INTERVAL 3 MONTH)";
            }
            
            // Query to get ward data with cleanliness ratings and population density
            String sql = "SELECT w.ward_id, w.name, w.population, " +
                         "COUNT(d.bin_id) AS bin_count, " +
                         "AVG(cr.rating) AS avg_rating " +
                         "FROM wards w " +
                         "LEFT JOIN dustbins d ON w.ward_id = d.ward_id " +
                         "LEFT JOIN cleanliness_reports cr ON w.ward_id = cr.ward_id " +
                         "WHERE w.deleted_at IS NULL " + timeFilter + " " +
                         "GROUP BY w.ward_id, w.name, w.population " +
                         "HAVING AVG(cr.rating) IS NOT NULL";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            // Create a series for the scatter plot
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Wards");
            
            while (rs.next()) {
                String wardName = rs.getString("name");
                int population = rs.getInt("population");
                int binCount = rs.getInt("bin_count");
                double avgRating = rs.getDouble("avg_rating");
                
                // Calculate population density (population per dustbin)
                double populationDensity = binCount > 0 ? population / (double)binCount : population;
                
                XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(populationDensity, avgRating);
                series.getData().add(dataPoint);
                
                // Add tooltip to show ward name on hover
                dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        Tooltip.install(newNode, new Tooltip("Ward: " + wardName));
                    }
                });
            }
            
            
            // Configure node appearance
            for (XYChart.Data<Number, Number> data : series.getData()) {
                // Make the scatter points show the ward name on hover
                javafx.scene.Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-background-color: " + primaryColor + "; -fx-background-radius: 5px;");
                    
                    // Create tooltip
                    Tooltip tooltip = new Tooltip();
                    tooltip.setText(data.getExtraValue() != null ? data.getExtraValue().toString() : "");
                    Tooltip.install(node, tooltip);
                }
            }
            
            scatterChart.getData().add(series);
            
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error loading data: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            chartContainer.setCenter(errorLabel);
        }
    };
    
    // Set up refresh action
    refreshButton.setOnAction(e -> loadScatterData.run());
    timeFilterBox.setOnAction(e -> loadScatterData.run());
    
    // Initial data load
    loadScatterData.run();
    
    // Add custom legend below the chart
    HBox legendBox = new HBox(20);
    legendBox.setPadding(new Insets(10, 0, 0, 0));
    legendBox.setAlignment(Pos.CENTER);
    
    Label legendInfoLabel = new Label("This chart shows the relationship between ward population density and cleanliness ratings");
    legendInfoLabel.setWrapText(true);
    legendInfoLabel.setStyle("-fx-font-style: italic;");
    
    legendBox.getChildren().add(legendInfoLabel);
    
    // Assemble the chart view
    VBox chartView = new VBox(10, filterOptions, scatterChart, legendBox);
    chartContainer.setCenter(chartView);
    
    return chartContainer;
}

private BorderPane createAdvancedScatterPlotChart() {
    BorderPane chartContainer = new BorderPane();
    chartContainer.setPadding(new Insets(15));
    
    // Create axes for the scatter plot - we'll update these based on user selection
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    
    // Create the scatter chart
    ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
    scatterChart.setTitle("Data Correlation Analysis");
    
    // Create filter options and axis selectors
    GridPane controlsGrid = new GridPane();
    controlsGrid.setHgap(15);
    controlsGrid.setVgap(10);
    controlsGrid.setPadding(new Insets(0, 0, 15, 0));
    controlsGrid.setAlignment(Pos.CENTER_LEFT);
    
    // X-axis selector
    Label xAxisLabel = new Label("X-Axis:");
    ComboBox<String> xAxisSelector = createStyledComboBox("Select X-Axis");
    xAxisSelector.getItems().addAll(
        "Population", 
        "Bin Count", 
        "Population Per Bin", 
        "Reports Count",
        "Average Collection Frequency"
    );
    xAxisSelector.setValue("Population");
    
    // Y-axis selector
    Label yAxisLabel = new Label("Y-Axis:");
    ComboBox<String> yAxisSelector = createStyledComboBox("Select Y-Axis");
    yAxisSelector.getItems().addAll(
        "Cleanliness Rating",
        "Reports Count", 
        "Bin Count", 
        "Collection Efficiency"
    );
    yAxisSelector.setValue("Cleanliness Rating");
    
    // Time filter
    Label timeFilterLabel = new Label("Time Period:");
    ComboBox<String> timeFilterBox = createStyledComboBox("Select Period");
    timeFilterBox.getItems().addAll("Last Week", "Last Month", "Last 3 Months", "All Time");
    timeFilterBox.setValue("All Time");
    
    Button refreshButton = createStyledButton("Generate Chart", secondaryColor);
    
    // Add controls to grid
    controlsGrid.add(xAxisLabel, 0, 0);
    controlsGrid.add(xAxisSelector, 1, 0);
    controlsGrid.add(yAxisLabel, 2, 0);
    controlsGrid.add(yAxisSelector, 3, 0);
    controlsGrid.add(timeFilterLabel, 0, 1);
    controlsGrid.add(timeFilterBox, 1, 1);
    controlsGrid.add(refreshButton, 3, 1);
    
    // Function to load scatter plot data
    Runnable loadScatterData = () -> {
        scatterChart.getData().clear();
        
        String xAxisMetric = xAxisSelector.getValue();
        String yAxisMetric = yAxisSelector.getValue();
        
        // Update chart title and axis labels
        scatterChart.setTitle(yAxisMetric + " vs " + xAxisMetric + " by Ward");
        xAxis.setLabel(xAxisMetric);
        yAxis.setLabel(yAxisMetric);
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Build time filter clause
            String timeFilter = "";
            if (timeFilterBox.getValue().equals("Last Week")) {
                timeFilter = " AND (cr.created_at >= DATE_SUB(NOW(), INTERVAL 1 WEEK) OR gcl.created_at >= DATE_SUB(NOW(), INTERVAL 1 WEEK))";
            } else if (timeFilterBox.getValue().equals("Last Month")) {
                timeFilter = " AND (cr.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH) OR gcl.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH))";
            } else if (timeFilterBox.getValue().equals("Last 3 Months")) {
                timeFilter = " AND (cr.created_at >= DATE_SUB(NOW(), INTERVAL 3 MONTH) OR gcl.created_at >= DATE_SUB(NOW(), INTERVAL 3 MONTH))";
            }
            
            // Complex query to get all the data we might need for different chart types
            String sql = "SELECT " +
                         "w.ward_id, w.name, w.population, " +
                         "COUNT(DISTINCT d.bin_id) AS bin_count, " +
                         "AVG(cr.rating) AS avg_rating, " +
                         "COUNT(DISTINCT cr.report_id) AS report_count, " +
                         "COUNT(DISTINCT gcl.log_id) AS collection_count, " +
                         "COUNT(DISTINCT gcl.log_id) / COUNT(DISTINCT d.bin_id) AS collection_per_bin " +
                         "FROM wards w " +
                         "LEFT JOIN dustbins d ON w.ward_id = d.ward_id " +
                         "LEFT JOIN cleanliness_reports cr ON w.ward_id = cr.ward_id " +
                         "LEFT JOIN garbage_collection_logs gcl ON d.bin_id = gcl.bin_id " +
                         "WHERE w.deleted_at IS NULL " + timeFilter + " " +
                         "GROUP BY w.ward_id, w.name, w.population";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            // Create a series for the scatter plot
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Wards");
            
            while (rs.next()) {
                String wardName = rs.getString("name");
                int population = rs.getInt("population");
                int binCount = rs.getInt("bin_count");
                double avgRating = rs.getDouble("avg_rating");
                int reportCount = rs.getInt("report_count");
                int collectionCount = rs.getInt("collection_count");
                double collectionPerBin = rs.getDouble("collection_per_bin");
                
                // Calculate population density (population per dustbin)
                double populationPerBin = binCount > 0 ? population / (double)binCount : 0;
                
                // Determine x and y values based on user selections
                double xValue = 0;
                switch(xAxisMetric) {
                    case "Population":
                        xValue = population;
                        break;
                    case "Bin Count":
                        xValue = binCount;
                        break;
                    case "Population Per Bin":
                        xValue = populationPerBin;
                        break;
                    case "Reports Count":
                        xValue = reportCount;
                        break;
                    case "Average Collection Frequency":
                        xValue = collectionPerBin;
                        break;
                }
                
                double yValue = 0;
                switch(yAxisMetric) {
                    case "Cleanliness Rating":
                        yValue = avgRating;
                        break;
                    case "Reports Count":
                        yValue = reportCount;
                        break;
                    case "Bin Count":
                        yValue = binCount;
                        break;
                    case "Collection Efficiency":
                        yValue = collectionCount;
                        break;
                }
                
                // Skip data points with zero values
                if (xValue > 0 && yValue > 0) {
                    XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xValue, yValue);
                    dataPoint.setExtraValue(wardName);
                    series.getData().add(dataPoint);
                }
            }
            
            scatterChart.getData().add(series);
            
            // Add tooltips to data points
            for (XYChart.Data<Number, Number> data : series.getData()) {
                String wardName = (String) data.getExtraValue();
                
                Tooltip tooltip = new Tooltip(
                    wardName + "\n" +
                    "X: " + data.getXValue() + " (" + xAxisMetric + ")\n" +
                    "Y: " + data.getYValue() + " (" + yAxisMetric + ")"
                );
                
                Tooltip.install(data.getNode(), tooltip);
                
                // Style the node
                data.getNode().setStyle(
                    "-fx-background-color: " + primaryColor + ";" +
                    "-fx-background-radius: 5px;"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error loading data: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            chartContainer.setCenter(errorLabel);
        }
    };
    
    // Set up refresh action
    refreshButton.setOnAction(e -> loadScatterData.run());
    
    // Assemble the chart view
    VBox chartView = new VBox(10, controlsGrid, scatterChart);
    chartContainer.setCenter(chartView);
    
    // Add explanatory text
    Label analysisLabel = new Label(
        "This interactive scatter plot allows you to explore correlations between different metrics " +
        "across wards. Select different variables for X and Y axes to discover relationships."
    );
    analysisLabel.setWrapText(true);
    analysisLabel.setPadding(new Insets(10, 0, 0, 0));
    analysisLabel.setStyle("-fx-font-style: italic;");
    
    chartView.getChildren().add(analysisLabel);
    
    // Initial data load
    loadScatterData.run();
    
    return chartContainer;
}
    
    private BorderPane createWardRatingsChart() {
        BorderPane chartContainer = new BorderPane();
        chartContainer.setPadding(new Insets(15));
        
        // Chart creation will go here, using JavaFX charts
        // This is a simple placeholder showing how to create a bar chart for ward ratings
        
        // Create axes and chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Wards");
        yAxis.setLabel("Average Rating");
        
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Average Cleanliness Ratings by Ward");
        
        // Load ward ratings data from database
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Average Rating");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT w.name, AVG(cr.rating) as avg_rating " +
                         "FROM cleanliness_reports cr " +
                         "JOIN wards w ON cr.ward_id = w.ward_id " +
                         "GROUP BY w.ward_id, w.name " +
                         "ORDER BY avg_rating DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String wardName = rs.getString("name");
                double avgRating = rs.getDouble("avg_rating");
                series.getData().add(new XYChart.Data<>(wardName, avgRating));
            }
            
            barChart.getData().add(series);
            
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Could not load ward ratings data: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            chartContainer.setCenter(errorLabel);
            return chartContainer;
        }
        
        // Add the chart to the container
        chartContainer.setCenter(barChart);
        
        // Add refresh button
        Button refreshButton = createStyledButton("Refresh Data", secondaryColor);
        refreshButton.setOnAction(e -> {
            chartContainer.setCenter(createWardRatingsChart().getCenter());
        });
        
        HBox buttonBar = new HBox(10, refreshButton);
        buttonBar.setPadding(new Insets(10, 0, 0, 0));
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        
        chartContainer.setBottom(buttonBar);
        
        return chartContainer;
    }
    
    private BorderPane createTrendAnalysisChart() {
        BorderPane chartContainer = new BorderPane();
        chartContainer.setPadding(new Insets(15));
        
        // Create a line chart to show trends in cleanliness ratings over time
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 5, 1);
        xAxis.setLabel("Date");
        yAxis.setLabel("Average Rating");
        
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Cleanliness Rating Trends Over Time");
        lineChart.setAnimated(false);  // Animation can cause issues with date axis
        
        // Create a combo box for ward selection
        ComboBox<String> wardSelector = createStyledComboBox("Select Ward");
        wardSelector.getItems().add("All Wards");  // Default option
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Populate ward selector
            String wardQuery = "SELECT ward_id, name FROM wards WHERE deleted_at IS NULL ORDER BY name";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(wardQuery);
            
            while (rs.next()) {
                String wardIdAndName = rs.getInt("ward_id") + " - " + rs.getString("name");
                wardSelector.getItems().add(wardIdAndName);
            }
            
            wardSelector.setValue("All Wards");  // Set default
            
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Could not load wards: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            chartContainer.setCenter(errorLabel);
            return chartContainer;
        }
        
        // Function to load chart data based on ward selection
        Runnable loadChartData = () -> {
            lineChart.getData().clear();
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql;
                PreparedStatement stmt;
                
                if (wardSelector.getValue().equals("All Wards")) {
                    sql = "SELECT DATE(created_at) as report_date, AVG(rating) as avg_rating " +
                          "FROM cleanliness_reports " +
                          "GROUP BY DATE(created_at) " +
                          "ORDER BY report_date";
                    stmt = conn.prepareStatement(sql);
                } else {
                    int wardId = Integer.parseInt(wardSelector.getValue().split(" - ")[0]);
                    sql = "SELECT DATE(created_at) as report_date, AVG(rating) as avg_rating " +
                          "FROM cleanliness_reports " +
                          "WHERE ward_id = ? " +
                          "GROUP BY DATE(created_at) " +
                          "ORDER BY report_date";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, wardId);
                }
                
                ResultSet rs = stmt.executeQuery();
                
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName(wardSelector.getValue());
                
                while (rs.next()) {
                    java.sql.Date reportDate = rs.getDate("report_date");
                    double avgRating = rs.getDouble("avg_rating");
                    series.getData().add(new XYChart.Data<>(reportDate.toString(), avgRating));
                }
                
                lineChart.getData().add(series);
                
            } catch (SQLException e) {
                e.printStackTrace();
                Label errorLabel = new Label("Error loading data: " + e.getMessage());
                errorLabel.setTextFill(Color.RED);
                chartContainer.setCenter(errorLabel);
            }
        };
        
        // Set up the UI
        wardSelector.setOnAction(e -> loadChartData.run());
        
        // Initial data load
        loadChartData.run();
        
        // Add refresh button
        Button refreshButton = createStyledButton("Refresh Data", secondaryColor);
        refreshButton.setOnAction(e -> loadChartData.run());
        
        HBox controlBar = new HBox(15, new Label("Filter:"), wardSelector, refreshButton);
        controlBar.setAlignment(Pos.CENTER_LEFT);
        controlBar.setPadding(new Insets(0, 0, 10, 0));
        
        // Assemble the chart view
        VBox chartView = new VBox(10, controlBar, lineChart);
        chartContainer.setCenter(chartView);
        
        return chartContainer;
    }
    

    private VBox getViewWardsPane() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        TableView<Ward> wardTable = new TableView<>();

        TableColumn<Ward, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("wardId"));
        idCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Ward, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);

        TableColumn<Ward, Integer> zoneCol = new TableColumn<>("Zone");
        zoneCol.setCellValueFactory(new PropertyValueFactory<>("zoneNumber"));
        zoneCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Ward, Integer> popCol = new TableColumn<>("Population");
        popCol.setCellValueFactory(new PropertyValueFactory<>("population"));
        popCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Ward, Timestamp> createdCol = new TableColumn<>("Created");
        createdCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        createdCol.setPrefWidth(150);

        wardTable.getColumns().addAll(idCol, nameCol, zoneCol, popCol, createdCol);
        wardTable.setStyle("-fx-border-color: #ccc; -fx-border-width: 1;");

        Button refreshBtn = createStyledButton("Refresh", secondaryColor);
        refreshBtn.setOnAction(e -> loadWardData(wardTable));

        loadWardData(wardTable);

        container.getChildren().addAll(refreshBtn, wardTable);
        return container;
    }

    private void loadWardData(TableView<Ward> table) {
        ObservableList<Ward> data = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM wards WHERE deleted_at IS NULL ORDER BY ward_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Ward ward = new Ward(
                    rs.getInt("ward_id"),
                    rs.getString("name"),
                    rs.getObject("zone_number") != null ? rs.getInt("zone_number") : null,
                    rs.getObject("population") != null ? rs.getInt("population") : null,
                    rs.getTimestamp("created_at")
                );
                data.add(ward);
            }

            table.setItems(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading wards: " + ex.getMessage());
        }
    }

    private VBox getCleanlinessReportPane() {
        GridPane grid = createStyledGrid();

        TextField userIdField = createStyledTextField("User ID");
        TextField wardIdField = createStyledTextField("Ward ID");
        TextField ratingField = createStyledTextField("Rating (1–5)");
        TextArea feedbackArea = createStyledTextArea("Feedback", 4);
        Button submitBtn = createSubmitButton("Submit Report");

        grid.add(createStyledLabel("User ID:"), 0, 0);
        grid.add(userIdField, 1, 0);
        grid.add(createStyledLabel("Ward ID:"), 0, 1);
        grid.add(wardIdField, 1, 1);
        grid.add(createStyledLabel("Rating:"), 0, 2);
        grid.add(ratingField, 1, 2);
        grid.add(createStyledLabel("Feedback:"), 0, 3);
        grid.add(feedbackArea, 1, 3);
        grid.add(submitBtn, 1, 4);
        GridPane.setColumnSpan(submitBtn, 2);
        grid.setAlignment(Pos.CENTER);

        submitBtn.setOnAction(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO cleanliness_reports (user_id, ward_id, rating, feedback) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(userIdField.getText()));
                stmt.setInt(2, Integer.parseInt(wardIdField.getText()));
                stmt.setInt(3, Integer.parseInt(ratingField.getText()));
                stmt.setString(4, feedbackArea.getText());

                stmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Cleanliness report submitted!");

                userIdField.clear();
                wardIdField.clear();
                ratingField.clear();
                feedbackArea.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
            }
        });

        VBox wrapper = new VBox(20, grid);
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }

    private void openAdminDashboard() {
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin Dashboard");

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-tab-header-background: #e0e0e0;");

        tabPane.getTabs().add(createAdminTab("Wards", "wards"));
        tabPane.getTabs().add(createAdminTab("Cleanliness Reports", "cleanliness_reports"));
        tabPane.getTabs().add(createAdminTab("Dustbins", "dustbins"));
        tabPane.getTabs().add(createAdminTab("Users", "users"));
        tabPane.getTabs().add(createAdminTab("Garbage Logs", "garbage_collection_logs"));

        Scene scene = new Scene(tabPane, 900, 700);
        adminStage.setScene(scene);
        adminStage.show();
    }

    private Tab createAdminTab(String label, String tableName) {
        Tab tab = new Tab(label);
        tab.setStyle("-fx-background-color: #f9f9f9;");
        TableView<ObservableList<String>> tableView = new TableView<>();
        tableView.setStyle("-fx-border-color: #ccc; -fx-border-width: 1;");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safaisetu", "root", "naman")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                final int colIndex = i - 1;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i));
                col.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(colIndex)));
                tableView.getColumns().add(col);
            }

            TableColumn<ObservableList<String>, Void> deleteCol = new TableColumn<>("Delete");
            deleteCol.setCellFactory(tc -> new TableCell<ObservableList<String>, Void>() {
                private final Button deleteBtn = new Button("❌");

                {
                    deleteBtn.setStyle("-fx-background-color: #ff6961; -fx-text-fill: white; -fx-padding: 5px 10px; -fx-border-radius: 5; -fx-cursor: hand;");
                    deleteBtn.setOnAction(e -> {
                        ObservableList<String> row = getTableView().getItems().get(getIndex());
                        String primaryKey = row.get(0);
                        deleteRecord(tableName, "id", primaryKey);
                        getTableView().getItems().remove(row);
                    });
                    deleteBtn.setOnMouseEntered(event -> deleteBtn.setStyle("-fx-background-color: #dc143c; -fx-text-fill: white; -fx-padding: 5px 10px; -fx-border-radius: 5; -fx-cursor: hand;"));
                    deleteBtn.setOnMouseExited(event -> deleteBtn.setStyle("-fx-background-color: #ff6961; -fx-text-fill: white; -fx-padding: 5px 10px; -fx-border-radius: 5; -fx-cursor: hand;"));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteBtn);
                    }
                }
            });


            tableView.getColumns().add(deleteCol);

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                tableView.getItems().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tab.setContent(new VBox(tableView));
        return tab;
    }

    private void deleteRecord(String tableName, String idColumn, String idValue) {
        String query = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/safaisetu", "root", "naman");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idValue);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Ward model class for TableView
    public static class Ward {
        private final int wardId;
        private final String name;
        private final Integer zoneNumber;
        private final Integer population;
        private final Timestamp createdAt;

        public Ward(int wardId, String name, Integer zoneNumber, Integer population, Timestamp createdAt) {
            this.wardId = wardId;
            this.name = name;
            this.zoneNumber = zoneNumber;
            this.population = population;
            this.createdAt = createdAt;
        }

        public int getWardId() {
            return wardId;
        }

        public String getName() {
            return name;
        }

        public Integer getZoneNumber() {
            return zoneNumber;
        }

        public Integer getPopulation() {
            return population;
        }

        public Timestamp getCreatedAt() {
            return createdAt;
        }
    }
}

