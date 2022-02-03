package oos.bank;

public interface CurrencyConverter {
    default double convertEURtoUSD(double eur){
        return eur * 1.15;
    }

    default double convertUSDtoEUR(double usd){
        return usd / 1.15;
    }
}
