/*
 * Program 53: String Compression (Run-Length Encoding)
 * 
 * Problem Statement:
 *   Implement string compression using run-length encoding (RLE):
 *     - Compress: "aabbbcccc" -> "a2b3c4"
 *     - Decompress: "a2b3c4" -> "aabbbcccc"
 *   If the compressed string is not shorter than the original, return the original.
 *   Also handle edge cases like single characters and mixed content.
 * 
 * Sample Input:  "aabbbcc"
 * Sample Output: 
 *   Compressed  : "a2b3c2"
 *   Decompressed: "aabbbcc"
 * 
 * Sample Input:  "abcdef"
 * Sample Output:
 *   Compressed  : "abcdef" (original returned, since "a1b1c1d1e1f1" is longer)
 * 
 * Explanation:
 *   Compression: Traverse the string, count consecutive identical characters,
 *   append the character followed by its count.
 *   Decompression: Parse character-count pairs and expand them.
 * 
 * Time Complexity: O(n) for both compression and decompression
 * Space Complexity: O(n) for the resulting string
 */

public class StringCompression {

    // =========================================================================
    // Compress: Run-Length Encoding
    //   "aabbbcccc" -> "a2b3c4"
    //   Returns original if compressed is not shorter.
    // =========================================================================
    public static String compress(String s) {
        if (s == null || s.length() <= 1) return s;

        StringBuilder compressed = new StringBuilder();
        int count = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(s.charAt(i - 1));
                compressed.append(count);
                count = 1;
            }
        }
        // Append the last group
        compressed.append(s.charAt(s.length() - 1));
        compressed.append(count);

        // Only return compressed if it's actually shorter
        return compressed.length() < s.length() ? compressed.toString() : s;
    }

    // =========================================================================
    // Compress (always include count, even for single characters)
    //   "abc" -> "a1b1c1" (forced compression)
    // =========================================================================
    public static String compressForced(String s) {
        if (s == null || s.isEmpty()) return s;

        StringBuilder compressed = new StringBuilder();
        int count = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(s.charAt(i - 1));
                compressed.append(count);
                count = 1;
            }
        }
        compressed.append(s.charAt(s.length() - 1));
        compressed.append(count);

        return compressed.toString();
    }

    // =========================================================================
    // Compress (omit count when count == 1)
    //   "aabccc" -> "a2bc3"
    // =========================================================================
    public static String compressOmitSingles(String s) {
        if (s == null || s.length() <= 1) return s;

        StringBuilder compressed = new StringBuilder();
        int count = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(s.charAt(i - 1));
                if (count > 1) compressed.append(count);
                count = 1;
            }
        }
        compressed.append(s.charAt(s.length() - 1));
        if (count > 1) compressed.append(count);

        return compressed.length() < s.length() ? compressed.toString() : s;
    }

    // =========================================================================
    // Decompress: Reverse of compress (expects char followed by digits)
    //   "a2b3c4" -> "aabbbcccc"
    // =========================================================================
    public static String decompress(String s) {
        if (s == null || s.isEmpty()) return s;

        StringBuilder decompressed = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            // Current character (the letter/symbol)
            char c = s.charAt(i);
            i++;

            // Parse the count (could be multi-digit)
            StringBuilder numStr = new StringBuilder();
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                numStr.append(s.charAt(i));
                i++;
            }

            int count = numStr.length() > 0 ? Integer.parseInt(numStr.toString()) : 1;

            // Repeat the character 'count' times
            for (int j = 0; j < count; j++) {
                decompressed.append(c);
            }
        }

        return decompressed.toString();
    }

    // =========================================================================
    // Verify: compress then decompress should give original
    // =========================================================================
    public static boolean verify(String original) {
        String compressed = compressForced(original);
        String decompressed = decompress(compressed);
        return original.equals(decompressed);
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           String Compression (Run-Length Encoding)           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        String[] testCases = {
            "aabbbcc",
            "aabbbcccc",
            "abcdef",           // No repeated chars
            "aaaaaaaaaa",       // All same character
            "aabbccdd",
            "aaabba",
            "a",                // Single char
            "",                 // Empty string
            "aabcccccaaa",
            "AAABBBCCC",
            "xxxyyyzzz"
        };

        System.out.printf("%-20s %-20s %-20s %-20s %-8s%n",
                "Original", "Compressed", "Forced", "Decompressed", "Verify");
        System.out.println("-".repeat(90));

        for (String test : testCases) {
            String compressed = compress(test);
            String forced = compressForced(test);
            String decompressed = decompress(forced);
            boolean verified = verify(test);

            System.out.printf("%-20s %-20s %-20s %-20s %-8s%n",
                    "\"" + test + "\"",
                    "\"" + compressed + "\"",
                    "\"" + forced + "\"",
                    "\"" + decompressed + "\"",
                    verified ? "✓" : "✗");
        }

        // =====================================================================
        // Detailed Compression/Decompression Demo
        // =====================================================================
        System.out.println();
        System.out.println("=== Detailed Compression Trace ===");
        traceCompression("aabbbcccc");

        System.out.println();
        System.out.println("=== Detailed Decompression Trace ===");
        traceDecompression("a2b3c4");

        // =====================================================================
        // Compression with singles omitted
        // =====================================================================
        System.out.println();
        System.out.println("=== Compression (Omit Singles) ===");
        String[] omitTests = {"aabccc", "aabbcc", "abcdef", "aaaa"};
        for (String t : omitTests) {
            System.out.printf("  \"%s\" -> \"%s\"%n", t, compressOmitSingles(t));
        }

        // =====================================================================
        // Compression Ratio Analysis
        // =====================================================================
        System.out.println();
        System.out.println("=== Compression Ratio Analysis ===");
        String[] ratioTests = {"aabbbcccc", "abcdef", "aaaaaaaaaa", "aabb"};
        for (String t : ratioTests) {
            String comp = compressForced(t);
            double ratio = (double) comp.length() / t.length() * 100;
            System.out.printf("  \"%s\" (%d chars) -> \"%s\" (%d chars) = %.1f%% of original%n",
                    t, t.length(), comp, comp.length(), ratio);
        }
    }

    /**
     * Step-by-step trace of compression.
     */
    private static void traceCompression(String s) {
        System.out.println("Input: \"" + s + "\"");
        StringBuilder compressed = new StringBuilder();
        int count = 1;

        for (int i = 1; i <= s.length(); i++) {
            if (i < s.length() && s.charAt(i) == s.charAt(i - 1)) {
                count++;
                System.out.printf("  i=%d: '%c' == '%c', count=%d%n", i, s.charAt(i), s.charAt(i-1), count);
            } else {
                compressed.append(s.charAt(i - 1));
                compressed.append(count);
                System.out.printf("  i=%d: End of run -> append '%c%d'  =>  \"%s\"%n",
                        i, s.charAt(i - 1), count, compressed.toString());
                count = 1;
            }
        }
        System.out.println("Result: \"" + compressed.toString() + "\"");
    }

    /**
     * Step-by-step trace of decompression.
     */
    private static void traceDecompression(String s) {
        System.out.println("Input: \"" + s + "\"");
        StringBuilder decompressed = new StringBuilder();
        int i = 0;

        while (i < s.length()) {
            char c = s.charAt(i);
            i++;
            StringBuilder numStr = new StringBuilder();
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                numStr.append(s.charAt(i));
                i++;
            }
            int count = numStr.length() > 0 ? Integer.parseInt(numStr.toString()) : 1;
            String repeated = String.valueOf(c).repeat(count);
            decompressed.append(repeated);
            System.out.printf("  Char='%c', Count=%d  =>  \"%s\"  =>  built: \"%s\"%n",
                    c, count, repeated, decompressed.toString());
        }
        System.out.println("Result: \"" + decompressed.toString() + "\"");
    }
}
