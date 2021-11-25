/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.exceptions;

public class AccountDoesNotExistException extends Exception{
    public AccountDoesNotExistException(String account){
        super("Error! Account: " + account + " does NOT exists!");
    }
}
