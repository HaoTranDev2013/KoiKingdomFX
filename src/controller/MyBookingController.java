package controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import koikingdom.dao.BookingDAO;

import koikingdom.pojo.Account;
import koikingdom.pojo.Booking;
import koikingdom.pojo.Farm;
import koikingdom.pojo.Tour;
import koikingdom.service.BookingService;
import koikingdom.service.IBookingService;
import koikingdom.service.ITourService;
import koikingdom.service.TourService;
import session.UserSession;

public class MyBookingController {
	@FXML private Button menuHome;
    @FXML private Button menuBookingTour;
    @FXML private Button menuFarm;
    @FXML private Button menuMyBooking;
    @FXML private Button menuEmployee;
    @FXML private Button menuKoi;
    
    @FXML
	private TableView tblBooking;
    
    private ObservableList<Booking> tableModel;
    private IBookingService bookingService;
    
    public MyBookingController() {

    	bookingService = new BookingService();
    	int customerID = UserSession.getCurrentAccount().getId();
		tableModel = FXCollections.observableArrayList(bookingService.getBookingById(customerID));
	}

    
    @FXML private TableColumn<Booking, Integer> bookingID;
    @FXML private TableColumn<Booking, Integer> tourID;
    @FXML private TableColumn<Booking, Integer> customerID;
    @FXML private TableColumn<Booking, Integer> numberOfPeople;
    @FXML private TableColumn<Booking, LocalDate> bookingDate;
    @FXML private TableColumn<Booking, Double> totalPrice;
    
    private BookingDAO bookingDAO = new BookingDAO();
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void initialize() {
    	// Sử dụng PropertyValueFactory để thiết lập các cột trong TableView
    	bookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
    	customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    	tourID.setCellValueFactory(new PropertyValueFactory<>("tourID"));
    	numberOfPeople.setCellValueFactory(new PropertyValueFactory<>("numberOfPeople"));
    	bookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
    	totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    	tblBooking.setItems(bookingList);
        

        // Load all farms into the TableView
    	loadBookingData();
        
    }
        // Load all farm data into the table
        private void loadBookingData() {
        	int customerID = UserSession.getCurrentAccount().getId();
        	bookingList.addAll(bookingDAO.getBookingById(customerID));
        	tblBooking.setItems(tableModel);
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
    	public void toBookingTour() {
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/BookingTour.fxml"));
                Parent root = loader.load();
                Stage primaryStage = (Stage) menuBookingTour.getScene().getWindow();
                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Booking Tour");
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
        
    

}
