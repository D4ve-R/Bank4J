package bank;

import java.util.*;
import bank.exceptions.*;

public class PrivateBank implements Bank{
    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<String, List<Transaction>>();

    /**
     * PrivateBank Constructor initialize name and interests
     * @param name Name of the Bank
     * @param in incomingInterest
     * @param out outgoingInterest
     */
    public PrivateBank(String name, double in, double out){
        this.name = name;
        outgoingInterest = out;
        incomingInterest = in;
    }

    /**
     * PrivateBank Copy Constructor initialize from other
     * PrivateBank obj
     * @param bank PrivateBank obj to be copied
     */
    public PrivateBank(PrivateBank bank){
        name = bank.getName();
        incomingInterest = bank.getIncomingInterest();
        outgoingInterest = bank.getOutgoingInterest();
    }

    /**
     * getName returns the name attr of Bank Obj
     * @return name
     */
    public String getName(){ return name; }

    /**
     * setName sets the name attr of Bank Obj
     * @param name
     */
    public void setName(String name) { this.name = name; }

    /**
     * getIncomingInterest returns the incomingInterest attr of Bank obj
     * @return incomingInterest
     */
    public double getIncomingInterest(){ return incomingInterest; }

    /**
     * setIncomingInterest sets the incomingInterest attr of Bank obj
     * @param in
     */
    public void setIncomingInterest(double in) { incomingInterest = in; }

    /**
     * getOutgoingInterest returns the outgoingInterest attr of Bank obj
     * @return outgoingInterest
     */
    public double getOutgoingInterest() { return outgoingInterest; }

    /**
     * setOutgoingInterest setss the outgoingInterest attr of Bank obj
     * @param out
     */
    public void setOutgoingInterest(double out) { outgoingInterest = out;}

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException(account);
        }
        else{
            accountsToTransactions.put(account, new ArrayList<Transaction>());
        }
    }

    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException {
        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException(account);
        }
        else {
            accountsToTransactions.put(account, transactions);
        }

    }

    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException {
        if(!accountsToTransactions.containsKey(account)){
            throw new AccountDoesNotExistException(account);
        }
        else {
            List list = accountsToTransactions.get(account);
            if (list.contains(transaction)) {
                throw new TransactionAlreadyExistException(transaction);
            }
            else {
                if(transaction instanceof Payment){
                    ((Payment) transaction).setIncomingInterest(incomingInterest);
                    ((Payment) transaction).setOutgoingInterest(outgoingInterest);
                }
                list.add(transaction);
            }
        }
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws TransactionDoesNotExistException, AccountDoesNotExistException {
        if(!accountsToTransactions.containsKey(account)){
            throw new AccountDoesNotExistException(account);
        }
        else {
            List list = accountsToTransactions.get(account);
            if (!list.contains(transaction)) {
                throw new TransactionDoesNotExistException(transaction);
            }
            else {
                list.remove(transaction);
            }
        }
    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        return accountsToTransactions.get(account).contains(transaction);
    }

    @Override
    public double getAccountBalance(String account) {
        return 0;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        return accountsToTransactions.get(account);
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        return accountsToTransactions.get(account);
    }

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        return null;
    }

    @Override
    public String toString(){
        return "Bank: { \n Name: " + name + "\nIncomingInterest: " + incomingInterest + "\nOutgoingInterest: " + outgoingInterest + "\nCustomers: " + accountsToTransactions.size() + "}\n";
    }

    @Override
    public boolean equals(Object obj){
        return true;
    }
}
