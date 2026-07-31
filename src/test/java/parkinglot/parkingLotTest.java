package parkinglot;

import org.junit.jupiter.api.Test;
import parkinglot.Entity.ParkingSpot;
import parkinglot.Entity.Vehicle;
import parkinglot.LookUpStrategy.RandomLookupStrategy;
import parkinglot.Parkinglot.*;
import parkinglot.Payment.CashPayment;
import parkinglot.Pricing.CostComputation;
import parkinglot.Pricing.FixedPricing;
import parkinglot.SpotManager.ParkingSpotManager;
import parkinglot.SpotManager.TwoWheelerManager;
import parkinglot.Ticket.Ticket;
import parkinglot.enums.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class parkingLotTest {

        private ParkingLot buildOneLevelLotWithOneTwoWheelerSpot() {
            Map<VehicleType, ParkingSpotManager> managers = new HashMap<>();
            managers.put(VehicleType.TWO_WHEELER,
                    new TwoWheelerManager(new RandomLookupStrategy(), List.of(new ParkingSpot("L1-S1"))));

            ParkingLevel level = new ParkingLevel(1, managers);
            ParkingBuilding building = new ParkingBuilding(List.of(level), new CostComputation(new FixedPricing()));

            return new ParkingLot(building, new EntryGate(),
                    new ExitGate(new CostComputation(new FixedPricing())));
        }

        @Test
        void vehicleArrives_getsAValidTicket() {
            ParkingLot lot = buildOneLevelLotWithOneTwoWheelerSpot();
            Vehicle bike = new Vehicle("bike1", VehicleType.TWO_WHEELER);

            Ticket ticket = lot.vehicleArrives(bike);

            assertNotNull(ticket);
            assertEquals(bike, ticket.getVehicle());
        }

        @Test
        void secondVehicle_cannotParkWhenLotIsFull() {
            ParkingLot lot = buildOneLevelLotWithOneTwoWheelerSpot();
            lot.vehicleArrives(new Vehicle("bike1", VehicleType.TWO_WHEELER)); // takes the only spot

            assertThrows(RuntimeException.class, () ->
                    lot.vehicleArrives(new Vehicle("bike2", VehicleType.TWO_WHEELER)));
        }

        @Test
        void vehicleExits_freesTheSpot_forTheNextVehicle() {
            ParkingLot lot = buildOneLevelLotWithOneTwoWheelerSpot();
            Ticket ticket = lot.vehicleArrives(new Vehicle("bike1", VehicleType.TWO_WHEELER));

            lot.vehicleExits(ticket, new CashPayment());

            // Spot should be free again, so a new vehicle can park.
            Ticket newTicket = lot.vehicleArrives(new Vehicle("bike2", VehicleType.TWO_WHEELER));
            assertNotNull(newTicket);
        }
    }

