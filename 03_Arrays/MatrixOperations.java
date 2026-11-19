/*
 * Program 38: Matrix Operations
 * 
 * Problem Statement:
 *   Implement the following operations on 2D arrays (matrices):
 *   1. Matrix Addition (A + B)
 *   2. Matrix Subtraction (A - B)
 *   3. Matrix Multiplication (A × B)
 *   4. Matrix Transpose (Aᵀ)
 *   Validate dimensions before each operation and display formatted results.
 * 
 * Sample Input:
 *   A = {{1, 2, 3},     B = {{9, 8, 7},
 *        {4, 5, 6},          {6, 5, 4},
 *        {7, 8, 9}}          {3, 2, 1}}
 * Sample Output:
 *   A + B = {{10,10,10}, {10,10,10}, {10,10,10}}
 *   A - B = {{-8,-6,-4}, {-2, 0, 2}, { 4, 6, 8}}
 *   A × B = {{30,24,18}, {84,69,54}, {138,114,90}}
 *   Aᵀ   = {{1,4,7}, {2,5,8}, {3,6,9}}
 * 
 * Explanation:
 *   - Addition/Subtraction: Element-wise, requires same dimensions (m×n).
 *   - Multiplication: (m×n) × (n×p) → (m×p). Each cell [i][j] is the dot
 *     product of row i from A and column j from B.
 *   - Transpose: Rows become columns. (m×n) → (n×m).
 * 
 * Time Complexity:
 *   Addition/Subtraction: O(m × n)
 *   Multiplication:       O(m × n × p)
 *   Transpose:            O(m × n)
 * Space Complexity: O(m × n) for the result matrix (or O(m × p) for multiplication)
 */

public class MatrixOperations {

    // ───────────────────────────────────────────────────
    // PRINT MATRIX — formatted with aligned columns
    // ───────────────────────────────────────────────────
    public static void printMatrix(String label, int[][] matrix) {
        System.out.println("  " + label + ":");
        if (matrix.length == 0) {
            System.out.println("    (empty matrix)");
            return;
        }

        // Find max width for alignment
        int maxWidth = 1;
        for (int[] row : matrix) {
            for (int val : row) {
                maxWidth = Math.max(maxWidth, String.valueOf(val).length());
            }
        }

        for (int[] row : matrix) {
            System.out.print("    │");
            for (int j = 0; j < row.length; j++) {
                System.out.printf(" %" + maxWidth + "d", row[j]);
            }
            System.out.println(" │");
        }
        System.out.println("    (" + matrix.length + "×" + matrix[0].length + " matrix)\n");
    }

    // ───────────────────────────────────────────────────
    // MATRIX ADDITION — A + B (same dimensions required)
    // ───────────────────────────────────────────────────
    public static int[][] add(int[][] a, int[][] b) {
        int rows = a.length, cols = a[0].length;

        if (b.length != rows || b[0].length != cols) {
            throw new IllegalArgumentException(
                "Cannot add: A is " + rows + "×" + cols
                + " but B is " + b.length + "×" + b[0].length);
        }

        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // MATRIX SUBTRACTION — A - B
    // ───────────────────────────────────────────────────
    public static int[][] subtract(int[][] a, int[][] b) {
        int rows = a.length, cols = a[0].length;

        if (b.length != rows || b[0].length != cols) {
            throw new IllegalArgumentException(
                "Cannot subtract: dimensions mismatch.");
        }

        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // MATRIX MULTIPLICATION — A × B
    // A(m×n) × B(n×p) = C(m×p)
    // ───────────────────────────────────────────────────
    public static int[][] multiply(int[][] a, int[][] b) {
        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;

        if (b.length != n) {
            throw new IllegalArgumentException(
                "Cannot multiply: A is " + m + "×" + n
                + " but B is " + b.length + "×" + p
                + ". A's columns (" + n + ") must equal B's rows (" + b.length + ").");
        }

        int[][] result = new int[m][p];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // MATRIX TRANSPOSE — swap rows and columns
    // ───────────────────────────────────────────────────
    public static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] result = new int[cols][rows];  // Note: dimensions swap!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    // ───────────────────────────────────────────────────
    // TRACE (sum of diagonal elements) — square matrices only
    // ───────────────────────────────────────────────────
    public static int trace(int[][] matrix) {
        int n = Math.min(matrix.length, matrix[0].length);
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += matrix[i][i];
        }
        return sum;
    }

    // ───────────────────────────────────────────────────
    // CHECK if matrix is SYMMETRIC (A == Aᵀ)
    // ───────────────────────────────────────────────────
    public static boolean isSymmetric(int[][] matrix) {
        if (matrix.length != matrix[0].length) return false;
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix[i][j] != matrix[j][i]) return false;
            }
        }
        return true;
    }

    // ───────────────────────────────────────────────────
    // MAIN
    // ───────────────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("         PROGRAM 38 — MATRIX OPERATIONS");
        System.out.println("=".repeat(60));

        // ── Define sample matrices ──
        int[][] A = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int[][] B = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };

        printMatrix("Matrix A", A);
        printMatrix("Matrix B", B);

        // ── 1. ADDITION ──
        System.out.println("━━━━━━━━ 1. MATRIX ADDITION (A + B) ━━━━━━━━\n");
        int[][] sum = add(A, B);
        printMatrix("A + B", sum);

        // ── 2. SUBTRACTION ──
        System.out.println("━━━━━━━━ 2. MATRIX SUBTRACTION (A - B) ━━━━━━━━\n");
        int[][] diff = subtract(A, B);
        printMatrix("A - B", diff);

        // ── 3. MULTIPLICATION ──
        System.out.println("━━━━━━━━ 3. MATRIX MULTIPLICATION (A × B) ━━━━━━━━\n");

        // Show step-by-step for one cell
        System.out.println("  Step-by-step for C[0][0]:");
        System.out.println("    C[0][0] = A[0][0]*B[0][0] + A[0][1]*B[1][0] + A[0][2]*B[2][0]");
        System.out.println("           = " + A[0][0] + "*" + B[0][0] + " + " + A[0][1] + "*" + B[1][0] + " + " + A[0][2] + "*" + B[2][0]);
        System.out.println("           = " + (A[0][0]*B[0][0]) + " + " + (A[0][1]*B[1][0]) + " + " + (A[0][2]*B[2][0]));
        System.out.println("           = " + (A[0][0]*B[0][0] + A[0][1]*B[1][0] + A[0][2]*B[2][0]));
        System.out.println();

        int[][] product = multiply(A, B);
        printMatrix("A × B", product);

        // ── 4. TRANSPOSE ──
        System.out.println("━━━━━━━━ 4. MATRIX TRANSPOSE ━━━━━━━━\n");
        int[][] transA = transpose(A);
        printMatrix("Aᵀ (Transpose of A)", transA);

        // ── Non-square matrix transpose ──
        int[][] rect = {
            {1, 2, 3, 4},
            {5, 6, 7, 8}
        };
        printMatrix("Rectangular Matrix R (2×4)", rect);
        int[][] transRect = transpose(rect);
        printMatrix("Rᵀ (Transpose of R, 4×2)", transRect);

        // ── Multiplication with non-square matrices ──
        System.out.println("━━━━━━━━ 5. NON-SQUARE MULTIPLICATION ━━━━━━━━\n");
        int[][] P = {
            {1, 2},
            {3, 4},
            {5, 6}
        };
        int[][] Q = {
            {7, 8, 9},
            {10, 11, 12}
        };
        printMatrix("P (3×2)", P);
        printMatrix("Q (2×3)", Q);
        int[][] PQ = multiply(P, Q);
        printMatrix("P × Q (3×3)", PQ);

        // ── Bonus: Trace and Symmetry ──
        System.out.println("━━━━━━━━ 6. BONUS: TRACE & SYMMETRY ━━━━━━━━\n");
        System.out.println("  Trace of A (sum of diagonal): " + trace(A));
        System.out.println("  Is A symmetric? " + isSymmetric(A));

        int[][] sym = {
            {1, 2, 3},
            {2, 5, 6},
            {3, 6, 9}
        };
        printMatrix("Symmetric Matrix S", sym);
        System.out.println("  Is S symmetric? " + isSymmetric(sym));

        // ── Dimension mismatch demo ──
        System.out.println("\n━━━━━━━━ 7. DIMENSION MISMATCH HANDLING ━━━━━━━━\n");
        try {
            int[][] bad = {{1, 2}};
            multiply(A, bad);
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught error: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  Matrix Operations demonstration complete!");
        System.out.println("=".repeat(60));
    }
}
