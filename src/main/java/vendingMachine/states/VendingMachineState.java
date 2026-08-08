package vendingMachine.states;

import vendingMachine.VendingMachine;
import vendingMachine.model.Coin;
import vendingMachine.model.Item;

import java.util.List;

public abstract class VendingMachineState {
    public  void clickOnInsertCointButton(VendingMachine machine) throws Exception{

    }
    public  void clickOnSelectProductButton(VendingMachine machin) throws Exception{

    }
    public  void insertCoin(VendingMachine machine, Coin coin) throws Exception{

    }
    public  void chooseProduct(VendingMachine machine,int itemCode) throws Exception{

    }
    public int getChange(int returnChangeMoney) throws  Exception{
        return 0;
    }
    public Item dispenseProduct(VendingMachine machine,int itemCode)throws Exception{
        return null;
    }
    public List<Coin> refundFullMoney(VendingMachine machine){
        return null;
    }
    public void updateInventory(VendingMachine machine, Item item, int codeNumber) throws Exception{

    }
}
