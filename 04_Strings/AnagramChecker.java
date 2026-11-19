/*
 * Program 49: Anagram Checker
 * 
 * Problem Statement:
 *   Check if two strings are anagrams of each other. Two strings are anagrams
 *   if they contain the same characters with the same frequencies, regardless
 *   of order. Implement using:
 *     1. Sorting approach
 *     2. Frequency counting (array) approach
 *     3. Frequency counting (HashMap) approach
 * 
 * Sample Input:  "listen", "silent"
 * Sample Output: true (they are anagrams)
 * 
 * Sample Input:  "hello", "world"
 * Sample Output: false
 * 
 * Explanation:
 *   - Sorting: sort both strings and compare. If equal, they are anagrams.
 *   - Frequency Array: use a 26-element int array; increment for first string,
 *     decrement for second. If all zeros at the end, they are anagrams.
 *   - HashMap: similar logic using a map (supports Unicode/extended chars).
 * 
 * Time Complexity:
 *   - Sorting: O(n log n) where n = max length of the two strings
 *   - Frequency Array: O(n) 
 *   - HashMap: O(n)
 * Space Complexity:
 *   - Sorting: O(n) for the sorted arrays
 *   - Frequency Array: O(1) (fixed 26-element array)
 *   - HashMap: O(k) where k = number of distinct characters
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnagramChecker {

    // =========================================================================
    // Method 1: Sorting Approach
    // =========================================================================
    public static boolean isAnagramBySorting(String s1, String s2) {
        // Quick length check
        if (s1.length() != s2.length()) return false;

        char[] arr1 = s1.toLowerCase().toCharArray();
        char[] arr2 = s2.toLowerCase().toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        return Arrays.equals(arr1, arr2);
    }

    // =========================================================================
    // Method 2: Frequency Array (works for lowercase English letters)
    // =========================================================================
    public static boolean isAnagramByFrequencyArray(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        int[] freq = new int[26];
        String lower1 = s1.toLowerCase();
        String lower2 = s2.toLowerCase();

        for (int i = 0; i < lower1.length(); i++) {
            freq[lower1.charAt(i) - 'a']++;
            freq[lower2.charAt(i) - 'a']--;
        }

        for (int count : freq) {
            if (count != 0) return false;
        }
        return true;
    }

    // =========================================================================
    // Method 3: HashMap (supports any characters including Unicode)
    // =========================================================================
    public static boolean isAnagramByHashMap(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        Map<Character, Integer> freqMap = new HashMap<>();
        String lower1 = s1.toLowerCase();
        String lower2 = s2.toLowerCase();

        // Count characters from first string
        for (char c : lower1.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Subtract characters from second string
        for (char c : lower2.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) - 1);
            if (freqMap.get(c) < 0) return false; // early exit
        }

        return true; // All counts must be zero (guaranteed by equal length + no negatives)
    }

    // =========================================================================
    // Bonus: Group Anagrams (given an array of strings, group anagrams together)
    //   e.g., ["eat","tea","tan","ate","nat","bat"] =>
    //         [["eat","tea","ate"], ["tan","nat"], ["bat"]]
    // =========================================================================
    public static Map<String, java.util.List<String>> groupAnagrams(String[] words) {
        Map<String, java.util.List<String>> groups = new HashMap<>();

        for (String word : words) {
            char[] chars = word.toLowerCase().toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            groups.computeIfAbsent(key, k -> new java.util.ArrayList<>()).add(word);
        }

        return groups;
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("=== Anagram Checker ===");
        System.out.println();

        String[][] testPairs = {
            {"listen", "silent"},
            {"Hello", "Olelh"},
            {"triangle", "integral"},
            {"apple", "papel"},
            {"hello", "world"},
            {"abc", "abcd"},
            {"Astronomer", "Moon starer"},  // Not anagram by strict rules (space counts)
            {"anagram", "nagaram"},
            {"", ""},
            {"a", "a"}
        };

        System.out.printf("%-20s %-20s %-10s %-10s %-10s%n",
                "String 1", "String 2", "Sorting", "FreqArray", "HashMap");
        System.out.println("-".repeat(70));

        for (String[] pair : testPairs) {
            String s1 = pair[0], s2 = pair[1];
            // For frequency array, only use if all chars are alphabetic
            boolean canUseArray = s1.chars().allMatch(Character::isLetter)
                               && s2.chars().allMatch(Character::isLetter);

            boolean bySorting = isAnagramBySorting(s1, s2);
            String byArray = canUseArray ? String.valueOf(isAnagramByFrequencyArray(s1, s2)) : "N/A";
            boolean byMap = isAnagramByHashMap(s1, s2);

            System.out.printf("%-20s %-20s %-10b %-10s %-10b%n",
                    "\"" + s1 + "\"", "\"" + s2 + "\"", bySorting, byArray, byMap);
        }

        // =====================================================================
        // Group Anagrams Demo
        // =====================================================================
        System.out.println();
        System.out.println("=== Group Anagrams ===");
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat", "tab"};
        System.out.println("Input : " + Arrays.toString(words));
        Map<String, java.util.List<String>> groups = groupAnagrams(words);
        System.out.println("Groups:");
        int groupNum = 1;
        for (java.util.List<String> group : groups.values()) {
            System.out.println("  Group " + groupNum++ + ": " + group);
        }

        // =====================================================================
        // Step-by-step trace of frequency array approach
        // =====================================================================
        System.out.println();
        System.out.println("=== Step-by-Step Trace: Frequency Array for (\"listen\", \"silent\") ===");
        traceFrequencyArray("listen", "silent");
    }

    /**
     * Visual trace of the frequency array approach.
     */
    private static void traceFrequencyArray(String s1, String s2) {
        int[] freq = new int[26];
        String lower1 = s1.toLowerCase();
        String lower2 = s2.toLowerCase();

        System.out.println("Processing characters:");
        for (int i = 0; i < lower1.length(); i++) {
            char c1 = lower1.charAt(i);
            char c2 = lower2.charAt(i);
            freq[c1 - 'a']++;
            freq[c2 - 'a']--;
            System.out.printf("  i=%d: s1[%d]='%c' (inc), s2[%d]='%c' (dec)  =>  ", i, i, c1, i, c2);
            printNonZeroFreq(freq);
        }

        boolean allZero = true;
        for (int f : freq) {
            if (f != 0) { allZero = false; break; }
        }
        System.out.println("All frequencies zero? " + allZero + " => " + (allZero ? "ANAGRAM ✓" : "NOT an anagram ✗"));
    }

    private static void printNonZeroFreq(int[] freq) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (int i = 0; i < 26; i++) {
            if (freq[i] != 0) {
                if (!first) sb.append(", ");
                sb.append((char)('a' + i)).append(":").append(freq[i]);
                first = false;
            }
        }
        sb.append("}");
        System.out.println(sb.toString().equals("{}") ? "{all zero}" : sb.toString());
    }
}
