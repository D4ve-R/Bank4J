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
import oos.bank.transactions.IncomingTransfer;
import oos.bank.transactions.OutgoingTransfer;
import oos.bank.transactions.Payment;
import oos.bank.transactions.Transfer;

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

    public void addTransaction(){
        double amnt = Double.parseDouble(amount.getText());
        String dt = date.getText();
        String descrp = description.getText();
        System.out.println(amnt);
        try {
            if(payment.isSelected()) {
                Payment pay = new Payment(dt, amnt, descrp);
                pb.addTransaction((String) stage.getUserData(), pay);
            }
            else if(transfer.isSelected()){
                Transfer trans = null;
                if(amnt < 0)
                    trans = new OutgoingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());
                else
                    trans = new IncomingTransfer(dt, amnt, descrp, sender.getText(), recipient.getText());

                pb.addTransaction((String) stage.getUserData(), trans);
            }
        }catch(Exception e){
            e.printStackTrace();
            ErrorAlert alert = new ErrorAlert(e.getLocalizedMessage());
            alert.display();
        }
        stageCtrl.close();
    }
}
