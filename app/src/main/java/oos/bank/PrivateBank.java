/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.*;
import java.io.IOException;
import com.google.gson.*;

import com.google.gson.reflect.TypeToken;
import oos.bank.exceptions.*;
import oos.bank.transactions.*;

/**
 * Class for a generic bank. Implements Bank Interface.
 * Handles interests, accounts and associated transactions
 */
public class PrivateBank implements Bank{
    private String name;
    private double incomingInterest;
    private double outgoingInterest;
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<String, List<Transaction>>();
    private String directoryName;

    /**
     * PrivateBank Constructor initialize name and interests
     * @param name Name of the Bank
     * @param in incomingInterest
     * @param out outgoingInterest
     */
    public PrivateBank(String name, double in, double out, String directoryName){
        this.name = name;
        outgoingInterest = out;
        incomingInterest = in;
        this.directoryName = directoryName;
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

    /**
     * readAccounts() reads all persistent stored accounts from filesystem
     * @throws IOException
     */
    public void readAccounts() throws IOException {

        Path fileName = Path.of("Accounts/KontoDave.json");
        String json = Files.readString(fileName);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new CustomDeserializer())
                .create();

        Transaction[] list = gson.fromJson(json, Transaction[].class);

        try {
            createAccount("Dave", Arrays.asList(list));
        }catch(AccountAlreadyExistsException e){
            e.printStackTrace();
        }
    }

    /**
     * writeAccounts() writes all transactions from account to filesystem
     * filename = Konto<Account>.json
     * uses directoryName attribute to specify location in filesystem
     * @param account to be stored persistent
     * @throws IOException
     */
    public void writeAccounts(String account) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Transaction.class, new CustomSerializer())
                .setPrettyPrinting()
                .create();

        String accountName = account.substring(0,1).toUpperCase() + account.substring(1);
        Writer writer = new FileWriter(   directoryName + "/" + "Konto" + accountName + ".json");
        gson.toJson(getTransactions(account), writer);
        writer.flush();
        writer.close();
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

    /**
     * CustomSerializer Class implements JsonSerializer
     * handles serialization of Transaction Class
     */
    private static class CustomSerializer implements JsonSerializer<Transaction> {
        @SuppressWarnings("unchecked")
        @Override
        public JsonElement serialize(Transaction src, Type typeOfSrc, JsonSerializationContext context) {
            Gson gson = new Gson();
            JsonElement el = gson.toJsonTree(src, typeOfSrc);
            JsonObject obj = new JsonObject();
            obj.addProperty("CLASSNAME", src.getClass().getSimpleName());
            obj.add("INSTANCE", el);
            return obj;
        }
    }

    /**
     * CustomDeserializer implements JsonDeserializer
     * handles deserialization of Transaction Class
     */
    private static class CustomDeserializer implements JsonDeserializer<Transaction> {
        @SuppressWarnings("unchecked")
        @Override
        public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Gson gson = new Gson();
            JsonObject obj = json.getAsJsonObject();
            JsonElement el = obj.get("CLASSNAME");
            if(el == null)
                throw new JsonParseException("Object member field CLASSNAME not found");
            String className = el.getAsString();

            switch(className) {
                case "Payment":
                    return gson.fromJson(obj.get("INSTANCE"), Payment.class);
                case "IncomingTransfer":
                    return gson.fromJson(obj.get("INSTANCE"), IncomingTransfer.class);
                case "OutgoingTransfer":
                    return gson.fromJson(obj.get("INSTANCE"), OutgoingTransfer.class);
                case "Transfer":
                    return gson.fromJson(obj.get("INSTANCE"), Transfer.class);
                default:
                    return null;
            }
        }
    }
}