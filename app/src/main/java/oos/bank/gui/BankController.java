/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import oos.bank.PrivateBank;

import java.io.File;
import java.util.Optional;

public class BankController {

    PrivateBank pb = new PrivateBank();
    @FXML ListView<String> listView;
    @FXML Label label;
    @FXML Label totalAmount;

    public void showAccounts(){
        label.setText(pb.getName());
        totalAmount.setText("Total: " + pb.getTotalAmount() + " €");

        listView.setItems(FXCollections.observableArrayList(pb.getAllAccounts()));
        listView.setCellFactory(factory -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());

            MenuItem viewItem = new MenuItem("View");
            viewItem.setOnAction(event -> {
                System.out.println(cell.getItem());
                Stage stage = (Stage) cell.getScene().getWindow();
                try {
                    Parent view = new FXMLLoader(getClass().getResource("/main2.fxml")).load();
                    Scene scene = new Scene(view);
                    stage.setScene(scene);
                }catch (Exception e){e.printStackTrace();}
            });

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete");
                alert.setContentText("Deleting Account: " + cell.getItem() + " ?");
                Optional<ButtonType> result = alert.showAndWait();

                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    try {
                        pb.deleteAccount(cell.getItem());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alertError = new Alert(Alert.AlertType.INFORMATION);
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Failed to deleted");
                        String s ="This is an example of JavaFX 8 Dialogs... ";
                        alertError.setContentText(s);
                        alertError.show();
                    }
                    listView.getItems().remove(cell.getItem());
                }
            });

            ContextMenu contextMenu = new ContextMenu(viewItem, deleteItem);
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
    }

    @FXML void deleteAll() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete all");
        alert.setContentText("Deleting all accounts ?");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            try {
                for (String account : pb.getAllAccounts())
                    pb.deleteAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alertError = new Alert(Alert.AlertType.INFORMATION);
                alertError.setTitle("Error");
                alertError.setHeaderText("Failed to deleted");
                String s = "This is an example of JavaFX 8 Dialogs... ";
                alertError.setContentText(s);
                alertError.show();
            }
            listView.getItems().clear();
        }
    }

    @FXML void showInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Information");
        alert.setContentText("Bankname: " + pb.getName() + "\nDirectoryname: " + System.getProperty("user.home") + File.separator + pb.getDirectoryName());
        alert.show();
    }
}