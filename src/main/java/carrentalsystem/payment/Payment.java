package carrentalsystem.payment;

import java.time.LocalDate;
import java.util.Date;

public class Payment {
    private  final int paymentId;
    private  final int billId;
    private  final double paymentAmount;
    private  final PaymentMode paymentMode;
    private  final Date paymentDate;


    public Payment(int paymentId, int billId, double paymentAmount, PaymentMode paymentMode, Date paymentDate) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.paymentAmount = paymentAmount;
        this.paymentMode = paymentMode;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getBillId() {
        return billId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }
}
