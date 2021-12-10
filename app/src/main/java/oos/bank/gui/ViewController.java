/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ViewController extends Controller{
    private String account;
    @FXML Label accountName;
    @FXML Label balance;
    @FXML Label transaction;
    @FXML ListView transactionList;

    @FXML void sortAsc(){
        transactionList.setItems(FXCollections.observableArrayList(pb.getTransactionsSorted(account, true)));
    }

    @FXML void sortDesc(){
        transactionList.setItems(FXCollections.observableArrayList(pb.getTransactionsSorted(account, false)));
    }

    @FXML void onlyPositiv(){
        transactionList.setItems(FXCollections.observableArrayList(pb.getTransactionsByType(account, true)));
    }

    @FXML void onlyNegative(){
        transactionList.setItems(FXCollections.observableArrayList(pb.getTransactionsByType(account, false)));
    }

    @FXML void backToMain(){

    }

    @FXML void addTransaction(){

    }

    public void init(){
        super.init();
        account = (String) stage.getUserData();
        accountName.setText(account);
        balance.setText(pb.getAccountBalance(account) + " €");
        transactionList.setItems(FXCollections.observableArrayList(pb.getTransactions(account)));
    }
}
