package bank;

//SuperClass
public class Transaction{
    private String date;
    private double amount;
    private String description;

    //Constructor
    protected Transaction(String date, double amount, String description){
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /*
    setter & getter methods
     */
    protected void setDate(String date){
        this.date = date;
    }
    protected void setAmount(double amount){
        this.amount = amount;
    }
    protected void setDescription(String description){
        this.description = description;
    }
    protected String getDate(){
        return date;
    }
    protected double getAmount(){
        return amount;
    }
    protected String getDescription() { return description; }

    protected void printTransactionData(){
        System.out.println("Date: " + date);
        System.out.println("Amount: " + amount);
        System.out.println("Description: " + description);
    }
}