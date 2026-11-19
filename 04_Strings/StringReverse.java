/*
 * Program 47: String Reverse
 * 
 * Problem Statement:
 *   Reverse a string using four different approaches:
 *     1. StringBuilder's built-in reverse()
 *     2. Character array swap
 *     3. Recursion
 *     4. Two-pointer swap technique
 * 
 * Sample Input:  "Hello World"
 * Sample Output: "dlroW olleH"
 * 
 * Explanation:
 *   Each method produces the same result but demonstrates a different technique.
 *   - StringBuilder: leverages Java's built-in mutable string class.
 *   - Char array: convert to char[], swap from ends toward center.
 *   - Recursion: take last char + reverse(remaining).
 *   - Two-pointer: classic in-place swap with left/right indices on a char array.
 * 
 * Time Complexity: O(n) for all four approaches, where n = string length
 * Space Complexity: O(n) for all (new string/array created)
 */

public class StringReverse {

    // =========================================================================
    // Method 1: Using StringBuilder
    // =========================================================================
    public static String reverseWithStringBuilder(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    // =========================================================================
    // Method 2: Using Character Array (simple loop)
    // =========================================================================
    public static String reverseWithCharArray(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length;
        char[] reversed = new char[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = chars[n - 1 - i];
        }
        return new String(reversed);
    }

    // =========================================================================
    // Method 3: Using Recursion
    // =========================================================================
    public static String reverseWithRecursion(String s) {
        // Base case: empty or single character
        if (s == null || s.length() <= 1) {
            return s;
        }
        // Take the last character and recurse on the rest
        return s.charAt(s.length() - 1) + reverseWithRecursion(s.substring(0, s.length() - 1));
    }

    // =========================================================================
    // Method 4: Two-Pointer Swap (in-place on char array)
    // =========================================================================
    public static String reverseWithTwoPointers(String s) {
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            // Swap characters at left and right
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    // =========================================================================
    // Bonus: Reverse words in a sentence (keeps word order reversed)
    // "Hello World Java" -> "Java World Hello"
    // =========================================================================
    public static String reverseWords(String s) {
        String[] words = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) sb.append(" ");
        }
        return sb.toString();
    }

    // =========================================================================
    // Main: Demonstrate all methods
    // =========================================================================
    public static void main(String[] args) {
        String[] testCases = {
            "Hello World",
            "Java",
            "abcdef",
            "racecar",          // palindrome
            "A",                // single char
            "",                 // empty string
            "Hello World Java"  // multi-word
        };

        for (String test : testCases) {
            System.out.println("Original           : \"" + test + "\"");
            System.out.println("  StringBuilder    : \"" + reverseWithStringBuilder(test) + "\"");
            System.out.println("  Char Array       : \"" + reverseWithCharArray(test) + "\"");
            System.out.println("  Recursion        : \"" + reverseWithRecursion(test) + "\"");
            System.out.println("  Two-Pointer      : \"" + reverseWithTwoPointers(test) + "\"");
            System.out.println("  Reverse Words    : \"" + reverseWords(test) + "\"");
            System.out.println();
        }

        // Step-by-step trace for two-pointer approach
        System.out.println("=== Two-Pointer Step-by-Step Trace for \"abcdef\" ===");
        traceReverse("abcdef");
    }

    /**
     * Visual trace of the two-pointer swap approach.
     */
    private static void traceReverse(String s) {
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        int step = 1;

        System.out.println("Initial: " + new String(chars));
        while (left < right) {
            System.out.printf("Step %d: swap chars[%d]='%c' <-> chars[%d]='%c'  =>  ",
                    step, left, chars[left], right, chars[right]);
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            System.out.println(new String(chars));
            left++;
            right--;
            step++;
        }
        System.out.println("Result : " + new String(chars));
    }
}
