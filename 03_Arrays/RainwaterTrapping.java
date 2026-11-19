/*
 * Program 44: Rainwater Trapping
 * 
 * Problem Statement:
 *   Given an array representing an elevation map where the width of each bar is 1,
 *   compute how much water can be trapped after raining. (LeetCode #42)
 *   
 *   For each position, the water level is determined by the minimum of the maximum
 *   heights to its left and right, minus the height at that position.
 *   water[i] = max(0, min(leftMax[i], rightMax[i]) - height[i])
 * 
 * Sample Input:  [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
 * Sample Output: 6 units of water trapped
 * 
 * Sample Input:  [4, 2, 0, 3, 2, 5]
 * Sample Output: 9 units of water trapped
 * 
 * Explanation:
 *   Prefix Max Approach: Pre-compute leftMax[] and rightMax[] arrays. For each index,
 *   the trapped water is min(leftMax, rightMax) - height. Sum all positive values.
 *   
 *   Two-Pointer Approach: Use left and right pointers converging inward. Track the
 *   running leftMax and rightMax. The side with the smaller max determines the water
 *   level, so process from that side. This avoids the extra arrays.
 * 
 * Time Complexity:  O(n) for both approaches
 * Space Complexity: Prefix Max: O(n) for the two extra arrays | Two-Pointer: O(1)
 */

import java.util.Arrays;

public class RainwaterTrapping {

    // ==================== Approach 1: Prefix Max Arrays ====================
    /**
     * Uses pre-computed leftMax[] and rightMax[] arrays.
     * leftMax[i] = max height from index 0 to i
     * rightMax[i] = max height from index i to n-1
     * Water at i = max(0, min(leftMax[i], rightMax[i]) - height[i])
     */
    public static int trapPrefixMax(int[] height) {
        int n = height.length;
        if (n <= 2) return 0; // Can't trap water with less than 3 bars

        // Build leftMax array
        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        // Build rightMax array
        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        // Calculate trapped water
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            int waterAtI = Math.min(leftMax[i], rightMax[i]) - height[i];
            totalWater += waterAtI; // waterAtI is always >= 0 by construction
        }

        return totalWater;
    }

    // ==================== Approach 2: Two-Pointer (Optimal) ====================
    /**
     * Uses two pointers converging from both ends. No extra arrays needed.
     * 
     * Key insight: We only need to know the minimum of leftMax and rightMax.
     * If leftMax < rightMax, we know leftMax is the bottleneck, so we process
     * the left side. Vice versa for rightMax < leftMax.
     */
    public static int trapTwoPointer(int[] height) {
        int n = height.length;
        if (n <= 2) return 0;

        int left = 0, right = n - 1;
        int leftMax = 0, rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // Left side is the bottleneck
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // Update the left max
                } else {
                    totalWater += leftMax - height[left]; // Trap water
                }
                left++;
            } else {
                // Right side is the bottleneck
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // Update the right max
                } else {
                    totalWater += rightMax - height[right]; // Trap water
                }
                right--;
            }
        }

        return totalWater;
    }

    // ==================== Detailed Breakdown (Educational) ====================
    /**
     * Shows the water trapped at each position for educational purposes.
     */
    public static void trapDetailed(int[] height) {
        int n = height.length;
        if (n <= 2) {
            System.out.println("  Cannot trap water with fewer than 3 bars.");
            return;
        }

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        System.out.println("  Index:    " + formatIndexRow(n));
        System.out.println("  Height:   " + Arrays.toString(height));
        System.out.println("  LeftMax:  " + Arrays.toString(leftMax));
        System.out.println("  RightMax: " + Arrays.toString(rightMax));

        int totalWater = 0;
        int[] waterPerIndex = new int[n];
        for (int i = 0; i < n; i++) {
            waterPerIndex[i] = Math.min(leftMax[i], rightMax[i]) - height[i];
            totalWater += waterPerIndex[i];
        }

        System.out.println("  Water:    " + Arrays.toString(waterPerIndex));
        System.out.println("  Total:    " + totalWater);
    }

    private static String formatIndexRow(int n) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(", ");
            sb.append(i);
        }
        sb.append("]");
        return sb.toString();
    }

    // ==================== Visual Representation ====================
    /**
     * Prints an ASCII visualization of the elevation map and trapped water.
     */
    public static void visualize(int[] height) {
        int n = height.length;
        if (n == 0) return;

        // Compute water at each position
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) rightMax[i] = Math.max(rightMax[i + 1], height[i]);

        int[] water = new int[n];
        for (int i = 0; i < n; i++) water[i] = Math.min(leftMax[i], rightMax[i]) - height[i];

        int maxHeight = 0;
        for (int h : height) maxHeight = Math.max(maxHeight, h);

        System.out.println("  ASCII Visualization (█ = wall, ~ = water):");
        for (int level = maxHeight; level >= 1; level--) {
            StringBuilder row = new StringBuilder("  ");
            for (int col = 0; col < n; col++) {
                if (height[col] >= level) {
                    row.append(" █ ");
                } else if (height[col] + water[col] >= level) {
                    row.append(" ~ ");
                } else {
                    row.append("   ");
                }
            }
            System.out.println(row);
        }
        // Print base line
        StringBuilder base = new StringBuilder("  ");
        for (int col = 0; col < n; col++) base.append("---");
        System.out.println(base);
    }

    // ==================== Main Method ====================
    public static void main(String[] args) {
        System.out.println("=== Program 44: Rainwater Trapping ===\n");

        // ----- Test 1: Classic example -----
        int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("--- Test 1 ---");
        System.out.println("Height: " + Arrays.toString(height1));
        System.out.println("Prefix Max Result: " + trapPrefixMax(height1) + " units");
        System.out.println("Two-Pointer Result: " + trapTwoPointer(height1) + " units");
        System.out.println();
        trapDetailed(height1);
        System.out.println();
        visualize(height1);

        // ----- Test 2: Another example -----
        int[] height2 = {4, 2, 0, 3, 2, 5};
        System.out.println("\n--- Test 2 ---");
        System.out.println("Height: " + Arrays.toString(height2));
        System.out.println("Prefix Max Result: " + trapPrefixMax(height2) + " units");
        System.out.println("Two-Pointer Result: " + trapTwoPointer(height2) + " units");
        System.out.println();
        trapDetailed(height2);
        System.out.println();
        visualize(height2);

        // ----- Test 3: No water trapped (ascending) -----
        int[] height3 = {1, 2, 3, 4, 5};
        System.out.println("\n--- Test 3: Ascending (No trapping) ---");
        System.out.println("Height: " + Arrays.toString(height3));
        System.out.println("Water trapped: " + trapTwoPointer(height3) + " units");

        // ----- Test 4: No water trapped (descending) -----
        int[] height4 = {5, 4, 3, 2, 1};
        System.out.println("\n--- Test 4: Descending (No trapping) ---");
        System.out.println("Height: " + Arrays.toString(height4));
        System.out.println("Water trapped: " + trapTwoPointer(height4) + " units");

        // ----- Test 5: Valley shape -----
        int[] height5 = {3, 0, 0, 0, 3};
        System.out.println("\n--- Test 5: Valley shape ---");
        System.out.println("Height: " + Arrays.toString(height5));
        System.out.println("Water trapped: " + trapTwoPointer(height5) + " units");
        // Expected: 9 (3 positions × 3 depth)
        visualize(height5);

        // ----- Test 6: Empty / too small -----
        int[] height6 = {5};
        System.out.println("\n--- Test 6: Single bar ---");
        System.out.println("Height: " + Arrays.toString(height6));
        System.out.println("Water trapped: " + trapTwoPointer(height6) + " units");

        // ----- Complexity Summary -----
        System.out.println("\n--- Complexity Comparison ---");
        System.out.println("Prefix Max: Time O(n), Space O(n) — uses two extra arrays");
        System.out.println("Two-Pointer: Time O(n), Space O(1) — constant extra space");
        System.out.println("Both approaches produce the same correct result.");
    }
}
