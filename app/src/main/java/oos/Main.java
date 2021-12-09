/**
 * OOS Praktikum
 * David Rechkemmer
 */
package oos;

import javafx.scene.Node;

import javafx.scene.layout.StackPane;
import oos.bank.exceptions.AccountAlreadyExistsException;
import oos.bank.exceptions.AccountDoesNotExistException;
import oos.bank.exceptions.TransactionAlreadyExistException;
import oos.bank.gui.GUIBank;
import oos.bank.transactions.*;
import oos.bank.PrivateBank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Main extends GUIBank {
    public static void main(String args[]){
        Payment pay = new Payment("01.01.01", 200.5, "rent");
        Payment pay2 = new Payment("02.02.02", 100, "power bill", 0.05, 0.1);
        Payment pay3 = new Payment(pay);
        Transfer trans = new Transfer("03.03.03", 200.5, "bla");
        Transfer trans2 = new Transfer("04.04.04", 100.9, "blabla", "bob", "tim");
        Transfer trans3 = new Transfer(trans2);

        PrivateBank pb = new PrivateBank("HSBC", 0.05, 0.07, "BankManager");

        Transfer it = new IncomingTransfer(trans2);
        Transfer ot = new OutgoingTransfer(trans2);
        List<Transaction> l = Arrays.asList(it, ot);


        try {
            pb.createAccount("test");
            pb.addTransaction("test", pay);
            pb.addTransaction("test", trans2);
            pb.createAccount("test2", l);
        } catch(Exception e){
            e.printStackTrace();
        }

        // P4
        List<Transaction> list = Arrays.asList(new Transaction[]{trans, pay});
        try {
            pb.createAccount("dave", list);
        }catch(Exception e){
            e.printStackTrace();
        }

        launch();


    }
}
