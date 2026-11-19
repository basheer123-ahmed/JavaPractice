/*
 * Program 14: User Input Demo — Scanner Methods Showcase
 * 
 * Problem Statement:
 *   Write a Java program that demonstrates reading various data types from the user
 *   using the Scanner class. Cover the following:
 *   1. Reading an integer (nextInt)
 *   2. Reading a double (nextDouble)
 *   3. Reading a single word String (next)
 *   4. Reading a full line String (nextLine) — including the newline-consumption pitfall
 *   5. Reading a character (charAt(0) from next())
 *   6. Reading a boolean (nextBoolean)
 *   7. Reading a long (nextLong)
 *   8. Demonstrating hasNext/hasNextInt for input validation
 *   9. Common pitfall: nextInt/nextLine ordering issue and how to fix it
 * 
 * Sample Input:
 *   Age: 25
 *   GPA: 3.85
 *   First name: John
 *   Full name: John Michael Doe
 *   Initial: J
 *   Student: true
 * 
 * Sample Output:
 *   ===== USER INPUT DEMO =====
 *   
 *   [1] Integer Input (nextInt):
 *       Enter your age: 25
 *       You entered age: 25
 *       You were born approximately in 1999
 *   
 *   [2] Double Input (nextDouble):
 *       Enter your GPA: 3.85
 *       Your GPA is: 3.85
 *       GPA Status: Dean's List (>= 3.5)
 *   
 *   [3] String Input (next - single word):
 *       Enter your first name: John
 *       Hello, John!
 *   
 *   [4] String Input (nextLine - full line):
 *       Enter your full name: John Michael Doe
 *       Full name: John Michael Doe
 *       Name has 3 parts
 *   ...
 * 
 * Explanation:
 *   - Scanner is in the java.util package and reads input from System.in (keyboard).
 *   - nextInt(), nextDouble(), nextLong(), nextBoolean() read their respective types.
 *   - next() reads a single token (word), stopping at whitespace.
 *   - nextLine() reads the entire line including spaces, stopping at newline.
 *   - PITFALL: After nextInt()/nextDouble(), the newline character '\n' remains
 *     in the input buffer. A subsequent nextLine() reads this leftover '\n' as an
 *     empty string. FIX: Call scanner.nextLine() once to consume the leftover newline
 *     before reading the actual line input.
 *   - hasNextInt(), hasNext() etc. check if the next token matches the expected type,
 *     useful for input validation without throwing InputMismatchException.
 * 
 * Time Complexity: O(n) where n is the length of user input strings
 * Space Complexity: O(n) for storing input strings
 */

import java.util.Scanner;

public class UserInputDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     USER INPUT DEMO — SCANNER METHODS   ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // ============================================================
        // [1] Reading an Integer — nextInt()
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[1] INTEGER INPUT (nextInt)");
        System.out.println("────────────────────────────────────────");
        System.out.println("    Method: scanner.nextInt()");
        System.out.println("    Reads: One integer value from input");

        System.out.print("    Enter your age: ");
        int age = scanner.nextInt();

        System.out.println("    You entered age: " + age);
        int currentYear = java.time.Year.now().getValue();
        System.out.println("    You were born approximately in " + (currentYear - age));
        System.out.println("    Age in months: " + (age * 12));
        System.out.println("    Age in days (approx): " + (age * 365));

        // ============================================================
        // [2] Reading a Double — nextDouble()
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[2] DOUBLE INPUT (nextDouble)");
        System.out.println("────────────────────────────────────────");
        System.out.println("    Method: scanner.nextDouble()");
        System.out.println("    Reads: One floating-point value");

        System.out.print("    Enter your GPA (0.0 - 4.0): ");
        double gpa = scanner.nextDouble();

        System.out.printf("    Your GPA is: %.2f%n", gpa);
        if (gpa >= 3.5) {
            System.out.println("    GPA Status: Dean's List (>= 3.5) ★");
        } else if (gpa >= 3.0) {
            System.out.println("    GPA Status: Honor Roll (>= 3.0)");
        } else if (gpa >= 2.0) {
            System.out.println("    GPA Status: Good Standing (>= 2.0)");
        } else {
            System.out.println("    GPA Status: Academic Probation (< 2.0)");
        }

        // ============================================================
        // [3] Reading a Single Word — next()
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[3] SINGLE WORD INPUT (next)");
        System.out.println("────────────────────────────────────────");
        System.out.println("    Method: scanner.next()");
        System.out.println("    Reads: One token (stops at whitespace)");

        System.out.print("    Enter your first name: ");
        String firstName = scanner.next();

        System.out.println("    Hello, " + firstName + "!");
        System.out.println("    Name length: " + firstName.length() + " characters");
        System.out.println("    Uppercase: " + firstName.toUpperCase());
        System.out.println("    Lowercase: " + firstName.toLowerCase());
        System.out.println("    First character: '" + firstName.charAt(0) + "'");

        // ============================================================
        // [4] THE PITFALL: nextInt/next followed by nextLine
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[4] FULL LINE INPUT (nextLine)");
        System.out.println("────────────────────────────────────────");
        System.out.println("    Method: scanner.nextLine()");
        System.out.println("    Reads: Entire line including spaces");
        System.out.println();
        System.out.println("    ⚠ COMMON PITFALL:");
        System.out.println("    After nextInt()/next()/nextDouble(), the '\\n' remains");
        System.out.println("    in the buffer. nextLine() reads this empty '\\n' instead");
        System.out.println("    of waiting for new input.");
        System.out.println();
        System.out.println("    ✓ FIX: Call scanner.nextLine() once to consume the");
        System.out.println("    leftover newline, then call it again for real input.");
        System.out.println();

        // THIS is the fix — consume the leftover newline from previous next() call
        scanner.nextLine(); // <--- Consuming the leftover '\n'

        System.out.print("    Enter your full name (with spaces): ");
        String fullName = scanner.nextLine();

        System.out.println("    Full name: " + fullName);
        String[] nameParts = fullName.trim().split("\\s+");
        System.out.println("    Name has " + nameParts.length + " part(s)");
        if (nameParts.length >= 2) {
            System.out.println("    First name: " + nameParts[0]);
            System.out.println("    Last name: " + nameParts[nameParts.length - 1]);
        }

        // ============================================================
        // [5] Reading a Character
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[5] CHARACTER INPUT");
        System.out.println("────────────────────────────────────────");
        System.out.println("    Java has NO scanner.nextChar() method!");
        System.out.println("    Workaround: scanner.next().charAt(0)");

        System.out.print("    Enter your middle initial: ");
        char initial = scanner.next().charAt(0);

        System.out.println("    Your initial is: '" + initial + "'");
        System.out.println("    ASCII value: " + (int) initial);
        System.out.println("    Is uppercase? " + Character.isUpperCase(initial));
        System.out.println("    Is letter? " + Character.isLetter(initial));
        System.out.println("    Is digit? " + Character.isDigit(initial));

        // ============================================================
        // [6] Reading a Boolean — nextBoolean()
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[6] BOOLEAN INPUT (nextBoolean)");
        System.out.println("────────────────────────────────────────");
        System.out.println("    Method: scanner.nextBoolean()");
        System.out.println("    Accepts: 'true' or 'false' (case-insensitive)");

        System.out.print("    Are you a student? (true/false): ");
        boolean isStudent = scanner.nextBoolean();

        System.out.println("    Student status: " + isStudent);
        System.out.println("    Discount eligible: " + (isStudent ? "Yes (10% off)" : "No"));

        // ============================================================
        // [7] Reading a Long — nextLong()
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[7] LONG INPUT (nextLong)");
        System.out.println("────────────────────────────────────────");
        System.out.println("    Method: scanner.nextLong()");
        System.out.println("    Range: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807");

        System.out.print("    Enter your phone number (digits only): ");
        long phoneNumber = scanner.nextLong();

        System.out.println("    Phone number: " + phoneNumber);
        System.out.println("    Number of digits: " + String.valueOf(phoneNumber).length());

        // ============================================================
        // [8] Input Validation with hasNextXxx()
        // ============================================================
        System.out.println("\n────────────────────────────────────────");
        System.out.println("[8] INPUT VALIDATION (hasNextInt, hasNext)");
        System.out.println("────────────────────────────────────────");
        System.out.println("    hasNextInt()  — checks if next token is an int");
        System.out.println("    hasNextDouble() — checks if next token is a double");
        System.out.println("    hasNext()     — checks if there is another token");

        System.out.print("    Enter a number for validation: ");
        if (scanner.hasNextInt()) {
            int validNum = scanner.nextInt();
            System.out.println("    ✓ Valid integer: " + validNum);
        } else {
            String invalidInput = scanner.next();
            System.out.println("    ✗ '" + invalidInput + "' is NOT a valid integer!");
        }

        // ============================================================
        // [9] Summary of Scanner Methods
        // ============================================================
        System.out.println("\n════════════════════════════════════════");
        System.out.println("  SCANNER METHODS QUICK REFERENCE");
        System.out.println("════════════════════════════════════════");
        System.out.println("  ┌──────────────────┬────────────────────────────────────┐");
        System.out.println("  │ Method           │ Description                        │");
        System.out.println("  ├──────────────────┼────────────────────────────────────┤");
        System.out.println("  │ nextInt()         │ Reads an int                       │");
        System.out.println("  │ nextDouble()      │ Reads a double                     │");
        System.out.println("  │ nextFloat()       │ Reads a float                      │");
        System.out.println("  │ nextLong()        │ Reads a long                       │");
        System.out.println("  │ nextShort()       │ Reads a short                      │");
        System.out.println("  │ nextByte()        │ Reads a byte                       │");
        System.out.println("  │ nextBoolean()     │ Reads true/false                   │");
        System.out.println("  │ next()            │ Reads one word (token)             │");
        System.out.println("  │ nextLine()        │ Reads entire line                  │");
        System.out.println("  │ next().charAt(0)  │ Reads a char (workaround)          │");
        System.out.println("  │ hasNextInt()      │ Checks if next is int              │");
        System.out.println("  │ hasNextDouble()   │ Checks if next is double           │");
        System.out.println("  │ hasNext()         │ Checks if more tokens exist        │");
        System.out.println("  │ hasNextLine()     │ Checks if another line exists      │");
        System.out.println("  └──────────────────┴────────────────────────────────────┘");

        System.out.println("\n  ⚠ PITFALL REMINDER:");
        System.out.println("  After nextInt()/nextDouble()/next(), always call");
        System.out.println("  nextLine() once to consume the leftover '\\n' before");
        System.out.println("  reading a full line with nextLine().");
        System.out.println();
        System.out.println("  WRONG:                      RIGHT:");
        System.out.println("  int n = sc.nextInt();        int n = sc.nextInt();");
        System.out.println("  String s = sc.nextLine();    sc.nextLine(); // consume \\n");
        System.out.println("  // s is empty \"\"!            String s = sc.nextLine();");

        // Close the scanner
        scanner.close();

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║     Program completed successfully!      ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
