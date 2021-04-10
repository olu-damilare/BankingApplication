import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankingApplicationTests {
    int bankID;
    CentralBank centralBank;
    Bank gtBank;

    @BeforeEach
    void setup() {
        centralBank = new CentralBank();
        bankID = centralBank.generateBankIdentificationNumber();
        centralBank.createBank("Guarantee Trust Bank", bankID);
        gtBank = centralBank.getBank(bankID);

    }
    @AfterEach
    void tearDown(){
        centralBank = null;
    }


    @Test
    void testThatCentralBankCanCreateBank() {

        assertNotNull(gtBank);
        assertEquals("Guarantee Trust Bank", centralBank.getBankName(bankID));
        assertEquals(bankID, centralBank.getBankID("Guarantee Trust Bank"));
        assertEquals(bankID, centralBank.getBankID(gtBank));
        assertEquals(1, centralBank.getTotalNumberOfBanks());
    }

    @Test
    void testThatCentralBankCanCreateMoreThanOneBank() {
        assertNotNull(gtBank);
        assertEquals(1, centralBank.getTotalNumberOfBanks());

        int bankID_2 = centralBank.generateBankIdentificationNumber();
        centralBank.createBank("Zenith Bank", bankID_2);
        Bank zenithBank = centralBank.getBank(bankID_2);

        assertNotNull(zenithBank);
        assertEquals(bankID_2, centralBank.getBankID(zenithBank));
        assertEquals(2, centralBank.getTotalNumberOfBanks());

    }
    @Test
    void testThatCentralBankCanDissolveBank(){
        assertNotNull(gtBank);
        assertEquals(1, centralBank.getTotalNumberOfBanks());

        int bankID_2 = centralBank.generateBankIdentificationNumber();
        centralBank.createBank("Zenith Bank", bankID_2);
        Bank zenithBank = centralBank.getBank(bankID_2);

        assertNotNull(zenithBank);
        assertEquals(bankID_2, centralBank.getBankID(zenithBank));
        assertEquals(2, centralBank.getTotalNumberOfBanks());

        centralBank.dissolveBank(gtBank);
        assertEquals(1, centralBank.getTotalNumberOfBanks());
        centralBank.dissolveBank(zenithBank);
        assertEquals(0, centralBank.getTotalNumberOfBanks());

    }

}
