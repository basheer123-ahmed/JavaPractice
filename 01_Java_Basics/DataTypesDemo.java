/*
 * Program 02: Data Types Demo
 * 
 * Problem Statement:
 *   Demonstrate all 8 primitive data types in Java (byte, short, int, long,
 *   float, double, char, boolean) with their sizes, ranges, default concepts,
 *   and practical usage examples.
 * 
 * Sample Input:  (none)
 * Sample Output:
 *   ============================================================
 *            JAVA PRIMITIVE DATA TYPES DEMONSTRATION
 *   ============================================================
 *   
 *   1. byte  (1 byte / 8 bits)
 *      Range: -128 to 127
 *      Example value: 100
 *      Use case: Storing small numbers, file I/O byte streams
 *   
 *   2. short (2 bytes / 16 bits)
 *      Range: -32768 to 32767
 *      Example value: 30000
 *      Use case: Saving memory in large arrays of small numbers
 *   
 *   3. int   (4 bytes / 32 bits)
 *      Range: -2147483648 to 2147483647
 *      Example value: 1000000
 *      Use case: Default choice for whole numbers
 *   
 *   4. long  (8 bytes / 64 bits)
 *      Range: -9223372036854775808 to 9223372036854775807
 *      Example value: 9876543210
 *      Use case: Large numbers like timestamps, file sizes
 *   
 *   5. float (4 bytes / 32 bits)
 *      Example value: 3.14159
 *      Precision: ~6-7 decimal digits
 *      Use case: When memory is limited and precision is not critical
 *   
 *   6. double (8 bytes / 64 bits)
 *      Example value: 3.141592653589793
 *      Precision: ~15-16 decimal digits
 *      Use case: Default choice for decimal numbers, scientific calc
 *   
 *   7. char  (2 bytes / 16 bits - Unicode)
 *      Example: 'A' -> Unicode value: 65
 *      Use case: Storing single characters, Unicode support
 *   
 *   8. boolean (JVM dependent, logically 1 bit)
 *      Values: true, false
 *      Example: isJavaFun = true
 *      Use case: Flags, conditions, logical operations
 *   
 *   --- Special Literals ---
 *   Binary literal:      0b1010 = 10
 *   Octal literal:       012 = 10
 *   Hexadecimal literal: 0xA = 10
 *   Underscore in numeric: 1_000_000 = 1000000
 *   ============================================================
 * 
 * Explanation:
 *   Java has 8 primitive data types organized into 4 categories:
 *   - Integer types: byte (8-bit), short (16-bit), int (32-bit), long (64-bit)
 *   - Floating-point types: float (32-bit), double (64-bit)
 *   - Character type: char (16-bit Unicode)
 *   - Boolean type: boolean (true/false)
 *   Each type has a wrapper class (Integer, Double, etc.) for object representation.
 * 
 * Time Complexity:  O(1) - constant operations
 * Space Complexity: O(1) - fixed number of variables
 */

public class DataTypesDemo {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("         JAVA PRIMITIVE DATA TYPES DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println();

        // 1. byte: 8-bit signed integer
        byte myByte = 100;
        System.out.println("1. byte  (1 byte / 8 bits)");
        System.out.println("   Range: " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE);
        System.out.println("   Example value: " + myByte);
        System.out.println("   Use case: Storing small numbers, file I/O byte streams");
        System.out.println();

        // 2. short: 16-bit signed integer
        short myShort = 30000;
        System.out.println("2. short (2 bytes / 16 bits)");
        System.out.println("   Range: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE);
        System.out.println("   Example value: " + myShort);
        System.out.println("   Use case: Saving memory in large arrays of small numbers");
        System.out.println();

        // 3. int: 32-bit signed integer (default for integers)
        int myInt = 1_000_000;
        System.out.println("3. int   (4 bytes / 32 bits)");
        System.out.println("   Range: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE);
        System.out.println("   Example value: " + myInt);
        System.out.println("   Use case: Default choice for whole numbers");
        System.out.println();

        // 4. long: 64-bit signed integer
        long myLong = 9_876_543_210L;  // Note the 'L' suffix
        System.out.println("4. long  (8 bytes / 64 bits)");
        System.out.println("   Range: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE);
        System.out.println("   Example value: " + myLong);
        System.out.println("   Use case: Large numbers like timestamps, file sizes");
        System.out.println("   Note: Long literals require 'L' suffix (e.g., 9876543210L)");
        System.out.println();

        // 5. float: 32-bit IEEE 754 floating point
        float myFloat = 3.14159f;  // Note the 'f' suffix
        System.out.println("5. float (4 bytes / 32 bits)");
        System.out.println("   Example value: " + myFloat);
        System.out.println("   Precision: ~6-7 decimal digits");
        System.out.println("   Use case: When memory is limited and precision is not critical");
        System.out.println("   Note: Float literals require 'f' suffix (e.g., 3.14f)");
        System.out.println();

        // 6. double: 64-bit IEEE 754 floating point (default for decimals)
        double myDouble = 3.141592653589793;
        System.out.println("6. double (8 bytes / 64 bits)");
        System.out.println("   Example value: " + myDouble);
        System.out.println("   Precision: ~15-16 decimal digits");
        System.out.println("   Use case: Default choice for decimal numbers, scientific calc");
        System.out.println();

        // 7. char: 16-bit Unicode character
        char myChar = 'A';
        char unicodeChar = '\u0041';  // Also 'A' in Unicode
        System.out.println("7. char  (2 bytes / 16 bits - Unicode)");
        System.out.println("   Example: '" + myChar + "' -> Unicode value: " + (int) myChar);
        System.out.println("   Unicode literal: '\\u0041' = '" + unicodeChar + "'");
        System.out.println("   Range: '\\u0000' (0) to '\\uffff' (65535)");
        System.out.println("   Use case: Storing single characters, Unicode support");
        System.out.println();

        // 8. boolean: true or false
        boolean isJavaFun = true;
        boolean isFishMammal = false;
        System.out.println("8. boolean (JVM dependent, logically 1 bit)");
        System.out.println("   Values: true, false");
        System.out.println("   Example: isJavaFun = " + isJavaFun);
        System.out.println("   Example: isFishMammal = " + isFishMammal);
        System.out.println("   Use case: Flags, conditions, logical operations");
        System.out.println();

        // --- Special Number Literals ---
        System.out.println("--- Special Literals ---");
        int binary = 0b1010;        // Binary literal (prefix 0b)
        int octal = 012;            // Octal literal (prefix 0)
        int hex = 0xA;              // Hexadecimal literal (prefix 0x)
        int withUnderscore = 1_000_000; // Underscore for readability (Java 7+)

        System.out.println("Binary literal:      0b1010 = " + binary);
        System.out.println("Octal literal:       012 = " + octal);
        System.out.println("Hexadecimal literal: 0xA = " + hex);
        System.out.println("Underscore in numeric: 1_000_000 = " + withUnderscore);
        System.out.println();

        // --- Wrapper Classes and Useful Methods ---
        System.out.println("--- Wrapper Classes ---");
        System.out.println("Integer.parseInt(\"42\") = " + Integer.parseInt("42"));
        System.out.println("Double.parseDouble(\"3.14\") = " + Double.parseDouble("3.14"));
        System.out.println("Integer.toBinaryString(10) = " + Integer.toBinaryString(10));
        System.out.println("Integer.toHexString(255) = " + Integer.toHexString(255));
        System.out.println("Integer.toOctalString(8) = " + Integer.toOctalString(8));
        System.out.println("Character.isDigit('5') = " + Character.isDigit('5'));
        System.out.println("Character.isLetter('A') = " + Character.isLetter('A'));
        System.out.println("Character.toUpperCase('a') = " + Character.toUpperCase('a'));
        System.out.println();

        // --- Floating Point Precision Gotcha ---
        System.out.println("--- Floating Point Precision Gotcha ---");
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
        System.out.println("Expected:    0.3");
        System.out.println("Tip: Use BigDecimal for exact decimal arithmetic (e.g., money).");
        System.out.println();

        // --- Overflow / Underflow Demo ---
        System.out.println("--- Overflow / Underflow Demo ---");
        int maxInt = Integer.MAX_VALUE;
        System.out.println("Integer.MAX_VALUE     = " + maxInt);
        System.out.println("Integer.MAX_VALUE + 1 = " + (maxInt + 1) + "  (overflow wraps around!)");
        byte maxByte = Byte.MAX_VALUE;
        System.out.println("Byte.MAX_VALUE        = " + maxByte);
        System.out.println("Byte.MAX_VALUE + 1    = " + (byte)(maxByte + 1) + "   (overflow wraps around!)");

        System.out.println();
        System.out.println("============================================================");
        System.out.println("          DATA TYPES DEMO COMPLETED SUCCESSFULLY");
        System.out.println("============================================================");
    }
}
