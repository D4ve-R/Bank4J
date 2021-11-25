/**
 * OOS Praktikum
 * David Rechkemmer
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import bank.*;
import bank.exceptions.*;


public class BankTest {
    static Payment pay = new Payment("01.01.01", 200.5, "rent");
    static Payment pay2 = new Payment("02.02.02", 100, "power bill", 0.05, 0.1);
    static Payment pay3;
    static Payment pay4 = new Payment("05.05.05", 100, "bill", 0.1, 0.1);
    static Payment pay5 = new Payment("06.06.06", 100, "bill", 0.1, 0.1);
    static Transfer trans = new Transfer("03.03.03", 200, "bla");
    static Transfer trans2 = new Transfer("04.04.04", 100, "blabla", "bob", "tim");
    static Transfer trans3;
    static IncomingTransfer it = new IncomingTransfer(trans2);
    static OutgoingTransfer ot = new OutgoingTransfer(trans);

    static PrivateBank pb = new PrivateBank("myBank", 0.05, 0.1);

    @DisplayName("initialize")
    @BeforeAll
    static void init() {
        pay3 = new Payment(pay2);
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
            assertAll("Test Constructor on Payment pay2",
                    () -> assertEquals("02.02.02", pay3.getDate()),
                    () -> assertEquals(100, pay3.getAmount()),
                    () -> assertEquals("power bill", pay3.getDescription()),
                    () -> assertEquals(0.05, pay3.getIncomingInterest()),
                    () -> assertEquals(0.1, pay3.getOutgoingInterest())
            );

        }

        @DisplayName("Test CopyConstructor Payment")
        @Test
        void testPaymentCopyConstructor() {
            assertTrue(pay3.equals(pay2));
        }

        @DisplayName("Test Constructor for Transfer")
        @Test
        void testTransfer() {
            assertAll("Test Constructor on Transfer trans",
                    () -> assertEquals("03.03.03", trans.getDate()),
                    () -> assertEquals(200, trans.getAmount()),
                    () -> assertEquals("bla", trans.getDescription())
            );
            assertAll("Test Constructor on Transfer trans2",
                    () -> assertEquals("04.04.04", trans2.getDate()),
                    () -> assertEquals(100, trans2.getAmount()),
                    () -> assertEquals("blabla", trans2.getDescription()),
                    () -> assertEquals("bob", trans2.getSender()),
                    () -> assertEquals("tim", trans2.getRecipient())
            );
            assertAll("Test Constructor on Transfer trans2",
                    () -> assertEquals("04.04.04", trans3.getDate()),
                    () -> assertEquals(100, trans3.getAmount()),
                    () -> assertEquals("blabla", trans3.getDescription()),
                    () -> assertEquals("bob", trans3.getSender()),
                    () -> assertEquals("tim", trans3.getRecipient())
            );
        }

        @DisplayName("Test CopyConstructor for Transfer")
        @Test
        void testTransferCopyConstructor() {
            assertAll("copy constructor",
                    ()-> assertTrue(trans2.equals(trans2)),
                    ()-> assertFalse(trans3.equals(trans))
            );
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
        void testOutgoingTransfer(){
            assertAll("Test Constructor on OutgoingTransfer",
                    () -> assertEquals("03.03.03", ot.getDate()),
                    () -> assertEquals(200, ot.getAmount()),
                    () -> assertEquals("bla", ot.getDescription())
            );
        }

        @DisplayName("Test calculate() on Incoming/OutgoingTransfer")
        @Test
        void testCalculateIOTransfers(){
            assertAll("test calculate()",
                    ()-> assertEquals(100, it.calculate()),
                    ()-> assertEquals(-200, ot.calculate())
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

            @DisplayName("Test createAccount()")
            @Test
            void testCreateAccount(){
                try {
                    pb.createAccount("david");
                }catch(Exception e){
                    e.printStackTrace();
                }
                assertAll("createAccount()",
                        ()-> assertTrue(pb.getAccounts().containsKey("david")),
                        ()-> assertThrows(AccountAlreadyExistsException.class, ()-> pb.createAccount("david"))
                );
            }

            @DisplayName("Test addTransaction")
            @Test
            void testAddTransaction(){
                try{
                    pb.addTransaction("david", pay);
                }catch(Exception e){
                    e.printStackTrace();
                }
                assertAll("addTransaction()",
                        ()-> assertTrue(pb.containsTransaction("david", pay)),
                        ()-> assertFalse(pb.containsTransaction("david", pay2)),
                        ()-> assertThrows(TransactionAlreadyExistException.class, () -> pb.addTransaction("david",pay))
                );
            }

            @DisplayName("Test removeTransaction")
            @Test
            void testRemoveTransaction(){
                try{
                    pb.removeTransaction("david", pay);
                }catch(Exception e){
                    e.printStackTrace();
                }
                assertAll("addTransaction()",
                        ()-> assertFalse(pb.containsTransaction("david", pay)),
                        ()-> assertThrows(TransactionDoesNotExistException.class, () -> pb.removeTransaction("david",pay)),
                        ()-> assertThrows(AccountDoesNotExistException.class, ()-> pb.removeTransaction("peter", pay3))
                );
            }

            @DisplayName("Test getAccountBalance")
            @Test
            void testGetAccountBalance(){
                try{
                    pb.createAccount("hans");
                    pb.addTransaction("hans", pay4);    //95
                    pb.addTransaction("hans",pay5);     //95
                    pb.addTransaction("hans", it);      //95
                    pb.addTransaction("hans", ot);      //-180
                }catch(Exception e){
                    e.printStackTrace();
                }
                assertEquals(465, pb.getAccountBalance("hans"));
            }


        }
    }

}