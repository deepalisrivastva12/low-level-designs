package atmMachine.AtmStates;

import atmMachine.enums.TransactionType;
import atmMachine.model.Atm;
import atmMachine.model.Card;


public class SelectionState extends AtmMachineState {

    public SelectionState() {
        showOperations();
    }

    @Override
    public void selectOperation(Card card, Atm atm, TransactionType type) {
        switch (type){
            case WITHDRAW : atm.setMachineState(new CashWithdraw());
            break;
            case CHECK_BALANCE: atm.setMachineState(new CheckBalanceState());
            break;
            default:
                System.out.println("Invalid option");
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

    private void showOperations() {
        System.out.println("Kindly Select the desired operation!!");
        TransactionType.printAllStates();
    }
}
