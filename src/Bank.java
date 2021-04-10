public class Bank {
    private final String bankName;
    private final int bankIdentificationNumber;

    public Bank(String bankName, int bankIdentificationNumber) {
        this.bankName = bankName;
        this.bankIdentificationNumber = bankIdentificationNumber;
    }

    public int getBankIdentificationNumber() {
        return bankIdentificationNumber;
    }

    public String getBankName() {
        return bankName;
    }

    @Override
    public String toString() {
        return  "Bank Name = '" + bankName + '\n' +
                "Bank Identification Number = " + bankIdentificationNumber;
    }
}
