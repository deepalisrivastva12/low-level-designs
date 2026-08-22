package inventoryManagementSystem.controllers;

import inventoryManagementSystem.model.Warehouse;
import inventoryManagementSystem.selectionStrategy.WarehouseSlectionStrategy;

import java.util.List;

public class WarehouseController {
    public List<Warehouse> warehouseList;
    WarehouseSlectionStrategy warehouseSlectionStrategy;

    public WarehouseController(List<Warehouse> warehouseList,
                               WarehouseSlectionStrategy warehouseSlectionStrategy) {
        this.warehouseList = warehouseList;
        this.warehouseSlectionStrategy=warehouseSlectionStrategy;
    }
    public void addWarehouse(Warehouse warehouse){
        warehouseList.add(warehouse);
    }
    public void removeWarehouse(Warehouse warehouse){
        warehouseList.remove(warehouse);
    }
    public Warehouse selectWarehouse(WarehouseSlectionStrategy strategy){
        this.warehouseSlectionStrategy=strategy;
        return warehouseSlectionStrategy.selectWarehouse(warehouseList);
    }
}
