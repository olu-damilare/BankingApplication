package africa.semicolon.bankingApplication;

import java.util.ArrayList;

public interface GetBy {
    Customer getCustomer(String accountNumber);

    Customer getCustomer(int customerID);

    String getAccountNumber(Customer customer, AccountType current);

    Account getAccount(Customer customer, AccountType accountType);

    Account getAccount(String accountNumber);

    ArrayList<Account> getAccounts(int customerID);
}
