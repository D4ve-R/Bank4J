/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.gui.dialog;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AddAccountDialog extends Dialog<ButtonType> {
    public AddAccountDialog(){
        setTitle("Add Account");
        setHeaderText("adding account to persistent storage");

        Label label = new Label("Account Name: ");
        TextField text = new TextField();
        GridPane grid = new GridPane();
        grid.add(label, 1,1);
        grid.add(text, 2, 1);
        getDialogPane().setContent(grid);

        getDialogPane().getButtonTypes().add(new ButtonType("Add", ButtonBar.ButtonData.OK_DONE));
    }
}
