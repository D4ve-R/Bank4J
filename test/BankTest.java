
import bank.Payment;
import bank.Transfer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    static Payment pay = new Payment("01.01.01", 200.5, "rent");
    static Payment pay2 = new Payment("02.02.02", 100, "power bill", 0.05, 0.1);
    static Payment pay3;
    static Transfer trans = new Transfer("03.03.03", 200.5, "bla");
    static Transfer trans2 = new Transfer("04.04.04", 100.9, "blabla", "bob", "tim");
    static Transfer trans3;

    @DisplayName("initialize pay3 and trans3 with copy constructor")
    @BeforeAll
    static void init() {
        pay3 = new Payment(pay);
        trans3 = new Transfer(trans2);
    }

    @DisplayName("Test Constructor for Payment")
    @Test
    void testPayment(){
        assertAll("Test Constructor on Payment pay",
                ()-> assertEquals("01.01.01", pay.getDate()),
                ()-> assertEquals(200.5, pay.getAmount()),
                ()->assertEquals("rent", pay.getDescription())
        );
        assertAll("Test Constructor on Payment pay2",
                ()-> assertEquals("02.02.02", pay2.getDate()),
                ()-> assertEquals(100, pay2.getAmount()),
                ()->assertEquals("power bill", pay2.getDescription()),
                ()->assertEquals(0.05, pay2.getIncomingInterest()),
                ()->assertEquals(0.1, pay2.getOutgoingInterest())
        );
    }

    @DisplayName("Test CopyConstructor Payment")
    @Test
    void testPaymentCopyConstructor(){
        assertTrue(pay3.equals(pay));
    }

    @DisplayName("Test Constructor for Tranfer")
    @Test
    void testTransfer(){
        assertAll("Test Constructor on Transfer trans",
                ()-> assertEquals("03.03.03", trans.getDate()),
                ()-> assertEquals(200.5, trans.getAmount()),
                ()->assertEquals("bla", trans.getDescription())
        );
        assertAll("Test Constructor on Transfer trans2",
                ()-> assertEquals("04.04.04", trans2.getDate()),
                ()-> assertEquals(100.9, trans2.getAmount()),
                ()->assertEquals("blabla", trans2.getDescription()),
                ()->assertEquals("bob", trans2.getSender()),
                ()->assertEquals("tim", trans2.getRecipient())
        );
    }

    @DisplayName("Test CopyConstructor for Transfer")
    @Test
    void testTransferCopyConstructor(){
        assertFalse(trans3.equals(trans));
    }

    @DisplayName("Test calculate() on Payment")
    @Test
    void testCalculateBillPayment(){
        assertEquals(95, pay2.calculate());
    }

    @DisplayName("Test calculate() on Transfer")
    @Test
    void testCalculateBillTransfer(){
        assertEquals(100.9, trans2.calculate());
    }
}