/*
 * Program 04: Type Casting
 * 
 * Problem Statement:
 *   Demonstrate implicit (widening) and explicit (narrowing) type casting in Java.
 *   Show the type promotion hierarchy, potential data loss during narrowing,
 *   and practical examples of when each type of casting is needed.
 * 
 * Sample Input:  (none)
 * Sample Output:
 *   ============================================================
 *              JAVA TYPE CASTING DEMONSTRATION
 *   ============================================================
 *   
 *   --- 1. IMPLICIT (WIDENING) CASTING ---
 *   Automatic: smaller type -> larger type (no data loss)
 *   Hierarchy: byte -> short -> int -> long -> float -> double
 *   
 *   byte  myByte   = 42
 *   short myShort  = 42       (byte -> short)
 *   int   myInt    = 42       (short -> int)
 *   long  myLong   = 42       (int -> long)
 *   float myFloat  = 42.0     (long -> float)
 *   double myDouble = 42.0    (float -> double)
 *   
 *   --- 2. EXPLICIT (NARROWING) CASTING ---
 *   Manual: larger type -> smaller type (possible data loss!)
 *   Syntax: (targetType) value
 *   
 *   double pi = 3.14159
 *   (int) pi     = 3          (fractional part lost!)
 *   (byte) 130   = -126       (overflow! 130 > Byte.MAX_VALUE)
 *   (short) 40000 = -25536    (overflow! 40000 > Short.MAX_VALUE)
 *   (char) 65    = 'A'        (int to char)
 *   (int) 'Z'    = 90         (char to int)
 *   ============================================================
 * 
 * Explanation:
 *   Widening (Implicit): Java automatically converts smaller types to larger types
 *   because there is no risk of data loss. This follows the hierarchy:
 *   byte -> short -> int -> long -> float -> double.
 *   
 *   Narrowing (Explicit): The programmer must manually cast using (type) syntax.
 *   Data loss can occur through truncation (decimals lost) or overflow (value
 *   wraps around). Understanding this is critical for avoiding subtle bugs.
 * 
 * Time Complexity:  O(1) - constant time operations
 * Space Complexity: O(1) - fixed number of variables
 */

public class TypeCasting {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("           JAVA TYPE CASTING DEMONSTRATION");
        System.out.println("============================================================");
        System.out.println();

        // ================================================================
        // 1. IMPLICIT (WIDENING) CASTING
        // ================================================================
        System.out.println("--- 1. IMPLICIT (WIDENING) CASTING ---");
        System.out.println("Automatic: smaller type -> larger type (no data loss)");
        System.out.println("Hierarchy: byte -> short -> int -> long -> float -> double");
        System.out.println();

        byte myByte = 42;
        short myShort = myByte;    // byte -> short (automatic)
        int myInt = myShort;       // short -> int (automatic)
        long myLong = myInt;       // int -> long (automatic)
        float myFloat = myLong;    // long -> float (automatic)
        double myDouble = myFloat; // float -> double (automatic)

        System.out.println("byte   myByte   = " + myByte);
        System.out.println("short  myShort  = " + myShort + "       (byte -> short)");
        System.out.println("int    myInt    = " + myInt + "       (short -> int)");
        System.out.println("long   myLong   = " + myLong + "       (int -> long)");
        System.out.println("float  myFloat  = " + myFloat + "     (long -> float)");
        System.out.println("double myDouble = " + myDouble + "    (float -> double)");
        System.out.println();

        // char to int widening
        char letter = 'A';
        int letterAsInt = letter;  // char -> int (automatic, gives Unicode value)
        System.out.println("char 'A' widened to int = " + letterAsInt + " (Unicode value)");
        System.out.println();

        // ================================================================
        // 2. EXPLICIT (NARROWING) CASTING
        // ================================================================
        System.out.println("--- 2. EXPLICIT (NARROWING) CASTING ---");
        System.out.println("Manual: larger type -> smaller type (possible data loss!)");
        System.out.println("Syntax: (targetType) value");
        System.out.println();

        // double to int - fractional part is truncated (not rounded)
        double pi = 3.14159;
        int piAsInt = (int) pi;
        System.out.println("double pi = " + pi);
        System.out.println("(int) pi  = " + piAsInt + "  (fractional part TRUNCATED, not rounded!)");
        System.out.println();

        // double to long
        double bigDecimal = 123456789.987;
        long bigLong = (long) bigDecimal;
        System.out.println("double bigDecimal = " + bigDecimal);
        System.out.println("(long) bigDecimal = " + bigLong + "  (fractional part lost)");
        System.out.println();

        // int to byte - overflow demonstration
        int largeInt = 130;
        byte narrowedByte = (byte) largeInt;
        System.out.println("int 130 -> (byte) = " + narrowedByte + "  (OVERFLOW! 130 > 127)");
        System.out.println("  Explanation: 130 in binary = 10000010");
        System.out.println("  byte keeps last 8 bits = 10000010 = -126 (two's complement)");
        System.out.println();

        // int to short - overflow
        int bigNumber = 40000;
        short narrowedShort = (short) bigNumber;
        System.out.println("int 40000 -> (short) = " + narrowedShort + "  (OVERFLOW! 40000 > 32767)");
        System.out.println();

        // int to char and char to int
        int asciiValue = 65;
        char charFromInt = (char) asciiValue;
        System.out.println("(char) 65 = '" + charFromInt + "'  (int to char, ASCII/Unicode)");
        System.out.println("(int) 'Z' = " + (int) 'Z' + "   (char to int)");
        System.out.println();

        // ================================================================
        // 3. TYPE PROMOTION IN EXPRESSIONS
        // ================================================================
        System.out.println("--- 3. TYPE PROMOTION IN EXPRESSIONS ---");
        System.out.println("Java automatically promotes types in arithmetic expressions.");
        System.out.println();

        byte b1 = 50;
        byte b2 = 30;
        // byte result = b1 + b2;  // ERROR! byte + byte is promoted to int
        int result = b1 + b2;      // Correct: result is int
        byte resultByte = (byte) (b1 + b2); // Or cast back explicitly
        System.out.println("byte 50 + byte 30 = " + result + " (result is int, not byte!)");
        System.out.println("(byte)(50 + 30)   = " + resultByte + " (explicitly cast back to byte)");
        System.out.println();

        // Mixed type expression
        int i = 10;
        double d = 5.5;
        double mixedResult = i + d;  // int promoted to double
        System.out.println("int 10 + double 5.5 = " + mixedResult + " (int promoted to double)");
        System.out.println();

        // Integer division vs floating-point division
        int numerator = 7;
        int denominator = 2;
        System.out.println("7 / 2 (int / int)         = " + (numerator / denominator) + "   (integer division, truncated)");
        System.out.println("7 / 2.0 (int / double)    = " + (numerator / 2.0) + " (promoted to double)");
        System.out.println("(double) 7 / 2            = " + ((double) numerator / denominator) + " (cast numerator first)");
        System.out.println();

        // ================================================================
        // 4. STRING TO PRIMITIVE CONVERSIONS (PARSING)
        // ================================================================
        System.out.println("--- 4. STRING <-> PRIMITIVE CONVERSIONS ---");
        System.out.println();

        // String to primitive (parsing)
        String numStr = "12345";
        int parsedInt = Integer.parseInt(numStr);
        double parsedDouble = Double.parseDouble("98.76");
        boolean parsedBool = Boolean.parseBoolean("true");
        System.out.println("Integer.parseInt(\"12345\")     = " + parsedInt);
        System.out.println("Double.parseDouble(\"98.76\")   = " + parsedDouble);
        System.out.println("Boolean.parseBoolean(\"true\")  = " + parsedBool);
        System.out.println();

        // Primitive to String
        String intToStr = String.valueOf(42);
        String doubleToStr = String.valueOf(3.14);
        String concatWay = "" + 42;  // Concatenation trick
        System.out.println("String.valueOf(42)   = \"" + intToStr + "\"");
        System.out.println("String.valueOf(3.14) = \"" + doubleToStr + "\"");
        System.out.println("\"\" + 42             = \"" + concatWay + "\" (concatenation trick)");

        System.out.println();
        System.out.println("============================================================");
        System.out.println("         TYPE CASTING DEMO COMPLETED");
        System.out.println("============================================================");
    }
}
