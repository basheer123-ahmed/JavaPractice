/*
 * Program 23: Reverse Number
 * 
 * Problem Statement:
 *   Reverse the digits of a given integer. Handle negative numbers by preserving
 *   the sign and reversing only the digits. Also handle edge cases like
 *   trailing zeros (which become leading zeros and are dropped) and potential
 *   integer overflow when the reversed number exceeds int range.
 * 
 * Sample Input:  12345
 * Sample Output: 54321
 * 
 * Sample Input:  -987
 * Sample Output: -789
 * 
 * Sample Input:  1200
 * Sample Output: 21 (trailing zeros dropped)
 * 
 * Explanation:
 *   Extract the last digit using modulo (%), add it to the reversed number
 *   after multiplying by 10, then remove the last digit using integer division.
 *   For negative numbers, work with the absolute value and restore the sign.
 * 
 * Time Complexity:  O(d) where d is the number of digits
 * Space Complexity: O(1) for numeric methods, O(d) for string method
 */

import java.util.Scanner;

public class ReverseNumber {

    // --- Method 1: Iterative reversal using modulo and division ---
    public static int reverse(int number) {
        int reversed = 0;
        boolean isNegative = number < 0;
        number = Math.abs(number);

        while (number > 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }

        return isNegative ? -reversed : reversed;
    }

    // --- Method 2: Reversal with overflow check (LeetCode style) ---
    // Returns 0 if reversing causes integer overflow
    public static int reverseSafe(int number) {
        int reversed = 0;

        while (number != 0) {
            int digit = number % 10; // Works correctly for negative numbers in Java
            number /= 10;

            // Check for overflow before multiplying
            if (reversed > Integer.MAX_VALUE / 10 
                || (reversed == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0; // Would overflow positive
            }
            if (reversed < Integer.MIN_VALUE / 10 
                || (reversed == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0; // Would overflow negative
            }

            reversed = reversed * 10 + digit;
        }

        return reversed;
    }

    // --- Method 3: String-based reversal ---
    public static int reverseString(int number) {
        boolean isNegative = number < 0;
        String str = String.valueOf(Math.abs(number));
        String reversed = new StringBuilder(str).reverse().toString();
        int result = Integer.parseInt(reversed);
        return isNegative ? -result : result;
    }

    // --- Method 4: Recursive reversal ---
    public static int reverseRecursive(int number) {
        boolean isNegative = number < 0;
        number = Math.abs(number);
        int result = reverseHelper(number, 0);
        return isNegative ? -result : result;
    }

    private static int reverseHelper(int number, int reversed) {
        if (number == 0) return reversed;
        return reverseHelper(number / 10, reversed * 10 + number % 10);
    }

    // --- Method 5: Reverse using long (handles larger numbers) ---
    public static long reverseLong(long number) {
        boolean isNegative = number < 0;
        number = Math.abs(number);
        long reversed = 0;

        while (number > 0) {
            reversed = reversed * 10 + number % 10;
            number /= 10;
        }

        return isNegative ? -reversed : reversed;
    }

    // --- Method 6: Reverse and check if palindrome ---
    public static boolean isPalindrome(int number) {
        if (number < 0) return false;
        return number == reverse(number);
    }

    // --- Method 7: Reverse specific portion (from position start to end, 0-indexed from right) ---
    public static int reversePartial(int number, int startPos, int endPos) {
        String str = String.valueOf(Math.abs(number));
        int len = str.length();
        // Convert right-indexed positions to left-indexed
        int left = len - 1 - endPos;
        int right = len - 1 - startPos;

        if (left < 0 || right >= len || left > right) {
            return number; // Invalid range
        }

        char[] chars = str.toCharArray();
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }

        int result = Integer.parseInt(new String(chars));
        return number < 0 ? -result : result;
    }

    // --- Method 8: Count digits ---
    public static int countDigits(int number) {
        if (number == 0) return 1;
        number = Math.abs(number);
        int count = 0;
        while (number > 0) {
            count++;
            number /= 10;
        }
        return count;
    }

    // --- Method 9: Explain reversal step by step ---
    public static void explainReversal(int number) {
        System.out.println("Reversing " + number + " step by step:");
        boolean isNegative = number < 0;
        int original = number;
        number = Math.abs(number);
        int reversed = 0;
        int step = 0;

        System.out.printf("  %-6s %-12s %-8s %-12s%n", "Step", "Number", "Digit", "Reversed");
        System.out.println("  " + "-".repeat(42));

        while (number > 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
            step++;
            System.out.printf("  %-6d %-12d %-8d %-12d%n", step, number, digit, reversed);
        }

        if (isNegative) reversed = -reversed;
        System.out.println("  Final result: " + original + " → " + reversed + "\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Reverse a user-entered number ---
        System.out.print("Enter a number to reverse: ");
        int number = scanner.nextInt();

        System.out.println("\n=== Reversing " + number + " ===");
        System.out.println("Iterative:        " + reverse(number));
        System.out.println("Overflow-safe:    " + reverseSafe(number));
        System.out.println("String-based:     " + reverseString(number));
        System.out.println("Recursive:        " + reverseRecursive(number));
        System.out.println("Is palindrome?    " + (isPalindrome(number) ? "YES" : "NO"));

        // --- Demo 2: Step-by-step explanation ---
        System.out.println("\n=== Step-by-Step Explanation ===");
        explainReversal(number);
        explainReversal(12345);
        explainReversal(1200);

        // --- Demo 3: Test with various numbers ---
        System.out.println("=== Test Cases ===");
        int[] testCases = {0, 1, 10, 100, 123, 12321, -456, -100, 1000, 999, 1020304};
        System.out.printf("%-12s %-12s %-12s %-12s%n", "Original", "Reversed", "Digits", "Palindrome?");
        System.out.println("-".repeat(50));
        for (int test : testCases) {
            System.out.printf("%-12d %-12d %-12d %-12s%n", 
                              test, reverse(test), countDigits(test),
                              isPalindrome(test) ? "YES" : "NO");
        }

        // --- Demo 4: Overflow handling ---
        System.out.println("\n=== Overflow Handling ===");
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        int nearOverflow = 1463847412; // Reversal = 2147483641, within range
        int overflow = 1463847413;      // Reversal = 3147483641, overflows int

        System.out.println("reverseSafe(" + nearOverflow + ") = " + reverseSafe(nearOverflow) + " (OK)");
        System.out.println("reverseSafe(" + overflow + ") = " + reverseSafe(overflow) + " (overflow returns 0)");
        System.out.println("reverseLong(" + overflow + ") = " + reverseLong(overflow) + " (using long)");

        // --- Demo 5: Negative numbers ---
        System.out.println("\n=== Negative Number Handling ===");
        int[] negTests = {-1, -12, -123, -1200, -9999};
        for (int test : negTests) {
            System.out.println(test + " → " + reverse(test));
        }

        // --- Demo 6: Trailing zeros ---
        System.out.println("\n=== Trailing Zeros (become leading zeros, dropped) ===");
        int[] trailingZeroTests = {10, 100, 1000, 1200, 50300, 7000080};
        for (int test : trailingZeroTests) {
            System.out.println(test + " → " + reverse(test) 
                               + " (original digits: " + countDigits(test) 
                               + ", reversed digits: " + countDigits(reverse(test)) + ")");
        }

        scanner.close();
    }
}
