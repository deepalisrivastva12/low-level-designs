package splitwise.expenses.split;

import java.util.List;

public class UnEqualExpenseSplit implements ExpenseSplit{


    @Override
    public boolean validate(List<Split> splits, double amount) {
        double totalAmountSum = 0;
        for (Split split : splits) {
            totalAmountSum += split.getAmountUserOwe();
        }
        if (totalAmountSum != amount) {
            throw new IllegalArgumentException("The total sum of users is not equal to "+amount);
        }
        return true;
    }
    // ExactExpenseSplit — amounts are already given upfront, so calculateSplit can just be a no-op
    @Override
    public void calculateOweAmount(List<Split> splits, double amount) {
        // amountUserOwe already set by caller; nothing to compute
        // (validateSplitRequest is where you'd check sum(amountUserOwe) == totalAmount)
    }
}

