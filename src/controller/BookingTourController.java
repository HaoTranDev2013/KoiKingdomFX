package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import koikingdom.pojo.Booking;
import koikingdom.pojo.Tour;
import koikingdom.service.AccountService;
import koikingdom.service.BookingService;
import koikingdom.service.IAccountService;
import koikingdom.service.IBookingService;
import koikingdom.service.ITourService;
import koikingdom.service.TourService;
import session.UserSession;

public class BookingTourController {
	@FXML
	private Button menuHome;
	@FXML
	private Button menuBookingTour;
	@FXML
	private Button menuFarm;
	@FXML
	private Button menuMyBooking;
	@FXML
	private Button menuKoi;

	@FXML
	private Button btnBookTour;
	@FXML
	private Button btnPayment;
	
	@FXML
	private Button btnLogOut;

	@FXML
	private TextField txtTourID;

	@FXML
	private TextField txtCustomerID;

	@FXML
	private TextField txtNumPeople;

	@FXML
	private TextField txtCfCustomerID;

	@FXML
	private TextField txtCfTourID;

	@FXML
	private TextField txtCfNumPeople;
	@FXML
	private TextField txtBookingDate;

	@FXML
	private TextField txtUnitPrice;

	@FXML
	private TextField txtTotalPrice;

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

	private IBookingService bookingService;
	public BookingTourController() {

		tourService = new TourService();
		tableModel = FXCollections.observableArrayList(tourService.getAllTour());
		bookingService = new BookingService();
	}

	public void showAlert(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText(content);
		alert.setHeaderText(header);
		alert.showAndWait();
	}

	private void showTour(Tour tour) {

		this.txtTourID.setText(String.valueOf(tour.getTourID()));
		// Lấy customerID từ UserSession và gán vào TextField txtCustomerID
		int customerID = UserSession.getCurrentAccount().getId();
		this.txtCustomerID.setText(String.valueOf(customerID));
		this.txtUnitPrice.setText(String.valueOf(tour.getTourPrice()));

	}

	private void refreshData() {

		tableModel = FXCollections.observableArrayList(tourService.getAllTour());
		tblTour.setItems(tableModel);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize() { // hàm khởi tạo
		// TODO Auto-generated method stub
		txtCustomerID.setEditable(false);
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
	
	@FXML
	public void bookTour() {
	    try {
	        // Lấy thông tin từ các trường nhập liệu
	        int tourID = Integer.parseInt(txtTourID.getText());
	        int customerID = Integer.parseInt(txtCustomerID.getText());
	        int numPeople = Integer.parseInt(txtNumPeople.getText());
	        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

	        // Tính toán total price
	        double totalPrice = unitPrice * numPeople;
	        txtTotalPrice.setText(String.valueOf(totalPrice));

	        // Lấy ngày hiện tại và gán vào Booking Date
	        LocalDate currentDate = LocalDate.now();
	        txtBookingDate.setText(currentDate.toString());

	        // Hiển thị lại thông tin trong các trường bên trái
	        txtCfTourID.setText(txtTourID.getText());
	        txtCfCustomerID.setText(txtCustomerID.getText());
	        txtCfNumPeople.setText(txtNumPeople.getText());

	        // Bạn có thể gọi dịch vụ BookTour của bạn ở đây để lưu booking vào database
	        // Ví dụ: tourService.bookTour(tourID, customerID, numPeople, currentDate, totalPrice);

	        // Hiển thị thông báo thành công
	        showAlert("Booking Successful", "Please click button Payment to finish.");

	    } catch (NumberFormatException e) {
	        showAlert("Error", "Please enter valid numbers for Tour ID, Customer ID, Number of People, and Unit Price.");
	    } catch (Exception e) {
	        showAlert("Error", "An error occurred while booking the tour.");
	    }
	}
	@FXML
	public void payment() {
		
		try {
			Booking booking = new Booking();
			booking.setTourID(Integer.parseInt(txtCfTourID.getText()));
			booking.setCustomerID(Integer.parseInt(txtCfCustomerID.getText()));
			booking.setNumberOfPeople(Integer.parseInt(txtCfNumPeople.getText()));
			// Lấy ngày từ TextField và chuyển đổi thành LocalDate
	        String bookingDateStr = txtBookingDate.getText();  // Ngày nhập vào từ TextField
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // Định dạng ngày
	        LocalDate bookingDate = LocalDate.parse(bookingDateStr, formatter);  // Chuyển đổi String thành LocalDate
	        booking.setBookingDate(bookingDate);
	        booking.setTotalPrice(Double.parseDouble(txtTotalPrice.getText()));
	        
			bookingService.insertBooking(booking);;
//			refreshData();
			showAlert("Success", "Tour added successfully!");
		} catch (NumberFormatException e) {
			// Handle invalid number format (e.g., price, ID, duration)
			showAlert("Input Error", "Please enter valid numerical values for ID, price, and duration.");
		} catch (Exception e) {
			// Handle other exceptions
			e.printStackTrace();
			showAlert("Error", "An error occurred while adding the tour. Please try again.");
		}

	}


	@FXML
	public void toKoi() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/ViewKoi.fxml"));
			Parent root = loader.load();
			Stage primaryStage = (Stage) menuKoi.getScene().getWindow();
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("Koi");
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void toFarm() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/ViewFarm.fxml"));
			Parent root = loader.load();
			Stage primaryStage = (Stage) menuFarm.getScene().getWindow();
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("Farm");
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void toHome() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/CustomerHome.fxml"));
			Parent root = loader.load();
			Stage primaryStage = (Stage) menuHome.getScene().getWindow();
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("Home");
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void toMyBooking() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/MyBooking.fxml"));
			Parent root = loader.load();
			Stage primaryStage = (Stage) menuMyBooking.getScene().getWindow();
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("My Booking");
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void logOut() {
		
		try {
			UserSession.clear();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/LoginForm.fxml"));
			Parent root = loader.load();
			Stage primaryStage = (Stage) btnLogOut.getScene().getWindow();
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("Login Form");
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
