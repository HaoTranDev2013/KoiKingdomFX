	package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Login");

            // Tải FXML và tạo AnchorPane layout chính
            AnchorPane root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));

         

            // Tạo Scene với kích thước theo màn hình
            Scene scene = new Scene(root);

            // Thêm CSS nếu cần thiết
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            // Đặt scene và hiển thị stage
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
