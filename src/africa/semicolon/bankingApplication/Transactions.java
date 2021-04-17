package africa.semicolon.bankingApplication;

import java.math.BigDecimal;

public interface Transactions {

    void deposit(String accountNumber, BigDecimal amount);

    BigDecimal getBalance(String accountNumber);
    public void withdraw(String accountNumber, BigDecimal amount, String pin);

    void transfer(String senderAccountNumber, String beneficiaryAccountNumber, BigDecimal amount , String senderAccountPin);
}
