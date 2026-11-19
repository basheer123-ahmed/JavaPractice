/*
 * Program 57: StringTokenizerDemo
 * 
 * Problem Statement:
 *   Demonstrate three approaches for parsing delimited data in Java:
 *     1. StringTokenizer (legacy, from java.util)
 *     2. String.split() with regex
 *     3. Scanner with useDelimiter()
 *   Parse CSV data, pipe-delimited data, and multi-delimiter data.
 *   Show the pros, cons, and behavioral differences of each approach,
 *   especially regarding empty tokens, regex support, and streaming.
 * 
 * Sample Input:  (hard-coded CSV and delimited strings)
 * Sample Output:
 *   === METHOD 1: StringTokenizer ===
 *   Token 1: John
 *   Token 2: Doe
 *   Token 3: 30
 *   ...
 *   === METHOD 2: String.split() ===
 *   ...
 *   === METHOD 3: Scanner ===
 *   ...
 *   === COMPARISON: Empty field handling ===
 *   StringTokenizer tokens: 3  (skips empties)
 *   split() tokens:         5  (preserves empties)
 *   ...
 * 
 * Explanation:
 *   - StringTokenizer: fast, simple, but cannot handle empty tokens, no regex,
 *     considered legacy (not deprecated, just older). Treats any character in
 *     the delimiter string as an individual delimiter.
 *   - String.split(regex): uses regex, preserves empty tokens (trailing empties
 *     removed by default; use split(regex, -1) to keep all). Returns String[].
 *   - Scanner: streaming parser, good for mixed-type data (nextInt, nextDouble),
 *     can read from files/streams. useDelimiter() sets the regex delimiter.
 * 
 * Time Complexity:  O(n) for all three methods, where n is the string length
 * Space Complexity: O(n) — all tokens stored (or O(1) if processed one at a time)
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StringTokenizerDemo {

    // Helper: print a section header
    private static void header(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }

    public static void main(String[] args) {

        // ──────────────────────────────────────────────
        //  Sample data
        // ──────────────────────────────────────────────
        String csvLine      = "John,Doe,30,Engineer,New York";
        String pipeLine     = "AAPL|Apple Inc.|182.63|+1.25%|NASDAQ";
        String tsvLine      = "ID\tName\tScore\tGrade";
        String multiDelim   = "one;two|three,four:five";
        String withEmpties  = "a,,b,,c";       // has empty fields
        String csvMultiLine = 
                "Name,Age,City,Salary\n"
              + "Alice,28,Boston,95000\n"
              + "Bob,35,Chicago,120000\n"
              + "Charlie,42,Denver,88000\n"
              + "Diana,31,,105000";            // missing city

        // ══════════════════════════════════════════════
        //  METHOD 1: StringTokenizer
        // ══════════════════════════════════════════════
        header("METHOD 1: StringTokenizer");

        // Basic usage
        System.out.println("\n--- Basic CSV parsing ---");
        System.out.println("Input: \"" + csvLine + "\"");
        StringTokenizer st = new StringTokenizer(csvLine, ",");
        System.out.println("Token count: " + st.countTokens());
        int idx = 1;
        while (st.hasMoreTokens()) {
            System.out.printf("  Token %d: %s%n", idx++, st.nextToken());
        }

        // Multiple delimiter characters
        System.out.println("\n--- Multiple delimiters (;|,:) ---");
        System.out.println("Input: \"" + multiDelim + "\"");
        st = new StringTokenizer(multiDelim, ";|,:");
        while (st.hasMoreTokens()) {
            System.out.println("  Token: " + st.nextToken());
        }

        // returnDelims = true
        System.out.println("\n--- returnDelims = true ---");
        System.out.println("Input: \"" + csvLine + "\"");
        st = new StringTokenizer(csvLine, ",", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals(",")) {
                System.out.println("  [DELIMITER]: ,");
            } else {
                System.out.println("  Token: " + tok);
            }
        }

        // Empty field problem
        System.out.println("\n--- Empty field problem ---");
        System.out.println("Input: \"" + withEmpties + "\"  (has empty fields)");
        st = new StringTokenizer(withEmpties, ",");
        System.out.print("  StringTokenizer result: ");
        while (st.hasMoreTokens()) {
            System.out.print("[" + st.nextToken() + "] ");
        }
        System.out.println("  <-- empty fields SKIPPED!");

        // ══════════════════════════════════════════════
        //  METHOD 2: String.split()
        // ══════════════════════════════════════════════
        header("METHOD 2: String.split()");

        // Basic usage
        System.out.println("\n--- Basic CSV parsing ---");
        System.out.println("Input: \"" + csvLine + "\"");
        String[] parts = csvLine.split(",");
        for (int i = 0; i < parts.length; i++) {
            System.out.printf("  parts[%d]: \"%s\"%n", i, parts[i]);
        }

        // Pipe delimiter (needs escaping since | is regex OR)
        System.out.println("\n--- Pipe-delimited (regex escape needed) ---");
        System.out.println("Input: \"" + pipeLine + "\"");
        String[] pipeParts = pipeLine.split("\\|");
        for (int i = 0; i < pipeParts.length; i++) {
            System.out.printf("  parts[%d]: \"%s\"%n", i, pipeParts[i]);
        }

        // Tab delimiter
        System.out.println("\n--- Tab-delimited ---");
        System.out.println("Input: \"" + tsvLine.replace("\t", "\\t") + "\"");
        String[] tabParts = tsvLine.split("\t");
        for (String part : tabParts) {
            System.out.println("  Token: \"" + part + "\"");
        }

        // Empty fields preserved
        System.out.println("\n--- Empty fields preserved ---");
        System.out.println("Input: \"" + withEmpties + "\"");
        String[] emptyParts = withEmpties.split(",", -1); // -1 keeps trailing empties
        System.out.print("  split(\",\", -1) result: ");
        for (String p : emptyParts) {
            System.out.print("[" + p + "] ");
        }
        System.out.println();

        // Default split vs split with limit -1
        String trailingEmpties = "a,b,,,";
        System.out.println("\n--- Trailing empty tokens ---");
        System.out.println("Input: \"" + trailingEmpties + "\"");
        System.out.println("  split(\",\")     length: "
                + trailingEmpties.split(",").length
                + "  => " + Arrays.toString(trailingEmpties.split(",")));
        System.out.println("  split(\",\", -1) length: "
                + trailingEmpties.split(",", -1).length
                + " => " + Arrays.toString(trailingEmpties.split(",", -1)));

        // Limit parameter
        System.out.println("\n--- Limit parameter ---");
        System.out.println("Input: \"" + csvLine + "\"");
        String[] limited = csvLine.split(",", 3);
        System.out.println("  split(\",\", 3): " + Arrays.toString(limited));
        System.out.println("  (Only first 2 splits applied; remainder stays together)");

        // Regex power: split on multiple delimiters
        System.out.println("\n--- Regex: multiple delimiters [;|,:] ---");
        System.out.println("Input: \"" + multiDelim + "\"");
        String[] multiParts = multiDelim.split("[;|,:]");
        System.out.println("  Result: " + Arrays.toString(multiParts));

        // ══════════════════════════════════════════════
        //  METHOD 3: Scanner
        // ══════════════════════════════════════════════
        header("METHOD 3: Scanner with useDelimiter()");

        // Basic CSV
        System.out.println("\n--- Basic CSV parsing ---");
        System.out.println("Input: \"" + csvLine + "\"");
        Scanner scanner = new Scanner(csvLine);
        scanner.useDelimiter(",");
        idx = 1;
        while (scanner.hasNext()) {
            System.out.printf("  Token %d: %s%n", idx++, scanner.next());
        }
        scanner.close();

        // Type-aware parsing
        System.out.println("\n--- Type-aware parsing (nextInt, nextDouble) ---");
        String mixedData = "Alice,28,3.95,true";
        System.out.println("Input: \"" + mixedData + "\"");
        scanner = new Scanner(mixedData);
        scanner.useDelimiter(",");
        String name  = scanner.next();
        int age       = scanner.nextInt();
        double gpa    = scanner.nextDouble();
        boolean honor = scanner.nextBoolean();
        System.out.printf("  Name: %s, Age: %d, GPA: %.2f, Honors: %b%n",
                name, age, gpa, honor);
        scanner.close();

        // Multi-line CSV parsing
        System.out.println("\n--- Multi-line CSV parsing ---");
        System.out.println("Input (multi-line CSV):");
        System.out.println("  " + csvMultiLine.replace("\n", "\n  "));
        scanner = new Scanner(csvMultiLine);
        String headerLine = scanner.nextLine(); // read header
        String[] headers = headerLine.split(",");
        System.out.println("\n  Headers: " + Arrays.toString(headers));
        System.out.println("  Data rows:");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split(",", -1);
            System.out.printf("    %-10s Age=%-4s City=%-10s Salary=%s%n",
                    fields[0], fields[1],
                    fields[2].isEmpty() ? "(missing)" : fields[2],
                    fields[3]);
        }
        scanner.close();

        // Scanner with regex delimiter
        System.out.println("\n--- Scanner with regex delimiter ---");
        System.out.println("Input: \"" + multiDelim + "\"");
        scanner = new Scanner(multiDelim);
        scanner.useDelimiter("[;|,:]");
        while (scanner.hasNext()) {
            System.out.println("  Token: " + scanner.next());
        }
        scanner.close();

        // ══════════════════════════════════════════════
        //  COMPARISON TABLE
        // ══════════════════════════════════════════════
        header("COMPARISON: Side-by-Side");

        System.out.println("\n--- Parsing \"" + withEmpties + "\" ---");
        // StringTokenizer
        st = new StringTokenizer(withEmpties, ",");
        int stCount = st.countTokens();

        // split
        String[] splitResult = withEmpties.split(",", -1);

        // Scanner
        scanner = new Scanner(withEmpties);
        scanner.useDelimiter(",");
        int scannerCount = 0;
        StringBuilder scannerTokens = new StringBuilder();
        while (scanner.hasNext()) {
            if (scannerCount > 0) scannerTokens.append(", ");
            String tok = scanner.next();
            scannerTokens.append("[").append(tok).append("]");
            scannerCount++;
        }
        scanner.close();

        System.out.printf("  %-20s tokens: %d%n", "StringTokenizer", stCount);
        System.out.printf("  %-20s tokens: %d  => %s%n",
                "split(\",\", -1)", splitResult.length,
                Arrays.toString(splitResult));
        System.out.printf("  %-20s tokens: %d  => %s%n",
                "Scanner", scannerCount, scannerTokens);

        System.out.println();
        System.out.println("┌─────────────────────┬────────────────┬──────────────┬────────────────┐");
        System.out.println("│ Feature             │ StringTokenizer│ split()      │ Scanner        │");
        System.out.println("├─────────────────────┼────────────────┼──────────────┼────────────────┤");
        System.out.println("│ Regex support        │ No             │ Yes          │ Yes            │");
        System.out.println("│ Empty tokens         │ Skipped        │ Preserved*   │ Preserved      │");
        System.out.println("│ Streaming            │ Yes            │ No (array)   │ Yes            │");
        System.out.println("│ Typed parsing        │ No             │ No           │ Yes            │");
        System.out.println("│ Thread-safe          │ No             │ N/A (static) │ No             │");
        System.out.println("│ Recommended          │ Legacy         │ General use  │ Mixed types    │");
        System.out.println("└─────────────────────┴────────────────┴──────────────┴────────────────┘");
        System.out.println("  * Use split(regex, -1) to preserve trailing empty tokens.");

        // ══════════════════════════════════════════════
        //  BONUS: Practical CSV Parser
        // ══════════════════════════════════════════════
        header("BONUS: Handling Quoted CSV Fields");

        String quotedCsv = "\"Smith, John\",42,\"New York, NY\",\"He said \"\"hello\"\"\"";
        System.out.println("Input: " + quotedCsv);
        System.out.println("Parsed fields:");
        String[] quotedFields = parseQuotedCSV(quotedCsv);
        for (int i = 0; i < quotedFields.length; i++) {
            System.out.printf("  Field %d: %s%n", i, quotedFields[i]);
        }
    }

    /**
     * Simple quoted CSV parser – handles:
     *   - Comma-separated values
     *   - Quoted fields containing commas
     *   - Escaped quotes ("") inside quoted fields
     * Note: For production use, consider Apache Commons CSV or OpenCSV.
     */
    private static String[] parseQuotedCSV(String line) {
        java.util.List<String> fields = new java.util.ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    // Check for escaped quote ""
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"');
                        i++; // skip next quote
                    } else {
                        inQuotes = false; // closing quote
                    }
                } else {
                    current.append(c);
                }
            } else {
                if (c == '"') {
                    inQuotes = true;
                } else if (c == ',') {
                    fields.add(current.toString());
                    current.setLength(0);
                } else {
                    current.append(c);
                }
            }
        }
        fields.add(current.toString()); // last field

        return fields.toArray(new String[0]);
    }
}
