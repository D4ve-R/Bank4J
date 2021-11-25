/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.exceptions;

import oos.bank.Transaction;

public class TransactionDoesNotExistException extends Exception{
    public TransactionDoesNotExistException(Transaction t){
        super("Error ! Transaction does NOT exits Transaction: " + t.toString());
    }
}
