/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import oos.bank.PrivateBank;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    static PrivateBank bankModel;
    static Stage stage;
    private Scene scene;
    private Parent root;
    @FXML Label balance;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bankModel = PrivateBank.getInstance();
    }

    /**
     * stores the Stage
     * @param stage current Stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * switch the currently displayed scene
     * @param pathFxmlFile path to fxml file
     * @throws IOException
     */
    public void switchScene(@Nonnull String pathFxmlFile) throws IOException {
        root = FXMLLoader.load(getClass().getResource(pathFxmlFile));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens a Dialog displaying Informations about the current PrivateBank Model
     */
    public void showInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Information");
        alert.setContentText("Bankname: " + bankModel.getName() + "\nDirectoryname: " + System.getProperty("user.home") + File.separator + bankModel.getDirectoryName());
        alert.show();
    }

    /**
     * updates the balance Label to display new total amount
     */
    public void updateBalance(){
        balance.setText(String.format("%.2f", bankModel.getTotalAmount()) + " ???");
    }
}
