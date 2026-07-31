package parkinglot.Payment;

public class UPIpayment implements Payment{
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment through UPI with amount: "+amount+" paid successfully");
        return true;
    }
}
