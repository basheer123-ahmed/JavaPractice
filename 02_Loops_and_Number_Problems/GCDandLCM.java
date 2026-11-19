/*
 * Program 22: GCD and LCM
 * 
 * Problem Statement:
 *   Find the Greatest Common Divisor (GCD) using the Euclidean algorithm and
 *   the Least Common Multiple (LCM) of two numbers. The GCD is the largest
 *   number that divides both, and LCM is the smallest number divisible by both.
 *   The key relationship: LCM(a, b) = |a * b| / GCD(a, b)
 * 
 * Sample Input:  a = 12, b = 18
 * Sample Output:
 *   GCD(12, 18) = 6
 *   LCM(12, 18) = 36
 * 
 * Explanation:
 *   Euclidean Algorithm: GCD(a, b) = GCD(b, a % b) until b == 0, then GCD = a.
 *   Example: GCD(12, 18) -> GCD(18, 12) -> GCD(12, 6) -> GCD(6, 0) = 6
 *   LCM formula: LCM(12, 18) = (12 * 18) / 6 = 216 / 6 = 36
 * 
 * Time Complexity:  O(log(min(a, b))) for Euclidean algorithm
 * Space Complexity: O(1) iterative, O(log(min(a, b))) recursive (call stack)
 */

import java.util.Scanner;
import java.util.Arrays;

public class GCDandLCM {

    // --- Method 1: GCD using Euclidean algorithm (iterative) ---
    public static int gcdIterative(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // --- Method 2: GCD using Euclidean algorithm (recursive) ---
    public static int gcdRecursive(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (b == 0) return a;
        return gcdRecursive(b, a % b);
    }

    // --- Method 3: GCD using subtraction (original Euclid's method) ---
    // Less efficient than modular arithmetic but historically significant
    public static int gcdSubtraction(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a == 0) return b;
        if (b == 0) return a;
        while (a != b) {
            if (a > b) {
                a -= b;
            } else {
                b -= a;
            }
        }
        return a;
    }

    // --- Method 4: GCD (naive brute force for educational comparison) ---
    public static int gcdNaive(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        int min = Math.min(a, b);
        int gcd = 1;
        for (int i = 1; i <= min; i++) {
            if (a % i == 0 && b % i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }

    // --- Method 5: LCM using GCD ---
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) return 0;
        // Use long to prevent intermediate overflow
        return (int) ((long) Math.abs(a) / gcdIterative(a, b) * Math.abs(b));
    }

    // --- Method 6: GCD of multiple numbers ---
    public static int gcdMultiple(int... numbers) {
        if (numbers.length == 0) return 0;
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result = gcdIterative(result, numbers[i]);
            if (result == 1) return 1; // Early termination: GCD can't go below 1
        }
        return result;
    }

    // --- Method 7: LCM of multiple numbers ---
    public static long lcmMultiple(int... numbers) {
        if (numbers.length == 0) return 0;
        long result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result = lcmOfTwo(result, numbers[i]);
        }
        return result;
    }

    private static long lcmOfTwo(long a, long b) {
        if (a == 0 || b == 0) return 0;
        a = Math.abs(a);
        b = Math.abs(b);
        return a / gcdLong(a, b) * b;
    }

    private static long gcdLong(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // --- Method 8: Extended Euclidean Algorithm ---
    // Finds x, y such that a*x + b*y = GCD(a, b) (Bézout's identity)
    // Returns int[] {gcd, x, y}
    public static int[] extendedGcd(int a, int b) {
        if (b == 0) {
            return new int[]{a, 1, 0}; // gcd = a, x = 1, y = 0
        }
        int[] result = extendedGcd(b, a % b);
        int gcd = result[0];
        int x = result[2];
        int y = result[1] - (a / b) * result[2];
        return new int[]{gcd, x, y};
    }

    // --- Method 9: Check if two numbers are co-prime ---
    public static boolean areCoPrime(int a, int b) {
        return gcdIterative(a, b) == 1;
    }

    // --- Method 10: Explain GCD computation step by step ---
    public static void explainGcd(int a, int b) {
        System.out.println("GCD(" + a + ", " + b + ") step by step:");
        a = Math.abs(a);
        b = Math.abs(b);
        int step = 0;
        while (b != 0) {
            step++;
            int remainder = a % b;
            System.out.println("  Step " + step + ": GCD(" + a + ", " + b + ") -> " 
                               + a + " = " + (a / b) + " × " + b + " + " + remainder
                               + " -> GCD(" + b + ", " + remainder + ")");
            a = b;
            b = remainder;
        }
        System.out.println("  Result: GCD = " + a + " (reached remainder 0)\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Basic GCD and LCM ---
        System.out.print("Enter first number: ");
        int a = scanner.nextInt();
        System.out.print("Enter second number: ");
        int b = scanner.nextInt();

        System.out.println("\n=== GCD and LCM of " + a + " and " + b + " ===");
        System.out.println("GCD (iterative):   " + gcdIterative(a, b));
        System.out.println("GCD (recursive):   " + gcdRecursive(a, b));
        System.out.println("GCD (subtraction): " + gcdSubtraction(a, b));
        System.out.println("GCD (naive):       " + gcdNaive(a, b));
        System.out.println("LCM:               " + lcm(a, b));

        // Verify the relationship: a * b = GCD * LCM
        System.out.println("\nVerification: |a × b| = GCD × LCM");
        long product = (long) Math.abs(a) * Math.abs(b);
        long gcdLcmProduct = (long) gcdIterative(a, b) * lcm(a, b);
        System.out.println("|" + a + " × " + b + "| = " + product);
        System.out.println("GCD × LCM = " + gcdIterative(a, b) + " × " + lcm(a, b) + " = " + gcdLcmProduct);
        System.out.println("Equal? " + (product == gcdLcmProduct));

        // --- Demo 2: Step-by-step explanation ---
        System.out.println("\n=== Step-by-Step Euclidean Algorithm ===");
        explainGcd(a, b);
        explainGcd(48, 18);
        explainGcd(252, 105);

        // --- Demo 3: Extended Euclidean Algorithm ---
        System.out.println("=== Extended Euclidean Algorithm ===");
        int[][] extTests = {{12, 18}, {35, 15}, {252, 105}, {17, 13}};
        for (int[] test : extTests) {
            int[] result = extendedGcd(test[0], test[1]);
            System.out.println("Extended GCD(" + test[0] + ", " + test[1] + "):");
            System.out.println("  GCD = " + result[0]);
            System.out.println("  Bézout coefficients: x = " + result[1] + ", y = " + result[2]);
            System.out.println("  Verify: " + test[0] + " × " + result[1] + " + " 
                               + test[1] + " × " + result[2] + " = " 
                               + (test[0] * result[1] + test[1] * result[2]));
            System.out.println();
        }

        // --- Demo 4: GCD and LCM of multiple numbers ---
        System.out.println("=== GCD and LCM of Multiple Numbers ===");
        int[] multipleNums = {12, 18, 24, 36};
        System.out.println("Numbers: " + Arrays.toString(multipleNums));
        System.out.println("GCD: " + gcdMultiple(multipleNums));
        System.out.println("LCM: " + lcmMultiple(multipleNums));

        int[] multipleNums2 = {4, 6, 8, 10, 12};
        System.out.println("\nNumbers: " + Arrays.toString(multipleNums2));
        System.out.println("GCD: " + gcdMultiple(multipleNums2));
        System.out.println("LCM: " + lcmMultiple(multipleNums2));

        // --- Demo 5: Co-prime pairs ---
        System.out.println("\n=== Co-prime Check ===");
        int[][] coPrimeTests = {{3, 7}, {4, 9}, {6, 10}, {15, 28}, {12, 18}};
        for (int[] pair : coPrimeTests) {
            System.out.println("(" + pair[0] + ", " + pair[1] + ") are " 
                               + (areCoPrime(pair[0], pair[1]) ? "co-prime" : "NOT co-prime")
                               + " (GCD = " + gcdIterative(pair[0], pair[1]) + ")");
        }

        // --- Demo 6: Edge cases ---
        System.out.println("\n=== Edge Cases ===");
        System.out.println("GCD(0, 5) = " + gcdIterative(0, 5));
        System.out.println("GCD(5, 0) = " + gcdIterative(5, 0));
        System.out.println("GCD(0, 0) = " + gcdIterative(0, 0));
        System.out.println("GCD(-12, 18) = " + gcdIterative(-12, 18));
        System.out.println("LCM(0, 5) = " + lcm(0, 5));
        System.out.println("LCM(1, 7) = " + lcm(1, 7));

        // --- Demo 7: Table of GCD/LCM values ---
        System.out.println("\n=== GCD/LCM Table ===");
        System.out.printf("%-6s %-6s %-6s %-8s%n", "a", "b", "GCD", "LCM");
        System.out.println("-".repeat(28));
        int[][] tableTests = {
            {4, 6}, {12, 8}, {15, 25}, {7, 13}, {24, 36},
            {100, 75}, {17, 19}, {48, 18}, {56, 42}
        };
        for (int[] pair : tableTests) {
            System.out.printf("%-6d %-6d %-6d %-8d%n", 
                              pair[0], pair[1], 
                              gcdIterative(pair[0], pair[1]), 
                              lcm(pair[0], pair[1]));
        }

        scanner.close();
    }
}
