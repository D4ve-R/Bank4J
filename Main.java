import bank.Payment;
import bank.Transfer;

public class Main{
    public static void main(String argvs[]){
        Payment pay = new Payment("dd", 2, "hello");
        pay.setAmount(2.4);
    }
}