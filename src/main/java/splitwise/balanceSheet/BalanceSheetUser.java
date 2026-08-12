package splitwise.balanceSheet;

import java.util.HashMap;
import java.util.Map;

public class BalanceSheetUser {
    Map<String,Balance> balanceSheeetPerUser;
    double totalPayment;
    double totalYouOwe;
    double totalGetBackMoney;
    double totalYourExpenses;

    public BalanceSheetUser() {
        this.totalGetBackMoney=0;
        this.totalPayment=0;
        this.totalYouOwe=0;
        this.totalYourExpenses=0;
        this.balanceSheeetPerUser=new HashMap<>();
    }

    public Map<String, Balance> getBalanceSheeetPerUser() {
        return balanceSheeetPerUser;
    }

    public void setBalanceSheeetPerUser(Map<String, Balance> balanceSheeetPerUser) {
        this.balanceSheeetPerUser = balanceSheeetPerUser;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getTotalYouOwe() {
        return totalYouOwe;
    }

    public void setTotalYouOwe(double totalYouOwe) {
        this.totalYouOwe = totalYouOwe;
    }

    public double getTotalGetBackMoney() {
        return totalGetBackMoney;
    }

    public void setTotalGetBackMoney(double totalGetBackMoney) {
        this.totalGetBackMoney = totalGetBackMoney;
    }

    public double getTotalYourExpenses() {
        return totalYourExpenses;
    }

    public void setTotalYourExpenses(double totalYourExpenses) {
        this.totalYourExpenses = totalYourExpenses;
    }
}
