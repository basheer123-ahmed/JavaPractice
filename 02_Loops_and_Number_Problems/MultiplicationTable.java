/*
 * Program 16: Multiplication Table
 * 
 * Problem Statement:
 *   Print the multiplication table for a given number from 1 to 10
 *   using a for loop. Also support custom range (e.g., 1 to 20).
 * 
 * Sample Input:  7
 * Sample Output:
 *   7 x 1 = 7
 *   7 x 2 = 14
 *   7 x 3 = 21
 *   7 x 4 = 28
 *   7 x 5 = 35
 *   7 x 6 = 42
 *   7 x 7 = 49
 *   7 x 8 = 56
 *   7 x 9 = 63
 *   7 x 10 = 70
 * 
 * Explanation:
 *   A multiplication table lists the product of a number with integers
 *   from 1 to 10 (or any specified range). We iterate using a for loop
 *   and compute number * i for each iteration.
 * 
 * Time Complexity:  O(n) where n is the range (default 10)
 * Space Complexity: O(1)
 */

import java.util.Scanner;

public class MultiplicationTable {

    // --- Method 1: Basic multiplication table (1 to 10) ---
    public static void printTable(int number) {
        printTable(number, 1, 10);
    }

    // --- Method 2: Custom range multiplication table ---
    public static void printTable(int number, int start, int end) {
        System.out.println("=== Multiplication Table for " + number 
                           + " (from " + start + " to " + end + ") ===");
        for (int i = start; i <= end; i++) {
            System.out.println(number + " x " + i + " = " + (number * i));
        }
        System.out.println();
    }

    // --- Method 3: Formatted table using printf for aligned output ---
    public static void printFormattedTable(int number, int end) {
        System.out.println("=== Formatted Multiplication Table for " + number + " ===");
        for (int i = 1; i <= end; i++) {
            System.out.printf("%4d x %-4d = %6d%n", number, i, (number * i));
        }
        System.out.println();
    }

    // --- Method 4: Print a grid-style table for multiple numbers ---
    public static void printMultiplicationGrid(int maxNumber) {
        System.out.println("=== Multiplication Grid (1 to " + maxNumber + ") ===");
        
        // Print header row
        System.out.printf("%5s", "x");
        for (int j = 1; j <= maxNumber; j++) {
            System.out.printf("%5d", j);
        }
        System.out.println();

        // Print separator
        System.out.print("-----");
        for (int j = 1; j <= maxNumber; j++) {
            System.out.print("-----");
        }
        System.out.println();

        // Print each row
        for (int i = 1; i <= maxNumber; i++) {
            System.out.printf("%4d|", i);
            for (int j = 1; j <= maxNumber; j++) {
                System.out.printf("%5d", i * j);
            }
            System.out.println();
        }
        System.out.println();
    }

    // --- Method 5: Using while loop (alternative approach) ---
    public static void printTableWhileLoop(int number) {
        System.out.println("=== Table using While Loop for " + number + " ===");
        int i = 1;
        while (i <= 10) {
            System.out.println(number + " x " + i + " = " + (number * i));
            i++;
        }
        System.out.println();
    }

    // --- Method 6: Using StringBuilder for efficient string building ---
    public static String getTableAsString(int number) {
        StringBuilder sb = new StringBuilder();
        sb.append("Multiplication Table for ").append(number).append(":\n");
        for (int i = 1; i <= 10; i++) {
            sb.append(number).append(" x ").append(i)
              .append(" = ").append(number * i).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Basic table ---
        System.out.print("Enter a number for its multiplication table: ");
        int number = scanner.nextInt();
        printTable(number);

        // --- Demo 2: Custom range ---
        System.out.print("Enter start of range: ");
        int start = scanner.nextInt();
        System.out.print("Enter end of range: ");
        int end = scanner.nextInt();
        printTable(number, start, end);

        // --- Demo 3: Formatted table ---
        printFormattedTable(number, 12);

        // --- Demo 4: While loop version ---
        printTableWhileLoop(number);

        // --- Demo 5: Multiplication grid ---
        System.out.println("--- Compact 10x10 Multiplication Grid ---");
        printMultiplicationGrid(10);

        // --- Demo 6: StringBuilder version ---
        System.out.println("--- StringBuilder Version ---");
        System.out.println(getTableAsString(number));

        // --- Demo 7: Tables for multiple numbers ---
        System.out.println("--- Tables for numbers 1 through 5 ---");
        for (int n = 1; n <= 5; n++) {
            printTable(n);
        }

        scanner.close();
    }
}
