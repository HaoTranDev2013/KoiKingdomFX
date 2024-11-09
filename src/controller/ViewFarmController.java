package controller;

import java.io.IOException;

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
import koikingdom.dao.FarmDAO;
import koikingdom.pojo.Farm;

public class ViewFarmController {
	@FXML private Button menuHome;
    @FXML private Button menuBookingTour;
    @FXML private Button menuFarm;
    @FXML private Button menuMyBooking;
    @FXML private Button menuEmployee;
    @FXML private Button menuKoi;
    
    @FXML private TableColumn<Farm, Integer> farmId;
    @FXML private TableColumn<Farm, String> farmName;
    @FXML private TableColumn<Farm, String> loc;
    @FXML private TableColumn<Farm, String> description;
    @FXML private TableColumn<Farm, Boolean> status;
    @FXML private TableView<Farm> tblFarm;
    
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
        tblFarm.setItems(farmList);
       

        // Load all farms into the TableView
        loadFarmData();
        
        
    }
    
 // Load all farm data into the table
    private void loadFarmData() {
        farmList.clear();
        farmList.addAll(farmDAO.getAllFarms());
        tblFarm.setItems(farmList);
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
}
