package africa.semicolon.bankingApplication;

public class Card {
    private final CardType cardType;
    private String cardHolderName;
    private String cardNumber;

    public Card(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return  "Card Type = " + cardType + '\n' +
                "Card Holder Name = " + cardHolderName + '\n' +
                "Card Number = " + cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void assignCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void assignCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
