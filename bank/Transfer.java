package bank;
import bank.Transaction;

public class Transfer extends Transaction{
    private String sender;
    private String recipient;

    public Transfer(String date, double amount, String descrp){
        super(date, amount, descrp);
    }
    public Transfer(String date, double amount, String descrp, String sender, String recipient){
        super(date, amount, descrp);
        this.sender = sender;
        this.recipient = recipient;
    }

    public void setSender(String sender){
        this.sender = sender;
    }
    public void setRecipient(String recipient){
        this.recipient = recipient;
    }

    public String getSender(){
        return sender;
    }
    public String getOutgoingInterest(){
        return recipient;
    }
}
