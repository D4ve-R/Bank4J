/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.transactions;

/**
 * Incoming
 * Transfer class handles all outgoing Transfer
 * inherits from Transfer
 */
public class IncomingTransfer extends Transfer {
    public IncomingTransfer(Transfer trans){
        super(trans);
    }
}