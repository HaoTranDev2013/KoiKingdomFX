package controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import koikingdom.dao.TourDAO;
import koikingdom.pojo.Tour;
import koikingdom.repository.TourRepo;
import koikingdom.service.ITourService;
import koikingdom.service.TourService;

public class TourManagementController {
	@FXML
	private Button btnAdd;

	@FXML
	private Button btnUpdate;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnUpload;
	
	@FXML
	private Button btnClear;
	
	@FXML
	private Button menuKoi;

	@FXML
	private TextField txtTourID;

	@FXML
	private TextField txtTourName;

	@FXML
	private TextField txtDuration;

	@FXML
	private TextField txtDescription;

	@FXML
	private TextField txtTourPrice;

	@FXML
	private DatePicker txtStartDate;

	@FXML
	private DatePicker txtEndDate;

	@FXML
	private TextField txtImage;

	@FXML
	private ComboBox<String> txtStatus;

	@FXML
	private TextField txtDeparture;

	@FXML
	private TableView tblTour;

	@FXML // nối được từ khóa trên giao diện với biến
	private TableColumn<Tour, Integer> tourID;

	@FXML
	private TableColumn<Tour, String> tourName;

	@FXML
	private TableColumn<Tour, Integer> tourDuration;

	@FXML
	private TableColumn<Tour, String> tourDescription;

	@FXML
	private TableColumn<Tour, Double> tourPrice;

	@FXML
	private TableColumn<Tour, LocalDate> startDate;

	@FXML
	private TableColumn<Tour, LocalDate> endDate;

	@FXML
	private TableColumn<Tour, String> image;

	@FXML
	private TableColumn<Tour, Boolean> status;

	@FXML
	private TableColumn<Tour, String> departure;

	private ObservableList<Tour> tableModel;

	private ITourService tourService;

	public TourManagementController() {

		tourService = new TourService();
		tableModel = FXCollections.observableArrayList(tourService.getAllTour());
	}

	public void showAlert(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText(content);
		alert.setHeaderText(header);
		alert.showAndWait();
	}

	private void showTour(Tour tour) {

		this.txtTourID.setText(String.valueOf(tour.getTourID()));
		this.txtTourName.setText(tour.getTourName());
		this.txtDuration.setText(String.valueOf(tour.getDuration()));
		this.txtDescription.setText(tour.getDescription());
		this.txtTourPrice.setText(String.valueOf(tour.getTourPrice()));
		this.txtStartDate.setValue(tour.getStartDate());
		;
		this.txtEndDate.setValue(tour.getEndDate());
		this.txtImage.setText(tour.getImage());
		this.txtStatus.getSelectionModel().select(tour.isStatus() ? "True" : "False");
		this.txtDeparture.setText(tour.getDepartureLocation());

	}

	private void refreshData() {

		tableModel = FXCollections.observableArrayList(tourService.getAllTour());
		tblTour.setItems(tableModel);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize() { // hàm khởi tạo
		// TODO Auto-generated method stub
		// Thêm các giá trị vào ComboBox
	    ObservableList<String> statusOptions = FXCollections.observableArrayList("True", "False");
	    txtStatus.setItems(statusOptions);
		tourID.setCellValueFactory(new PropertyValueFactory<>("TourID"));
		tourName.setCellValueFactory(new PropertyValueFactory<>("TourName"));
		tourDuration.setCellValueFactory(new PropertyValueFactory<>("Duration"));
		tourDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
		tourPrice.setCellValueFactory(new PropertyValueFactory<>("tourPrice"));
		startDate.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
		image.setCellValueFactory(new PropertyValueFactory<>("Image"));
		status.setCellValueFactory(new PropertyValueFactory<>("Status"));
		departure.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));
		tblTour.setItems(tableModel);

		tblTour.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue observableValue, Object oldValue, Object index) {
				if (tblTour.getSelectionModel().selectedItemProperty() != null) {
					TableViewSelectionModel selectionModel = tblTour.getSelectionModel();
					ObservableList selectionCells = selectionModel.getSelectedCells();
					TablePosition tablePosition = (TablePosition) selectionCells.get(0);

					Object tourID = tablePosition.getTableColumn().getCellData(index);
					try {
						Tour tour = tourService.findTourById(Integer.valueOf(tourID.toString()));
						showTour(tour);
					} catch (Exception ex) {
						showAlert("Information Board !", "Please choose the First Cell !");
					}
				}
			}

		});
		this.refreshData();

	}

	public void CancelOnAction() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText(null);
		alert.setContentText("Do you want to exit ? "

				+ "We will miss you so much <3");
		if (alert.showAndWait().get() == ButtonType.OK) {
			Platform.exit();
		} else {
			alert.close();
		}

	}

	public void AddOnAction() {
		if (txtTourID.getText().isEmpty() || txtTourName.getText().isEmpty() || txtDuration.getText().isEmpty()
				|| txtDescription.getText().isEmpty() || txtTourPrice.getText().isEmpty()
				|| txtStartDate.getValue() == null || txtEndDate.getValue() == null || txtImage.getText().isEmpty()
				|| txtStatus.getSelectionModel().isEmpty() || txtDeparture.getText().isEmpty()) {
			showAlert("Input Error", "Please fill in all fields before adding a tour.");
			return;
		}
		try {
			Tour tour = new Tour();
			tour.setTourID(Integer.parseInt(txtTourID.getText()));
			tour.setTourName(txtTourName.getText());
			tour.setDuration(Integer.parseInt(txtDuration.getText()));
			tour.setDescription(txtDescription.getText());
			tour.setTourPrice(Double.parseDouble(txtTourPrice.getText()));
			tour.setStartDate(txtStartDate.getValue());
			tour.setEndDate(txtEndDate.getValue());
			tour.setImage(txtImage.getText());
			tour.setStatus("True".equals(txtStatus.getSelectionModel().getSelectedItem()));
			tour.setDepartureLocation(txtDeparture.getText());
			tourService.addTour(tour);
			refreshData();
			showAlert("Success", "Tour added successfully!");
		} catch (NumberFormatException e) {
			// Handle invalid number format (e.g., price, ID, duration)
			showAlert("Input Error", "Please enter valid numerical values for ID, price, and duration.");
		} catch (Exception e) {
			// Handle other exceptions
			showAlert("Error", "An error occurred while adding the tour. Please try again.");
		}

	}
	
	public void UpdateOnAction() {
		if (txtTourID.getText().isEmpty() || txtTourName.getText().isEmpty() || txtDuration.getText().isEmpty()
				|| txtDescription.getText().isEmpty() || txtTourPrice.getText().isEmpty()
				|| txtStartDate.getValue() == null || txtEndDate.getValue() == null || txtImage.getText().isEmpty()
				|| txtStatus.getSelectionModel().isEmpty() || txtDeparture.getText().isEmpty()) {
			showAlert("Input Error", "Please fill in all fields before updating a tour.");
			return;
		}
		try {
			int tourID = Integer.parseInt(txtTourID.getText());
			if (tourService.findTourById(tourID) == null) {
	            showAlert("Error", "Tour with ID " + tourID + " does not exist.");
	            return;
	        }
			Tour tour = new Tour();
			tour.setTourID(Integer.parseInt(txtTourID.getText()));
			tour.setTourName(txtTourName.getText());
			tour.setDuration(Integer.parseInt(txtDuration.getText()));
			tour.setDescription(txtDescription.getText());
			tour.setTourPrice(Double.parseDouble(txtTourPrice.getText()));
			tour.setStartDate(txtStartDate.getValue());
			tour.setEndDate(txtEndDate.getValue());
			tour.setImage(txtImage.getText());
			tour.setStatus("True".equals(txtStatus.getSelectionModel().getSelectedItem()));
			tour.setDepartureLocation(txtDeparture.getText());
			tourService.update(tour);
			refreshData();
			showAlert("Success", "Tour updated successfully!");
		} catch (NumberFormatException e) {
			// Handle invalid number format (e.g., price, ID, duration)
			showAlert("Input Error", "Please enter valid numerical values for ID, price, and duration.");
		} catch (Exception e) {
			// Handle other exceptions
			showAlert("Error", "An error occurred while updating the tour. Please try again.");
		}

	}
	
	public void deleteOnAction() {
	    // Kiểm tra xem trường TourID có trống hay không
	    if (txtTourID.getText().isEmpty()) {
	        showAlert("Input Error", "Please enter a Tour ID to delete.");
	        return;
	    }

	    try {
	        int tourID = Integer.parseInt(txtTourID.getText()); // Lấy ID của tour từ text field
	        
	        // Kiểm tra sự tồn tại của tour trước khi xóa
	        TourDAO tourDAO = new TourDAO();
	        Tour tour = tourDAO.findTourById(tourID);

	        if (tour == null) {
	            showAlert("Error", "Tour with ID " + tourID + " does not exist.");
	            return;
	        }

	        // Gọi phương thức xóa từ DAO
	        tourDAO.deleteTour(tourID);
	        
	        showAlert("Success", "Tour deleted (status set to false) successfully.");
	        refreshData(); // Cập nhật lại dữ liệu trong bảng sau khi xóa

	    } catch (NumberFormatException e) {
	        showAlert("Input Error", "Please enter a valid numerical Tour ID.");
	    } catch (Exception e) {
	        showAlert("Error", "An error occurred while deleting the tour. Please try again.");
	    }
	}
	
	@FXML
	public void toKoi() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/KoiManagement.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) menuKoi.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Tour Management");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
	public void clearFields() {
	    txtTourID.clear();
	    txtTourName.clear();
	    txtDuration.clear();
	    txtDescription.clear();
	    txtTourPrice.clear();
	    txtStartDate.setValue(null);
	    txtEndDate.setValue(null);
	    txtImage.clear();
	    txtStatus.setValue(null);
	    txtDeparture.clear();
	}

	// Phương thức upload ảnh
	public void uploadImage() {
		// Tạo một FileChooser để chọn tệp
		FileChooser fileChooser = new FileChooser();

		// Cấu hình loại tệp có thể chọn (chỉ cho phép chọn ảnh)
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png",
				"*.jpeg", "*.gif");
		fileChooser.getExtensionFilters().add(extFilter);

		// Mở hộp thoại chọn file
		File selectedFile = fileChooser.showOpenDialog(null);

		// Nếu người dùng chọn file, hiển thị đường dẫn trong TextField
		if (selectedFile != null) {
			txtImage.setText(selectedFile.getAbsolutePath());
		}
	}

}
