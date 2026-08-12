package splitwise.expenses;

import splitwise.expenses.split.EqualExpenseSplit;
import splitwise.expenses.split.ExpenseSplit;
import splitwise.expenses.split.PercentageExpenseSplit;
import splitwise.expenses.split.UnEqualExpenseSplit;

public class SplitFactory{
    public static ExpenseSplit expenseSplit(ExpenseSplitType type){
        switch (type){
            case EQUAL -> {
                return new EqualExpenseSplit();
            }
            case UNEQUAL -> {
                return new UnEqualExpenseSplit();
            }
            case PERCENTAGE -> {
                return new PercentageExpenseSplit();
            }
            default -> {
                throw new IllegalArgumentException("Invalid Split Type");
            }
        }

    }

}
