package africa.semicolon.bankingApplication;

public class Address {
    private final String houseNumber;
    private final String streetName;

    public Address(String houseNumber, String streetName) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
    }

    @Override
    public String toString() {
        return  "House Number = " + houseNumber + '\n' +
                "Street Name = " + streetName;
    }
}
