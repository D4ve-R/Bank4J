/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    @FXML TextField incomingInterest;
    @FXML TextField outgoingInterest;
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
        if(validateTransactionData()) {
            double amnt = Double.parseDouble(amount.getText());
            String dt = date.getText();
            String descrp = description.getText();
            try {
                if (payment.isSelected() && validatePaymentData()) {
                    double out = Double.parseDouble(outgoingInterest.getText());
                    double in = Double.parseDouble(incomingInterest.getText());
                    Payment pay = new Payment(dt, amnt, descrp, in, out);
                    addTransaction(pay);
                } else if (transfer.isSelected() && validateTransferData()) {
                    if (sender.getText().equals(stage.getUserData())) {
                        OutgoingTransfer trans = new OutgoingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());
                        addTransaction(trans);
                    } else {
                        IncomingTransfer trans = new IncomingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());
                        addTransaction(trans);
                    }
                } else {
                    new ErrorAlert("Missing Payment or Transfer details!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                new ErrorAlert(e.getLocalizedMessage());
            }
        } else new ErrorAlert("Missing Transaction details!");
        parentControllerInstance.updateBalance();
        stageCtrl.close();
    }

    /**
     * neeeds to be empty to override ViewController behaviour
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Validates Transaction Data
     * @return true if data is valid & not empty, else false
     */
    private boolean validateTransactionData(){
        if(date.getText() == null || date.getText().isEmpty()) return false;
        if(amount.getText() == null || amount.getText().isEmpty()) return false;
        if(description.getText() == null || description.getText().isEmpty()) return false;
        return true;
    }

    /**
     * Validates Payment Data
     * @return true if data is valid & not empty, else false
     */
    private boolean validatePaymentData(){
        if(outgoingInterest.getText() == null || outgoingInterest.getText().isEmpty()) return false;
        if(incomingInterest.getText() == null || incomingInterest.getText().isEmpty()) return false;
        return true;
    }

    /**
     * Validates Transfer Data
     * @return true if data is valid & not empty, else false
     */
    private boolean validateTransferData(){
        if(sender.getText() == null || sender.getText().isEmpty()) return false;
        if(recipient.getText() == null || recipient.getText().isEmpty()) return false;
        return true;
    }
}
