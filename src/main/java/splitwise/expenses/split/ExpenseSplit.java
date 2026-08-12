package splitwise.expenses.split;

import java.util.List;

public interface ExpenseSplit {
    public boolean validate(List<Split> splits,double amount);
    public void calculateOweAmount(List<Split> splits,double amount);
}
