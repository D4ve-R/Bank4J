/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import oos.bank.PrivateBank;

public class BankController {
    @FXML ListView<String> listView;
    @FXML Label label;
    @FXML Label totalAmount;

    public void showAccounts(){
        PrivateBank pb = new PrivateBank();
        label.setText(pb.getName());
        totalAmount.setText(Double.toString(pb.getTotalAmount()));

        listView.setItems(FXCollections.observableArrayList(pb.getAllAccounts()));
        listView.setCellFactory(factory -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());

            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem();
            editItem.textProperty().bind(Bindings.format("Delete ", cell.itemProperty()));
            editItem.setOnAction(event -> {
                String item = cell.getItem();
                System.out.println(item);
            });
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("Delete ", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                try {
                    String item = cell.getItem();
                    pb.deleteAccount(item);
                }catch(Exception e){e.printStackTrace();}
                listView.getItems().remove(cell.getItem());
            });
            contextMenu.getItems().addAll(editItem, deleteItem);

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