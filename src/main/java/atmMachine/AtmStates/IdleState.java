package atmMachine.AtmStates;

import atmMachine.model.Atm;
import atmMachine.model.Card;


public class IdleState extends AtmMachineState {
    @Override
    public void insertCard(Card card, Atm atm) {
        System.out.println("Card is inserted!!");
        atm.setMachineState(new HasCard());
    }
}
