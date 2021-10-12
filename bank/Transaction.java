package bank;
import java.io.*;

//SuperClass
public class Transaction{
    private String date;
    private double amount;
    private String description;

    //Constructor
    public Transaction(String date, double amount, String description){
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /*
    setter & getter methods
     */
    public void setDate(String date){
        this.date = date;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDate(){
        return date;
    }
    public double getAmount(){
        return amount;
    }
    public String getDescription() { return description; }

    public void printTransactionData(){
        System.out.println("Date: " + date);
        System.out.println("Amount: " + amount);
        System.out.println("Description: " + description);
    }
}