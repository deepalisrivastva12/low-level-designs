package parkinglot.Pricing;

import parkinglot.Entity.Vehicle;
import parkinglot.Ticket.Ticket;
import parkinglot.enums.VehicleType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class FixedPricing implements PricingStrategy {
    private static final Map<VehicleType, Double> HOURLY_RATE = Map.of(
            VehicleType.TWO_WHEELER, 10.0,
            VehicleType.FOUR_WHEELER, 20.0
    );

    @Override
    public double calculate(Ticket ticket) {
        LocalDateTime entryTime = ticket.getTime();
        LocalDateTime exitTime = LocalDateTime.now();

        long minutesParked = Duration.between(entryTime, exitTime).toMinutes();

        // Every parking lot bills a minimum of 1 hour, and rounds any
        // partial hour up to the next full hour.
        long hoursParked = (long) Math.ceil(minutesParked / 60.0);
        if (hoursParked < 1) {
            hoursParked = 1;
        }

        double rate = HOURLY_RATE.getOrDefault(ticket.getVehicle().getVehicleType(), 10.0);
        return hoursParked * rate;
    }
}
