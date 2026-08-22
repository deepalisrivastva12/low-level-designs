package inventoryManagementSystem.controllers;

import inventoryManagementSystem.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    List<User> userList;

    public UserController() {
        this.userList = new ArrayList<>();
    }

    public UserController(List<User> userList) {
        this.userList = userList;
    }

    public void addUseer(User user){
        userList.add(user);
    }
    public void removeUseer(User user){
        userList.remove(user);
    }
    public User getUserById(int id) throws Exception{
        for (User user:userList){
            if(user.getUserId()==id){
                return user;
            }
        }
       throw new IllegalArgumentException("Invalid User Id!!");
    }
}
