package parkinglot.LookUpStrategy;

import parkinglot.Entity.ParkingSpot;

import java.util.List;

public class RandomLookupStrategy implements ParkingSpotLookupStrategy{

    @Override
    public ParkingSpot selectSpot(List<ParkingSpot> sports) {
        for(ParkingSpot spot:sports){
            if(spot.isSpotFree()){
                return spot;
            }
        }
        return null;
    }
}
