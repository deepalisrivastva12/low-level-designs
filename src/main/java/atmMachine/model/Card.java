package atmMachine.model;

import java.util.UUID;

public class Card {

    private String cardNumber;
    private UUID ccv;
    private String expiryDate;
    private String holderNamer;


    public Card(String cardNumber, String expiryDate, String holderNamer) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.holderNamer = holderNamer;
        this.ccv=UUID.randomUUID();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public UUID getCcv() {
        return ccv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getHolderNamer() {
        return holderNamer;
    }

    public void setHolderNamer(String holderNamer) {
        this.holderNamer = holderNamer;
    }
}
