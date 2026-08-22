package inventoryManagementSystem.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Integer , CartItem> productIdAndCartItemMap;

    public Cart() {
        this.productIdAndCartItemMap=new HashMap<>();
    }
    public void addItemInCart(Product product, int count){
        if(productIdAndCartItemMap.containsKey(product.getProductId())){
            CartItem item=productIdAndCartItemMap.get(product.getProductId());
            item.increaseQuantity(count);
        }else {
          productIdAndCartItemMap.put(product.getProductId(),new CartItem(product,count));
        }
    }

    public void removeItem(Product product, int quantity) {

        if (!productIdAndCartItemMap.containsKey(product.getProductId())) {
            return;
        }

        CartItem item = productIdAndCartItemMap.get(product.getProductId());

        if (quantity >= item.getQuantity()) {

            productIdAndCartItemMap.remove(product.getProductId());

        } else {

            item.decreaseQuantity(quantity);
        }
    }

    public void emptyCart() {
        productIdAndCartItemMap.clear();
    }

    public Collection<CartItem> getCartItems() {
        return productIdAndCartItemMap.values();
    }
}
