# 🥤 Vending Machine — Low Level Design (LLD)

A Java implementation of a **Vending Machine** system, built as a Low Level Design practice project. It demonstrates the **State Design Pattern** to model the lifecycle of a real-world vending machine — from idle, to accepting coins, to selecting a product, to dispensing it.

---

## 📌 Overview

This project simulates the core workflow of a vending machine:

1. Machine starts in an **Idle** state, displaying available inventory.
2. User inserts coins → machine transitions to **Has Money** state.
3. User selects a product → machine validates payment, computes change if needed.
4. If payment is sufficient → machine transitions to **Dispense** state, releases the item, marks it sold out, and returns to **Idle**.
5. If payment is insufficient → the inserted money is refunded and the transaction is cancelled.

The design intentionally separates **state-specific behavior** (via the State pattern) from **inventory management** and **domain models**, so new states or item types can be added without touching unrelated code.

---

## 🏗️ Design Pattern Used

### State Pattern

The vending machine's behavior changes based on its current state. Rather than a tangle of `if/else` or `switch` statements, each state is its own class implementing a common `VendingMachineState` contract:

| State | Responsibility |
|---|---|
| `IdleState` | Waiting for a user to start a transaction (insert coins) |
| `HasMoneyState` | Coins inserted; waiting for product selection |
| `SelectionState` | Validates payment against item price, computes change |
| `DispenseState` | Releases the item, updates inventory, resets machine to Idle |

The `VendingMachine` class holds a reference to its **current state** and delegates all user actions (`insertCoin`, `chooseProduct`, etc.) to that state object — a textbook example of favoring composition and polymorphism over conditional logic.

---

## 📂 Project Structure

```
vendingMachine
├── VendingMachine.java          # Core context class — holds current state, inventory, coins
├── VendingMachineApp.java       # Entry point / demo driver
│
├── inventory
│   └── ItemShelfInventory.java  # Manages the array of shelves, stock lookups & updates
│
├── model
│   ├── Coin.java                # Coin denominations & values
│   ├── Item.java                # Product data (type, price)
│   ├── ItemShelf.java           # A single shelf slot (item code, item, sold-out flag)
│   └── ItemType.java            # Enum of product types (COKE, PEPSI, WATER, etc.)
│
└── states
    ├── VendingMachineState.java # Abstract state contract
    ├── IdleState.java
    ├── HasMoneyState.java
    ├── SelectionState.java
    └── DispenseState.java
```

---

## 📊 UML Class Diagram
📌 [Click here to view the UML diagram](UML%20Diagram/uml-diagram.png)

![BookMyShow UML Class Diagram](UML%20Diagram/uml-diagram.png)

## ▶️ Sample Run

```
-----Starting Vending Machine-----
-----Filling Inventory-----

Item Code is: 101 | Item: COKE | Item Price: 30 | Item is available: Yes
...

Clicking on insert coin button
Currently in a HasMoneyState!!
Clicking on product select button
Refunded the extra money successfully!!
Kindly check the Dispense Tray
Product dispensed successfully!!
Currently inventory is idle!!

Item Code is: 102 | Item: COKE | Item Price: 30 | Item is available: No
```

---

## 🚀 How to Run

```bash
# Compile
javac -d target/classes $(find src -name "*.java")

# Run
java -cp target/classes vendingMachine.VendingMachineApp
```

---

## 🧠 Key Learnings / Concepts Practiced

- **State Design Pattern** — modeling a real-world finite state machine cleanly in OOP
- **Encapsulation** — inventory and coin logic hidden behind well-defined class boundaries
- **Exception-driven flow control** — invalid item codes, insufficient payment, and sold-out items are handled via checked exceptions rather than error codes
- Debugging classic Java pitfalls: enhanced `for-each` loops not mutating array contents, stale state references, off-by-one range conditions

---

## 🛠️ Future Improvements

- [ ] Replace flat coin `List` with a proper `CashInventory`/`CoinDispenser` that tracks denomination counts for correct change-making
- [ ] Add a `PaymentState`/`RefundState` split so partial refunds and full refunds aren't conflated
- [ ] Persist inventory (file/DB) instead of in-memory array
- [ ] Add unit tests (JUnit) for each state transition and inventory edge case
- [ ] Add a `SoldOutState` for when the entire machine has no stock left

---

## 📄 License

This project is for personal learning and portfolio purposes.