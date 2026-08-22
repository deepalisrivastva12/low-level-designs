# Inventory Management System (LLD)

A low-level design implementation of a warehouse-based inventory & order management system in **Java**, built as an interview-prep exercise. The system models multiple warehouses each maintaining their own inventory, lets users add items to a cart, and walks through order creation, checkout, and payment.

This project focuses on clean **object-oriented design**, **separation of concerns**, and the use of the **Strategy** and **Facade** patterns to keep the design extensible.

---

## Table of Contents

- [Problem Statement](#problem-statement)
- [Features](#features)
- [Design Patterns Used](#design-patterns-used)
- [Project Structure](#project-structure)
- [UML Class Diagram](#uml-class-diagram)
- [Order Flow (Sequence Diagram)](#order-flow-sequence-diagram)
- [Class Overview](#class-overview)
- [How to Run](#how-to-run)
- [Sample Output](#sample-output)
- [Future Enhancements](#future-enhancements)

---

## Problem Statement

Design a system that supports:

- Multiple **warehouses**, each with its own **inventory** of product categories and products.
- **Users** who can browse products, add them to a **cart**, and place an **order**.
- Selecting a warehouse to fulfil an order using a pluggable **selection strategy**.
- Generating an **invoice** and processing **payment** for an order.
- Updating warehouse inventory once an order is checked out.

---

## Features

- Add/remove product categories and products in a warehouse's inventory.
- Add/remove items from a user's cart with quantity tracking.
- Pluggable **warehouse selection strategy** (currently random selection; nearest-warehouse selection can be plugged in later).
- Pluggable **payment strategy** (currently UPI; other modes can be added without touching `Order` or `Payment`).
- Order creation, checkout, invoice generation, and inventory deduction.
- Order history lookup by user ID or order ID via `OrderController`.

---

## Design Patterns Used

| Pattern | Where | Why |
|---|---|---|
| **Strategy** | `WarehouseSlectionStrategy` (→ `RandomWarehouseSelectionStrategy`), `PaymentStrategy` (→ `UPIPaymentMode`) | Lets warehouse selection logic and payment processing vary independently and be swapped at runtime without changing `WarehouseController`, `Order`, or `Payment`. |
| **Facade** | `App` | Provides a single, simplified entry point (`addProductToCart`, `placeOrder`, `checkout`, etc.) that hides the coordination between `UserController`, `WarehouseController`, `OrderController`, and `CartService`. |
| **Layered / MVC-ish separation** | `model` vs `controllers` vs `selectionStrategy` | Domain entities, orchestration logic, and pluggable algorithms are kept in separate packages for clarity and testability. |

---

## Project Structure

```
inventoryManagementSystem
├── controllers
│   ├── CartService.java
│   ├── OrderController.java
│   ├── UserController.java
│   └── WarehouseController.java
├── model
│   ├── Address.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── Inventory.java
│   ├── Invoice.java
│   ├── Order.java
│   ├── OrderStatus.java
│   ├── Payment.java
│   ├── Product.java
│   ├── ProductCategory.java
│   ├── User.java
│   └── Warehouse.java
├── selectionStrategy
│   ├── PaymentStrategy.java
│   ├── RandomWarehouseSelectionStrategy.java
│   ├── UPIPaymentMode.java
│   └── WarehouseSlectionStrategy.java
├── App.java
└── Main.java
```

---

## UML Class Diagram

```mermaid
classDiagram
    class App {
        -UserController userController
        -WarehouseController warehouseController
        -OrderController orderController
        -CartService cartService
        +addProductToCart(User, Warehouse, Product, int)
        +getUser(int) User
        +getWarehouse(WarehouseSlectionStrategy) Warehouse
        +getInventory(Warehouse) Inventory
        +placeOrder(User, Warehouse) Order
        +checkout(Order)
    }

    class UserController {
        -List~User~ userList
        +addUseer(User)
        +removeUseer(User)
        +getUserById(int) User
    }

    class WarehouseController {
        -List~Warehouse~ warehouseList
        -WarehouseSlectionStrategy strategy
        +addWarehouse(Warehouse)
        +removeWarehouse(Warehouse)
        +selectWarehouse(WarehouseSlectionStrategy) Warehouse
    }

    class OrderController {
        -List~Order~ orderList
        -Map~Integer, List~ userIDVsOrders
        +createNewOrder(User, Warehouse) Order
        +removeOrder(Order)
        +getOrderByCustomerId(int) List~Order~
        +getOrderByOrderId(int) Order
    }

    class CartService {
        +addItemToCart(Cart, Warehouse, Product, int)
        +removeItemFromCart(Cart, Product, int)
        +checkout(Cart, Warehouse)
    }

    class User {
        -int userId
        -String userName
        -List~Integer~ orderIds
        -Cart cartDetails
        -Address address
        +getUserCart() Cart
        +getUserId() int
    }

    class Address {
        -String area
        -String city
        -String state
        -String pincode
    }

    class Cart {
        -Map~Integer, CartItem~ productIdAndCartItemMap
        +addItemInCart(Product, int)
        +removeItem(Product, int)
        +emptyCart()
        +getCartItems() Collection~CartItem~
    }

    class CartItem {
        -Product product
        -int quantity
        +increaseQuantity(int)
        +decreaseQuantity(int)
    }

    class Warehouse {
        -Inventory inventory
        -Address address
        +addProductInInventory(Map, Product)
        +removeProductInInventory(Map)
        +getInventory() Inventory
    }

    class Inventory {
        -List~ProductCategory~ productCategorieList
        +addCategory(int, String)
        +addProduct(Product, int)
        +addProductFromCategory(Map, Product)
        +removeProductFromCategory(Map)
        +getCategoryById(int) ProductCategory
    }

    class ProductCategory {
        -int categoryId
        -String categoryName
        -double price
        -List~Product~ products
        +addProduct(Product)
        +removeProduct(int)
    }

    class Product {
        -int productId
        -String productName
        -int categoryId
        -double price
    }

    class Order {
        -User user
        -Address deliveryAddress
        -Map~Integer, CartItem~ productCategoryAndCountMap
        -Warehouse warehouse
        -Invoice invoice
        -Payment payment
        -OrderStatus orderStatus
        -double price
        -UUID orderId
        +checkout()
        +generateOrderInvoice()
    }

    class OrderStatus {
        <<enumeration>>
        PENDING
        SHIPPED
        DELIVERED
    }

    class Invoice {
        -int totalItemPrice
        -int totalTax
        -int totalFinalPrice
        +generateInvoice(Order)
    }

    class Payment {
        -PaymentStrategy paymentMode
        +makePayment() bool
    }

    class PaymentStrategy {
        <<interface>>
        +makePayment() bool
    }

    class UPIPaymentMode {
        +makePayment() bool
    }

    class WarehouseSlectionStrategy {
        <<interface>>
        +selectWarehouse(List~Warehouse~) Warehouse
    }

    class RandomWarehouseSelectionStrategy {
        +selectWarehouse(List~Warehouse~) Warehouse
    }

    App --> UserController
    App --> WarehouseController
    App --> OrderController
    App --> CartService

    UserController --> User
    WarehouseController --> Warehouse
    WarehouseController --> WarehouseSlectionStrategy
    OrderController --> Order
    CartService --> Cart
    CartService --> Warehouse

    User --> Address
    User --> Cart
    Cart --> CartItem
    CartItem --> Product

    Warehouse --> Address
    Warehouse --> Inventory
    Inventory --> ProductCategory
    ProductCategory --> Product

    Order --> User
    Order --> Warehouse
    Order --> Invoice
    Order --> Payment
    Order --> OrderStatus
    Invoice --> Order

    Payment --> PaymentStrategy
    PaymentStrategy <|.. UPIPaymentMode
    WarehouseSlectionStrategy <|.. RandomWarehouseSelectionStrategy
```

---

## Order Flow (Sequence Diagram)

```mermaid
sequenceDiagram
    actor Client
    participant App
    participant WarehouseController
    participant CartService
    participant OrderController
    participant Order
    participant Warehouse
    participant Payment

    Client->>App: getWarehouse(RandomWarehouseSelectionStrategy)
    App->>WarehouseController: selectWarehouse(strategy)
    WarehouseController-->>App: Warehouse

    Client->>App: addProductToCart(user, warehouse, product, qty)
    App->>CartService: addItemToCart(cart, warehouse, product, qty)
    CartService-->>App: item added

    Client->>App: placeOrder(user, warehouse)
    App->>OrderController: createNewOrder(user, warehouse)
    OrderController->>Order: new Order(user, warehouse)
    Order-->>OrderController: order
    OrderController-->>App: Order

    Client->>App: checkout(order)
    App->>Order: checkout()
    Order->>Payment: makePayment()
    Payment-->>Order: isPaymentSuccess

    alt payment failed
        Order-->>App: throws RuntimeException
    else payment succeeded
        Order->>Warehouse: removeProductInInventory(categoryCountMap)
        Order->>Invoice: generateInvoice(this)
        Order->>Order: orderStatus = SHIPPED
        Order->>Order: cart.emptyCart()
    end
```

---

## Class Overview

### Model Layer
| Class | Responsibility |
|---|---|
| `User` | Represents a customer — holds identity, address, cart, and order history. |
| `Address` | Value object for area/city/state/pincode. |
| `Cart` | Holds a map of `productId → CartItem` for a user. |
| `CartItem` | A product + quantity pairing inside a cart. |
| `Warehouse` | Holds an `Inventory` and an `Address`; source of truth for stock at a location. |
| `Inventory` | Holds `ProductCategory` list; supports adding/removing products by category. |
| `ProductCategory` | Groups products of the same type and tracks category-level price. |
| `Product` | A sellable item with id, name, category, and price. |
| `Order` | Snapshots a user's cart into an order, drives checkout, invoice, and inventory deduction. `orderId` is assigned a random `UUID` at creation; `orderStatus` starts `PENDING` and moves to `SHIPPED` once checkout succeeds. |
| `OrderStatus` | Enum: `PENDING`, `SHIPPED`, `DELIVERED`. |
| `Invoice` | Computes item total, tax, and final price for an order. |
| `Payment` | Wraps a `PaymentStrategy` to execute payment. |

### Controller Layer
| Class | Responsibility |
|---|---|
| `UserController` | CRUD-style access to users. |
| `WarehouseController` | CRUD-style access to warehouses + delegates warehouse selection to a strategy. |
| `OrderController` | Creates orders, and looks them up by user or order id. |
| `CartService` | Validates stock and mutates a user's cart; also exposes an alternate checkout path. |

### Strategy Layer
| Class | Responsibility |
|---|---|
| `WarehouseSlectionStrategy` | Interface for picking a warehouse to fulfil an order. |
| `RandomWarehouseSelectionStrategy` | Picks a random warehouse from the list. |
| `PaymentStrategy` | Interface for processing a payment. |
| `UPIPaymentMode` | Stub UPI payment implementation. |

### Facade
| Class | Responsibility |
|---|---|
| `App` | Single entry point wiring together all controllers and services for client code (`Main`). |

---

## How to Run

```bash
# From the project root
javac inventoryManagementSystem/**/*.java inventoryManagementSystem/*.java
java inventoryManagementSystem.Main
```

`Main.java` demonstrates the full flow:
1. Create a warehouse and populate its inventory with categories and products.
2. Create a user.
3. Wire everything up via `App`.
4. Select a warehouse, add a product to the cart, place an order, and check out.

---

## Sample Output

```
Order placed successfully!
```

---

## Future Enhancements

- Add a `NearestWarehouseSelectionStrategy` to demonstrate the Strategy pattern's extensibility.
- Support the full `OrderStatus` lifecycle (e.g. `SHIPPED → DELIVERED`) triggered by delivery/warehouse events.
- Add more `PaymentStrategy` implementations (Card, Wallet, COD).
- Introduce exception classes instead of generic `IllegalArgumentException`/`RuntimeException`.
- Fix `Invoice.totalItemPrice`/`totalFinalPrice` being declared as `int` while summing a `double` price (currently truncates decimals).
- Add unit tests (JUnit) for `Inventory`, `Cart`, and `Order` checkout logic.

---

## Author

**Deepali Srivastava**
B.Tech CSE, Lovely Professional University