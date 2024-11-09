package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import session.UserSession;

public class ManagerHomeController {
	@FXML private Button menuTour;
    @FXML private Button menuKoi;
    @FXML private Button menuFarm;
    @FXML private Button menuCustomer;
    @FXML private Button menuEmployee;
    @FXML private Button menuHome;
    @FXML private Button btnLogOut;
    
    @FXML public void toTour() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/TourManagement.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) menuTour.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Tour Management");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    @FXML
	public void toKoi() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/KoiManagement.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) menuKoi.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Koi Management");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void toFarm() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/FarmController.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) menuFarm.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Farm Management");
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
