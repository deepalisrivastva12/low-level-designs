# Elevator System — Low-Level Design

A multi-threaded elevator control system in Java, built as a Low-Level Design (LLD) exercise. It models a small building with multiple elevator cars, each running as its own thread, coordinated through a pluggable dispatch strategy.

## What this demonstrates

This is the kind of problem asked in LLD interviews (design an elevator system), so the goal isn't "an elevator" — it's showing:
- clean separation between **request intake** (floor buttons, in-car buttons), **request routing** (which elevator should handle this?), and **request execution** (the elevator itself)
- a **Strategy pattern** for elevator assignment, so the algorithm can be swapped without touching the rest of the system
- a **producer–consumer concurrency model** — button presses (producers) enqueue requests; each elevator's own thread (consumer) drains and executes them
- one thread per elevator car, each with independent state and independent queues

## Architecture

```
                 ┌───────────────┐
Floor buttons →  │ ExternalButton│ → ExternalDispatcher → ElevatorScheduler
(up/down)        └───────────────┘                               │
                                                      (Strategy) │
                                                                 ▼
In-car buttons →  InternalButton → InternalDispatcher   NearestElevatorStrategy
                        │                                       │
                        └──────────────┬────────────────────────┘
                                       ▼
                              ElevatorController (1 per car)
                              ├─ upMinPQ   (min-heap, closest floor above)
                              ├─ downMaxPQ (max-heap, closest floor below)
                              └─ monitor (wait/notify)
                                        │
                                        ▼
                                  ElevatorCar (moves, has a Door)
```

### Components

| Class | Responsibility |
|---|---|
| `Building` / `Floors` | Holds the building's floors (1-indexed); each floor owns its own up/down `ExternalButton`s |
| `ExternalButton` / `ExternalDispatcher` | A floor call ("I want to go up/down") is routed to the scheduler for elevator *assignment* |
| `InternalButton` / `InternalDispatcher` | A button pressed *inside* a car goes straight to that car's own controller — no assignment needed |
| `ElevatorScheduler` | Delegates "which elevator should serve this floor?" to the configured `ElevatorAssigningStartegy` |
| `NearestElevatorStrategy` | Concrete strategy: prefers an elevator already moving in the same direction and closest to the request; falls back to an idle elevator; falls back to elevator 0 |
| `ElevatorController` | One per car. Owns two priority queues (up-requests as a min-heap, down-requests as a max-heap) and runs the car's control loop on its own thread |
| `ElevatorCar` | Physical state — current floor, direction, door — and the logic to move floor-by-floor toward a destination |
| `Door` | Trivial open/close state holder |

### Concurrency model

Each `ElevatorController` implements `Runnable` and runs an infinite loop:
1. If both queues are empty, `wait()` on a monitor object (car goes `IDLE`).
2. `submitRequest()` (called from any thread — external or internal) adds to the appropriate queue and calls `notifyAll()`.
3. Once woken, the controller drains all "up" requests (nearest-first, via the min-heap), then all "down" requests (nearest-first, via the max-heap), moving the car for each one.

This is a simplified SCAN-style elevator algorithm: it services all pending requests in one direction before reversing, rather than a strict floor-by-floor FCFS.

## Design patterns used

- **Strategy** — `ElevatorAssigningStartegy` / `NearestElevatorStrategy` decouples the assignment algorithm from the scheduler. A new strategy (e.g. round-robin, load-based) can be dropped in via `ElevatorScheduler.setStartegy()` without changing any other class.
- **Producer–Consumer** — button presses (producers) and the elevator control loop (consumer) communicate through thread-safe queues and a `wait`/`notify` monitor, rather than any thread directly calling into another's execution.

## Running it

`Demo.java` wires up a 2-elevator, 5-floor building and fires a sequence of floor calls and in-car button presses with small delays between them to let you observe interleaving in the console output.

```bash
javac -d out $(find . -name "*.java")
java -cp out elevator.Demo
```

Expect console output like:

```
elevator1 is IDLE!
elevator2 is IDLE!
Request Details: 3 accepted by Elevator: 1
Serving floor: 3 by elevator:1 currentFloor: 0
Elevator1 on current floor 0 moving towards 3
...
```

## Known limitations

Being upfront about these rather than hiding them:

- **`enqueueRequest` routes by comparing against `nextFloor`, not `currentFloor`.** Since `nextFloor` defaults to `0` for a freshly created car, the very first request's up/down classification is based on "0" rather than where the car actually is — works fine in practice here since cars start at floor 0, but it's the wrong field to compare against in general and would misroute on a car that had already moved.
- **`NearestElevatorStrategy`'s "same direction" check also compares against `nextFloor`** (the car's current target) rather than `currentFloor`. It's a reasonable approximation but not a precise distance calculation.
- **Door handling is incomplete/inconsistent** — the door is opened only in the "already at destination" early-return case, and `closeDoor()` is called at the end of the *down* branch of `moveElevator` but never in the *up* branch, and never as an "arrived, open door" call for a normal trip. This is cosmetic (console logging) rather than functional, but worth fixing for realism.
- **`contains()` + `offer()` on `PriorityBlockingQueue` is a check-then-act race.** If two threads submit the same destination floor to the same controller at nearly the same instant, both `contains()` checks can pass before either `offer()` lands, producing a duplicate entry. Not fatal (the car just visits the floor twice), but not atomic.
- **Swallowed interrupts**: a couple of `catch (InterruptedException e) {}` blocks (e.g. inside `moveElevator`'s `Thread.sleep`) don't restore the interrupt status via `Thread.currentThread().interrupt()`, which is generally considered bad practice for cooperative shutdown.
- **Dead code / minor cleanup**: an unused `ElevatorCar elevatorCar` field in `NearestElevatorStrategy`, an unused `Objects`/`PriorityQueue`/`FileNameMap` import in `ElevatorController`, and an unused Swing import (`EtchedBorder`) in `Floors.java`.
- **No capacity, no direction-preference for pickups, no fairness/starvation handling** across elevators — out of scope for this exercise but would matter for a production system.

## Possible extensions

- A `FarthestFirst` or `LoadBalanced` strategy to compare against `NearestElevatorStrategy`
- Elevator capacity limits and overload rejection
- A "no requests for N seconds → return to lobby" idle policy
- Metrics: average wait time, average travel time, per-elevator utilization
- Replacing `Thread.sleep`-based simulated movement with a virtual clock for deterministic tests