/*
 * Program 25: Star Patterns
 * 
 * Problem Statement:
 *   Print 5 different star patterns using nested loops:
 *     1. Right-aligned triangle
 *     2. Inverted triangle
 *     3. Pyramid (centered triangle)
 *     4. Diamond
 *     5. Hollow rectangle
 * 
 * Sample Input:  5 (number of rows)
 * Sample Output: (see patterns below in the demo)
 * 
 * Explanation:
 *   Each pattern uses nested for loops. The outer loop controls rows and
 *   inner loops control the number of spaces and stars in each row.
 *   Understanding nested loops and the relationship between row number
 *   and the count of stars/spaces is key to solving pattern problems.
 * 
 * Time Complexity:  O(n²) for each pattern where n is the number of rows
 * Space Complexity: O(1) (printing directly, no storage)
 */

import java.util.Scanner;

public class StarPatterns {

    // ================================================================
    // Pattern 1: Right-Aligned Triangle (Left-aligned staircase)
    // ================================================================
    // *
    // **
    // ***
    // ****
    // *****
    public static void rightTriangle(int rows) {
        System.out.println("=== Pattern 1: Right Triangle ===");
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ================================================================
    // Pattern 2: Inverted Triangle
    // ================================================================
    // *****
    // ****
    // ***
    // **
    // *
    public static void invertedTriangle(int rows) {
        System.out.println("=== Pattern 2: Inverted Triangle ===");
        for (int i = rows; i >= 1; i--) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ================================================================
    // Pattern 3: Pyramid (Centered Triangle)
    // ================================================================
    //     *
    //    ***
    //   *****
    //  *******
    // *********
    public static void pyramid(int rows) {
        System.out.println("=== Pattern 3: Pyramid ===");
        for (int i = 1; i <= rows; i++) {
            // Print leading spaces
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            // Print stars
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ================================================================
    // Pattern 4: Diamond
    // ================================================================
    //     *
    //    ***
    //   *****
    //  *******
    // *********
    //  *******
    //   *****
    //    ***
    //     *
    public static void diamond(int rows) {
        System.out.println("=== Pattern 4: Diamond ===");
        // Upper half (including middle row)
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        // Lower half
        for (int i = rows - 1; i >= 1; i--) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ================================================================
    // Pattern 5: Hollow Rectangle
    // ================================================================
    // *****
    // *   *
    // *   *
    // *   *
    // *****
    public static void hollowRectangle(int rows, int cols) {
        System.out.println("=== Pattern 5: Hollow Rectangle (" + rows + "x" + cols + ") ===");
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                // Print star if on border, otherwise space
                if (i == 1 || i == rows || j == 1 || j == cols) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // ================================================================
    // BONUS Patterns
    // ================================================================

    // Bonus 1: Left-aligned inverted triangle (mirror of right triangle)
    //     *
    //    **
    //   ***
    //  ****
    // *****
    public static void leftAlignedTriangle(int rows) {
        System.out.println("=== Bonus: Left-Aligned Triangle (Right-justified) ===");
        for (int i = 1; i <= rows; i++) {
            // Leading spaces
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            // Stars
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Bonus 2: Inverted Pyramid
    // *********
    //  *******
    //   *****
    //    ***
    //     *
    public static void invertedPyramid(int rows) {
        System.out.println("=== Bonus: Inverted Pyramid ===");
        for (int i = rows; i >= 1; i--) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Bonus 3: Hollow Pyramid
    //     *
    //    * *
    //   *   *
    //  *     *
    // *********
    public static void hollowPyramid(int rows) {
        System.out.println("=== Bonus: Hollow Pyramid ===");
        for (int i = 1; i <= rows; i++) {
            // Leading spaces
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            // Stars and inner spaces
            for (int j = 1; j <= 2 * i - 1; j++) {
                if (i == rows || j == 1 || j == 2 * i - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Bonus 4: Hollow Diamond
    //     *
    //    * *
    //   *   *
    //  *     *
    // *       *
    //  *     *
    //   *   *
    //    * *
    //     *
    public static void hollowDiamond(int rows) {
        System.out.println("=== Bonus: Hollow Diamond ===");
        // Upper half
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                if (j == 1 || j == 2 * i - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        // Lower half
        for (int i = rows - 1; i >= 1; i--) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                if (j == 1 || j == 2 * i - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Bonus 5: Number Pyramid
    //     1
    //    1 2
    //   1 2 3
    //  1 2 3 4
    // 1 2 3 4 5
    public static void numberPyramid(int rows) {
        System.out.println("=== Bonus: Number Pyramid ===");
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Bonus 6: Butterfly Pattern
    // *       *
    // **     **
    // ***   ***
    // **** ****
    // *********
    // **** ****
    // ***   ***
    // **     **
    // *       *
    public static void butterfly(int rows) {
        System.out.println("=== Bonus: Butterfly Pattern ===");
        // Upper half
        for (int i = 1; i <= rows; i++) {
            // Left stars
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            // Middle spaces
            for (int j = 1; j <= 2 * (rows - i) - 1; j++) {
                System.out.print(" ");
            }
            // Right stars (skip for the middle row where spaces = 0)
            if (i == rows) {
                // Middle row: stars already cover everything minus one
                for (int j = 1; j < i; j++) {
                    System.out.print("*");
                }
            } else {
                for (int j = 1; j <= i; j++) {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        // Lower half
        for (int i = rows - 1; i >= 1; i--) {
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            for (int j = 1; j <= 2 * (rows - i) - 1; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Bonus 7: Sandglass (Inverted Diamond / Hourglass)
    // *********
    //  *******
    //   *****
    //    ***
    //     *
    //    ***
    //   *****
    //  *******
    // *********
    public static void sandglass(int rows) {
        System.out.println("=== Bonus: Sandglass (Hourglass) ===");
        // Upper inverted pyramid
        for (int i = rows; i >= 1; i--) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        // Lower pyramid (starting from row 2 to avoid duplicate middle)
        for (int i = 2; i <= rows; i++) {
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows for patterns: ");
        int rows = scanner.nextInt();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("   STAR PATTERNS WITH " + rows + " ROWS");
        System.out.println("=".repeat(50) + "\n");

        // --- 5 Required Patterns ---
        rightTriangle(rows);
        invertedTriangle(rows);
        pyramid(rows);
        diamond(rows);
        hollowRectangle(rows, rows);

        // --- Bonus Patterns ---
        System.out.println("=".repeat(50));
        System.out.println("   BONUS PATTERNS");
        System.out.println("=".repeat(50) + "\n");

        leftAlignedTriangle(rows);
        invertedPyramid(rows);
        hollowPyramid(rows);
        hollowDiamond(rows);
        numberPyramid(rows);
        butterfly(rows);
        sandglass(rows);

        scanner.close();
    }
}
