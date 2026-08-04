package carrentalsystem.reservation;

import carrentalsystem.User;
import carrentalsystem.product.VehicleInventoryManager;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationManager {
    private final ReservationRepository repository;
    private final VehicleInventoryManager inventoryManager;

    AtomicInteger reservationIdgenerator=new AtomicInteger(20000);

    public ReservationManager( VehicleInventoryManager inventoryManager) {
        this.repository = new ReservationRepository();
        this.inventoryManager = inventoryManager;
        this.inventoryManager.setReservationRepository(repository);
    }
    public Optional<Reservation> findById(int reservationId){
        return repository.findById(reservationId);

    }
    public Reservation createReservation(int vehicleid, User user, LocalDate from,LocalDate to,ReservationType type){
        int reservedId=reservationIdgenerator.getAndIncrement();
        boolean reserved= inventoryManager.reserve(vehicleid,reservedId,from,to);
        if(!reserved){
            throw new RuntimeException("Vehicle is not available for selected dates");

        }
        Reservation reservation=new Reservation(vehicleid,type,from,to,vehicleid, user.getUserId());
        repository.save(reservation);
        return reservation;
    }
    public void cancelReservation(int reservationID){
        Optional<Reservation> reservation=repository.findById(reservationID);
        if(!reservation.isPresent()){
            throw new RuntimeException("Reservation not found");
        }
        Reservation r=reservation.get();
        r.setReservationStatus(ReservationStatus.CANCELLED);
        inventoryManager.release(r.getVichleId(),reservationID);
        repository.remove(reservationID);

    }
    public void startTrip(int reservationId){
        Reservation r = repository.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation Not Found"));
        r.setReservationStatus(ReservationStatus.INUSE);
    }
    public void submitVehicle(int reservationId){
        Reservation r=repository.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation Not Found"));
        r.setReservationStatus(ReservationStatus.COMPLETED);
        inventoryManager.release(r.getVichleId(),r.getReservationID());

    }
    public void remove(int reservationId){
        repository.remove(reservationId);
    }
}
