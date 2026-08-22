package inventoryManagementSystem.model;

import java.util.Map;

public class Warehouse {
    private Inventory inventory;
    private Address address;

    public Warehouse(Address address) {
        this.address = address;
        this.inventory = new Inventory();
    }


    public void addProductInInventory(Map<Integer, Integer> productCategoryAndCountMap, Product product){
        inventory.addProductFromCategory(productCategoryAndCountMap,product);
    }
    public void removeProductInInventory(Map<Integer, Integer> productCategoryAndCountMap){
        inventory.removeProductFromCategory(productCategoryAndCountMap);
    }
    public Inventory getInventory() {
        return inventory;
    }

    public Address getAddress() {
        return address;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
