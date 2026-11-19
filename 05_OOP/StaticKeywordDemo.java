/*
 * Program 67: Static Keyword Demo
 * 
 * Problem Statement:
 *   Demonstrate static variables (counter), static methods (utility),
 *   static blocks (initialization), and static inner classes. Show the
 *   difference between static and instance members.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Static Variables ===
 *   Total employees created: 3
 *   Company: TechCorp (shared across all instances)
 *   === Static Methods ===
 *   MathUtils.add(5, 3) = 8
 *   MathUtils.factorial(5) = 120
 *   === Static Blocks ===
 *   Static block 1 executed (class loading)
 *   Static block 2 executed
 *   === Static Inner Class ===
 *   LinkedList.Node: data=10, next=20
 *   ...
 * 
 * Explanation:
 *   The 'static' keyword in Java belongs to the class rather than instances:
 *   
 *   1. Static Variables (Class Variables):
 *      - Shared among ALL instances of a class
 *      - Only one copy exists in memory (regardless of object count)
 *      - Use for: counters, constants, shared configuration
 *   
 *   2. Static Methods:
 *      - Can be called without creating an object
 *      - Cannot access instance variables or 'this' keyword
 *      - Use for: utility/helper methods, factory methods
 *   
 *   3. Static Blocks:
 *      - Execute once when the class is first loaded
 *      - Run before any constructor or static method
 *      - Use for: complex static initialization
 *   
 *   4. Static Inner Classes:
 *      - Nested class that doesn't need outer class instance
 *      - Can access outer class's static members only
 * 
 * Time Complexity: O(1) for most operations
 * Space Complexity: O(1) - static members use fixed memory
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

// =================== STATIC VARIABLES & METHODS ===================

class Employee {
    // Instance variables - each object gets its own copy
    private String name;
    private int id;
    private double salary;

    // Static variables - shared across ALL objects
    private static int employeeCount = 0;
    private static String companyName = "TechCorp";
    private static final double TAX_RATE = 0.3; // static + final = constant

    // Static block - runs once when class is loaded
    static {
        System.out.println("  [Static Block] Employee class loaded.");
        System.out.println("  [Static Block] Company: " + companyName + ", Tax rate: " + (TAX_RATE * 100) + "%");
    }

    // Instance initializer block - runs for each object before constructor
    {
        employeeCount++;
        System.out.println("  [Instance Block] Employee #" + employeeCount + " being created...");
    }

    // Constructor
    public Employee(String name, double salary) {
        this.name = name;
        this.id = employeeCount; // Uses the count incremented in instance block
        this.salary = salary;
    }

    // Instance method - can access both instance AND static members
    public double getNetSalary() {
        return salary * (1 - TAX_RATE); // Instance method using static constant
    }

    public void displayInfo() {
        System.out.printf("  ID: %d | Name: %-15s | Salary: $%.2f | Net: $%.2f%n",
                          id, name, salary, getNetSalary());
    }

    // Static method - can only access static members
    public static int getEmployeeCount() {
        // return salary; // ERROR: cannot access instance variable from static method
        // return this.name; // ERROR: 'this' doesn't exist in static context
        return employeeCount;
    }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(String name) {
        companyName = name; // Changes for ALL instances
    }

    public static void displayCompanyInfo() {
        System.out.println("  Company: " + companyName);
        System.out.println("  Total Employees: " + employeeCount);
        System.out.println("  Tax Rate: " + (TAX_RATE * 100) + "%");
    }
}

// =================== STATIC UTILITY CLASS ===================

class MathUtils {
    // Private constructor - prevent instantiation (utility class pattern)
    private MathUtils() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    // Static constants
    public static final double PI = 3.14159265358979;
    public static final double E = 2.71828182845905;
    public static final double GOLDEN_RATIO = 1.61803398874989;

    // Static utility methods
    public static int add(int a, int b) {
        return a + b;
    }

    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n < 4) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    public static int[] fibonacciSeries(int count) {
        int[] fib = new int[count];
        if (count >= 1) fib[0] = 0;
        if (count >= 2) fib[1] = 1;
        for (int i = 2; i < count; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }
}

// =================== STATIC BLOCKS (Multiple) ===================

class DatabaseConfig {
    private static final Map<String, String> config = new HashMap<>();
    private static final String dbUrl;
    private static final int maxConnections;

    // First static block
    static {
        System.out.println("  [DB Static Block 1] Loading default configuration...");
        config.put("driver", "com.mysql.jdbc.Driver");
        config.put("dialect", "MySQL");
        config.put("pool", "HikariCP");
    }

    // Second static block (runs after the first)
    static {
        System.out.println("  [DB Static Block 2] Loading connection settings...");
        dbUrl = "jdbc:mysql://localhost:3306/mydb";
        maxConnections = 10;
        config.put("url", dbUrl);
        config.put("maxConn", String.valueOf(maxConnections));
    }

    public static void displayConfig() {
        System.out.println("  Database Configuration:");
        for (Map.Entry<String, String> entry : config.entrySet()) {
            System.out.println("    " + entry.getKey() + " = " + entry.getValue());
        }
    }
}

// =================== STATIC INNER CLASS ===================

class LinkedListDemo {

    // Static inner class - does not need outer class instance
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }

        @Override
        public String toString() {
            return "Node{data=" + data + ", next=" + (next != null ? next.data : "null") + "}";
        }
    }

    // Non-static inner class (for comparison)
    class Iterator {
        private Node current;

        Iterator(Node start) {
            this.current = start;
        }

        boolean hasNext() {
            return current != null;
        }

        int next() {
            int val = current.data;
            current = current.next;
            return val;
        }
    }

    private Node head;
    private int size;

    public LinkedListDemo() {
        head = null;
        size = 0;
    }

    public void addFirst(int data) {
        Node newNode = new Node(data); // Can use static inner class directly
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void display() {
        Node current = head;
        System.out.print("  LinkedList: ");
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println(" → null (size=" + size + ")");
    }
}

// =================== SINGLETON PATTERN (uses static) ===================

class AppSettings {
    // Static reference to the single instance
    private static AppSettings instance;

    // Instance fields
    private String theme;
    private int fontSize;
    private String language;

    // Private constructor - only this class can create instances
    private AppSettings() {
        theme = "Dark";
        fontSize = 14;
        language = "English";
    }

    // Static factory method - creates or returns the single instance
    public static AppSettings getInstance() {
        if (instance == null) {
            instance = new AppSettings();
            System.out.println("  [Singleton] AppSettings instance created.");
        }
        return instance;
    }

    public void setTheme(String theme) { this.theme = theme; }
    public String getTheme() { return theme; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    public int getFontSize() { return fontSize; }

    @Override
    public String toString() {
        return "AppSettings{theme='" + theme + "', fontSize=" + fontSize + ", lang='" + language + "'}";
    }
}

// =================== COUNTER DEMO (Classic static variable use) ===================

class Counter {
    private static int count = 0; // Shared across all instances
    private int instanceId;

    public Counter() {
        count++;
        instanceId = count;
    }

    public static int getCount() {
        return count;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public static void resetCount() {
        count = 0;
    }
}

public class StaticKeywordDemo {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 67: Static Keyword Demo           ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // 1. Static Variables & Instance Blocks
        System.out.println("=== Static Variables (Shared Across Instances) ===");
        Employee emp1 = new Employee("Alice", 80000);
        Employee emp2 = new Employee("Bob", 95000);
        Employee emp3 = new Employee("Charlie", 70000);

        emp1.displayInfo();
        emp2.displayInfo();
        emp3.displayInfo();

        System.out.println("\n  Total employees: " + Employee.getEmployeeCount());
        System.out.println("  Company (via class): " + Employee.getCompanyName());

        // Changing static variable affects ALL instances
        System.out.println("\n  Changing company name to 'MegaCorp'...");
        Employee.setCompanyName("MegaCorp");
        Employee.displayCompanyInfo();

        // 2. Static Methods (Utility Class)
        System.out.println("\n=== Static Methods (Utility Class) ===");
        System.out.println("  MathUtils.add(5, 3) = " + MathUtils.add(5, 3));
        System.out.println("  MathUtils.factorial(10) = " + MathUtils.factorial(10));
        System.out.println("  MathUtils.isPrime(17) = " + MathUtils.isPrime(17));
        System.out.println("  MathUtils.isPrime(15) = " + MathUtils.isPrime(15));
        System.out.println("  MathUtils.gcd(48, 18) = " + MathUtils.gcd(48, 18));
        System.out.println("  Fibonacci(10): " + Arrays.toString(MathUtils.fibonacciSeries(10)));
        System.out.println("  MathUtils.PI = " + MathUtils.PI);
        System.out.println("  MathUtils.E = " + MathUtils.E);

        // Cannot instantiate utility class
        // MathUtils utils = new MathUtils(); // ERROR: private constructor

        // 3. Static Blocks
        System.out.println("\n=== Static Blocks (Run once at class loading) ===");
        System.out.println("  Triggering DatabaseConfig class load...");
        DatabaseConfig.displayConfig();

        // Static blocks don't run again
        System.out.println("\n  Calling displayConfig again (static blocks won't re-execute):");
        DatabaseConfig.displayConfig();

        // 4. Static Inner Class
        System.out.println("\n=== Static Inner Class ===");
        // Static inner class can be instantiated WITHOUT outer class
        LinkedListDemo.Node node1 = new LinkedListDemo.Node(10);
        LinkedListDemo.Node node2 = new LinkedListDemo.Node(20);
        node1.next = node2;
        System.out.println("  " + node1);
        System.out.println("  " + node2);

        // Using LinkedListDemo
        LinkedListDemo list = new LinkedListDemo();
        list.addFirst(30);
        list.addFirst(20);
        list.addFirst(10);
        list.display();

        // Non-static inner class NEEDS outer class instance
        // LinkedListDemo.Iterator iter = new LinkedListDemo.Iterator(node1); // ERROR
        LinkedListDemo.Iterator iter = list.new Iterator(node1); // Correct
        System.out.print("  Iterating: ");
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();

        // 5. Static Counter Demo
        System.out.println("\n=== Static Counter (Classic Example) ===");
        Counter.resetCount();
        System.out.println("  Initial count: " + Counter.getCount());

        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();
        Counter c4 = new Counter();
        Counter c5 = new Counter();

        System.out.println("  After creating 5 objects: " + Counter.getCount());
        System.out.println("  c1 instanceId: " + c1.getInstanceId());
        System.out.println("  c3 instanceId: " + c3.getInstanceId());
        System.out.println("  c5 instanceId: " + c5.getInstanceId());

        // 6. Singleton Pattern
        System.out.println("\n=== Singleton Pattern (Static Instance) ===");
        AppSettings settings1 = AppSettings.getInstance();
        AppSettings settings2 = AppSettings.getInstance(); // Returns same instance
        System.out.println("  settings1: " + settings1);
        System.out.println("  settings1 == settings2? " + (settings1 == settings2)); // true!

        settings1.setTheme("Light");
        System.out.println("  After settings1.setTheme('Light'):");
        System.out.println("  settings2.getTheme() = " + settings2.getTheme()); // Also "Light"!

        // 7. Static vs Instance Summary
        System.out.println("\n=== Static vs Instance: Key Differences ===");
        System.out.println("  ┌───────────────────────┬───────────────────────┐");
        System.out.println("  │ Static Members        │ Instance Members      │");
        System.out.println("  ├───────────────────────┼───────────────────────┤");
        System.out.println("  │ Belong to class       │ Belong to object      │");
        System.out.println("  │ One copy in memory    │ Copy per object       │");
        System.out.println("  │ Access via ClassName  │ Access via object ref │");
        System.out.println("  │ No 'this' keyword     │ 'this' available      │");
        System.out.println("  │ Can't access instance │ Can access static     │");
        System.out.println("  │ Loaded at class load  │ Created with 'new'    │");
        System.out.println("  └───────────────────────┴───────────────────────┘");

        System.out.println("\n  Static Keyword Uses:");
        System.out.println("  1. static variable  → shared counter, config");
        System.out.println("  2. static method    → utility/factory, Math.sqrt()");
        System.out.println("  3. static block     → complex initialization");
        System.out.println("  4. static class      → inner class without outer ref");
        System.out.println("  5. static import    → import static java.lang.Math.*");
        System.out.println("  6. static final     → constants (TAX_RATE, PI)");
    }
}
