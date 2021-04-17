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
        amountValidation(amount);
        balance = balance.add(amount);
    }

    public void setPin(String pin) {
        validatePin(pin);
        this.pin = pin;
    }

    private void validatePin(String pin) {
        if(pin.length() !=  4)
            throw new IllegalArgumentException("Invalid pin");
    }

    public String getPin() {
        return pin;
    }

    public void withdraw(BigDecimal amount, String pin) {
        amountValidation(amount);
        sufficientBalanceValidation(amount);
        if(!pin.equals(this.pin))
            throw new IllegalArgumentException("Invalid pin");
        balance = balance.subtract(amount);
    }

    private void sufficientBalanceValidation(BigDecimal amount) {
        if(amount.compareTo(balance) == 1)
            throw new IllegalArgumentException("Insufficient funds");
    }

    private void amountValidation(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) == -1)
            throw new IllegalArgumentException("Invalid amount");
    }
    private void pinValidation(String pin){
        if((pin.equals(this.pin)))
            throw new IllegalArgumentException("Invalid pin");
    }
}
