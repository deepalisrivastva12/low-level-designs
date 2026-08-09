package atmMachine.repository;

import atmMachine.model.UserBankAccount;

import java.util.HashMap;
import java.util.Map;

public class BankRepository {
    private Map<String, UserBankAccount> cardNumberToAccount=new HashMap<>();

    public void registerCard(String cardNumber,UserBankAccount account){
        cardNumberToAccount.put(cardNumber,account);
    }
    public UserBankAccount getAccountByCard(String  cardNumber){
        return cardNumberToAccount.get(cardNumber);
    }
}
