/*
 * Program 50: Count Vowels, Consonants, Digits, Spaces, and Special Characters
 * 
 * Problem Statement:
 *   Given a string, count the number of:
 *     - Vowels (a, e, i, o, u — case-insensitive)
 *     - Consonants (all other letters)
 *     - Digits (0-9)
 *     - Spaces
 *     - Special characters (everything else: punctuation, symbols, etc.)
 * 
 * Sample Input:  "Hello World! 123"
 * Sample Output:
 *   Vowels     : 3  (e, o, o)
 *   Consonants : 7  (H, l, l, W, r, l, d)
 *   Digits     : 3  (1, 2, 3)
 *   Spaces     : 2
 *   Special    : 1  (!)
 * 
 * Explanation:
 *   Iterate through each character, classify it using Character utility methods
 *   and a vowel set, then increment the appropriate counter.
 * 
 * Time Complexity: O(n) where n = length of the string
 * Space Complexity: O(1) — only counters and a small constant-size set
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CountVowelsConsonants {

    // Set of vowel characters for quick lookup
    private static final String VOWELS = "aeiouAEIOU";

    /**
     * Result container holding all counts.
     */
    static class CharCounts {
        int vowels;
        int consonants;
        int digits;
        int spaces;
        int special;

        @Override
        public String toString() {
            return String.format(
                "Vowels: %d, Consonants: %d, Digits: %d, Spaces: %d, Special: %d",
                vowels, consonants, digits, spaces, special
            );
        }
    }

    // =========================================================================
    // Count all character categories
    // =========================================================================
    public static CharCounts countAll(String s) {
        CharCounts counts = new CharCounts();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isLetter(c)) {
                if (VOWELS.indexOf(c) >= 0) {
                    counts.vowels++;
                } else {
                    counts.consonants++;
                }
            } else if (Character.isDigit(c)) {
                counts.digits++;
            } else if (c == ' ') {
                counts.spaces++;
            } else {
                counts.special++;
            }
        }

        return counts;
    }

    // =========================================================================
    // Collect individual characters in each category for display
    // =========================================================================
    public static Map<String, StringBuilder> collectByCategory(String s) {
        Map<String, StringBuilder> categories = new LinkedHashMap<>();
        categories.put("Vowels", new StringBuilder());
        categories.put("Consonants", new StringBuilder());
        categories.put("Digits", new StringBuilder());
        categories.put("Spaces", new StringBuilder());
        categories.put("Special", new StringBuilder());

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            String key;

            if (Character.isLetter(c)) {
                key = VOWELS.indexOf(c) >= 0 ? "Vowels" : "Consonants";
            } else if (Character.isDigit(c)) {
                key = "Digits";
            } else if (c == ' ') {
                key = "Spaces";
            } else {
                key = "Special";
            }

            StringBuilder sb = categories.get(key);
            if (sb.length() > 0) sb.append(", ");
            sb.append('\'').append(c).append('\'');
        }

        return categories;
    }

    // =========================================================================
    // Count only vowels (utility method)
    // =========================================================================
    public static int countVowels(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (VOWELS.indexOf(c) >= 0) count++;
        }
        return count;
    }

    // =========================================================================
    // Count only consonants (utility method)
    // =========================================================================
    public static int countConsonants(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c) && VOWELS.indexOf(c) < 0) count++;
        }
        return count;
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        String[] testStrings = {
            "Hello World! 123",
            "Java Programming @ 2024",
            "aeiou AEIOU",
            "bcdfg BCDFG",
            "12345",
            "!@#$%^&*()",
            "   ",
            "",
            "The quick brown fox jumps over 2 lazy dogs!"
        };

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           Vowel, Consonant & Character Counter              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        for (String test : testStrings) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("-".repeat(60));

            CharCounts counts = countAll(test);
            Map<String, StringBuilder> collected = collectByCategory(test);

            System.out.printf("  Vowels     : %2d  %s%n", counts.vowels,
                    collected.get("Vowels").length() > 0 ? "(" + collected.get("Vowels") + ")" : "");
            System.out.printf("  Consonants : %2d  %s%n", counts.consonants,
                    collected.get("Consonants").length() > 0 ? "(" + collected.get("Consonants") + ")" : "");
            System.out.printf("  Digits     : %2d  %s%n", counts.digits,
                    collected.get("Digits").length() > 0 ? "(" + collected.get("Digits") + ")" : "");
            System.out.printf("  Spaces     : %2d%n", counts.spaces);
            System.out.printf("  Special    : %2d  %s%n", counts.special,
                    collected.get("Special").length() > 0 ? "(" + collected.get("Special") + ")" : "");
            System.out.printf("  Total      : %2d%n", test.length());
            System.out.println();
        }

        // =====================================================================
        // Interactive Mode
        // =====================================================================
        System.out.println("=== Interactive Mode (type 'quit' to exit) ===");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a string: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("quit")) break;

            CharCounts c = countAll(input);
            System.out.println("  " + c);
            System.out.println();
        }

        scanner.close();
        System.out.println("Goodbye!");
    }
}
