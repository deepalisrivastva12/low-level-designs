# Car Rental System - LLD

## Problem Statement
Design a car rental system where users can browse available vehicles across
stores, reserve a vehicle for a date range, start/complete a trip, and pay
via a generated bill — while preventing double-booking of the same vehicle
for overlapping dates.

## Requirements
- Multiple stores, each with its own vehicle inventory
- Support multiple vehicle types (two-wheeler, four-wheeler)
- Check vehicle availability for a given date range
- Reserve a vehicle, preventing overlapping-date double bookings
- Start and complete a trip for a reservation
- Generate a bill based on rental duration and pricing strategy
- Support multiple payment modes (currently UPI)
- Thread-safe reservation handling for concurrent booking requests

## Key Design Decisions
- **Per-vehicle locking (`ReentrantLock`)**: `VehicleInventoryManager` locks
  on a per-vehicle basis rather than globally, so booking requests for
  different vehicles don't block each other — only concurrent requests for
  the *same* vehicle are serialized. Locks are lazily created via
  `putIfAbsent` and stored in a `ConcurrentHashMap`.
- **Date-range overlap check (`DateInterval`)**: availability is determined
  by checking the requested date range against all existing reservations
  for that vehicle, not just a simple "is it booked" boolean — allows
  future reservations on the same vehicle for non-overlapping dates.
- **Strategy Pattern — Billing**: `BillStrategy` interface with
  `DailyBillStrategy` as the current implementation, managed via
  `BillManager`. New pricing models (hourly, weekly) can be added without
  touching reservation or payment logic.
- **Strategy Pattern — Payment**: `PaymentStrategy` interface with
  `UPIPaymentStrategy` currently implemented, managed via `PaymentManager`
  — same reasoning, new payment modes plug in without changing `Store`.
- **Store as orchestrator**: `Store` composes `VehicleInventoryManager`,
  `ReservationManager`, `BillManager`, and `PaymentManager`, and exposes a
  simple public API (`createReservation`, `startTrip`, `generateBill`,
  `makePayment`) — calling code doesn't need to know about the internal
  managers directly.
- **Reservation lifecycle**: reservation is only removed from the active
  reservation manager *after* successful payment (`reservationManager.remove()`
  is called inside `makePayment`), so an unpaid reservation stays tracked.

## Classes
| Class | Responsibility |
|---|---|
| `VehicleRentalStores` | Top-level entry — owns all stores and users |
| `Store` | Orchestrates inventory, reservations, billing, and payment for one location |
| `Location` | Store's physical address |
| `User` | Represents a renter with a driving license |
| `Vehicle` | Represents a rentable vehicle (type, status, daily price) |
| `VehicleInventoryManager` | Tracks vehicles, checks availability, handles atomic reserve/release with per-vehicle locking |
| `DateInterval` | Value object for date range overlap checking |
| `Reservation` (+ `ReservationManager`, `ReservationRepository`) | Manages reservation creation, lookup, and lifecycle |
| `BillStrategy` (+ `DailyBillStrategy`), `BillManager` | Computes and generates bills based on rental duration |
| `PaymentStrategy` (+ `UPIPaymentStrategy`), `PaymentManager` | Processes payment for a generated bill |

## How to Run
```bash
mvn compile
mvn exec:java -Dexec.mainClass="carrentalsystem.Demo"
```

## Tests
```bash
mvn test
```
Located at `src/test/java/carrentalsystem/`

## What I'd Improve
- Add a `CashPaymentStrategy` / more payment modes to fully exercise the strategy pattern
- Add validation for `Location` fields (currently package-private, no getters)
- Handle concurrent reservation cancellation racing with `startTrip`/`submitVehicle`
- Add hourly/weekly billing strategy alongside `DailyBillStrategy`
- `VehicleRentalStores.getUser()` / `getStore()` use list index as ID lookup — this breaks if a store/user is removed mid-list; should use a Map keyed by ID instead