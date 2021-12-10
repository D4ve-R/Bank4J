/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import oos.bank.gui.dialog.AddAccountDialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController extends Controller {
    @FXML ListView<String> listView;
    @FXML Label label;

    @FXML void deleteAll() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting all accounts ?");
        alert.setTitle("Delete all");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            try {
                for (String account : pb.getAllAccounts())
                    pb.deleteAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alertError = new Alert(Alert.AlertType.INFORMATION, "Could not delete files");
                alertError.setTitle("Error");
                alertError.setHeaderText("Failed to deleted");
                alertError.show();
            }
            listView.getItems().clear();
        }
        updateBalance();
    }

    @FXML void addAccount(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Account");
        dialog.setHeaderText("Create a new Account");
        dialog.setContentText("Account name : ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(account -> {
            try {
                pb.createAccount(account);
            } catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error occured !");
                alert.setContentText(e.getLocalizedMessage());
                alert.showAndWait();
            }
        });
        listView.setItems(FXCollections.observableArrayList(pb.getAllAccounts()));
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        label.setText(pb.getName());
        balance.setText(pb.getTotalAmount() + " €");
        showAccounts();
    }

    /**
     * Adds all accounts of PrivateBank to ListView
     */
    public void showAccounts(){
        listView.setItems(FXCollections.observableArrayList(pb.getAllAccounts()));
        listView.setCellFactory(factory -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());

            MenuItem viewItem = new MenuItem("View");
            viewItem.setOnAction(event -> {
                Stage stage = (Stage) cell.getScene().getWindow();
                stage.setUserData(cell.getItem());
                try {
                    switchToView(event);
                }catch (Exception e){e.printStackTrace();}
            });

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting Account: " + cell.getItem() + " ?");
                alert.setTitle("Delete");
                Optional<ButtonType> result = alert.showAndWait();
                if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                    try {
                        pb.deleteAccount(cell.getItem());
                        updateBalance();
                    } catch (Exception e) { e.printStackTrace();}
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
}