/**
 * OOS Praktikum
 * David Rechkemmer
 */

package bank;

public class IncomingTransfer extends Transfer{
    private double incomingInterest = 0.05;
    public IncomingTransfer(String date, double amount, String descrp, double interest) {
        super(date, amount, descrp);
        incomingInterest = interest;
    }

    public IncomingTransfer(String date, double amount, String descrp, String sender, String recipient, double interest) {
        super(date, amount, descrp, sender, recipient);
        incomingInterest = interest;
    }

    public IncomingTransfer(Transfer trans, double interest){
        super(trans);
        incomingInterest = interest;
    }

    @Override
    public double calculate() {
        return super.calculate() * (1 - incomingInterest);
    }
}
