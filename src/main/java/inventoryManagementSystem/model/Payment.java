package inventoryManagementSystem.model;


import inventoryManagementSystem.selectionStrategy.PaymentStrategy;

public class Payment {

    PaymentStrategy paymentMode;

    Payment(PaymentStrategy paymentMode){
        this.paymentMode = paymentMode;
    }

    public boolean makePayment(){
        return paymentMode.makePayment();
    }

}
