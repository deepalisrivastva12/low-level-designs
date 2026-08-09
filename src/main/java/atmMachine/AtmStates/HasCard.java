package atmMachine.AtmStates;

import atmMachine.model.Atm;
import atmMachine.model.Card;
import atmMachine.model.UserBankAccount;


public class HasCard extends AtmMachineState {
    public HasCard(){
        System.out.println("Authenticating the Pin");
    }

    @Override
    public void authenticateCardPin(Card card, Atm atm, int pin) {
        UserBankAccount bankAccount=atm.getBankRepository().getAccountByCard(card.getCardNumber());
        if(bankAccount.getPinNumber()==pin){
            atm.setMachineState(new SelectionState());
        }else {
            System.out.println("Invalid Pin Number!!");
            exit(atm);
        }
    }

    @Override
    public void exit(Atm atm) {
        returnCard();
        atm.setMachineState(new IdleState());
        System.out.println("Exit Successfully!!");
    }

    @Override
    public void returnCard() {
        System.out.println("Kindly take the card out!!");
    }
}
