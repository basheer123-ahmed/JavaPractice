/*
 * Program 65: Encapsulation Demo
 * 
 * Problem Statement:
 *   Create a BankAccount class with private fields, public getters/setters
 *   with validation (no negative balance), deposit/withdraw methods with
 *   proper validation, and transaction history tracking.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Account Creation ===
 *   Account: ACC-1001 | Holder: Alice Johnson | Balance: $1000.00
 *   === Deposit ===
 *   Deposited $500.00. New balance: $1500.00
 *   === Withdrawal ===
 *   Withdrew $200.00. New balance: $1300.00
 *   === Invalid Operations ===
 *   Error: Cannot deposit negative amount: $-100.00
 *   Error: Insufficient funds. Balance: $1300.00, Requested: $5000.00
 *   === Transaction History ===
 *   1. DEPOSIT: +$500.00 | Balance: $1500.00
 *   2. WITHDRAWAL: -$200.00 | Balance: $1300.00
 *   ...
 * 
 * Explanation:
 *   Encapsulation is the bundling of data (fields) and methods that operate
 *   on that data into a single unit (class), restricting direct access to
 *   some components. This is achieved through:
 *   - Private fields: data is hidden from outside access
 *   - Public methods: controlled access via getters/setters
 *   - Validation in setters: ensures data integrity
 *   - Immutable fields: some data should never change after creation
 *   
 *   Benefits:
 *   - Data hiding and protection
 *   - Flexibility to change internal representation
 *   - Validation and business rules enforcement
 *   - Controlled access and security
 * 
 * Time Complexity: O(1) for deposit/withdraw, O(n) for transaction history
 * Space Complexity: O(n) for n transactions stored in history
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Transaction record (immutable data class)
class Transaction {
    // All fields are private and final (immutable)
    private final String type;      // DEPOSIT, WITHDRAWAL, TRANSFER
    private final double amount;
    private final double balanceAfter;
    private final String timestamp;
    private final String description;

    public Transaction(String type, double amount, double balanceAfter, String description) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.description = description;
    }

    // Only getters, no setters (immutable)
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getTimestamp() { return timestamp; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        String sign = type.equals("DEPOSIT") ? "+" : "-";
        return String.format("%s: %s$%.2f | Balance: $%.2f | %s | %s",
                             type, sign, amount, balanceAfter, description, timestamp);
    }
}

// Fully encapsulated BankAccount class
class BankAccount {
    // Private fields - cannot be accessed directly from outside
    private final String accountNumber;    // Immutable after creation
    private final String accountHolder;    // Immutable after creation
    private double balance;
    private final String accountType;      // SAVINGS, CHECKING
    private boolean isActive;
    private String pin;
    private final List<Transaction> transactionHistory;
    private double dailyWithdrawLimit;
    private double withdrawnToday;

    // Static field for auto-generating account numbers
    private static int nextAccountId = 1001;

    // Constructor with validation
    public BankAccount(String accountHolder, double initialDeposit, String accountType, String pin) {
        // Validate inputs
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty");
        }
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }
        if (pin == null || pin.length() != 4 || !pin.matches("\\d{4}")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits");
        }

        this.accountNumber = "ACC-" + nextAccountId++;
        this.accountHolder = accountHolder.trim();
        this.balance = initialDeposit;
        this.accountType = accountType;
        this.pin = pin;
        this.isActive = true;
        this.transactionHistory = new ArrayList<>();
        this.dailyWithdrawLimit = 10000.0;
        this.withdrawnToday = 0;

        if (initialDeposit > 0) {
            transactionHistory.add(new Transaction("DEPOSIT", initialDeposit, balance, "Initial deposit"));
        }
    }

    // Convenience constructor
    public BankAccount(String accountHolder, double initialDeposit) {
        this(accountHolder, initialDeposit, "SAVINGS", "0000");
    }

    // =================== GETTERS (Controlled Read Access) ===================

    public String getAccountNumber() {
        return accountNumber; // Read-only, no setter
    }

    public String getAccountHolder() {
        return accountHolder; // Read-only, no setter
    }

    public double getBalance() {
        return balance; // Read-only - balance changes only via deposit/withdraw
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return isActive;
    }

    // Returns UNMODIFIABLE list - external code can't modify our internal list
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public double getDailyWithdrawLimit() {
        return dailyWithdrawLimit;
    }

    // =================== SETTERS (Controlled Write Access) ===================

    // No setter for balance! Only deposit/withdraw can change it.
    // No setter for accountNumber or accountHolder - they're immutable.

    public void setDailyWithdrawLimit(double limit) {
        if (limit <= 0) {
            System.out.println("  ✗ Error: Withdraw limit must be positive.");
            return;
        }
        this.dailyWithdrawLimit = limit;
        System.out.println("  ✓ Daily withdraw limit set to: $" + String.format("%.2f", limit));
    }

    public boolean changePin(String oldPin, String newPin) {
        if (!this.pin.equals(oldPin)) {
            System.out.println("  ✗ Error: Incorrect old PIN.");
            return false;
        }
        if (newPin == null || newPin.length() != 4 || !newPin.matches("\\d{4}")) {
            System.out.println("  ✗ Error: New PIN must be exactly 4 digits.");
            return false;
        }
        this.pin = newPin;
        System.out.println("  ✓ PIN changed successfully.");
        return true;
    }

    // =================== BUSINESS METHODS ===================

    // Deposit with validation
    public boolean deposit(double amount) {
        if (!isActive) {
            System.out.println("  ✗ Error: Account is deactivated.");
            return false;
        }
        if (amount <= 0) {
            System.out.printf("  ✗ Error: Cannot deposit negative/zero amount: $%.2f%n", amount);
            return false;
        }
        if (amount > 100000) {
            System.out.println("  ✗ Error: Single deposit cannot exceed $100,000.00");
            return false;
        }

        balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount, balance, "Cash deposit"));
        System.out.printf("  ✓ Deposited $%.2f. New balance: $%.2f%n", amount, balance);
        return true;
    }

    // Withdraw with validation
    public boolean withdraw(double amount) {
        if (!isActive) {
            System.out.println("  ✗ Error: Account is deactivated.");
            return false;
        }
        if (amount <= 0) {
            System.out.printf("  ✗ Error: Cannot withdraw negative/zero amount: $%.2f%n", amount);
            return false;
        }
        if (amount > balance) {
            System.out.printf("  ✗ Error: Insufficient funds. Balance: $%.2f, Requested: $%.2f%n",
                              balance, amount);
            return false;
        }
        if (withdrawnToday + amount > dailyWithdrawLimit) {
            System.out.printf("  ✗ Error: Daily withdraw limit ($%.2f) would be exceeded.%n",
                              dailyWithdrawLimit);
            return false;
        }

        balance -= amount;
        withdrawnToday += amount;
        transactionHistory.add(new Transaction("WITHDRAWAL", amount, balance, "Cash withdrawal"));
        System.out.printf("  ✓ Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        return true;
    }

    // Transfer between accounts
    public boolean transferTo(BankAccount recipient, double amount) {
        if (recipient == null) {
            System.out.println("  ✗ Error: Invalid recipient account.");
            return false;
        }
        if (this == recipient) {
            System.out.println("  ✗ Error: Cannot transfer to the same account.");
            return false;
        }
        if (amount <= 0 || amount > balance) {
            System.out.printf("  ✗ Error: Invalid transfer amount: $%.2f (Balance: $%.2f)%n",
                              amount, balance);
            return false;
        }

        // Atomically update both accounts
        this.balance -= amount;
        recipient.balance += amount;

        String desc = "Transfer to " + recipient.accountNumber;
        this.transactionHistory.add(new Transaction("WITHDRAWAL", amount, this.balance, desc));

        desc = "Transfer from " + this.accountNumber;
        recipient.transactionHistory.add(new Transaction("DEPOSIT", amount, recipient.balance, desc));

        System.out.printf("  ✓ Transferred $%.2f from %s to %s%n",
                          amount, this.accountNumber, recipient.accountNumber);
        return true;
    }

    // Deactivate account
    public void deactivate() {
        this.isActive = false;
        System.out.println("  Account " + accountNumber + " has been deactivated.");
    }

    public void activate() {
        this.isActive = true;
        System.out.println("  Account " + accountNumber + " has been activated.");
    }

    // Print transaction history
    public void printTransactionHistory() {
        System.out.println("  Transaction History for " + accountNumber + ":");
        if (transactionHistory.isEmpty()) {
            System.out.println("    No transactions yet.");
            return;
        }
        for (int i = 0; i < transactionHistory.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + transactionHistory.get(i));
        }
    }

    // Display account summary
    public void displaySummary() {
        System.out.println("  ┌─────────────────────────────────────────────┐");
        System.out.printf("  │ Account: %-35s │%n", accountNumber);
        System.out.printf("  │ Holder:  %-35s │%n", accountHolder);
        System.out.printf("  │ Type:    %-35s │%n", accountType);
        System.out.printf("  │ Balance: $%-34.2f │%n", balance);
        System.out.printf("  │ Status:  %-35s │%n", (isActive ? "ACTIVE" : "INACTIVE"));
        System.out.printf("  │ Transactions: %-30d │%n", transactionHistory.size());
        System.out.println("  └─────────────────────────────────────────────┘");
    }
}

public class EncapsulationDemo {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 65: Encapsulation Demo            ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // 1. Account Creation
        System.out.println("=== Account Creation ===");
        BankAccount alice = new BankAccount("Alice Johnson", 1000.00, "SAVINGS", "1234");
        BankAccount bob = new BankAccount("Bob Smith", 2500.00, "CHECKING", "5678");
        alice.displaySummary();
        bob.displaySummary();

        // 2. Cannot access private fields directly
        System.out.println("=== Encapsulation in Action ===");
        System.out.println("  // alice.balance = -1000;  ← COMPILE ERROR: balance is private");
        System.out.println("  // alice.pin = \"hack\";     ← COMPILE ERROR: pin is private");
        System.out.println("  Accessing via getter: Balance = $" + String.format("%.2f", alice.getBalance()));

        // 3. Deposits
        System.out.println("\n=== Deposits ===");
        alice.deposit(500.00);
        alice.deposit(250.75);

        // 4. Withdrawals
        System.out.println("\n=== Withdrawals ===");
        alice.withdraw(200.00);

        // 5. Validation - Invalid Operations
        System.out.println("\n=== Validation - Invalid Operations ===");
        alice.deposit(-100.00);            // Negative deposit
        alice.deposit(0);                  // Zero deposit
        alice.withdraw(-50.00);            // Negative withdrawal
        alice.withdraw(50000.00);          // Exceeds balance

        // 6. Transfer
        System.out.println("\n=== Transfer Between Accounts ===");
        alice.transferTo(bob, 300.00);
        System.out.println("  Alice's balance: $" + String.format("%.2f", alice.getBalance()));
        System.out.println("  Bob's balance: $" + String.format("%.2f", bob.getBalance()));

        // 7. Invalid Transfer
        System.out.println("\n=== Invalid Transfers ===");
        alice.transferTo(alice, 100.00);   // Self-transfer
        alice.transferTo(bob, 50000.00);   // Exceeds balance
        alice.transferTo(null, 100.00);    // Null recipient

        // 8. PIN Change
        System.out.println("\n=== PIN Change (Encapsulated) ===");
        alice.changePin("wrong", "9999");  // Wrong old PIN
        alice.changePin("1234", "abc");    // Invalid new PIN
        alice.changePin("1234", "9999");   // Success

        // 9. Daily Withdraw Limit
        System.out.println("\n=== Daily Withdraw Limit ===");
        alice.setDailyWithdrawLimit(500.00);
        alice.withdraw(400.00);             // Should succeed
        alice.withdraw(200.00);             // Should fail - exceeds daily limit

        // 10. Account Deactivation
        System.out.println("\n=== Account Deactivation ===");
        alice.deactivate();
        alice.deposit(100.00);              // Should fail - account inactive
        alice.withdraw(50.00);              // Should fail - account inactive
        alice.activate();
        alice.deposit(100.00);              // Should succeed now

        // 11. Transaction History
        System.out.println("\n=== Transaction History ===");
        alice.printTransactionHistory();
        System.out.println();
        bob.printTransactionHistory();

        // 12. Immutable list protection
        System.out.println("\n=== Immutable List Protection ===");
        List<Transaction> history = alice.getTransactionHistory();
        try {
            history.add(new Transaction("HACK", 999999, 999999, "Hacked!")); // Should throw
        } catch (UnsupportedOperationException e) {
            System.out.println("  ✓ Cannot modify transaction history externally!");
            System.out.println("    UnsupportedOperationException caught: list is unmodifiable");
        }

        // 13. Final Summary
        System.out.println("\n=== Final Account Summaries ===");
        alice.displaySummary();
        bob.displaySummary();

        // 14. Constructor validation
        System.out.println("=== Constructor Validation ===");
        try {
            new BankAccount("", 1000.00);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Empty name rejected: " + e.getMessage());
        }
        try {
            new BankAccount("Test", -500.00);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Negative deposit rejected: " + e.getMessage());
        }
        try {
            new BankAccount("Test", 100.00, "SAVINGS", "12");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Invalid PIN rejected: " + e.getMessage());
        }

        // Summary
        System.out.println("\n=== Encapsulation Principles Demonstrated ===");
        System.out.println("  1. Private fields: balance, pin, transactionHistory");
        System.out.println("  2. Public getters: controlled read access");
        System.out.println("  3. Validation in setters/methods: no negative balance");
        System.out.println("  4. Immutable fields: accountNumber, accountHolder");
        System.out.println("  5. Business logic in methods: deposit(), withdraw()");
        System.out.println("  6. Defensive copies: unmodifiable transaction list");
        System.out.println("  7. Data integrity: all state changes go through methods");
    }
}
