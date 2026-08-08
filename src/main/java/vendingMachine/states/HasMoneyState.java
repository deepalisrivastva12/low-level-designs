package vendingMachine.states;

import vendingMachine.VendingMachine;
import vendingMachine.model.Coin;
import vendingMachine.model.Item;

import java.util.List;

public class HasMoneyState extends VendingMachineState{

    public HasMoneyState(){
        System.out.println("Currently in a HasMoneyState!!");
    }

    @Override
    public void clickOnSelectProductButton(VendingMachine machin) throws Exception {
        machin.setVendingMachineState(new SelectionState());
    }

    @Override
    public void insertCoin(VendingMachine machine, Coin coin) throws Exception {
        machine.getCoins().add(coin);
    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine machine) {
        System.out.println("Refund completed successfully!!\nKindly check the Dispense Tray");
        machine.setVendingMachineState(new IdleState());
        return machine.getCoins();
    }
}
