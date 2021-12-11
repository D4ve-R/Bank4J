/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import oos.bank.gui.cellfactory.TransactionCellFactory;
import oos.bank.gui.dialog.AddTransactionDialog;
import oos.bank.transactions.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController extends Controller {
    private String account;
    private static ObservableList<Transaction> items;
    @FXML Label accountName;
    @FXML Label balance;
    @FXML  Label transaction;
    @FXML ListView<Transaction> transactionList;

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
        items = FXCollections.observableArrayList(bankModel.getTransactions(account));
        transactionList.setItems(items);
        transactionList.setCellFactory(transaction -> new TransactionCellFactory(this));
    }

    /**
     * display the selected Transaction in the sidebar
     * @param t Trasnaction to display
     */
    public void setViewArea(Transaction t){
        transaction.setText(t.toString());
    }

    @FXML void sortAsc(){
        items.clear();
        items = FXCollections.observableArrayList(bankModel.getTransactionsSorted(account, true));
        transactionList.setItems(items);

    }

    @FXML void sortDesc(){
        items.clear();
        items = FXCollections.observableArrayList(bankModel.getTransactionsSorted(account, false));
        transactionList.setItems(items);

    }

    @FXML void onlyPositiv(){
        items.clear();
        items = FXCollections.observableArrayList(bankModel.getTransactionsByType(account, true));
        transactionList.setItems(items);

    }

    @FXML void onlyNegativ(){
        items.clear();
        items = FXCollections.observableArrayList(bankModel.getTransactionsByType(account, false));
        transactionList.setItems(items);
    }

    @FXML void backToMain(){
        try {
            switchScene("/main.fxml");
        } catch(Exception e){e.printStackTrace();}
    }

    public void addTransaction() {
        AddTransactionDialog dialog = new AddTransactionDialog(this);
        dialog.showAndWait();
    }

    public void addTransaction(Transaction t) throws Exception{
        bankModel.addTransaction((String) stage.getUserData(), t);
        items.add(t);
    }

    /**
     * Handles deletion of Transaction
     * @param t Transaction to delete
     * @throws Exception
     */
    public void deleteTransaction(Transaction t) throws Exception {
        bankModel.removeTransaction((String) stage.getUserData(), t);
        transactionList.getItems().remove(t);
        updateBalance();
    }

    /**
     * updates balance to the account's balance
     */
    @Override
    public void updateBalance(){
        balance.setText(String.format("%.2f", bankModel.getAccountBalance(account)) + " €");
    }
}
