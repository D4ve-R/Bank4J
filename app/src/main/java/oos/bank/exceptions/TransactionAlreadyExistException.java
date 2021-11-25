/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.exceptions;

import oos.bank.transactions.Transaction;

public class TransactionAlreadyExistException extends Exception{
    public TransactionAlreadyExistException(Transaction t){
        super("Error! Transaction already exits Transaction: " + t.toString());
    }
}
