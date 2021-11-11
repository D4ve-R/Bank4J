package bank.exceptions;

import bank.Transaction;

public class TransactionAlreadyExistException extends Exception{
    public TransactionAlreadyExistException(Transaction t){
        super("Error! Transaction already exits Transaction: " + t.toString());
    }
}
