package inventoryManagementSystem.controllers;
import inventoryManagementSystem.model.*;

import java.util.HashMap;
import java.util.Map;

public class CartService {

    public void addItemToCart(Cart cart,
                              Warehouse warehouse,
                              Product product,
                              int quantity) {

        ProductCategory category =
                warehouse.getInventory()
                        .getCategoryById(product.getCategoryId());

        if (category == null) {
            throw new IllegalArgumentException("Category not found.");
        }

        if (category.getProducts().size() < quantity) {
            throw new IllegalArgumentException("Insufficient stock.");
        }

        cart.addItemInCart(product, quantity);
    }

    public void removeItemFromCart(Cart cart,
                                   Product product,
                                   int quantity) {

        cart.removeItem(product, quantity);
    }

    public void checkout(Cart cart, Warehouse warehouse) {

        Map<Integer, Integer> categoryCountMap = new HashMap<>();

        for (CartItem item : cart.getCartItems()) {

            int categoryId = item.getProduct().getCategoryId();

            categoryCountMap.put(
                    categoryId,
                    categoryCountMap.getOrDefault(categoryId, 0)
                            + item.getQuantity()
            );
        }

        warehouse.removeProductInInventory(categoryCountMap);

        cart.emptyCart();
    }
}