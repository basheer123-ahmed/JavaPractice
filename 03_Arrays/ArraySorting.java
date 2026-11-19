/*
 * Program 32: Array Sorting
 * 
 * Problem Statement:
 *   Implement Bubble Sort and Selection Sort on integer arrays. Show the
 *   step-by-step sorting process after each pass, including comparisons and
 *   swaps performed. Compare the two algorithms on the same input.
 * 
 * Sample Input: arr = {64, 34, 25, 12, 22, 11, 90}
 * Sample Output:
 *   === Bubble Sort ===
 *   Original: [64, 34, 25, 12, 22, 11, 90]
 *   Pass 1: [34, 25, 12, 22, 11, 64, 90]
 *   Pass 2: [25, 12, 22, 11, 34, 64, 90]
 *   ...
 *   Sorted:  [11, 12, 22, 25, 34, 64, 90]
 *   Comparisons: 21, Swaps: 13
 *   
 *   === Selection Sort ===
 *   Original: [64, 34, 25, 12, 22, 11, 90]
 *   Pass 1: [11, 34, 25, 12, 22, 64, 90]  (swapped 64 ↔ 11)
 *   ...
 *   Sorted:  [11, 12, 22, 25, 34, 64, 90]
 *   Comparisons: 21, Swaps: 5
 * 
 * Explanation:
 *   Bubble Sort repeatedly compares adjacent elements and swaps them if they
 *   are in the wrong order; the largest unsorted element "bubbles" to its
 *   correct position after each pass. An optimized version stops early if no
 *   swaps occur in a pass.
 * 
 *   Selection Sort divides the array into sorted and unsorted regions. In each
 *   pass, it finds the minimum element from the unsorted region and swaps it
 *   with the first unsorted element.
 * 
 *   Both are comparison-based, in-place, and have O(n²) worst-case time.
 *   Selection Sort does fewer swaps (at most n-1); Bubble Sort can detect
 *   already-sorted input in O(n) with the early-stop optimization.
 * 
 * Time Complexity:
 *   Bubble Sort   — Best: O(n) [optimized], Worst/Avg: O(n²)
 *   Selection Sort — Best/Worst/Avg: O(n²)
 * Space Complexity: O(1) for both (in-place)
 */

import java.util.Arrays;

public class ArraySorting {

    // ───────────────────────────────────────────────────
    // BUBBLE SORT (optimized with early-stop flag)
    // ───────────────────────────────────────────────────
    public static int[] bubbleSort(int[] original) {
        int[] arr = Arrays.copyOf(original, original.length);
        int n = arr.length;
        int comparisons = 0;
        int swaps = 0;

        System.out.println("=== Bubble Sort ===");
        System.out.println("Original: " + Arrays.toString(arr));

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    // Swap adjacent elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }

            System.out.println("Pass " + (i + 1) + ": " + Arrays.toString(arr)
                    + (swapped ? "" : "  [No swaps — already sorted!]"));

            // Optimization: if no swap occurred, array is sorted
            if (!swapped) {
                System.out.println("  → Early termination: no swaps in this pass.");
                break;
            }
        }

        System.out.println("Sorted:  " + Arrays.toString(arr));
        System.out.println("Comparisons: " + comparisons + ", Swaps: " + swaps);
        return arr;
    }

    // ───────────────────────────────────────────────────
    // SELECTION SORT
    // ───────────────────────────────────────────────────
    public static int[] selectionSort(int[] original) {
        int[] arr = Arrays.copyOf(original, original.length);
        int n = arr.length;
        int comparisons = 0;
        int swaps = 0;

        System.out.println("=== Selection Sort ===");
        System.out.println("Original: " + Arrays.toString(arr));

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Find the minimum element in the unsorted portion
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the found minimum with the first unsorted element
            String swapInfo;
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                swaps++;
                swapInfo = "  (swapped " + arr[minIndex] + " ↔ " + arr[i] + ")";
            } else {
                swapInfo = "  (no swap needed, " + arr[i] + " already in place)";
            }

            System.out.println("Pass " + (i + 1) + ": " + Arrays.toString(arr) + swapInfo);
        }

        System.out.println("Sorted:  " + Arrays.toString(arr));
        System.out.println("Comparisons: " + comparisons + ", Swaps: " + swaps);
        return arr;
    }

    // ───────────────────────────────────────────────────
    // MAIN — demonstrate both sorts
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("         PROGRAM 32 — ARRAY SORTING");
        System.out.println("=".repeat(60));

        // ── Test Case 1: Random order ──
        int[] test1 = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("\n─── Test Case 1: Random Order ───\n");
        bubbleSort(test1);
        System.out.println();
        selectionSort(test1);

        // ── Test Case 2: Already sorted (best case for Bubble Sort) ──
        int[] test2 = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("\n─── Test Case 2: Already Sorted ───\n");
        bubbleSort(test2);
        System.out.println();
        selectionSort(test2);

        // ── Test Case 3: Reverse sorted (worst case) ──
        int[] test3 = {7, 6, 5, 4, 3, 2, 1};
        System.out.println("\n─── Test Case 3: Reverse Sorted (Worst Case) ───\n");
        bubbleSort(test3);
        System.out.println();
        selectionSort(test3);

        // ── Test Case 4: Array with duplicates ──
        int[] test4 = {5, 3, 8, 3, 1, 5, 2};
        System.out.println("\n─── Test Case 4: Array with Duplicates ───\n");
        bubbleSort(test4);
        System.out.println();
        selectionSort(test4);

        // ── Comparison Summary ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  COMPARISON SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("  ┌──────────────────┬────────────────┬──────────────────┐");
        System.out.println("  │ Property         │ Bubble Sort    │ Selection Sort   │");
        System.out.println("  ├──────────────────┼────────────────┼──────────────────┤");
        System.out.println("  │ Best Time        │ O(n)           │ O(n²)            │");
        System.out.println("  │ Worst Time       │ O(n²)          │ O(n²)            │");
        System.out.println("  │ Space            │ O(1)           │ O(1)             │");
        System.out.println("  │ Stable?          │ Yes            │ No               │");
        System.out.println("  │ Adaptive?        │ Yes (optimized)│ No               │");
        System.out.println("  │ # Swaps (worst)  │ O(n²)          │ O(n)             │");
        System.out.println("  └──────────────────┴────────────────┴──────────────────┘");
        System.out.println("=".repeat(60));
    }
}
