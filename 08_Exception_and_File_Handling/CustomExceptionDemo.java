/*
 * Program 97: Custom Exception Demo
 * 
 * Problem Statement:
 *   Demonstrate how to create and use custom exceptions in Java:
 *   - Custom CHECKED exception: InsufficientBalanceException (extends Exception)
 *     Used in a BankAccount scenario — thrown when withdrawal exceeds available balance.
 *   - Custom UNCHECKED exception: InvalidAgeException (extends RuntimeException)
 *     Used in a VotingSystem scenario — thrown when age does not meet voting requirements.
 *   - Exception chaining: wrapping a root-cause exception inside a higher-level one
 *     to preserve the full error context for debugging.
 *   
 *   The program illustrates real-world patterns:
 *   - When to use checked vs. unchecked custom exceptions
 *   - How to add custom fields (errorCode, attemptedAmount) to exception classes
 *   - How to chain exceptions using the Throwable cause parameter
 *   - How to traverse an exception chain programmatically
 * 
 * Sample Input: (No user input required)
 * 
 * Sample Output:
 *   === Scenario 1: BankAccount — Checked Exception ===
 *   Created account: ACC-1001, Balance: $5000.00
 *   Withdrew $1500.00. New balance: $3500.00
 *   Withdrew $2000.00. New balance: $1500.00
 *   INSUFFICIENT BALANCE!
 *     Message: Cannot withdraw $3000.00 — available balance is $1500.00
 *     Error Code: ERR_INSUFFICIENT_FUNDS
 *     Attempted: $3000.00, Available: $1500.00
 *   Deposit of $500.00. New balance: $2000.00
 *   ...
 *   === Scenario 2: VotingSystem — Unchecked Exception ===
 *   Alice (age 25): Eligible to vote. Voter ID: VOT-1001
 *   Bob (age 16): INVALID AGE! Age 16 is below minimum voting age of 18
 *   ...
 *   === Scenario 3: Exception Chaining ===
 *   Top-level: Transaction failed for account ACC-2001
 *     Caused by: Cannot withdraw $10000.00 — available balance is $1000.00
 *   Full chain:
 *     -> TransactionException: Transaction failed for account ACC-2001
 *     -> InsufficientBalanceException: Cannot withdraw $10000.00 ...
 * 
 * Explanation:
 *   CHECKED exceptions (extend Exception):
 *     - Must be declared with 'throws' or caught by the caller.
 *     - Use for recoverable conditions (e.g., insufficient funds — the user can deposit more).
 *     - The compiler enforces handling, making the API explicit about failure modes.
 *   
 *   UNCHECKED exceptions (extend RuntimeException):
 *     - Do NOT need to be declared or caught (but can be).
 *     - Use for programming errors or invalid input (e.g., negative age).
 *     - Avoids cluttering method signatures for conditions that indicate bugs.
 *   
 *   EXCEPTION CHAINING:
 *     - Pass the original exception as the 'cause' to the wrapper exception's constructor.
 *     - Callers can traverse the chain with getCause() to find the root cause.
 *     - Critical for debugging: preserves the full story of what went wrong.
 * 
 * Time Complexity: O(1) — fixed number of operations per demo
 * Space Complexity: O(1) — only a few objects are created
 */

public class CustomExceptionDemo {

    // ==================== Main Method ====================

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         CUSTOM EXCEPTION DEMO                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        demoBankAccount();
        demoVotingSystem();
        demoExceptionChaining();
        demoExceptionChainTraversal();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("  1. Checked exceptions → extend Exception → must be caught/declared.");
        System.out.println("  2. Unchecked exceptions → extend RuntimeException → optional handling.");
        System.out.println("  3. Add custom fields (errorCode, amounts) to provide rich error info.");
        System.out.println("  4. Exception chaining preserves the root cause for debugging.");
        System.out.println("  5. Use checked for recoverable conditions, unchecked for bugs/invalid input.");
        System.out.println("═══════════════════════════════════════════════════");
    }

    // ==================== Scenario 1: BankAccount ====================

    public static void demoBankAccount() {
        System.out.println("=== Scenario 1: BankAccount — Checked Exception ===\n");

        BankAccount account = new BankAccount("ACC-1001", "Alice Johnson", 5000.00);
        System.out.println("Created account: " + account.getAccountId()
                + ", Balance: " + String.format("$%.2f", account.getBalance()));

        // Successful withdrawals
        try {
            account.withdraw(1500.00);
            System.out.println("Withdrew $1500.00. New balance: "
                    + String.format("$%.2f", account.getBalance()));

            account.withdraw(2000.00);
            System.out.println("Withdrew $2000.00. New balance: "
                    + String.format("$%.2f", account.getBalance()));

            // This should fail — insufficient balance
            account.withdraw(3000.00);
            System.out.println("This line should not be reached.");

        } catch (InsufficientBalanceException e) {
            System.out.println("INSUFFICIENT BALANCE!");
            System.out.println("  Message: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Attempted: " + String.format("$%.2f", e.getAttemptedAmount())
                    + ", Available: " + String.format("$%.2f", e.getAvailableBalance()));
        }

        // Account still usable after a caught exception
        account.deposit(500.00);
        System.out.println("Deposit of $500.00. New balance: "
                + String.format("$%.2f", account.getBalance()));

        // Try withdrawing zero or negative
        try {
            account.withdraw(-100);
        } catch (InsufficientBalanceException e) {
            System.out.println("Caught: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
        }

        System.out.println();
    }

    // ==================== Scenario 2: VotingSystem ====================

    public static void demoVotingSystem() {
        System.out.println("=== Scenario 2: VotingSystem — Unchecked Exception ===\n");

        VotingSystem votingSystem = new VotingSystem(18); // Minimum voting age = 18

        // Test various ages
        String[][] voters = {
                {"Alice", "25"},
                {"Bob", "16"},
                {"Charlie", "18"},
                {"Diana", "-3"},
                {"Eve", "17"},
                {"Frank", "65"}
        };

        for (String[] voter : voters) {
            String name = voter[0];
            int age = Integer.parseInt(voter[1]);
            try {
                String voterId = votingSystem.registerVoter(name, age);
                System.out.println(name + " (age " + age + "): Eligible to vote. Voter ID: " + voterId);
            } catch (InvalidAgeException e) {
                System.out.println(name + " (age " + age + "): INVALID AGE! " + e.getMessage());
                // Access custom fields
                if (e.getProvidedAge() < 0) {
                    System.out.println("  (Negative age suggests a data entry error)");
                }
            }
        }

        System.out.println("\nTotal registered voters: " + votingSystem.getRegisteredCount());
        System.out.println();
    }

    // ==================== Scenario 3: Exception Chaining ====================

    public static void demoExceptionChaining() {
        System.out.println("=== Scenario 3: Exception Chaining ===\n");

        TransactionService service = new TransactionService();

        // Scenario A: Transaction that triggers insufficient balance
        try {
            service.processTransaction("ACC-2001", 10000.00);
        } catch (TransactionException e) {
            System.out.println("Top-level: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("  Caused by: " + e.getCause().getMessage());
            }
        }

        // Scenario B: Transaction with invalid account
        try {
            service.processTransaction(null, 500.00);
        } catch (TransactionException e) {
            System.out.println("Top-level: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("  Caused by: " + e.getCause().getClass().getSimpleName()
                        + ": " + e.getCause().getMessage());
            }
        }

        System.out.println();
    }

    // ==================== Scenario 4: Exception Chain Traversal ====================

    public static void demoExceptionChainTraversal() {
        System.out.println("=== Scenario 4: Exception Chain Traversal ===\n");

        try {
            // Create a deep exception chain
            simulateDeepFailure();
        } catch (Exception e) {
            System.out.println("Full chain:");
            Throwable current = e;
            int depth = 0;
            while (current != null) {
                String indent = "  " + "  ".repeat(depth);
                System.out.println(indent + "-> " + current.getClass().getSimpleName()
                        + ": " + current.getMessage());
                current = current.getCause();
                depth++;
            }

            // Find the root cause
            Throwable rootCause = e;
            while (rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }
            System.out.println("\nRoot cause: " + rootCause.getClass().getSimpleName()
                    + ": " + rootCause.getMessage());
        }

        System.out.println();
    }

    /**
     * Simulates a deep failure chain:
     * ApplicationException -> ServiceException -> InsufficientBalanceException
     */
    private static void simulateDeepFailure() {
        try {
            simulateServiceCall();
        } catch (Exception e) {
            throw new RuntimeException("Application layer: request processing failed", e);
        }
    }

    private static void simulateServiceCall() {
        try {
            simulateRepositoryAccess();
        } catch (InsufficientBalanceException e) {
            throw new RuntimeException("Service layer: business rule violated", e);
        }
    }

    private static void simulateRepositoryAccess() throws InsufficientBalanceException {
        throw new InsufficientBalanceException(
                "Cannot withdraw $5000.00 — available balance is $200.00",
                "ERR_INSUFFICIENT_FUNDS",
                5000.00,
                200.00
        );
    }
}

// ==================== Custom Checked Exception ====================

/**
 * InsufficientBalanceException — a CHECKED exception.
 * Extends Exception, so the compiler forces callers to handle it.
 * 
 * Use case: Bank account operations where a withdrawal exceeds the balance.
 * This is a recoverable condition — the user can deposit more money.
 */
class InsufficientBalanceException extends Exception {

    // serialVersionUID for serialization compatibility
    private static final long serialVersionUID = 1L;

    // Custom fields to provide rich error information
    private final String errorCode;
    private final double attemptedAmount;
    private final double availableBalance;

    /**
     * Constructor with all custom fields.
     */
    public InsufficientBalanceException(String message, String errorCode,
                                         double attemptedAmount, double availableBalance) {
        super(message); // Pass message to Exception's constructor
        this.errorCode = errorCode;
        this.attemptedAmount = attemptedAmount;
        this.availableBalance = availableBalance;
    }

    /**
     * Constructor supporting exception chaining.
     */
    public InsufficientBalanceException(String message, String errorCode,
                                         double attemptedAmount, double availableBalance,
                                         Throwable cause) {
        super(message, cause); // Pass both message and cause
        this.errorCode = errorCode;
        this.attemptedAmount = attemptedAmount;
        this.availableBalance = availableBalance;
    }

    // Getters for custom fields
    public String getErrorCode() { return errorCode; }
    public double getAttemptedAmount() { return attemptedAmount; }
    public double getAvailableBalance() { return availableBalance; }
}

// ==================== Custom Unchecked Exception ====================

/**
 * InvalidAgeException — an UNCHECKED exception.
 * Extends RuntimeException, so callers are NOT forced to catch it.
 * 
 * Use case: Voting system where an invalid age indicates a programming error
 * or bad user input that should be validated upstream.
 */
class InvalidAgeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int providedAge;
    private final int minimumAge;

    public InvalidAgeException(String message, int providedAge, int minimumAge) {
        super(message);
        this.providedAge = providedAge;
        this.minimumAge = minimumAge;
    }

    public InvalidAgeException(String message, int providedAge, int minimumAge, Throwable cause) {
        super(message, cause);
        this.providedAge = providedAge;
        this.minimumAge = minimumAge;
    }

    public int getProvidedAge() { return providedAge; }
    public int getMinimumAge() { return minimumAge; }
}

// ==================== BankAccount Class ====================

/**
 * A simple bank account that uses InsufficientBalanceException.
 */
class BankAccount {
    private final String accountId;
    private final String holderName;
    private double balance;

    public BankAccount(String accountId, String holderName, double initialBalance) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    /**
     * Withdraw money. Throws InsufficientBalanceException (checked) if balance is too low.
     * Throws IllegalArgumentException (unchecked) for invalid amounts.
     */
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive: " + amount);
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(
                    String.format("Cannot withdraw $%.2f — available balance is $%.2f", amount, balance),
                    "ERR_INSUFFICIENT_FUNDS",
                    amount,
                    balance
            );
        }
        balance -= amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive: " + amount);
        }
        balance += amount;
    }

    public String getAccountId() { return accountId; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
}

// ==================== VotingSystem Class ====================

/**
 * A voting registration system that uses InvalidAgeException.
 */
class VotingSystem {
    private final int minimumVotingAge;
    private int registeredCount = 0;

    public VotingSystem(int minimumVotingAge) {
        this.minimumVotingAge = minimumVotingAge;
    }

    /**
     * Register a voter. Throws InvalidAgeException (unchecked) for invalid ages.
     */
    public String registerVoter(String name, int age) {
        if (age < 0) {
            throw new InvalidAgeException(
                    "Age " + age + " is invalid — age cannot be negative",
                    age, minimumVotingAge
            );
        }
        if (age < minimumVotingAge) {
            throw new InvalidAgeException(
                    "Age " + age + " is below minimum voting age of " + minimumVotingAge,
                    age, minimumVotingAge
            );
        }
        registeredCount++;
        return "VOT-" + (1000 + registeredCount);
    }

    public int getRegisteredCount() { return registeredCount; }
}

// ==================== Transaction Service (Exception Chaining) ====================

/**
 * Custom exception for transaction failures — demonstrates exception chaining.
 */
class TransactionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause); // The 'cause' is the chained exception
    }
}

/**
 * Service that wraps lower-level exceptions into TransactionException.
 */
class TransactionService {

    /**
     * Process a transaction, chaining any lower-level exception.
     */
    public void processTransaction(String accountId, double amount) {
        try {
            // Validate input
            if (accountId == null || accountId.isEmpty()) {
                throw new IllegalArgumentException("Account ID cannot be null or empty");
            }

            // Simulate account with limited balance
            BankAccount account = new BankAccount(accountId, "Demo User", 1000.00);
            account.withdraw(amount);

        } catch (InsufficientBalanceException e) {
            // Chain: wrap the checked exception inside our unchecked TransactionException
            throw new TransactionException(
                    "Transaction failed for account " + accountId, e);

        } catch (IllegalArgumentException e) {
            // Chain: wrap the unchecked validation error
            throw new TransactionException(
                    "Transaction failed: invalid input", e);
        }
    }
}
