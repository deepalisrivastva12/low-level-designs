package carrentalsystem;

import carrentalsystem.BillSystem.Bill;
import carrentalsystem.BillSystem.BillManager;
import carrentalsystem.BillSystem.BillStrategy;
import carrentalsystem.BillSystem.DailyBillStrategy;
import carrentalsystem.payment.*;
import carrentalsystem.product.Vehicle;
import carrentalsystem.product.VehicleInventoryManager;
import carrentalsystem.product.VehicleType;
import carrentalsystem.reservation.Reservation;
import carrentalsystem.reservation.ReservationManager;
import carrentalsystem.reservation.ReservationStatus;
import carrentalsystem.reservation.ReservationType;

import java.time.LocalDate;
import java.util.List;

public class Store {
    private final int storeId;
    private final PaymentManager paymentManager;
    private final VehicleInventoryManager inventoryManager;
    private final ReservationManager reservationManager;
    private final BillManager billManager;
    private final Location location;

    public Store(int storeId, Location location) {
        this.storeId = storeId;
        this.location = location;
        this.inventoryManager=new VehicleInventoryManager();
        this.reservationManager =new ReservationManager(inventoryManager);
        this.billManager=new BillManager(new DailyBillStrategy(inventoryManager));
        this.paymentManager=new PaymentManager(new UPIPaymentStrategy());
    }
    /// ----get the avialble vehicle-----
    public List<Vehicle> getAllVehicle(VehicleType type, LocalDate from,LocalDate to){
        return inventoryManager.getAvailableVehicle(type,from,to);
    }
    //---create an reservation for the desireed vehicle----
    public Reservation createReservation(int vehicleId, User user,
                                         LocalDate from,LocalDate to,ReservationType type){
        return  reservationManager.createReservation(vehicleId,user,from,to,type);
    }
    //---operations after getting reserved vehicle---
    public void cancelReservation(int reservationId){
        reservationManager.cancelReservation(reservationId);
    }
    public void startTrip(int reservationId){
        reservationManager.startTrip(reservationId);
    }
    public void submitVehicle(int reservationId){
        reservationManager.submitVehicle(reservationId);
    }
    //---generate the bill and complete the payment---
    public Bill generateBill(int reservationId,BillStrategy billStrategy){
        Reservation reservation=reservationManager.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation Not Found"));
        billManager.setBillStrategy(billStrategy);
        return billManager.generateBill(reservation);
    }
    public Payment makePayment(PaymentStrategy paymentStrategy,Bill bill,double paymentAmount){
        paymentManager.setPaymentStrategy(paymentStrategy);
        Payment payment=paymentManager.makePayment(bill,paymentAmount);
        if(!bill.isBillPaid()){
            throw new RuntimeException("Payment faileed!!");
        }
        reservationManager.remove(bill.getReservationId());
        return payment;
    }

    public int getStoreId() {
        return storeId;
    }

    public VehicleInventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
