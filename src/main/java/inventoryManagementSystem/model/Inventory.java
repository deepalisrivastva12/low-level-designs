package inventoryManagementSystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inventory {
    List<ProductCategory> productCategorieList;

    public Inventory() {
        productCategorieList=new ArrayList<>();
    }

    //adding a category in Inventory
    public void addCategory(int id,String categoryName){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(id);
        productCategory.setCategoryName(categoryName);
        productCategorieList.add(productCategory);
    }
    //adding products in category

    public void addProduct(Product product,int productCategoryId) throws Exception {
        ProductCategory productCategory=getCategoryById(productCategoryId);
        if(productCategory!=null){
            productCategory.addProduct(product);
        }
        else throw new IllegalArgumentException("Invalid Category ID!!");
    }
    public void addProductFromCategory(Map<Integer,Integer> categoryIdAndProductCount,Product product){
        for (Map.Entry<Integer,Integer> entry:categoryIdAndProductCount.entrySet()){
            ProductCategory category=getCategoryById(entry.getKey());
            if(category==null) {
                throw new IllegalArgumentException("Invalid Category ID!!");
            }
            for(int i=0;i<entry.getValue();i++){
                category.addProduct(product);
            }
        }
    }

    public void removeProductFromCategory(Map<Integer,Integer> categoryIdAndProductCount){
        for (Map.Entry<Integer,Integer> entry:categoryIdAndProductCount.entrySet()){
            ProductCategory category=getCategoryById(entry.getKey());
            if(category!=null){
                category.removeProduct(entry.getValue());
            }
            else throw new IllegalArgumentException("Invalid Category ID!!");
        }
    }
    public ProductCategory getCategoryById(int categoryId){

        for(ProductCategory category:productCategorieList){
            if(category.getCategoryId()==categoryId){
               return category;
            }
        }
        return null;
    }

    public List<ProductCategory> getProductCategorieList() {
        return productCategorieList;
    }

    public void setProductCategorieList(List<ProductCategory> productCategorieList) {
        this.productCategorieList = productCategorieList;
    }
}
