package africa.semicolon.bankingApplication;

import java.math.BigDecimal;

public interface Transactions {

    void deposit(String accountNumber, BigDecimal amount);

    BigDecimal getBalance(String accountNumber);
}
