package controller;

import koikingdom.dao.KoiDAO;
import koikingdom.pojo.KoiFish;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class KoiFishController {

    @FXML private TableView<KoiFish> tblFish;
    @FXML private TableColumn<KoiFish, Integer> koiID;
    @FXML private TableColumn<KoiFish, String> fishName;
    @FXML private TableColumn<KoiFish, Integer> age;
    @FXML private TableColumn<KoiFish, Double> lenght;
    @FXML private TableColumn<KoiFish, Double> weight;
    @FXML private TableColumn<KoiFish, Double> price;
    
    @FXML private TextField txtKoiID;
    @FXML private TextField txtName;
    @FXML private TextField txtAge;
    @FXML private TextField txtLenght;
    @FXML private TextField txtWeight;
    @FXML private TextField txtPrice;
    @FXML private TextField txtSearch;
    
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    
    
    @FXML private Button menuTour;
    @FXML private Button menuKoi;
    @FXML private Button menuFarm;
    @FXML private Button menuCustomer;
    @FXML private Button menuEmployee;
    @FXML private Button menuHome;
    private KoiDAO koiDAO;
    private ObservableList<KoiFish> koiList;

    public KoiFishController() {
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

        // Hiển thị danh sách KoiFish trong bảng
        loadKoiFishData();
        tblFish.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillFormWithSelectedKoiFish(newValue);
            }
        });
    }

    // Thêm KoiFish mới
    @FXML
    public void btnAddOnAct() {
        String name = txtName.getText();
        int age = Integer.parseInt(txtAge.getText());
        double lenght = Double.parseDouble(txtLenght.getText());
        double weight = Double.parseDouble(txtWeight.getText());
        double price = Double.parseDouble(txtPrice.getText());

        KoiFish newKoiFish = new KoiFish(name, age, lenght, weight, price);

        koiDAO.addKoiFish(newKoiFish);  // Gọi DAO để thêm KoiFish vào DB
        loadKoiFishData();  // Tải lại danh sách KoiFish

        showAlert("Success", "KoiFish added successfully", Alert.AlertType.INFORMATION);
    }

    // Cập nhật thông tin KoiFish
    @FXML
    public void btnUpdateOnAct() {
        int koiId = Integer.parseInt(txtKoiID.getText());
        String name = txtName.getText();
        int age = Integer.parseInt(txtAge.getText());
        double lenght = Double.parseDouble(txtLenght.getText());
        double weight = Double.parseDouble(txtWeight.getText());
        double price = Double.parseDouble(txtPrice.getText());

        KoiFish updatedKoiFish = new KoiFish(koiId, name, age, lenght, weight, price);

        koiDAO.updateKoiFish(updatedKoiFish);  // Gọi DAO để cập nhật KoiFish trong DB
        loadKoiFishData();  // Tải lại danh sách KoiFish

        showAlert("Success", "KoiFish updated successfully", Alert.AlertType.INFORMATION);
    }

    // Xóa KoiFish
    @FXML
    public void btnDeleteOnAct() {
        int koiId = Integer.parseInt(txtKoiID.getText());

        koiDAO.deleteKoiFish(koiId);  // Gọi DAO để xóa KoiFish khỏi DB
        loadKoiFishData();  // Tải lại danh sách KoiFish

        showAlert("Success", "KoiFish deleted successfully", Alert.AlertType.INFORMATION);
    }

    // Tìm kiếm KoiFish theo ID
    @FXML
    public void btnSearchOnAct() {
        String searchText = txtSearch.getText();
        if (searchText != null && !searchText.isEmpty()) {
            KoiFish koiFish = koiDAO.getKoiFishById(Integer.parseInt(searchText));
            if (koiFish != null) {
                // Cập nhật các trường thông tin với dữ liệu từ KoiFish tìm được
                txtKoiID.setText(String.valueOf(koiFish.getKoiId()));
                txtName.setText(koiFish.getName());
                txtAge.setText(String.valueOf(koiFish.getAge()));
                txtLenght.setText(String.valueOf(koiFish.getLength()));
                txtWeight.setText(String.valueOf(koiFish.getWeight()));
                txtPrice.setText(String.valueOf(koiFish.getPrice()));
            } else {
                showAlert("Not Found", "KoiFish not found with ID: " + searchText, Alert.AlertType.WARNING);
            }
        }
    }
    
    

    // Load danh sách tất cả KoiFish
    private void loadKoiFishData() {
        koiList = FXCollections.observableArrayList(koiDAO.getAllKoiFish());
        tblFish.setItems(koiList);
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    // Hiển thị thông báo
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void fillFormWithSelectedKoiFish(KoiFish koiFish) {
        txtKoiID.setText(String.valueOf(koiFish.getKoiId()));
        txtName.setText(koiFish.getName());
        txtAge.setText(String.valueOf(koiFish.getAge()));
        txtLenght.setText(String.valueOf(koiFish.getLength()));
        txtWeight.setText(String.valueOf(koiFish.getWeight()));
        txtPrice.setText(String.valueOf(koiFish.getPrice()));
    }
    
    private boolean validateInput() {
        String errorMessage = "";

        if (txtKoiID.getText() == null || txtKoiID.getText().isEmpty()) {
            errorMessage += "Koi ID không được để trống.\n";
        }
        if (txtName.getText() == null || txtName.getText().isEmpty()) {
            errorMessage += "Tên cá không được để trống.\n";
        }
        if (txtAge.getText() == null || txtAge.getText().isEmpty()) {
            errorMessage += "Tuổi không được để trống.\n";
        }
        if (txtLenght.getText() == null || txtLenght.getText().isEmpty()) {
            errorMessage += "Chiều dài không được để trống.\n";
        }
        if (txtWeight.getText() == null || txtWeight.getText().isEmpty()) {
            errorMessage += "Cân nặng không được để trống.\n";
        }
        if (txtPrice.getText() == null || txtPrice.getText().isEmpty()) {
            errorMessage += "Giá không được để trống.\n";
        }

        // Kiểm tra nếu có lỗi
        if (!errorMessage.isEmpty()) {
            // Hiển thị thông báo lỗi cho người dùng
            showAlert("Lỗi nhập liệu", "Vui lòng kiểm tra lại các trường sau:", errorMessage);
            return false;
        }

        return true;
    }

	@FXML public void home() {}

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
	public void toHome() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/ManagerHome.fxml"));
            Parent root = loader.load();
            Stage primaryStage = (Stage) menuHome.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Home");
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@FXML public void koi() {}

	@FXML public void farm() {}

	@FXML public void customer() {}

	@FXML public void employee() {}

	@FXML public void btnCloseOnAct() {
		Platform.exit();
	}
}
