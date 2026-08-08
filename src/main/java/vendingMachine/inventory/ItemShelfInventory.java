package vendingMachine.inventory;

import vendingMachine.model.Item;
import vendingMachine.model.ItemShelf;

public class ItemShelfInventory {
    ItemShelf[] inventory=null;

    public ItemShelfInventory(int totalItem) {
        inventory=new ItemShelf[totalItem];
        initializeInventory();
    }

    private void initializeInventory() {
        int itemCode=101;
        for (int idx = 0; idx < inventory.length; idx++) {
            ItemShelf shelf = new ItemShelf();
            shelf.setItemCode(itemCode);
            shelf.setSoldOut(true);
            inventory[idx] = shelf;
            itemCode++;
        }
    }
    public void addItem(Item item,int itemCode) throws Exception{
        for(ItemShelf i : inventory){
            if(i.getItemCode()==itemCode) {
                if (i.isSoldOut() == true) {
                    i.setItem(item);
                    i.setSoldOut(false);
                }else {
                    throw new RuntimeException("Item is already present in inventory!!");
                }
            }
        }

    }

    public Item getItem(int itemCode) throws Exception{
        for (ItemShelf i:inventory){
            if(i.getItemCode()==itemCode){
                if(i.isSoldOut()==false){
                    return i.getItem();
                }else
                    throw new Exception("Item is already sold!!");
            }
        }
        throw new Exception("Enter the valid Item Code Number !!");
    }
    public void updateSoldOutItem(int itemCode){
        for (ItemShelf i:inventory){
            if(i.getItemCode()==itemCode){
                i.setSoldOut(true);
            }
        }
    }

    public ItemShelf[] getInventory() {
        return inventory;
    }

    public void setInventory(ItemShelf[] inventory) {
        this.inventory = inventory;
    }
}
