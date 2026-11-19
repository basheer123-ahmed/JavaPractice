/*
 * Program 51: String Permutations
 * 
 * Problem Statement:
 *   Generate all permutations of a string using recursion/backtracking.
 *   Also handle cases with duplicate characters to generate only unique permutations.
 * 
 * Sample Input:  "ABC"
 * Sample Output: ABC, ACB, BAC, BCA, CAB, CBA  (3! = 6 permutations)
 * 
 * Sample Input:  "AAB"
 * Sample Output: AAB, ABA, BAA  (3 unique permutations)
 * 
 * Explanation:
 *   Approach 1 (Fix-and-Recur): Fix each character at the current position,
 *     then recursively permute the remaining characters.
 *   Approach 2 (Backtracking with used[] array): Build permutations character
 *     by character, marking each as used/unused.
 *   For duplicates: sort the string first, then skip duplicate characters at
 *     the same recursion level.
 * 
 * Time Complexity: O(n! * n) — n! permutations, each of length n to build/print
 * Space Complexity: O(n! * n) to store all permutations; O(n) for recursion stack
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringPermutations {

    // =========================================================================
    // Approach 1: Fix-and-Recur (swap-based)
    //   - Swap each character to the front position, recurse on the rest.
    //   - Uses a HashSet to avoid duplicates at each level.
    // =========================================================================
    public static List<String> permuteBySwap(String s) {
        List<String> result = new ArrayList<>();
        char[] chars = s.toCharArray();
        permuteSwapHelper(chars, 0, result);
        return result;
    }

    private static void permuteSwapHelper(char[] chars, int index, List<String> result) {
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }

        Set<Character> used = new HashSet<>(); // avoid duplicate swaps at this level
        for (int i = index; i < chars.length; i++) {
            if (used.contains(chars[i])) continue;  // skip duplicate
            used.add(chars[i]);

            swap(chars, index, i);                   // fix chars[i] at position 'index'
            permuteSwapHelper(chars, index + 1, result);
            swap(chars, index, i);                   // backtrack
        }
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    // =========================================================================
    // Approach 2: Backtracking with used[] boolean array
    //   - Sort the input to handle duplicates naturally.
    //   - Build permutation character by character.
    // =========================================================================
    public static List<String> permuteByBacktracking(String s) {
        List<String> result = new ArrayList<>();
        char[] chars = s.toCharArray();
        Arrays.sort(chars);                          // sort to group duplicates
        boolean[] used = new boolean[chars.length];
        permuteBacktrackHelper(chars, used, new StringBuilder(), result);
        return result;
    }

    private static void permuteBacktrackHelper(char[] chars, boolean[] used,
                                                StringBuilder current, List<String> result) {
        if (current.length() == chars.length) {
            result.add(current.toString());
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            if (used[i]) continue;
            // Skip duplicates: if same char as previous AND previous is not used at this level
            if (i > 0 && chars[i] == chars[i - 1] && !used[i - 1]) continue;

            used[i] = true;
            current.append(chars[i]);

            permuteBacktrackHelper(chars, used, current, result);

            current.deleteCharAt(current.length() - 1); // backtrack
            used[i] = false;
        }
    }

    // =========================================================================
    // Approach 3: Simple recursion (no duplicates handling, educational)
    //   - Fix first char, permute rest, insert first char at each position.
    // =========================================================================
    public static List<String> permuteSimple(String s) {
        List<String> result = new ArrayList<>();
        if (s.length() <= 1) {
            result.add(s);
            return result;
        }

        char firstChar = s.charAt(0);
        String remaining = s.substring(1);
        List<String> subPermutations = permuteSimple(remaining);

        for (String perm : subPermutations) {
            for (int i = 0; i <= perm.length(); i++) {
                String newPerm = perm.substring(0, i) + firstChar + perm.substring(i);
                result.add(newPerm);
            }
        }
        return result;
    }

    // =========================================================================
    // Count permutations mathematically: n! / (d1! * d2! * ... * dk!)
    // =========================================================================
    public static long countUniquePermutations(String s) {
        int[] freq = new int[128]; // ASCII
        for (char c : s.toCharArray()) freq[c]++;

        long numerator = factorial(s.length());
        long denominator = 1;
        for (int f : freq) {
            if (f > 1) denominator *= factorial(f);
        }
        return numerator / denominator;
    }

    private static long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║              String Permutations Generator          ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println();

        // Test 1: No duplicates
        String test1 = "ABC";
        System.out.println("=== Permutations of \"" + test1 + "\" (no duplicates) ===");
        System.out.println("Expected count: " + countUniquePermutations(test1));
        System.out.println();

        System.out.println("Approach 1 (Swap-based):");
        List<String> result1 = permuteBySwap(test1);
        printPermutations(result1);

        System.out.println("Approach 2 (Backtracking):");
        List<String> result2 = permuteByBacktracking(test1);
        printPermutations(result2);

        System.out.println("Approach 3 (Simple Recursion):");
        List<String> result3 = permuteSimple(test1);
        printPermutations(result3);

        // Test 2: With duplicates
        String test2 = "AAB";
        System.out.println("=== Permutations of \"" + test2 + "\" (with duplicates) ===");
        System.out.println("Expected unique count: " + countUniquePermutations(test2));
        System.out.println();

        System.out.println("Approach 1 (Swap-based, handles duplicates):");
        List<String> result4 = permuteBySwap(test2);
        printPermutations(result4);

        System.out.println("Approach 2 (Backtracking, handles duplicates):");
        List<String> result5 = permuteByBacktracking(test2);
        printPermutations(result5);

        // Test 3: Single character
        String test3 = "X";
        System.out.println("=== Permutations of \"" + test3 + "\" ===");
        List<String> result6 = permuteBySwap(test3);
        printPermutations(result6);

        // Test 4: Two characters with duplicates
        String test4 = "AA";
        System.out.println("=== Permutations of \"" + test4 + "\" ===");
        System.out.println("Expected unique count: " + countUniquePermutations(test4));
        List<String> result7 = permuteBySwap(test4);
        printPermutations(result7);

        // Test 5: 4-character string
        String test5 = "ABCD";
        System.out.println("=== Permutations of \"" + test5 + "\" ===");
        System.out.println("Expected count: " + countUniquePermutations(test5));
        List<String> result8 = permuteBySwap(test5);
        printPermutations(result8);

        // =====================================================================
        // Recursion Tree Trace for "ABC"
        // =====================================================================
        System.out.println("=== Recursion Tree Trace for \"ABC\" (Swap Approach) ===");
        tracePermutations("ABC".toCharArray(), 0, 0);
    }

    private static void printPermutations(List<String> perms) {
        System.out.println("Count: " + perms.size());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < perms.size(); i++) {
            sb.append(perms.get(i));
            if (i < perms.size() - 1) sb.append(", ");
            if ((i + 1) % 10 == 0) sb.append("\n");
        }
        System.out.println(sb.toString());
        System.out.println();
    }

    /**
     * Trace showing the recursive calls and swaps.
     */
    private static void tracePermutations(char[] chars, int index, int depth) {
        String indent = "  ".repeat(depth);
        if (index == chars.length) {
            System.out.println(indent + "→ RESULT: " + new String(chars));
            return;
        }
        for (int i = index; i < chars.length; i++) {
            System.out.printf("%sSwap [%d]='%c' with [%d]='%c': %s%n",
                    indent, index, chars[index], i, chars[i], "");
            swap(chars, index, i);
            System.out.printf("%s  State: %s (fixing position %d as '%c')%n",
                    indent, new String(chars), index, chars[index]);
            tracePermutations(chars, index + 1, depth + 1);
            swap(chars, index, i); // backtrack
        }
    }
}
