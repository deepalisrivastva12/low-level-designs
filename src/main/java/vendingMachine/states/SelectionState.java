package vendingMachine.states;

import vendingMachine.VendingMachine;
import vendingMachine.model.Coin;
import vendingMachine.model.Item;

import java.util.List;

public class SelectionState extends VendingMachineState{

    @Override
    public void chooseProduct(VendingMachine machine, int itemCode) throws Exception {
        Item item=machine.getInventory().getItem(itemCode);
        int payment=0;
        for(Coin coin:machine.getCoins()){
            payment+=coin.value;
        }
        if(payment<item.getPrice()){
            System.out.println("Insufficient Amount: The price of "+item+" " +
                    "is "+item.getPrice()+" and the payment has been made of "+payment);
            refundFullMoney(machine);
            throw new Exception("Insufficient Amount");
        } else if (payment>=item.getPrice()) {
            if(payment>item.getPrice()){
                System.out.println("Refunded Amount:"+getChange(payment-item.getPrice()));
            }
            machine.setVendingMachineState(new DispenseState(machine,itemCode));

        }

    }

    @Override
    public List<Coin> refundFullMoney(VendingMachine machine) {
        System.out.println("Refunded the Full money back in the Dispense Tray!!");
        return machine.getCoins();
    }

    @Override
    public int getChange(int returnChangeMoney) throws Exception {
        System.out.println("Refunded the extra money successfully!!\nKindly check the Dispense Tray");
        return returnChangeMoney;
    }
}
