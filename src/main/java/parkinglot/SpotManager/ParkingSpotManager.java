package parkinglot.SpotManager;

import parkinglot.Entity.ParkingSpot;
import parkinglot.LookUpStrategy.ParkingSpotLookupStrategy;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class ParkingSpotManager {
    protected List<ParkingSpot> parkingSpot;
    ReentrantLock lock=new ReentrantLock();
    protected final ParkingSpotLookupStrategy strategy;

    public ParkingSpotManager(ParkingSpotLookupStrategy strategy, List<ParkingSpot> parkingSpot) {
        this.strategy = strategy;
        this.parkingSpot = parkingSpot;
    }

    public ParkingSpot park(){
        lock.lock();
        try{
            ParkingSpot spot=strategy.selectSpot(parkingSpot);
            if (spot == null) {
                return null;
            }
            spot.occupySpot();
            return spot;
        }
        finally {
            lock.unlock();
        }
    }
    public void unPark(ParkingSpot spot){
        lock.lock();
        try{
            spot.releaseSpot();
        }finally {
            lock.unlock();
        }
    }
    public boolean hasFreeSpot()
    {
        lock.lock();
        try{
            return parkingSpot.stream().anyMatch(ParkingSpot::isSpotFree);
        }finally {
            lock.unlock();
        }
    }

}
