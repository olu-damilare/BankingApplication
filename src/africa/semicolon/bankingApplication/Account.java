package africa.semicolon.bankingApplication;

import java.math.BigDecimal;

public class Account {
    private final int customerID;
    private final String accountNumber;
    private final String accountName;
    private final AccountType accountType;
    private BigDecimal balance;

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

    public BigDecimal getBalance() {
        return balance;
    }
}
