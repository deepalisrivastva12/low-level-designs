package carrentalsystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VehicleRentalStores {
    private final Map<Integer, Store> stores;
    private final Map<Integer, User> users;

    public VehicleRentalStores() {
        this.stores = new HashMap<>();
        this.users = new HashMap<>();
    }

    public void addStore(Store store){
        stores.put(store.getStoreId(), store);
    }

    public void addUser(User user){
        users.put(user.getUserId(), user);
    }

    public User getUser(int userId){
        User user = users.get(userId);
        if (user == null) {
            throw new RuntimeException("User doesn't exist with id: " + userId);
        }
        return user;
    }

    public Store getStore(int storeId){
        Store store = stores.get(storeId);
        if (store == null) {
            throw new RuntimeException("Store doesn't exist with id: " + storeId);
        }
        return store;
    }

    public void removeStore(int storeId){
        stores.remove(storeId);
    }

    public void removeUser(int userId){
        users.remove(userId);
    }

    public Collection<Store> getAllStores(){
        return stores.values();
    }

    public Collection<User> getAllUsers(){
        return users.values();
    }
}