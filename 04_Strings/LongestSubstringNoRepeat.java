/*
 * Program 52: Longest Substring Without Repeating Characters
 * 
 * Problem Statement:
 *   Given a string, find the length of the longest substring without repeating
 *   characters. Also return the actual substring.
 *   (LeetCode 3 - Longest Substring Without Repeating Characters)
 * 
 * Sample Input:  "abcabcbb"
 * Sample Output: 3 ("abc")
 * 
 * Sample Input:  "bbbbb"
 * Sample Output: 1 ("b")
 * 
 * Sample Input:  "pwwkew"
 * Sample Output: 3 ("wke")
 * 
 * Explanation:
 *   Use the Sliding Window technique with two pointers (left, right):
 *   - Expand right pointer to include new characters.
 *   - If a character is already in the current window, shrink from the left
 *     until the duplicate is removed.
 *   - Track the maximum window size seen.
 *   Three implementations shown:
 *     1. HashSet-based sliding window
 *     2. HashMap-based (jump left pointer to last occurrence + 1)
 *     3. Integer array (ASCII optimization)
 * 
 * Time Complexity: O(n) where n = string length (each character visited at most twice)
 * Space Complexity: O(min(m, n)) where m = size of the character set
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringNoRepeat {

    // =========================================================================
    // Approach 1: Sliding Window with HashSet
    //   - Expand right; if duplicate found, shrink from left until no duplicate.
    // =========================================================================
    public static int[] longestSubstringHashSet(String s) {
        if (s == null || s.isEmpty()) return new int[]{0, 0, 0}; // {length, start, end}

        Set<Character> window = new HashSet<>();
        int left = 0;
        int maxLen = 0;
        int maxStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // Shrink window from left until no duplicate
            while (window.contains(c)) {
                window.remove(s.charAt(left));
                left++;
            }

            window.add(c);

            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                maxStart = left;
            }
        }

        return new int[]{maxLen, maxStart, maxStart + maxLen - 1};
    }

    // =========================================================================
    // Approach 2: Sliding Window with HashMap (Optimized)
    //   - Store last index of each character.
    //   - When duplicate found, jump left directly past the previous occurrence.
    // =========================================================================
    public static int[] longestSubstringHashMap(String s) {
        if (s == null || s.isEmpty()) return new int[]{0, 0, 0};

        Map<Character, Integer> lastIndex = new HashMap<>();
        int left = 0;
        int maxLen = 0;
        int maxStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                // Jump left past the previous occurrence
                left = lastIndex.get(c) + 1;
            }

            lastIndex.put(c, right);

            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                maxStart = left;
            }
        }

        return new int[]{maxLen, maxStart, maxStart + maxLen - 1};
    }

    // =========================================================================
    // Approach 3: Sliding Window with int[128] (ASCII optimization)
    //   - Fastest for ASCII strings: use array index as character, value as last pos + 1.
    // =========================================================================
    public static int[] longestSubstringArray(String s) {
        if (s == null || s.isEmpty()) return new int[]{0, 0, 0};

        int[] charIndex = new int[128]; // stores last seen index + 1
        // Initialize to 0 (meaning not seen, since valid positions start from 1)
        int left = 0;
        int maxLen = 0;
        int maxStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If this char was seen and its stored position is within current window
            if (charIndex[c] > left) {
                left = charIndex[c]; // jump left past previous occurrence
            }

            charIndex[c] = right + 1; // store position + 1

            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                maxStart = left;
            }
        }

        return new int[]{maxLen, maxStart, maxStart + maxLen - 1};
    }

    // =========================================================================
    // Brute Force (for comparison / verification): O(n^3)
    // =========================================================================
    public static int longestSubstringBruteForce(String s) {
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (allUnique(s, i, j)) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    private static boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            if (!set.add(s.charAt(i))) return false;
        }
        return true;
    }

    // =========================================================================
    // Helper: format result
    // =========================================================================
    private static String formatResult(String s, int[] result) {
        if (result[0] == 0) return "length=0, substring=\"\"";
        String sub = s.substring(result[1], result[2] + 1);
        return String.format("length=%d, substring=\"%s\" [index %d..%d]",
                result[0], sub, result[1], result[2]);
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║      Longest Substring Without Repeating Characters         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        String[] testCases = {
            "abcabcbb",     // 3 -> "abc"
            "bbbbb",        // 1 -> "b"
            "pwwkew",       // 3 -> "wke"
            "dvdf",         // 3 -> "vdf"
            "abcdef",       // 6 -> "abcdef" (all unique)
            "aab",          // 2 -> "ab"
            "",             // 0
            "a",            // 1 -> "a"
            "abba",         // 2 -> "ab" or "ba"
            "tmmzuxt",      // 5 -> "mzuxt"
        };

        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");

            int[] r1 = longestSubstringHashSet(test);
            int[] r2 = longestSubstringHashMap(test);
            int[] r3 = longestSubstringArray(test);

            System.out.println("  HashSet  : " + formatResult(test, r1));
            System.out.println("  HashMap  : " + formatResult(test, r2));
            System.out.println("  Array    : " + formatResult(test, r3));

            // Verify with brute force for small strings
            if (test.length() <= 100) {
                int bruteLen = longestSubstringBruteForce(test);
                boolean match = (r1[0] == bruteLen && r2[0] == bruteLen && r3[0] == bruteLen);
                System.out.println("  BruteForce: length=" + bruteLen + " (verified: " + match + ")");
            }
            System.out.println();
        }

        // =====================================================================
        // Step-by-Step Sliding Window Trace
        // =====================================================================
        System.out.println("=== Step-by-Step Sliding Window Trace for \"abcabcbb\" ===");
        traceSlidingWindow("abcabcbb");
    }

    /**
     * Visual trace of the sliding window (HashMap approach).
     */
    private static void traceSlidingWindow(String s) {
        System.out.println("Input: \"" + s + "\"");
        System.out.println();

        Map<Character, Integer> lastIndex = new HashMap<>();
        int left = 0;
        int maxLen = 0;
        int maxStart = 0;

        System.out.printf("%-6s %-6s %-8s %-6s %-20s %-15s%n",
                "right", "char", "action", "left", "window", "maxLen");
        System.out.println("-".repeat(70));

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            String action;

            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                action = "SHRINK";
                left = lastIndex.get(c) + 1;
            } else {
                action = "EXPAND";
            }

            lastIndex.put(c, right);

            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                maxStart = left;
            }

            String window = "\"" + s.substring(left, right + 1) + "\"";
            System.out.printf("%-6d %-6c %-8s %-6d %-20s %-15d%n",
                    right, c, action, left, window, maxLen);
        }

        System.out.println();
        System.out.println("Result: longest = \"" + s.substring(maxStart, maxStart + maxLen)
                + "\" (length " + maxLen + ")");
    }
}
