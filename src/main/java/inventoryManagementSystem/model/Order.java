package inventoryManagementSystem.model;

import inventoryManagementSystem.selectionStrategy.UPIPaymentMode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Order {
    private User user;
    private Address deliveryAddress;
    private Map<Integer,CartItem> productCategoryAndCountMap;
    private Warehouse warehouse;
    private Invoice invoice;
    private Payment payment;
    private OrderStatus orderStatus;
    private double price;
    private UUID orderId;

    public Order(User user, Warehouse warehouse){
        this.user = user;
        this.deliveryAddress = user.getAddress();
        this.productCategoryAndCountMap = user.cartDetails.productIdAndCartItemMap;
        this.warehouse = warehouse;
        this.invoice = new Invoice();
        this.payment = null;
        this.orderStatus = OrderStatus.PENDING;
        invoice.generateInvoice(this);
        orderId=UUID.randomUUID();
    }

    public void checkout() {

        Cart cart = user.getUserCart();

        Map<Integer, Integer> categoryCountMap = new HashMap<>();
        price = 0;
        for (CartItem item : cart.getCartItems()) {

            int categoryId = item.getProduct().getCategoryId();

            categoryCountMap.put(
                    categoryId,
                    categoryCountMap.getOrDefault(categoryId, 0)
                            + item.getQuantity()
            );
            price+=item.product.getPrice();
        }
        //make payment
        boolean isPaymentSuccess = makePayment(new UPIPaymentMode());

        if (!isPaymentSuccess) {
            throw new RuntimeException("Payment failed for order.");
        }

        warehouse.removeProductInInventory(categoryCountMap);

        invoice.generateInvoice(this);   // now price is correct
        orderStatus = OrderStatus.SHIPPED;


        cart.emptyCart();
    }

    private boolean makePayment(UPIPaymentMode upiPaymentMode) {
        payment=new Payment(upiPaymentMode);
        return payment.makePayment();
    }
    public void generateOrderInvoice(){
        invoice.generateInvoice(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Map<Integer, CartItem> getProductCategoryAndCountMap() {
        return productCategoryAndCountMap;
    }

    public void setProductCategoryAndCountMap(Map<Integer, CartItem> productCategoryAndCountMap) {
        this.productCategoryAndCountMap = productCategoryAndCountMap;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getOrderId() {
        return orderId;
    }


}
