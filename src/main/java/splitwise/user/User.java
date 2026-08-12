package splitwise.user;

import splitwise.balanceSheet.BalanceSheetController;
import splitwise.balanceSheet.BalanceSheetUser;

public class User {
    String name;

    String userId;
    BalanceSheetUser balanceSheetUser;


    public User(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.balanceSheetUser=new BalanceSheetUser();
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public BalanceSheetUser getBalanceSheetUser() {
        return balanceSheetUser;
    }

}
