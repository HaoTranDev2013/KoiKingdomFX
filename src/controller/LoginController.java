package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import koikingdom.dao.AccountDAO;
import koikingdom.pojo.Account;
import koikingdom.repository.AccountRepo;
import koikingdom.service.AccountService;



public class LoginController {
	private AccountService accountservice;
	
	
	
	@FXML 
	private TextField txtEmail;
	
	@FXML 
	private PasswordField txtPassword;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private Button btnClose;
	
	@FXML
	private Button btnSignup;
	
	
	 @FXML
	    public void initialize() {
	       
	        AccountDAO accountDAO = new AccountDAO();
	        AccountRepo accountRepo = new AccountRepo(accountDAO);
	        accountservice = new AccountService(accountRepo);  // Khởi tạo accountService
	    }

	
	public void CancelOnAction() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText(null);
		alert.setContentText("Do you want to exit ? "
				
				+ "We will miss you so much <3");
		if(alert.showAndWait().get() == ButtonType.OK) {
			Platform.exit();
		} else {
			alert.close();
		}
		
	}
	
	@FXML
	public void LoginOnAction() throws IOException {
		String email = txtEmail.getText();
		String password = txtPassword.getText();

		Account account = accountservice.login(email, password);

		if (account != null && account.getPassword().equals(password) && account.isStatus() == true) {
			String roleID = account.getRole();
			if (roleID.equals("manager") ) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/TourManagement.fxml"));
				Parent root = loader.load();
				Stage primaryStage = (Stage) txtEmail.getScene().getWindow();
				primaryStage.setScene(new Scene(root));
				primaryStage.setTitle("KoiKingdom Management");
				primaryStage.show();
			} else {
				showAlert("Login Error","You have no permission to access this function!");
			}
		} else {
			showAlert("Login Error","Invalid username or password!");
		}

	}
	
	@FXML
	public void fromLoginToSignUp() throws IOException {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/SignUpForm.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) btnSignup.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Sign up");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	
	
	
	// hàm alert thông báo
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	
	
}
