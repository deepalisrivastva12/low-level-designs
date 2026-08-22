package inventoryManagementSystem.selectionStrategy;

public class UPIPaymentMode implements PaymentStrategy{
    @Override
    //We can impplement te proper payment mehtod startegy but for now we are returning true for an example
    public boolean makePayment() {
        return true;
    }
}
