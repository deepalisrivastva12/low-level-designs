package atmMachine.AtmStates;

import atmMachine.enums.TransactionType;
import atmMachine.model.Atm;
import atmMachine.model.Card;

public abstract class AtmMachineState {
    public void insertCard(Card card,  Atm atm){
        System.out.println("Try Again!!\nSomething went wring!!");
    }
    public void authenticateCardPin(Card card,  Atm atm,int pin){
        System.out.println("Try Again!!\nSomething went wring!!");
    }
    public void selectOperation(Card card, Atm atm, TransactionType type){
        System.out.println("Try Again!!\nSomething went wring!!");
    }
    public void cashWithdraw(Card card,  Atm atm,int cashWithdrawAmount){
        System.out.println("Try Again!!\nSomething went wring!!");
    }
    public void exit(Atm atm){
        System.out.println("Try Again!!\nSomething went wring!!");
    }
    public void returnCard(){
        System.out.println("Try Again!!\nSomething went wring!!");
    }
    public void displayBalance(Atm atm, Card card){
        System.out.println("Try Again!!\nSomething went wring!!");
    }
}
