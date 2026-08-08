package vendingMachine;

import vendingMachine.inventory.ItemShelfInventory;
import vendingMachine.model.Coin;
import vendingMachine.states.IdleState;
import vendingMachine.states.VendingMachineState;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    ItemShelfInventory inventory;
    List<Coin> coins;
    VendingMachineState vendingMachineState;

    public VendingMachine() {
        this.vendingMachineState = new IdleState();
        coins=new ArrayList<>();
        inventory=new ItemShelfInventory(10);
    }

    public ItemShelfInventory getInventory() {
        return inventory;
    }

    public void setInventory(ItemShelfInventory inventory) {
        this.inventory = inventory;
    }

    public VendingMachineState getVendingMachineState() {
        return vendingMachineState;
    }

    public void setVendingMachineState(VendingMachineState vendingMachineState) {
        this.vendingMachineState = vendingMachineState;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
