package parkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parkinglot.Entity.ParkingSpot;
import parkinglot.LookUpStrategy.RandomLookupStrategy;
import parkinglot.SpotManager.TwoWheelerManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class parkingSpotManager {
    private TwoWheelerManager manager;
    @BeforeEach
    void setUp(){
        List<ParkingSpot> spotList= List.of(new ParkingSpot("S1"),new ParkingSpot("S2"));
        manager=new TwoWheelerManager(new RandomLookupStrategy(),spotList);
    }
    @Test
    void hasFreeSpot_retursTrue_ifBothSpotsAreFree(){
        assertTrue(manager.hasFreeSpot());
    }
    @Test
    void park_occupiesASpot_andReturnsIt() {
        ParkingSpot spot = manager.park();
        assertNotNull(spot);
        assertFalse(spot.isSpotFree());
    }
    @Test
    void park_returnsNull_whenLotIsFull() {
        manager.park(); // occupies S1 or S2
        manager.park(); // occupies the other one
        assertFalse(manager.hasFreeSpot());

        ParkingSpot thirdAttempt = manager.park();
        assertNull(thirdAttempt, "Parking a 3rd vehicle in a 2-spot lot should fail");
    }
    @Test
    void unPark_freesTheSpot_soItCanBeReused() {
        ParkingSpot spot = manager.park();
        manager.park(); // lot is now full
        assertFalse(manager.hasFreeSpot());

        manager.unPark(spot);
        assertTrue(manager.hasFreeSpot(), "Lot should have space again after unparking");
    }
}
