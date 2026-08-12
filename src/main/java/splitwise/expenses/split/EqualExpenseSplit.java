package splitwise.expenses.split;

import java.util.List;

public class EqualExpenseSplit implements ExpenseSplit{


    @Override
    public boolean validate(List<Split> splits, double amount) {
        double expectedSplitAmount=amount/splits.size();
        for (Split split:splits){
            if(split.amountUserOwe!=expectedSplitAmount){
                throw new IllegalArgumentException("The split is not equal for user: "+split.getUser().getName());
            }
        }
        return true;
    }

    @Override
    public void calculateOweAmount(List<Split> splits, double amount) {
        double expectedSplitAmount=amount/splits.size();
        for (Split split:splits){
                split.setAmountUserOwe(expectedSplitAmount);
        }
    }
}
