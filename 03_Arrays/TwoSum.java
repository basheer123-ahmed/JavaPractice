/*
 * Program 41: Two Sum
 * 
 * Problem Statement:
 *   Given an array of integers and a target sum, find two elements in the
 *   array that add up to the target. Return the indices of these two elements.
 *   Implement both a brute force O(n²) approach and an optimized HashMap O(n) approach.
 *   This is one of the most classic coding interview problems (LeetCode #1).
 * 
 * Sample Input:  arr = [2, 7, 11, 15], target = 9
 * Sample Output: Indices: [0, 1] (because arr[0] + arr[1] = 2 + 7 = 9)
 * 
 * Explanation:
 *   Brute Force: Check every pair (i, j) where i < j. If arr[i] + arr[j] == target,
 *   we found our answer. Simple but slow for large arrays.
 *   
 *   HashMap Approach: For each element, compute complement = target - arr[i].
 *   Check if the complement already exists in the map. If yes, we found the pair.
 *   If no, store arr[i] -> i in the map. This gives us O(1) lookups.
 * 
 * Time Complexity:  Brute Force: O(n²) | HashMap: O(n)
 * Space Complexity: Brute Force: O(1)  | HashMap: O(n)
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // ==================== Approach 1: Brute Force O(n²) ====================
    /**
     * Checks every possible pair of elements to find two that sum to target.
     * @return array of two indices, or empty array if no solution found
     */
    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{}; // no solution found
    }

    // ==================== Approach 2: HashMap O(n) ====================
    /**
     * Uses a HashMap to store seen values and their indices.
     * For each element, checks if the complement (target - current) was already seen.
     * @return array of two indices, or empty array if no solution found
     */
    public static int[] twoSumHashMap(int[] nums, int target) {
        // Map: value -> index
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }

            seen.put(nums[i], i);
        }

        return new int[]{}; // no solution found
    }

    // ==================== Bonus: Find ALL pairs that sum to target ====================
    /**
     * Finds all unique pairs of indices whose elements sum to target.
     */
    public static void findAllPairs(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        boolean foundAny = false;

        System.out.println("  All pairs summing to " + target + ":");
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                System.out.println("    -> (" + nums[seen.get(complement)] + ", " + nums[i]
                        + ") at indices [" + seen.get(complement) + ", " + i + "]");
                foundAny = true;
            }
            seen.put(nums[i], i);
        }

        if (!foundAny) {
            System.out.println("    -> No pairs found.");
        }
    }

    // ==================== Helper: Format result ====================
    private static String formatResult(int[] nums, int[] result, int target) {
        if (result.length == 0) {
            return "No two elements sum to " + target;
        }
        return "Indices: " + Arrays.toString(result)
                + " => " + nums[result[0]] + " + " + nums[result[1]] + " = " + target;
    }

    // ==================== Main Method ====================
    public static void main(String[] args) {
        System.out.println("=== Program 41: Two Sum ===\n");

        // ----- Test Case 1: Classic example -----
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.println("Test 1: nums = " + Arrays.toString(nums1) + ", target = " + target1);

        int[] result1BF = twoSumBruteForce(nums1, target1);
        int[] result1HM = twoSumHashMap(nums1, target1);
        System.out.println("  Brute Force: " + formatResult(nums1, result1BF, target1));
        System.out.println("  HashMap:     " + formatResult(nums1, result1HM, target1));

        // ----- Test Case 2: Elements in the middle -----
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        System.out.println("\nTest 2: nums = " + Arrays.toString(nums2) + ", target = " + target2);

        int[] result2BF = twoSumBruteForce(nums2, target2);
        int[] result2HM = twoSumHashMap(nums2, target2);
        System.out.println("  Brute Force: " + formatResult(nums2, result2BF, target2));
        System.out.println("  HashMap:     " + formatResult(nums2, result2HM, target2));

        // ----- Test Case 3: Same element used twice (but different indices) -----
        int[] nums3 = {3, 3};
        int target3 = 6;
        System.out.println("\nTest 3: nums = " + Arrays.toString(nums3) + ", target = " + target3);

        int[] result3BF = twoSumBruteForce(nums3, target3);
        int[] result3HM = twoSumHashMap(nums3, target3);
        System.out.println("  Brute Force: " + formatResult(nums3, result3BF, target3));
        System.out.println("  HashMap:     " + formatResult(nums3, result3HM, target3));

        // ----- Test Case 4: Negative numbers -----
        int[] nums4 = {-3, 4, 3, 90};
        int target4 = 0;
        System.out.println("\nTest 4: nums = " + Arrays.toString(nums4) + ", target = " + target4);

        int[] result4BF = twoSumBruteForce(nums4, target4);
        int[] result4HM = twoSumHashMap(nums4, target4);
        System.out.println("  Brute Force: " + formatResult(nums4, result4BF, target4));
        System.out.println("  HashMap:     " + formatResult(nums4, result4HM, target4));

        // ----- Test Case 5: No solution -----
        int[] nums5 = {1, 2, 3, 4};
        int target5 = 100;
        System.out.println("\nTest 5: nums = " + Arrays.toString(nums5) + ", target = " + target5);

        int[] result5BF = twoSumBruteForce(nums5, target5);
        int[] result5HM = twoSumHashMap(nums5, target5);
        System.out.println("  Brute Force: " + formatResult(nums5, result5BF, target5));
        System.out.println("  HashMap:     " + formatResult(nums5, result5HM, target5));

        // ----- Test Case 6: Multiple pairs (find all) -----
        int[] nums6 = {1, 5, 3, 7, 4, 2, 6};
        int target6 = 8;
        System.out.println("\nTest 6 (All Pairs): nums = " + Arrays.toString(nums6) + ", target = " + target6);
        findAllPairs(nums6, target6);

        // ----- Complexity Summary -----
        System.out.println("\n--- Complexity Comparison ---");
        System.out.println("Brute Force: Time O(n²), Space O(1)");
        System.out.println("HashMap:     Time O(n),  Space O(n)");
        System.out.println("Trade-off: HashMap uses extra memory for a significant speed gain.");
    }
}
