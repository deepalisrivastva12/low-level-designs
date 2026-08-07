# BookMyShow — Low Level Design

A simplified movie-ticket-booking system (inspired by BookMyShow) built to practice
Object-Oriented Design, SOLID principles, and the **Controller → Service** layering pattern.

## Features

- Browse movies playing in a selected city on a selected date
- List theatres in a city showing a selected movie
- List shows for a movie at a selected theatre
- Lock, confirm, and release seats during booking
- Create a booking with payment confirmation
- Fetch a booking by ID or by user

## Package Structure

```
bookingMyShow
├── BookMyShow.java              # Entry point / demo driver
├── Controllers
│   ├── TheatreController.java   # Delegates to TheatreServices
│   └── BookingController.java   # Delegates to BookingServices
├── Services
│   ├── TheatreServices.java     # Movie/Theatre/Show lookup logic
│   └── BookingServices.java     # Booking creation & retrieval logic
├── model
│   ├── Movie.java
│   ├── Screen.java
│   ├── Theatre.java
│   ├── Show.java
│   ├── Seat.java
│   ├── User.java
│   ├── Payment.java
│   └── Booking.java
└── enums
    ├── Category.java            # SILVER, GOLD, PLATINUM
    ├── City.java                # BANGALORE, LUCKNOW, ...
    ├── PaymentStatus.java       # SUCCESS, FAILED
    └── SeatStatus.java          # AVAILABLE, LOCKED, BOOKED
```

## UML Class Diagram

📌 [Click here to view the UML diagram](UML%20Diagram/uml_diagram.png)

![BookMyShow UML Class Diagram](UML%20Diagram/uml_diagram.png)
## Design Notes

- **Controller → Service pattern**: Controllers expose a thin public API; all business logic
  (lookups, booking creation, seat locking) lives in the Service layer, keeping controllers
  free of implementation detail.
- **`computeIfAbsent` multimaps**: Both `Screen.showsByDate` and `TheatreServices.theatreListByCity`
  use `Map<Key, List<Value>>` populated via `computeIfAbsent`, avoiding manual null-checks when
  grouping shows/theatres.
- **Seat locking flow**: `Show.lockSeat()` is called before payment is attempted; on success,
  `confirmSeat()` finalizes the booking, and on failure `releaseSeat()` frees the seats back up —
  preventing double-booking during the payment window.
- **Immutable-safe lookups**: `getShows()` and `getTheatreService()`/`getMoviesService()` return
  `List.of()` / empty collections instead of `null` when nothing matches, so callers never hit a
  `NullPointerException` on an empty result.

## Running the Demo

```bash
javac -d out $(find bookingMyShow -name "*.java")
java -cp out bookingMyShow.BookMyShow
```

The `main` method seeds two theatres (INOX in Bangalore, PVR in Lucknow) with sample shows,
then walks through a full user flow: login → pick date/city → pick movie → pick theatre →
pick show → pick seats → book.

## Possible Improvements

- Replace in-memory `Map`-based storage with a persistence layer (JPA/JDBC)
- Add concurrency handling (e.g. `synchronized` or optimistic locking) around `lockSeat`
  for real multi-user seat contention
- Introduce a `PaymentService` abstraction instead of a hardcoded `PaymentStatus.SUCCESS`
- Add validation/exception types instead of generic `RuntimeException`
- Unit tests for `TheatreServices` and `BookingServices`