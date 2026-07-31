package parkinglot;

import parkinglot.Entity.ParkingSpot;
import parkinglot.Entity.Vehicle;
import parkinglot.LookUpStrategy.ParkingSpotLookupStrategy;
import parkinglot.LookUpStrategy.RandomLookupStrategy;
import parkinglot.Parkinglot.*;
import parkinglot.Payment.CashPayment;
import parkinglot.Payment.UPIpayment;
import parkinglot.Pricing.CostComputation;
import parkinglot.Pricing.FixedPricing;
import parkinglot.SpotManager.FourWheelerManager;
import parkinglot.SpotManager.ParkingSpotManager;
import parkinglot.SpotManager.TwoWheelerManager;
import parkinglot.Ticket.Ticket;
import parkinglot.enums.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingClient {
    public static void main(String[] args){
        ParkingSpotLookupStrategy lookupStrategy=new RandomLookupStrategy();
        Map<VehicleType, ParkingSpotManager> levelOneManager = new HashMap<>();
        levelOneManager.put(VehicleType.TWO_WHEELER,
                new TwoWheelerManager(lookupStrategy,List.of(new ParkingSpot("L1-S1"),
                        new ParkingSpot("L1-S2"))));
        levelOneManager.put(VehicleType.FOUR_WHEELER,
                new FourWheelerManager(lookupStrategy,List.of(new ParkingSpot("L1-S3"))));

        ParkingLevel parkingLevel1=new ParkingLevel(1,levelOneManager);

        Map<VehicleType, ParkingSpotManager> levelTwoManager = new HashMap<>();
        levelTwoManager.put(VehicleType.TWO_WHEELER,
                new TwoWheelerManager(lookupStrategy,List.of(new ParkingSpot("L2-S1"),
                        new ParkingSpot("L2-S2"))));
        levelTwoManager.put(VehicleType.FOUR_WHEELER,
                new FourWheelerManager(lookupStrategy,List.of(new ParkingSpot("L2-S3"))));

        ParkingLevel parkingLevel2=new ParkingLevel(2,levelTwoManager);

        ParkingBuilding parkingBuilding=new ParkingBuilding(
                List.of(parkingLevel1,parkingLevel2),new CostComputation(new FixedPricing())
        );
        ParkingLot parkingLot=new ParkingLot(parkingBuilding,new EntryGate(),
                new ExitGate(new CostComputation(new FixedPricing())));
        Vehicle bike = new Vehicle("bike102",VehicleType.TWO_WHEELER);
        Vehicle car = new Vehicle("car104",VehicleType.FOUR_WHEELER);

        Ticket t1=parkingLot.vehicleArrives(bike);
        Ticket t2 =parkingLot.vehicleArrives(car);
        parkingLot.vehicleExits(t1,new UPIpayment());
        parkingLot.vehicleExits(t2,new CashPayment());
    }
}
