import africa.semicolon.bankingApplication.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        centralBank.createBank("Guarantee Trust Bank", bankID);
        gtBank = centralBank.getBank(bankID);

    }

    @AfterEach
    void tearDown() {
        centralBank.resetBankIdentificationNumber();
        centralBank = null;

    }


    @Test
    void testThatCentralBankCanCreateBank() {

        assertNotNull(gtBank);
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
    void testThatCentralBankCanDissolveBank() {
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

    @Test
    void testThatBankCanSetUpBranch() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
    }

    @Test
    void testThatBankCanSetUpMoreThanOneBranch() {
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
    void testThatBankCanDissolveBranch() {
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
    void testThatBranchCanOpenSavingsAccountForCustomer() {
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
        String accountNumber = account.getAccountNumber();
        assertEquals("John Joe Bloggs", branch.getAccountName(accountNumber));

    }

    @Test
    void testThatBranchCanOpenCurrentAccountForCustomer() {
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
    void testThatBranchCanOpenMoreThanOneSavingsAccountForMoreThanOneCustomer() {
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

    @Test
    void testThatBranchCanOpenMoreThanOneCurrentAccountForMoreThanOneCustomer() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());

        Address address = new Address("12", "Yaba road");
        Customer firstCustomer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        Customer secondCustomer = new Customer("Doe", "Re", "Mi", address, "07012345678");
        branch.openAccount(firstCustomer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfAccounts());
        assertEquals(1, branch.getTotalNumberOfCustomers());
        Account account = branch.getAccount(firstCustomer, CURRENT);
        assertNotNull(account);

        branch.openAccount(secondCustomer, CURRENT);
        assertEquals(2, branch.getTotalNumberOfAccounts());
        assertEquals(2, branch.getTotalNumberOfCustomers());
        Account secondAccount = branch.getAccount(secondCustomer, CURRENT);
        assertNotNull(secondAccount);
    }

    @Test
    void testThatACustomerCannotHaveMoreThanOneSavingsAccount() {
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

        branch.openAccount(customer, SAVINGS);
        assertEquals(1, branch.getTotalNumberOfAccounts());
        assertEquals(1, branch.getTotalNumberOfCustomers());

    }

    @Test
    void testThatACustomerCannotHaveMoreThanOneCurrentAccount() {
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

        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfAccounts());
        assertEquals(1, branch.getTotalNumberOfCustomers());

    }

    @Test
    void testThatACustomerCanHaveBothSavingsAndCurrentAccounts() {
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

        branch.openAccount(customer, SAVINGS);
        assertEquals(2, branch.getTotalNumberOfAccounts());
        assertEquals(1, branch.getTotalNumberOfCustomers());
    }

    @Test
    void testThatBranchCanCloseCurrentAccount() {
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

        branch.closeAccount(customer, CURRENT);
        assertEquals(0, branch.getTotalNumberOfAccounts());
    }

    @Test
    void testThatBankCanFetchTotalNumberOfAccountsInABranch() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());

        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfAccounts());
        Account account = branch.getAccount(customer, CURRENT);
        assertNotNull(account);

        assertEquals(1, gtBank.getTotalNumberOfAccounts(sortCode));
    }

    @Test
    void testThatBankCanFetchTotalNumberOfCustomersInABranch() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());

        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfAccounts());

        assertEquals(1, gtBank.getTotalNumberOfCustomers(sortCode));
    }

    @Test
    void testThatBankCanFetchTotalNumberOfAccountsBankWide() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfAccounts());

        String secondSortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(secondSortCode);
        assertEquals(2, gtBank.getTotalNumberOfBranches());
        Branch secondBranch = gtBank.getBranch(secondSortCode);
        Customer secondCustomer = new Customer("Doe", "Joe", "Bloggs", address, "08012345678");
        secondBranch.openAccount(secondCustomer, SAVINGS);


        assertEquals(2, gtBank.getTotalNumberOfAccounts());
    }

    @Test
    void testThatBankCanFetchTotalNumberOfCustomersBankWide() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfCustomers());

        String secondSortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(secondSortCode);
        assertEquals(2, gtBank.getTotalNumberOfBranches());
        Branch secondBranch = gtBank.getBranch(secondSortCode);
        Customer secondCustomer = new Customer("Doe", "Joe", "Bloggs", address, "08012345678");
        secondBranch.openAccount(secondCustomer, SAVINGS);
        assertEquals(1, secondBranch.getTotalNumberOfCustomers());

        assertEquals(2, gtBank.getTotalNumberOfCustomers());
    }

    @Test
    void testThatBranchCanGetCustomerByAccountNumber() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfCustomers());

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        assertEquals(customer, branch.getCustomer(accountNumber));

    }

    @Test
    void testThatBranchCanGetCustomerByCustomerID() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfCustomers());

        int customerID = customer.getCustomerID();
        assertEquals(customer, branch.getCustomer(customerID));
    }

    @Test
    void testThatBranchCanGetAccountByAccountNumber() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfCustomers());

        Account account = branch.getAccount(customer, CURRENT);
        String accountNumber = branch.getAccountNumber(customer, CURRENT);
        assertEquals(account, branch.getAccount(accountNumber));
    }

    @Test
    void testThatBranchCanGetCustomerAccountsByCustomerID() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals(1, branch.getTotalNumberOfCustomers());

        int customerID = customer.getCustomerID();
        ArrayList<Account> customerAccounts = customer.getAccounts();
        ArrayList<Account> accounts = branch.getAccounts(customerID);
        assertEquals(accounts, customerAccounts);
    }


    @Test
    void testThatBranchCanDepositIntoAccount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(50_000), customer.getBalance(accountNumber));
    }

    @Test
    void testThatBranchCannotDepositNegativeAmount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(50_000), customer.getBalance(accountNumber));

        try {
            branch.deposit(accountNumber, BigDecimal.valueOf(-50_000));
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(50_000), customer.getBalance(accountNumber));
    }

    @Test
    void testThatCustomerCanSetPinOnAccount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        String pin = "1234";
        customer.setPin(accountNumber, pin);

        assertEquals(pin, customer.getPin(accountNumber));
    }
    @Test
    void testThatCustomerCanUpdatePinOnAccount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        String pin = "1234";
        customer.setPin(accountNumber, pin);
        assertEquals(pin, customer.getPin(accountNumber));

        String newPin = "1111";
        customer.updatePin(accountNumber, pin, newPin);
        assertEquals(newPin, customer.getPin(accountNumber));
    }

    @Test
    void testThatCustomerCanOnlySet_4_DigitPinOnAccount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        String pin = "12345";
        try {
            customer.setPin(accountNumber, pin);
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        assertNull(customer.getPin(accountNumber));
    }

    @Test
    void testThatBranchCanWithdrawFromAccount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));

        String pin = "1234";
        customer.setPin(accountNumber, pin);

        branch.withdraw(accountNumber, BigDecimal.valueOf(20_000), customer.getPin(accountNumber));
        assertEquals(BigDecimal.valueOf(30_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(30_000), customer.getBalance(accountNumber));
    }

    @Test
    void testThatBranchCannotWithdrawNegativeAmountFromAccount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));

        String pin = "1234";
        customer.setPin(accountNumber, pin);

        try {
            branch.withdraw(accountNumber, BigDecimal.valueOf(-20_000), customer.getPin(accountNumber));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            ;
        }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(50_000), customer.getBalance(accountNumber));
    }

    @Test
    void testThatBranchCannotWithdrawFromAnInsufficientBalance() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));

        String pin = "1234";
        customer.setPin(accountNumber, pin);

        try {
            branch.withdraw(accountNumber, BigDecimal.valueOf(100_000), customer.getPin(accountNumber));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(50_000), customer.getBalance(accountNumber));
    }

    @Test
    void testThatBranchCannotWithdrawWithInvalidPin() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));

        String pin = "1234";
        customer.setPin(accountNumber, pin);

        try {
            branch.withdraw(accountNumber, BigDecimal.valueOf(10_000), "1111");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(50_000), customer.getBalance(accountNumber));
    }

    @Test
    void testThatBranchCanTransferFundsBetweenTwoAccounts() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        Customer secondCustomer = new Customer("Doe", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(secondCustomer, SAVINGS);
        assertEquals(2, branch.getTotalNumberOfAccounts());

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        String pin = "1234";
        customer.setPin(accountNumber, pin);

        String beneficiaryAccountNumber = branch.getAccount(secondCustomer, SAVINGS).getAccountNumber();
        assertEquals(BigDecimal.valueOf(0), branch.getBalance(beneficiaryAccountNumber));
        branch.transfer(accountNumber, beneficiaryAccountNumber, BigDecimal.valueOf(20_000), customer.getPin(accountNumber));
        assertEquals(BigDecimal.valueOf(30_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(20_000), branch.getBalance(beneficiaryAccountNumber));

    }

    @Test
    void testThatBranchCannotTransferFundsBetweenTwoAccountsWithInvalidAmount() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        Customer secondCustomer = new Customer("Doe", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(secondCustomer, SAVINGS);
        assertEquals(2, branch.getTotalNumberOfAccounts());

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        String pin = "1234";
        customer.setPin(accountNumber, pin);

        String beneficiaryAccountNumber = branch.getAccount(secondCustomer, SAVINGS).getAccountNumber();
        assertEquals(BigDecimal.valueOf(0), branch.getBalance(beneficiaryAccountNumber));
        try {
            branch.transfer(accountNumber, beneficiaryAccountNumber, BigDecimal.valueOf(-20_000), customer.getPin(accountNumber));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(0), branch.getBalance(beneficiaryAccountNumber));
    }

    @Test
    void testThatBranchCannotTransferFundsBetweenTwoAccountsWithInsufficientBalance() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        Customer secondCustomer = new Customer("Doe", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(secondCustomer, SAVINGS);
        assertEquals(2, branch.getTotalNumberOfAccounts());

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        String pin = "1234";
        customer.setPin(accountNumber, pin);

        String beneficiaryAccountNumber = branch.getAccount(secondCustomer, SAVINGS).getAccountNumber();
        assertEquals(BigDecimal.valueOf(0), branch.getBalance(beneficiaryAccountNumber));
        try {
            branch.transfer(accountNumber, beneficiaryAccountNumber, BigDecimal.valueOf(120_000), customer.getPin(accountNumber));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(0), branch.getBalance(beneficiaryAccountNumber));
    }

    @Test
    void testThatBranchCannotTransferFundsBetweenTwoAccountsWithInvalidPin() {
        String sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        Customer secondCustomer = new Customer("Doe", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(secondCustomer, SAVINGS);
        assertEquals(2, branch.getTotalNumberOfAccounts());

        String accountNumber = branch.getAccount(customer, CURRENT).getAccountNumber();
        branch.deposit(accountNumber, BigDecimal.valueOf(50_000));
        String pin = "1234";
        customer.setPin(accountNumber, pin);

        String beneficiaryAccountNumber = branch.getAccount(secondCustomer, SAVINGS).getAccountNumber();
        assertEquals(BigDecimal.valueOf(0), branch.getBalance(beneficiaryAccountNumber));
        try {
            branch.transfer(accountNumber, beneficiaryAccountNumber, BigDecimal.valueOf(10_000), "1111");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(0), branch.getBalance(beneficiaryAccountNumber));
    }


}


