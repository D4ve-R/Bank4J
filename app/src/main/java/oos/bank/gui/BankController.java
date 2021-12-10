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

import java.util.Optional;

public class BankController extends Controller{

    @FXML ListView<String> listView;
    @FXML Label label;

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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
                    Parent view = loader.load();
                    Controller controller = loader.getController();
                    Scene scene = new Scene(view);
                    stage.setScene(scene);
                    controller.init();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alertError = new Alert(Alert.AlertType.INFORMATION, "Could not delete file");
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Failed");
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
    }

    public void init(){
        super.init();
        label.setText(pb.getName());
        balance.setText("Total: " + pb.getTotalAmount() + " €");
        showAccounts();
    }

}