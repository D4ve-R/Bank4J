/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.dialog;

import javafx.scene.control.TextInputDialog;

public class AddAccountDialog extends TextInputDialog {
    public AddAccountDialog(){
        setTitle("Add Account");
        setHeaderText("Create a new Account");
        setContentText("Account name : ");
    }
}
