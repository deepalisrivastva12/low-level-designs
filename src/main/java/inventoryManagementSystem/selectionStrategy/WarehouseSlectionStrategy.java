package inventoryManagementSystem.selectionStrategy;

import inventoryManagementSystem.model.Warehouse;

import java.util.List;

public interface WarehouseSlectionStrategy {
    public Warehouse selectWarehouse(List<Warehouse> warehouseList);
}
