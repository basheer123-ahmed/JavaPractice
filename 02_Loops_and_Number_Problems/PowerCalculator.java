/*
 * Program 28: Power Calculator
 * 
 * Problem Statement:
 *   Calculate base^exponent using three different methods:
 *   1. Iterative loop (simple multiplication)
 *   2. Recursive approach
 *   3. Fast exponentiation using binary method (exponentiation by squaring)
 *   Also handle edge cases: exponent = 0, negative exponents, and large values.
 * 
 * Sample Input:  base=2, exp=10 | base=3, exp=5 | base=5, exp=0 | base=2, exp=-3
 * Sample Output:
 *   2^10 = 1024
 *   3^5  = 243
 *   5^0  = 1
 *   2^-3 = 0.125
 * 
 * Explanation:
 *   - Iterative: Multiply base by itself exp times. O(n) time.
 *   - Recursive: power(b,e) = b * power(b, e-1). O(n) time, O(n) stack space.
 *   - Fast (Binary): If exp is even, power(b,e) = power(b*b, e/2).
 *     If exp is odd, power(b,e) = b * power(b*b, (e-1)/2). O(log n) time.
 *     This is the method used in real-world implementations (e.g., modular exponentiation in RSA).
 * 
 * Time Complexity:  Iterative O(n), Recursive O(n), Fast O(log n)
 * Space Complexity: Iterative O(1), Recursive O(n) stack, Fast O(log n) stack / O(1) iterative
 */

import java.util.Scanner;

public class PowerCalculator {

    // ==================== METHOD 1: ITERATIVE ====================

    /**
     * Calculates base^exp using a simple loop.
     * Time: O(exp), Space: O(1)
     */
    public static long powerIterative(long base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Use powerIterativeDouble for negative exponents");
        }
        if (exp == 0) return 1;

        long result = 1;
        for (int i = 0; i < exp; i++) {
            result *= base;
        }
        return result;
    }

    /**
     * Iterative power that handles negative exponents (returns double).
     */
    public static double powerIterativeDouble(double base, int exp) {
        if (exp == 0) return 1.0;

        boolean negative = exp < 0;
        int absExp = Math.abs(exp);

        double result = 1.0;
        for (int i = 0; i < absExp; i++) {
            result *= base;
        }

        return negative ? 1.0 / result : result;
    }

    // ==================== METHOD 2: RECURSIVE ====================

    /**
     * Calculates base^exp using simple recursion.
     * Time: O(exp), Space: O(exp) due to call stack
     */
    public static long powerRecursive(long base, int exp) {
        // Base cases
        if (exp == 0) return 1;
        if (exp == 1) return base;

        // Recursive case
        return base * powerRecursive(base, exp - 1);
    }

    /**
     * Recursive power with negative exponent support.
     */
    public static double powerRecursiveDouble(double base, int exp) {
        if (exp == 0) return 1.0;
        if (exp < 0) return 1.0 / powerRecursiveDouble(base, -exp);
        return base * powerRecursiveDouble(base, exp - 1);
    }

    // ==================== METHOD 3: FAST EXPONENTIATION ====================

    /**
     * Calculates base^exp using exponentiation by squaring (recursive).
     * Key insight:
     *   - If exp is even: base^exp = (base^2)^(exp/2)
     *   - If exp is odd:  base^exp = base * (base^2)^((exp-1)/2)
     * Time: O(log exp), Space: O(log exp) stack
     */
    public static long fastPowerRecursive(long base, int exp) {
        if (exp == 0) return 1;
        if (exp == 1) return base;

        if (exp % 2 == 0) {
            // Even: base^exp = (base*base)^(exp/2)
            return fastPowerRecursive(base * base, exp / 2);
        } else {
            // Odd: base^exp = base * (base*base)^((exp-1)/2)
            return base * fastPowerRecursive(base * base, (exp - 1) / 2);
        }
    }

    /**
     * Calculates base^exp using exponentiation by squaring (iterative).
     * This is the most efficient version: O(log exp) time, O(1) space.
     */
    public static long fastPowerIterative(long base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Use fastPowerDouble for negative exponents");
        }

        long result = 1;
        long currentBase = base;

        while (exp > 0) {
            if ((exp & 1) == 1) { // exp is odd (check last bit)
                result *= currentBase;
            }
            currentBase *= currentBase; // Square the base
            exp >>= 1;                 // Divide exp by 2
        }

        return result;
    }

    /**
     * Fast exponentiation with negative exponent support.
     */
    public static double fastPowerDouble(double base, int exp) {
        if (exp == 0) return 1.0;
        boolean negative = exp < 0;
        long absExp = Math.abs((long) exp);

        double result = 1.0;
        double currentBase = base;

        while (absExp > 0) {
            if ((absExp & 1) == 1) {
                result *= currentBase;
            }
            currentBase *= currentBase;
            absExp >>= 1;
        }

        return negative ? 1.0 / result : result;
    }

    // ==================== MODULAR EXPONENTIATION ====================

    /**
     * Calculates (base^exp) % mod using fast exponentiation.
     * Essential for cryptography (RSA, Diffie-Hellman).
     * Time: O(log exp), Space: O(1)
     */
    public static long modularPower(long base, long exp, long mod) {
        if (mod == 1) return 0;

        long result = 1;
        base %= mod;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }

        return result;
    }

    // ==================== STEP-BY-STEP TRACER ====================

    /**
     * Traces the binary exponentiation process step by step.
     */
    public static void traceFastPower(long base, int exp) {
        System.out.printf("  Tracing %d^%d using binary exponentiation:%n", base, exp);
        System.out.printf("  Exponent %d in binary: %s%n", exp, Integer.toBinaryString(exp));

        long result = 1;
        long currentBase = base;
        int step = 1;
        int originalExp = exp;

        while (exp > 0) {
            boolean isOdd = (exp & 1) == 1;
            System.out.printf("    Step %d: exp=%d (bit=%d), base=%d, ", step, exp, exp & 1, currentBase);

            if (isOdd) {
                result *= currentBase;
                System.out.printf("result = result * %d = %d", currentBase, result);
            } else {
                System.out.printf("result unchanged = %d", result);
            }
            System.out.println();

            currentBase *= currentBase;
            exp >>= 1;
            step++;
        }

        System.out.printf("  Final: %d^%d = %d%n", base, originalExp, result);
    }

    /**
     * Counts the number of multiplications performed by each method.
     */
    public static void compareMultiplications(long base, int exp) {
        // Iterative: exactly (exp) multiplications
        int iterativeOps = exp;

        // Fast: at most 2*floor(log2(exp)) multiplications
        int fastOps = 0;
        int tempExp = exp;
        while (tempExp > 0) {
            if ((tempExp & 1) == 1) fastOps++; // multiply by base
            fastOps++;                          // square the base
            tempExp >>= 1;
        }
        fastOps--; // last squaring is wasted

        System.out.printf("  %d^%d: Iterative = %d multiplications, Fast = %d multiplications%n",
                base, exp, iterativeOps, fastOps);
    }

    public static void main(String[] args) {
        System.out.println("=== Program 28: Power Calculator ===\n");

        // ---- Comparison of all three methods ----
        System.out.println("--- Comparing All Three Methods ---");
        int[][] testCases = {{2, 10}, {3, 5}, {5, 0}, {7, 3}, {2, 20}, {1, 100}};

        System.out.printf("  %-10s %-15s %-15s %-18s %-18s%n",
                "base^exp", "Iterative", "Recursive", "Fast(Recursive)", "Fast(Iterative)");
        System.out.println("  " + "-".join("", java.util.Collections.nCopies(78, "-")).replace("-", "-"));
        // Simple separator
        System.out.println("  " + "─".repeat(78));

        for (int[] tc : testCases) {
            int base = tc[0], exp = tc[1];
            long r1 = powerIterative(base, exp);
            long r2 = powerRecursive(base, exp);
            long r3 = fastPowerRecursive(base, exp);
            long r4 = fastPowerIterative(base, exp);

            System.out.printf("  %-10s %-15d %-15d %-18d %-18d%n",
                    base + "^" + exp, r1, r2, r3, r4);
        }

        // ---- Negative exponents ----
        System.out.println("\n--- Negative Exponents ---");
        double[][] negTests = {{2, -3}, {5, -2}, {10, -4}, {3, -1}};
        for (double[] tc : negTests) {
            double base = tc[0];
            int exp = (int) tc[1];
            System.out.printf("  %.0f^%d = %.10f (Iterative) | %.10f (Recursive) | %.10f (Fast)%n",
                    base, exp,
                    powerIterativeDouble(base, exp),
                    powerRecursiveDouble(base, exp),
                    fastPowerDouble(base, exp));
        }

        // ---- Step-by-step trace ----
        System.out.println("\n--- Step-by-Step Trace of Fast Exponentiation ---");
        traceFastPower(2, 13);
        System.out.println();
        traceFastPower(3, 7);

        // ---- Multiplication count comparison ----
        System.out.println("\n--- Multiplication Count: Iterative vs Fast ---");
        int[] exponents = {5, 10, 20, 50, 100, 1000};
        for (int exp : exponents) {
            compareMultiplications(2, exp);
        }

        // ---- Modular exponentiation ----
        System.out.println("\n--- Modular Exponentiation: (base^exp) % mod ---");
        System.out.printf("  2^10 mod 1000   = %d%n", modularPower(2, 10, 1000));
        System.out.printf("  3^100 mod 1000  = %d%n", modularPower(3, 100, 1000));
        System.out.printf("  7^256 mod 13    = %d%n", modularPower(7, 256, 13));
        System.out.printf("  2^31 mod 1000000007 = %d%n", modularPower(2, 31, 1000000007L));

        // ---- Practical application: Fermat's Little Theorem ----
        System.out.println("\n--- Fermat's Little Theorem: a^(p-1) ≡ 1 (mod p) for prime p ---");
        long prime = 97;
        long[] bases = {2, 5, 13, 42, 88};
        for (long b : bases) {
            long result = modularPower(b, prime - 1, prime);
            System.out.printf("  %d^%d mod %d = %d %s%n",
                    b, prime - 1, prime, result,
                    result == 1 ? "✓ (confirms Fermat)" : "✗ (unexpected!)");
        }

        // ---- Interactive mode ----
        System.out.println("\n--- Interactive Mode ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter base and exponent (e.g., 2 10): ");
        if (scanner.hasNextLong()) {
            long base = scanner.nextLong();
            if (scanner.hasNextInt()) {
                int exp = scanner.nextInt();
                if (exp >= 0) {
                    System.out.printf("  Iterative:       %d^%d = %d%n", base, exp, powerIterative(base, exp));
                    System.out.printf("  Fast (iterative): %d^%d = %d%n", base, exp, fastPowerIterative(base, exp));
                }
                System.out.printf("  As double:       %.0f^%d = %.6f%n",
                        (double) base, exp, fastPowerDouble((double) base, exp));
            }
        }
        scanner.close();
    }
}
