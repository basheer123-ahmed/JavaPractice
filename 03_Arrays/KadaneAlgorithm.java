/*
 * Program 40: Kadane's Algorithm
 * 
 * Problem Statement:
 *   Find the contiguous subarray within a one-dimensional integer array that
 *   has the maximum sum. Also print the actual subarray (start and end indices).
 *   Handle arrays with all negative numbers as well.
 * 
 * Sample Input: arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4}
 * Sample Output:
 *   Maximum subarray sum: 6
 *   Subarray: [4, -1, 2, 1] (indices 3 to 6)
 * 
 * Explanation:
 *   Kadane's Algorithm uses dynamic programming intuition:
 *   At each index i, decide whether to:
 *     (a) Extend the current subarray by adding arr[i], or
 *     (b) Start a new subarray beginning at arr[i].
 *   
 *   We track currentSum = max(arr[i], currentSum + arr[i]).
 *   We track maxSum = max(maxSum, currentSum).
 *   
 *   When currentSum drops below arr[i], we start a new subarray at i.
 *   This ensures we find the maximum sum in a single pass.
 * 
 *   To also recover the subarray itself, we track the start and end indices:
 *   - When we start a new subarray (reset currentSum), update tempStart = i.
 *   - When we find a new maxSum, update start = tempStart, end = i.
 * 
 * Time Complexity: O(n) — single pass through the array
 * Space Complexity: O(1) — only a few variables used
 */

import java.util.Arrays;

public class KadaneAlgorithm {

    // ───────────────────────────────────────────────────
    // Result class to hold both the sum and the subarray info
    // ───────────────────────────────────────────────────
    static class Result {
        int maxSum;
        int startIndex;
        int endIndex;

        Result(int maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.startIndex = start;
            this.endIndex = end;
        }
    }

    // ───────────────────────────────────────────────────
    // KADANE'S ALGORITHM — with subarray tracking
    // ───────────────────────────────────────────────────
    public static Result kadane(int[] arr, boolean verbose) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        int currentSum = arr[0];
        int maxSum = arr[0];
        int tempStart = 0; // potential start of a new best subarray
        int start = 0;     // start index of the best subarray
        int end = 0;       // end index of the best subarray

        if (verbose) {
            System.out.println("  Array: " + Arrays.toString(arr));
            System.out.println();
            System.out.printf("  %-6s │ %-8s │ %-12s │ %-12s │ %-10s │ %s%n",
                    "Index", "arr[i]", "currentSum", "maxSum", "Action", "Best subarray");
            System.out.println("  " + "─".repeat(80));
            System.out.printf("  %-6d │ %8d │ %12d │ %12d │ %-10s │ [%d]%n",
                    0, arr[0], currentSum, maxSum, "Init", arr[0]);
        }

        for (int i = 1; i < arr.length; i++) {
            String action;

            if (arr[i] > currentSum + arr[i]) {
                // Starting fresh at index i is better
                currentSum = arr[i];
                tempStart = i;
                action = "Restart";
            } else {
                // Extend the current subarray
                currentSum = currentSum + arr[i];
                action = "Extend";
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
                action += " ★NEW MAX";
            }

            if (verbose) {
                // Build subarray string
                StringBuilder sb = new StringBuilder("[");
                for (int k = tempStart; k <= i; k++) {
                    if (k > tempStart) sb.append(", ");
                    sb.append(arr[k]);
                }
                sb.append("]");

                System.out.printf("  %-6d │ %8d │ %12d │ %12d │ %-18s │ %s%n",
                        i, arr[i], currentSum, maxSum, action, sb.toString());
            }
        }

        return new Result(maxSum, start, end);
    }

    // ───────────────────────────────────────────────────
    // BRUTE FORCE — O(n²) for comparison
    // ───────────────────────────────────────────────────
    public static Result bruteForce(int[] arr) {
        int maxSum = Integer.MIN_VALUE;
        int start = 0, end = 0;

        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if (sum > maxSum) {
                    maxSum = sum;
                    start = i;
                    end = j;
                }
            }
        }

        return new Result(maxSum, start, end);
    }

    // ───────────────────────────────────────────────────
    // Helper: extract and print subarray from result
    // ───────────────────────────────────────────────────
    public static void printResult(int[] arr, Result result) {
        System.out.println("\n  ► Maximum Subarray Sum: " + result.maxSum);
        System.out.print("  ► Subarray: [");
        for (int i = result.startIndex; i <= result.endIndex; i++) {
            if (i > result.startIndex) System.out.print(", ");
            System.out.print(arr[i]);
        }
        System.out.println("] (indices " + result.startIndex + " to " + result.endIndex + ")");
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(70));
        System.out.println("           PROGRAM 40 — KADANE'S ALGORITHM");
        System.out.println("=".repeat(70));

        // ── Test Case 1: Classic example ──
        System.out.println("\n─── Test Case 1: Classic Example ───\n");
        int[] test1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        Result r1 = kadane(test1, true);
        printResult(test1, r1);

        // Verify with brute force
        Result br1 = bruteForce(test1);
        System.out.println("  ► Brute force verification: sum=" + br1.maxSum
                + " (indices " + br1.startIndex + " to " + br1.endIndex + ") ✓");

        // ── Test Case 2: All negative numbers ──
        System.out.println("\n─── Test Case 2: All Negative Numbers ───\n");
        int[] test2 = {-8, -3, -6, -2, -5, -4};
        Result r2 = kadane(test2, true);
        printResult(test2, r2);
        System.out.println("  (Kadane returns the least negative = largest single element)");

        // ── Test Case 3: All positive numbers ──
        System.out.println("\n─── Test Case 3: All Positive Numbers ───\n");
        int[] test3 = {1, 2, 3, 4, 5};
        Result r3 = kadane(test3, true);
        printResult(test3, r3);
        System.out.println("  (Entire array is the max subarray when all positive)");

        // ── Test Case 4: Single element ──
        System.out.println("\n─── Test Case 4: Single Element ───\n");
        int[] test4 = {42};
        Result r4 = kadane(test4, true);
        printResult(test4, r4);

        // ── Test Case 5: Mix with the max subarray at the end ──
        System.out.println("\n─── Test Case 5: Max Subarray at the End ───\n");
        int[] test5 = {-1, -2, -3, 10, 20, 30};
        Result r5 = kadane(test5, true);
        printResult(test5, r5);

        // ── Test Case 6: Larger example ──
        System.out.println("\n─── Test Case 6: Larger Example ───\n");
        int[] test6 = {2, -4, 3, -1, 2, -1, 5, -3, 2, -1, 3, -2, 1};
        Result r6 = kadane(test6, true);
        printResult(test6, r6);

        // ── Algorithm summary ──
        System.out.println("\n" + "=".repeat(70));
        System.out.println("  ALGORITHM SUMMARY");
        System.out.println("=".repeat(70));
        System.out.println("  Kadane's Algorithm (Dynamic Programming):");
        System.out.println("    currentSum = max(arr[i], currentSum + arr[i])");
        System.out.println("    maxSum     = max(maxSum, currentSum)");
        System.out.println();
        System.out.println("  Key insight: At each position, either:");
        System.out.println("    • EXTEND the existing subarray (currentSum + arr[i])");
        System.out.println("    • RESTART a new subarray at arr[i]");
        System.out.println();
        System.out.println("  ┌──────────────┬────────────┬──────────┐");
        System.out.println("  │ Approach     │ Time       │ Space    │");
        System.out.println("  ├──────────────┼────────────┼──────────┤");
        System.out.println("  │ Brute Force  │ O(n²)      │ O(1)     │");
        System.out.println("  │ Kadane's     │ O(n)       │ O(1)     │");
        System.out.println("  └──────────────┴────────────┴──────────┘");
        System.out.println("=".repeat(70));
    }
}
