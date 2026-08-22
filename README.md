# 🧩 Low Level Design (LLD) Practice — Java

**11 object-oriented design problems, solved from scratch in Java** — the kind of problems asked in machine-coding / LLD interview rounds at product companies. Every project here is built the same way: identify the entities, pick the right design pattern for the *actual* problem (not just to check a box), and write code that's easy to extend without rewriting existing classes.
This repo is my structured practice ground for SDE interview prep — final-year B.Tech CSE at Lovely Professional University, currently applying to new-grad SDE roles.

> 🚧 **Actively growing** — this isn't a finished set. New LLD problems get added here as I work through them, so expect the project index and pattern table below to keep expanding.

---

## 🧠 Why this repo exists

Most LLD practice repos are either pseudocode sketches or 200-line God classes. The goal here was different: **write each system the way I'd want it reviewed in an interview** — a class diagram I can defend, a design-pattern choice I can justify with a trade-off, and a README that's honest about what's incomplete rather than pretending everything is production-ready.
Every project README below has its own **"What I'd Improve" / "Known Limitations"** section — deliberately, because knowing the gaps in your own design is as important as the design itself in an interview.

---

## 🛠️ Tech Stack

- **Java** (core OOP, no frameworks — the point is the design, not the tooling)
- **Maven** for build/dependency management on select projects
- **JUnit** for the projects with test coverage
- **Mermaid** for class & sequence diagrams (renders natively on GitHub — no export tooling needed)

---

## 📂 Repository Structure

```
low-level-designs/
├── AtmMachine/                  → ATM cash withdrawal & balance check
├── bookingMyShow/                → Movie ticket booking (BookMyShow-style)
├── carrentalsystem/              → Vehicle rental with date-range reservations
├── cricbuzz/                     → Ball-by-ball cricket match simulator
├── elevator/                     → Multi-threaded elevator dispatch system
├── inventoryManagementSystem/    → Warehouse inventory, cart & order flow
├── parkinglot/                   → Multi-level parking lot with entry/exit gates
├── snakeandladder/                → Classic board game simulation
├── splitwise/                    → Expense splitting with balance sheets
├── tictactoe/                    → Configurable n×n Tic Tac Toe
└── vendingMachine/                → Coin-based vending machine
```

> 📎 Folder names above are taken from each project's own package declaration — adjust the paths in the links below if your actual `src/` layout differs.

---

## 📊 Design Pattern Index

The fastest way to see the range of problems covered — cross-referenced by pattern rather than by project, since that's usually how an interviewer probes ("where else have you used Strategy?").

| Pattern | Used In | Core Idea |
|---|---|---|
| **State** | ATM Machine, Vending Machine | Behavior changes based on current stage of a transaction — each stage is its own class instead of a tangle of `if/else` |
| **Strategy** | Inventory Management System (warehouse selection, payment), Splitwise (split calculation), Elevator (car assignment), Parking Lot (spot lookup, pricing), Car Rental System (billing, payment) | Swap an algorithm at runtime without touching the class that uses it — the most-repeated pattern in this repo, on purpose |
| **Chain of Responsibility** | ATM Machine | Cash dispensing cascades through denominations (₹2000 → ₹500 → ₹100), each handler solving what it can and forwarding the rest |
| **Observer** | Cricbuzz | Ball-by-ball events notify batting/bowling scorecards independently, without `BallDetails` knowing how either scorecard works |
| **Singleton** | ATM Machine | One physical ATM ⇒ one shared instance for note inventory and balance |
| **Facade** | Inventory Management System | A single `App` entry point coordinates multiple controllers/services so client code doesn't wire them up itself |
| **Controller → Service layering** | BookMyShow, Splitwise, Inventory Management System | Controllers stay thin; business logic (lookups, validation, orchestration) lives in a service layer underneath |
| **Producer–Consumer (concurrency)** | Elevator | Floor/in-car button presses are producers; each elevator's own thread is a consumer draining a request queue |
| **Repository** | ATM Machine | `BankRepository` decouples the ATM from account storage — the ATM authenticates against the bank, never trusts the card itself |

---

## 📁 Project Index

| # | Project | Problem | Key Pattern(s) | Concurrency? |
|---|---|---|---|---|
| 1 | [ATM Machine](./AtmMachine) | Card auth, PIN check, denomination-wise cash dispensing | State, Chain of Responsibility, Singleton, Repository | — |
| 2 | [BookMyShow](./bookingMyShow) | Browse shows by city/date, lock → confirm seats, book | Controller → Service | — |
| 3 | [Car Rental System](./carrentalsystem) | Multi-store vehicle reservation with overlap-safe booking | Strategy (billing, payment) | ✅ per-vehicle `ReentrantLock` |
| 4 | [Cricbuzz](./cricbuzz) | Ball-by-ball cricket match simulation with scorecards | Observer | — |
| 5 | [Elevator System](./elevator) | Multi-car dispatch with nearest-elevator assignment | Strategy, Producer–Consumer | ✅ one thread per car |
| 6 | [Inventory Management System](./inventoryManagementSystem) | Warehouse inventory, cart, order checkout | Strategy, Facade | — |
| 7 | [Parking Lot](./parkinglot) | Multi-level, multi-vehicle-type parking with billing | Strategy (spot lookup, pricing) | — |
| 8 | [Snake and Ladder](./snakeandladder) | Turn-based board game with random snakes/ladders | Composition-driven design | — |
| 9 | [Splitwise](./splitwise) | Expense splitting (equal / exact / percentage) with net balances | Strategy, Factory Method | — |
| 10 | [Tic Tac Toe](./tictactoe) | Configurable n×n board with win/draw detection | Clean state separation (Board vs. Game) | — |
| 11 | [Vending Machine](./vendingMachine) | Coin-based purchase flow with change & refunds | State | — |

Each project folder has its **own README** with a full class diagram, sequence diagram (where relevant), design rationale, sample output, and a "what I'd improve" section — this top-level README is the map; the details live one level down.

---

## 🏗️ What This Repo Demonstrates

- **Picking the pattern that fits the problem, not the reverse.** State makes sense for the ATM and vending machine because their behavior genuinely depends on transaction stage; Strategy shows up wherever an algorithm needed to vary independently (pricing, splitting, routing) without new patterns forced in just for variety.
- **Concurrency where the problem actually calls for it.** The elevator system and car rental system are the two places actual concurrent access matters (multiple cars operating independently, multiple users booking the same vehicle pool), so those are the two with explicit thread/lock handling — not sprinkled in everywhere for show.
- **SOLID as a working habit, not a checklist.** Controllers stay thin and delegate to services; new split types, payment modes, or pricing strategies are new classes, not new `if` branches in existing ones.
- **Honest documentation.** Every project's README names its own known limitations — the kind of self-review an interviewer is actually trying to test for in a design discussion.

---

## ▶️ Running a Project

Each project is runnable independently — see its own README for the exact command, since a couple use Maven and the rest use plain `javac`/`java`. General pattern:

```bash
# Plain javac/java projects
javac -d out $(find <projectFolder> -name "*.java")
java -cp out <package>.<EntryPointClass>

# Maven-based projects (Car Rental System, Parking Lot, Tic Tac Toe)
mvn compile
mvn exec:java -Dexec.mainClass="<package>.<EntryPointClass>"
```

---

## 🎯 About Me

**Deepali Srivastava**
B.Tech Computer Science Engineering, Lovely Professional University (2026)
Currently prepping for and applying to SDE new-grad roles.

If you're an interviewer or a fellow LLD-prep grinder browsing this repo — feedback, issues, and PRs are welcome. Every project here is meant to be a starting point for a design discussion, not a finished product.
