package africa.semicolon.bankingApplication;

import java.util.ArrayList;

public class Branch {
    private final String sortCode;
    private final ArrayList<Account> accounts = new ArrayList<>();
    private final ArrayList<Customer> customers = new ArrayList<>();
    private static int accountNumberCounter;
    private static int customerIDCounter;

    public Branch(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void openAccount(Customer customer, AccountType accountType) {
        if(customer.getTotalNumberOfAccounts() > 0) {
            if (!customer.verifyThatCustomerOwnsAccountType(accountType)) {
                accountOpeningProcess(customer, accountType);
            }
        }
        else {
            accountOpeningProcess(customer, accountType);
        }
    }

    private void accountOpeningProcess(Customer customer, AccountType accountType) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(customer, accountType, accountNumber);
        accounts.add(account);
        if(!customers.contains(customer))
            customers.add(customer);
        customer.addAccount(account);
        customer.assignCustomerID(++customerIDCounter);
    }

    private String generateAccountNumber() {
        return String.format("%s%04d", sortCode, ++accountNumberCounter);
    }

    public int getTotalNumberOfAccounts() {
        return accounts.size();
    }

    public int getTotalNumberOfCustomers() {
        return customers.size();
    }

    public Account getAccount(Customer customer, AccountType accountType) {
        confirmThatCustomerProfileExistsInBranch(customer);

        for (int i = 0; i < customers.size(); i++) {
            if(customers.contains(customer))
                return customer.getAccount(accountType);
        }
        return null;
    }

    private void confirmThatCustomerProfileExistsInBranch(Customer customer) {
        boolean customerExistsInBranch = customers.contains(customer);
        if(!customerExistsInBranch)
            throw new IllegalArgumentException("Customer does not exist");
    }

    public void closeAccount(Customer customer, AccountType accountType) {
        for (Customer value : customers) {
            if (value.equals(customer)) {
                accounts.remove(value.getAccount(accountType));
                customer.getAccounts().remove(value.getAccount(accountType));

            }

        }
    }

    public String getAccountName(String accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i).getAccountNumber().equals(accountNumber))
                return accounts.get(i).getAccountName();
        }
        throw new IllegalArgumentException("Account does not exist");
    }
}
