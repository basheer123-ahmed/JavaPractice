/*
 * Program 46: String Basics
 * 
 * Problem Statement:
 *   Demonstrate String creation (literal vs new), immutability, String pool,
 *   and common methods: length, charAt, indexOf, substring, equals, compareTo,
 *   concat, trim, replace, toUpperCase, toLowerCase.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === String Creation ===
 *   Literal s1: Hello
 *   new String s2: Hello
 *   s1 == s2 (reference): false
 *   s1.equals(s2) (content): true
 *   ...
 *   === Common String Methods ===
 *   Length of "Hello, World!": 13
 *   charAt(0): H
 *   indexOf("World"): 7
 *   ...
 * 
 * Explanation:
 *   - String literals are stored in the String Pool (intern pool). Two literals
 *     with the same content share the same reference.
 *   - `new String(...)` always creates a new object on the heap, bypassing the pool.
 *   - Strings in Java are immutable: every "modification" method returns a new String.
 *   - equals() compares content; == compares references.
 *   - compareTo() returns lexicographic difference (0 if equal, negative/positive otherwise).
 * 
 * Time Complexity: O(n) for most individual String methods where n = string length
 * Space Complexity: O(n) for methods that create new String objects
 */

public class StringBasics {

    public static void main(String[] args) {

        // =====================================================================
        // 1. STRING CREATION: Literal vs new
        // =====================================================================
        System.out.println("=== String Creation: Literal vs new ===");

        String s1 = "Hello";           // String pool literal
        String s2 = new String("Hello"); // Heap object (not from pool)
        String s3 = "Hello";           // Reuses same pool literal as s1

        System.out.println("s1 (literal)      : " + s1);
        System.out.println("s2 (new String)   : " + s2);
        System.out.println("s3 (literal)      : " + s3);
        System.out.println();

        // Reference comparison (==) vs Content comparison (equals)
        System.out.println("s1 == s2           : " + (s1 == s2));       // false – different objects
        System.out.println("s1 == s3           : " + (s1 == s3));       // true  – same pool reference
        System.out.println("s1.equals(s2)      : " + s1.equals(s2));    // true  – same content
        System.out.println("s1.equals(s3)      : " + s1.equals(s3));    // true
        System.out.println();

        // =====================================================================
        // 2. STRING POOL & intern()
        // =====================================================================
        System.out.println("=== String Pool & intern() ===");

        String s4 = s2.intern(); // Returns the pool reference for "Hello"
        System.out.println("s2.intern() == s1  : " + (s4 == s1));       // true
        System.out.println();

        // =====================================================================
        // 3. IMMUTABILITY DEMONSTRATION
        // =====================================================================
        System.out.println("=== Immutability ===");

        String original = "Java";
        String modified = original.concat(" Programming");
        System.out.println("original           : " + original);   // Still "Java"
        System.out.println("modified           : " + modified);   // "Java Programming"
        System.out.println("original unchanged?: " + original.equals("Java")); // true
        System.out.println();

        // =====================================================================
        // 4. COMMON STRING METHODS
        // =====================================================================
        String str = "Hello, World!";
        System.out.println("=== Common String Methods on \"" + str + "\" ===");

        // length()
        System.out.println("length()           : " + str.length());           // 13

        // charAt(index)
        System.out.println("charAt(0)          : " + str.charAt(0));          // H
        System.out.println("charAt(7)          : " + str.charAt(7));          // W

        // indexOf / lastIndexOf
        System.out.println("indexOf('o')       : " + str.indexOf('o'));       // 4
        System.out.println("lastIndexOf('o')   : " + str.lastIndexOf('o'));   // 8
        System.out.println("indexOf(\"World\")   : " + str.indexOf("World")); // 7
        System.out.println("indexOf(\"xyz\")     : " + str.indexOf("xyz"));   // -1

        // substring(begin) and substring(begin, end)
        System.out.println("substring(7)       : " + str.substring(7));       // World!
        System.out.println("substring(0,5)     : " + str.substring(0, 5));    // Hello

        // equals and equalsIgnoreCase
        System.out.println("equals(\"hello, world!\")          : " + str.equals("hello, world!"));
        System.out.println("equalsIgnoreCase(\"hello, world!\"): " + str.equalsIgnoreCase("hello, world!"));

        // compareTo (lexicographic)
        System.out.println("\"apple\".compareTo(\"banana\")      : " + "apple".compareTo("banana")); // negative
        System.out.println("\"banana\".compareTo(\"apple\")      : " + "banana".compareTo("apple")); // positive
        System.out.println("\"apple\".compareTo(\"apple\")       : " + "apple".compareTo("apple"));  // 0

        // concat
        System.out.println("\"Hello\".concat(\" Java\")          : " + "Hello".concat(" Java"));

        // trim / strip
        String padded = "   spaced out   ";
        System.out.println("trim(\"" + padded + "\")   : \"" + padded.trim() + "\"");
        System.out.println("strip(\"" + padded + "\")  : \"" + padded.strip() + "\"");

        // replace
        System.out.println("replace('l','r')   : " + str.replace('l', 'r'));          // Herro, Worrd!
        System.out.println("replace(\"World\",\"Java\"): " + str.replace("World", "Java")); // Hello, Java!

        // toUpperCase / toLowerCase
        System.out.println("toUpperCase()      : " + str.toUpperCase());
        System.out.println("toLowerCase()      : " + str.toLowerCase());

        // contains, startsWith, endsWith
        System.out.println("contains(\"World\")  : " + str.contains("World"));     // true
        System.out.println("startsWith(\"Hello\"): " + str.startsWith("Hello"));   // true
        System.out.println("endsWith(\"!\")      : " + str.endsWith("!"));         // true

        // isEmpty and isBlank (Java 11+)
        System.out.println("\"\" .isEmpty()      : " + "".isEmpty());     // true
        System.out.println("\"  \".isEmpty()     : " + "  ".isEmpty());   // false
        System.out.println("\"  \".isBlank()     : " + "  ".isBlank());   // true

        // toCharArray
        char[] chars = "ABC".toCharArray();
        System.out.print("toCharArray(\"ABC\") : ");
        for (char c : chars) System.out.print(c + " ");
        System.out.println();

        // split
        String csv = "one,two,three,four";
        String[] parts = csv.split(",");
        System.out.print("split(\"" + csv + "\", \",\"): ");
        for (String p : parts) System.out.print("[" + p + "] ");
        System.out.println();

        // valueOf (static)
        System.out.println("String.valueOf(42) : " + String.valueOf(42));
        System.out.println("String.valueOf(3.14): " + String.valueOf(3.14));

        // =====================================================================
        // 5. StringBuilder vs StringBuffer (bonus)
        // =====================================================================
        System.out.println();
        System.out.println("=== StringBuilder (mutable, not thread-safe) ===");
        StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World");
        sb.insert(5, ",");
        sb.replace(7, 12, "Java");
        sb.delete(0, 0); // no-op demo
        System.out.println("Result             : " + sb.toString());
        System.out.println("Reverse            : " + sb.reverse().toString());

        System.out.println();
        System.out.println("=== StringBuffer (mutable, thread-safe) ===");
        StringBuffer sbuf = new StringBuffer("Thread");
        sbuf.append("-Safe");
        System.out.println("Result             : " + sbuf.toString());

        System.out.println();
        System.out.println("=== Summary ===");
        System.out.println("• String   : immutable, stored in pool, use for constants");
        System.out.println("• StringBuilder : mutable, faster, NOT thread-safe");
        System.out.println("• StringBuffer  : mutable, slower,  thread-safe");
    }
}
