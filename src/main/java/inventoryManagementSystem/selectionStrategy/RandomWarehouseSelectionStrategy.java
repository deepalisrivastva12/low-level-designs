package inventoryManagementSystem.selectionStrategy;

import inventoryManagementSystem.model.Warehouse;

import java.util.List;
import java.util.Random;

public class RandomWarehouseSelectionStrategy implements WarehouseSlectionStrategy{
    private final Random random=new Random();
    //for an example for now we have implemented a random warehouse selection
    //but in future we can add nearest warehouse selection strategy too

    @Override
    public Warehouse selectWarehouse(List<Warehouse> warehouseList) throws RuntimeException {
        if(warehouseList==null || warehouseList.isEmpty()){
            throw new RuntimeException("Warehouse list is empty!!");
        }
        int randomNumber = random.nextInt(warehouseList.size());
        return warehouseList.get(randomNumber);
    }
}
