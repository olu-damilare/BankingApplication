package africa.semicolon.bankingApplication;

import java.math.BigDecimal;

public class Account {
    private final int customerID;
    private final String accountNumber;
    private final String accountName;
    private final AccountType accountType;
    private BigDecimal balance = new BigDecimal(0);
    private String pin;

    public Account(Customer customer, AccountType accountType, String accountNumber) {
        this.accountType = accountType;
        customerID = customer.getCustomerID();
        this.accountNumber = accountNumber;
        accountName = customer.getFullName();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public int getCustomerID(){return customerID;}
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        if(amount.compareTo(new BigDecimal(0)) == 1)
      balance = balance.add(amount);
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }
}
