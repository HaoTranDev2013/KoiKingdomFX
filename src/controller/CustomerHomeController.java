package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import session.UserSession;

public class CustomerHomeController {
	@FXML private Button menuHome;
    @FXML private Button menuBookingTour;
    @FXML private Button menuFarm;
    @FXML private Button menuMyBooking;
    @FXML private Button menuKoi;
    @FXML private Button btnLogOut;
    
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
