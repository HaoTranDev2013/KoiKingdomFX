package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import koikingdom.dao.FarmDAO;
import koikingdom.pojo.Farm;


public class FarmController {

    @FXML private TableView<Farm> tblFarm;
    @FXML private TableColumn<Farm, Integer> farmId;
    @FXML private TableColumn<Farm, String> farmName;
    @FXML private TableColumn<Farm, String> loc;
    @FXML private TableColumn<Farm, String> description;
    @FXML private TableColumn<Farm, Boolean> status;

    @FXML private TextField txtFarmId;
    @FXML private TextField txtName;
    @FXML private TextField txtLocation;
    @FXML private TextField txtDescription;
    @FXML private ChoiceBox<String> choiceStatus;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClose;
    @FXML private TextField txtSearch;
    @FXML private Button btnSearch;

    private FarmDAO farmDAO = new FarmDAO();
    private ObservableList<Farm> farmList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    	// Sử dụng PropertyValueFactory để thiết lập các cột trong TableView
        farmId.setCellValueFactory(new PropertyValueFactory<>("farmID"));
        farmName.setCellValueFactory(new PropertyValueFactory<>("farmName"));
        loc.setCellValueFactory(new PropertyValueFactory<>("location"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Initialize ChoiceBox for status options (Active/Inactive)
        choiceStatus.setItems(FXCollections.observableArrayList("Active", "Inactive"));
        choiceStatus.getSelectionModel().select(0); // Set default value to "Active"

        // Load all farms into the TableView
        loadFarmData();
        
        tblFarm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Điền giá trị của dòng đã chọn vào các TextField
                txtFarmId.setText(String.valueOf(newValue.getFarmID())); // Chuyển int sang String
                txtName.setText(newValue.getFarmName());
                txtLocation.setText(newValue.getLocation());
                txtDescription.setText(newValue.getDescription());
                choiceStatus.setValue(newValue.isStatus() ? "Active" : "Inactive");
            }
        });
    }

    // Load all farm data into the table
    private void loadFarmData() {
        farmList.clear();
        farmList.addAll(farmDAO.getAllFarms());
        tblFarm.setItems(farmList);
    }

    // Add new farm
    @FXML
    private void btnAddOnAct() {
    	if (validateFields()) {
            Farm newFarm = new Farm();
            newFarm.setFarmName(txtName.getText());
            newFarm.setLocation(txtLocation.getText());
            newFarm.setDescription(txtDescription.getText());
            newFarm.setStatus(choiceStatus.getValue().equals("Active"));

            farmDAO.addFarm(newFarm);
            loadFarmData();  // Reload farm data after adding

            clearFields();
        };
    }

    // Update an existing farm
    @FXML
    private void btnUpdateOnAct() {
    	if (validateFields()) {
            Farm selectedFarm = tblFarm.getSelectionModel().getSelectedItem();
            if (selectedFarm != null) {
                selectedFarm.setFarmName(txtName.getText());
                selectedFarm.setLocation(txtLocation.getText());
                selectedFarm.setDescription(txtDescription.getText());
                selectedFarm.setStatus(choiceStatus.getValue().equals("Active"));

                farmDAO.updateFarm(selectedFarm);
                loadFarmData();  // Reload farm data after update

                clearFields();
            }
        }
    }

    // Delete a selected farm
    @FXML
    private void btnDeleteOnAct() {
        Farm selectedFarm = tblFarm.getSelectionModel().getSelectedItem();
        if (selectedFarm != null) {
            farmDAO.deleteFarm(selectedFarm.getFarmID());
            loadFarmData();  // Reload farm data after delete

            clearFields();
        }
    }

    // Search farm by ID
    @FXML
    private void btnSearchOnAct() {
        String searchId = txtSearch.getText();
        if (!searchId.isEmpty()) {
            int id = Integer.parseInt(searchId);
            Farm farm = farmDAO.getFarmById(id);
            if (farm != null) {
                farmList.clear();
                farmList.add(farm);
                tblFarm.setItems(farmList);
            } else {
                showAlert("Farm not found", "No farm found with ID: " + searchId);
            }
        }
    }

    // Close the form
    @FXML
    private void btnCloseOnAct() {
        // Close the application or the window
        System.exit(0);
    }

    // Clear input fields
    private void clearFields() {
        txtFarmId.clear();
        txtName.clear();
        txtLocation.clear();
        txtDescription.clear();
        choiceStatus.getSelectionModel().select(0);
    }

    // Show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean validateFields() {
        if (txtName.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter the farm name.");
            return false;
        }
        if (txtLocation.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter the location.");
            return false;
        }
        if (txtDescription.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter the description.");
            return false;
        }
        if (choiceStatus.getSelectionModel().isEmpty()) {
            showAlert("Validation Error", "Please select a status.");
            return false;
        }
        return true;
    }

	@FXML public void home() {}

	@FXML public void tour() {}

	@FXML public void koi() {}

	@FXML public void farm() {}

	@FXML public void customer() {}

	@FXML public void employee() {}
}
