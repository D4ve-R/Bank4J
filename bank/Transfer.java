package bank;               //defines package-env of the following code

/**
 * Transfer Class inherits from super Class Transaction
 * implements CalculateBill class , @see CalculateBill
 * @see Transaction
 */
public class Transfer extends Transaction implements CalculateBill{
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
    public void setSender(String sender){
        this.sender = sender;
    }

    /**
     * set Recipient
     * @param recipient
     */
    public void setRecipient(String recipient){
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
     * calculate interest
     * overwritten from CalculateBill Class
     * @see CalculateBill
     * @return amount
     */
    @Override
    public double calculate(){
        return getAmount();
    }

    /**
     * transfers an Object to a String
     * overwriting Object.toString()
     * @return s Object as String
     */
    @Override
    public String toString(){
        setAmount(calculate());
        String s = "{ \n" + super.toString();
        s += "Sender: " + sender + "\n" + "Recipient: " + recipient + "\n} \n";
        return s;
    }

    public boolean equals(Transfer o){
        if(super.equals(o) && o.getSender() == getSender() && o.getRecipient() == getRecipient())
            return true;
        else
            return false;
    }
}
