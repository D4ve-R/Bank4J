/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.util.stream.Collectors;

import com.google.gson.*;

import oos.bank.exceptions.*;
import oos.bank.transactions.*;

/**
 * Class for a generic bank. Implements Bank Interface.
 * Handles interests, accounts and associated transactions
 */
public class PrivateBank implements Bank {
    private static String name;
    private static double incomingInterest;
    private static double outgoingInterest;
    private static Map<String, List<Transaction>> accountsToTransactions = new HashMap<String, List<Transaction>>();
    private static String directoryName;

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


        try {
            readAccounts();
        }catch(IOException e) {
            e.printStackTrace();
        }

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
        directoryName = bank.getDirectoryName();
    }

    public PrivateBank(){
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

    public String getDirectoryName(){
        return directoryName;
    }

    /**
     * readAccounts() reads all persistent stored accounts from filesystem
     * searches for the directory in the user home path and creates it, if not exists
     * loops over all files in the dir
     * @throws IOException
     */
    private void readAccounts() throws IOException {
        List<String> files = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new CustomDeserializer())
                .create();

        Path path = Paths.get(System.getProperty("user.home") + File.separator + directoryName);
        if(!Files.exists(path)) {
            Files.createDirectory(path);
            return;
        }

        try {
            files = Files.list(path)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }catch(IOException e){
            e.printStackTrace();
        }

        for(String filePath : files){
            Path fileName = Path.of(filePath);
            String json = "";
            try {
                json = Files.readString(fileName);
            } catch(IOException e){
                e.printStackTrace();
            }

            Transaction[] list = gson.fromJson(json, Transaction[].class);

            try {
                String[] arr = filePath.split("/Konto", 2);
                String[] account = arr[1].split(".json");
                if(account != null && list != null)
                    createAccount(account[0], Arrays.asList(list));
            }catch(AccountAlreadyExistsException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * writeAccounts() writes all transactions from account to filesystem
     * filename = Konto<Account>.json
     * uses directoryName attribute to specify location in filesystem
     * @param account to be stored persistent
     * @throws IOException
     */
    private void writeAccounts(String account) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Transaction.class, new CustomSerializer())
                .setPrettyPrinting()
                .create();

        Writer writer = new FileWriter(  System.getProperty("user.home") + File.separator + directoryName + File.separator + "Konto" + account + ".json");
        gson.toJson(getTransactions(account), writer);
        writer.flush();
        writer.close();
    }

    /**
     * get all accounts
     *
     * @return List containing all accounts
     */
    @Override
    public List<String> getAllAccounts() {
        return new ArrayList<String>(accountsToTransactions.keySet());
    }

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException{
        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException(account);
        }
        else{
            accountsToTransactions.put(account, new ArrayList<Transaction>());
            writeAccounts(account);
        }
    }

    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, IOException {
        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException(account);
        }
        else {
            accountsToTransactions.put(account, transactions);
            writeAccounts(account);
        }

    }

    /**
     * deletes the account matching account
     *
     * @param account
     * @throws AccountDoesNotExistException
     * @throws IOException
     */
    @Override
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
        if(accountsToTransactions.containsKey(account)) {
            accountsToTransactions.remove(account);
            writeAccounts(account);
            Files.deleteIfExists(Paths.get(System.getProperty("user.home") + File.separator + directoryName + File.separator + "Konto" + account + ".json"));
        }
        else
            throw new AccountDoesNotExistException(account);
    }

    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, IOException {
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
                writeAccounts(account);
            }
        }
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws TransactionDoesNotExistException, AccountDoesNotExistException, IOException {
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
                writeAccounts(account);
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
        return sum;
    }

    /**
     * Returns the sum of all accounts's amount
     * complexity n^2
     * @return total money in the bank
     */
    @Override
    public double getTotalAmount() {
        double sum = 0;
        for(String account : getAllAccounts())
            sum += getAccountBalance(account);
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
