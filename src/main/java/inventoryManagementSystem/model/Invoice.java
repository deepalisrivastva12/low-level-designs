package inventoryManagementSystem.model;

public class Invoice {
    int totalItemPrice=0;
    int totalTax;
    int totalFinalPrice;


    //generate Invoice
    public void generateInvoice(Order order) {

        //it will compute and update the above details
        //for now we have kept the total tax as constant 10
        totalItemPrice += order.getPrice();
        totalTax = 10;
        totalFinalPrice = totalItemPrice + totalTax;
    }
}

