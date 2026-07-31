# Parking Lot - LLD

## Problem Statement
Design a parking lot system that supports multiple vehicle types (two-wheeler,
four-wheeler), multiple levels, entry/exit gates, ticket generation, and
payment processing on exit.

## Requirements
- Support multiple vehicle types with different spot requirements
- Multiple parking levels, each with its own spot availability per vehicle type
- Issue a ticket when a vehicle enters
- Compute parking cost and accept payment when a vehicle exits
- Support different payment methods (cash, UPI)
- Easily extendable to different pricing strategies and spot-selection strategies

## Key Design Decisions
- **Strategy Pattern — Spot Lookup**: `ParkingSpotLookupStrategy` interface
  with `RandomLookupStrategy` as one implementation. Lets me swap in a
  "nearest empty spot" or "least recently used" strategy later without
  touching `ParkingSpotManager` or higher-level classes.
- **Strategy Pattern — Pricing**: `CostComputation` depends on a pricing
  interface (`FixedPricing` currently implements it). Same reasoning —
  hourly, surge, or membership-based pricing can be added as new classes
  without changing existing code.
- **Separation by Vehicle Type**: `TwoWheelerManager` and `FourWheelerManager`
  each manage their own pool of spots, so allocation logic per vehicle type
  stays independent and doesn't leak into shared logic.
- **Payment abstraction**: `CashPayment` and `UPIpayment` are interchangeable
  at the point of exit — new payment methods can be added without touching
  `ExitGate`.

## Classes
| Class | Responsibility |
|---|---|
| `ParkingLot` | Entry point — owns the building and gates |
| `ParkingBuilding` | Owns a list of levels, delegates parking/unparking |
| `ParkingLevel` | Owns spot managers per vehicle type for that level |
| `ParkingSpotManager` (+ subclasses) | Tracks and allocates spots for one vehicle type |
| `ParkingSpotLookupStrategy` (+ `RandomLookupStrategy`) | Decides which spot to allocate |
| `Ticket` | Issued on entry, used to compute cost on exit |
| `CostComputation` (+ `FixedPricing`) | Calculates cost based on ticket/time |
| `EntryGate` / `ExitGate` | Handles vehicle arrival and departure flow |
| `Payment` (+ `CashPayment`, `UPIpayment`) | Handles payment on exit |

## How to Run
```bash
mvn compile
mvn exec:java -Dexec.mainClass="parkinglot.ParkingClient"
```

## Tests
```bash
mvn test
```
Located at `src/test/java/parkinglot/ParkingLotTest.java`

## What I'd Improve
- Thread-safety for concurrent vehicle entry/exit (currently not handled)
- Dynamic/surge pricing based on occupancy
- Reservation support (pre-booking a spot)
- Better error handling for edge cases (lot full, invalid ticket on exit)