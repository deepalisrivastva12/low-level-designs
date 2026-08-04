package carrentalsystem.BillSystem;

public class Bill {
    private int reservationId;
    private int billId;
    private boolean billPaid;
    private double totalAmount;

    public Bill(double totalAmount, int billId, int reservationId) {
        this.totalAmount = totalAmount;
        this.billId = billId;
        this.reservationId = reservationId;
        billPaid=false;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public boolean isBillPaid() {
        return billPaid;
    }

    public void setBillPaid(boolean billPaid) {
        this.billPaid = billPaid;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

