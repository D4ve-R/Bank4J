/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import oos.bank.PrivateBank;

import java.io.File;

public class Controller {
    PrivateBank pb = new PrivateBank();
    Stage stage;
    @FXML
    Label balance;

    @FXML
    void showInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Information");
        alert.setContentText("Bankname: " + pb.getName() + "\nDirectoryname: " + System.getProperty("user.home") + File.separator + pb.getDirectoryName());
        alert.show();
    }

    public void init(){
        stage = (Stage) balance.getScene().getWindow();
    }
}
