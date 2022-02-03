/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank;

public class BankUSDAdapter implements CurrencyConverter{
    private Bank bank;

    public BankUSDAdapter(Bank bank){
        this.bank = bank;
    }

    public double getAccountBalance(String account){
        return convertEURtoUSD(bank.getAccountBalance(account));
    }

    public double getTotalAmount(){
        return convertEURtoUSD(bank.getTotalAmount());
    }
}
