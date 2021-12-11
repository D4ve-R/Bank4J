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

/**
 * handles creation of a new transaction through AddTransactionDialog
 */
public class TransactionController extends Controller {
    private Stage stageCtrl;
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

    public void cancel(){
        stageCtrl.close();
    }

    public void add(){
        double amnt = Double.parseDouble(amount.getText());
        String dt = date.getText();
        String descrp = description.getText();
        try {
            if(payment.isSelected()) {
                Payment pay = new Payment(dt, amnt, descrp);
                pb.addTransaction((String) stage.getUserData(), pay);
            }
            else if(transfer.isSelected()){
                if(amnt < 0) {
                    OutgoingTransfer trans = new OutgoingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());
                    pb.addTransaction((String) stage.getUserData(), trans);
                }
                else {
                    IncomingTransfer trans = new IncomingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());
                    pb.addTransaction((String) stage.getUserData(), trans);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            ErrorAlert alert = new ErrorAlert(e.getLocalizedMessage());
            alert.display();
        }
        stageCtrl.close();
    }
}
