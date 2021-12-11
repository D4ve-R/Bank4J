/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.dialog;

import javafx.scene.control.Alert;

public class ErrorAlert extends Alert {
    public ErrorAlert(String contentText) {
        super(Alert.AlertType.ERROR, contentText);
        setTitle("Error");
        setHeaderText("Error occured !");
    }
    public void display(){
        showAndWait();
    }
}
