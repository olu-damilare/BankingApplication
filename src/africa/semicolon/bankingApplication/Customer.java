package africa.semicolon.bankingApplication;

import org.w3c.dom.ls.LSOutput;

public class Customer {

    private final String lastName;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private Address address;
    private Account[] accounts = new Account[2];
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
        confirmEligibilityToOpenAdditionalAccount();
        if(accounts[0] == null)
            accounts[0] = account;
        else
            accounts[1] = account;
    }

    private void confirmEligibilityToOpenAdditionalAccount() {
        boolean eligibleForAdditionalAccountOwnership = accounts[0] == null || accounts[1] == null;
        if(!eligibleForAdditionalAccountOwnership)
            throw new ArrayIndexOutOfBoundsException(
                    "You have reached the maximum number of accounts owned by a customer"
            );
    }

    public Account getAccount(AccountType accountType) {
        verifyThatCustomerOwnsAccountType(accountType);

        if(accounts[0].getAccountType() == accountType)
        return accounts[0];

        else
            return accounts[1];
    }

    private void verifyThatCustomerOwnsAccountType(AccountType accountType) {
        boolean customerOwnsAccountType = accounts[0].getAccountType() == accountType || accounts[1].getAccountType() == accountType;
        if(!customerOwnsAccountType)
            throw new IllegalArgumentException("Customer does not own a " + accountType + " account");
    }

    public int getCustomerID() {
        return customerID;
    }

    public void assignCustomerID(int customerID) {
        this.customerID = customerID;
    }
}

