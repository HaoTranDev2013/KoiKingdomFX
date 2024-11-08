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

public class SignUpController {
	private AccountService accountservice;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private PasswordField txtCfPassword;

	@FXML
	private TextField txtFullName;
	
	@FXML
	private TextField txtAddress;
	
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
		accountservice = new AccountService(accountRepo); // Khởi tạo accountService
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
	public void SignUpOnAction() throws IOException {
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		String cfpassword = txtCfPassword.getText();
		String fullname = txtFullName.getText();
		String address = txtAddress.getText();
		
		

		if (password.equals(cfpassword)) {
	
		    Account account = new Account(email, password, "customer", fullname, address, true);
		    
		  
		    try {
		        accountservice.signup(account); 
		        showAlert("Success", "Account created successfully.");
		    } catch (Exception e) {
		        showAlert("Error", "An error occurred while creating account: " + e.getMessage());
		    }
		} else {
		    // Thông báo lỗi nếu mật khẩu không khớp
		    showAlert("Error", "Password and Confirm Password do not match!");
		}
		

	}
	
	@FXML
	public void fromSignUpToLogin() throws IOException {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/LoginForm.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login");
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
