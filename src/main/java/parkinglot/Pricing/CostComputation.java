package parkinglot.Pricing;

import parkinglot.Ticket.Ticket;

public class CostComputation {
    private PricingStrategy pricingStrategy;

    public CostComputation(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }
    public double compute( Ticket ticket){
        return pricingStrategy.calculate(ticket);
    }
}

