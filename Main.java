/*
OOS Praktikum WS2021/22
David Rechkemmer
 */
//import Payment & Transfer Class from bank Package
import bank.Payment;
import bank.Transfer;

public class Main{
    //main
    public static void main(String args[]){
        Payment pay = new Payment("01.01.01", 200.5, "hello");
        pay.printObj();
        Payment pay2 = new Payment("02.02.02", 100.9, "bye", 23.4, 34.5);
        pay2.printObj();
        Payment pay3 = new Payment(pay);
        pay3.printObj();

        Transfer trans = new Transfer("01.01.01", 200.5, "hello");
        trans.printObj();
        Transfer trans2 = new Transfer("02.02.02", 100.9, "bye", "bob", "tim");
        trans2.printObj();
        Transfer trans3 = new Transfer(trans2);
        trans3.printObj();
    }
}