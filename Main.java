/*
OOS Praktikum WS2021/22
David Rechkemmer
 */
//import Payment & Transfer Class from bank Package
//import bank.Transfer;
//import bank.Payment;
import bank.*;

public class Main{
    //main
    public static void main(String args[]){
        Payment pay = new Payment("01.01.01", 200.5, "rent");
        System.out.print(pay);
        Payment pay2 = new Payment("02.02.02", 100, "power bill", 0.4, 0.1);
        System.out.print(pay2);
        Payment pay3 = new Payment(pay);
        System.out.print(pay3);

        Transfer trans = new Transfer("03.03.03", 200.5, "bla");
        System.out.print(trans);
        Transfer trans2 = new Transfer("04.04.04", 100.9, "blabla", "bob", "tim");
        System.out.print(trans2);
        Transfer trans3 = new Transfer(trans2);
        System.out.print(trans3);
    }
}