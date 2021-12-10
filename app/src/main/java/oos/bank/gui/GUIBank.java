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
import oos.bank.gui.controller.Controller;

public class GUIBank extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("BankManager");
        stage.setScene(scene);
        stage.setResizable(false);

        Controller controller = loader.getController();
        controller.setStage(stage);

        stage.show();
    }
}

