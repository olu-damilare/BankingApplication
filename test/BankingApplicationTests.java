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
    String sortCode;
    Branch branch;

    @BeforeEach
    void setup() {
        centralBank = new CentralBank();
        bankID = centralBank.generateBankIdentificationNumber();
        centralBank.createBank("Guarantee Trust Bank", bankID);
        gtBank = centralBank.getBank(bankID);
        sortCode = gtBank.generateSortCode();
        gtBank.setUpBranch(sortCode);
        branch = gtBank.getBranch(sortCode);

    }

    @AfterEach
    void tearDown() {
        centralBank.resetBankIdentificationNumber();
        centralBank = null;
        branch.resetCustomerIDCounter();

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
        Branch branch = gtBank.getBranch(sortCode);
        assertNotNull(branch);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
    }

    @Test
    void testThatBankCanSetUpMoreThanOneBranch() {
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
                    }
        assertEquals(BigDecimal.valueOf(50_000), branch.getBalance(accountNumber));
        assertEquals(BigDecimal.valueOf(50_000), customer.getBalance(accountNumber));
    }

    @Test
    void testThatBranchCannotWithdrawFromAnInsufficientBalance() {
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

    @Test
    void testThatBranchCanUpdateCustomerFirstName(){
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        assertEquals("John", branch.getFirstName(customer));
        assertEquals("John Joe Bloggs", branch.getAccount(customer, CURRENT).getAccountName());

        String newFirstName = "Mia";
        branch.updateFirstName(customer, newFirstName);

        assertEquals(newFirstName, branch.getFirstName(customer));
        assertEquals(newFirstName + " Joe Bloggs", branch.getAccount(customer, CURRENT).getAccountName());
    }

    @Test
    void testThatBranchCanUpdateCustomerMiddleName(){
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        assertEquals("Joe", branch.getMiddleName(customer));
        assertEquals("John Joe Bloggs", branch.getAccount(customer, CURRENT).getAccountName());

        String newMiddleName = "Mia";
        branch.updateMiddleName(customer, newMiddleName);

        assertEquals(newMiddleName, branch.getMiddleName(customer));
        assertEquals("John " + newMiddleName + " Bloggs", branch.getAccount(customer, CURRENT).getAccountName());
    }

    @Test
    void testThatBranchCanUpdateCustomerLastName(){
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);

        assertEquals("Bloggs", branch.getLastName(customer));
        assertEquals("John Joe Bloggs", branch.getAccount(customer, CURRENT).getAccountName());

        String newMiddleName = "Mia";
        branch.updateMiddleName(customer, newMiddleName);

        assertEquals(newMiddleName, branch.getMiddleName(customer));
        assertEquals("John " + newMiddleName + " Bloggs", branch.getAccount(customer, CURRENT).getAccountName());
    }

    @Test
    void testThatBranchCanUpdateCustomerPhoneNumber(){
        Branch branch = gtBank.getBranch(sortCode);
        assertEquals(1, gtBank.getTotalNumberOfBranches());
        Address address = new Address("12", "Yaba road");
        Customer customer = new Customer("John", "Joe", "Bloggs", address, "08012345678");
        branch.openAccount(customer, CURRENT);
        assertEquals("08012345678", branch.getPhoneNumber(customer));

        String newPhoneNumber = "08098765432";
        branch.updatePhoneNumber(customer, newPhoneNumber);

        assertEquals(newPhoneNumber, branch.getPhoneNumber(customer));
        }



}


