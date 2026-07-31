package parkinglot.Pricing;

import parkinglot.Ticket.Ticket;

public interface PricingStrategy {
     double calculate(Ticket ticket);
}
