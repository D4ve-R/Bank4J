
import bank.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    static Payment pay = new Payment("01.01.01", 200.5, "rent");
    static Payment pay2 = new Payment("02.02.02", 100, "power bill", 0.05, 0.1);
    static Payment pay3;
    static Transfer trans = new Transfer("03.03.03", 200.5, "bla");
    static Transfer trans2 = new Transfer("04.04.04", 100, "blabla", "bob", "tim");
    static Transfer trans3;
    static IncomingTransfer it = new IncomingTransfer(trans2, 0.05);
    static OutgoingTransfer ot = new OutgoingTransfer(trans2, 0.1);

    static PrivateBank pb = new PrivateBank("myBank", 0.05, 0.1);

    @DisplayName("initialize")
    @BeforeAll
    static void init() {
        pay3 = new Payment(pay);
        trans3 = new Transfer(trans2);
    }

    @Nested
    @DisplayName("Test Group Prak2")
    class Prak2Test {
        @DisplayName("Test Constructor for Payment")
        @Test
        void testPayment() {
            assertAll("Test Constructor on Payment pay",
                    () -> assertEquals("01.01.01", pay.getDate()),
                    () -> assertEquals(200.5, pay.getAmount()),
                    () -> assertEquals("rent", pay.getDescription())
            );
            assertAll("Test Constructor on Payment pay2",
                    () -> assertEquals("02.02.02", pay2.getDate()),
                    () -> assertEquals(100, pay2.getAmount()),
                    () -> assertEquals("power bill", pay2.getDescription()),
                    () -> assertEquals(0.05, pay2.getIncomingInterest()),
                    () -> assertEquals(0.1, pay2.getOutgoingInterest())
            );
        }

        @DisplayName("Test CopyConstructor Payment")
        @Test
        void testPaymentCopyConstructor() {
            assertTrue(pay3.equals(pay));
        }

        @DisplayName("Test Constructor for Tranfer")
        @Test
        void testTransfer() {
            assertAll("Test Constructor on Transfer trans",
                    () -> assertEquals("03.03.03", trans.getDate()),
                    () -> assertEquals(200.5, trans.getAmount()),
                    () -> assertEquals("bla", trans.getDescription())
            );
            assertAll("Test Constructor on Transfer trans2",
                    () -> assertEquals("04.04.04", trans2.getDate()),
                    () -> assertEquals(100, trans2.getAmount()),
                    () -> assertEquals("blabla", trans2.getDescription()),
                    () -> assertEquals("bob", trans2.getSender()),
                    () -> assertEquals("tim", trans2.getRecipient())
            );
        }

        @DisplayName("Test CopyConstructor for Transfer")
        @Test
        void testTransferCopyConstructor() {
            assertFalse(trans3.equals(trans));
        }

        @DisplayName("Test calculate() on Payment")
        @Test
        void testCalculateBillPayment() {
            assertEquals(95, pay2.calculate());
        }

        @DisplayName("Test calculate() on Transfer")
        @Test
        void testCalculateBillTransfer() {
            assertEquals(100, trans2.calculate());
        }
    }

    @Nested
    @DisplayName("Test Group Prak3")
    class Prak3Test {
        @DisplayName("Test Constructor for IncomingTransfer")
        @Test
        void testIncomingTransfer() {
            assertAll("Test Constructor on IncomingTransfer",
                    () -> assertEquals("04.04.04", it.getDate()),
                    () -> assertEquals(100, it.getAmount()),
                    () -> assertEquals("blabla", it.getDescription())
            );
        }

        @DisplayName("Test Constructor on OutgoingTransfer")
        @Test
        void testOutgoingInterest(){
            assertAll("Test Constructor on OutgoingTransfer",
                    () -> assertEquals("04.04.04", ot.getDate()),
                    () -> assertEquals(100, ot.getAmount()),
                    () -> assertEquals("blabla", ot.getDescription())
            );
        }

        @DisplayName("Test calculate() on Incoming/OutgoingTransfer")
        @Test
        void testCalculateIOTransfers(){
            assertAll("test calculate()",
                    ()-> assertEquals(95, it.calculate()),
                    ()-> assertEquals(90, ot.calculate())
            );
        }

        @Nested
        @DisplayName("Test PrivateBank Class")
        class PrivateBankTest{
            @DisplayName("Test Constructor PrivateBank")
            @Test
            void testPrivateBank(){
                assertAll("constructor",
                        ()-> assertEquals("myBank", pb.getName()),
                        ()-> assertEquals(0.05, pb.getIncomingInterest()),
                        ()-> assertEquals(0.1, pb.getOutgoingInterest())
                );
            }
        }
    }

}