/*
 * Program 01: Hello World
 * 
 * Problem Statement:
 *   Print "Hello, World!" to the console and demonstrate the basic structure
 *   of a Java program including class declaration, main method, and output.
 * 
 * Sample Input:  (none)
 * Sample Output:
 *   ========================================
 *   Welcome to Java Programming!
 *   ========================================
 *   Hello, World!
 *   
 *   --- Understanding Java Program Structure ---
 *   1. Every Java program must have at least one class.
 *   2. The class name must match the filename (HelloWorld.java -> class HelloWorld).
 *   3. The main method is the entry point: public static void main(String[] args)
 *   4. System.out.println() prints text followed by a newline.
 *   5. System.out.print() prints text WITHOUT a newline.
 *   6. Java statements end with a semicolon (;).
 *   7. Java is case-sensitive: 'Main' is not the same as 'main'.
 *   
 *   --- Demonstrating print vs println ---
 *   Hello World (print - no newline)...followed immediately!
 *   This is on a new line (println adds newline)
 *   
 *   --- Demonstrating escape sequences ---
 *   Tab separated:	Column1	Column2	Column3
 *   She said, "Hello!"
 *   Backslash: \
 *   Line1
 *   Line2
 *   
 *   --- Demonstrating formatted output ---
 *   Name: Alice    | Age:  25 | GPA: 3.90
 *   Name: Bob      | Age:  30 | GPA: 3.75
 *   ========================================
 *   Program completed successfully!
 *   ========================================
 * 
 * Explanation:
 *   This program introduces the fundamental building blocks of a Java application.
 *   - 'public class HelloWorld' declares a public class named HelloWorld.
 *   - 'public static void main(String[] args)' is the entry point where execution begins.
 *   - 'System.out.println()' writes to standard output with a trailing newline.
 *   - 'System.out.print()' writes without a trailing newline.
 *   - 'System.out.printf()' allows formatted output similar to C's printf.
 *   - Escape sequences like \t (tab), \" (quote), \\ (backslash), \n (newline) are shown.
 * 
 * Time Complexity:  O(1) - constant time, only prints fixed output
 * Space Complexity: O(1) - no additional data structures used
 */

public class HelloWorld {

    public static void main(String[] args) {

        // --- Basic Hello World ---
        System.out.println("========================================");
        System.out.println("Welcome to Java Programming!");
        System.out.println("========================================");
        System.out.println("Hello, World!");
        System.out.println();

        // --- Java Program Structure Explanation ---
        System.out.println("--- Understanding Java Program Structure ---");
        System.out.println("1. Every Java program must have at least one class.");
        System.out.println("2. The class name must match the filename (HelloWorld.java -> class HelloWorld).");
        System.out.println("3. The main method is the entry point: public static void main(String[] args)");
        System.out.println("4. System.out.println() prints text followed by a newline.");
        System.out.println("5. System.out.print() prints text WITHOUT a newline.");
        System.out.println("6. Java statements end with a semicolon (;).");
        System.out.println("7. Java is case-sensitive: 'Main' is not the same as 'main'.");
        System.out.println();

        // --- Demonstrating print vs println ---
        System.out.println("--- Demonstrating print vs println ---");
        System.out.print("Hello World (print - no newline)...");
        System.out.println("followed immediately!");
        System.out.println("This is on a new line (println adds newline)");
        System.out.println();

        // --- Demonstrating escape sequences ---
        System.out.println("--- Demonstrating escape sequences ---");
        System.out.println("Tab separated:\tColumn1\tColumn2\tColumn3");
        System.out.println("She said, \"Hello!\"");
        System.out.println("Backslash: \\");
        System.out.println("Line1\nLine2");
        System.out.println();

        // --- Demonstrating formatted output with printf ---
        System.out.println("--- Demonstrating formatted output ---");
        System.out.printf("Name: %-8s | Age: %3d | GPA: %.2f%n", "Alice", 25, 3.9);
        System.out.printf("Name: %-8s | Age: %3d | GPA: %.2f%n", "Bob", 30, 3.75);

        System.out.println("========================================");
        System.out.println("Program completed successfully!");
        System.out.println("========================================");
    }
}
