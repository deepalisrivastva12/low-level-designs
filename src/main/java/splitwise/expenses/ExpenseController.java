package splitwise.expenses;

import splitwise.balanceSheet.BalanceSheetController;
import splitwise.expenses.split.ExpenseSplit;
import splitwise.expenses.split.PercentageExpenseSplit;
import splitwise.expenses.split.Split;
import splitwise.user.User;

import java.util.List;

public class ExpenseController {
    BalanceSheetController balanceSheetController;
    public ExpenseController() {
        balanceSheetController=new BalanceSheetController();
    }
    public Expense createExpense(String description, double expenseAmount,
                                 User paidBy, ExpenseSplitType type, List<Split> splits){
        ExpenseSplit expenseSplit =SplitFactory.expenseSplit(type);
        expenseSplit.validate(splits,expenseAmount);
        expenseSplit.calculateOweAmount(splits,expenseAmount);
        Expense expense=new Expense(description,expenseAmount,paidBy,type,splits);
        balanceSheetController.updateBalanceSheetForUsers(paidBy,splits,expenseAmount);
        return expense;
    }
}
