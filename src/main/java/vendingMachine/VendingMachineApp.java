package vendingMachine;

import vendingMachine.model.Coin;
import vendingMachine.model.Item;
import vendingMachine.model.ItemShelf;
import vendingMachine.model.ItemType;
import vendingMachine.states.VendingMachineState;

public class VendingMachineApp {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();
        try {
            System.out.println("-----Starting Vending Machine-----");
            System.out.println("-----Filling Inventory-----");
            fillingUpInventory(machine);
            displayInventory(machine);
            System.out.println("Clicking on insert coin button");

            VendingMachineState state=machine.getVendingMachineState();
            state.clickOnInsertCointButton(machine);
            state=machine.getVendingMachineState();
            state.insertCoin(machine, Coin.DIME);
            state.insertCoin(machine,Coin.QUARTER);

            System.out.println("Clicking on product select button");
            state.clickOnSelectProductButton(machine);
            state=machine.getVendingMachineState();

            state.chooseProduct(machine,102);

            displayInventory(machine);
        }catch (Exception e){
            displayInventory(machine);
        }
    }

    public static void fillingUpInventory(VendingMachine machine) {
        ItemShelf[] shelves=machine.getInventory().getInventory();
        for (int i =0;i<shelves.length;i++){
            Item item=new Item();
            if(i>=0 && i<2){
                item.setItemType(ItemType.COKE);
                item.setPrice(30);
            } else if (i>=2 && i<5) {
                item.setItemType(ItemType.JUICE);
                item.setPrice(20);
            } else if (i>=5 && i<7) {
                item.setItemType(ItemType.PEPSI);
                item.setPrice(10);
            } else if (i>=7 && i<9) {
                item.setItemType(ItemType.LEMON_WATER);
                item.setPrice(15);
            } else if (i>=9 && i<10) {
                item.setItemType(ItemType.WATER);
                item.setPrice(12);
            }
            shelves[i].setItem(item);
            shelves[i].setSoldOut(false);
        }
    }
    public static void displayInventory(VendingMachine machine){
        System.out.println("--------Inventory-------");
        for (ItemShelf shelf:machine.getInventory().getInventory()){
            System.out.print("Item Code is: "+shelf.getItemCode()+" | ");
            System.out.print("Item: "+shelf.getItem().getItemType()+" | ");
            System.out.print("Item Price: "+shelf.getItem().getPrice()+" | ");
            System.out.println("Item is available: "+ (!shelf.isSoldOut()? "Yes":"No"));
        }
    }
}
