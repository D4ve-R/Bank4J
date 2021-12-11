/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.cellfactory;

import javafx.scene.control.*;
import javafx.stage.Stage;
import oos.bank.gui.controller.MainController;
import oos.bank.gui.dialog.ErrorAlert;

import java.util.Optional;

public class AccountCellFactory extends ListCell<String> {
    private MainController controller;
    public AccountCellFactory(MainController controller){
        this.controller = controller;
    }

    @Override
    protected void updateItem(String str, boolean empty){
        super.updateItem(str, empty);
        textProperty().bind(this.itemProperty());
        MenuItem viewItem = new MenuItem("View");
        viewItem.setOnAction(event -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setUserData(this.getItem());
            try {
                controller.switchScene("/view.fxml");
            }catch (Exception e){e.printStackTrace();}
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting Account: " + this.getItem() + " ?");
            alert.setTitle("Delete");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                try {
                    controller.deleteAccount(this.getItem());
                    //pb.deleteAccount(cell.getItem());
                    //listView.getItems().remove(cell.getItem());
                    //updateBalance();
                } catch (Exception e) {
                    ErrorAlert errorAlert = new ErrorAlert(e.getLocalizedMessage());
                    errorAlert.display();
                }
            }
        });

        ContextMenu contextMenu = new ContextMenu(viewItem, deleteItem);
        emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                setContextMenu(null);
            } else {
                setContextMenu(contextMenu);
            }
        });
    }
}
