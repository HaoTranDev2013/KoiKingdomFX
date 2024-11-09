package controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import koikingdom.dao.KoiDAO;
import koikingdom.pojo.KoiFish;

public class ViewKoiController {
	@FXML private Button menuHome;
    @FXML private Button menuBookingTour;
    @FXML private Button menuFarm;
    @FXML private Button menuMyBooking;
    @FXML private Button menuEmployee;
    @FXML private Button menuKoi;
    
    @FXML
	private TableView tblFish;
    
    @FXML private TableColumn<KoiFish, Integer> koiID;
    @FXML private TableColumn<KoiFish, String> fishName;
    @FXML private TableColumn<KoiFish, Integer> age;
    @FXML private TableColumn<KoiFish, Double> lenght;
    @FXML private TableColumn<KoiFish, Double> weight;
    @FXML private TableColumn<KoiFish, Double> price;
    
    private KoiDAO koiDAO;
    private ObservableList<KoiFish> koiList;

    public ViewKoiController() {
        this.koiDAO = new KoiDAO();  // Khởi tạo DAO
    }

    @FXML
    public void initialize() {
    	koiID.setCellValueFactory(new PropertyValueFactory<>("koiId"));
        fishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        lenght.setCellValueFactory(new PropertyValueFactory<>("length"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tblFish.setItems(koiList);
        // Hiển thị danh sách KoiFish trong bảng
        loadKoiFishData();
    }
    
 // Load danh sách tất cả KoiFish
    private void loadKoiFishData() {
        koiList = FXCollections.observableArrayList(koiDAO.getAllKoiFish());
        tblFish.setItems(koiList);
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
