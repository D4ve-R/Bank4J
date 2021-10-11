package bank;

public class Transaction{
    private String date;
    private double amount;
    private String description;

    public Transaction(String date, double amount, String description){
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

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
        return this.date;
    }
    public double getAmount(){
        return this.amount;
    }
    public String getDescription(){
        return this.description;
    }
}