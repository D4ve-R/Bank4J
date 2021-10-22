package bank;

/**
 * Transaction Class, super class for Transfer & Payment
 */
public class Transaction{
    private String date;
    private double amount;
    private String description;

    /**
     * Constructor method, sets attributes
     * @param date Date of Transaction
     * @param amount Amount of Transaction
     * @param description Description
     */
    protected Transaction(String date, double amount, String description){
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /**
     * set Date of Transaction
     * @param date
     */
    protected void setDate(String date){
        this.date = date;
    }

    /**
     * set Amount of Transaction
     * @param amount
     */
    protected void setAmount(double amount){
        this.amount = amount;
    }

    /**
     * set Descritption of Transaction
     * @param description
     */
    protected void setDescription(String description){
        this.description = description;
    }

    /**
     * get Date of Transaction
     * @return date
     */
    protected String getDate(){
        return date;
    }

    /**
     * get Amount of Transaction
     * @return amount
     */
    protected double getAmount(){
        return amount;
    }

    /**
     * get Description of Transaction
     * @return description
     */
    protected String getDescription(){
        return description;
    }

    /**
     * transfers an Object to a String
     * overwriting Object.toString()
     * @return s Object as String
     */
    @Override
    public String toString(){
        String s = "Date: " + date + "\n" + "Amount: " + amount + "\n" + "Description: " + description + "\n";
        return s;
    }

}