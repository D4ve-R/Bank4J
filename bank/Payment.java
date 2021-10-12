package bank;
import bank.Transaction;

//Payment Class derived from Transaction Class
public class Payment extends Transaction{
    private double incomingInterest;
    private double outgoingInterest;

    //Super-Constructor
    public Payment(String date, double amount, String descrp){
        super(date, amount, descrp);
    }

    //Constructor
    public Payment(String date, double amount, String descrp, double incomingInterest, double outgoingInterest){
        super(date, amount, descrp);
        this.incomingInterest = incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }

    //Copy-Constructor
    public Payment(Payment pay){
        super(pay.getDate(), pay.getAmount(), pay.getDescription());
        incomingInterest = pay.getIncomingInterest();
        outgoingInterest = pay.getOutgoingInterest();
    }

    /*
    setter methods
     */
    public void setIncomingInterest(double in){
        incomingInterest = in;
    }
    public void setOutgoingInterest(double out){
        outgoingInterest = out;
    }

    /*
    getter methods
     */
    public double getIncomingInterest(){
        return incomingInterest;
    }
    public double getOutgoingInterest(){
        return outgoingInterest;
    }

    //print out Object
    public void printObj(){
        System.out.println("{");
        super.printTransactionData();
        System.out.println("IncomingInterest: " + incomingInterest);
        System.out.println("OutgoingInterest: " + outgoingInterest);
        System.out.println("}");
    }
}
