package africa.semicolon.bankingApplication;

public class Account {
    private final Object customerID;
    private final String accountNumber;
    private AccountType accountType;

    public Account(Customer customer, AccountType accountType, String accountNumber) {
        this.accountType = accountType;
        this.customerID = customer.getCustomerID();
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
