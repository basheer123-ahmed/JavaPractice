/*
 * Program 17: Factorial Calculator
 * 
 * Problem Statement:
 *   Calculate the factorial of a non-negative integer using both iterative
 *   (loop) and recursive approaches. Handle edge cases like 0! = 1, negative
 *   numbers (invalid), and large numbers that cause overflow. Also demonstrate
 *   using BigInteger for arbitrarily large factorials.
 * 
 * Sample Input:  5
 * Sample Output:
 *   Iterative: 5! = 120
 *   Recursive: 5! = 120
 *   BigInteger: 5! = 120
 * 
 * Additional Sample:
 *   Input:  20
 *   Output: 20! = 2432902008176640000
 *   Input:  25 (overflows long)
 *   BigInteger: 25! = 15511210043330985984000000
 * 
 * Explanation:
 *   Factorial of n (n!) = n * (n-1) * (n-2) * ... * 2 * 1
 *   Special case: 0! = 1 (by definition)
 *   The iterative approach uses a loop; the recursive approach calls itself
 *   with n-1 until reaching the base case. For very large n, BigInteger
 *   avoids overflow.
 * 
 * Time Complexity:  O(n) for both iterative and recursive
 * Space Complexity: O(1) iterative, O(n) recursive (call stack)
 */

import java.math.BigInteger;
import java.util.Scanner;

public class FactorialCalculator {

    // --- Method 1: Iterative factorial using long ---
    public static long factorialIterative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // --- Method 2: Recursive factorial using long ---
    public static long factorialRecursive(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        // Base case
        if (n == 0 || n == 1) {
            return 1;
        }
        // Recursive case
        return n * factorialRecursive(n - 1);
    }

    // --- Method 3: Iterative factorial using BigInteger (handles arbitrarily large numbers) ---
    public static BigInteger factorialBigInteger(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    // --- Method 4: Tail-recursive factorial (using accumulator) ---
    // Java doesn't optimize tail recursion, but this is an important pattern to know.
    public static long factorialTailRecursive(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        return factorialHelper(n, 1);
    }

    private static long factorialHelper(int n, long accumulator) {
        if (n == 0 || n == 1) {
            return accumulator;
        }
        return factorialHelper(n - 1, n * accumulator);
    }

    // --- Method 5: Iterative with overflow detection ---
    public static long factorialSafe(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        if (n > 20) {
            throw new ArithmeticException("Factorial of " + n 
                + " overflows long. Maximum is 20! Use BigInteger instead.");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result = Math.multiplyExact(result, i); // throws ArithmeticException on overflow
        }
        return result;
    }

    // --- Method 6: Count trailing zeros in n! ---
    // Trailing zeros are caused by factors of 10, which come from pairs of 2 and 5.
    // Since there are always more 2s than 5s, we just count factors of 5.
    public static int trailingZeros(int n) {
        int count = 0;
        while (n >= 5) {
            n /= 5;
            count += n;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Basic factorial calculations ---
        System.out.print("Enter a non-negative integer: ");
        int n = scanner.nextInt();

        System.out.println("\n=== Factorial of " + n + " ===");

        // Edge case: negative number
        if (n < 0) {
            System.out.println("Error: Factorial is not defined for negative numbers.");
        } else {
            // Iterative
            if (n <= 20) {
                System.out.println("Iterative (long):       " + n + "! = " + factorialIterative(n));
                System.out.println("Recursive (long):       " + n + "! = " + factorialRecursive(n));
                System.out.println("Tail-recursive (long):  " + n + "! = " + factorialTailRecursive(n));
            } else {
                System.out.println("(long versions skipped — overflow for n > 20)");
            }

            // BigInteger (never overflows)
            System.out.println("BigInteger:             " + n + "! = " + factorialBigInteger(n));

            // Trailing zeros
            System.out.println("Trailing zeros in " + n + "!:  " + trailingZeros(n));
        }

        // --- Demo 2: Edge cases ---
        System.out.println("\n=== Edge Cases ===");
        System.out.println("0! = " + factorialIterative(0) + "  (by definition)");
        System.out.println("1! = " + factorialIterative(1));
        System.out.println("2! = " + factorialIterative(2));

        // --- Demo 3: Overflow demonstration ---
        System.out.println("\n=== Overflow Demonstration ===");
        System.out.println("20! = " + factorialIterative(20) + " (fits in long)");
        System.out.println("21! via BigInteger = " + factorialBigInteger(21));
        System.out.println("25! via BigInteger = " + factorialBigInteger(25));
        System.out.println("50! via BigInteger = " + factorialBigInteger(50));

        // Safe factorial with overflow check
        System.out.println("\n=== Overflow-Safe Factorial ===");
        try {
            System.out.println("Safe 20! = " + factorialSafe(20));
            System.out.println("Safe 21! = " + factorialSafe(21)); // This will throw
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // --- Demo 4: Trailing zeros ---
        System.out.println("\n=== Trailing Zeros ===");
        int[] testValues = {5, 10, 25, 100, 1000};
        for (int val : testValues) {
            System.out.println("Trailing zeros in " + val + "! = " + trailingZeros(val));
        }

        // --- Demo 5: Comparison table ---
        System.out.println("\n=== Factorial Table (0 to 20) ===");
        System.out.printf("%-5s %-25s%n", "n", "n!");
        System.out.println("---   " + "-".repeat(22));
        for (int i = 0; i <= 20; i++) {
            System.out.printf("%-5d %-25d%n", i, factorialIterative(i));
        }

        scanner.close();
    }
}
