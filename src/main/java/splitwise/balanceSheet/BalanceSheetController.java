package splitwise.balanceSheet;

import splitwise.expenses.split.Split;
import splitwise.user.User;

import java.util.List;
import java.util.Map;

public class BalanceSheetController {
    public void updateBalanceSheetForUsers(User paidBy, List<Split> splits, double expenseAmount) {
        BalanceSheetUser paidByUserBalanceSheet=paidBy.getBalanceSheetUser();
        paidByUserBalanceSheet.setTotalPayment(paidByUserBalanceSheet.getTotalPayment()+expenseAmount);

        for(Split split:splits){
            User oweUser =split.getUser();
            BalanceSheetUser oweUserBalanceSheet=oweUser.getBalanceSheetUser();
            double oweAmount=split.getAmountUserOwe();
            if(paidBy.getUserId().equals(oweUser.getUserId())){
                paidByUserBalanceSheet.setTotalYourExpenses(paidByUserBalanceSheet.getTotalYourExpenses()+oweAmount);
            }
            else {
                Balance userOweBalance;
                paidByUserBalanceSheet.setTotalGetBackMoney(paidByUserBalanceSheet.getTotalGetBackMoney()+oweAmount);
                if(paidByUserBalanceSheet.balanceSheeetPerUser.containsKey(oweUser.getUserId())){
                    userOweBalance=paidByUserBalanceSheet.getBalanceSheeetPerUser().get(oweUser.getUserId());
                }
                else {
                    userOweBalance=new Balance();
                    paidByUserBalanceSheet.getBalanceSheeetPerUser().put(oweUser.getUserId(),userOweBalance);
                }
                userOweBalance.setGetBackMoneyAmount(userOweBalance.getGetBackMoneyAmount()+oweAmount);


                //update the balance sheet of owe user
                oweUserBalanceSheet.setTotalYouOwe(oweUserBalanceSheet.getTotalYouOwe()+oweAmount);
                oweUserBalanceSheet.setTotalYourExpenses(oweUserBalanceSheet.getTotalYourExpenses()+oweAmount);

                Balance userPaidBalance;
                if(oweUserBalanceSheet.balanceSheeetPerUser.containsKey(paidBy.getUserId())){
                   userPaidBalance= oweUserBalanceSheet.getBalanceSheeetPerUser().get(paidBy.getUserId());
                }
                else {
                    userPaidBalance=new Balance();
                    oweUserBalanceSheet.getBalanceSheeetPerUser().put(paidBy.getUserId(),userPaidBalance);
                }
                userPaidBalance.setOweAmount(userPaidBalance.getOweAmount()+oweAmount);

            }
        }
    }
    public void showBalanceSheetOfUser(User user){

        System.out.println("---------------------------------------");

        System.out.println("Balance sheet of user : " + user.getUserId());

        BalanceSheetUser userExpenseBalanceSheet =  user.getBalanceSheetUser();

        System.out.println("TotalYourExpense: " + userExpenseBalanceSheet.getTotalYourExpenses());
        System.out.println("TotalGetBack: " + userExpenseBalanceSheet.getTotalGetBackMoney());
        System.out.println("TotalYourOwe: " + userExpenseBalanceSheet.getTotalYouOwe());
        System.out.println("TotalPaymnetMade: " + userExpenseBalanceSheet.getTotalPayment());

        for(Map.Entry<String, Balance> entry : userExpenseBalanceSheet.getBalanceSheeetPerUser().entrySet()){

            String userID = entry.getKey();
            Balance balance = entry.getValue();

            double netAmount = balance.getGetBackMoneyAmount() - balance.getOweAmount();

            if(netAmount > 0){
                System.out.println("userID:" + userID + " owes you: " + netAmount);
            } else if(netAmount < 0){
                System.out.println("userID:" + userID + " you owe: " + Math.abs(netAmount));
            } else {
                System.out.println("userID:" + userID + " settled up");
            }
        }

        System.out.println("---------------------------------------");

    }
}
