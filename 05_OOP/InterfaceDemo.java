/*
 * Program 66: Interface Demo
 * 
 * Problem Statement:
 *   Create multiple interfaces (Printable, Showable) with default and static
 *   methods. Demonstrate multiple interface implementation, functional interfaces,
 *   interface inheritance, and the diamond problem resolution.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Basic Interface Implementation ===
 *   Printing document: Annual Report (10 pages)
 *   Showing document: Annual Report on screen
 *   === Default Methods ===
 *   [LOG] Processing: Annual Report
 *   === Functional Interfaces ===
 *   5 + 3 = 8
 *   5 * 3 = 15
 *   ...
 * 
 * Explanation:
 *   Interfaces define contracts that classes must fulfill. Modern Java interfaces
 *   support:
 *   - Abstract methods: must be implemented by classes
 *   - Default methods (Java 8+): provide default implementation
 *   - Static methods (Java 8+): utility methods on the interface itself
 *   - Private methods (Java 9+): helper methods for default methods
 *   - Functional interfaces: exactly one abstract method (for lambdas)
 *   - Interface inheritance: interfaces can extend other interfaces
 *   - Multiple implementation: a class can implement many interfaces
 * 
 * Time Complexity: O(1) for each interface method call
 * Space Complexity: O(1) for each object
 */

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

// =================== BASIC INTERFACES ===================

// Interface 1: Printable
interface Printable {
    // Abstract method - must be implemented
    void print();

    // Default method (Java 8+) - can be overridden
    default void printInColor(String color) {
        System.out.println("  Printing in " + color + ":");
        print(); // calls the abstract method
    }

    // Static method (Java 8+) - called via interface name
    static void printLine() {
        System.out.println("  ─────────────────────────────────────");
    }

    // Another static utility
    static String formatHeader(String title) {
        return "═══ " + title.toUpperCase() + " ═══";
    }
}

// Interface 2: Showable
interface Showable {
    // Abstract method
    void show();

    // Default method - note: same name as Printable would create diamond problem
    default void showPreview() {
        System.out.println("  [Preview Mode]");
        show();
    }

    static void clearScreen() {
        System.out.println("  [Screen cleared]");
    }
}

// Interface 3: Loggable - with default method
interface Loggable {
    // Default method (no abstract methods - marker-like with behavior)
    default void log(String message) {
        System.out.println("  [LOG " + getTimestamp() + "] " + message);
    }

    // Private helper method (Java 9+ concept, simulated here)
    // In Java 9+, this would be: private String getTimestamp()
    // For Java 8 compatibility, we use a default method
    default String getTimestamp() {
        return java.time.LocalTime.now().toString().substring(0, 8);
    }
}

// =================== INTERFACE INHERITANCE ===================

// Interface extending another interface
interface Exportable extends Printable {
    // Additional abstract method
    void exportToFile(String filename);

    // Can add new default methods
    default void exportToPDF(String filename) {
        System.out.println("  Exporting to PDF: " + filename);
        print(); // inherited from Printable
    }
}

// =================== DIAMOND PROBLEM RESOLUTION ===================

// Two interfaces with same default method name
interface InterfaceA {
    default void greet() {
        System.out.println("  Hello from Interface A!");
    }

    default void commonMethod() {
        System.out.println("  Common method from A");
    }
}

interface InterfaceB {
    default void greet() {
        System.out.println("  Hello from Interface B!");
    }

    default void commonMethod() {
        System.out.println("  Common method from B");
    }
}

// Class implementing both MUST resolve the conflict
class DiamondResolver implements InterfaceA, InterfaceB {
    @Override
    public void greet() {
        // Must explicitly choose or provide own implementation
        System.out.println("  Hello from DiamondResolver!");
        // Can still call specific interface versions:
        InterfaceA.super.greet();
        InterfaceB.super.greet();
    }

    @Override
    public void commonMethod() {
        // Choose InterfaceA's version
        InterfaceA.super.commonMethod();
    }
}

// =================== FUNCTIONAL INTERFACES ===================

// Functional interface (exactly one abstract method)
@FunctionalInterface
interface MathOperation {
    double operate(double a, double b);
    // Can have default and static methods
    // double anotherMethod(); // ERROR: would make it non-functional
}

@FunctionalInterface
interface StringProcessor {
    String process(String input);
}

@FunctionalInterface
interface Validator<T> {
    boolean validate(T item);
}

// =================== CONCRETE IMPLEMENTING CLASS ===================

// Document class implementing multiple interfaces
class Document implements Printable, Showable, Loggable, Exportable {
    private String title;
    private int pages;
    private String content;

    public Document(String title, int pages, String content) {
        this.title = title;
        this.pages = pages;
        this.content = content;
    }

    // Implementing Printable
    @Override
    public void print() {
        System.out.println("  Printing document: " + title + " (" + pages + " pages)");
    }

    // Implementing Showable
    @Override
    public void show() {
        System.out.println("  Showing document: " + title + " on screen");
        System.out.println("  Content: " + content);
    }

    // Implementing Exportable
    @Override
    public void exportToFile(String filename) {
        System.out.println("  Exporting '" + title + "' to file: " + filename);
    }

    // No need to implement Loggable.log() - using default implementation

    @Override
    public String toString() {
        return "Document{title='" + title + "', pages=" + pages + "}";
    }
}

// Another implementing class
class Image implements Printable, Showable {
    private String name;
    private int width, height;

    public Image(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    @Override
    public void print() {
        System.out.println("  Printing image: " + name + " (" + width + "x" + height + ")");
    }

    @Override
    public void show() {
        System.out.println("  Displaying image: " + name + " at " + width + "x" + height + " resolution");
    }

    // Override default method from Printable
    @Override
    public void printInColor(String color) {
        System.out.println("  Printing image " + name + " with " + color + " tint filter");
    }
}

public class InterfaceDemo {

    // Method accepting interface types (polymorphism)
    public static void printAnything(Printable item) {
        item.print();
    }

    public static void showAnything(Showable item) {
        item.show();
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 66: Interface Demo                ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // 1. Basic Interface Implementation
        System.out.println("=== Basic Interface Implementation ===");
        Document doc = new Document("Annual Report", 10, "Financial data for 2024...");
        Image img = new Image("Logo.png", 1920, 1080);

        doc.print();      // Printable method
        doc.show();       // Showable method
        img.print();
        img.show();

        // 2. Default Methods
        System.out.println("\n=== Default Methods ===");
        doc.printInColor("Blue");     // Uses default from Printable
        img.printInColor("Sepia");    // Uses overridden version
        doc.showPreview();            // Uses default from Showable
        doc.log("Processing document: " + doc); // Uses default from Loggable

        // 3. Static Methods on Interfaces
        System.out.println("\n=== Static Methods ===");
        Printable.printLine();
        System.out.println("  " + Printable.formatHeader("Important Notice"));
        Printable.printLine();
        Showable.clearScreen();

        // 4. Interface as Reference Type
        System.out.println("\n=== Interface as Reference Type ===");
        Printable p = doc;    // Interface reference
        p.print();            // OK
        // p.show();          // ERROR: Printable doesn't know about show()
        // p.log("test");     // ERROR: Printable doesn't know about log()

        Showable s = doc;     // Different interface reference to same object
        s.show();

        // 5. Polymorphism with Interfaces
        System.out.println("\n=== Polymorphism via Interfaces ===");
        Printable[] printables = { doc, img };
        for (Printable item : printables) {
            printAnything(item); // Each calls its own print()
        }

        Showable[] showables = { doc, img };
        for (Showable item : showables) {
            showAnything(item);
        }

        // 6. Interface Inheritance
        System.out.println("\n=== Interface Inheritance (Exportable extends Printable) ===");
        Exportable exportable = doc;
        exportable.print();                     // Inherited from Printable
        exportable.exportToFile("report.txt");  // Exportable's own method
        exportable.exportToPDF("report.pdf");   // Exportable's default method
        exportable.printInColor("Green");       // Inherited default from Printable

        // 7. Diamond Problem Resolution
        System.out.println("\n=== Diamond Problem Resolution ===");
        DiamondResolver resolver = new DiamondResolver();
        resolver.greet();         // Resolves ambiguity explicitly
        resolver.commonMethod();  // Chooses InterfaceA's version

        // 8. Functional Interfaces with Lambdas
        System.out.println("\n=== Functional Interfaces ===");

        // Lambda implementations of MathOperation
        MathOperation add = (a, b) -> a + b;
        MathOperation subtract = (a, b) -> a - b;
        MathOperation multiply = (a, b) -> a * b;
        MathOperation divide = (a, b) -> b != 0 ? a / b : Double.NaN;
        MathOperation power = Math::pow;  // Method reference

        double x = 10, y = 3;
        System.out.printf("  %s + %s = %s%n", x, y, add.operate(x, y));
        System.out.printf("  %s - %s = %s%n", x, y, subtract.operate(x, y));
        System.out.printf("  %s * %s = %s%n", x, y, multiply.operate(x, y));
        System.out.printf("  %s / %s = %.4f%n", x, y, divide.operate(x, y));
        System.out.printf("  %s ^ %s = %s%n", x, y, power.operate(x, y));

        // 9. StringProcessor Functional Interface
        System.out.println("\n=== String Processing with Lambdas ===");
        StringProcessor toUpper = String::toUpperCase;
        StringProcessor reverse = str -> new StringBuilder(str).reverse().toString();
        StringProcessor addBraces = str -> "[" + str + "]";
        StringProcessor trimAndUpper = str -> str.trim().toUpperCase();

        String test = "  Hello World  ";
        System.out.println("  Original:    '" + test + "'");
        System.out.println("  toUpper:     '" + toUpper.process(test) + "'");
        System.out.println("  reverse:     '" + reverse.process(test) + "'");
        System.out.println("  addBraces:   '" + addBraces.process(test) + "'");
        System.out.println("  trimAndUpper:'" + trimAndUpper.process(test) + "'");

        // 10. Custom Validator Functional Interface
        System.out.println("\n=== Custom Validator<T> Functional Interface ===");
        Validator<String> notEmpty = str -> str != null && !str.isEmpty();
        Validator<Integer> isPositive = n -> n > 0;
        Validator<String> isEmail = str -> str != null && str.contains("@") && str.contains(".");

        System.out.println("  notEmpty(\"\"):      " + notEmpty.validate(""));
        System.out.println("  notEmpty(\"hello\"): " + notEmpty.validate("hello"));
        System.out.println("  isPositive(-5):    " + isPositive.validate(-5));
        System.out.println("  isPositive(10):    " + isPositive.validate(10));
        System.out.println("  isEmail(\"a@b.c\"): " + isEmail.validate("a@b.c"));
        System.out.println("  isEmail(\"abc\"):   " + isEmail.validate("abc"));

        // 11. Built-in Functional Interfaces from java.util.function
        System.out.println("\n=== Built-in Functional Interfaces (java.util.function) ===");

        // Predicate<T>: T → boolean
        Predicate<Integer> isEven = n -> n % 2 == 0;
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.print("  Even numbers: ");
        numbers.stream().filter(isEven).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // Function<T,R>: T → R
        Function<String, Integer> strLength = String::length;
        System.out.println("  Length of 'Hello': " + strLength.apply("Hello"));

        // Consumer<T>: T → void
        Consumer<String> printer = str -> System.out.println("  > " + str);
        printer.accept("This is printed by a Consumer");

        // 12. instanceof with Interfaces
        System.out.println("\n=== instanceof with Interfaces ===");
        Object[] objects = { doc, img, "String", 42, resolver };
        for (Object obj : objects) {
            System.out.printf("  %-30s Printable=%-5b Showable=%-5b Loggable=%b%n",
                              obj.getClass().getSimpleName(),
                              obj instanceof Printable,
                              obj instanceof Showable,
                              obj instanceof Loggable);
        }

        // Summary
        System.out.println("\n=== Interface Feature Summary ===");
        System.out.println("  ┌─────────────────────────────────────────────────┐");
        System.out.println("  │ Feature                │ Since    │ Notes       │");
        System.out.println("  ├─────────────────────────────────────────────────┤");
        System.out.println("  │ Abstract methods       │ Java 1.0 │ Must impl   │");
        System.out.println("  │ Constants              │ Java 1.0 │ public      │");
        System.out.println("  │ Default methods        │ Java 8   │ Can override│");
        System.out.println("  │ Static methods         │ Java 8   │ Not inherit │");
        System.out.println("  │ Functional interfaces  │ Java 8   │ 1 abstract  │");
        System.out.println("  │ Private methods        │ Java 9   │ Helpers     │");
        System.out.println("  └─────────────────────────────────────────────────┘");
    }
}
