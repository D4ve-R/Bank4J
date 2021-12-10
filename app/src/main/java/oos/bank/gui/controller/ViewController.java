/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import oos.bank.gui.dialog.AddAccountDialog;
import oos.bank.transactions.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController extends Controller {
    private String account;
    private ObservableList<Transaction> items;
    @FXML Label accountName;
    @FXML Label balance;
    @FXML Label transaction;
    @FXML ListView<Transaction> transactionList;

    @FXML void sortAsc(){
        items.clear();
        items = FXCollections.observableArrayList(pb.getTransactionsSorted(account, true));
        transactionList.setItems(items);
    }

    @FXML void sortDesc(){
        items.clear();
        items = FXCollections.observableArrayList(pb.getTransactionsSorted(account, false));
        transactionList.setItems(items);
    }

    @FXML void onlyPositiv(){
        items.clear();
        items = FXCollections.observableArrayList(pb.getTransactionsByType(account, true));
        transactionList.setItems(items);
    }

    @FXML void onlyNegativ(){
        items.clear();
        items = FXCollections.observableArrayList(pb.getTransactionsByType(account, false));
        transactionList.setItems(items);
    }

    @FXML void backToMain(){
        try {
            switchScene("/main.fxml");
        } catch(Exception e){e.printStackTrace();}
    }

    @FXML void addTransaction(){
        AddAccountDialog dialog = new AddAccountDialog();
        dialog.show();
    }

    /**
     * updates balance to the account's balance
     */
    @Override
    public void updateBalance(){
        balance.setText(String.format("%.2f", pb.getAccountBalance(account)) + " €");
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
        super.initialize(null, null);
        if(account == null){
            account = (String) stage.getUserData();
        }
        accountName.setText(account);
        updateBalance();
        items = FXCollections.observableArrayList(pb.getTransactions(account));
        transactionList.setItems(items);
    }
}
