package parkinglot.Ticket;

import parkinglot.Entity.ParkingSpot;
import parkinglot.Entity.Vehicle;
import parkinglot.Parkinglot.ParkingLevel;

import java.time.LocalDateTime;

public class Ticket {
    private final ParkingLevel level;
    private Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime time;

    public Ticket(ParkingLevel level, ParkingSpot spot, Vehicle vehicle) {
        this.level = level;
        this.spot = spot;
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
    }

    public ParkingLevel getLevel() {
        return level;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
