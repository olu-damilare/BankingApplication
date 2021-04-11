import africa.semicolon.bankingApplication.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static africa.semicolon.bankingApplication.AccountType.*;

public class BankingApplicationTests {
    int bankID;
    CentralBank centralBank;
    Bank gtBank;

    @BeforeEach
    void setup() {
        centralBank = new CentralBank();
        bankID = centralBank.generateBankIdentificationNumber();
        centralBank.createBank("Guarantee Trust africa.semicolon.bankingApplication.Bank", bankID);
        gtBank = centralBank.getBank(bankID);

    }
    @AfterEach
    void tearDown(){
        centralBank = null;
    }


    @Test
    void testThatCentralBankCanCreateBank() {

        assertNotNull(gtBank);
        assertEquals("Guarantee Trust africa.semicolon.bankingApplication.Bank", centralBank.getBankName(bankID));
        assertEquals(bankID, centralBank.getBankID("Guarantee Trust africa.semicolon.bankingApplication.Bank"));
        assertEquals(bankID, centralBank.getBankID(gtBank));
        assertEquals(1, centralBank.getTotalNumberOfBanks());
    }

    @Test
    void testThatCentralBankCanCreateMoreThanOneBank() {
        assertNotNull(gtBank);
        assertEquals(1, centralBank.getTotalNumberOfBanks());

        int bankID_2 = centralBank.generateBankIdentificationNumber();
        centralBank.createBank("Zenith africa.semicolon.bankingApplication.Bank", bankID_2);
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
        centralBank.createBank("Zenith africa.semicolon.bankingApplication.Bank", bankID_2);
        Bank zenithBank = centralBank.getBank(bankID_2);

        assertNotNull(zenithBank);
        assertEquals(bankID_2, centralBank.getBankID(zenithBank));
        assertEquals(2, centralBank.getTotalNumberOfBanks());

        centralBank.dissolveBank(gtBank);
        assertEquals(1, centralBank.getTotalNumberOfBanks());
        centralBank.dissolveBank(zenithBank);
        assertEquals(0, centralBank.getTotalNumberOfBanks());

    }

    @Test
    void testThatBankCanSetUpBranch(){
    String sortCode = gtBank.generateSortCode();
    gtBank.setUpBranch(sortCode);
    Branch branch = gtBank.getBranch(sortCode);
    assertNotNull(branch);
    assertEquals(1, gtBank.getTotalNumberOfBranches());
    }

    @Test
    void testThatBankCanSetUpMoreThanOneBranch(){
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());

        String secondSortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(secondSortCode);
        Branch secondBranch = gtBank.getBranch(secondSortCode);
        assertNotNull(secondBranch);
        assertEquals(2, gtBank.getTotalNumberOfBranches());

    }
    @Test
    void testThatBankCanDissolveBranch(){
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());

        String secondSortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(secondSortCode);
        Branch secondBranch = gtBank.getBranch(secondSortCode);
        assertNotNull(secondBranch);
        assertEquals(2, gtBank.getTotalNumberOfBranches());

        gtBank.dissolveBranch(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        gtBank.dissolveBranch(secondBranch);
        assertEquals(0, gtBank.getTotalNumberOfBranches());
    }

    @Test
    void testThatBranchCanOpenSavingsAccountForCustomer(){
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");

        branch.openAccount(customer, SAVINGS);
        assertEquals(1, branch.getTotalNumberOfAccounts());
        assertEquals(1, branch.getTotalNumberOfCustomers());
        Account account = branch.getAccount(customer, SAVINGS);
        assertNotNull(account);

    }

    @Test
    void testThatBranchCanOpenCurrentAccountForCustomer(){
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");

        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfAccounts());
        assertEquals(1, branch.getTotalNumberOfCustomers());
        Account account = branch.getAccount(customer, CURRENT);
        assertNotNull(account);

    }
    @Test
    void testThatBranchCanOpenMoreThanOneSavingAccountsForMoreThanOneCustomer(){
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());

        Address address = new Address("12", "Yaba road");
        Customer firstCustomer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        Customer secondCustomer = new Customer("Doe", "Re", "Mi", address, "07012345678");
        branch.openAccount(firstCustomer, SAVINGS);
        assertEquals(1, branch.getTotalNumberOfAccounts());
        assertEquals(1, branch.getTotalNumberOfCustomers());
        Account account = branch.getAccount(firstCustomer, SAVINGS);
        assertNotNull(account);

        branch.openAccount(secondCustomer, SAVINGS);
        assertEquals(2, branch.getTotalNumberOfAccounts());
        assertEquals(2, branch.getTotalNumberOfCustomers());
        Account secondAccount = branch.getAccount(secondCustomer, SAVINGS);
        assertNotNull(secondAccount);
    }
}
