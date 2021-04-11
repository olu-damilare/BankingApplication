package africa.semicolon.bankingApplication;

public class Account {
    private final int customerID;
    private final String accountNumber;
    private final String accountName;
    private final AccountType accountType;

    public Account(Customer customer, AccountType accountType, String accountNumber) {
        this.accountType = accountType;
        customerID = customer.getCustomerID();
        this.accountNumber = accountNumber;
        accountName = customer.getFullName();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }
}
