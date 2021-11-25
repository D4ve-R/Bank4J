/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.exceptions;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String account){
        super("Error! Account: " + account + " already exists!");
    }
}
