package carrentalsystem.BillSystem;

import carrentalsystem.product.VehicleInventoryManager;
import carrentalsystem.reservation.Reservation;

public interface BillStrategy {
    public Bill generateBill(Reservation reservation);
}
