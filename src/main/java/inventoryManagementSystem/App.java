package inventoryManagementSystem;

import inventoryManagementSystem.controllers.CartService;
import inventoryManagementSystem.controllers.OrderController;
import inventoryManagementSystem.controllers.UserController;
import inventoryManagementSystem.controllers.WarehouseController;
import inventoryManagementSystem.model.*;
import inventoryManagementSystem.selectionStrategy.WarehouseSlectionStrategy;

import java.util.List;


public class App {


        UserController userController;
        WarehouseController warehouseController;
        OrderController orderController;
        CartService cartService;

        App(List<User> userList, List<Warehouse> warehouseList){
            userController = new UserController(userList);
            warehouseController = new WarehouseController(warehouseList, null);
            orderController = new OrderController();
            cartService = new CartService();
        }

        public void addProductToCart(User user,
                                     Warehouse warehouse,
                                     Product product,
                                     int count){

            cartService.addItemToCart(
                    user.getUserCart(),
                    warehouse,
                    product,
                    count);
        }
    //get user object
    public User getUser(int userId) throws Exception {
        return userController.getUserById(userId);
    }

    //get warehouse
    public Warehouse getWarehouse(WarehouseSlectionStrategy warehouseSelectionStrategy){
        return warehouseController.selectWarehouse(warehouseSelectionStrategy);

    }

    //get inventory
    public Inventory getInventory(Warehouse warehouse){
        return warehouse.getInventory();

    }

    //place order
    public Order placeOrder(User user, Warehouse warehouse){
        return orderController.createNewOrder(user, warehouse);
    }

    public void checkout(Order order){
        order.checkout();
    }

}
