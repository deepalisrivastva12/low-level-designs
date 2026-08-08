package vendingMachine.states;

import vendingMachine.VendingMachine;
import vendingMachine.model.Item;

import java.util.ArrayList;

public class IdleState extends VendingMachineState {
    public IdleState(){
        System.out.println("Currently inventory is idle!!");
    }
    public IdleState(VendingMachine vendingMachine){
        System.out.println("Currently Vending machine is in idle state!!");
        vendingMachine.setCoins(new ArrayList<>());
    }

    @Override
    public void clickOnInsertCointButton(VendingMachine machine) throws Exception {
        machine.setVendingMachineState(new HasMoneyState());
    }

    @Override
    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception {
        machine.getInventory().addItem(item,codeNumber);
    }
}
