/*
 * Program 58: StringBuilderDemo
 * 
 * Problem Statement:
 *   Demonstrate StringBuilder vs String performance for building large strings.
 *   Compare concatenation with += (creates new String objects each time) versus
 *   StringBuilder.append() (modifies internal buffer in-place). Time both
 *   approaches and showcase ALL StringBuilder methods with examples.
 *   Also compare StringBuffer (thread-safe, synchronized) vs StringBuilder.
 * 
 * Sample Input:  (none — self-contained benchmarks)
 * Sample Output:
 *   === PERFORMANCE COMPARISON ===
 *   String concatenation (50000 iterations): 1523 ms
 *   StringBuilder.append  (50000 iterations):    2 ms
 *   Speedup: ~761x faster
 *   ...
 *   === ALL STRINGBUILDER METHODS ===
 *   append():    "Hello World 42 3.14 true"
 *   insert():    "Hello Beautiful World"
 *   delete():    "Hello World"
 *   ...
 * 
 * Explanation:
 *   String is immutable in Java. Each += creates a new String object, copies
 *   old content + new content. For n concatenations of average length m, this
 *   is O(n² * m) time and creates n temporary String objects for GC.
 *   StringBuilder uses a resizable char[] (since Java 9: byte[]) buffer and
 *   grows by doubling capacity, giving amortized O(1) per append and O(n*m) total.
 * 
 * Time Complexity:  String concatenation loop: O(n²), StringBuilder loop: O(n)
 * Space Complexity: String concat: O(n²) total allocated, StringBuilder: O(n)
 */

public class StringBuilderDemo {

    // ──────────────────────────────────────────────
    //  Performance benchmark
    // ──────────────────────────────────────────────

    private static void performanceBenchmark() {
        System.out.println("=== PERFORMANCE COMPARISON: String += vs StringBuilder ===\n");

        int[] sizes = {1_000, 10_000, 50_000};

        for (int n : sizes) {
            // --- String concatenation ---
            long startTime = System.nanoTime();
            String s = "";
            for (int i = 0; i < n; i++) {
                s += "a";
            }
            long stringTime = System.nanoTime() - startTime;

            // --- StringBuilder ---
            startTime = System.nanoTime();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append("a");
            }
            String result = sb.toString();
            long builderTime = System.nanoTime() - startTime;

            // Both produce the same result
            assert s.length() == result.length();

            double stringMs  = stringTime / 1_000_000.0;
            double builderMs = builderTime / 1_000_000.0;
            double speedup   = (double) stringTime / builderTime;

            System.out.printf("  n = %,6d:%n", n);
            System.out.printf("    String +=          : %10.2f ms%n", stringMs);
            System.out.printf("    StringBuilder      : %10.2f ms%n", builderMs);
            System.out.printf("    Speedup            : %10.1fx faster%n%n", speedup);
        }

        // --- Why is String += slow? ---
        System.out.println("  WHY String += IS SLOW:");
        System.out.println("  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │ Iteration 1: new String(\"\" + \"a\")           => \"a\"      │");
        System.out.println("  │ Iteration 2: new String(\"a\" + \"a\")          => \"aa\"     │");
        System.out.println("  │ Iteration 3: new String(\"aa\" + \"a\")         => \"aaa\"    │");
        System.out.println("  │ ...                                                     │");
        System.out.println("  │ Iteration n: new String(n-1 chars + \"a\")     => n chars  │");
        System.out.println("  │                                                         │");
        System.out.println("  │ Total chars copied: 1+2+3+...+n = n(n+1)/2 = O(n²)     │");
        System.out.println("  │ Total objects created: n String objects (all GC'd)       │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("  WHY StringBuilder IS FAST:");
        System.out.println("  ┌─────────────────────────────────────────────────────────┐");
        System.out.println("  │ Internal buffer: char[] (resizable, starts at cap 16)   │");
        System.out.println("  │ Append: copy new chars to buffer end (amortized O(1))   │");
        System.out.println("  │ Resize: double capacity when full (rare, O(n) copy)     │");
        System.out.println("  │ Total work: O(n) for n appends                          │");
        System.out.println("  │ Objects created: 1 StringBuilder + a few char[] arrays   │");
        System.out.println("  └─────────────────────────────────────────────────────────┘");
    }

    // ──────────────────────────────────────────────
    //  All StringBuilder methods
    // ──────────────────────────────────────────────

    private static void allMethods() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ALL STRINGBUILDER METHODS");
        System.out.println("=".repeat(60));

        // ── CONSTRUCTORS ──
        System.out.println("\n--- Constructors ---");

        StringBuilder sb1 = new StringBuilder();            // default capacity 16
        StringBuilder sb2 = new StringBuilder(100);         // specified capacity
        StringBuilder sb3 = new StringBuilder("Hello");     // from String
        StringBuilder sb4 = new StringBuilder((CharSequence) "World"); // from CharSequence

        System.out.println("  new StringBuilder()          -> capacity: " + sb1.capacity()
                + ", length: " + sb1.length());
        System.out.println("  new StringBuilder(100)       -> capacity: " + sb2.capacity()
                + ", length: " + sb2.length());
        System.out.println("  new StringBuilder(\"Hello\")   -> capacity: " + sb3.capacity()
                + ", length: " + sb3.length() + ", value: \"" + sb3 + "\"");

        // ── APPEND ──
        System.out.println("\n--- append() — 13 overloads ---");
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");                     // String
        sb.append(' ');                          // char
        sb.append("World");                     // String
        sb.append(' ');
        sb.append(42);                           // int
        sb.append(' ');
        sb.append(3.14);                         // double
        sb.append(' ');
        sb.append(true);                         // boolean
        sb.append(' ');
        sb.append(100L);                         // long
        sb.append(' ');
        sb.append(2.5f);                         // float
        sb.append(' ');
        sb.append(new char[]{'A','B','C'});      // char[]
        sb.append(' ');
        sb.append(new char[]{'X','Y','Z'}, 1, 2);// char[], offset, len -> "YZ"
        sb.append(' ');
        sb.append((Object) null);                // Object (prints "null")
        System.out.println("  Result: \"" + sb + "\"");

        // ── INSERT ──
        System.out.println("\n--- insert() ---");
        sb = new StringBuilder("Hello World");
        sb.insert(5, " Beautiful");              // insert at index
        System.out.println("  insert(5, \" Beautiful\"): \"" + sb + "\"");

        sb = new StringBuilder("Value: ");
        sb.insert(7, 42);                        // insert int
        System.out.println("  insert(7, 42):            \"" + sb + "\"");

        sb = new StringBuilder("Pi is  approximately");
        sb.insert(6, 3.14159);                   // insert double
        System.out.println("  insert(6, 3.14159):       \"" + sb + "\"");

        // ── DELETE ──
        System.out.println("\n--- delete() and deleteCharAt() ---");
        sb = new StringBuilder("Hello Beautiful World");
        sb.delete(5, 15);                        // delete range [5, 15)
        System.out.println("  delete(5, 15):    \"" + sb + "\"");

        sb = new StringBuilder("Helllo");
        sb.deleteCharAt(3);                      // delete single char
        System.out.println("  deleteCharAt(3):  \"" + sb + "\"");

        // ── REPLACE ──
        System.out.println("\n--- replace() ---");
        sb = new StringBuilder("Hello World");
        sb.replace(6, 11, "Java");               // replace range with new string
        System.out.println("  replace(6, 11, \"Java\"): \"" + sb + "\"");

        sb.replace(0, 5, "Hi");                  // replacement can be shorter
        System.out.println("  replace(0, 5, \"Hi\"):    \"" + sb + "\"");

        // ── REVERSE ──
        System.out.println("\n--- reverse() ---");
        sb = new StringBuilder("Hello");
        sb.reverse();
        System.out.println("  \"Hello\".reverse(): \"" + sb + "\"");

        sb = new StringBuilder("racecar");
        String original = sb.toString();
        sb.reverse();
        System.out.println("  \"racecar\" reversed: \"" + sb 
                + "\" (palindrome: " + original.equals(sb.toString()) + ")");

        // ── CHARAT / SETCHARAT ──
        System.out.println("\n--- charAt() and setCharAt() ---");
        sb = new StringBuilder("Hello");
        System.out.println("  charAt(0): '" + sb.charAt(0) + "'");
        System.out.println("  charAt(4): '" + sb.charAt(4) + "'");
        sb.setCharAt(0, 'J');
        System.out.println("  setCharAt(0, 'J'): \"" + sb + "\"");

        // ── SUBSTRING ──
        System.out.println("\n--- substring() ---");
        sb = new StringBuilder("Hello World");
        System.out.println("  substring(6):    \"" + sb.substring(6) + "\"");
        System.out.println("  substring(0, 5): \"" + sb.substring(0, 5) + "\"");

        // ── INDEXOF / LASTINDEXOF ──
        System.out.println("\n--- indexOf() and lastIndexOf() ---");
        sb = new StringBuilder("Hello World Hello");
        System.out.println("  Source: \"" + sb + "\"");
        System.out.println("  indexOf(\"Hello\"):      " + sb.indexOf("Hello"));
        System.out.println("  indexOf(\"Hello\", 1):   " + sb.indexOf("Hello", 1));
        System.out.println("  lastIndexOf(\"Hello\"):  " + sb.lastIndexOf("Hello"));
        System.out.println("  indexOf(\"xyz\"):        " + sb.indexOf("xyz") + " (not found)");

        // ── LENGTH / CAPACITY / ENSURECAPACITY / TRIMTOSIZE ──
        System.out.println("\n--- length(), capacity(), ensureCapacity(), trimToSize() ---");
        sb = new StringBuilder("Hello");
        System.out.println("  length():   " + sb.length());
        System.out.println("  capacity(): " + sb.capacity()
                + "  (default = initialString.length + 16 = " + (5 + 16) + ")");

        sb.ensureCapacity(100);
        System.out.println("  ensureCapacity(100) -> capacity: " + sb.capacity());

        sb.trimToSize();
        System.out.println("  trimToSize()        -> capacity: " + sb.capacity()
                + " (matches length)");

        // ── SETLENGTH ──
        System.out.println("\n--- setLength() ---");
        sb = new StringBuilder("Hello World");
        sb.setLength(5);    // truncate
        System.out.println("  setLength(5): \"" + sb + "\"");

        sb.setLength(10);   // pad with null chars
        System.out.println("  setLength(10): \"" + sb + "\"  (padded with \\0)");
        System.out.println("  Actual chars: ");
        for (int i = 0; i < sb.length(); i++) {
            System.out.printf("    [%d] = '%s' (code %d)%n",
                    i, sb.charAt(i) == '\0' ? "\\0" : "" + sb.charAt(i),
                    (int) sb.charAt(i));
        }

        // ── SUBSEQUENCE ──
        System.out.println("\n--- subSequence() (implements CharSequence) ---");
        sb = new StringBuilder("Hello World");
        CharSequence cs = sb.subSequence(0, 5);
        System.out.println("  subSequence(0, 5): \"" + cs + "\"");

        // ── GETCHAR / CODEPOINTAT / CODEPOINTCOUNT ──
        System.out.println("\n--- codePointAt(), codePointBefore(), codePointCount() ---");
        sb = new StringBuilder("A\uD83D\uDE00Z");  // A, 😀, Z
        System.out.println("  Source: \"A😀Z\"  (length = " + sb.length() + " chars, but 3 code points)");
        System.out.println("  codePointAt(0):      " + sb.codePointAt(0) + " ('A')");
        System.out.println("  codePointAt(1):      " + sb.codePointAt(1) + " (😀 = U+1F600)");
        System.out.println("  codePointCount(0,4): " + sb.codePointCount(0, sb.length()));

        // ── CHARS / TOSTRING ──
        System.out.println("\n--- chars() stream and toString() ---");
        sb = new StringBuilder("Hello");
        System.out.print("  chars(): ");
        sb.chars().forEach(c -> System.out.print((char) c + " "));
        System.out.println();
        System.out.println("  toString(): \"" + sb.toString() + "\"");

        // ── APPENDCODEPOINT ──
        System.out.println("\n--- appendCodePoint() ---");
        sb = new StringBuilder("Hello ");
        sb.appendCodePoint(0x1F600); // 😀
        sb.appendCodePoint(0x1F4BB); // 💻
        System.out.println("  appendCodePoint(0x1F600, 0x1F4BB): \"" + sb + "\"");

        // ── GETCHARS ──
        System.out.println("\n--- getChars() ---");
        sb = new StringBuilder("Hello World");
        char[] dst = new char[5];
        sb.getChars(6, 11, dst, 0);  // copy "World" into dst
        System.out.println("  getChars(6, 11, dst, 0): \"" + new String(dst) + "\"");
    }

    // ──────────────────────────────────────────────
    //  StringBuilder vs StringBuffer
    // ──────────────────────────────────────────────

    private static void builderVsBuffer() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  STRINGBUILDER vs STRINGBUFFER");
        System.out.println("=".repeat(60));

        int iterations = 500_000;

        // StringBuilder (not synchronized)
        long start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append('x');
        }
        long builderTime = System.nanoTime() - start;

        // StringBuffer (synchronized)
        start = System.nanoTime();
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sbuf.append('x');
        }
        long bufferTime = System.nanoTime() - start;

        System.out.printf("%n  %,d iterations:%n", iterations);
        System.out.printf("    StringBuilder : %.2f ms%n", builderTime / 1_000_000.0);
        System.out.printf("    StringBuffer  : %.2f ms%n", bufferTime / 1_000_000.0);
        System.out.printf("    StringBuilder is %.1fx faster%n%n",
                (double) bufferTime / builderTime);

        System.out.println("  ┌─────────────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │ Feature             │ StringBuilder    │ StringBuffer     │");
        System.out.println("  ├─────────────────────┼──────────────────┼──────────────────┤");
        System.out.println("  │ Thread-safe         │ No               │ Yes (synchronized│");
        System.out.println("  │ Performance         │ Faster           │ Slower           │");
        System.out.println("  │ Introduced          │ Java 5 (1.5)     │ Java 1.0         │");
        System.out.println("  │ When to use         │ Single-threaded  │ Multi-threaded   │");
        System.out.println("  │ Common interview Q  │ ✓ (preferred)    │ ✓ (know the diff)│");
        System.out.println("  └─────────────────────┴──────────────────┴──────────────────┘");
    }

    // ──────────────────────────────────────────────
    //  Practical examples
    // ──────────────────────────────────────────────

    private static void practicalExamples() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  PRACTICAL STRINGBUILDER EXAMPLES");
        System.out.println("=".repeat(60));

        // 1. Building SQL queries
        System.out.println("\n--- 1. Building SQL Query ---");
        String[] columns = {"name", "age", "city", "salary"};
        String table = "employees";
        String whereClause = "age > 25 AND city = 'Boston'";

        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            if (i > 0) sql.append(", ");
            sql.append(columns[i]);
        }
        sql.append(" FROM ").append(table);
        sql.append(" WHERE ").append(whereClause);
        sql.append(";");
        System.out.println("  " + sql);

        // 2. Joining strings with delimiter (like String.join)
        System.out.println("\n--- 2. Custom join with delimiter ---");
        String[] words = {"Java", "is", "awesome"};
        System.out.println("  join(\" | \"): " + joinWith(words, " | "));
        System.out.println("  join(\", \"): " + joinWith(words, ", "));

        // 3. Reversing words in a sentence
        System.out.println("\n--- 3. Reverse words in sentence ---");
        String sentence = "Hello World from Java";
        String[] wordArr = sentence.split(" ");
        StringBuilder reversed = new StringBuilder();
        for (int i = wordArr.length - 1; i >= 0; i--) {
            if (reversed.length() > 0) reversed.append(' ');
            reversed.append(wordArr[i]);
        }
        System.out.println("  Input:  \"" + sentence + "\"");
        System.out.println("  Output: \"" + reversed + "\"");

        // 4. Building formatted table
        System.out.println("\n--- 4. Building formatted table ---");
        String[][] data = {
            {"Name", "Score", "Grade"},
            {"Alice", "95", "A"},
            {"Bob", "82", "B"},
            {"Charlie", "78", "C+"}
        };
        StringBuilder tableStr = new StringBuilder();
        String divider = "+" + "-".repeat(12) + "+" + "-".repeat(8) + "+" + "-".repeat(8) + "+\n";
        tableStr.append(divider);
        for (int r = 0; r < data.length; r++) {
            tableStr.append(String.format("| %-10s | %-6s | %-6s |%n",
                    data[r][0], data[r][1], data[r][2]));
            if (r == 0) tableStr.append(divider);
        }
        tableStr.append(divider);
        System.out.print("  " + tableStr.toString().replace("\n", "\n  "));

        // 5. Method chaining
        System.out.println("\n--- 5. Method chaining (fluent API) ---");
        String result = new StringBuilder()
                .append("Hello")
                .append(" ")
                .append("World")
                .insert(5, ",")
                .replace(7, 12, "Java")
                .append("!")
                .reverse()
                .toString();
        System.out.println("  Chained result: \"" + result + "\"");
    }

    private static String joinWith(String[] items, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            if (i > 0) sb.append(delimiter);
            sb.append(items[i]);
        }
        return sb.toString();
    }

    // ──────────────────────────────────────────────
    //  Capacity growth visualization
    // ──────────────────────────────────────────────

    private static void capacityGrowth() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  CAPACITY GROWTH VISUALIZATION");
        System.out.println("=".repeat(60));

        System.out.println("\n  Appending 1 char at a time, tracking capacity changes:\n");
        System.out.printf("  %-10s %-10s %-10s %s%n",
                "Length", "Capacity", "Used%", "Resize?");
        System.out.println("  " + "-".repeat(45));

        StringBuilder sb = new StringBuilder();  // initial capacity = 16
        int prevCap = sb.capacity();
        System.out.printf("  %-10d %-10d %-10s %s%n",
                sb.length(), sb.capacity(), "0%", "initial");

        for (int i = 1; i <= 100; i++) {
            sb.append('a');
            int curCap = sb.capacity();
            if (curCap != prevCap || i <= 2 || i == 16 || i == 17) {
                String used = String.format("%.0f%%",
                        100.0 * sb.length() / sb.capacity());
                String resize = curCap != prevCap ? "YES (old=" + prevCap + ")" : "";
                System.out.printf("  %-10d %-10d %-10s %s%n",
                        sb.length(), sb.capacity(), used, resize);
                prevCap = curCap;
            }
        }

        System.out.println("\n  Growth formula: newCapacity = (oldCapacity + 1) * 2");
        System.out.println("  Example: 16 -> (16+1)*2 = 34 -> (34+1)*2 = 70 -> ...");
    }

    // ──────────────────────────────────────────────
    //  Main
    // ──────────────────────────────────────────────

    public static void main(String[] args) {
        performanceBenchmark();
        allMethods();
        builderVsBuffer();
        practicalExamples();
        capacityGrowth();
    }
}
