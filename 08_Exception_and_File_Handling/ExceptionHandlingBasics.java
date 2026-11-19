/*
 * Program 96: Exception Handling Basics
 * 
 * Problem Statement:
 *   Demonstrate all core exception handling mechanisms in Java:
 *   - try-catch-finally blocks for structured error handling
 *   - Multiple catch blocks to handle different exception types
 *   - try-with-resources for automatic resource management (AutoCloseable)
 *   - throw keyword to explicitly raise exceptions
 *   - throws keyword to declare exceptions in method signatures
 *   - Handling common exceptions: ArithmeticException, ArrayIndexOutOfBoundsException,
 *     NullPointerException, and NumberFormatException
 * 
 * Sample Input: (No user input required - all scenarios are demonstrated programmatically)
 * 
 * Sample Output:
 *   === Demo 1: Basic try-catch-finally ===
 *   Attempting division: 10 / 0
 *   Caught ArithmeticException: / by zero
 *   Finally block always executes (cleanup goes here).
 *   
 *   === Demo 2: Multiple Catch Blocks ===
 *   --- Scenario A: ArithmeticException ---
 *   Caught ArithmeticException: / by zero
 *   ...
 *   === Demo 7: Combining throw and throws ===
 *   Caught IOException: File not found: ghost.txt
 *   Suppressed: java.lang.RuntimeException: Error during cleanup
 * 
 * Explanation:
 *   1. try-catch-finally: The 'try' block contains code that might throw exceptions.
 *      The 'catch' block handles specific exception types. The 'finally' block always
 *      executes regardless of whether an exception occurred — ideal for cleanup.
 *   2. Multiple catch blocks: Java checks catch blocks top to bottom. More specific
 *      exceptions must come before more general ones (e.g., ArithmeticException before
 *      Exception) to avoid compilation errors.
 *   3. Multi-catch (Java 7+): A single catch block can handle multiple unrelated
 *      exception types using the pipe (|) operator.
 *   4. try-with-resources (Java 7+): Resources that implement AutoCloseable are
 *      automatically closed at the end of the try block, even if an exception occurs.
 *      This eliminates boilerplate finally blocks for resource management.
 *   5. throw: Used inside a method body to explicitly throw an exception object.
 *   6. throws: Used in a method declaration to indicate that the method may propagate
 *      a checked exception to its caller.
 *   7. Common runtime exceptions are demonstrated with realistic scenarios.
 * 
 * Time Complexity: O(1) — each demo runs a fixed number of operations
 * Space Complexity: O(1) — only a few local variables are used
 */

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class ExceptionHandlingBasics {

    // ==================== Demo 1: Basic try-catch-finally ====================

    /**
     * Shows the fundamental try-catch-finally structure.
     * finally always runs — whether the try succeeds or an exception is caught.
     */
    public static void demoBasicTryCatchFinally() {
        System.out.println("=== Demo 1: Basic try-catch-finally ===");
        int numerator = 10;
        int denominator = 0;

        System.out.println("Attempting division: " + numerator + " / " + denominator);
        try {
            int result = numerator / denominator; // ArithmeticException
            System.out.println("Result: " + result); // Never reached
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } finally {
            System.out.println("Finally block always executes (cleanup goes here).");
        }

        // Demonstrate finally running even when NO exception occurs
        System.out.println("\nAttempting division: 10 / 2");
        try {
            int result = 10 / 2;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } finally {
            System.out.println("Finally block still executes when no exception occurs.");
        }
        System.out.println();
    }

    // ==================== Demo 2: Multiple Catch Blocks ====================

    /**
     * Multiple catch blocks handle different exception types.
     * Order matters: specific exceptions first, general ones last.
     */
    public static void demoMultipleCatchBlocks() {
        System.out.println("=== Demo 2: Multiple Catch Blocks ===");

        // Scenario A: ArithmeticException
        System.out.println("--- Scenario A: ArithmeticException ---");
        try {
            int[] numbers = {10, 20, 30};
            int result = numbers[0] / 0;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        } catch (Exception e) {
            // General catch — a safety net for any other exception
            System.out.println("Caught general Exception: " + e.getMessage());
        }

        // Scenario B: ArrayIndexOutOfBoundsException
        System.out.println("--- Scenario B: ArrayIndexOutOfBoundsException ---");
        try {
            int[] numbers = {10, 20, 30};
            System.out.println("Accessing index 5: " + numbers[5]);
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught general Exception: " + e.getMessage());
        }

        // Scenario C: NullPointerException
        System.out.println("--- Scenario C: NullPointerException ---");
        try {
            String text = null;
            System.out.println("Length: " + text.length()); // NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught general Exception: " + e.getMessage());
        }

        // Scenario D: NumberFormatException
        System.out.println("--- Scenario D: NumberFormatException ---");
        try {
            String invalidNumber = "abc123";
            int parsed = Integer.parseInt(invalidNumber);
            System.out.println("Parsed: " + parsed);
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught general Exception: " + e.getMessage());
        }
        System.out.println();
    }

    // ==================== Demo 3: Multi-Catch (Java 7+) ====================

    /**
     * A single catch block handles multiple exception types with the | operator.
     * The exception variable is implicitly final in a multi-catch.
     */
    public static void demoMultiCatch() {
        System.out.println("=== Demo 3: Multi-Catch (Java 7+) ===");

        Object[] testInputs = {"hello", null, "42", ""};
        for (Object input : testInputs) {
            try {
                String str = (String) input;
                // Trigger NullPointerException if null, NumberFormatException if not a number
                int value = Integer.parseInt(str);
                int result = 100 / value; // ArithmeticException if value == 0
                System.out.println("  Input '" + input + "' -> result = " + result);
            } catch (NullPointerException | NumberFormatException | ArithmeticException e) {
                // Single block handles all three unrelated exception types
                System.out.println("  Input '" + input + "' -> Caught "
                        + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        System.out.println();
    }

    // ==================== Demo 4: try-with-resources ====================

    /**
     * try-with-resources automatically closes resources that implement AutoCloseable.
     * This is the preferred pattern since Java 7 for managing I/O resources.
     */
    public static void demoTryWithResources() {
        System.out.println("=== Demo 4: try-with-resources (AutoCloseable) ===");

        // Using a StringReader (which implements AutoCloseable) as a safe demo
        String data = "Line 1\nLine 2\nLine 3";
        System.out.println("Reading from StringReader using try-with-resources:");

        try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("  Read: " + line);
            }
            // reader.close() is called automatically here, even if an exception occurs
        } catch (IOException e) {
            System.out.println("  Caught IOException: " + e.getMessage());
        }
        System.out.println("  BufferedReader was automatically closed.");

        // Custom AutoCloseable to illustrate the closing mechanism
        System.out.println("\nUsing a custom AutoCloseable resource:");
        try (DatabaseConnectionSimulator db = new DatabaseConnectionSimulator("TestDB")) {
            db.executeQuery("SELECT * FROM users");
            db.executeQuery("SELECT * FROM orders");
        } catch (Exception e) {
            System.out.println("  Caught: " + e.getMessage());
        }

        // Multiple resources in a single try-with-resources
        System.out.println("\nMultiple resources in try-with-resources:");
        try (DatabaseConnectionSimulator db1 = new DatabaseConnectionSimulator("DB_Primary");
             DatabaseConnectionSimulator db2 = new DatabaseConnectionSimulator("DB_Replica")) {
            db1.executeQuery("INSERT INTO logs VALUES ('event1')");
            db2.executeQuery("SELECT * FROM logs");
        } catch (Exception e) {
            System.out.println("  Caught: " + e.getMessage());
        }
        // Note: Resources are closed in reverse order (db2 first, then db1)

        System.out.println();
    }

    // ==================== Demo 5: throw Keyword ====================

    /**
     * The 'throw' keyword is used to explicitly throw an exception from code.
     * It can throw any object that is a subclass of Throwable.
     */
    public static void demoThrowKeyword() {
        System.out.println("=== Demo 5: throw Keyword ===");

        // Example: Validating input and throwing exceptions
        String[] testAges = {"25", "-5", "abc", "150", "0"};
        for (String ageStr : testAges) {
            try {
                int age = validateAndParseAge(ageStr);
                System.out.println("  Age '" + ageStr + "' is valid: " + age);
            } catch (IllegalArgumentException e) {
                System.out.println("  Age '" + ageStr + "' is invalid: " + e.getMessage());
            }
        }
        System.out.println();
    }

    /**
     * Uses 'throw' to signal invalid age values.
     */
    private static int validateAndParseAge(String ageStr) {
        if (ageStr == null || ageStr.isEmpty()) {
            throw new IllegalArgumentException("Age string cannot be null or empty");
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            // Re-throw as a more descriptive exception
            throw new IllegalArgumentException("Age must be a number, got: '" + ageStr + "'");
        }

        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (age > 130) {
            throw new IllegalArgumentException("Age is unrealistically high: " + age);
        }
        return age;
    }

    // ==================== Demo 6: throws Keyword ====================

    /**
     * The 'throws' keyword declares that a method might throw a checked exception.
     * Callers MUST either catch the exception or declare it with throws as well.
     */
    public static void demoThrowsKeyword() {
        System.out.println("=== Demo 6: throws Keyword ===");

        // Calling a method that declares 'throws IOException'
        try {
            String content = readConfigFile("app_config.properties");
            System.out.println("  Config content: " + content);
        } catch (IOException e) {
            System.out.println("  Caught IOException: " + e.getMessage());
            System.out.println("  (This is expected — we're demonstrating 'throws')");
        }

        // Demonstrating throws propagation
        try {
            processData();
        } catch (Exception e) {
            System.out.println("  Caught propagated exception: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * This method declares 'throws IOException', forcing callers to handle it.
     * This is required because IOException is a CHECKED exception.
     */
    private static String readConfigFile(String filename) throws IOException {
        // Attempting to read a file that likely doesn't exist — to trigger IOException
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.readLine();
        }
    }

    /**
     * Demonstrates how 'throws' propagates up the call chain.
     */
    private static void processData() throws Exception {
        validateData(); // This can throw Exception, so we propagate it
    }

    private static void validateData() throws Exception {
        throw new Exception("Data validation failed: missing required field 'name'");
    }

    // ==================== Demo 7: Combining throw, throws, and suppressed exceptions ====================

    public static void demoCombinedScenario() {
        System.out.println("=== Demo 7: Combining throw and throws ===");

        try {
            performFileOperation("ghost.txt");
        } catch (IOException e) {
            System.out.println("  Caught IOException: " + e.getMessage());
            // Check for suppressed exceptions (from try-with-resources)
            Throwable[] suppressed = e.getSuppressed();
            for (Throwable t : suppressed) {
                System.out.println("  Suppressed: " + t);
            }
        }

        System.out.println();
    }

    /**
     * Demonstrates exception suppression in try-with-resources.
     * When both the try block and close() throw, the close() exception
     * is suppressed (attached to the primary exception).
     */
    private static void performFileOperation(String filename) throws IOException {
        try (FaultyResource resource = new FaultyResource()) {
            // This throws IOException
            throw new IOException("File not found: " + filename);
            // When FaultyResource.close() also throws, its exception is suppressed
        }
    }

    // ==================== Demo 8: Practical Exception Handling Patterns ====================

    public static void demoPracticalPatterns() {
        System.out.println("=== Demo 8: Practical Exception Handling Patterns ===");

        // Pattern 1: Return default on failure
        System.out.println("--- Pattern: Return default on failure ---");
        System.out.println("  parseIntSafe(\"42\") = " + parseIntSafe("42", -1));
        System.out.println("  parseIntSafe(\"abc\") = " + parseIntSafe("abc", -1));
        System.out.println("  parseIntSafe(null) = " + parseIntSafe(null, -1));

        // Pattern 2: Rethrow with context
        System.out.println("--- Pattern: Rethrow with context ---");
        try {
            processUserInput("not_a_number");
        } catch (RuntimeException e) {
            System.out.println("  Caught: " + e.getMessage());
            System.out.println("  Caused by: " + e.getCause());
        }

        // Pattern 3: Exception chaining
        System.out.println("--- Pattern: Exception chaining ---");
        try {
            connectToService("unreachable-host", 8080);
        } catch (RuntimeException e) {
            System.out.println("  Top-level: " + e.getMessage());
            Throwable cause = e.getCause();
            while (cause != null) {
                System.out.println("  Caused by: " + cause.getClass().getSimpleName()
                        + ": " + cause.getMessage());
                cause = cause.getCause();
            }
        }

        System.out.println();
    }

    private static int parseIntSafe(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    private static void processUserInput(String input) {
        try {
            int value = Integer.parseInt(input);
            System.out.println("  Processed value: " + value);
        } catch (NumberFormatException e) {
            // Rethrow with added context, preserving the original cause
            throw new RuntimeException(
                    "Failed to process user input: '" + input + "'", e);
        }
    }

    private static void connectToService(String host, int port) {
        try {
            // Simulate a low-level failure
            throw new java.net.ConnectException("Connection refused");
        } catch (java.net.ConnectException e) {
            // Wrap in a mid-level exception
            RuntimeException mid = new RuntimeException(
                    "Cannot reach host " + host + ":" + port, e);
            // Wrap again in a high-level exception
            throw new RuntimeException("Service unavailable", mid);
        }
    }

    // ==================== Main Method ====================

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║      JAVA EXCEPTION HANDLING BASICS              ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        demoBasicTryCatchFinally();
        demoMultipleCatchBlocks();
        demoMultiCatch();
        demoTryWithResources();
        demoThrowKeyword();
        demoThrowsKeyword();
        demoCombinedScenario();
        demoPracticalPatterns();

        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("  1. try-catch-finally: Structure for handling exceptions.");
        System.out.println("  2. Multiple catch: Specific exceptions before general ones.");
        System.out.println("  3. Multi-catch (|): Handle unrelated exceptions in one block.");
        System.out.println("  4. try-with-resources: Auto-close resources (AutoCloseable).");
        System.out.println("  5. throw: Explicitly throw an exception from code.");
        System.out.println("  6. throws: Declare checked exceptions in method signature.");
        System.out.println("  7. Exception chaining: Wrap cause for debugging context.");
        System.out.println("═══════════════════════════════════════════════════");
    }
}

// ==================== Helper Classes (non-public, same file) ====================

/**
 * Simulates a database connection that implements AutoCloseable.
 * Demonstrates how try-with-resources auto-calls close().
 */
class DatabaseConnectionSimulator implements AutoCloseable {
    private final String dbName;
    private boolean open;

    public DatabaseConnectionSimulator(String dbName) {
        this.dbName = dbName;
        this.open = true;
        System.out.println("  [" + dbName + "] Connection opened.");
    }

    public void executeQuery(String sql) {
        if (!open) {
            throw new IllegalStateException("Connection is already closed!");
        }
        System.out.println("  [" + dbName + "] Executing: " + sql);
    }

    @Override
    public void close() {
        if (open) {
            open = false;
            System.out.println("  [" + dbName + "] Connection closed automatically.");
        }
    }
}

/**
 * A resource whose close() method throws an exception.
 * Used to demonstrate suppressed exceptions in try-with-resources.
 */
class FaultyResource implements AutoCloseable {
    @Override
    public void close() throws RuntimeException {
        throw new RuntimeException("Error during cleanup");
    }
}
