/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank;

import java.util.*;
import oos.exceptions.*;

/**
 * Class for a generic bank. Implements Bank Interface.
 * Handles interests, accounts and associated transactions
 */
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

    /**
     * getAccounts returns the accountsToTransaction Hashmap of Bank obj
     * @return accountsToTransactions
     */
    public Map<String, List<Transaction>> getAccounts(){
        return accountsToTransactions;
    }

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
        double sum = 0;
        for(Transaction t : getTransactions(account)){
            sum += t.calculate();
        }
        /*
        for(Object t : accountsToTransactions.get(account)){
            if(t instanceof Transfer){
                Transfer tf = (Transfer) t;
                if(tf.getSender() == account){
                   sum -= tf.calculate();
                }
                else{
                    sum += tf.calculate();
                }
            }
            else{
                Transaction ts = (Transaction) t;
                sum += ts.calculate();
            }
        }
        */
        return sum;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        return accountsToTransactions.get(account);
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> list = getTransactions(account);
        Collections.sort(list, (t1,t2)->{return (int)(t1.getAmount() - t2.getAmount());} );
        if(!asc) Collections.reverse(list);
        return list;
    }

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> list = getTransactions(account);
        if(positive){
            list.removeIf(t -> t.getAmount() < 0);
        }
        else {
            list.removeIf(t -> t.getAmount() >= 0);
        }
        return list;
    }

    @Override
    public String toString(){
        return "Bank: { \n Name: " + name + "\n IncomingInterest: " + incomingInterest + "\n OutgoingInterest: " + outgoingInterest + "\n Customers: " + accountsToTransactions.size() + "\n}\n";
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(!(obj instanceof PrivateBank)) return false;
        PrivateBank other = (PrivateBank) obj;
        return name == other.getName() && incomingInterest == other.getIncomingInterest() && outgoingInterest == other.getOutgoingInterest() && accountsToTransactions.equals(other.getAccounts());
    }
}
