/*
 * Program 26: Number To Words
 * 
 * Problem Statement:
 *   Convert an integer (0-9999) to its English word representation.
 *   For example, 123 -> "One Hundred Twenty Three", 5001 -> "Five Thousand One".
 *   Handle edge cases like 0 ("Zero"), teens (11-19), and multiples of 10/100/1000.
 * 
 * Sample Input:  0, 15, 123, 5001, 9999
 * Sample Output:
 *   0 -> "Zero"
 *   15 -> "Fifteen"
 *   123 -> "One Hundred Twenty Three"
 *   5001 -> "Five Thousand One"
 *   9999 -> "Nine Thousand Nine Hundred Ninety Nine"
 * 
 * Explanation:
 *   We break the number into its place values: thousands, hundreds, tens, and ones.
 *   Special handling is needed for the "teens" (11-19) since they don't follow
 *   the regular pattern. We use lookup arrays for ones, teens, and tens words,
 *   then concatenate the appropriate words for each place value.
 * 
 * Time Complexity: O(1) - fixed number of digits (at most 4)
 * Space Complexity: O(1) - fixed-size lookup arrays
 */

import java.util.Scanner;

public class NumberToWords {

    // Lookup arrays for word representations
    private static final String[] ONES = {
        "", "One", "Two", "Three", "Four", "Five",
        "Six", "Seven", "Eight", "Nine"
    };

    private static final String[] TEENS = {
        "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
        "Sixteen", "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] TENS = {
        "", "", "Twenty", "Thirty", "Forty", "Fifty",
        "Sixty", "Seventy", "Eighty", "Ninety"
    };

    /**
     * Converts a number 0-99 to words.
     */
    private static String twoDigitToWords(int num) {
        if (num == 0) {
            return "";
        } else if (num < 10) {
            return ONES[num];
        } else if (num < 20) {
            return TEENS[num - 10];
        } else {
            String tens = TENS[num / 10];
            String ones = ONES[num % 10];
            if (ones.isEmpty()) {
                return tens;
            }
            return tens + " " + ones;
        }
    }

    /**
     * Converts a number 0-999 to words.
     */
    private static String threeDigitToWords(int num) {
        if (num == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        int hundreds = num / 100;
        int remainder = num % 100;

        if (hundreds > 0) {
            sb.append(ONES[hundreds]).append(" Hundred");
            if (remainder > 0) {
                sb.append(" ");
            }
        }

        if (remainder > 0) {
            sb.append(twoDigitToWords(remainder));
        }

        return sb.toString();
    }

    /**
     * Main conversion method: converts 0-9999 to English words.
     */
    public static String convert(int num) {
        if (num < 0 || num > 9999) {
            return "Number out of range (0-9999)";
        }

        if (num == 0) {
            return "Zero";
        }

        StringBuilder sb = new StringBuilder();

        int thousands = num / 1000;
        int remainder = num % 1000;

        if (thousands > 0) {
            sb.append(ONES[thousands]).append(" Thousand");
            if (remainder > 0) {
                sb.append(" ");
            }
        }

        if (remainder > 0) {
            sb.append(threeDigitToWords(remainder));
        }

        return sb.toString();
    }

    /**
     * Extended version: converts any integer to English words (handles negatives and large numbers).
     * Supports up to billions for educational purposes.
     */
    public static String convertExtended(int num) {
        if (num == 0) return "Zero";

        if (num < 0) {
            // Handle Integer.MIN_VALUE edge case
            if (num == Integer.MIN_VALUE) {
                return "Negative Two Billion One Hundred Forty Seven Million " +
                       "Four Hundred Eighty Three Thousand Six Hundred Forty Eight";
            }
            return "Negative " + convertExtended(-num);
        }

        // For numbers 0-9999, use the simpler method
        if (num <= 9999) {
            return convert(num);
        }

        StringBuilder sb = new StringBuilder();
        String[] groups = {"", " Thousand", " Million", " Billion"};

        int groupIndex = 0;
        // We'll build from right to left, then reverse
        String[] parts = new String[4];

        while (num > 0 && groupIndex < 4) {
            int chunk = num % 1000;
            if (chunk != 0) {
                parts[groupIndex] = threeDigitToWords(chunk) + groups[groupIndex];
            }
            num /= 1000;
            groupIndex++;
        }

        // Concatenate from highest group to lowest
        boolean first = true;
        for (int i = groupIndex - 1; i >= 0; i--) {
            if (parts[i] != null) {
                if (!first) sb.append(" ");
                sb.append(parts[i]);
                first = false;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Program 26: Number To Words ===\n");

        // ---- Demo with predefined test cases ----
        System.out.println("--- Predefined Test Cases (0-9999 range) ---");
        int[] testCases = {0, 1, 10, 11, 15, 19, 20, 42, 99, 100, 101, 110,
                           115, 123, 200, 500, 999, 1000, 1001, 1010, 1100,
                           2345, 5001, 7890, 9999};

        for (int num : testCases) {
            System.out.printf("  %4d -> \"%s\"%n", num, convert(num));
        }

        // ---- Demo with extended range ----
        System.out.println("\n--- Extended Range (beyond 9999) ---");
        int[] extendedCases = {10000, 12345, 100000, 1000000, 1234567, 
                               -42, -9999, 2000000000};

        for (int num : extendedCases) {
            System.out.printf("  %,d -> \"%s\"%n", num, convertExtended(num));
        }

        // ---- Detailed walkthrough ----
        System.out.println("\n--- Detailed Walkthrough for 5678 ---");
        int example = 5678;
        System.out.println("  Number: " + example);
        System.out.println("  Thousands digit: " + (example / 1000) + " -> \"" + ONES[example / 1000] + " Thousand\"");
        int rem = example % 1000;
        System.out.println("  Hundreds digit:  " + (rem / 100) + " -> \"" + ONES[rem / 100] + " Hundred\"");
        int lastTwo = rem % 100;
        System.out.println("  Tens digit:      " + (lastTwo / 10) + " -> \"" + TENS[lastTwo / 10] + "\"");
        System.out.println("  Ones digit:      " + (lastTwo % 10) + " -> \"" + ONES[lastTwo % 10] + "\"");
        System.out.println("  Result: \"" + convert(example) + "\"");

        // ---- Edge cases ----
        System.out.println("\n--- Edge Cases ---");
        System.out.println("  Teens (special naming):");
        for (int i = 10; i <= 19; i++) {
            System.out.printf("    %d -> \"%s\"%n", i, convert(i));
        }
        System.out.println("  Multiples of 10:");
        for (int i = 10; i <= 90; i += 10) {
            System.out.printf("    %d -> \"%s\"%n", i, convert(i));
        }

        // ---- Interactive mode ----
        System.out.println("\n--- Interactive Mode ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number (0-9999, or any integer for extended): ");
        if (scanner.hasNextInt()) {
            int userNum = scanner.nextInt();
            if (userNum >= 0 && userNum <= 9999) {
                System.out.println("  Result: \"" + convert(userNum) + "\"");
            }
            System.out.println("  Extended: \"" + convertExtended(userNum) + "\"");
        } else {
            System.out.println("  Invalid input. Please enter an integer.");
        }
        scanner.close();
    }
}
