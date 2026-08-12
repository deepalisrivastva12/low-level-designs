package splitwise.expenses.split;

import java.util.List;
import java.util.Map;

public class PercentageExpenseSplit implements ExpenseSplit{
    private static final double EPSILON=0.01;
    @Override
    public boolean validate(List<Split> splits, double amount) {
        double totalPercentageSum=0;
        for (Split split:splits){
            if(split.getPercentage()<=0){
                throw new IllegalArgumentException("Percentage for User: "+split.getUser().getName()+" is not greater than 0");
            }
            totalPercentageSum+=split.getPercentage();
        }
        if(Math.abs(totalPercentageSum-100)>EPSILON){
            throw new IllegalArgumentException("Percentage Sum should be 100");
        }
        return true;
     }

    @Override
    public void calculateOweAmount(List<Split> splits, double amount) {
        for (Split split:splits){
            double calculatedAmount=(split.getPercentage()*amount)/100;
            split.setAmountUserOwe(calculatedAmount);
        }
    }
}
