package africa.semicolon.bankingApplication;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Branch  implements  Transactions, GetBy{
    private final String sortCode;
    private final ArrayList<Account> accounts = new ArrayList<>();
    private final ArrayList<Customer> customers = new ArrayList<>();
    private static int accountNumberCounter;
    private static int customerIDCounter;

    @Override
    public String toString() {
        return "Sort code: " + sortCode + '\n' +
                "Total number of customers: " + getTotalNumberOfCustomers() + '\n' +
                "Total number of accounts: " + getTotalNumberOfAccounts();
    }

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

    @Override
    public Account getAccount(Customer customer, AccountType accountType) {
        confirmThatCustomerProfileExistsInBranch(customer);

        for (int i = 0; i < customers.size(); i++) {
            if(customers.contains(customer))
                return customer.getAccount(accountType);
        }
        throw new IllegalArgumentException("Customer does not have a " + accountType + " account");
    }

    private void confirmThatCustomerProfileExistsInBranch(Customer customer) {
        boolean customerExistsInBranch = customers.contains(customer);
        if(!customerExistsInBranch)
            throw new IllegalArgumentException("Customer does not exist");
    }

    public void closeAccount(Customer customer, AccountType accountType) {
        confirmThatCustomerProfileExistsInBranch(customer);
        for (Customer value : customers) {
            if (value.equals(customer)) {
                accounts.remove(value.getAccount(accountType));
                customer.getAccounts().remove(value.getAccount(accountType));

            }

        }
    }

    public String getAccountName(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber))
                return account.getAccountName();
        }
        throw new IllegalArgumentException("Account does not exist");
    }


//    @Override
//    public void deposit(String accountNumber, BigDecimal amount) {
//        for (int i = 0; i < accounts.size(); i++) {
//
//        }
//    }

//    @Override
//    public BigDecimal getBalance(String accountNumber) {
//        for (Account account : accounts) {
//            if (account.getAccountNumber().equals(accountNumber))
//                return account.getBalance();
//        }
//        throw new IllegalArgumentException("Invalid account number");
//    }


    @Override
    public Customer getCustomer(String accountNumber) {
        AccountType type = getAccountType(accountNumber);

        for (Customer customer : customers) {
            if (customer.getAccount(type).getAccountNumber().equals(accountNumber))
                return customer;
        }
        throw new IllegalArgumentException("Invalid Account Number");
    }

    private AccountType getAccountType(String accountNumber){
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account.getAccountType();
            }
        }
        throw new IllegalArgumentException("Invalid Account Number");
    }

    @Override
    public Customer getCustomer(int customerID) {
        for(Customer customer : customers)
            if(customer.getCustomerID() == customerID)
                return customer;

            throw new IllegalArgumentException("Invalid customer ID");
    }

    @Override
    public String getAccountNumber(Customer customer, AccountType accountType) {
        confirmThatCustomerProfileExistsInBranch(customer);
        for (Customer value : customers) {
            if (value.equals(customer))
                return value.getAccount(accountType).getAccountNumber();
        }
        throw new IllegalArgumentException("Customer does not have a " + accountType + " account");
    }

    @Override
    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber))
                return account;
        }
        throw new IllegalArgumentException("Invalid account number");
    }

    @Override
    public ArrayList<Account> getAccounts(int customerID) {
        for(Customer customer: customers)
            if(customer.getCustomerID() == customerID)
                return customer.getAccounts();

            throw new IllegalArgumentException("Invalid customer ID");
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.deposit(amount);
                break;
            }
        }
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber))
                return account.getBalance();
        }
        throw new IllegalArgumentException("Invalid account number");
    }

}
