package parkinglot.Parkinglot;

import parkinglot.Entity.ParkingSpot;
import parkinglot.Entity.Vehicle;
import parkinglot.Pricing.CostComputation;
import parkinglot.Ticket.Ticket;

import java.util.List;

public class ParkingBuilding {
    private List<ParkingLevel > levels;

    public ParkingBuilding(List<ParkingLevel> levels, CostComputation costComputation) {
        this.levels = levels;
    }
    public Ticket allocateSpot(Vehicle vehicle){
        for(ParkingLevel level:levels){
            if(level.hasAvailablity(vehicle.getVehicleType())){
                ParkingSpot spot=level.park(vehicle.getVehicleType());
                if(spot!=null){
                    System.out.println("Parking allocated at level: "
                            + level.getLevelNum()
                            + " spot: " + spot.getSpotId());
                    return new Ticket(level,spot,vehicle);
                }
            }
        }
        throw new RuntimeException("Parking is full");
    }
    public void release(Ticket ticket){
        ticket.getLevel().unPark(ticket.getVehicle().getVehicleType(),
                ticket.getSpot());
    }
}
