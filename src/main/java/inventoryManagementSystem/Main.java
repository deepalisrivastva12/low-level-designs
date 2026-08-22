package inventoryManagementSystem;

import inventoryManagementSystem.model.*;
import inventoryManagementSystem.selectionStrategy.RandomWarehouseSelectionStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        Main mainObj = new Main();

        // 1. Create warehouses
        List<Warehouse> warehouseList = new ArrayList<>();
        warehouseList.add(mainObj.addWarehouseAndItsInventory());

        // 2. Create users
        List<User> userList = new ArrayList<>();
        userList.add(mainObj.createUser());

        // 3. Initialize the system
        App app = new App(userList, warehouseList);

        // 4. Run the complete flow
        mainObj.runDeliveryFlow(app, 1);
    }

    private Warehouse addWarehouseAndItsInventory() throws Exception {

        Warehouse warehouse = new Warehouse(new Address("Malabar","Agra","UP","232001"));
        Inventory inventory = new Inventory();

        // Categories
        inventory.addCategory(1, "Pepsi Large Cold Drink");
        inventory.addCategory(4, "Dove Small Soap");

        // Products
        Product product1 = new Product(1,"Pepsiii",1,100);
        Product product2 = new Product(2, "cokee", 1,150);
        Product product3 = new Product(3, "Dovee", 4,50);

        inventory.addProduct(product1, 1);
        inventory.addProduct(product2, 1);
        inventory.addProduct(product3, 4);

        warehouse.setInventory(inventory);

        return warehouse;
    }

    private User createUser() {

        User user = new User(1,"Dips",new Address("Ghanta Ghar","Agra","UP","2420023"));
        return user;
    }

    private void runDeliveryFlow(App app, int userId) throws Exception {

        // 1. Get user
        User user = app.getUser(userId);

        // 2. Select warehouse
        Warehouse warehouse = app.getWarehouse(new RandomWarehouseSelectionStrategy());

        // 3. Get inventory
        Inventory inventory = app.getInventory(warehouse);

        // 4. Find the product we want (Pepsi)
        Product productToOrder = null;

        for (ProductCategory category : inventory.getProductCategorieList()) {

            if (category.getCategoryName().equals("Pepsi Large Cold Drink")) {

                if (!category.getProducts().isEmpty()) {
                    productToOrder = category.getProducts().get(0);
                }

                break;
            }
        }

        if (productToOrder == null) {
            throw new RuntimeException("Product not found.");
        }

        // 5. Add two Pepsis to cart
        app.addProductToCart(user, warehouse, productToOrder, 2);

        // 6. Place order
        Order order = app.placeOrder(user, warehouse);

        // 7. Checkout
        app.checkout(order);

        System.out.println("Order placed successfully!");
    }
}