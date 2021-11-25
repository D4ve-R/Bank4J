/**
 * OOS Praktikum
 * David Rechkemmer
 */

package bank;

/**
 * Transaction Class
 */
public abstract class Transaction implements CalculateBill{
    private String date;
    private double amount;
    private String description;

    /**
     * Constructor method, sets attributes
     * @param date Date of Transaction
     * @param amount Amount of Transaction
     * @param description Description
     */
    public Transaction(String date, double amount, String description){
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
    public String getDate(){
        return date;
    }

    /**
     * get Amount of Transaction
     * @return amount
     */
    public double getAmount(){
        return amount;
    }

    /**
     * get Description of Transaction
     * @return description
     */
    public String getDescription(){
        return description;
    }

    /**
     * calculate method from CalcuclateBill
     * @see CalculateBill
     * @return double calculated Amount
     */
    public double calculate(){
        return amount;
    }

    /**
     * transfers an Object to a String
     * overwriting Object.toString()
     * @return s Object as String
     */
    @Override
    public String toString(){
        return "Date: " + date + "\n" + "Amount: " + calculate() + "\n" + "Description: " + description + "\n";
    }

    /**
     * check if obj is the same as Object method is called on
     * @param obj Object
     * @return  boolean
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(!(obj instanceof Transaction)) return false;
        Transaction objT = (Transaction) obj;
        return objT.getDate().equals(date) && objT.getAmount() == amount && objT.getDescription().equals(description);
    }

}