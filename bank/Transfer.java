package bank;               //defines package-env of the following code
import java.io.*;
import bank.Transaction;    //import class from package


//Transfer Class derived from Transaction Class
public class Transfer extends Transaction{
    private String sender;
    private String recipient;

    //Super-Constructor
    public Transfer(String date, double amount, String descrp){
        super(date, amount, descrp);
    }

    //Constructor
    public Transfer(String date, double amount, String descrp, String sender, String recipient){
        super(date, amount, descrp);
        this.sender = sender;
        this.recipient = recipient;
    }

    //Copy-Constructor
    public Transfer(Transfer trans){
        super(trans.getDate(), trans.getAmount(), trans.getDescription());
        sender = trans.getSender();
        recipient = trans.getRecipient();
    }

    /*
    setter methods
     */
    public void setSender(String sender){
        this.sender = sender;
    }
    public void setRecipient(String recipient){
        this.recipient = recipient;
    }

    /*
    getter methods
     */
    public String getSender() { return sender; }
    public String getRecipient(){
        return recipient;
    }

    //print out Object
    public void printObj(){
        System.out.println("{");
        printTransactionData();
        System.out.println("Sender: " + sender);
        System.out.println("Recipient: " + recipient);
        System.out.println("}");
    }
}
