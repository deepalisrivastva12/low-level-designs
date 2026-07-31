package parkinglot.Parkinglot;

import parkinglot.Entity.ParkingSpot;
import parkinglot.Entity.Vehicle;
import parkinglot.SpotManager.ParkingSpotManager;
import parkinglot.enums.VehicleType;

import java.util.Map;

public class ParkingLevel {
    private final int levelNum;
    private final Map<parkinglot.enums.VehicleType, ParkingSpotManager> managers;


    public ParkingLevel(int levelNum, Map<VehicleType, ParkingSpotManager> levelTwoManager) {
        this.levelNum = levelNum;
        this.managers = levelTwoManager;
    }

    public boolean hasAvailablity(VehicleType type){
        ParkingSpotManager manager=managers.get(type);
        return manager!=null && manager.hasFreeSpot();
    }
    public ParkingSpot park(VehicleType type){
        ParkingSpotManager manager=managers.get(type);
        if(manager==null){
            throw new IllegalArgumentException("No Parking Spot is Available for vehicle type:"+ type);
        }
        return manager.park();
    }
    public void unPark(VehicleType type, ParkingSpot parkingSpot){
        ParkingSpotManager manager=managers.get(type);
        if(manager!=null){
            manager.unPark(parkingSpot);

        }
    }

    public int getLevelNum() {
        return levelNum;
    }
}
