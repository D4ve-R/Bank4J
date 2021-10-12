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
        pay.printObj();
        Payment pay2 = new Payment("02.02.02", 100.9, "power bill", 23.4, 34.5);
        pay2.printObj();
        Payment pay3 = new Payment(pay);
        pay3.printObj();

        Transfer trans = new Transfer("03.03.03", 200.5, "bla");
        trans.printObj();
        Transfer trans2 = new Transfer("04.04.04", 100.9, "blabla", "bob", "tim");
        trans2.printObj();
        Transfer trans3 = new Transfer(trans2);
        trans3.printObj();
    }
}