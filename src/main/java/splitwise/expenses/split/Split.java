package splitwise.expenses.split;

import splitwise.user.User;

public class Split {
    User user;
    double amountUserOwe;
    int percentage;

    private Split(User user) {
        this.user = user;
    }

    public static Split forAmount(User user, double amountUserOwe) {
        Split split = new Split(user);
        split.amountUserOwe = amountUserOwe;
        return split;
    }

    public static Split forPercentage(User user, int percentage) {
        Split split = new Split(user);
        split.percentage = percentage;
        return split;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmountUserOwe() {
        return amountUserOwe;
    }

    public void setAmountUserOwe(double amountUserOwe) {
        this.amountUserOwe = amountUserOwe;
    }
}
