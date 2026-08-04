package carrentalsystem;

import java.util.ArrayList;
import java.util.List;

public class VehicleRentalStores {
    private final Map<Integer, Store> stores;
    private final Map<Integer, User> users;

    public VehicleRentalStores() {
        this.stores=new ArrayList<>();
        this.users=new ArrayList<>();
    }
    public void addStore(Store store){
        stores.add(store);
    }
    public void addUser(User user){
        users.add(user);
    }
    public User getUser(int userId){
        return users.get(userId);
    }
    public Store getStore(int storeId){
        return stores.get(storeId);
    }
    public void removeStore(int storeId){
        stores.remove(storeId);
    }
    public void removeUser(int userId){
        stores.remove(userId);
    }

}
