/*
 * Program 31: Array Basics
 * 
 * Problem Statement:
 *   Declare, initialize, traverse, and print arrays using for and for-each loops.
 *   Demonstrate both 1D and 2D arrays with various initialization techniques,
 *   including default values, literal initialization, and dynamic population.
 * 
 * Sample Input: (No user input - uses hardcoded arrays)
 * Sample Output:
 *   === 1D Array Operations ===
 *   Array declared with size 5 (default values): [0, 0, 0, 0, 0]
 *   Array with literal initialization: [10, 20, 30, 40, 50]
 *   
 *   Traversal using for loop:
 *   Index 0: 10
 *   Index 1: 20
 *   ...
 *   
 *   Traversal using for-each loop:
 *   Element: 10
 *   Element: 20
 *   ...
 *   
 *   === 2D Array Operations ===
 *   3x4 Matrix:
 *    1  2  3  4
 *    5  6  7  8
 *    9 10 11 12
 *   ...
 * 
 * Explanation:
 *   Arrays are fixed-size, contiguous data structures that store elements of the
 *   same type. In Java, arrays are objects allocated on the heap. 1D arrays store
 *   a linear sequence, while 2D arrays are arrays of arrays (each row can even
 *   have a different length — jagged arrays). This program covers declaration,
 *   initialization, traversal patterns, and common utility operations.
 * 
 * Time Complexity: O(n) for 1D traversal, O(m*n) for 2D traversal
 * Space Complexity: O(n) for 1D, O(m*n) for 2D arrays
 */

import java.util.Arrays;

public class ArrayBasics {

    // ─── Helper: print a 1D int array ───
    public static void printArray(String label, int[] arr) {
        System.out.print(label + ": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // ─── Helper: print a 2D int array as a matrix ───
    public static void printMatrix(String label, int[][] matrix) {
        System.out.println(label + ":");
        for (int[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                System.out.printf("%4d", row[j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("         PROGRAM 31 — ARRAY BASICS");
        System.out.println("=".repeat(60));

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // SECTION 1 : 1D Array Declaration & Initialization
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        System.out.println("\n=== 1D Array Operations ===\n");

        // Method 1: Declare with size (default values: 0 for int)
        int[] defaultArr = new int[5];
        printArray("Array declared with size 5 (default values)", defaultArr);

        // Method 2: Literal / inline initialization
        int[] literalArr = {10, 20, 30, 40, 50};
        printArray("Array with literal initialization", literalArr);

        // Method 3: new + literal
        int[] newLiteralArr = new int[]{100, 200, 300};
        printArray("Array with new + literal", newLiteralArr);

        // Method 4: Populate dynamically
        int[] dynamicArr = new int[5];
        for (int i = 0; i < dynamicArr.length; i++) {
            dynamicArr[i] = (i + 1) * (i + 1); // squares: 1, 4, 9, 16, 25
        }
        printArray("Dynamically populated (squares)", dynamicArr);

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // SECTION 2 : Traversal — for loop vs for-each loop
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        System.out.println("\n--- Traversal using traditional for loop ---");
        for (int i = 0; i < literalArr.length; i++) {
            System.out.println("  Index " + i + ": " + literalArr[i]);
        }

        System.out.println("\n--- Traversal using for-each (enhanced for) loop ---");
        for (int element : literalArr) {
            System.out.println("  Element: " + element);
        }

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // SECTION 3 : Common 1D Array Operations
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        System.out.println("\n--- Common Operations ---");

        // Length
        System.out.println("  Length of literalArr: " + literalArr.length);

        // Sum & Average
        int sum = 0;
        for (int val : literalArr) sum += val;
        double avg = (double) sum / literalArr.length;
        System.out.println("  Sum  = " + sum);
        System.out.println("  Avg  = " + avg);

        // Min & Max
        int min = literalArr[0], max = literalArr[0];
        for (int val : literalArr) {
            if (val < min) min = val;
            if (val > max) max = val;
        }
        System.out.println("  Min  = " + min);
        System.out.println("  Max  = " + max);

        // Reverse
        int[] reversed = new int[literalArr.length];
        for (int i = 0; i < literalArr.length; i++) {
            reversed[i] = literalArr[literalArr.length - 1 - i];
        }
        printArray("  Reversed array", reversed);

        // Copy using Arrays.copyOf
        int[] copied = Arrays.copyOf(literalArr, literalArr.length);
        printArray("  Copied array (Arrays.copyOf)", copied);

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // SECTION 4 : 2D Array Declaration & Initialization
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        System.out.println("\n=== 2D Array Operations ===\n");

        // Rectangular 2D array (3 rows × 4 columns)
        int[][] matrix = {
            { 1,  2,  3,  4},
            { 5,  6,  7,  8},
            { 9, 10, 11, 12}
        };
        printMatrix("3x4 Matrix (literal initialization)", matrix);

        // Dynamic 2D array
        int rows = 3, cols = 3;
        int[][] dynamicMatrix = new int[rows][cols];
        int value = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dynamicMatrix[i][j] = value++;
            }
        }
        printMatrix("\n3x3 Dynamically populated matrix", dynamicMatrix);

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // SECTION 5 : 2D Array Traversal
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        System.out.println("\n--- 2D Traversal using for loop ---");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print("  matrix[" + i + "][" + j + "]=" + matrix[i][j]);
            }
            System.out.println();
        }

        System.out.println("\n--- 2D Traversal using for-each loop ---");
        for (int[] row : matrix) {
            for (int elem : row) {
                System.out.printf("%4d", elem);
            }
            System.out.println();
        }

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // SECTION 6 : Jagged Array (rows of different lengths)
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        System.out.println("\n--- Jagged Array ---");
        int[][] jagged = new int[3][];
        jagged[0] = new int[]{1, 2};
        jagged[1] = new int[]{3, 4, 5, 6};
        jagged[2] = new int[]{7};

        for (int i = 0; i < jagged.length; i++) {
            System.out.print("  Row " + i + " (length " + jagged[i].length + "): ");
            for (int elem : jagged[i]) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }

        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        // SECTION 7 : String Array Demo
        // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        System.out.println("\n--- String Array ---");
        String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Elderberry"};
        System.out.println("  Fruits: " + Arrays.toString(fruits));
        System.out.println("  Sorted: ");
        Arrays.sort(fruits);
        System.out.println("  " + Arrays.toString(fruits));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  Array Basics demonstration complete!");
        System.out.println("=".repeat(60));
    }
}
