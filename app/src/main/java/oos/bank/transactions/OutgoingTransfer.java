/**
 * OOS Praktikum
 * David Rechkemmer
 */

package oos.bank.transactions;

/**
 * OutgoingTransfer class handles all outgoing Transfer
 * inherits from Transfer
 */
public class OutgoingTransfer extends Transfer {

    /**
     * Constructor for OutgoingTransfer
     * @param trans Transfer Object
     */
    public OutgoingTransfer(Transfer trans) {
        super(trans);
    }

    /**
     * overwritten from Transfer
     * @return negative Amount
     */
    @Override
    public double calculate(){
        return (-1 * super.calculate());
    }
}
