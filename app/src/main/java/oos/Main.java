/**
 * OOS Praktikum
 * David Rechkemmer
 */
package oos;

import oos.bank.exceptions.AccountAlreadyExistsException;
import oos.bank.exceptions.AccountDoesNotExistException;
import oos.bank.exceptions.TransactionAlreadyExistException;
import oos.bank.transactions.*;
import oos.bank.PrivateBank;

import java.util.Arrays;
import java.util.List;

/**
 * Main Class
 */
public class Main{

    /**
     * main method
     * @param args String Array of Arguments
     */
    public static void main(String args[]){
        Payment pay = new Payment("01.01.01", 200.5, "rent");
        //System.out.print(pay);
        Payment pay2 = new Payment("02.02.02", 100, "power bill", 0.05, 0.1);
        //System.out.print(pay2);
        Payment pay3 = new Payment(pay);
        //System.out.print(pay3);

        Transfer trans = new Transfer("03.03.03", 200.5, "bla");
        //System.out.print(trans);
        Transfer trans2 = new Transfer("04.04.04", 100.9, "blabla", "bob", "tim");
        //System.out.print(trans2);
        Transfer trans3 = new Transfer(trans2);
        //System.out.print(trans3);

        //if(trans3.equals(trans2))
            //System.out.println("Test succesful");

        //if(trans.equals(trans2))
            //System.out.println("Equal");


        // Prak 3

        PrivateBank pb = new PrivateBank("test", 0.05, 0.07, "Accounts");
        PrivateBank pb2 = new PrivateBank("test", 0.05, 0.07, "Accounts2");

        Transfer it = new IncomingTransfer(trans2);
        Transfer ot = new OutgoingTransfer(trans2);
        List<Transaction> l = Arrays.asList(it, ot);

        if(pb.equals(pb2)){
            System.out.println("Banks are Equal");
        }
        try {
            pb.createAccount("test");
            pb.addTransaction("test", pay);
            pb.addTransaction("test", trans2);
            pb.createAccount("test2", l);
        } catch(AccountAlreadyExistsException |TransactionAlreadyExistException  | AccountDoesNotExistException e){
            e.printStackTrace();
        }
        System.out.println(pb);
        System.out.println(pb2);

        System.out.println("Account Balance fpr Test2: " + pb.getAccountBalance("test2"));

        // P4
        List<Transaction> list = Arrays.asList(new Transaction[]{trans, pay});
        try {
            pb.createAccount("dave", list);
        }catch(AccountAlreadyExistsException e){
            e.printStackTrace();
        }
    }
}