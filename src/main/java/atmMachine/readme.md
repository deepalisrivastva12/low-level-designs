# 🏧 ATM Machine — Low Level Design (LLD)

A clean, object-oriented implementation of an ATM system in Java, built to demonstrate real-world **Low Level Design** principles — state management, chain-based processing, and clear separation of concerns between the ATM hardware, the bank's records, and the user's card.

This project is part of my ongoing **LLD practice series**, where I take classic system design interview problems and implement them from scratch with proper design patterns instead of quick hacks.

---

## 📌 Why this project

Most "toy" ATM implementations hardcode a PIN on the card object and skip the interesting parts. This one intentionally models the ATM the way a real one works:

- The **card** only carries identity (card number, expiry, holder name) — it never knows its own PIN or balance.
- The **bank** (via a repository) is the single source of truth for PIN and account balance — the ATM authenticates *against* the bank, not against the card.
- The **ATM's own cash** (physical note inventory) is tracked separately from the **user's bank balance** — withdrawing money validates both independently, exactly like a real machine would.
- Cash dispensing is handled by a **denomination cascade** (₹2000 → ₹500 → ₹100), not a single lump-sum deduction.

---

## 🎯 Features

- Insert card → Authenticate PIN → Select operation → Withdraw cash / Check balance
- ATM state transitions modeled explicitly (Idle → Card Inserted → Pin Authenticated → Transaction → Idle)
- Real-time tracking of ATM's physical note inventory (₹2000 / ₹500 / ₹100 notes)
- Independent validation of ATM cash availability vs. user's bank account balance
- Denomination-wise cash dispensing using a Chain of Responsibility

---

## 🧠 Design Patterns Used

| Pattern | Where | Why |
|---|---|---|
| **State Pattern** | `AtmMachineState` and its implementations (`IdleState`, `CashWithdraw`, etc.) | The ATM behaves differently at each stage of a transaction — inserting a card, entering a PIN, or withdrawing cash only makes sense in specific states. Each state encapsulates its own valid operations instead of one giant `if-else` machine. |
| **Chain of Responsibility** | `CashWithdrawProccess` → `TwoThousandProcess` → `FiveHundredProcess` → `OneHundredProcess` | Cash is dispensed by cascading through denominations — each handler deducts what it can and passes the remainder down the chain, mirroring how a real ATM's note dispenser logic works. |
| **Singleton** | `Atm.getAtmObj()` | An ATM room has exactly one physical machine — its note inventory and balance must be a single shared instance, not recreated per transaction. |
| **Repository Pattern** | `BankRepository` | Decouples the ATM from account storage. The ATM never trusts the card for balance/PIN — it always looks up the account via the bank's own records, keyed by card number. |

---

## 🔄 Deep Dive #1 — State Pattern (the ATM's brain)

An ATM is a textbook case for the **State design pattern**: what the machine *lets you do* depends entirely on *what stage of the transaction you're in*. You can't withdraw cash before entering a PIN, and you can't enter a PIN before inserting a card. Rather than encoding that as a tangle of booleans and `if-else` checks, each stage is modeled as its own class.

```java
public abstract class AtmMachineState {
    public void insertCard(Card card, Atm atm)                          { /* default: invalid in this state */ }
    public void authenticateCardPin(Card card, Atm atm, int pin)        { /* default: invalid in this state */ }
    public void selectOperation(Card card, Atm atm, TransactionType t)  { /* default: invalid in this state */ }
    public void cashWithdraw(Card card, Atm atm, int amount)            { /* default: invalid in this state */ }
    public void checkBalance(Card card, Atm atm)                        { /* default: invalid in this state */ }
    public abstract void exit(Atm atm);
    public abstract void returnCard();
}
```

Each concrete state overrides **only the operations that make sense for it**, and the transition to the *next* state happens by simply swapping out `atm.machineState`:

```java
public class IdleState extends AtmMachineState {
    @Override
    public void insertCard(Card card, Atm atm) {
        System.out.println("Card is inserted!!");
        atm.setMachineState(new CardInsertedState());   // ← transition
    }
}
```

**The flow, state by state:**

```
IdleState  ──insertCard()──▶  CardInsertedState  ──authenticateCardPin()──▶  PinAuthenticatedState
                                                                                      │
                                                                          selectOperation()
                                                                                      │
                                                       ┌──────────────────────────────┴──────────────────────────────┐
                                                       ▼                                                              ▼
                                                CashWithdraw                                                 CheckBalanceState
                                                       │                                                              │
                                                    exit()                                                        exit()
                                                       └──────────────────────────────┬───────────────────────────────┘
                                                                                       ▼
                                                                                  IdleState
```

**Why this matters over a naive approach:** the `Atm` class itself never needs to know or care what stage of a transaction it's in — it just delegates every call to `atm.getMachineState()`. Add a new transaction type (say, "Mini Statement") tomorrow, and it's a *new class*, not a new `if` branch buried inside an existing method. That's the whole point of the State pattern: **behavior that varies by state becomes a class hierarchy instead of conditional logic.**

---

## 💸 Deep Dive #2 — Chain of Responsibility (cash dispensing)

Once a withdrawal is approved, the ATM has to figure out *which physical notes* to hand over — and it has to do that greedily, denomination by denomination, exactly the way a real cash dispenser does. This is a clean fit for the **Chain of Responsibility** pattern: each handler tries to satisfy as much of the amount as it can using *its own* denomination, then passes whatever's left to the next handler in line.

```java
public abstract class CashWithdrawProccess {
    protected CashWithdrawProccess nextProcess;

    public CashWithdrawProccess(CashWithdrawProccess nextProcess) {
        this.nextProcess = nextProcess;
    }

    public void withdraw(Atm atm, int amount) {
        if (nextProcess != null) {
            nextProcess.withdraw(atm, amount);   // fallback: forward to the next handler
        }
    }
}
```

Each concrete handler overrides `withdraw()` with its own denomination logic, and calls `super.withdraw(atm, remaining)` to hand off whatever it couldn't cover:

```java
public class TwoThousandProcess extends CashWithdrawProccess {
    public TwoThousandProcess(CashWithdrawProccess next) { super(next); }

    @Override
    public void withdraw(Atm atm, int amount) {
        int requiredNotes = amount / 2000;
        int remaining     = amount % 2000;

        if (requiredNotes <= atm.getTwoThousandsNotes()) {
            atm.deduct2kNotes(requiredNotes);
        } else {
            atm.deduct2kNotes(atm.getTwoThousandsNotes());
            remaining += (requiredNotes - atm.getTwoThousandsNotes()) * 2000;
        }

        if (remaining != 0) {
            super.withdraw(atm, remaining);   // hand off to FiveHundredProcess
        }
    }
}
```

The chain itself is assembled once, at the point of withdrawal — inside `CashWithdraw.cashWithdraw(...)`:

```java
@Override
public void cashWithdraw(Card card, Atm atm, int cashWithdrawAmount) {
    
    CashWithdrawProccess chain = new TwoThousandProcess(
                                      new FiveHundredProcess(
                                          new OneHundredProcess(null)));
    chain.withdraw(atm, cashWithdrawAmount);
}
```

**Tracing ₹3500 through the chain** (ATM stock: 3×₹2000, 7×₹500, 5×₹100):

| Handler | Notes needed | Notes used | Amount passed down |
|---|---|---|---|
| `TwoThousandProcess` | 1 | 1 × ₹2000 | ₹1500 |
| `FiveHundredProcess` | 3 | 3 × ₹500 | ₹0 |
| `OneHundredProcess` | — | never invoked | — |

**Why this matters over a naive approach:** each handler is completely unaware of the others — `TwoThousandProcess` doesn't know `FiveHundredProcess` exists, it just knows there's *some* `nextProcess` it can defer to. That means adding a new denomination (say, ₹200 notes) means writing one new class and re-wiring the chain's construction — no existing handler's code changes at all. This is the Chain of Responsibility payoff: **a request travels through a sequence of independent handlers, and each one decides locally how much of it to handle.**

---

## 🗺️ UML Class Diagram
📌 [Click here to view the UML diagram](UML%20Diagram/uml-diagram.png)

![ATM Machine UML Class Diagram](UML%20Diagram/uml-diagram.png)

---

## 📂 Project Structure

```
AtmMachine/
├── ATMRoom.java                     # Entry point — wires up ATM, bank, and user
├── model/
│   ├── Atm.java                     # Singleton — holds note inventory + balance
│   ├── Card.java                    # Card identity only (no PIN/balance)
│   ├── User.java
│   └── UserBankAccount.java         # PIN + balance, owned by the bank
├── repository/
│   └── BankRepository.java          # cardNumber → UserBankAccount lookup
├── AtmStates/
│   ├── AtmMachineState.java         # Abstract state
│   ├── IdleState.java
│   ├── CashWithdraw.java
│   └── ...                          # Other transaction states
├── CashWithdrawProcess/
│   ├── CashWithdrawProccess.java    # Abstract chain handler
│   ├── TwoThousandProcess.java
│   ├── FiveHundredProcess.java
│   └── OneHundredProcess.java
└── enums/
    └── TransactionType.java
```

---

## ▶️ How to Run

```bash
git clone https://github.com/<your-username>/LLD_Designs.git
cd LLD_Designs
javac -d out $(find . -name "*.java")
java -cp out AtmMachine.ATMRoom
```

**Sample output:**
```
-----Current Status of ATM-----
Total Number of 2k Notes: 3
Total Number of 500 Notes: 7
Total Number of 100 Notes: 5
Current ATM Balance: 10000
Card is inserted!!
Authenticating the Pin
Kindly Select the desired operation!!
WITHDRAW
CHECK_BALANCE
-----Cash Withdrawn-----
Current balance in user's account: 1500.0
-----Current Status of ATM-----
Total Number of 2k Notes: 2
Total Number of 500 Notes: 4
Total Number of 100 Notes: 5
Current ATM Balance: 6500
```

---

## 🔮 Possible Improvements

- Return a success/failure signal from the withdrawal chain instead of assuming the full amount always gets dispensed, and only deduct the ATM's total balance after confirming the notes were actually available.
- Add a `Bank` entity above `BankRepository` to support multiple banks per ATM (real ATMs serve cards from many banks, not just one).
- Replace `System.out.println`-based prompts with a proper `AtmDisplay`/`AtmInput` abstraction for easier testing.
- Add unit tests around the note-dispensing chain for edge cases (exact denomination shortages, zero balance, etc.)

---

## 🧾 About

Built as hands-on practice for Low Level Design interviews — focused on writing extensible, pattern-driven Java rather than a working-but-rigid script. Feedback and PRs welcome.