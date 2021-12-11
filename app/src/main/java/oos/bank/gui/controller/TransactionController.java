/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oos.bank.gui.cellfactory.TransactionCellFactory;
import oos.bank.gui.dialog.ErrorAlert;
import oos.bank.transactions.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * handles creation of a new transaction through AddTransactionDialog
 */
public class TransactionController extends ViewController {
    private Stage stageCtrl;
    private ViewController parentControllerInstance;

    @FXML TextField date;
    @FXML TextField amount;
    @FXML TextField description;
    @FXML TextField sender;
    @FXML TextField recipient;
    @FXML CheckBox payment;
    @FXML CheckBox transfer;

    @Override
    public void setStage(Stage stage){
        stageCtrl = stage;
    }

    public void setParentController(ViewController parentControllerInstance){
        this.parentControllerInstance = parentControllerInstance;
    }

    public void cancel(){
        stageCtrl.close();
    }

    @Override
    public void addTransaction(){
        double amnt = Double.parseDouble(amount.getText());
        String dt = date.getText();
        String descrp = description.getText();
        try {
            if(payment.isSelected()) {
                Payment pay = new Payment(dt, amnt, descrp);
                addTransaction(pay);
            }
            else if(transfer.isSelected()){
                if(sender.getText() != null && sender.getText().equals(stage.getUserData())) {
                    OutgoingTransfer trans = new OutgoingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());
                    addTransaction(trans);
                }
                else {
                    IncomingTransfer trans = new IncomingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());
                    addTransaction(trans);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            ErrorAlert alert = new ErrorAlert(e.getLocalizedMessage());
            alert.display();
        }
        parentControllerInstance.updateBalance();
        stageCtrl.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
