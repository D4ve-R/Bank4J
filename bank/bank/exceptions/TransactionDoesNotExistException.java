/**
 * OOS Praktikum
 * David Rechkemmer
 */

package bank.exceptions;

import bank.Transaction;

public class TransactionDoesNotExistException extends Exception{
    public TransactionDoesNotExistException(Transaction t){
        super("Error ! Transaction does NOT exits Transaction: " + t.toString());
    }
}
