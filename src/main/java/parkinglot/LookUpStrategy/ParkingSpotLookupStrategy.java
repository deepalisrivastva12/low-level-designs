package parkinglot.LookUpStrategy;

import parkinglot.Entity.ParkingSpot;

import java.util.List;

public interface ParkingSpotLookupStrategy {
    ParkingSpot selectSpot(List<ParkingSpot> sports);
}
