package carrentalsystem.BillSystem;

import carrentalsystem.product.Vehicle;
import carrentalsystem.product.VehicleInventoryManager;
import carrentalsystem.reservation.Reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DailyBillStrategy implements BillStrategy{

    private VehicleInventoryManager inventoryManager;

    public DailyBillStrategy(VehicleInventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }
    AtomicInteger billIdGenerator= new AtomicInteger(20000);

    private long countDays(LocalDate from,LocalDate to){
        long days= ChronoUnit.DAYS.between(from,to)+1;
        return days;
    }
    @Override
    public Bill generateBill(Reservation reservation) {
        Vehicle vehicle=inventoryManager.getVehicle(reservation.getVichleId());
        long totaldays=countDays(reservation.getReservationDateFrom(),reservation.getReservationDateTo());
        double rate=vehicle.getDailyRentalprice();

        double totalAmount = totaldays*rate;

        return new Bill(totalAmount, billIdGenerator.getAndIncrement(),reservation.getReservationID());
    }
}

