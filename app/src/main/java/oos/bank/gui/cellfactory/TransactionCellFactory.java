/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.cellfactory;

import javafx.scene.control.*;
import oos.bank.gui.controller.ViewController;
import oos.bank.gui.dialog.ErrorAlert;
import oos.bank.transactions.Transaction;

import java.util.Optional;

public class TransactionCellFactory extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction t, boolean empty){
        super.updateItem(t, empty);
        ViewController controller = new ViewController();

        MenuItem viewItem = new MenuItem("View");
        viewItem.setOnAction(event -> {
           controller.setViewArea(this.getItem());
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deleting Transaction: " + this.getItem() + " ?");
            alert.setTitle("Delete");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                try {
                    ListView<Transaction> listView = this.getListView();
                    listView.getItems().remove(this.getItem());
                    controller.deleteTransaction(this.getItem());
                } catch (Exception e) {
                    ErrorAlert errorAlert = new ErrorAlert(e.getLocalizedMessage());
                    errorAlert.display();
                }
            }
        });

        ContextMenu contextMenu = new ContextMenu(viewItem, deleteItem);

        if(empty || t == null){
            setText(null);
            setGraphic(null);
            setContextMenu(null);
        }
        else{
            setText(t.toString());
            setContextMenu(contextMenu);
            setGraphic(null);
        }

        emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                setContextMenu(null);
            } else {
                setContextMenu(contextMenu);
            }
        });
    }
}
