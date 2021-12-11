/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.dialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oos.bank.gui.controller.TransactionController;

import java.io.IOException;

public class AddTransactionDialog {
    public void display() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/transaction.fxml"));
        Parent root = loader.load();
        TransactionController controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        stage.setTitle("Transaction Dialog");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
