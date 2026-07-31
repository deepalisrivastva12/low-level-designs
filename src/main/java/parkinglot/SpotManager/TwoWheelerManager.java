package parkinglot.SpotManager;

import parkinglot.Entity.ParkingSpot;
import parkinglot.LookUpStrategy.ParkingSpotLookupStrategy;

import java.util.List;

public class TwoWheelerManager extends ParkingSpotManager{
    public TwoWheelerManager(ParkingSpotLookupStrategy strategy, List<ParkingSpot> parkingSpot) {
        super(strategy, parkingSpot);
    }
}
