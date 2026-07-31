package parkinglot.Payment;

public class CashPayment implements Payment{
    @Override
    public boolean pay(double amount) {
        System.out.println("Cash amount: "+amount+" paid successfully");
        return true;
    }
}
