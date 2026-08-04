package carrentalsystem.payment;

import carrentalsystem.BillSystem.Bill;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PaymentManager {

    private PaymentStrategy paymentStrategy;
    ConcurrentMap<Integer,Payment> payments=new ConcurrentHashMap<>();

    public PaymentManager(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    public Payment makePayment(Bill bill,double paymentAmount){
        Payment payment=paymentStrategy.makePayment(bill,paymentAmount);
        payments.put(payment.getPaymentId(),payment);
        return payment;
    }
    public List<Payment> getAllPayment(int billId){
        return payments.values().stream()
                .filter(i->i.getBillId()==billId)
                .toList();
    }
    public Optional<Payment> getPayment(int paymentId){
        return Optional.ofNullable(payments.get(paymentId));
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
}

