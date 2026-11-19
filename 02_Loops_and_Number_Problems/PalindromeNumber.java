/*
 * Program 19: Palindrome Number
 * 
 * Problem Statement:
 *   Check if a given integer is a palindrome. A palindrome number reads the
 *   same forwards and backwards (e.g., 121, 1331, 12321). Handle negative
 *   numbers (they are NOT palindromes). Also demonstrate the string-based
 *   and half-reversal approaches.
 * 
 * Sample Input:  12321
 * Sample Output: 12321 is a palindrome.
 * 
 * Sample Input:  12345
 * Sample Output: 12345 is NOT a palindrome.
 * 
 * Sample Input:  -121
 * Sample Output: -121 is NOT a palindrome (negative numbers are not palindromes).
 * 
 * Explanation:
 *   Method 1 (Full Reversal): Reverse the entire number and compare with original.
 *   Method 2 (Half Reversal - Optimal): Reverse only the second half and compare
 *     with the first half — avoids potential overflow and is more efficient.
 *   Method 3 (String Conversion): Convert to string and use two-pointer comparison.
 * 
 * Time Complexity:  O(d) where d is the number of digits
 * Space Complexity: O(1) for numeric methods, O(d) for string method
 */

import java.util.Scanner;

public class PalindromeNumber {

    // --- Method 1: Full digit reversal ---
    public static boolean isPalindromeFullReverse(int number) {
        // Negative numbers are not palindromes
        if (number < 0) return false;

        int original = number;
        int reversed = 0;

        while (number > 0) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
            number /= 10;
        }

        return original == reversed;
    }

    // --- Method 2: Half reversal (LeetCode optimal approach) ---
    // Only reverse the second half and compare with the first half
    // Avoids integer overflow for very large numbers
    public static boolean isPalindromeHalfReverse(int number) {
        // Negative numbers or numbers ending in 0 (except 0 itself) are not palindromes
        if (number < 0 || (number % 10 == 0 && number != 0)) {
            return false;
        }

        int reversedHalf = 0;
        while (number > reversedHalf) {
            reversedHalf = reversedHalf * 10 + number % 10;
            number /= 10;
        }

        // For even-length: number == reversedHalf
        // For odd-length:  number == reversedHalf / 10 (middle digit is in reversedHalf)
        return number == reversedHalf || number == reversedHalf / 10;
    }

    // --- Method 3: String-based with two pointers ---
    public static boolean isPalindromeString(int number) {
        String str = String.valueOf(number);
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // --- Method 4: Recursive approach ---
    public static boolean isPalindromeRecursive(int number) {
        if (number < 0) return false;
        String str = String.valueOf(number);
        return isPalindromeHelper(str, 0, str.length() - 1);
    }

    private static boolean isPalindromeHelper(String str, int left, int right) {
        if (left >= right) return true;
        if (str.charAt(left) != str.charAt(right)) return false;
        return isPalindromeHelper(str, left + 1, right - 1);
    }

    // --- Method 5: Find the nearest palindrome ---
    public static int nearestPalindrome(int number) {
        if (isPalindromeFullReverse(Math.abs(number))) {
            return number;
        }

        int lower = Math.abs(number) - 1;
        int upper = Math.abs(number) + 1;

        while (true) {
            if (isPalindromeFullReverse(lower)) return lower;
            if (isPalindromeFullReverse(upper)) return upper;
            lower--;
            upper++;
        }
    }

    // --- Method 6: Find all palindromes in a range ---
    public static void findPalindromesInRange(int start, int end) {
        System.out.println("Palindromes between " + start + " and " + end + ":");
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (isPalindromeHalfReverse(i)) {
                System.out.print(i + " ");
                count++;
                if (count % 10 == 0) System.out.println(); // line break every 10
            }
        }
        System.out.println("\nTotal: " + count + " palindromes found.");
    }

    // --- Method 7: Check if number can become a palindrome by rearranging digits ---
    public static boolean canFormPalindrome(int number) {
        int[] digitCount = new int[10];
        number = Math.abs(number);
        while (number > 0) {
            digitCount[number % 10]++;
            number /= 10;
        }
        int oddCount = 0;
        for (int count : digitCount) {
            if (count % 2 != 0) oddCount++;
        }
        return oddCount <= 1; // At most one digit can have an odd count
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Check a user-entered number ---
        System.out.print("Enter a number to check if it's a palindrome: ");
        int number = scanner.nextInt();

        System.out.println("\n=== Palindrome Check for " + number + " ===");
        System.out.println("Full Reversal:   " + (isPalindromeFullReverse(number) ? "PALINDROME" : "NOT a palindrome"));
        System.out.println("Half Reversal:   " + (isPalindromeHalfReverse(number) ? "PALINDROME" : "NOT a palindrome"));
        System.out.println("String Method:   " + (isPalindromeString(number) ? "PALINDROME" : "NOT a palindrome"));
        System.out.println("Recursive:       " + (isPalindromeRecursive(number) ? "PALINDROME" : "NOT a palindrome"));

        // --- Demo 2: Test with various numbers ---
        System.out.println("\n=== Testing Various Numbers ===");
        int[] testCases = {0, 1, 7, 11, 121, 123, 1221, 1331, 12321, 12345, -121, 1000021};
        System.out.printf("%-12s %-15s %-15s%n", "Number", "Palindrome?", "Nearest Palindrome");
        System.out.println("-".repeat(45));
        for (int test : testCases) {
            boolean isPalin = isPalindromeHalfReverse(test);
            int nearest = nearestPalindrome(test);
            System.out.printf("%-12d %-15s %-15d%n", test, isPalin ? "YES" : "NO", nearest);
        }

        // --- Demo 3: Palindromes in a range ---
        System.out.println("\n=== Palindromes in a Range ===");
        findPalindromesInRange(1, 200);

        System.out.println();
        findPalindromesInRange(100, 1000);

        // --- Demo 4: Can digits form a palindrome? ---
        System.out.println("\n=== Can Digits Form a Palindrome? ===");
        int[] rearrangeTests = {121, 123, 1122, 1123, 11233, 1234};
        for (int test : rearrangeTests) {
            System.out.println(test + " -> Digits can form palindrome: " 
                               + (canFormPalindrome(test) ? "YES" : "NO"));
        }

        // --- Demo 5: Explain the half-reversal algorithm step by step ---
        System.out.println("\n=== Half-Reversal Algorithm Walkthrough (12321) ===");
        int walkthrough = 12321;
        int reversedHalf = 0;
        System.out.println("Original number: " + walkthrough);
        int step = 0;
        while (walkthrough > reversedHalf) {
            reversedHalf = reversedHalf * 10 + walkthrough % 10;
            walkthrough /= 10;
            step++;
            System.out.println("Step " + step + ": number=" + walkthrough 
                               + ", reversedHalf=" + reversedHalf);
        }
        System.out.println("Compare: number=" + walkthrough 
                           + ", reversedHalf/10=" + reversedHalf / 10);
        System.out.println("Result: " + (walkthrough == reversedHalf / 10 ? "PALINDROME" : "NOT"));

        scanner.close();
    }
}
