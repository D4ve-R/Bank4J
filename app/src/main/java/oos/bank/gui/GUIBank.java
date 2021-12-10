/**
 * OOS Praktikum
 * David Rechkemmer
 */
package oos.bank.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIBank extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("BankManager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        controller.init();
    }
}

