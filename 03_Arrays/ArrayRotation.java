/*
 * Program 34: Array Rotation
 * 
 * Problem Statement:
 *   Rotate an array left and right by K positions using two approaches:
 *   1. Temp Array method — copy elements to a temporary array in rotated order
 *   2. Reversal Algorithm — reverse sub-sections of the array in-place
 *   Handle cases where K > array length by taking K % n.
 * 
 * Sample Input: arr = {1, 2, 3, 4, 5, 6, 7}, K = 3
 * Sample Output:
 *   Original:           [1, 2, 3, 4, 5, 6, 7]
 *   Left rotate by 3:   [4, 5, 6, 7, 1, 2, 3]
 *   Right rotate by 3:  [5, 6, 7, 1, 2, 3, 4]
 * 
 * Explanation:
 *   Left Rotation by K: Move the first K elements to the end.
 *     - Temp Array: Copy arr[K..n-1] then arr[0..K-1] into a new array.
 *     - Reversal:   Reverse(0, K-1) → Reverse(K, n-1) → Reverse(0, n-1)
 * 
 *   Right Rotation by K: Move the last K elements to the front.
 *     - This is equivalent to left rotation by (n - K).
 *     - Reversal:   Reverse(0, n-1) → Reverse(0, K-1) → Reverse(K, n-1)
 * 
 *   The Reversal Algorithm is preferred because it uses O(1) extra space.
 * 
 * Time Complexity: O(n) for both methods
 * Space Complexity: O(n) for temp array, O(1) for reversal algorithm
 */

import java.util.Arrays;

public class ArrayRotation {

    // ───────────────────────────────────────────────────
    // METHOD 1: LEFT ROTATE using Temp Array
    // ───────────────────────────────────────────────────
    public static int[] leftRotateTempArray(int[] original, int k) {
        int n = original.length;
        if (n == 0) return original.clone();
        k = k % n;  // handle k > n
        if (k == 0) return original.clone();

        int[] result = new int[n];
        // Copy elements from position k to end
        for (int i = 0; i < n - k; i++) {
            result[i] = original[i + k];
        }
        // Copy the first k elements to the end
        for (int i = 0; i < k; i++) {
            result[n - k + i] = original[i];
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // METHOD 1: RIGHT ROTATE using Temp Array
    // ───────────────────────────────────────────────────
    public static int[] rightRotateTempArray(int[] original, int k) {
        int n = original.length;
        if (n == 0) return original.clone();
        k = k % n;
        if (k == 0) return original.clone();

        int[] result = new int[n];
        // Copy the last k elements to the front
        for (int i = 0; i < k; i++) {
            result[i] = original[n - k + i];
        }
        // Copy the remaining elements after
        for (int i = 0; i < n - k; i++) {
            result[k + i] = original[i];
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // HELPER: Reverse a subarray in-place (arr[start..end])
    // ───────────────────────────────────────────────────
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    // ───────────────────────────────────────────────────
    // METHOD 2: LEFT ROTATE using Reversal Algorithm
    //   Step 1: Reverse first K elements     → [K-1 ... 1, 0]
    //   Step 2: Reverse remaining elements   → [n-1 ... K]
    //   Step 3: Reverse entire array         → [K, K+1, ... n-1, 0, 1, ... K-1]
    // ───────────────────────────────────────────────────
    public static int[] leftRotateReversal(int[] original, int k) {
        int[] arr = original.clone();
        int n = arr.length;
        if (n == 0) return arr;
        k = k % n;
        if (k == 0) return arr;

        System.out.println("    Initial:                " + Arrays.toString(arr));

        reverse(arr, 0, k - 1);       // Reverse first K
        System.out.println("    After reverse(0, " + (k - 1) + "):     " + Arrays.toString(arr));

        reverse(arr, k, n - 1);       // Reverse remaining
        System.out.println("    After reverse(" + k + ", " + (n - 1) + "):     " + Arrays.toString(arr));

        reverse(arr, 0, n - 1);       // Reverse entire
        System.out.println("    After reverse(0, " + (n - 1) + "):     " + Arrays.toString(arr));

        return arr;
    }

    // ───────────────────────────────────────────────────
    // METHOD 2: RIGHT ROTATE using Reversal Algorithm
    //   Step 1: Reverse entire array         → [n-1, ... 1, 0]
    //   Step 2: Reverse first K elements     → [0 ... K-1, rest reversed]
    //   Step 3: Reverse remaining elements   → result
    // ───────────────────────────────────────────────────
    public static int[] rightRotateReversal(int[] original, int k) {
        int[] arr = original.clone();
        int n = arr.length;
        if (n == 0) return arr;
        k = k % n;
        if (k == 0) return arr;

        System.out.println("    Initial:                " + Arrays.toString(arr));

        reverse(arr, 0, n - 1);       // Reverse entire
        System.out.println("    After reverse(0, " + (n - 1) + "):     " + Arrays.toString(arr));

        reverse(arr, 0, k - 1);       // Reverse first K
        System.out.println("    After reverse(0, " + (k - 1) + "):     " + Arrays.toString(arr));

        reverse(arr, k, n - 1);       // Reverse remaining
        System.out.println("    After reverse(" + k + ", " + (n - 1) + "):     " + Arrays.toString(arr));

        return arr;
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("         PROGRAM 34 — ARRAY ROTATION");
        System.out.println("=".repeat(60));

        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;

        System.out.println("\nOriginal array: " + Arrays.toString(arr));
        System.out.println("Rotation amount K = " + k);

        // ── LEFT ROTATION ──
        System.out.println("\n━━━━━━━━ LEFT ROTATION by " + k + " ━━━━━━━━");

        System.out.println("\n  [Temp Array Method]");
        int[] leftTemp = leftRotateTempArray(arr, k);
        System.out.println("    Result: " + Arrays.toString(leftTemp));

        System.out.println("\n  [Reversal Algorithm]");
        int[] leftRev = leftRotateReversal(arr, k);
        System.out.println("    Result: " + Arrays.toString(leftRev));

        // ── RIGHT ROTATION ──
        System.out.println("\n━━━━━━━━ RIGHT ROTATION by " + k + " ━━━━━━━━");

        System.out.println("\n  [Temp Array Method]");
        int[] rightTemp = rightRotateTempArray(arr, k);
        System.out.println("    Result: " + Arrays.toString(rightTemp));

        System.out.println("\n  [Reversal Algorithm]");
        int[] rightRev = rightRotateReversal(arr, k);
        System.out.println("    Result: " + Arrays.toString(rightRev));

        // ── Edge Case: K > n ──
        int bigK = 10;  // 10 % 7 = 3, so same as rotating by 3
        System.out.println("\n━━━━━━━━ Edge Case: K = " + bigK + " (K > n, effective K = " + (bigK % arr.length) + ") ━━━━━━━━");
        System.out.println("  Left rotate:  " + Arrays.toString(leftRotateTempArray(arr, bigK)));
        System.out.println("  Right rotate: " + Arrays.toString(rightRotateTempArray(arr, bigK)));

        // ── Edge Case: K = 0 ──
        System.out.println("\n━━━━━━━━ Edge Case: K = 0 ━━━━━━━━");
        System.out.println("  Left rotate:  " + Arrays.toString(leftRotateTempArray(arr, 0)));
        System.out.println("  Right rotate: " + Arrays.toString(rightRotateTempArray(arr, 0)));

        // ── Edge Case: K = n ──
        System.out.println("\n━━━━━━━━ Edge Case: K = " + arr.length + " (full rotation = no change) ━━━━━━━━");
        System.out.println("  Left rotate:  " + Arrays.toString(leftRotateTempArray(arr, arr.length)));
        System.out.println("  Right rotate: " + Arrays.toString(rightRotateTempArray(arr, arr.length)));

        // ── Verification: left rotate by K + right rotate by K = original? ──
        System.out.println("\n━━━━━━━━ Verification ━━━━━━━━");
        System.out.println("  Original:                   " + Arrays.toString(arr));
        int[] leftThenRight = rightRotateTempArray(leftRotateTempArray(arr, k), k);
        System.out.println("  Left(" + k + ") then Right(" + k + "):   " + Arrays.toString(leftThenRight));
        System.out.println("  Match original? " + Arrays.equals(arr, leftThenRight));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  Array Rotation demonstration complete!");
        System.out.println("=".repeat(60));
    }
}
