package parkinglot.Parkinglot;

import parkinglot.Entity.Vehicle;
import parkinglot.Ticket.Ticket;

public class EntryGate {
    public Ticket entry(ParkingBuilding building, Vehicle vehicle){
        return building.allocateSpot(vehicle);
    }
}
