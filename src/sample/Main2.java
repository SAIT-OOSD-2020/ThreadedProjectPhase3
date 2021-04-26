package sample;

import controllers.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main2 extends Application {

    protected Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layout/login.fxml"));
        Parent root = (Parent) loader.load();
        LoginController controller = (LoginController) loader.getController();
        controller.passCurrentStage(stage);

        primaryStage.setTitle("Travel Experts");

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/layout/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
