/*
 * Program 35: Second Largest and Second Smallest
 * 
 * Problem Statement:
 *   Find the second largest and second smallest elements in an array without
 *   sorting. Use a single-pass algorithm that tracks the top two and bottom two
 *   values simultaneously. Handle edge cases like arrays with all duplicates,
 *   arrays with fewer than 2 distinct elements, and negative numbers.
 * 
 * Sample Input: arr = {12, 35, 1, 10, 34, 1}
 * Sample Output:
 *   Array: [12, 35, 1, 10, 34, 1]
 *   Largest:         35
 *   Second Largest:  34
 *   Smallest:        1
 *   Second Smallest: 10
 * 
 * Explanation:
 *   We maintain four variables: largest, secondLargest, smallest, secondSmallest.
 *   In a single traversal of the array:
 *     - If current > largest → secondLargest = largest, largest = current
 *     - Else if current > secondLargest (and current != largest) → secondLargest = current
 *     - Similarly for smallest tracking.
 *   This avoids the O(n log n) cost of sorting. We use Integer.MIN_VALUE and
 *   Integer.MAX_VALUE as sentinel initial values.
 * 
 * Time Complexity: O(n) — single pass through the array
 * Space Complexity: O(1) — only a few variables used
 */

import java.util.Arrays;

public class SecondLargest {

    // ───────────────────────────────────────────────────
    // Find second largest element (single pass)
    // Returns Integer.MIN_VALUE if no valid second largest exists
    // ───────────────────────────────────────────────────
    public static int findSecondLargest(int[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException("Array must have at least 2 elements");
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }

        return secondLargest;
    }

    // ───────────────────────────────────────────────────
    // Find second smallest element (single pass)
    // Returns Integer.MAX_VALUE if no valid second smallest exists
    // ───────────────────────────────────────────────────
    public static int findSecondSmallest(int[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException("Array must have at least 2 elements");
        }

        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        for (int num : arr) {
            if (num < smallest) {
                secondSmallest = smallest;
                smallest = num;
            } else if (num < secondSmallest && num != smallest) {
                secondSmallest = num;
            }
        }

        return secondSmallest;
    }

    // ───────────────────────────────────────────────────
    // Combined: find all four values in a SINGLE pass
    // ───────────────────────────────────────────────────
    public static void findAllFour(int[] arr) {
        if (arr.length < 2) {
            System.out.println("  Error: Array must have at least 2 elements.");
            return;
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        System.out.println("  Array: " + Arrays.toString(arr));
        System.out.println("  Tracing through each element:");

        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];

            // Track largest and second largest
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }

            // Track smallest and second smallest
            if (num < smallest) {
                secondSmallest = smallest;
                smallest = num;
            } else if (num < secondSmallest && num != smallest) {
                secondSmallest = num;
            }

            System.out.printf("    After arr[%d]=%d → largest=%s, 2ndLargest=%s, smallest=%s, 2ndSmallest=%s%n",
                    i, num,
                    formatValue(largest), formatValue(secondLargest),
                    formatValue(smallest), formatValue(secondSmallest));
        }

        System.out.println("\n  Results:");
        System.out.println("    Largest:          " + largest);
        if (secondLargest == Integer.MIN_VALUE) {
            System.out.println("    Second Largest:   Does not exist (all elements are the same)");
        } else {
            System.out.println("    Second Largest:   " + secondLargest);
        }

        System.out.println("    Smallest:         " + smallest);
        if (secondSmallest == Integer.MAX_VALUE) {
            System.out.println("    Second Smallest:  Does not exist (all elements are the same)");
        } else {
            System.out.println("    Second Smallest:  " + secondSmallest);
        }
    }

    // Helper to display sentinel values clearly
    private static String formatValue(int val) {
        if (val == Integer.MIN_VALUE) return "MIN";
        if (val == Integer.MAX_VALUE) return "MAX";
        return String.valueOf(val);
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("     PROGRAM 35 — SECOND LARGEST & SECOND SMALLEST");
        System.out.println("=".repeat(60));

        // ── Test Case 1: Normal array ──
        System.out.println("\n─── Test Case 1: Normal Array ───\n");
        int[] test1 = {12, 35, 1, 10, 34, 1};
        findAllFour(test1);

        // ── Test Case 2: Array with negatives ──
        System.out.println("\n─── Test Case 2: Array with Negative Numbers ───\n");
        int[] test2 = {-5, -1, -10, -3, -7};
        findAllFour(test2);

        // ── Test Case 3: Array with duplicates ──
        System.out.println("\n─── Test Case 3: Array with Duplicates ───\n");
        int[] test3 = {7, 7, 7, 3, 3, 3, 10, 10};
        findAllFour(test3);

        // ── Test Case 4: All same elements ──
        System.out.println("\n─── Test Case 4: All Same Elements ───\n");
        int[] test4 = {5, 5, 5, 5};
        findAllFour(test4);

        // ── Test Case 5: Two elements ──
        System.out.println("\n─── Test Case 5: Two Elements ───\n");
        int[] test5 = {100, 50};
        findAllFour(test5);

        // ── Test Case 6: Sorted ascending ──
        System.out.println("\n─── Test Case 6: Sorted Ascending ───\n");
        int[] test6 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        findAllFour(test6);

        // ── Quick API usage ──
        System.out.println("\n─── Quick API Usage (without trace) ───\n");
        int[] data = {44, 22, 88, 66, 11, 99, 33};
        System.out.println("  Array:            " + Arrays.toString(data));
        System.out.println("  Second Largest:   " + findSecondLargest(data));
        System.out.println("  Second Smallest:  " + findSecondSmallest(data));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  Second Largest/Smallest demonstration complete!");
        System.out.println("=".repeat(60));
    }
}
