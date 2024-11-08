package controller;

import koikingdom.dao.KoiDAO;
import koikingdom.pojo.KoiFish;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

	@FXML public void home() {}

	@FXML public void tour() {}

	@FXML public void koi() {}

	@FXML public void farm() {}

	@FXML public void customer() {}

	@FXML public void employee() {}

	@FXML public void btnCloseOnAct() {
		Platform.exit();
	}
}
