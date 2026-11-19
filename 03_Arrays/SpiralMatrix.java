/*
 * Program 45: Spiral Matrix
 * 
 * Problem Statement:
 *   Given a 2D matrix of m x n elements, return all elements of the matrix
 *   in spiral order (clockwise). Handle both square and rectangular matrices.
 *   (LeetCode #54)
 *   
 *   Spiral order means: traverse right across the top row, then down the right
 *   column, then left across the bottom row, then up the left column, then
 *   repeat for the inner layers.
 * 
 * Sample Input:
 *   [[1, 2, 3],
 *    [4, 5, 6],
 *    [7, 8, 9]]
 * Sample Output: [1, 2, 3, 6, 9, 8, 7, 4, 5]
 * 
 * Sample Input:
 *   [[1,  2,  3,  4],
 *    [5,  6,  7,  8],
 *    [9, 10, 11, 12]]
 * Sample Output: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
 * 
 * Explanation:
 *   Use four boundary variables: top, bottom, left, right.
 *   1. Traverse LEFT to RIGHT along the top boundary, then top++
 *   2. Traverse TOP to BOTTOM along the right boundary, then right--
 *   3. Traverse RIGHT to LEFT along the bottom boundary (if top <= bottom), then bottom--
 *   4. Traverse BOTTOM to TOP along the left boundary (if left <= right), then left++
 *   Repeat until all boundaries collapse.
 * 
 * Time Complexity:  O(m * n) — visit every element exactly once
 * Space Complexity: O(1) extra (output list not counted) or O(m*n) for the result list
 */

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {

    // ==================== Spiral Order Traversal ====================
    /**
     * Returns all elements of the matrix in clockwise spiral order.
     * Uses shrinking boundaries (top, bottom, left, right).
     */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        int top = 0, bottom = rows - 1;
        int left = 0, right = cols - 1;

        while (top <= bottom && left <= right) {
            // 1. Traverse RIGHT across the top row
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++; // shrink top boundary

            // 2. Traverse DOWN along the right column
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--; // shrink right boundary

            // 3. Traverse LEFT across the bottom row (if still valid)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--; // shrink bottom boundary
            }

            // 4. Traverse UP along the left column (if still valid)
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++; // shrink left boundary
            }
        }

        return result;
    }

    // ==================== Generate Spiral Matrix (LeetCode #59) ====================
    /**
     * Generates an n x n matrix filled with elements 1 to n² in spiral order.
     * This is the reverse problem: instead of reading in spiral, we write in spiral.
     */
    public static int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int num = 1;

        int top = 0, bottom = n - 1;
        int left = 0, right = n - 1;

        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                matrix[top][col] = num++;
            }
            top++;

            for (int row = top; row <= bottom; row++) {
                matrix[row][right] = num++;
            }
            right--;

            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    matrix[bottom][col] = num++;
                }
                bottom--;
            }

            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    matrix[row][left] = num++;
                }
                left++;
            }
        }

        return matrix;
    }

    // ==================== Counter-Clockwise Spiral ====================
    /**
     * Returns elements in counter-clockwise spiral order.
     * Direction: Down the left → Right across bottom → Up the right → Left across top
     */
    public static List<Integer> spiralOrderCCW(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        if (matrix == null || matrix.length == 0) return result;

        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. Traverse DOWN along the left column
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][left]);
            }
            left++;

            // 2. Traverse RIGHT across the bottom row
            if (top <= bottom) {
                for (int col = left; col <= right; col++) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }

            // 3. Traverse UP along the right column
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][right]);
                }
                right--;
            }

            // 4. Traverse LEFT across the top row
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[top][col]);
                }
                top++;
            }
        }

        return result;
    }

    // ==================== Helper: Print Matrix ====================
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            StringBuilder sb = new StringBuilder("    [");
            for (int j = 0; j < row.length; j++) {
                sb.append(String.format("%3d", row[j]));
                if (j < row.length - 1) sb.append(",");
            }
            sb.append(" ]");
            System.out.println(sb);
        }
    }

    // ==================== Main Method ====================
    public static void main(String[] args) {
        System.out.println("=== Program 45: Spiral Matrix ===\n");

        // ----- Test 1: 3x3 Square Matrix -----
        System.out.println("--- Test 1: 3x3 Square Matrix ---");
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("  Matrix:");
        printMatrix(matrix1);
        System.out.println("  Spiral Order (CW):  " + spiralOrder(matrix1));
        System.out.println("  Spiral Order (CCW): " + spiralOrderCCW(matrix1));
        // Expected CW:  [1, 2, 3, 6, 9, 8, 7, 4, 5]
        // Expected CCW: [1, 4, 7, 8, 9, 6, 3, 2, 5]

        // ----- Test 2: 3x4 Rectangular Matrix -----
        System.out.println("\n--- Test 2: 3x4 Rectangular Matrix ---");
        int[][] matrix2 = {
            { 1,  2,  3,  4},
            { 5,  6,  7,  8},
            { 9, 10, 11, 12}
        };
        System.out.println("  Matrix:");
        printMatrix(matrix2);
        System.out.println("  Spiral Order: " + spiralOrder(matrix2));
        // Expected: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]

        // ----- Test 3: 4x3 Rectangular Matrix (tall) -----
        System.out.println("\n--- Test 3: 4x3 Rectangular Matrix (tall) ---");
        int[][] matrix3 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
        };
        System.out.println("  Matrix:");
        printMatrix(matrix3);
        System.out.println("  Spiral Order: " + spiralOrder(matrix3));
        // Expected: [1, 2, 3, 6, 9, 12, 11, 10, 7, 4, 5, 8]

        // ----- Test 4: Single row -----
        System.out.println("\n--- Test 4: Single Row (1x4) ---");
        int[][] matrix4 = {{1, 2, 3, 4}};
        System.out.println("  Matrix:");
        printMatrix(matrix4);
        System.out.println("  Spiral Order: " + spiralOrder(matrix4));
        // Expected: [1, 2, 3, 4]

        // ----- Test 5: Single column -----
        System.out.println("\n--- Test 5: Single Column (4x1) ---");
        int[][] matrix5 = {{1}, {2}, {3}, {4}};
        System.out.println("  Matrix:");
        printMatrix(matrix5);
        System.out.println("  Spiral Order: " + spiralOrder(matrix5));
        // Expected: [1, 2, 3, 4]

        // ----- Test 6: 1x1 Matrix -----
        System.out.println("\n--- Test 6: 1x1 Matrix ---");
        int[][] matrix6 = {{42}};
        System.out.println("  Matrix:");
        printMatrix(matrix6);
        System.out.println("  Spiral Order: " + spiralOrder(matrix6));
        // Expected: [42]

        // ----- Test 7: 4x4 Matrix -----
        System.out.println("\n--- Test 7: 4x4 Matrix ---");
        int[][] matrix7 = {
            { 1,  2,  3,  4},
            { 5,  6,  7,  8},
            { 9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        System.out.println("  Matrix:");
        printMatrix(matrix7);
        System.out.println("  Spiral Order: " + spiralOrder(matrix7));
        // Expected: [1,2,3,4, 8,12,16, 15,14,13, 9,5, 6,7, 11, 10]

        // ----- Test 8: Generate Spiral Matrix -----
        System.out.println("\n--- Test 8: Generate n×n Spiral Matrix ---");
        int n = 4;
        System.out.println("  Generated " + n + "x" + n + " spiral matrix:");
        int[][] generated = generateMatrix(n);
        printMatrix(generated);
        System.out.println("  Spiral readout: " + spiralOrder(generated));
        // Should output [1, 2, 3, ..., 16]

        int n2 = 5;
        System.out.println("\n  Generated " + n2 + "x" + n2 + " spiral matrix:");
        int[][] generated2 = generateMatrix(n2);
        printMatrix(generated2);

        // ----- Walkthrough -----
        System.out.println("\n--- Step-by-Step Walkthrough (3x3) ---");
        System.out.println("  Matrix:        Boundaries: top=0, bottom=2, left=0, right=2");
        System.out.println("  [1, 2, 3]");
        System.out.println("  [4, 5, 6]");
        System.out.println("  [7, 8, 9]");
        System.out.println();
        System.out.println("  Layer 1 (outer ring):");
        System.out.println("    → Right: 1, 2, 3        (top row)      → top becomes 1");
        System.out.println("    ↓ Down:  6, 9            (right col)    → right becomes 1");
        System.out.println("    ← Left:  8, 7            (bottom row)   → bottom becomes 1");
        System.out.println("    ↑ Up:    4                (left col)     → left becomes 1");
        System.out.println();
        System.out.println("  Layer 2 (center):");
        System.out.println("    → Right: 5                (only element) → top becomes 2");
        System.out.println("    top > bottom → STOP");
        System.out.println();
        System.out.println("  Result: [1, 2, 3, 6, 9, 8, 7, 4, 5] ✓");
    }
}
