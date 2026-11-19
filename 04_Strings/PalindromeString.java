/*
 * Program 48: Palindrome String Checker
 * 
 * Problem Statement:
 *   Check if a string is a palindrome. A palindrome reads the same forwards and
 *   backwards. Handle:
 *     - Case-insensitivity ("Racecar" is a palindrome)
 *     - Ignoring non-alphanumeric characters ("A man, a plan, a canal: Panama")
 * 
 * Sample Input:  "A man, a plan, a canal: Panama"
 * Sample Output: true (it is a palindrome)
 * 
 * Sample Input:  "hello"
 * Sample Output: false
 * 
 * Explanation:
 *   1. Simple check: compare string with its reverse.
 *   2. Two-pointer check: use left/right pointers moving inward.
 *   3. Clean check: strip non-alphanumeric chars, convert to lowercase, then check.
 *   The two-pointer approach is optimal: O(n) time, O(1) extra space (if done on char array).
 * 
 * Time Complexity: O(n) where n = length of the string
 * Space Complexity: O(n) for the cleaned string approach; O(1) extra for two-pointer on char array
 */

public class PalindromeString {

    // =========================================================================
    // Method 1: Simple reverse comparison
    // =========================================================================
    public static boolean isPalindromeSimple(String s) {
        String reversed = new StringBuilder(s).reverse().toString();
        return s.equals(reversed);
    }

    // =========================================================================
    // Method 2: Two-pointer approach (case-sensitive, no filtering)
    // =========================================================================
    public static boolean isPalindromeTwoPointer(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // =========================================================================
    // Method 3: Case-insensitive, ignoring non-alphanumeric characters
    //   This is the classic LeetCode "Valid Palindrome" approach.
    // =========================================================================
    public static boolean isPalindromeClean(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            // Skip non-alphanumeric from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            // Compare (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // =========================================================================
    // Method 4: Recursive check
    // =========================================================================
    public static boolean isPalindromeRecursive(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return isPalindromeRecursive(s, left + 1, right - 1);
    }

    // =========================================================================
    // Bonus: Check if a string can become a palindrome by removing at most 1 char
    //   (LeetCode 680 - Valid Palindrome II)
    // =========================================================================
    public static boolean canBePalindromeByRemovingOne(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                // Try removing left char OR right char
                return isPalindromeRange(s, left + 1, right)
                    || isPalindromeRange(s, left, right - 1);
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean isPalindromeRange(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("=== Simple Palindrome Check (case-sensitive, no filtering) ===");
        String[] simpleTests = {"racecar", "madam", "hello", "abba", "a", ""};
        for (String s : simpleTests) {
            System.out.printf("  \"%s\"  =>  simple=%b,  twoPointer=%b,  recursive=%b%n",
                    s,
                    isPalindromeSimple(s),
                    isPalindromeTwoPointer(s),
                    isPalindromeRecursive(s, 0, s.length() - 1));
        }

        System.out.println();
        System.out.println("=== Clean Palindrome Check (case-insensitive, ignore non-alphanum) ===");
        String[] cleanTests = {
            "A man, a plan, a canal: Panama",
            "race a car",
            "Was it a car or a cat I saw?",
            "No 'x' in Nixon",
            "12321",
            "Hello, olleH"
        };
        for (String s : cleanTests) {
            System.out.printf("  \"%s\"  =>  %b%n", s, isPalindromeClean(s));
        }

        System.out.println();
        System.out.println("=== Can Become Palindrome by Removing at Most 1 Character ===");
        String[] almostTests = {"abca", "racecar", "abcdef", "deeee"};
        for (String s : almostTests) {
            System.out.printf("  \"%s\"  =>  %b%n", s, canBePalindromeByRemovingOne(s));
        }

        System.out.println();
        System.out.println("=== Step-by-Step Trace (Clean Check) ===");
        traceCleanPalindrome("A man, a plan, a canal: Panama");
    }

    /**
     * Visual trace showing the two-pointer palindrome check with filtering.
     */
    private static void traceCleanPalindrome(String s) {
        System.out.println("Input: \"" + s + "\"");
        int left = 0, right = s.length() - 1;
        int step = 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;

            char lc = Character.toLowerCase(s.charAt(left));
            char rc = Character.toLowerCase(s.charAt(right));
            System.out.printf("  Step %2d: [%2d]='%c' vs [%2d]='%c'  =>  %s%n",
                    step, left, lc, right, rc, (lc == rc ? "MATCH" : "MISMATCH"));

            if (lc != rc) {
                System.out.println("Result: NOT a palindrome");
                return;
            }
            left++;
            right--;
            step++;
        }
        System.out.println("Result: IS a palindrome ✓");
    }
}
