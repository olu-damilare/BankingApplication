
import java.security.SecureRandom;
import java.util.ArrayList;

public class CentralBank {
    private ArrayList<Bank> banks = new ArrayList<>();

    public void createBank(String bankName, int bankIdentificationNumber) {
        Bank bank = new Bank(bankName, bankIdentificationNumber);
        banks.add(bank);

    }

    public int generateBankIdentificationNumber() {
        SecureRandom random = new SecureRandom();
        return 1001 + random.nextInt(8999);
    }

    public String getBankName(int bank_ID_Number) {
        for (Bank bank : banks) {
            if (bank.getBankIdentificationNumber() == bank_ID_Number)
                return bank.getBankName();
        }
        return null;
    }

    public int getBankID(String bankName) {
        for (Bank bank : banks) {
            if (bank.getBankName().equals(bankName))
                return bank.getBankIdentificationNumber();
        }
        return 0;
    }

    public Bank getBank(int bankID) {
        for (Bank bank : banks) {
            if (bank.getBankIdentificationNumber() == bankID)
                return bank;
        }
        return null;
    }

    public int getBankID(Bank bank) {
        if(banks.contains(bank))
            return bank.getBankIdentificationNumber();
        else return 0;
    }

    public int getTotalNumberOfBanks() {
        return banks.size();
    }

    public void dissolveBank(Bank bank) {
        if(banks.contains(bank))
           banks.remove(bank);
    }
}
