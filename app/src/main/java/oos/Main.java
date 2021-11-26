/**
 * OOS Praktikum
 * David Rechkemmer
 */
package oos;

import oos.bank.exceptions.AccountAlreadyExistsException;
import oos.bank.transactions.Payment;
import oos.bank.transactions.Transaction;
import oos.bank.transactions.Transfer;
import oos.bank.PrivateBank;

import java.io.IOException;
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

        if(trans3.equals(trans2))
            System.out.println("Test succesful");

        if(trans.equals(trans2))
            System.out.println("Equal");


        // Prak 3

        PrivateBank pb = new PrivateBank("test", 0.05, 0.07, "Accounts");
        PrivateBank pb2 = new PrivateBank("test", 0.05, 0.07, "");
        if(pb.equals(pb2)){
            System.out.println("Banks are Equal");
        }
        //System.out.println(pb);
        //System.out.println(pb2);

        // P4
        List<Transaction> list = Arrays.asList(new Transaction[]{trans, pay});
        try {
            pb.createAccount("dave", list);
        }catch(AccountAlreadyExistsException e){
            e.printStackTrace();
        }


        try {
            pb.writeAccounts("dave");
            pb.readAccounts();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(pb.getTransactions("Dave"));


        if(pb.containsTransaction("Dave", trans))
            System.out.println("It Works!!");

    }
}