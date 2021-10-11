package bank;
import bank.Transaction;

public class Payment extends Transaction{
    private double incomingInterest;
    private double outgoingInterest;

    public Payment(String date, double amount, String descrp){
        super(date, amount, descrp);
    }
    public Payment(String date, double amount, String descrp, double incomingInterest, double outgoingInterest){
        super(date, amount, descrp);
        this.incomingInterest = incomingInterest;
        this.outgoingInterest = outgoingInterest;
    }

    public void setIncomingInterest(double in){
        incomingInterest = in;
    }
    public void setOutgoingInterest(double out){
        outgoingInterest = out;
    }

    public double getIncomingInterest(){
        return incomingInterest;
    }
    public double getOutgoingInterest(){
        return outgoingInterest;
    }
}
