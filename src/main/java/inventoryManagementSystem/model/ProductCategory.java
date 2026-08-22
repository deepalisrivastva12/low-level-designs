package inventoryManagementSystem.model;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
    private int categoryId;
    private String categoryName;
    private double price;
    private List<Product> products=new ArrayList<>();

    public void addProduct( Product product){
        products.add(product);
        price+=product.getPrice();
    }
    public void removeProduct(int count){
        if(count > products.size()){
            throw new IllegalArgumentException("Not enough products in inventory");
        }
        while (count>0){
            Product product=products.get(0);
            price-=product.getPrice();
            products.remove(0);
            count--;
        }
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price){
        this.price=price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
