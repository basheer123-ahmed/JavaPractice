/*
 * Program 36: Merge Sorted Arrays
 * 
 * Problem Statement:
 *   Merge two sorted arrays into a single sorted array using the two-pointer
 *   technique. The resulting array should maintain sorted order. Also handle
 *   edge cases: empty arrays, arrays of different sizes, and arrays with
 *   duplicate elements across both inputs.
 * 
 * Sample Input:
 *   arr1 = {1, 3, 5, 7, 9}
 *   arr2 = {2, 4, 6, 8, 10}
 * Sample Output:
 *   Merged: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
 * 
 * Explanation:
 *   The two-pointer technique uses one pointer for each input array, both
 *   starting at index 0. At each step, we compare the elements at both pointers,
 *   copy the smaller one into the result array, and advance that pointer.
 *   When one array is exhausted, we copy the remaining elements from the other.
 * 
 *   This is exactly the "merge" step used in Merge Sort. It runs in O(m + n)
 *   time and requires O(m + n) space for the result array.
 * 
 * Time Complexity: O(m + n) where m, n are the lengths of the two arrays
 * Space Complexity: O(m + n) for the merged result array
 */

import java.util.Arrays;

public class MergeSortedArrays {

    // ───────────────────────────────────────────────────
    // TWO-POINTER MERGE (with step-by-step trace)
    // ───────────────────────────────────────────────────
    public static int[] mergeSorted(int[] arr1, int[] arr2, boolean verbose) {
        int m = arr1.length;
        int n = arr2.length;
        int[] merged = new int[m + n];

        int i = 0;  // pointer for arr1
        int j = 0;  // pointer for arr2
        int k = 0;  // pointer for merged

        if (verbose) {
            System.out.println("  arr1: " + Arrays.toString(arr1));
            System.out.println("  arr2: " + Arrays.toString(arr2));
            System.out.println();
        }

        int step = 0;

        // Compare elements from both arrays
        while (i < m && j < n) {
            step++;
            if (arr1[i] <= arr2[j]) {
                merged[k] = arr1[i];
                if (verbose) {
                    System.out.printf("  Step %2d: arr1[%d]=%d <= arr2[%d]=%d → pick %d%n",
                            step, i, arr1[i], j, arr2[j], arr1[i]);
                }
                i++;
            } else {
                merged[k] = arr2[j];
                if (verbose) {
                    System.out.printf("  Step %2d: arr1[%d]=%d >  arr2[%d]=%d → pick %d%n",
                            step, i, arr1[i], j, arr2[j], arr2[j]);
                }
                j++;
            }
            k++;
        }

        // Copy remaining elements from arr1 (if any)
        while (i < m) {
            merged[k++] = arr1[i];
            if (verbose) {
                System.out.println("  Remaining from arr1: " + arr1[i]);
            }
            i++;
        }

        // Copy remaining elements from arr2 (if any)
        while (j < n) {
            merged[k++] = arr2[j];
            if (verbose) {
                System.out.println("  Remaining from arr2: " + arr2[j]);
            }
            j++;
        }

        return merged;
    }

    // ───────────────────────────────────────────────────
    // MERGE multiple sorted arrays (generalization)
    // Uses iterative pairwise merging
    // ───────────────────────────────────────────────────
    public static int[] mergeMultiple(int[]... arrays) {
        if (arrays.length == 0) return new int[0];

        int[] result = arrays[0];
        for (int i = 1; i < arrays.length; i++) {
            result = mergeSorted(result, arrays[i], false);
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("         PROGRAM 36 — MERGE SORTED ARRAYS");
        System.out.println("=".repeat(60));

        // ── Test Case 1: Equal-sized arrays ──
        System.out.println("\n─── Test Case 1: Equal-Sized Arrays ───\n");
        int[] a1 = {1, 3, 5, 7, 9};
        int[] b1 = {2, 4, 6, 8, 10};
        int[] merged1 = mergeSorted(a1, b1, true);
        System.out.println("\n  Merged: " + Arrays.toString(merged1));

        // ── Test Case 2: Different-sized arrays ──
        System.out.println("\n─── Test Case 2: Different-Sized Arrays ───\n");
        int[] a2 = {1, 5, 9, 10, 15, 20};
        int[] b2 = {2, 3, 8};
        int[] merged2 = mergeSorted(a2, b2, true);
        System.out.println("\n  Merged: " + Arrays.toString(merged2));

        // ── Test Case 3: Arrays with duplicates ──
        System.out.println("\n─── Test Case 3: Overlapping / Duplicate Elements ───\n");
        int[] a3 = {1, 3, 5, 5, 7};
        int[] b3 = {3, 4, 5, 6, 8};
        int[] merged3 = mergeSorted(a3, b3, true);
        System.out.println("\n  Merged: " + Arrays.toString(merged3));

        // ── Test Case 4: One empty array ──
        System.out.println("\n─── Test Case 4: One Empty Array ───\n");
        int[] a4 = {};
        int[] b4 = {1, 2, 3};
        int[] merged4 = mergeSorted(a4, b4, true);
        System.out.println("\n  Merged: " + Arrays.toString(merged4));

        // ── Test Case 5: Both empty ──
        System.out.println("\n─── Test Case 5: Both Empty ───\n");
        int[] merged5 = mergeSorted(new int[]{}, new int[]{}, true);
        System.out.println("  Merged: " + Arrays.toString(merged5));

        // ── Test Case 6: Non-overlapping ranges ──
        System.out.println("\n─── Test Case 6: Non-Overlapping Ranges ───\n");
        int[] a6 = {1, 2, 3};
        int[] b6 = {10, 20, 30};
        int[] merged6 = mergeSorted(a6, b6, true);
        System.out.println("\n  Merged: " + Arrays.toString(merged6));

        // ── Test Case 7: Merge multiple sorted arrays ──
        System.out.println("\n─── Test Case 7: Merge Multiple (3+) Sorted Arrays ───\n");
        int[] x = {1, 5, 10};
        int[] y = {2, 7, 11};
        int[] z = {3, 6, 12};
        System.out.println("  Array 1: " + Arrays.toString(x));
        System.out.println("  Array 2: " + Arrays.toString(y));
        System.out.println("  Array 3: " + Arrays.toString(z));
        int[] mergedMulti = mergeMultiple(x, y, z);
        System.out.println("  Merged:  " + Arrays.toString(mergedMulti));

        // ── Visual illustration ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  HOW TWO-POINTER MERGE WORKS:");
        System.out.println("=".repeat(60));
        System.out.println("  arr1:  [ 1 | 3 | 5 ]    arr2:  [ 2 | 4 | 6 ]");
        System.out.println("           ↑ i=0                    ↑ j=0");
        System.out.println("  Compare 1 vs 2 → pick 1, advance i");
        System.out.println("  Compare 3 vs 2 → pick 2, advance j");
        System.out.println("  Compare 3 vs 4 → pick 3, advance i");
        System.out.println("  Compare 5 vs 4 → pick 4, advance j");
        System.out.println("  Compare 5 vs 6 → pick 5, advance i");
        System.out.println("  arr1 exhausted → copy remaining {6}");
        System.out.println("  Result: [1, 2, 3, 4, 5, 6]");
        System.out.println("=".repeat(60));
    }
}
