/*
 * Program 59: SubstringSearch
 * 
 * Problem Statement:
 *   Implement two substring search (pattern matching) algorithms:
 *     1. Naive (Brute Force) — try every position in the text
 *     2. KMP (Knuth-Morris-Pratt) — precompute a failure/LPS table to skip
 *        redundant comparisons, achieving linear time
 *   Find ALL occurrences of a pattern in a given text, return their starting
 *   indices, and compare performance on large inputs.
 * 
 * Sample Input:
 *   Text:    "AABAACAADAABAABA"
 *   Pattern: "AABA"
 * 
 * Sample Output:
 *   === NAIVE SEARCH ===
 *   Pattern "AABA" found at indices: [0, 9, 12]
 *   Comparisons made: 41
 * 
 *   === KMP SEARCH ===
 *   LPS array for "AABA": [0, 1, 0, 1]
 *   Pattern "AABA" found at indices: [0, 9, 12]
 *   Comparisons made: 22
 * 
 * Explanation:
 *   NAIVE: For each position i in text [0..n-m], compare text[i..i+m-1] with
 *   pattern[0..m-1]. If mismatch, shift pattern by 1. Worst case: O(n*m).
 * 
 *   KMP: Build a "Longest Proper Prefix which is also Suffix" (LPS) array.
 *   When a mismatch occurs at pattern[j], instead of restarting from j=0,
 *   jump to lps[j-1] — the longest prefix of pattern[0..j-1] that is also
 *   a suffix. This avoids re-comparing characters we already know match.
 * 
 * Time Complexity:
 *   Naive:  O(n * m) worst case, O(n) best case
 *   KMP:    O(n + m) — O(m) to build LPS + O(n) to search
 * Space Complexity:
 *   Naive:  O(1) extra space
 *   KMP:    O(m) for the LPS array
 */

import java.util.ArrayList;
import java.util.List;

public class SubstringSearch {

    // ══════════════════════════════════════════════
    //  1. NAIVE (Brute Force) Search
    // ══════════════════════════════════════════════

    /**
     * Naive substring search: try every position in text.
     * Returns list of starting indices where pattern is found.
     */
    public static List<Integer> naiveSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) return result;

        int comparisons = 0;

        for (int i = 0; i <= n - m; i++) {
            int j = 0;

            // Compare pattern with text starting at position i
            while (j < m) {
                comparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;  // mismatch — shift pattern by 1
                }
                j++;
            }

            if (j == m) {
                result.add(i);  // full match found at index i
            }
        }

        // Store comparisons for display (using a simple trick)
        naiveComparisons = comparisons;
        return result;
    }

    private static int naiveComparisons = 0;

    // ══════════════════════════════════════════════
    //  2. KMP (Knuth-Morris-Pratt) Search
    // ══════════════════════════════════════════════

    /**
     * Build the LPS (Longest Proper Prefix which is also Suffix) array.
     * 
     * lps[i] = length of the longest proper prefix of pattern[0..i]
     *          which is also a suffix of pattern[0..i].
     * 
     * Example: pattern = "AABAACAABAA"
     *   Index:  0  1  2  3  4  5  6  7  8  9  10
     *   Char:   A  A  B  A  A  C  A  A  B  A  A
     *   LPS:    0  1  0  1  2  0  1  2  3  4  5
     */
    public static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        lps[0] = 0;  // lps[0] is always 0

        int len = 0;  // length of the previous longest prefix suffix
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    // Don't increment i; try shorter prefix
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    /**
     * KMP substring search using the LPS array.
     * Returns list of starting indices where pattern is found.
     */
    public static List<Integer> kmpSearch(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) return result;

        int[] lps = buildLPS(pattern);
        int comparisons = 0;

        int i = 0;  // index in text
        int j = 0;  // index in pattern

        while (i < n) {
            comparisons++;

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                // Full match found at index (i - j)
                result.add(i - j);
                // Use LPS to continue searching for more matches
                j = lps[j - 1];
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                comparisons++; // the failed comparison
                if (j != 0) {
                    // Skip: we know pattern[0..lps[j-1]-1] already matches
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        kmpComparisons = comparisons;
        return result;
    }

    private static int kmpComparisons = 0;

    // ══════════════════════════════════════════════
    //  Visualization helpers
    // ══════════════════════════════════════════════

    /**
     * Step-by-step visualization of KMP search.
     */
    private static void visualizeKMP(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int[] lps = buildLPS(pattern);

        System.out.println("    LPS array: " + arrayToString(lps));
        System.out.println();

        int i = 0, j = 0;
        int step = 0;

        while (i < n) {
            step++;
            // Show alignment
            System.out.printf("    Step %d: i=%d, j=%d%n", step, i, j);
            System.out.println("    Text:    " + text);
            System.out.println("    Pattern: " + " ".repeat(i - j) + pattern);
            System.out.println("    Compare: " + " ".repeat(i) + "^");

            if (text.charAt(i) == pattern.charAt(j)) {
                System.out.println("    => MATCH ('" + text.charAt(i) + "')");
                i++;
                j++;
            } else {
                System.out.println("    => MISMATCH ('"
                        + text.charAt(i) + "' vs '" + pattern.charAt(j) + "')");
            }

            if (j == m) {
                System.out.println("    *** PATTERN FOUND at index " + (i - j) + " ***");
                j = lps[j - 1];
                System.out.println("    => Reset j to lps[" + (m - 1) + "] = " + j);
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    System.out.println("    => Skip: j = lps[" + (j - 1) + "] = " + lps[j - 1]
                            + " (saved " + (j - lps[j - 1]) + " comparisons)");
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }

            System.out.println();

            // Limit visualization steps for large inputs
            if (step > 25) {
                System.out.println("    ... (truncating visualization) ...\n");
                break;
            }
        }
    }

    /**
     * Step-by-step visualization of LPS construction.
     */
    private static void visualizeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        lps[0] = 0;

        System.out.println("    Building LPS for: \"" + pattern + "\"");
        System.out.println("    Initial: lps[0] = 0  (always)\n");

        int len = 0;
        int i = 1;

        while (i < m) {
            System.out.printf("    i=%d, len=%d: comparing pattern[%d]='%c' with pattern[%d]='%c'",
                    i, len, i, pattern.charAt(i), len, pattern.charAt(len));

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                System.out.printf(" => MATCH, lps[%d] = %d%n", i, len);
                i++;
            } else {
                if (len != 0) {
                    System.out.printf(" => MISMATCH, fall back: len = lps[%d] = %d%n",
                            len - 1, lps[len - 1]);
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    System.out.printf(" => MISMATCH, lps[%d] = 0%n", i);
                    i++;
                }
            }
        }

        System.out.println("\n    Final LPS: " + arrayToString(lps));
        System.out.print("    Pattern:   ");
        for (int k = 0; k < m; k++) System.out.printf("%-3c", pattern.charAt(k));
        System.out.print("\n    Index:     ");
        for (int k = 0; k < m; k++) System.out.printf("%-3d", k);
        System.out.print("\n    LPS:       ");
        for (int k = 0; k < m; k++) System.out.printf("%-3d", lps[k]);
        System.out.println();
    }

    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(arr[i]);
        }
        return sb.append("]").toString();
    }

    // ══════════════════════════════════════════════
    //  Performance comparison
    // ══════════════════════════════════════════════

    private static void performanceComparison() {
        System.out.println("=".repeat(60));
        System.out.println("  PERFORMANCE COMPARISON ON LARGE INPUT");
        System.out.println("=".repeat(60));

        // Worst case for naive: "AAAA...A" searching for "AAA...AB"
        int textLen = 100_000;
        int patLen = 100;

        StringBuilder textBuilder = new StringBuilder();
        for (int i = 0; i < textLen; i++) textBuilder.append('A');
        String worstText = textBuilder.toString();

        StringBuilder patBuilder = new StringBuilder();
        for (int i = 0; i < patLen - 1; i++) patBuilder.append('A');
        patBuilder.append('B');
        String worstPattern = patBuilder.toString();

        System.out.println("\n  Worst case: text = \"AAA...A\" (n=" + textLen
                + "), pattern = \"AAA...AB\" (m=" + patLen + ")");

        long start = System.nanoTime();
        List<Integer> naiveResult = naiveSearch(worstText, worstPattern);
        long naiveTime = System.nanoTime() - start;

        start = System.nanoTime();
        List<Integer> kmpResult = kmpSearch(worstText, worstPattern);
        long kmpTime = System.nanoTime() - start;

        System.out.printf("    Naive: %.2f ms, %,d comparisons, %d matches%n",
                naiveTime / 1_000_000.0, naiveComparisons, naiveResult.size());
        System.out.printf("    KMP:   %.2f ms, %,d comparisons, %d matches%n",
                kmpTime / 1_000_000.0, kmpComparisons, kmpResult.size());
        System.out.printf("    KMP speedup: %.1fx fewer comparisons%n",
                (double) naiveComparisons / Math.max(1, kmpComparisons));

        // Best case: pattern at the very start
        System.out.println("\n  Best case: pattern at start of text");
        String bestText = "HELLO" + "X".repeat(10_000);
        String bestPat = "HELLO";

        start = System.nanoTime();
        naiveSearch(bestText, bestPat);
        naiveTime = System.nanoTime() - start;

        start = System.nanoTime();
        kmpSearch(bestText, bestPat);
        kmpTime = System.nanoTime() - start;

        System.out.printf("    Naive: %.2f ms, %d comparisons%n",
                naiveTime / 1_000_000.0, naiveComparisons);
        System.out.printf("    KMP:   %.2f ms, %d comparisons%n",
                kmpTime / 1_000_000.0, kmpComparisons);

        // Average case: English-like text
        System.out.println("\n  Average case: repeating text with periodic pattern");
        StringBuilder avgBuilder = new StringBuilder();
        for (int i = 0; i < 10_000; i++) {
            avgBuilder.append("the quick brown fox jumps over the lazy dog ");
        }
        String avgText = avgBuilder.toString();
        String avgPat = "lazy dog";

        start = System.nanoTime();
        naiveResult = naiveSearch(avgText, avgPat);
        naiveTime = System.nanoTime() - start;

        start = System.nanoTime();
        kmpResult = kmpSearch(avgText, avgPat);
        kmpTime = System.nanoTime() - start;

        System.out.printf("    Text length: %,d%n", avgText.length());
        System.out.printf("    Naive: %.2f ms, %,d comparisons, %d matches%n",
                naiveTime / 1_000_000.0, naiveComparisons, naiveResult.size());
        System.out.printf("    KMP:   %.2f ms, %,d comparisons, %d matches%n",
                kmpTime / 1_000_000.0, kmpComparisons, kmpResult.size());
    }

    // ══════════════════════════════════════════════
    //  Main
    // ══════════════════════════════════════════════

    public static void main(String[] args) {

        // ── Test Case 1: Basic ──
        System.out.println("=".repeat(60));
        System.out.println("  SUBSTRING SEARCH: Naive vs KMP");
        System.out.println("=".repeat(60));

        String[][] testCases = {
            {"AABAACAADAABAABA", "AABA"},
            {"ABABABCABABABCABABABABC", "ABABABABC"},
            {"HELLO WORLD", "WORLD"},
            {"AAAAAAAAAAAA", "AAAA"},
            {"ABCDEF", "XYZ"},           // no match
            {"AAACAAAA", "AAAA"},
            {"abcabcabcabc", "abcabc"},
        };

        for (String[] tc : testCases) {
            String text = tc[0];
            String pattern = tc[1];

            System.out.println("\n  Text:    \"" + text + "\"");
            System.out.println("  Pattern: \"" + pattern + "\"");

            List<Integer> naiveResult = naiveSearch(text, pattern);
            int naiveComp = naiveComparisons;

            List<Integer> kmpResult = kmpSearch(text, pattern);
            int kmpComp = kmpComparisons;

            System.out.println("  Naive  -> indices: " + naiveResult
                    + "  (comparisons: " + naiveComp + ")");
            System.out.println("  KMP    -> indices: " + kmpResult
                    + "  (comparisons: " + kmpComp + ")");

            // Verify both algorithms give the same result
            assert naiveResult.equals(kmpResult) : "Results differ!";
        }

        // ── LPS Construction Walkthrough ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  LPS ARRAY CONSTRUCTION WALKTHROUGH");
        System.out.println("=".repeat(60));
        System.out.println();

        visualizeLPS("AABAACAABAA");

        System.out.println("\n  ---");
        System.out.println();

        visualizeLPS("ABCABCABD");

        // ── KMP Step-by-Step ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  KMP STEP-BY-STEP VISUALIZATION");
        System.out.println("=".repeat(60));
        System.out.println();

        String demoText = "AABAABAAC";
        String demoPat  = "AABAC";
        System.out.println("    Text:    \"" + demoText + "\"");
        System.out.println("    Pattern: \"" + demoPat + "\"\n");
        visualizeKMP(demoText, demoPat);

        // ── LPS Examples ──
        System.out.println("=".repeat(60));
        System.out.println("  LPS ARRAY EXAMPLES");
        System.out.println("=".repeat(60));

        String[] lpsExamples = {
            "AAAA",       // [0,1,2,3]
            "ABCABC",     // [0,0,0,1,2,3]
            "AABAAAB",    // [0,1,0,1,2,2,3]? Let's compute
            "ABABAB",     // [0,0,1,2,3,4]
            "ABCDABCD",   // [0,0,0,0,1,2,3,4]
            "AAACAAAA",   // [0,1,2,0,1,2,3,3]
        };
        for (String pat : lpsExamples) {
            int[] lps = buildLPS(pat);
            System.out.printf("  Pattern: %-12s  LPS: %s%n", "\"" + pat + "\"",
                    arrayToString(lps));
        }

        // ── Performance ──
        System.out.println();
        performanceComparison();

        // ── Key Takeaways ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  KEY TAKEAWAYS");
        System.out.println("=".repeat(60));
        System.out.println("  1. Naive search: simple, O(nm) worst case, good for short patterns");
        System.out.println("  2. KMP: O(n+m) guaranteed, crucial for large texts / repetitive patterns");
        System.out.println("  3. LPS array is the heart of KMP — precomputes where to resume");
        System.out.println("  4. Java's String.indexOf() uses a naive approach (JDK source)");
        System.out.println("  5. Other O(n+m) algorithms: Rabin-Karp (hashing), Boyer-Moore");
        System.out.println("  6. KMP is preferred when the pattern has repeating prefixes");
    }
}
