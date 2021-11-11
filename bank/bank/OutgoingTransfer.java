/**
 * OOS Praktikum
 * David Rechkemmer
 */

package bank;

public class OutgoingTransfer extends Transfer{
    private double outgoingInterest;

    public OutgoingTransfer(String date, double amount, String descrp, double interest) {
        super(date, amount, descrp);
        outgoingInterest = interest;
    }

    public OutgoingTransfer(String date, double amount, String descrp, String sender, String recipient, double interest) {
        super(date, amount, descrp, sender, recipient);
        outgoingInterest = interest;
    }

    public OutgoingTransfer(Transfer trans, double interest) {
        super(trans);
        outgoingInterest = interest;
    }

    @Override
    public double calculate(){
        return super.calculate() * (1 - outgoingInterest);
    }
}
