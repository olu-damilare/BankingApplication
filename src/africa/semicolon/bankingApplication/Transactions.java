package africa.semicolon.bankingApplication;

import java.math.BigDecimal;

public interface Transactions {

    void deposit(String accountNumber, BigDecimal amount);

    BigDecimal getBalance(String accountNumber);
    void withdraw(String accountNumber, BigDecimal amount, String pin);

    void transfer(String senderAccountNumber, String beneficiaryAccountNumber, BigDecimal amount , String senderAccountPin);

    void updateFirstName(Customer customer, String newFirstName);

    void updateMiddleName(Customer customer, String newMiddleName);

    void updatePhoneNumber(Customer customer, String newPhoneNumber);

    void requestCard(String accountNumber, Card card);
}
