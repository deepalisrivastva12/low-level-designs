package vendingMachine.states;

import vendingMachine.VendingMachine;
import vendingMachine.model.Item;

public class DispenseState extends VendingMachineState{
    public DispenseState(VendingMachine machine,int itemCode) throws Exception {
        dispenseProduct(machine,itemCode);
    }

    @Override
    public Item dispenseProduct(VendingMachine machine, int itemCode) throws Exception {
        System.out.println("Product dispensed successfully!!");
        Item item=machine.getInventory().getItem(itemCode);
        machine.getInventory().updateSoldOutItem(itemCode);
        machine.setVendingMachineState(new IdleState());
        return item;
    }
}
