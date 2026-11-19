/*
 * Program 05: Swap Two Numbers
 * 
 * Problem Statement:
 *   Swap two numbers using three different methods:
 *   1. Using a temporary variable (most readable)
 *   2. Using arithmetic operations (no extra variable)
 *   3. Using XOR bitwise operator (no extra variable, integer only)
 * 
 * Sample Input:
 *   Enter first number (a): 25
 *   Enter second number (b): 73
 * 
 * Sample Output:
 *   ========================================
 *         SWAP TWO NUMBERS
 *   ========================================
 *   Original values: a = 25, b = 73
 *   
 *   --- Method 1: Using Temporary Variable ---
 *   Step 1: temp = a      -> temp = 25
 *   Step 2: a = b         -> a = 73
 *   Step 3: b = temp      -> b = 25
 *   After swap: a = 73, b = 25
 *   
 *   --- Method 2: Using Arithmetic (Addition/Subtraction) ---
 *   Step 1: a = a + b     -> a = 98
 *   Step 2: b = a - b     -> b = 25
 *   Step 3: a = a - b     -> a = 73
 *   After swap: a = 73, b = 25
 *   
 *   --- Method 3: Using XOR Bitwise Operator ---
 *   Step 1: a = a ^ b     -> a = 82
 *   Step 2: b = a ^ b     -> b = 25
 *   Step 3: a = a ^ b     -> a = 73
 *   After swap: a = 73, b = 25
 *   ========================================
 * 
 * Explanation:
 *   Method 1 (Temp Variable): The most common and readable approach. Uses one
 *   extra variable to hold the original value during the swap.
 *   
 *   Method 2 (Arithmetic): Uses addition and subtraction to avoid a temp variable.
 *   Caution: Can cause integer overflow for very large values.
 *   
 *   Method 3 (XOR): Uses the XOR property that a ^ a = 0 and a ^ 0 = a.
 *   Works only with integers. Fails if both variables reference the same location.
 *   XOR swap: a^=b; b^=a; a^=b effectively swaps without a temp variable.
 * 
 * Time Complexity:  O(1) - constant time for all three methods
 * Space Complexity: O(1) - Method 1 uses one extra variable; Methods 2 & 3 use none
 */

import java.util.Scanner;

public class SwapTwoNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("        SWAP TWO NUMBERS");
        System.out.println("========================================");

        System.out.print("Enter first number (a): ");
        int originalA = scanner.nextInt();

        System.out.print("Enter second number (b): ");
        int originalB = scanner.nextInt();

        System.out.println();
        System.out.println("Original values: a = " + originalA + ", b = " + originalB);

        // ============================================================
        // Method 1: Using a Temporary Variable
        // ============================================================
        System.out.println();
        System.out.println("--- Method 1: Using Temporary Variable ---");
        int a = originalA, b = originalB;

        System.out.println("Step 1: temp = a      -> temp = " + a);
        int temp = a;

        a = b;
        System.out.println("Step 2: a = b         -> a = " + a);

        b = temp;
        System.out.println("Step 3: b = temp      -> b = " + b);

        System.out.println("After swap: a = " + a + ", b = " + b);
        verifySwap(a, b, originalA, originalB);

        // ============================================================
        // Method 2: Using Arithmetic (Addition & Subtraction)
        // ============================================================
        System.out.println();
        System.out.println("--- Method 2: Using Arithmetic (Addition/Subtraction) ---");
        a = originalA;
        b = originalB;

        a = a + b;  // a now holds the sum
        System.out.println("Step 1: a = a + b     -> a = " + a);

        b = a - b;  // b gets original a (sum - original b)
        System.out.println("Step 2: b = a - b     -> b = " + b);

        a = a - b;  // a gets original b (sum - original a)
        System.out.println("Step 3: a = a - b     -> a = " + a);

        System.out.println("After swap: a = " + a + ", b = " + b);
        verifySwap(a, b, originalA, originalB);

        // ============================================================
        // Method 3: Using XOR Bitwise Operator
        // ============================================================
        System.out.println();
        System.out.println("--- Method 3: Using XOR Bitwise Operator ---");
        a = originalA;
        b = originalB;

        a = a ^ b;
        System.out.println("Step 1: a = a ^ b     -> a = " + a);
        System.out.println("   Binary: " + Integer.toBinaryString(originalA) 
            + " ^ " + Integer.toBinaryString(originalB) 
            + " = " + Integer.toBinaryString(a));

        b = a ^ b;
        System.out.println("Step 2: b = a ^ b     -> b = " + b);

        a = a ^ b;
        System.out.println("Step 3: a = a ^ b     -> a = " + a);

        System.out.println("After swap: a = " + a + ", b = " + b);
        verifySwap(a, b, originalA, originalB);

        // ============================================================
        // Why XOR works - Explanation
        // ============================================================
        System.out.println();
        System.out.println("--- Why XOR Swap Works ---");
        System.out.println("Property 1: a ^ a = 0 (any number XOR itself is 0)");
        System.out.println("Property 2: a ^ 0 = a (any number XOR 0 is itself)");
        System.out.println("Property 3: XOR is commutative and associative");
        System.out.println();
        System.out.println("Let a = X, b = Y initially:");
        System.out.println("Step 1: a = X ^ Y");
        System.out.println("Step 2: b = (X ^ Y) ^ Y = X ^ (Y ^ Y) = X ^ 0 = X  ✓");
        System.out.println("Step 3: a = (X ^ Y) ^ X = Y ^ (X ^ X) = Y ^ 0 = Y  ✓");

        // ============================================================
        // Comparison Summary
        // ============================================================
        System.out.println();
        System.out.println("--- Method Comparison ---");
        System.out.println("+----------+------------+----------+-------------------+");
        System.out.println("| Method   | Extra Space| Overflow | Works with floats |");
        System.out.println("+----------+------------+----------+-------------------+");
        System.out.println("| Temp Var | 1 variable | No       | Yes               |");
        System.out.println("| Arith.   | None       | Yes!     | Yes (with risk)   |");
        System.out.println("| XOR      | None       | No       | No (int only)     |");
        System.out.println("+----------+------------+----------+-------------------+");
        System.out.println("Recommendation: Use temp variable in production code.");

        System.out.println();
        System.out.println("========================================");
        System.out.println("     SWAP DEMO COMPLETED");
        System.out.println("========================================");

        scanner.close();
    }

    /**
     * Verifies that the swap was performed correctly.
     */
    private static void verifySwap(int a, int b, int originalA, int originalB) {
        if (a == originalB && b == originalA) {
            System.out.println("✓ Swap verified successfully!");
        } else {
            System.out.println("✗ Swap verification FAILED!");
        }
    }
}
