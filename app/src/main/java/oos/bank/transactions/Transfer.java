/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.transactions;               //defines package-env of the following code

import oos.bank.CalculateBill;

/**
 * Transfer Class inherits from super Class Transaction
 * implements CalculateBill class , @see CalculateBill
 * @see Transaction
 */
public class Transfer extends Transaction implements CalculateBill {
    private String sender;
    private String recipient;

    /**
     * Constructor method, call Constructor of Transaction
     * @param date Date of Transfer
     * @param amount Amount of Transfer
     * @param descrp Description
     */
    public Transfer(String date, double amount, String descrp){
        super(date, amount, descrp);
    }

    /**
     * Constructor for Transfer, calls super constructor,
     * sets sender, recipient
     * @param date Date of Transfer
     * @param amount Amoutn of Transfer
     * @param descrp Description
     * @param sender Sender of Transfer
     * @param recipient Recipient of Transfer
     */
    public Transfer(String date, double amount, String descrp, String sender, String recipient){
        super(date, amount, descrp);
        this.sender = sender;
        this.recipient = recipient;
    }

    /**
     * Copy Constructor for Transfer
     * @param trans Transfer Object
     */
    public Transfer(Transfer trans){
        super(trans.getDate(), trans.getAmount(), trans.getDescription());
        sender = trans.getSender();
        recipient = trans.getRecipient();
    }

    /**
     * set Sender of Transfer
     * @param sender Sender of Transfer
     */
    protected void setSender(String sender){
        this.sender = sender;
    }

    /**
     * set Recipient
     * @param recipient
     */
    protected void setRecipient(String recipient){
        this.recipient = recipient;
    }

    /**
     * get Sender
     * @return sender
     */
    public String getSender(){
        return sender;
    }

    /**
     * getRecipient
     * @return recipient
     */
    public String getRecipient(){
        return recipient;
    }


    /**
     * transfers an Object to a String
     * overwriting Object.toString()
     * @return s Object as String
     */
    @Override
    public String toString(){
        return "{ \n" + super.toString() + "Sender: " + sender + "\n" + "Recipient: " + recipient + "\n} \n";
    }

    /**
     * check if obj is the same as Object method is called on
     * @see Object
     * @param obj Object
     * @return  boolean
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(!(obj instanceof Transfer)) return false;
        Transfer objT = (Transfer) obj;
        return super.equals(objT) && objT.getSender().equals(sender) && objT.getRecipient().equals(recipient);
    }
}
