package carrentalsystem.payment;

import carrentalsystem.BillSystem.Bill;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class UPIPaymentStrategy implements PaymentStrategy{
    AtomicInteger paymentBillIdGenerator=new AtomicInteger(20000);
    @Override
    public Payment makePayment(Bill bill, double totalAmount) {
        Payment payment=new Payment(paymentBillIdGenerator.getAndIncrement(),
                bill.getBillId(),totalAmount,PaymentMode.UPI,new Date());

        bill.setBillPaid(true);
        return payment;
    }
}
