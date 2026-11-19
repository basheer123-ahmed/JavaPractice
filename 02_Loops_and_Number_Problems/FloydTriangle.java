/*
 * Program 30: Floyd's Triangle and Pascal's Triangle
 * 
 * Problem Statement:
 *   Print two famous number triangles for N rows:
 *   1. Floyd's Triangle: Consecutive natural numbers arranged in rows where
 *      row i contains i numbers. Row 1: {1}, Row 2: {2,3}, Row 3: {4,5,6}, etc.
 *   2. Pascal's Triangle: Each number is the sum of the two numbers directly
 *      above it. Row i, Col j = C(i,j) = i! / (j! * (i-j)!)
 *   Also explore properties: row sums, diagonal patterns, and symmetry.
 * 
 * Sample Input:  N = 5
 * Sample Output:
 *   Floyd's Triangle (5 rows):
 *    1
 *    2  3
 *    4  5  6
 *    7  8  9 10
 *   11 12 13 14 15
 * 
 *   Pascal's Triangle (5 rows):
 *       1
 *      1 1
 *     1 2 1
 *    1 3 3 1
 *   1 4 6 4 1
 * 
 * Explanation:
 *   Floyd's: Use a counter that starts at 1 and increments for each position.
 *   Pascal's: Use the property C(n,k) = C(n-1,k-1) + C(n-1,k), or build
 *   each row from the previous row. Each row starts and ends with 1.
 * 
 * Time Complexity: O(n^2) for both triangles (n rows, up to n elements per row)
 * Space Complexity: O(n) for Pascal's (storing one row), O(1) for Floyd's
 */

import java.util.Scanner;

public class FloydTriangle {

    // ==================== FLOYD'S TRIANGLE ====================

    /**
     * Prints Floyd's Triangle for n rows.
     * Floyd's triangle contains consecutive natural numbers where row i has i elements.
     */
    public static void printFloydTriangle(int n) {
        // Calculate the last number to determine column width for alignment
        int lastNumber = n * (n + 1) / 2;
        int maxWidth = String.valueOf(lastNumber).length();

        int number = 1;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= row; col++) {
                // Right-align numbers for neat display
                System.out.printf("%" + maxWidth + "d ", number);
                number++;
            }
            System.out.println();
        }
    }

    /**
     * Prints Floyd's Triangle without formatting (simple version).
     */
    public static void printFloydTriangleSimple(int n) {
        int number = 1;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= row; col++) {
                System.out.print(number + " ");
                number++;
            }
            System.out.println();
        }
    }

    /**
     * Returns the first number in row r of Floyd's triangle.
     * First number in row r = r*(r-1)/2 + 1
     */
    public static int floydFirstInRow(int row) {
        return row * (row - 1) / 2 + 1;
    }

    /**
     * Returns the last number in row r of Floyd's triangle.
     * Last number in row r = r*(r+1)/2
     */
    public static int floydLastInRow(int row) {
        return row * (row + 1) / 2;
    }

    // ==================== PASCAL'S TRIANGLE ====================

    /**
     * Prints Pascal's Triangle for n rows, centered.
     */
    public static void printPascalTriangle(int n) {
        // Build all rows first to determine maximum width
        int[][] triangle = buildPascalTriangle(n);

        // Calculate the width of the last (widest) row
        StringBuilder lastRow = new StringBuilder();
        for (int j = 0; j < n; j++) {
            if (j > 0) lastRow.append(" ");
            lastRow.append(triangle[n - 1][j]);
        }
        int maxRowWidth = lastRow.length();

        // Print each row centered
        for (int i = 0; i < n; i++) {
            StringBuilder rowStr = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                if (j > 0) rowStr.append(" ");
                rowStr.append(triangle[i][j]);
            }

            int padding = (maxRowWidth - rowStr.length()) / 2;
            System.out.print(" ".repeat(Math.max(0, padding)));
            System.out.println(rowStr);
        }
    }

    /**
     * Builds Pascal's Triangle as a 2D array.
     * Uses the property: C(n,k) = C(n-1,k-1) + C(n-1,k)
     */
    public static int[][] buildPascalTriangle(int n) {
        int[][] triangle = new int[n][];

        for (int i = 0; i < n; i++) {
            triangle[i] = new int[i + 1];
            triangle[i][0] = 1;      // First element is always 1
            triangle[i][i] = 1;      // Last element is always 1

            // Fill in the middle elements
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }

        return triangle;
    }

    /**
     * Calculates a specific element in Pascal's triangle using the formula.
     * C(n, k) = n! / (k! * (n-k)!)
     * Optimized to avoid overflow by computing iteratively.
     */
    public static long pascalElement(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k == 0 || k == n) return 1;

        // Use symmetry: C(n,k) = C(n, n-k), choose the smaller k
        if (k > n - k) k = n - k;

        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        return result;
    }

    /**
     * Prints Pascal's Triangle using only the formula (no 2D array needed).
     * Space efficient: O(1) extra space.
     */
    public static void printPascalTriangleFormula(int n) {
        // Find width of widest element in last row for spacing
        long maxVal = 0;
        for (int j = 0; j < n; j++) {
            maxVal = Math.max(maxVal, pascalElement(n - 1, j));
        }
        int cellWidth = String.valueOf(maxVal).length() + 1;

        for (int i = 0; i < n; i++) {
            // Leading spaces for centering
            int leadingSpaces = (n - 1 - i) * cellWidth / 2;
            System.out.print(" ".repeat(Math.max(0, leadingSpaces)));

            for (int j = 0; j <= i; j++) {
                System.out.printf("%" + cellWidth + "d", pascalElement(i, j));
            }
            System.out.println();
        }
    }

    // ==================== PASCAL'S TRIANGLE PROPERTIES ====================

    /**
     * Demonstrates properties of Pascal's Triangle.
     */
    public static void demonstrateProperties(int n) {
        int[][] triangle = buildPascalTriangle(n);

        // Property 1: Row sums = powers of 2
        System.out.println("  Property 1: Row sums = 2^n");
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j <= i; j++) sum += triangle[i][j];
            System.out.printf("    Row %d: sum = %d = 2^%d %s%n",
                    i, sum, i, sum == (1 << i) ? "✓" : "✗");
        }

        // Property 2: Symmetry - C(n,k) = C(n, n-k)
        System.out.println("\n  Property 2: Symmetry - each row is a palindrome");
        for (int i = 0; i < Math.min(n, 8); i++) {
            StringBuilder row = new StringBuilder("    Row " + i + ": ");
            boolean symmetric = true;
            for (int j = 0; j <= i; j++) {
                row.append(triangle[i][j]).append(" ");
                if (triangle[i][j] != triangle[i][i - j]) symmetric = false;
            }
            row.append(symmetric ? "✓" : "✗");
            System.out.println(row);
        }

        // Property 3: Diagonals
        System.out.println("\n  Property 3: Diagonals");
        System.out.print("    Diagonal 0 (all 1s):         ");
        for (int i = 0; i < Math.min(n, 8); i++) System.out.print(triangle[i][0] + " ");
        System.out.println();

        if (n >= 2) {
            System.out.print("    Diagonal 1 (natural numbers): ");
            for (int i = 1; i < Math.min(n, 8); i++) System.out.print(triangle[i][1] + " ");
            System.out.println();
        }

        if (n >= 3) {
            System.out.print("    Diagonal 2 (triangular nums): ");
            for (int i = 2; i < Math.min(n, 8); i++) System.out.print(triangle[i][2] + " ");
            System.out.println();
        }

        if (n >= 4) {
            System.out.print("    Diagonal 3 (tetrahedral nums):");
            for (int i = 3; i < Math.min(n, 8); i++) System.out.print(" " + triangle[i][3]);
            System.out.println();
        }

        // Property 4: Hockey Stick Pattern
        System.out.println("\n  Property 4: Hockey Stick - sum of diagonal = element below");
        if (n >= 5) {
            // Sum of C(0,0) + C(1,1) + C(2,2) + C(3,3) = C(4,3)
            System.out.printf("    C(0,0) + C(1,1) + C(2,2) + C(3,3) = %d + %d + %d + %d = %d = C(4,3) = %d %s%n",
                    triangle[0][0], triangle[1][1], triangle[2][2], triangle[3][3],
                    triangle[0][0] + triangle[1][1] + triangle[2][2] + triangle[3][3],
                    triangle[4][3],
                    (triangle[0][0] + triangle[1][1] + triangle[2][2] + triangle[3][3]) == triangle[4][3] ? "✓" : "✗");
        }

        // Property 5: Fibonacci numbers from diagonals
        System.out.println("\n  Property 5: Fibonacci from shallow diagonals");
        System.out.print("    Fibonacci sequence from Pascal's: ");
        for (int d = 0; d < Math.min(n, 10); d++) {
            int fibSum = 0;
            for (int j = 0; j <= d; j++) {
                int row = d - j;
                int col = j;
                if (col <= row && row < n) {
                    fibSum += triangle[row][col];
                }
            }
            System.out.print(fibSum + " ");
        }
        System.out.println();
    }

    // ==================== INVERTED AND VARIANT TRIANGLES ====================

    /**
     * Prints an inverted Floyd's triangle.
     */
    public static void printInvertedFloydTriangle(int n) {
        int number = 1;
        for (int row = n; row >= 1; row--) {
            for (int col = 1; col <= row; col++) {
                System.out.print(number + " ");
                number++;
            }
            System.out.println();
        }
    }

    /**
     * Prints Floyd's triangle with odd numbers only.
     */
    public static void printOddFloydTriangle(int n) {
        int number = 1;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= row; col++) {
                System.out.printf("%4d", number);
                number += 2;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Program 30: Floyd's Triangle and Pascal's Triangle ===\n");

        int rows = 7;

        // ---- Floyd's Triangle ----
        System.out.println("--- Floyd's Triangle (" + rows + " rows) ---");
        printFloydTriangle(rows);

        // ---- Floyd's Triangle Properties ----
        System.out.println("\n--- Floyd's Triangle Properties ---");
        for (int r = 1; r <= rows; r++) {
            int first = floydFirstInRow(r);
            int last = floydLastInRow(r);
            int count = r;
            int rowSum = count * (first + last) / 2;
            System.out.printf("  Row %d: first=%d, last=%d, count=%d, sum=%d%n",
                    r, first, last, count, rowSum);
        }
        System.out.printf("  Total numbers in %d rows: %d%n", rows, floydLastInRow(rows));

        // ---- Pascal's Triangle ----
        System.out.println("\n--- Pascal's Triangle (" + rows + " rows, centered) ---");
        printPascalTriangle(rows);

        // ---- Pascal's Triangle using formula ----
        System.out.println("\n--- Pascal's Triangle (10 rows, using formula) ---");
        printPascalTriangleFormula(10);

        // ---- Pascal's Triangle properties ----
        System.out.println("\n--- Properties of Pascal's Triangle (10 rows) ---");
        demonstrateProperties(10);

        // ---- Variant: Inverted Floyd's ----
        System.out.println("\n--- Inverted Floyd's Triangle (5 rows) ---");
        printInvertedFloydTriangle(5);

        // ---- Variant: Odd Floyd's ----
        System.out.println("\n--- Floyd's Triangle with Odd Numbers (5 rows) ---");
        printOddFloydTriangle(5);

        // ---- Large Pascal element computation ----
        System.out.println("\n--- Computing Large Pascal Elements ---");
        int[][] queries = {{10, 5}, {20, 10}, {30, 15}, {40, 20}, {50, 25}};
        for (int[] q : queries) {
            System.out.printf("  C(%d, %d) = %,d%n", q[0], q[1], pascalElement(q[0], q[1]));
        }

        // ---- Relationship: Binomial Theorem ----
        System.out.println("\n--- Binomial Theorem: (a+b)^n using Pascal's row ---");
        int n = 4;
        int[][] pascalRow4 = buildPascalTriangle(n + 1);
        System.out.printf("  (a+b)^%d = ", n);
        for (int k = 0; k <= n; k++) {
            if (k > 0) System.out.print(" + ");
            int coeff = pascalRow4[n][k];
            String aPart = (n - k > 0) ? "a" + (n - k > 1 ? "^" + (n - k) : "") : "";
            String bPart = (k > 0) ? "b" + (k > 1 ? "^" + k : "") : "";
            System.out.printf("%d%s%s", coeff, aPart, bPart);
        }
        System.out.println();

        // Verify with a=2, b=3
        int a = 2, b = 3;
        int direct = (int) Math.pow(a + b, n);
        int expanded = 0;
        System.out.printf("  For a=%d, b=%d: ", a, b);
        for (int k = 0; k <= n; k++) {
            int term = pascalRow4[n][k] * (int) Math.pow(a, n - k) * (int) Math.pow(b, k);
            expanded += term;
            if (k > 0) System.out.print(" + ");
            System.out.print(term);
        }
        System.out.printf(" = %d (direct: %d^%d = %d) %s%n",
                expanded, a + b, n, direct, expanded == direct ? "✓" : "✗");

        // ---- Interactive mode ----
        System.out.println("\n--- Interactive Mode ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of rows for the triangles: ");
        if (scanner.hasNextInt()) {
            int userRows = scanner.nextInt();
            if (userRows > 0 && userRows <= 20) {
                System.out.println("\nFloyd's Triangle:");
                printFloydTriangle(userRows);
                System.out.println("\nPascal's Triangle:");
                printPascalTriangle(userRows);
            } else {
                System.out.println("  Please enter a number between 1 and 20.");
            }
        }
        scanner.close();
    }
}
