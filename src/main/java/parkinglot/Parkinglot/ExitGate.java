package parkinglot.Parkinglot;

import parkinglot.Payment.Payment;
import parkinglot.Pricing.CostComputation;
import parkinglot.Ticket.Ticket;

public class ExitGate {
    private final CostComputation costComputation;

    public ExitGate(CostComputation costComputation) {
        this.costComputation = costComputation;
    }
    public void completeExit(ParkingBuilding building, Ticket ticket, Payment payment){
        double amoun=calculate(ticket);
        boolean paySuccess=payment.pay(amoun);
        if(!paySuccess){
            throw new RuntimeException("Payment failed!!, Exist denied");
        }
        building.release(ticket);
        System.out.println("Exit successful. Gate opened.");

    }

    private double calculate(Ticket ticket) {
        return costComputation.compute(ticket);
    }
}
