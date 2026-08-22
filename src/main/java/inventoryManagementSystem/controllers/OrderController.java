package inventoryManagementSystem.controllers;

import inventoryManagementSystem.model.Order;
import inventoryManagementSystem.model.Warehouse;
import inventoryManagementSystem.model.User;

import java.util.*;

public class OrderController {
    List<Order> orderList;
    Map<Integer, List<Order>> userIDVsOrders;

    public OrderController(){
        orderList = new ArrayList<>();
        userIDVsOrders = new HashMap<>();
    }

    //create New Order
    public Order createNewOrder(User user, Warehouse warehouse){
        Order order = new Order(user, warehouse);
        orderList.add(order);

        if(userIDVsOrders.containsKey(user.getUserId())){
            List<Order> userOrders = userIDVsOrders.get(user.getUserId());
            userOrders.add(order);
            userIDVsOrders.put(user.getUserId(), userOrders);
        } else {
            List<Order> userOrders = new ArrayList<>();
            userOrders.add(order);
            userIDVsOrders.put(user.getUserId(), userOrders);

        }
        return order;
    }


    //remove order
    public void removeOrder(Order order){
        User user=order.getUser();
        if(userIDVsOrders.containsKey(user.getUserId())){
            orderList.remove(user.getUserId());
        } else {
            throw new IllegalArgumentException("Invalid Order Details");

        }
    }

    public List<Order> getOrderByCustomerId(int userId){
        return userIDVsOrders.get(userId);
    }

    public Order getOrderByOrderId(UUID orderId){
        for(Order order:orderList){
            if(order.getOrderId()==orderId){
                return order;
            }
        }
        throw new RuntimeException("Invalid Order Id!!");
    }
}
