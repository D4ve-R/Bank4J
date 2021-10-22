package bank;

/**
 * Payment Class inherits from Transaction Class
 * implements CalculateBill class , @see CalculateBill
 * @see Transaction
 */
public class Payment extends Transaction implements CalculateBill{
    private double incomingInterest;
    private double outgoingInterest;

    /**
     * Constructor method, calls super constructor
     * @param date Date of Payment
     * @param amount Amount of Payment
     * @param descrp Description
     */
    public Payment(String date, double amount, String descrp){
        super(date, amount, descrp);
    }

    /**
     * Constructor method, calls supoer constructor
     * sets incomingInterest & outgoingInterest
     * @param date Date of Payment
     * @param amount Amount of Payment
     * @param descrp Description
     * @param incomingInterest Interest of incoming Payments
     * @param outgoingInterest Interest of outgoing Payments
     */
    public Payment(String date, double amount, String descrp, double incomingInterest, double outgoingInterest){
        super(date, amount, descrp);
        this.incomingInterest = incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Copy Constructor, calls super constructor
     * @param pay Payment Object
     */
    public Payment(Payment pay){
        super(pay.getDate(), pay.getAmount(), pay.getDescription());
        incomingInterest = pay.getIncomingInterest();
        outgoingInterest = pay.getOutgoingInterest();
    }

    /**
     * sets incomingInterest
     * @param in Interest
     */
    public void setIncomingInterest(double in){
        incomingInterest = in;
    }

    /**
     * sets outgoingInterest
     * @param out Interest
     */
    public void setOutgoingInterest(double out){
        outgoingInterest = out;
    }

    /**
     * get incomingInterest
     * @return incomingInterest
     */
    public double getIncomingInterest(){
        return incomingInterest;
    }

    /**
     * get outgoingInterest
     * @return outgoingInterest
     */
    public double getOutgoingInterest(){
        return outgoingInterest;
    }

    /**
     * calculate interest
     * overwritten from CalculateBill Class
     * @see CalculateBill
     * @return amount
     */
    @Override
    public double calculate(){
        double amount = getAmount();
        if(amount > 0){
            return amount - (amount * incomingInterest);
        }
        else{
            return amount - (amount * outgoingInterest);
        }
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
        s += "IncomingInterest: " + incomingInterest + "\n" + "OutgoingInterest: " + outgoingInterest + "\n{ \n";
        return s;
    }

    public boolean equals(Payment o){
        if(super.equals(o) && o.getIncomingInterest() == getIncomingInterest() && o.getOutgoingInterest() == getOutgoingInterest())
            return true;
        else
            return false;
    }
}
