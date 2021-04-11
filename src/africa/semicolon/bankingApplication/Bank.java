package africa.semicolon.bankingApplication;

import java.util.ArrayList;

public class Bank {
    private final String bankName;
    private final int bankIdentificationNumber;
    private static int branchSortCodeCounter = 1000;
    private final ArrayList<Branch> branches = new ArrayList<>();

    public Bank(String bankName, int bankIdentificationNumber) {
        this.bankName = bankName;
        this.bankIdentificationNumber = bankIdentificationNumber;
    }

    public String generateSortCode() {
        return "" + (bankIdentificationNumber / 100) + (++branchSortCodeCounter);
    }

    public int getBankIdentificationNumber() {
        return bankIdentificationNumber;
    }

    public String getBankName() {
        return bankName;
    }

    @Override
    public String toString() {
        return  "africa.semicolon.bankingApplication.Bank Name = '" + bankName + '\n' +
                "africa.semicolon.bankingApplication.Bank Identification Number = " + bankIdentificationNumber;
    }

    public void setUpBranch(String sortCode) {
        Branch branch = new Branch(sortCode);
        branches.add(branch);

    }

    public Branch getBranch(String sortCode) {
        for (Branch branch : branches) {
            if (branch.getSortCode().equals(sortCode))
                return branch;
        }
        return null;
    }

    public int getTotalNumberOfBranches() {
        return branches.size();
    }

    public void dissolveBranch(Branch branch) {
        for (int i = 0; i < branches.size(); i++) {
            if (branches.contains(branch))
                branches.remove(branch);
        }
    }
}
