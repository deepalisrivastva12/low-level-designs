package atmMachine.model;

import java.util.UUID;

public class UserBankAccount {
    private UUID accountNumber;
    private int pinNumber;
    private double balance;

    public UserBankAccount(double balance, int pinNumber) {
        this.balance = balance;
        this.pinNumber = pinNumber;
        this.accountNumber=UUID.randomUUID();
    }

    public UUID getAccountNumber() {
        return accountNumber;
    }
    public int getPinNumber() {
        return pinNumber;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
