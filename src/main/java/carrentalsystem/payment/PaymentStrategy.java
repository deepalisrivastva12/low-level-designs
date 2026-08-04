package carrentalsystem.payment;


import carrentalsystem.BillSystem.Bill;

public interface PaymentStrategy {
    public Payment makePayment(Bill bill,double totalAmount);

}
