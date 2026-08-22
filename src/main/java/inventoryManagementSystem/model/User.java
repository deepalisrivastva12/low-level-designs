package inventoryManagementSystem.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String userName;
    private List<Integer> orderIds;
    Cart cartDetails;
    private Address address;

    public User(int id, String name,Address address) {
        this.userId=id;
        this.userName=name;
        this.orderIds=new ArrayList<>();
        this.cartDetails=new Cart();
        this.address=address;

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Cart getUserCart(){
        return cartDetails;
    }

    public List<Integer> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(int orderId) {
        orderIds.add(orderId);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
