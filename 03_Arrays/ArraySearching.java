/*
 * Program 33: Array Searching
 * 
 * Problem Statement:
 *   Implement Linear Search and Binary Search on an integer array.
 *   Count the number of comparisons made during each search to illustrate
 *   their efficiency differences. Binary Search requires a sorted array.
 * 
 * Sample Input: arr = {2, 5, 8, 12, 16, 23, 38, 42, 56, 72, 91}
 *               target = 23
 * Sample Output:
 *   === Linear Search ===
 *   Searching for 23 in [2, 5, 8, 12, 16, 23, 38, 42, 56, 72, 91]
 *   Comparison 1: arr[0]=2 ≠ 23
 *   Comparison 2: arr[1]=5 ≠ 23
 *   ...
 *   Comparison 6: arr[5]=23 == 23  ✓ FOUND!
 *   Result: Found at index 5 (6 comparisons)
 *   
 *   === Binary Search ===
 *   Searching for 23 in [2, 5, 8, 12, 16, 23, 38, 42, 56, 72, 91]
 *   Step 1: low=0, high=10, mid=5, arr[5]=23 == 23  ✓ FOUND!
 *   Result: Found at index 5 (1 comparison)
 * 
 * Explanation:
 *   Linear Search checks each element sequentially — simple but O(n).
 *   Binary Search exploits sorted order by halving the search space each
 *   step — much faster at O(log n), but requires the array to be sorted.
 * 
 * Time Complexity:
 *   Linear Search — Best: O(1), Worst/Avg: O(n)
 *   Binary Search — Best: O(1), Worst/Avg: O(log n)
 * Space Complexity: O(1) for both (iterative implementations)
 */

import java.util.Arrays;

public class ArraySearching {

    // ───────────────────────────────────────────────────
    // LINEAR SEARCH — scan every element
    // ───────────────────────────────────────────────────
    public static int linearSearch(int[] arr, int target, boolean verbose) {
        int comparisons = 0;

        if (verbose) {
            System.out.println("Searching for " + target + " in " + Arrays.toString(arr));
        }

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (verbose) {
                String symbol = (arr[i] == target) ? "== " + target + "  ✓ FOUND!" : "≠ " + target;
                System.out.println("  Comparison " + comparisons + ": arr[" + i + "]=" + arr[i] + " " + symbol);
            }
            if (arr[i] == target) {
                if (verbose) {
                    System.out.println("Result: Found at index " + i + " (" + comparisons + " comparisons)");
                }
                return i;
            }
        }

        if (verbose) {
            System.out.println("Result: NOT found (" + comparisons + " comparisons)");
        }
        return -1;
    }

    // ───────────────────────────────────────────────────
    // BINARY SEARCH — iterative, requires sorted array
    // ───────────────────────────────────────────────────
    public static int binarySearch(int[] arr, int target, boolean verbose) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;
        int step = 0;

        if (verbose) {
            System.out.println("Searching for " + target + " in " + Arrays.toString(arr));
        }

        while (low <= high) {
            int mid = low + (high - low) / 2;  // avoids integer overflow
            comparisons++;
            step++;

            if (verbose) {
                System.out.print("  Step " + step + ": low=" + low + ", high=" + high
                        + ", mid=" + mid + ", arr[" + mid + "]=" + arr[mid]);
            }

            if (arr[mid] == target) {
                if (verbose) {
                    System.out.println(" == " + target + "  ✓ FOUND!");
                    System.out.println("Result: Found at index " + mid + " (" + comparisons + " comparisons)");
                }
                return mid;
            } else if (arr[mid] < target) {
                if (verbose) {
                    System.out.println(" < " + target + " → search RIGHT half");
                }
                low = mid + 1;
            } else {
                if (verbose) {
                    System.out.println(" > " + target + " → search LEFT half");
                }
                high = mid - 1;
            }
        }

        if (verbose) {
            System.out.println("Result: NOT found (" + comparisons + " comparisons)");
        }
        return -1;
    }

    // ───────────────────────────────────────────────────
    // BINARY SEARCH — recursive version
    // ───────────────────────────────────────────────────
    public static int binarySearchRecursive(int[] arr, int target, int low, int high, int depth) {
        if (low > high) {
            System.out.println("  " + "  ".repeat(depth) + "Base case: low(" + low + ") > high(" + high + ") → NOT FOUND");
            return -1;
        }

        int mid = low + (high - low) / 2;
        System.out.print("  " + "  ".repeat(depth) + "Check mid=" + mid + ", arr[" + mid + "]=" + arr[mid]);

        if (arr[mid] == target) {
            System.out.println(" == " + target + "  ✓ FOUND!");
            return mid;
        } else if (arr[mid] < target) {
            System.out.println(" < " + target + " → recurse RIGHT");
            return binarySearchRecursive(arr, target, mid + 1, high, depth + 1);
        } else {
            System.out.println(" > " + target + " → recurse LEFT");
            return binarySearchRecursive(arr, target, low, mid - 1, depth + 1);
        }
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("         PROGRAM 33 — ARRAY SEARCHING");
        System.out.println("=".repeat(60));

        int[] sortedArr = {2, 5, 8, 12, 16, 23, 38, 42, 56, 72, 91};

        // ── Test Case 1: Search for existing element ──
        int target1 = 23;
        System.out.println("\n─── Test Case 1: Search for " + target1 + " (exists) ───\n");

        System.out.println("=== Linear Search ===");
        linearSearch(sortedArr, target1, true);
        System.out.println();
        System.out.println("=== Binary Search (Iterative) ===");
        binarySearch(sortedArr, target1, true);

        // ── Test Case 2: Search for non-existing element ──
        int target2 = 50;
        System.out.println("\n─── Test Case 2: Search for " + target2 + " (does NOT exist) ───\n");

        System.out.println("=== Linear Search ===");
        linearSearch(sortedArr, target2, true);
        System.out.println();
        System.out.println("=== Binary Search (Iterative) ===");
        binarySearch(sortedArr, target2, true);

        // ── Test Case 3: Search for first and last elements ──
        System.out.println("\n─── Test Case 3: Edge Cases (first/last element) ───\n");

        System.out.println("=== Binary Search for FIRST element (" + sortedArr[0] + ") ===");
        binarySearch(sortedArr, sortedArr[0], true);
        System.out.println();
        System.out.println("=== Binary Search for LAST element (" + sortedArr[sortedArr.length - 1] + ") ===");
        binarySearch(sortedArr, sortedArr[sortedArr.length - 1], true);

        // ── Test Case 4: Recursive Binary Search ──
        int target4 = 42;
        System.out.println("\n─── Test Case 4: Recursive Binary Search for " + target4 + " ───\n");
        System.out.println("Array: " + Arrays.toString(sortedArr));
        int result = binarySearchRecursive(sortedArr, target4, 0, sortedArr.length - 1, 0);
        System.out.println("Returned index: " + result);

        // ── Comparison Table ──
        System.out.println("\n─── Comparison Counts for All Elements ───\n");
        System.out.println("  Element  │  Linear  │  Binary");
        System.out.println("  ─────────┼──────────┼─────────");
        for (int val : sortedArr) {
            int linComps = countLinearComparisons(sortedArr, val);
            int binComps = countBinaryComparisons(sortedArr, val);
            System.out.printf("  %7d  │  %5d   │  %5d%n", val, linComps, binComps);
        }

        // ── Summary ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("  Array size: " + sortedArr.length);
        System.out.println("  Max linear comparisons: " + sortedArr.length);
        System.out.println("  Max binary comparisons: " + (int) Math.ceil(Math.log(sortedArr.length + 1) / Math.log(2)));
        System.out.println("  Binary Search requires a SORTED array!");
        System.out.println("=".repeat(60));
    }

    // Helper: count comparisons for linear search
    private static int countLinearComparisons(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i + 1;
        }
        return arr.length;
    }

    // Helper: count comparisons for binary search
    private static int countBinaryComparisons(int[] arr, int target) {
        int low = 0, high = arr.length - 1, count = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            count++;
            if (arr[mid] == target) return count;
            else if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return count;
    }
}
