/*
 * Program 60: RomanToInteger
 * 
 * Problem Statement:
 *   Convert Roman numerals to integers and integers to Roman numerals.
 *   Valid range: 1 to 3999 (standard Roman numeral system).
 *   
 *   Roman Numeral Rules:
 *     I=1, V=5, X=10, L=50, C=100, D=500, M=1000
 *     Subtractive notation: IV=4, IX=9, XL=40, XC=90, CD=400, CM=900
 *     Symbols are generally written largest to smallest, left to right.
 *     A smaller symbol before a larger one means subtraction.
 * 
 * Sample Input:
 *   Roman to Integer: "MCMXCIV"
 *   Integer to Roman: 1994
 * 
 * Sample Output:
 *   "MCMXCIV" => 1994
 *   1994      => "MCMXCIV"
 *
 * Explanation:
 *   Roman to Integer: Scan right to left. If current value < next value,
 *   subtract it; otherwise add it. This handles subtractive cases like IV, IX.
 *   
 *   Integer to Roman: Greedily subtract the largest possible Roman value
 *   from the number, appending the corresponding symbol each time.
 *   Use a table that includes subtractive pairs (CM, CD, XC, XL, IX, IV).
 * 
 * Time Complexity:  O(n) for Roman-to-Int (n = length of Roman string)
 *                   O(1) for Int-to-Roman (bounded by 3999, at most 15 symbols)
 * Space Complexity: O(1) — fixed-size lookup tables
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class RomanToInteger {

    // ──────────────────────────────────────────────
    //  Lookup tables
    // ──────────────────────────────────────────────

    /** Map single Roman chars to their integer values */
    private static int romanCharValue(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default:  return 0;
        }
    }

    /**
     * Ordered map of integer values to Roman numeral strings.
     * Includes subtractive pairs to simplify conversion.
     * Uses LinkedHashMap to maintain insertion (descending) order.
     */
    private static final LinkedHashMap<Integer, String> INT_TO_ROMAN = new LinkedHashMap<>();
    static {
        INT_TO_ROMAN.put(1000, "M");
        INT_TO_ROMAN.put(900,  "CM");
        INT_TO_ROMAN.put(500,  "D");
        INT_TO_ROMAN.put(400,  "CD");
        INT_TO_ROMAN.put(100,  "C");
        INT_TO_ROMAN.put(90,   "XC");
        INT_TO_ROMAN.put(50,   "L");
        INT_TO_ROMAN.put(40,   "XL");
        INT_TO_ROMAN.put(10,   "X");
        INT_TO_ROMAN.put(9,    "IX");
        INT_TO_ROMAN.put(5,    "V");
        INT_TO_ROMAN.put(4,    "IV");
        INT_TO_ROMAN.put(1,    "I");
    }

    // ══════════════════════════════════════════════
    //  Roman to Integer
    // ══════════════════════════════════════════════

    /**
     * Convert a Roman numeral string to an integer.
     * 
     * Algorithm (right-to-left scan):
     *   - Start from the rightmost character.
     *   - If current value < previous value, subtract it (subtractive rule).
     *   - Otherwise, add it.
     * 
     * Example: "MCMXCIV" (right to left)
     *   V=5 (add)    -> 5
     *   I=1 (1<5, sub) -> 4
     *   C=100 (add)  -> 104
     *   X=10 (10<100, sub) -> 94
     *   M=1000 (add) -> 1094 — wait, let me redo...
     *   Actually scanning MCMXCIV:
     *     M=1000, C=100, M=1000, X=10, C=100, I=1, V=5
     *   Right to left: V(5), I(1<5→sub), C(100), X(10<100→sub), M(1000), C(100<1000→sub), M(1000)
     *     = 5 - 1 + 100 - 10 + 1000 - 100 + 1000 = 1994 ✓
     */
    public static int romanToInt(String s) {
        if (s == null || s.isEmpty()) return 0;

        s = s.toUpperCase();
        int result = 0;
        int prevValue = 0;

        // Scan right to left
        for (int i = s.length() - 1; i >= 0; i--) {
            int curValue = romanCharValue(s.charAt(i));

            if (curValue == 0) {
                throw new IllegalArgumentException(
                        "Invalid Roman numeral character: '" + s.charAt(i) + "'");
            }

            if (curValue < prevValue) {
                result -= curValue;  // subtractive: IV, IX, XL, XC, CD, CM
            } else {
                result += curValue;
            }

            prevValue = curValue;
        }

        return result;
    }

    /**
     * Alternative: Left-to-right scan (also common in interviews).
     */
    public static int romanToIntLeftToRight(String s) {
        if (s == null || s.isEmpty()) return 0;

        s = s.toUpperCase();
        int result = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int curValue = romanCharValue(s.charAt(i));

            // Look ahead: if next value is larger, subtract current
            if (i + 1 < n && curValue < romanCharValue(s.charAt(i + 1))) {
                result -= curValue;
            } else {
                result += curValue;
            }
        }

        return result;
    }

    // ══════════════════════════════════════════════
    //  Integer to Roman
    // ══════════════════════════════════════════════

    /**
     * Convert an integer (1-3999) to a Roman numeral string.
     * 
     * Algorithm (Greedy):
     *   - Start with the largest value in the table (1000 = M).
     *   - While num >= value, append the Roman string and subtract the value.
     *   - Move to the next smaller value.
     * 
     * Example: 1994
     *   1994 >= 1000 → "M",    rem = 994
     *   994  >= 900  → "CM",   rem = 94
     *   94   >= 90   → "XC",   rem = 4
     *   4    >= 4    → "IV",   rem = 0
     *   Result: "MCMXCIV"
     */
    public static String intToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException(
                    "Number must be between 1 and 3999, got: " + num);
        }

        StringBuilder result = new StringBuilder();

        for (Map.Entry<Integer, String> entry : INT_TO_ROMAN.entrySet()) {
            int value = entry.getKey();
            String symbol = entry.getValue();

            while (num >= value) {
                result.append(symbol);
                num -= value;
            }

            if (num == 0) break;
        }

        return result.toString();
    }

    // ══════════════════════════════════════════════
    //  Validation
    // ══════════════════════════════════════════════

    /**
     * Validate if a string is a well-formed Roman numeral.
     * Rules checked:
     *   - Only valid characters (I, V, X, L, C, D, M)
     *   - I, X, C, M can repeat max 3 times consecutively
     *   - V, L, D cannot repeat
     *   - Only valid subtractive pairs: IV, IX, XL, XC, CD, CM
     */
    public static boolean isValidRoman(String s) {
        if (s == null || s.isEmpty()) return false;

        // Regex for valid Roman numerals (1-3999)
        String regex = "^M{0,3}"          // thousands: 0-3 M's
                     + "(CM|CD|D?C{0,3})" // hundreds
                     + "(XC|XL|L?X{0,3})" // tens
                     + "(IX|IV|V?I{0,3})" // ones
                     + "$";

        return s.toUpperCase().matches(regex) && !s.isEmpty()
               && !s.toUpperCase().matches("^$"); // not empty after matching
    }

    // ══════════════════════════════════════════════
    //  Visualization: step-by-step conversion
    // ══════════════════════════════════════════════

    private static void showRomanToIntSteps(String roman) {
        System.out.println("    Step-by-step (right to left):");
        String s = roman.toUpperCase();
        int result = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int curValue = romanCharValue(s.charAt(i));
            String action;
            if (curValue < prevValue) {
                result -= curValue;
                action = "SUBTRACT (smaller than right neighbor)";
            } else {
                result += curValue;
                action = "ADD";
            }
            System.out.printf("      %c = %4d  =>  %s  =>  running total = %d%n",
                    s.charAt(i), curValue, action, result);
            prevValue = curValue;
        }
        System.out.println("    Result: " + result);
    }

    private static void showIntToRomanSteps(int num) {
        System.out.println("    Step-by-step (greedy):");
        int original = num;
        StringBuilder result = new StringBuilder();

        for (Map.Entry<Integer, String> entry : INT_TO_ROMAN.entrySet()) {
            int value = entry.getKey();
            String symbol = entry.getValue();

            while (num >= value) {
                result.append(symbol);
                num -= value;
                System.out.printf("      %d >= %4d  => append \"%s\"  => \"%s\"  (remaining: %d)%n",
                        num + value, value, symbol, result, num);
            }

            if (num == 0) break;
        }

        System.out.println("    Result: " + original + " => \"" + result + "\"");
    }

    // ══════════════════════════════════════════════
    //  Main
    // ══════════════════════════════════════════════

    public static void main(String[] args) {

        System.out.println("=".repeat(60));
        System.out.println("  ROMAN NUMERAL CONVERTER (1 – 3999)");
        System.out.println("=".repeat(60));

        // ── Roman Numeral Reference ──
        System.out.println("\n  Roman Numeral Value Table:");
        System.out.println("  ┌───────┬───────┬────────────────────────────┐");
        System.out.println("  │Symbol │ Value │ Note                       │");
        System.out.println("  ├───────┼───────┼────────────────────────────┤");
        System.out.println("  │  I    │     1 │ Can repeat up to 3 times   │");
        System.out.println("  │  V    │     5 │ Cannot repeat              │");
        System.out.println("  │  X    │    10 │ Can repeat up to 3 times   │");
        System.out.println("  │  L    │    50 │ Cannot repeat              │");
        System.out.println("  │  C    │   100 │ Can repeat up to 3 times   │");
        System.out.println("  │  D    │   500 │ Cannot repeat              │");
        System.out.println("  │  M    │  1000 │ Can repeat up to 3 times   │");
        System.out.println("  └───────┴───────┴────────────────────────────┘");
        System.out.println("  Subtractive pairs: IV=4, IX=9, XL=40, XC=90, CD=400, CM=900");

        // ── Roman to Integer Tests ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ROMAN TO INTEGER CONVERSION");
        System.out.println("=".repeat(60));

        String[] romanTests = {
            "I", "IV", "IX", "XIV", "XL", "XLIX",
            "L", "XC", "XCIX", "C", "CD", "D", "CM",
            "MCMXCIV", "MMXXIV", "MMMCMXCIX", "III",
            "LVIII", "DCCCXC", "MMMDCCCLXXXVIII"
        };

        System.out.printf("\n  %-20s => %s%n", "ROMAN", "INTEGER");
        System.out.println("  " + "-".repeat(35));
        for (String roman : romanTests) {
            int value = romanToInt(roman);
            int valueLR = romanToIntLeftToRight(roman);
            assert value == valueLR : "Algorithms disagree for " + roman;
            System.out.printf("  %-20s => %d%n", roman, value);
        }

        // ── Detailed walkthrough ──
        System.out.println("\n--- Detailed Walkthrough: \"MCMXCIV\" ---");
        showRomanToIntSteps("MCMXCIV");

        System.out.println("\n--- Detailed Walkthrough: \"MMMCMXCIX\" ---");
        showRomanToIntSteps("MMMCMXCIX");

        // ── Integer to Roman Tests ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  INTEGER TO ROMAN CONVERSION");
        System.out.println("=".repeat(60));

        int[] intTests = {
            1, 4, 9, 14, 40, 49, 50, 90, 99, 100,
            400, 500, 900, 1994, 2024, 3999, 3, 58,
            890, 3888, 2743, 1776
        };

        System.out.printf("\n  %-8s => %s%n", "INTEGER", "ROMAN");
        System.out.println("  " + "-".repeat(30));
        for (int num : intTests) {
            System.out.printf("  %-8d => %s%n", num, intToRoman(num));
        }

        // ── Detailed walkthrough ──
        System.out.println("\n--- Detailed Walkthrough: 1994 ---");
        showIntToRomanSteps(1994);

        System.out.println("\n--- Detailed Walkthrough: 3999 ---");
        showIntToRomanSteps(3999);

        // ── Round-trip verification ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ROUND-TRIP VERIFICATION (1 to 3999)");
        System.out.println("=".repeat(60));

        int errors = 0;
        for (int i = 1; i <= 3999; i++) {
            String roman = intToRoman(i);
            int backToInt = romanToInt(roman);
            if (backToInt != i) {
                System.out.printf("  ERROR: %d => \"%s\" => %d%n", i, roman, backToInt);
                errors++;
            }
        }
        System.out.println("\n  Tested all numbers 1-3999.");
        System.out.println("  Errors found: " + errors);
        System.out.println("  Status: " + (errors == 0 ? "ALL PASSED ✓" : "FAILURES DETECTED ✗"));

        // ── Validation Tests ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ROMAN NUMERAL VALIDATION");
        System.out.println("=".repeat(60));

        String[][] validationTests = {
            {"XIV",      "valid"},
            {"MCMXCIV",  "valid"},
            {"MMMCMXCIX","valid"},
            {"III",      "valid"},
            {"IIII",     "invalid — I repeated 4 times"},
            {"VV",       "invalid — V cannot repeat"},
            {"IC",       "invalid — not a valid subtractive pair"},
            {"MMMM",     "invalid — exceeds 3999"},
            {"",         "invalid — empty string"},
            {"ABC",      "invalid — non-Roman characters"},
            {"XXXX",     "invalid — X repeated 4 times"},
            {"LL",       "invalid — L cannot repeat"},
        };

        System.out.printf("\n  %-15s %-10s %s%n", "INPUT", "VALID?", "EXPECTED");
        System.out.println("  " + "-".repeat(50));
        for (String[] test : validationTests) {
            boolean valid = isValidRoman(test[0]);
            System.out.printf("  %-15s %-10s %s%n",
                    "\"" + test[0] + "\"",
                    valid ? "YES" : "NO",
                    test[1]);
        }

        // ── Special Numbers ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  FUN FACTS: FAMOUS YEARS IN ROMAN NUMERALS");
        System.out.println("=".repeat(60));

        int[][] specialYears = {
            {1776, 0}, // US Declaration of Independence
            {1969, 0}, // Moon landing
            {2000, 0}, // Y2K
            {2024, 0}, // Current year
            {1066, 0}, // Battle of Hastings
            {1215, 0}, // Magna Carta
            {1492, 0}, // Columbus
            {1945, 0}, // End of WWII
        };
        String[] descriptions = {
            "US Declaration of Independence",
            "Moon Landing (Apollo 11)",
            "Y2K",
            "Current Year",
            "Battle of Hastings",
            "Magna Carta",
            "Columbus reaches Americas",
            "End of World War II"
        };

        for (int i = 0; i < specialYears.length; i++) {
            int year = specialYears[i][0];
            System.out.printf("  %d = %-15s  %s%n",
                    year, intToRoman(year), descriptions[i]);
        }

        // ── Edge cases ──
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  EDGE CASES");
        System.out.println("=".repeat(60));

        System.out.println("\n  Smallest: " + intToRoman(1) + " = 1");
        System.out.println("  Largest:  " + intToRoman(3999) + " = 3999");
        System.out.println("  Longest Roman numeral (1-3999): "
                + intToRoman(3888) + " = 3888 (15 characters)");

        // Find the longest Roman numeral
        int longestLen = 0;
        int longestNum = 0;
        for (int i = 1; i <= 3999; i++) {
            String r = intToRoman(i);
            if (r.length() > longestLen) {
                longestLen = r.length();
                longestNum = i;
            }
        }
        System.out.println("  Verified longest: " + intToRoman(longestNum)
                + " = " + longestNum + " (" + longestLen + " chars)");

        // Count unique lengths
        int[] lengthCounts = new int[16]; // max 15 chars
        for (int i = 1; i <= 3999; i++) {
            lengthCounts[intToRoman(i).length()]++;
        }
        System.out.println("\n  Distribution of Roman numeral lengths:");
        for (int len = 1; len < lengthCounts.length; len++) {
            if (lengthCounts[len] > 0) {
                System.out.printf("    Length %2d: %4d numbers%n",
                        len, lengthCounts[len]);
            }
        }
    }
}
