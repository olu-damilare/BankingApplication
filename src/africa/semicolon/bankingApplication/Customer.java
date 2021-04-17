package africa.semicolon.bankingApplication;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Customer {

    private String lastName;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private Address address;
    private final ArrayList<Account> accounts = new ArrayList<>();
    private int customerID;

    public Customer(String firstName, String middleName, String lastName, Address address, String phoneNumber) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.lastName = lastName;
        if(phoneNumber.length() != 11)
            throw new IllegalArgumentException("invalid phone number");
        this.phoneNumber = phoneNumber;
    }

    public void addAccount(Account account) {
        if(confirmEligibilityToOpenAdditionalAccount(account))
            accounts.add(account);
        else throw new ArrayIndexOutOfBoundsException(
                "You have reached the maximum number of accounts owned by a customer"
        );
    }

    private boolean confirmEligibilityToOpenAdditionalAccount(Account account) {
        boolean eligibility = true;
        if(accounts.size() > 0)
            for (Account value : accounts) {
                if (value.getAccountType() == account.getAccountType()) {
                    eligibility = false;
                    break;
                }
            }
        return eligibility;
    }

    public Account getAccount(AccountType accountType) {
        if (verifyThatCustomerOwnsAccountType(accountType)) {
            for (Account account : accounts)
                if (account.getAccountType() == accountType)
                    return account;

        }
        throw new IllegalArgumentException("Customer does not own a " + accountType + " account");
    }

    public boolean verifyThatCustomerOwnsAccountType(AccountType accountType) {
        boolean accountTypeExists = false;
        if(accounts.size() > 0)
            for (Account account : accounts) {
                if (account.getAccountType() == accountType) {
                    accountTypeExists = true;
                    break;
                }
            }
        return accountTypeExists;


    }

    public int getCustomerID() {
        return customerID;
    }

    public void assignCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getTotalNumberOfAccounts() {
        int numberOfAccounts = 0;
        for (Account account : accounts) {
            if (account != null)
                numberOfAccounts++;
        }
        return numberOfAccounts;
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public BigDecimal getBalance(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber))
                return account.getBalance();
        }
        throw new IllegalArgumentException("invalid account number");
    }


    public void setPin(String accountNumber, String pin) {
        Account account = getAccount(accountNumber);
                account.setPin(pin);

    }

    private Account getAccount(String accountNumber){
        for (Account account : accounts)
            if (account.getAccountNumber().equals(accountNumber))
                return account;
        throw new IllegalArgumentException("Invalid account number");
    }

    public String getPin(String accountNumber) {
        Account account = getAccount(accountNumber);
             return account.getPin();
    }

    public void updatePin(String accountNumber, String oldPin, String newPin) {
        Account account = getAccount(accountNumber);
        if(account.getPin().equals(oldPin))
            account.setPin(newPin);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String newMiddleName) {
        middleName = newMiddleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }
}

