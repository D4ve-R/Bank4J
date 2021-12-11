/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.dialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import oos.bank.gui.controller.TransactionController;
import oos.bank.gui.controller.ViewController;
import oos.bank.transactions.Transaction;

public class AddTransactionDialog extends Dialog<Transaction> {
    public AddTransactionDialog(ViewController parentControllerInstance){
        final DialogPane pane = getDialogPane();
        setTitle("Transaction Dialog");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/transaction.fxml"));
        try {
            pane.setContent(loader.load());
        } catch(Exception e){ e.printStackTrace();}
        TransactionController controller = loader.getController();
        controller.setStage((Stage) pane.getScene().getWindow());
        controller.setParentController(parentControllerInstance);
    }
}
