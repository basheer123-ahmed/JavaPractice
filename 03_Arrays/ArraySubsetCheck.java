/*
 * Program 39: Array Subset Check
 * 
 * Problem Statement:
 *   Check if one array is a subset of another. Array B is a subset of Array A
 *   if every element of B exists in A (accounting for duplicates/frequency).
 *   Implement two approaches:
 *   1. Sorting + Two-Pointer / Binary Search — O(n log n + m log m) or O((n+m) log n)
 *   2. Hashing (HashMap with frequency counting) — O(n + m)
 * 
 * Sample Input:
 *   A = {11, 1, 13, 21, 3, 7}
 *   B = {11, 3, 7, 1}
 * Sample Output:
 *   B is a SUBSET of A ✓
 * 
 *   A = {1, 2, 3, 4, 5}
 *   B = {1, 2, 6}
 *   B is NOT a subset of A ✗ (element 6 not found in A)
 * 
 * Explanation:
 *   Sorting approach: Sort both arrays. Use two pointers — one for each array.
 *   If elements match, advance both; if A's element is smaller, advance A's
 *   pointer; otherwise B has an element not in A → not a subset.
 * 
 *   Hashing approach: Build a frequency map of A. For each element in B,
 *   check if it exists in the map with remaining count > 0.
 * 
 * Time Complexity: O(n + m) for hashing; O(n log n + m log m) for sorting
 * Space Complexity: O(n) for the hash map; O(1) extra for sorting (in-place)
 */

import java.util.*;

public class ArraySubsetCheck {

    // ───────────────────────────────────────────────────
    // METHOD 1: SORTING + TWO-POINTER
    // ───────────────────────────────────────────────────
    public static boolean isSubsetSorting(int[] a, int[] b) {
        int[] sortedA = a.clone();
        int[] sortedB = b.clone();
        Arrays.sort(sortedA);
        Arrays.sort(sortedB);

        System.out.println("    Sorted A: " + Arrays.toString(sortedA));
        System.out.println("    Sorted B: " + Arrays.toString(sortedB));

        int i = 0; // pointer for A
        int j = 0; // pointer for B

        while (i < sortedA.length && j < sortedB.length) {
            if (sortedA[i] == sortedB[j]) {
                System.out.println("    Match: A[" + i + "]=" + sortedA[i] + " == B[" + j + "]=" + sortedB[j]);
                i++;
                j++;
            } else if (sortedA[i] < sortedB[j]) {
                System.out.println("    Skip:  A[" + i + "]=" + sortedA[i] + " < B[" + j + "]=" + sortedB[j] + " → advance A");
                i++;
            } else {
                System.out.println("    FAIL:  A[" + i + "]=" + sortedA[i] + " > B[" + j + "]=" + sortedB[j]
                        + " → element " + sortedB[j] + " not in A!");
                return false;
            }
        }

        // If all elements of B were matched
        return j == sortedB.length;
    }

    // ───────────────────────────────────────────────────
    // METHOD 2: HASHING (HashMap with frequency)
    //   Handles duplicates correctly
    // ───────────────────────────────────────────────────
    public static boolean isSubsetHashing(int[] a, int[] b) {
        // Build frequency map for A
        Map<Integer, Integer> freqA = new HashMap<>();
        for (int num : a) {
            freqA.put(num, freqA.getOrDefault(num, 0) + 1);
        }

        System.out.println("    Frequency map of A: " + freqA);

        // Check each element of B against A's frequency map
        for (int num : b) {
            int count = freqA.getOrDefault(num, 0);
            if (count == 0) {
                System.out.println("    Element " + num + " from B: NOT found (or exhausted) in A → NOT subset");
                return false;
            }
            freqA.put(num, count - 1);
            System.out.println("    Element " + num + " from B: found (remaining count in A: " + (count - 1) + ")");
        }

        return true;
    }

    // ───────────────────────────────────────────────────
    // HELPER: Run both methods and display result
    // ───────────────────────────────────────────────────
    public static void checkSubset(int[] a, int[] b, String labelA, String labelB) {
        System.out.println("  " + labelA + " = " + Arrays.toString(a));
        System.out.println("  " + labelB + " = " + Arrays.toString(b));

        // Sorting approach
        System.out.println("\n  [Sorting + Two-Pointer]");
        boolean result1 = isSubsetSorting(a, b);
        System.out.println("    Result: " + labelB + (result1 ? " IS a subset of " : " is NOT a subset of ") + labelA
                + (result1 ? " ✓" : " ✗"));

        // Hashing approach
        System.out.println("\n  [Hashing (HashMap)]");
        boolean result2 = isSubsetHashing(a, b);
        System.out.println("    Result: " + labelB + (result2 ? " IS a subset of " : " is NOT a subset of ") + labelA
                + (result2 ? " ✓" : " ✗"));

        System.out.println();
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("       PROGRAM 39 — ARRAY SUBSET CHECK");
        System.out.println("=".repeat(60));

        // ── Test Case 1: B is a subset of A ──
        System.out.println("\n─── Test Case 1: B IS a subset ───\n");
        int[] a1 = {11, 1, 13, 21, 3, 7};
        int[] b1 = {11, 3, 7, 1};
        checkSubset(a1, b1, "A", "B");

        // ── Test Case 2: B is NOT a subset ──
        System.out.println("─── Test Case 2: B is NOT a subset ───\n");
        int[] a2 = {1, 2, 3, 4, 5};
        int[] b2 = {1, 2, 6};
        checkSubset(a2, b2, "A", "B");

        // ── Test Case 3: Duplicate handling ──
        System.out.println("─── Test Case 3: Duplicate handling ───\n");
        int[] a3 = {1, 2, 2, 3, 3, 3};
        int[] b3 = {2, 3, 3};
        checkSubset(a3, b3, "A", "B");

        // ── Test Case 4: B has more duplicates than A ──
        System.out.println("─── Test Case 4: B has more duplicates than A ───\n");
        int[] a4 = {1, 2, 3};
        int[] b4 = {1, 2, 2}; // A only has one 2
        checkSubset(a4, b4, "A", "B");

        // ── Test Case 5: Empty subset ──
        System.out.println("─── Test Case 5: Empty B (empty set is subset of any set) ───\n");
        int[] a5 = {1, 2, 3};
        int[] b5 = {};
        checkSubset(a5, b5, "A", "B");

        // ── Test Case 6: B equals A ──
        System.out.println("─── Test Case 6: B equals A ───\n");
        int[] a6 = {5, 10, 15};
        int[] b6 = {15, 10, 5};
        checkSubset(a6, b6, "A", "B");

        // ── Test Case 7: Single element ──
        System.out.println("─── Test Case 7: Single element arrays ───\n");
        int[] a7 = {42};
        int[] b7 = {42};
        checkSubset(a7, b7, "A", "B");

        // ── Complexity Comparison ──
        System.out.println("=".repeat(60));
        System.out.println("  APPROACH COMPARISON (A has n elements, B has m)");
        System.out.println("=".repeat(60));
        System.out.println("  ┌───────────────────┬──────────────────┬─────────────┐");
        System.out.println("  │ Approach          │ Time Complexity  │ Space       │");
        System.out.println("  ├───────────────────┼──────────────────┼─────────────┤");
        System.out.println("  │ Brute Force       │ O(n × m)         │ O(1)        │");
        System.out.println("  │ Sorting + 2-Ptr   │ O(nlogn + mlogm) │ O(1)*       │");
        System.out.println("  │ Hashing (HashMap) │ O(n + m)         │ O(n)        │");
        System.out.println("  └───────────────────┴──────────────────┴─────────────┘");
        System.out.println("  * O(1) extra if sorting in-place; O(n+m) if cloning.");
        System.out.println("=".repeat(60));
    }
}
