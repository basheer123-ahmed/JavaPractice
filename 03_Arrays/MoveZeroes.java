/*
 * Program 43: Move Zeroes
 * 
 * Problem Statement:
 *   Given an array of integers, move all zeroes to the end of the array while
 *   maintaining the relative order of the non-zero elements. This must be done
 *   in-place without making a copy of the array. (LeetCode #283)
 * 
 * Sample Input:  [0, 1, 0, 3, 12]
 * Sample Output: [1, 3, 12, 0, 0]
 * 
 * Sample Input:  [0, 0, 1]
 * Sample Output: [1, 0, 0]
 * 
 * Explanation:
 *   Two-Pointer Approach: Use a 'writePointer' (also called 'insertPos') that
 *   tracks where the next non-zero element should be placed. Iterate through the
 *   array with a 'readPointer'. When a non-zero element is found, place it at
 *   writePointer's position and increment writePointer. After the loop, fill
 *   the remaining positions with zeroes.
 *   
 *   Optimal Swap Approach: Instead of filling zeroes at the end, swap the
 *   non-zero element with the element at writePointer. This avoids the second
 *   pass and handles the array in a single traversal.
 * 
 * Time Complexity:  O(n) — single pass through the array
 * Space Complexity: O(1) — in-place, no extra array
 */

import java.util.Arrays;

public class MoveZeroes {

    // ==================== Approach 1: Overwrite + Fill ====================
    /**
     * Move non-zero elements forward, then fill remaining with zeroes.
     * Two passes: one to move non-zeroes, one to fill zeroes.
     */
    public static void moveZeroesOverwrite(int[] nums) {
        int writePointer = 0;

        // Pass 1: Copy all non-zero elements to the front
        for (int readPointer = 0; readPointer < nums.length; readPointer++) {
            if (nums[readPointer] != 0) {
                nums[writePointer] = nums[readPointer];
                writePointer++;
            }
        }

        // Pass 2: Fill remaining positions with 0
        while (writePointer < nums.length) {
            nums[writePointer] = 0;
            writePointer++;
        }
    }

    // ==================== Approach 2: Optimal Swap (Single Pass) ====================
    /**
     * Swap non-zero elements into position in a single pass.
     * This is the most elegant and interview-preferred solution.
     * 
     * The 'insertPos' pointer always points to the leftmost zero.
     * When we find a non-zero, swap it with the leftmost zero.
     */
    public static void moveZeroesSwap(int[] nums) {
        int insertPos = 0; // tracks where next non-zero should go

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // Swap nums[i] with nums[insertPos]
                int temp = nums[insertPos];
                nums[insertPos] = nums[i];
                nums[i] = temp;

                insertPos++;
            }
        }
    }

    // ==================== Variation: Move Zeroes to Front ====================
    /**
     * Move all zeroes to the FRONT while maintaining order of non-zeroes.
     * [0, 1, 0, 3, 12] => [0, 0, 1, 3, 12]
     */
    public static void moveZeroesToFront(int[] nums) {
        int insertPos = nums.length - 1; // start from the end

        // Traverse from right to left
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] != 0) {
                nums[insertPos] = nums[i];
                insertPos--;
            }
        }

        // Fill remaining positions with 0
        while (insertPos >= 0) {
            nums[insertPos] = 0;
            insertPos--;
        }
    }

    // ==================== Variation: Move Target Value to End ====================
    /**
     * Generalized: move all occurrences of a specific value to the end.
     * This is a generalization of the move-zeroes problem.
     */
    public static void moveValueToEnd(int[] nums, int val) {
        int insertPos = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                int temp = nums[insertPos];
                nums[insertPos] = nums[i];
                nums[i] = temp;
                insertPos++;
            }
        }
    }

    // ==================== Count minimum swaps ====================
    /**
     * Count the number of operations (swaps) performed.
     * Useful for analyzing efficiency.
     */
    public static int moveZeroesCountOps(int[] nums) {
        int insertPos = 0;
        int swapCount = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != insertPos) { // only count actual swaps
                    int temp = nums[insertPos];
                    nums[insertPos] = nums[i];
                    nums[i] = temp;
                    swapCount++;
                }
                insertPos++;
            }
        }

        return swapCount;
    }

    // ==================== Main Method ====================
    public static void main(String[] args) {
        System.out.println("=== Program 43: Move Zeroes ===\n");

        // ----- Test 1: Classic example -----
        System.out.println("--- Approach 1: Overwrite + Fill ---");
        int[] arr1 = {0, 1, 0, 3, 12};
        System.out.println("Original: " + Arrays.toString(arr1));
        moveZeroesOverwrite(arr1);
        System.out.println("Result:   " + Arrays.toString(arr1));
        // Expected: [1, 3, 12, 0, 0]

        // ----- Test 2: Optimal swap -----
        System.out.println("\n--- Approach 2: Optimal Swap (Single Pass) ---");
        int[] arr2 = {0, 1, 0, 3, 12};
        System.out.println("Original: " + Arrays.toString(arr2));
        moveZeroesSwap(arr2);
        System.out.println("Result:   " + Arrays.toString(arr2));
        // Expected: [1, 3, 12, 0, 0]

        // ----- Test 3: All zeroes -----
        int[] arr3 = {0, 0, 0, 0};
        System.out.println("\nOriginal: " + Arrays.toString(arr3));
        moveZeroesSwap(arr3);
        System.out.println("Result:   " + Arrays.toString(arr3));
        // Expected: [0, 0, 0, 0]

        // ----- Test 4: No zeroes -----
        int[] arr4 = {1, 2, 3, 4};
        System.out.println("\nOriginal: " + Arrays.toString(arr4));
        moveZeroesSwap(arr4);
        System.out.println("Result:   " + Arrays.toString(arr4));
        // Expected: [1, 2, 3, 4]

        // ----- Test 5: Leading zeroes -----
        int[] arr5 = {0, 0, 1};
        System.out.println("\nOriginal: " + Arrays.toString(arr5));
        moveZeroesSwap(arr5);
        System.out.println("Result:   " + Arrays.toString(arr5));
        // Expected: [1, 0, 0]

        // ----- Test 6: Single element -----
        int[] arr6 = {0};
        System.out.println("\nOriginal: " + Arrays.toString(arr6));
        moveZeroesSwap(arr6);
        System.out.println("Result:   " + Arrays.toString(arr6));

        // ----- Test 7: Move zeroes to FRONT -----
        System.out.println("\n--- Variation: Move Zeroes to Front ---");
        int[] arr7 = {0, 1, 0, 3, 12};
        System.out.println("Original: " + Arrays.toString(arr7));
        moveZeroesToFront(arr7);
        System.out.println("Result:   " + Arrays.toString(arr7));
        // Expected: [0, 0, 1, 3, 12]

        // ----- Test 8: Move specific value to end -----
        System.out.println("\n--- Variation: Move Value '3' to End ---");
        int[] arr8 = {3, 1, 3, 4, 3, 2};
        System.out.println("Original: " + Arrays.toString(arr8));
        moveValueToEnd(arr8, 3);
        System.out.println("Result:   " + Arrays.toString(arr8));
        // Expected: [1, 4, 2, 3, 3, 3]

        // ----- Test 9: Count operations -----
        System.out.println("\n--- Counting Swap Operations ---");
        int[] arr9 = {0, 1, 0, 3, 12};
        System.out.println("Original:    " + Arrays.toString(arr9));
        int swaps = moveZeroesCountOps(arr9);
        System.out.println("Result:      " + Arrays.toString(arr9));
        System.out.println("Swaps made:  " + swaps);

        // ----- Walkthrough -----
        System.out.println("\n--- Step-by-Step Walkthrough (Swap Approach) ---");
        System.out.println("Array: [0, 1, 0, 3, 12], insertPos = 0");
        System.out.println();
        System.out.println("i=0: arr[0]=0 (zero, skip)              → [0, 1, 0, 3, 12], insertPos=0");
        System.out.println("i=1: arr[1]=1 (non-zero, swap with 0)   → [1, 0, 0, 3, 12], insertPos=1");
        System.out.println("i=2: arr[2]=0 (zero, skip)              → [1, 0, 0, 3, 12], insertPos=1");
        System.out.println("i=3: arr[3]=3 (non-zero, swap with 1)   → [1, 3, 0, 0, 12], insertPos=2");
        System.out.println("i=4: arr[4]=12 (non-zero, swap with 2)  → [1, 3, 12, 0, 0], insertPos=3");
        System.out.println();
        System.out.println("Final: [1, 3, 12, 0, 0] ✓");
    }
}
