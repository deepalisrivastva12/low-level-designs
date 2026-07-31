package parkinglot.SpotManager;

import parkinglot.Entity.ParkingSpot;
import parkinglot.LookUpStrategy.ParkingSpotLookupStrategy;

import java.util.List;

public class FourWheelerManager extends ParkingSpotManager {
    public FourWheelerManager(ParkingSpotLookupStrategy strategy, List<ParkingSpot> parkingSpot) {
        super(strategy, parkingSpot);
    }
}
