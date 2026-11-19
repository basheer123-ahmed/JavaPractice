/*
 * Program 37: Duplicate Elements
 * 
 * Problem Statement:
 *   Given an integer array, perform two tasks:
 *   1. Find and display all duplicate elements along with their frequency.
 *   2. Remove duplicates and return an array of unique elements.
 *   Demonstrate approaches using: brute force, sorting, and hashing (HashMap).
 * 
 * Sample Input: arr = {4, 2, 7, 2, 4, 1, 7, 7, 3, 4}
 * Sample Output:
 *   === Frequency Count ===
 *   Element 1 → frequency: 1
 *   Element 2 → frequency: 2 (duplicate)
 *   Element 3 → frequency: 1
 *   Element 4 → frequency: 3 (duplicate)
 *   Element 7 → frequency: 3 (duplicate)
 *   
 *   === After Removing Duplicates ===
 *   Unique elements: [4, 2, 7, 1, 3]  (preserving first occurrence order)
 * 
 * Explanation:
 *   - Brute Force: For each element, count occurrences by scanning the array → O(n²).
 *   - Sorting: Sort first, then duplicates become adjacent → O(n log n).
 *   - HashMap: Count frequencies in O(n) time using a hash map → O(n).
 *   The HashMap approach is the most efficient for both tasks.
 * 
 * Time Complexity: O(n) using HashMap; O(n²) brute force; O(n log n) sorting
 * Space Complexity: O(n) for the HashMap / result arrays
 */

import java.util.*;

public class DuplicateElements {

    // ───────────────────────────────────────────────────
    // METHOD 1: BRUTE FORCE — O(n²)
    // ───────────────────────────────────────────────────
    public static void findDuplicatesBruteForce(int[] arr) {
        System.out.println("  [Brute Force Approach — O(n²)]");
        int n = arr.length;
        boolean[] visited = new boolean[n]; // track already-counted elements

        System.out.println("  Duplicates found:");
        boolean anyDuplicate = false;

        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;

            int count = 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] == arr[i]) {
                    count++;
                    visited[j] = true;
                }
            }

            if (count > 1) {
                System.out.println("    " + arr[i] + " appears " + count + " times");
                anyDuplicate = true;
            }
        }

        if (!anyDuplicate) {
            System.out.println("    None — all elements are unique.");
        }
    }

    // ───────────────────────────────────────────────────
    // METHOD 2: SORTING — O(n log n)
    // ───────────────────────────────────────────────────
    public static int[] removeDuplicatesSorting(int[] original) {
        System.out.println("  [Sorting Approach — O(n log n)]");
        int[] arr = original.clone();
        Arrays.sort(arr);
        System.out.println("  Sorted array: " + Arrays.toString(arr));

        // Count unique elements
        int uniqueCount = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                uniqueCount++;
            }
        }

        // Build unique array
        int[] unique = new int[uniqueCount];
        unique[0] = arr[0];
        int idx = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                unique[idx++] = arr[i];
            }
        }

        System.out.println("  Unique (sorted): " + Arrays.toString(unique));
        return unique;
    }

    // ───────────────────────────────────────────────────
    // METHOD 3: HASHMAP — O(n) — Best approach
    // ───────────────────────────────────────────────────
    public static void frequencyCountHashMap(int[] arr) {
        System.out.println("  [HashMap Approach — O(n)]");

        // LinkedHashMap preserves insertion order
        Map<Integer, Integer> freqMap = new LinkedHashMap<>();
        for (int num : arr) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        System.out.println("\n  Element Frequency Table:");
        System.out.println("  ┌─────────┬───────────┬────────────┐");
        System.out.println("  │ Element │ Frequency │ Status     │");
        System.out.println("  ├─────────┼───────────┼────────────┤");

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            String status = entry.getValue() > 1 ? "DUPLICATE" : "unique";
            System.out.printf("  │ %7d │ %9d │ %-10s │%n",
                    entry.getKey(), entry.getValue(), status);
        }
        System.out.println("  └─────────┴───────────┴────────────┘");
    }

    // ───────────────────────────────────────────────────
    // REMOVE DUPLICATES preserving first-occurrence order
    // ───────────────────────────────────────────────────
    public static int[] removeDuplicatesPreserveOrder(int[] arr) {
        Set<Integer> seen = new LinkedHashSet<>();
        for (int num : arr) {
            seen.add(num);  // LinkedHashSet ignores duplicates, keeps insertion order
        }

        int[] result = new int[seen.size()];
        int i = 0;
        for (int num : seen) {
            result[i++] = num;
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // FIND FIRST REPEATING ELEMENT
    // ───────────────────────────────────────────────────
    public static int findFirstRepeating(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        for (int num : arr) {
            if (seen.contains(num)) {
                return num;
            }
            seen.add(num);
        }
        return Integer.MIN_VALUE; // no repeating element
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("         PROGRAM 37 — DUPLICATE ELEMENTS");
        System.out.println("=".repeat(60));

        int[] arr = {4, 2, 7, 2, 4, 1, 7, 7, 3, 4};
        System.out.println("\nOriginal array: " + Arrays.toString(arr));

        // ── Task 1: Find duplicates ──
        System.out.println("\n━━━━━━━━ TASK 1: Find Duplicates ━━━━━━━━\n");

        findDuplicatesBruteForce(arr);
        System.out.println();
        frequencyCountHashMap(arr);

        // ── Task 2: Remove duplicates ──
        System.out.println("\n━━━━━━━━ TASK 2: Remove Duplicates ━━━━━━━━\n");

        removeDuplicatesSorting(arr);
        System.out.println();

        System.out.println("  [LinkedHashSet Approach — O(n), preserves order]");
        int[] unique = removeDuplicatesPreserveOrder(arr);
        System.out.println("  Unique (original order): " + Arrays.toString(unique));

        // ── Task 3: First repeating element ──
        System.out.println("\n━━━━━━━━ BONUS: First Repeating Element ━━━━━━━━\n");
        int firstRep = findFirstRepeating(arr);
        System.out.println("  Array: " + Arrays.toString(arr));
        if (firstRep != Integer.MIN_VALUE) {
            System.out.println("  First repeating element: " + firstRep);
        } else {
            System.out.println("  No repeating element found.");
        }

        // ── Additional test cases ──
        System.out.println("\n━━━━━━━━ Additional Test Cases ━━━━━━━━\n");

        // All unique
        int[] test2 = {1, 2, 3, 4, 5};
        System.out.println("  Test: All unique → " + Arrays.toString(test2));
        findDuplicatesBruteForce(test2);
        System.out.println("  After removal: " + Arrays.toString(removeDuplicatesPreserveOrder(test2)));

        System.out.println();

        // All same
        int[] test3 = {9, 9, 9, 9};
        System.out.println("  Test: All same → " + Arrays.toString(test3));
        findDuplicatesBruteForce(test3);
        System.out.println("  After removal: " + Arrays.toString(removeDuplicatesPreserveOrder(test3)));

        System.out.println();

        // Negative numbers
        int[] test4 = {-3, 1, -3, 2, 1, -3};
        System.out.println("  Test: With negatives → " + Arrays.toString(test4));
        frequencyCountHashMap(test4);
        System.out.println("  After removal: " + Arrays.toString(removeDuplicatesPreserveOrder(test4)));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  Duplicate Elements demonstration complete!");
        System.out.println("=".repeat(60));
    }
}
