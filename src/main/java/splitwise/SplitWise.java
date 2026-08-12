package splitwise;

import splitwise.balanceSheet.BalanceSheetController;
import splitwise.expenses.ExpenseSplitType;
import splitwise.expenses.split.Split;
import splitwise.group.Group;
import splitwise.group.GroupController;
import splitwise.user.User;
import splitwise.user.UserController;

import java.util.ArrayList;
import java.util.List;

public class SplitWise {
     UserController userController;
    GroupController groupController;

    BalanceSheetController balanceSheetController;

    SplitWise(){
        userController = new UserController();
        groupController = new GroupController();
        balanceSheetController = new BalanceSheetController();
    }

    public void demo(){

        setupUserAndGroup();

        //Step1: add members to the group
        Group group = groupController.getGroupById("G1001");
        group.addGroupMember(userController.getUser("U2001"));
        group.addGroupMember(userController.getUser("U3001"));

        //Step2. create an expense inside a group
        List<Split> splits = new ArrayList<>();
        Split split1 = Split.forPercentage(userController.getUser("U1001"), 10);
        Split split2 = Split.forPercentage(userController.getUser("U2001"), 20);
        Split split3 = Split.forPercentage(userController.getUser("U3001"), 70);
        splits.add(split1);
        splits.add(split2);
        splits.add(split3);
        group.createExpenseForGroup("Breakfast",900,userController.getUser("U1001"), ExpenseSplitType.PERCENTAGE,splits);

        List<Split> splits2 = new ArrayList<>();
        Split splits2_1 =Split.forAmount(userController.getUser("U1001"), 400);
        Split splits2_2 = Split.forAmount(userController.getUser("U2001"), 100);
        splits2.add(splits2_1);
        splits2.add(splits2_2);
        group.createExpenseForGroup( "Lunch",500,userController.getUser("U2001"), ExpenseSplitType.UNEQUAL,splits2);

        for(User user : userController.getAllUsers()) {
            balanceSheetController.showBalanceSheetOfUser(user);
        }
    }

    public void setupUserAndGroup(){

        //onboard user to splitwise app
        addUsersToSplitwiseApp();

        //create a group by user1
        User user1 = userController.getUser("U1001");
        groupController.createGroup("G1001", "Outing with Friends", user1);
    }

    private void addUsersToSplitwiseApp(){

        //adding User1
        User user1 = new User("User1","U1001");

        //adding User2
        User user2 = new User ("User2","U2001");

        //adding User3
        User user3 = new User ("User3","U3001");

        userController.addUser(user1);
        userController.addUser(user2);
        userController.addUser(user3);
    }

}
