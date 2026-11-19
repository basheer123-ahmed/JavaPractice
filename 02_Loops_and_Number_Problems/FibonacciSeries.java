/*
 * Program 18: Fibonacci Series
 * 
 * Problem Statement:
 *   Generate the first N Fibonacci numbers using iteration, basic recursion,
 *   and memoized recursion. The Fibonacci sequence is defined as:
 *     F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2) for n >= 2
 * 
 * Sample Input:  10
 * Sample Output:
 *   Iterative:  0 1 1 2 3 5 8 13 21 34
 *   Recursive:  0 1 1 2 3 5 8 13 21 34
 *   Memoized:   0 1 1 2 3 5 8 13 21 34
 * 
 * Explanation:
 *   - Iterative approach: Uses two variables to track the previous two values,
 *     runs in O(n) time and O(1) space (for single value) or O(n) for the series.
 *   - Naive recursion: Makes two recursive calls per step, resulting in O(2^n)
 *     time due to redundant computations.
 *   - Memoized recursion: Caches previously computed values in an array/map,
 *     reducing time to O(n) while using O(n) space.
 * 
 * Time Complexity:  O(n) iterative & memoized, O(2^n) naive recursive
 * Space Complexity: O(1) iterative (single value), O(n) memoized
 */

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class FibonacciSeries {

    // --- Method 1: Iterative approach ---
    public static void printFibonacciIterative(int n) {
        if (n <= 0) {
            System.out.println("N must be positive.");
            return;
        }

        System.out.print("Iterative:    ");
        long a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            long next = a + b;
            a = b;
            b = next;
        }
        System.out.println();
    }

    // --- Method 2: Get nth Fibonacci number iteratively ---
    public static long fibIterative(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            long next = a + b;
            a = b;
            b = next;
        }
        return b;
    }

    // --- Method 3: Naive recursive approach ---
    // WARNING: Exponential time — only use for small n (< 35)
    public static long fibRecursive(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    public static void printFibonacciRecursive(int n) {
        System.out.print("Recursive:    ");
        for (int i = 0; i < n; i++) {
            System.out.print(fibRecursive(i) + " ");
        }
        System.out.println();
    }

    // --- Method 4: Memoized recursive approach (top-down DP) ---
    private static Map<Integer, Long> memo = new HashMap<>();

    public static long fibMemoized(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;

        // Check if already computed
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        // Compute and cache
        long result = fibMemoized(n - 1) + fibMemoized(n - 2);
        memo.put(n, result);
        return result;
    }

    public static void printFibonacciMemoized(int n) {
        memo.clear(); // Clear cache for a fresh start
        System.out.print("Memoized:     ");
        for (int i = 0; i < n; i++) {
            System.out.print(fibMemoized(i) + " ");
        }
        System.out.println();
    }

    // --- Method 5: Array-based memoization (bottom-up DP / tabulation) ---
    public static long[] fibTabulation(int n) {
        if (n <= 0) return new long[]{};
        if (n == 1) return new long[]{0};

        long[] dp = new long[n];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp;
    }

    public static void printFibonacciTabulation(int n) {
        System.out.print("Tabulation:   ");
        long[] series = fibTabulation(n);
        for (long val : series) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    // --- Method 6: Check if a number is a Fibonacci number ---
    // A number is Fibonacci if 5*n*n + 4 or 5*n*n - 4 is a perfect square
    public static boolean isFibonacci(long num) {
        if (num < 0) return false;
        long check1 = 5 * num * num + 4;
        long check2 = 5 * num * num - 4;
        return isPerfectSquare(check1) || isPerfectSquare(check2);
    }

    private static boolean isPerfectSquare(long n) {
        if (n < 0) return false;
        long sqrt = (long) Math.sqrt(n);
        return sqrt * sqrt == n;
    }

    // --- Method 7: Find the index of a Fibonacci number ---
    public static int fibIndex(long target) {
        if (target < 0) return -1;
        if (target == 0) return 0;
        if (target == 1) return 1; // Returns the first occurrence (index 1, though index 2 is also 1)

        long a = 0, b = 1;
        int index = 1;
        while (b < target) {
            long next = a + b;
            a = b;
            b = next;
            index++;
        }
        return (b == target) ? index : -1;
    }

    // --- Method 8: Performance comparison ---
    public static void performanceComparison(int n) {
        System.out.println("\n=== Performance Comparison (computing F(" + n + ")) ===");

        // Iterative
        long startTime = System.nanoTime();
        long iterResult = fibIterative(n);
        long iterTime = System.nanoTime() - startTime;
        System.out.println("Iterative: F(" + n + ") = " + iterResult 
                           + "  Time: " + iterTime + " ns");

        // Memoized
        memo.clear();
        startTime = System.nanoTime();
        long memoResult = fibMemoized(n);
        long memoTime = System.nanoTime() - startTime;
        System.out.println("Memoized:  F(" + n + ") = " + memoResult 
                           + "  Time: " + memoTime + " ns");

        // Naive recursive (only for small n, otherwise way too slow)
        if (n <= 35) {
            startTime = System.nanoTime();
            long recResult = fibRecursive(n);
            long recTime = System.nanoTime() - startTime;
            System.out.println("Recursive: F(" + n + ") = " + recResult 
                               + "  Time: " + recTime + " ns");
        } else {
            System.out.println("Recursive: SKIPPED (too slow for n > 35)");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Generate first N Fibonacci numbers ---
        System.out.print("Enter how many Fibonacci numbers to generate: ");
        int n = scanner.nextInt();

        System.out.println("\n=== First " + n + " Fibonacci Numbers ===");
        printFibonacciIterative(n);
        if (n <= 35) {
            printFibonacciRecursive(n);
        } else {
            System.out.println("Recursive:    SKIPPED (too slow for n > 35)");
        }
        printFibonacciMemoized(n);
        printFibonacciTabulation(n);

        // --- Demo 2: Get a specific Fibonacci number ---
        System.out.println("\n=== Specific Fibonacci Numbers ===");
        int[] indices = {0, 1, 5, 10, 20, 30, 40, 50};
        for (int i : indices) {
            System.out.println("F(" + i + ") = " + fibIterative(i));
        }

        // --- Demo 3: Check if numbers are Fibonacci ---
        System.out.println("\n=== Is It a Fibonacci Number? ===");
        long[] testNumbers = {0, 1, 2, 3, 4, 5, 8, 10, 13, 21, 34, 50, 55, 100, 144};
        for (long num : testNumbers) {
            boolean isFib = isFibonacci(num);
            String indexInfo = isFib ? " (index: " + fibIndex(num) + ")" : "";
            System.out.println(num + " -> " + (isFib ? "YES" : "NO") + indexInfo);
        }

        // --- Demo 4: Performance comparison ---
        performanceComparison(30);
        performanceComparison(40);

        // --- Demo 5: Fibonacci properties ---
        System.out.println("\n=== Fibonacci Properties ===");
        System.out.println("Golden Ratio Approximation (F(n)/F(n-1)):");
        for (int i = 2; i <= 20; i++) {
            double ratio = (double) fibIterative(i) / fibIterative(i - 1);
            System.out.printf("  F(%2d)/F(%2d) = %.10f%n", i, i - 1, ratio);
        }
        System.out.println("  Actual φ    = " + ((1 + Math.sqrt(5)) / 2));

        scanner.close();
    }
}
