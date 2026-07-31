package parkinglot.Parkinglot;

import parkinglot.Entity.Vehicle;
import parkinglot.Payment.Payment;
import parkinglot.Ticket.Ticket;

public class ParkingLot {
    private final ParkingBuilding parkingBuilding;
    private final EntryGate entryGate;
    private final ExitGate exitGate;

    public ParkingLot(ParkingBuilding parkingBuilding, EntryGate entryGate, ExitGate exitGate) {
        this.parkingBuilding = parkingBuilding;
        this.entryGate = entryGate;
        this.exitGate = exitGate;
    }
    public Ticket vehicleArrives(Vehicle vehicle){
        return entryGate.entry(parkingBuilding,vehicle);
    }
    public void vehicleExits(Ticket ticket, Payment payment){
        exitGate.completeExit(parkingBuilding, ticket,payment);
    }
}
