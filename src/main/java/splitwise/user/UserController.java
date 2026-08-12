package splitwise.user;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    List<User> userList;

    public UserController() {
        this.userList=new ArrayList<>();
    }
    public void addUser(User user){
        if(getUser(user.getUserId())!=null){
            throw new IllegalArgumentException("User is already added!!");
        }
        userList.add(user);
    }
    public User getUser(String userId){
        for (User user:userList){
            if(user.getUserId().equals(userId)){
                return user;

            }
        }
        return null;
    }
    public List<User> getAllUsers(){
        return userList;
    }
}
