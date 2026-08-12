package splitwise.group;

import splitwise.expenses.Expense;
import splitwise.expenses.ExpenseController;
import splitwise.expenses.ExpenseSplitType;
import splitwise.expenses.split.Split;
import splitwise.user.User;

import java.util.ArrayList;
import java.util.List;

public class Group {
    String groupId;
    String groupName;
    List<User> groupList;
    List<Expense> groupExpenses;
    ExpenseController expenseController;
    User admin;

    public Group() {
        groupExpenses=new ArrayList<>();
        groupList=new ArrayList<>();
        expenseController=new ExpenseController();
    }
    public void addGroupMember(User user){
        groupList.add(user);
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public User isAdmin() {
        return admin;
    }

    public Group(List<User> groupList) {
        this.groupList = groupList;
    }

    public List<User> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<User> groupList) {
        this.groupList = groupList;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void removeMember(User user){
        groupList.remove(user);
    }
    public Expense createExpenseForGroup(String description, double expenseAmount,
                                         User paidBy, ExpenseSplitType type, List<Split> splits){
        Expense expense=expenseController.createExpense(description,expenseAmount,paidBy,type,splits);
        groupExpenses.add(expense);
        return expense;
    }
}
