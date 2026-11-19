/*
 * Program 42: Array Reversal
 * 
 * Problem Statement:
 *   Reverse an array in-place using the two-pointer technique. Also implement
 *   reversing a specific portion (sub-range) of the array. The two-pointer
 *   technique is a fundamental pattern used in many array and string problems.
 * 
 * Sample Input:  [1, 2, 3, 4, 5]
 * Sample Output: [5, 4, 3, 2, 1]
 * 
 * Sample Input (Partial):  [1, 2, 3, 4, 5], reverse from index 1 to 3
 * Sample Output (Partial): [1, 4, 3, 2, 5]
 * 
 * Explanation:
 *   Place one pointer at the start and one at the end. Swap the elements at
 *   these two pointers, then move them inward (start++ and end--). Continue
 *   until the pointers meet or cross. This reverses the array in-place with
 *   no extra memory (beyond a temp variable for swapping).
 *   
 *   For partial reversal, the same logic applies but we limit the pointer
 *   range to [left, right] instead of [0, n-1].
 * 
 * Time Complexity:  O(n) for full reversal, O(right - left) for partial
 * Space Complexity: O(1) — in-place swap, no extra array needed
 */

import java.util.Arrays;

public class ArrayReversal {

    // ==================== Approach 1: Two-Pointer Full Reversal ====================
    /**
     * Reverses the entire array in-place using two pointers.
     */
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // Swap elements at left and right
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }

    // ==================== Approach 2: Partial Reversal (Sub-range) ====================
    /**
     * Reverses a portion of the array from index 'left' to 'right' (inclusive).
     * This is used in many algorithms (e.g., rotating an array, next permutation).
     */
    public static void reverseRange(int[] arr, int left, int right) {
        // Validate bounds
        if (left < 0 || right >= arr.length || left >= right) {
            return;
        }

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    // ==================== Application: Rotate Array Using Reversal ====================
    /**
     * Rotates array to the right by k positions using the 3-reversal trick.
     * Example: [1,2,3,4,5] rotated by 2 => [4,5,1,2,3]
     * 
     * Algorithm:
     *   1. Reverse the entire array:        [5,4,3,2,1]
     *   2. Reverse the first k elements:    [4,5,3,2,1]
     *   3. Reverse the remaining elements:  [4,5,1,2,3]
     */
    public static void rotateArray(int[] arr, int k) {
        int n = arr.length;
        if (n == 0) return;

        k = k % n; // Handle k > n
        if (k == 0) return;

        reverseRange(arr, 0, n - 1);     // Step 1: Reverse all
        reverseRange(arr, 0, k - 1);     // Step 2: Reverse first k
        reverseRange(arr, k, n - 1);     // Step 3: Reverse rest
    }

    // ==================== Reverse a String using char array ====================
    /**
     * Demonstrates that the same two-pointer reversal works for strings
     * (via char arrays), which is another common interview question.
     */
    public static String reverseString(String s) {
        char[] chars = s.toCharArray();
        int left = 0, right = chars.length - 1;

        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }

        return new String(chars);
    }

    // ==================== Reverse Words Order ====================
    /**
     * Reverse the order of words in a sentence.
     * "hello world java" => "java world hello"
     * Uses the reversal trick: reverse all chars, then reverse each word.
     */
    public static String reverseWords(String sentence) {
        char[] chars = sentence.toCharArray();
        int n = chars.length;

        // Step 1: Reverse the entire character array
        reverseCharRange(chars, 0, n - 1);

        // Step 2: Reverse each individual word
        int start = 0;
        for (int end = 0; end <= n; end++) {
            if (end == n || chars[end] == ' ') {
                reverseCharRange(chars, start, end - 1);
                start = end + 1;
            }
        }

        return new String(chars);
    }

    private static void reverseCharRange(char[] arr, int left, int right) {
        while (left < right) {
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    // ==================== Main Method ====================
    public static void main(String[] args) {
        System.out.println("=== Program 42: Array Reversal ===\n");

        // ----- Test 1: Full array reversal -----
        System.out.println("--- Test 1: Full Array Reversal ---");
        int[] arr1 = {1, 2, 3, 4, 5};
        System.out.println("Original:  " + Arrays.toString(arr1));
        reverseArray(arr1);
        System.out.println("Reversed:  " + Arrays.toString(arr1));

        // ----- Test 2: Even-length array -----
        int[] arr2 = {10, 20, 30, 40};
        System.out.println("\nOriginal:  " + Arrays.toString(arr2));
        reverseArray(arr2);
        System.out.println("Reversed:  " + Arrays.toString(arr2));

        // ----- Test 3: Single element -----
        int[] arr3 = {42};
        System.out.println("\nOriginal:  " + Arrays.toString(arr3));
        reverseArray(arr3);
        System.out.println("Reversed:  " + Arrays.toString(arr3));

        // ----- Test 4: Partial reversal -----
        System.out.println("\n--- Test 2: Partial Reversal (Sub-range) ---");
        int[] arr4 = {1, 2, 3, 4, 5};
        System.out.println("Original:            " + Arrays.toString(arr4));
        reverseRange(arr4, 1, 3);
        System.out.println("Reverse [1..3]:      " + Arrays.toString(arr4));
        // Expected: [1, 4, 3, 2, 5]

        int[] arr5 = {10, 20, 30, 40, 50, 60, 70};
        System.out.println("\nOriginal:            " + Arrays.toString(arr5));
        reverseRange(arr5, 2, 5);
        System.out.println("Reverse [2..5]:      " + Arrays.toString(arr5));
        // Expected: [10, 20, 60, 50, 40, 30, 70]

        // ----- Test 5: Array rotation using reversal -----
        System.out.println("\n--- Test 3: Array Rotation via Reversal ---");
        int[] arr6 = {1, 2, 3, 4, 5};
        System.out.println("Original:            " + Arrays.toString(arr6));
        rotateArray(arr6, 2);
        System.out.println("Rotate right by 2:   " + Arrays.toString(arr6));
        // Expected: [4, 5, 1, 2, 3]

        int[] arr7 = {10, 20, 30, 40, 50, 60};
        System.out.println("\nOriginal:            " + Arrays.toString(arr7));
        rotateArray(arr7, 3);
        System.out.println("Rotate right by 3:   " + Arrays.toString(arr7));
        // Expected: [40, 50, 60, 10, 20, 30]

        // ----- Test 6: String reversal -----
        System.out.println("\n--- Test 4: String Reversal ---");
        String s1 = "hello";
        System.out.println("Original: \"" + s1 + "\"  =>  Reversed: \"" + reverseString(s1) + "\"");

        String s2 = "abcdef";
        System.out.println("Original: \"" + s2 + "\"  =>  Reversed: \"" + reverseString(s2) + "\"");

        // ----- Test 7: Reverse word order -----
        System.out.println("\n--- Test 5: Reverse Word Order ---");
        String sentence1 = "hello world java";
        System.out.println("Original: \"" + sentence1 + "\"");
        System.out.println("Reversed: \"" + reverseWords(sentence1) + "\"");

        String sentence2 = "the sky is blue";
        System.out.println("\nOriginal: \"" + sentence2 + "\"");
        System.out.println("Reversed: \"" + reverseWords(sentence2) + "\"");

        // ----- Walkthrough -----
        System.out.println("\n--- Step-by-Step Walkthrough ---");
        System.out.println("Array: [1, 2, 3, 4, 5]");
        System.out.println("Step 1: left=0, right=4 → swap(1, 5) → [5, 2, 3, 4, 1]");
        System.out.println("Step 2: left=1, right=3 → swap(2, 4) → [5, 4, 3, 2, 1]");
        System.out.println("Step 3: left=2, right=2 → pointers meet, STOP");
        System.out.println("Result: [5, 4, 3, 2, 1]");
    }
}
