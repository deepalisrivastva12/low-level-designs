package splitwise.expenses;

import splitwise.expenses.split.Split;
import splitwise.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Expense {
    UUID id;
    String description;
    double expenseAmount;
    User paidBy;
    ExpenseSplitType type;
    List<Split> splits=new ArrayList<>();

    public Expense(String description, double expenseAmount, User paidBy, ExpenseSplitType type, List<Split> splits) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.expenseAmount = expenseAmount;
        this.paidBy = paidBy;
        this.type = type;
        this.splits = splits;
    }
}
