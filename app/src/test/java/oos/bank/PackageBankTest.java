package oos.bank;

import oos.bank.transactions.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PackageBankTest {
    Payment p;
    Payment p2;
    Payment p3;
    IncomingTransfer it;
    IncomingTransfer it2;
    OutgoingTransfer ot;
    OutgoingTransfer ot2;
    Transfer t;

    @Nested
    class PaymentTest {

        @BeforeEach
        void setUp() {
            p =  new Payment("06.06.06", 100, "Paypal", 0.1, 0.2);
            p2 = new Payment("07.07.07", -200, "Esso", 0.1, 0.2);
            p3 = new Payment(p);
        }

        @Test
        void testConstructorPayment(){
            assertAll("Test Constructor on Payment p",
                    () -> assertEquals("06.06.06", p.getDate()),
                    () -> assertEquals(100, p.getAmount()),
                    () -> assertEquals("Paypal", p.getDescription()),
                    () -> assertEquals(0.1, p.getIncomingInterest()),
                    () -> assertEquals(0.2, p.getOutgoingInterest())
            );
        }

        @Test
        void testCopyConstructorPayment(){
            assertAll("Test CopyConstructor on Payment p3",
                    () -> assertEquals("06.06.06", p3.getDate()),
                    () -> assertEquals(100, p3.getAmount()),
                    () -> assertEquals("Paypal", p3.getDescription()),
                    () -> assertEquals(0.1, p3.getIncomingInterest()),
                    () -> assertEquals(0.2, p3.getOutgoingInterest())
            );
        }

        @Test
        void testCalculate() {
            assertAll("calculate() Payment",
                    ()-> assertEquals(90, p.calculate()),
                    ()-> assertEquals(-160, p2.calculate())
            );
        }

        @Test
        void testToString() {
            assertAll("calculate() Payment",
                    ()-> assertEquals("Payment: { \nDate: " + p.getDate() + "\nAmount: " + String.format("%.2f", p.calculate(true)) + " €\nDescription: "
                            + p.getDescription() + "\n IncomingInterest: " + p.getIncomingInterest() + "\n OutgoingInterest: "
                            + p.getOutgoingInterest() + "\n}\n", p.toString()),
                    ()-> assertEquals("Payment: { \nDate: " + p2.getDate() + "\nAmount: " + String.format("%.2f", p2.calculate(true)) + " €\nDescription: "
                            + p2.getDescription() + "\n IncomingInterest: " + p2.getIncomingInterest() + "\n OutgoingInterest: "
                            + p2.getOutgoingInterest() + "\n}\n", p2.toString())
            );
        }

        @Test
        void testEquals() {
            assertAll("equals() Payment",
                    () -> assertTrue(p.equals(p3)),
                    () -> assertTrue(p3.equals(p))
            );
        }
    }

    @Nested
    class TransferTest {

        @BeforeEach
        void setUp() {
            t =  new Transfer("06.06.06", 100, "Paypal", "bob", "alice");
            it = new IncomingTransfer(t);
            ot = new OutgoingTransfer(t);
            it2 = new IncomingTransfer(t);
            ot2 = new OutgoingTransfer(t);
        }

        @Test
        void testConstructorTransfer(){
            assertAll("Test Constructor on Transfer t",
                    () -> assertEquals("06.06.06", t.getDate()),
                    () -> assertEquals(100, t.getAmount()),
                    () -> assertEquals("Paypal", t.getDescription()),
                    () -> assertEquals("bob", t.getSender()),
                    () -> assertEquals("alice", t.getRecipient())
            );

            assertAll("Test Constructor on IncomingTransfer it",
                    () -> assertEquals("06.06.06", it.getDate()),
                    () -> assertEquals(100, it.getAmount()),
                    () -> assertEquals("Paypal", it.getDescription()),
                    () -> assertEquals("bob", it.getSender()),
                    () -> assertEquals("alice", it.getRecipient())
            );

            assertAll("Test Constructor on OutgoingTransfer ot",
                    () -> assertEquals("06.06.06", ot.getDate()),
                    () -> assertEquals(100, ot.getAmount()),
                    () -> assertEquals("Paypal", ot.getDescription()),
                    () -> assertEquals("bob", ot.getSender()),
                    () -> assertEquals("alice", ot.getRecipient())
            );
        }

        @Test
        void testCopyConstructorTransfer(){
            assertAll("Test CopyConstructor on OutgoingTransfer ot2",
                    () -> assertEquals("06.06.06", ot2.getDate()),
                    () -> assertEquals(100, ot2.getAmount()),
                    () -> assertEquals("Paypal", ot2.getDescription()),
                    () -> assertEquals("bob", ot2.getSender()),
                    () -> assertEquals("alice", ot2.getRecipient())
            );
        }

        @Test
        void testCalculate() {
            assertAll("calculate() Transfer",
                    ()-> assertEquals(100, it.calculate()),
                    ()-> assertEquals(-100, ot.calculate())
            );
        }

        @Test
        void testToString() {
            assertAll("calculate() Transfer",
                    ()-> assertEquals("IncomingTransfer: { \nDate: " + it.getDate() + "\nAmount: " + String.format("%.2f", it.calculate(true)) + " €\nDescription: "
                            + it.getDescription() + "\n Sender: " + it.getSender() + "\n Recipient: "
                            + it.getRecipient() + "\n}\n", it.toString()),
                    ()-> assertEquals("OutgoingTransfer: { \nDate: " + ot.getDate() + "\nAmount: " + String.format("%.2f", ot.calculate(true)) + " €\nDescription: "
                            + ot.getDescription() + "\n Sender: " + ot.getSender() + "\n Recipient: "
                            + ot.getRecipient() + "\n}\n", ot.toString())
            );
        }

        @Test
        void testEquals() {
            assertAll("equals() Transfer",
                    () -> assertTrue(it.equals(it2)),
                    () -> assertTrue(ot.equals(ot2)),
                    () -> assertTrue(it2.equals(it)),
                    () -> assertTrue(ot2.equals(ot))
            );
        }
    }

    @Nested
    class PrivateBankTest {
        PrivateBank pb1;
        private final String dirName = "BankManagerTest";
        private final String accountName = "DaveTest";
        private final Transaction tr = new Transfer("01.01.01", 10.5, "test transfer", "dave", "someone");

        @BeforeEach
        void setUp() {
            pb1 = PrivateBank.getInstance();
            pb1.setIncomingInterest(0.1);
            pb1.setOutgoingInterest(0.1);
            try {
                pb1.setDirectoryName(dirName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Test
        @Order(1)
        void createAccount() {
            try {
                pb1.createAccount(accountName);
                assertEquals(1, pb1.countCustomers());
                assertTrue(pb1.getAllAccounts().contains(accountName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Test
        @Order(2)
        void addTransaction() {
            try {
                pb1.addTransaction(accountName, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertTrue(pb1.containsTransaction(accountName, tr));
        }

        @Test
        @Order(3)
        void removeTransaction() {
            try {
                pb1.removeTransaction(accountName, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertFalse(pb1.containsTransaction(accountName, tr));
        }

        @Test
        @Order(4)
        void containsTransaction() {
            try {
                assertFalse(pb1.containsTransaction(accountName, tr));
                pb1.addTransaction(accountName, tr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertTrue(pb1.containsTransaction(accountName, tr));
        }

        @Test
        @Order(5)
        void getAccountBalance() {
            //assertEquals(10.5, pb1.getAccountBalance(accountName));
        }
    }



/*
        @Nested
        class BankAccountTest {

            @BeforeEach
            void setUp(){
                try {
                    pb1.createAccount(accountName);
                }catch(Exception e) { e.printStackTrace();}
            }

            @AfterEach
            void tearDown(){
                try {
                    pb1.deleteAccount(accountName);
                }catch(Exception e){ e.printStackTrace();}
            }


            @Test
            @Order(1)
            void addTransaction() {
                try {
                    pb1.addTransaction(accountName, tr);
                    //assertEquals(1, pb1.countCustomers());
                    assertTrue(pb1.containsTransaction(accountName, tr));
                    pb1.removeTransaction(accountName, tr);
                }catch(Exception e){ e.printStackTrace();}

            }

            @Test
            @Order(2)
            void removeTransaction() {
                try {
                    pb1.addTransaction(accountName, tr);
                    pb1.removeTransaction(accountName, tr);
                    assertFalse(pb1.containsTransaction(accountName, tr));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Test
            @Order(3)
            void containsTransaction() {
                Transaction trNotAdded = new Transfer("02.02.02", 10.9, "test transfer2", "dave", "someone");
                try {
                    pb1.addTransaction(accountName, tr);
                    assertTrue(pb1.containsTransaction(accountName,tr));
                    assertFalse(pb1.containsTransaction(accountName,trNotAdded));
                    pb1.removeTransaction(accountName, tr);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Test
            void getAccountBalance() {
            }

            @Test
            void getTransactions() {
            }

            @Test
            void getTransactionsSorted() {
            }

            @Test
            void getTransactionsByType() {
            }

        }

    }

 */
}