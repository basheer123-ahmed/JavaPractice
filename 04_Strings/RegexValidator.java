/*
 * Program 56: RegexValidator
 * 
 * Problem Statement:
 *   Validate common data formats using Java regular expressions with the
 *   Pattern and Matcher classes. Supported validations include:
 *     - Email addresses (RFC 5322 simplified)
 *     - Phone numbers (US formats: (xxx) xxx-xxxx, xxx-xxx-xxxx, +1xxxxxxxxxx, etc.)
 *     - URLs (http/https with optional port and path)
 *     - IPv4 addresses (0.0.0.0 – 255.255.255.255)
 *     - IPv6 addresses (8 groups of 4 hex digits, with :: shorthand)
 *   Additionally demonstrates Pattern flags, named groups, find() vs matches(),
 *   and how to extract matched portions from text.
 * 
 * Sample Input:  (hard-coded test cases)
 * Sample Output:
 *   === EMAIL VALIDATION ===
 *   "user@example.com"              => VALID
 *   "john.doe+tag@sub.domain.org"   => VALID
 *   "@missing-local.com"            => INVALID
 *   ...
 *   === PHONE NUMBER VALIDATION ===
 *   "(555) 123-4567"                => VALID
 *   ...
 *   === EXTRACT ALL EMAILS FROM TEXT ===
 *   Found: alice@example.com
 *   Found: bob@company.org
 * 
 * Explanation:
 *   Pattern.compile() compiles a regex into a reusable Pattern object.
 *   Matcher is obtained from pattern.matcher(input) and provides:
 *     - matches()  : true if the ENTIRE input matches the pattern
 *     - find()     : true if a subsequence matches (can be called repeatedly)
 *     - group()    : returns the matched substring
 *     - group(name): returns a named capturing group
 *   We use verbose patterns with (?x) or COMMENTS flag where helpful.
 * 
 * Time Complexity:  O(n) per validation where n = input length (for these patterns)
 * Space Complexity: O(1) per validation (compiled Pattern is reusable)
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator {

    // ──────────────────────────────────────────────
    //  Compiled Pattern constants (thread-safe, reusable)
    // ──────────────────────────────────────────────

    /**
     * EMAIL – simplified RFC-5322:
     *   local-part : [A-Za-z0-9._%+-]+
     *   @
     *   domain     : [A-Za-z0-9.-]+\.[A-Za-z]{2,}
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$"
    );

    /**
     * US PHONE NUMBER – accepts several common formats:
     *   (555) 123-4567    555-123-4567    555.123.4567
     *   5551234567        +1-555-123-4567  +15551234567
     *   1-555-123-4567
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(\\+?1[\\-. ]?)?"                     // optional country code +1
          + "(\\(\\d{3}\\)|\\d{3})"                  // area code (xxx) or xxx
          + "[\\-. ]?"                               // separator
          + "\\d{3}"                                 // exchange
          + "[\\-. ]?"                               // separator
          + "\\d{4}$"                                // subscriber
    );

    /**
     * URL – http or https, optional port, optional path/query/fragment.
     * Uses named groups for demonstration.
     */
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(?<scheme>https?)://"                          // scheme
          + "(?<host>[A-Za-z0-9.\\-]+)"                      // host
          + "(?::(?<port>\\d{1,5}))?"                        // optional port
          + "(?<path>/[A-Za-z0-9._~:/?#\\[\\]@!$&'()*+,;%\\-]*)?" // optional path
          + "$"
    );

    /**
     * IPv4 – four octets 0-255 separated by dots.
     * Each octet: 25[0-5] | 2[0-4]\d | 1\d\d | [1-9]?\d
     */
    private static final String IPV4_OCTET =
            "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)";
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^" + IPV4_OCTET + "\\." + IPV4_OCTET + "\\."
                + IPV4_OCTET + "\\." + IPV4_OCTET + "$"
    );

    /**
     * IPv6 – simplified: 8 groups of 1-4 hex digits separated by colons,
     * OR contains "::" for zero-group compression.
     */
    private static final Pattern IPV6_PATTERN = Pattern.compile(
            "^("
          +   "([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}"           // full form
          + "|"
          +   "([0-9a-fA-F]{1,4}:){1,7}:"                         // :: at end
          + "|"
          +   "([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}"        // ::x
          + "|"
          +   "([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}" // ::x:x
          + "|"
          +   "([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}"
          + "|"
          +   "([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}"
          + "|"
          +   "([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}"
          + "|"
          +   "[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})"
          + "|"
          +   ":((:[0-9a-fA-F]{1,4}){1,7}|:)"                     // ::x...x or ::
          + ")$"
    );

    // ──────────────────────────────────────────────
    //  Validation methods
    // ──────────────────────────────────────────────

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidURL(String url) {
        return URL_PATTERN.matcher(url).matches();
    }

    public static boolean isValidIPv4(String ip) {
        return IPV4_PATTERN.matcher(ip).matches();
    }

    public static boolean isValidIPv6(String ip) {
        return IPV6_PATTERN.matcher(ip).matches();
    }

    // ──────────────────────────────────────────────
    //  Helper: print validation result
    // ──────────────────────────────────────────────

    private static void check(String label, String input, boolean valid) {
        System.out.printf("  %-42s => %s%n",
                "\"" + input + "\"", valid ? "VALID" : "INVALID");
    }

    // ──────────────────────────────────────────────
    //  Main – comprehensive demonstration
    // ──────────────────────────────────────────────

    public static void main(String[] args) {

        // ============ EMAIL ============
        System.out.println("=== EMAIL VALIDATION ===");
        String[] emails = {
            "user@example.com",
            "john.doe+tag@sub.domain.org",
            "firstname.lastname@company.co.uk",
            "user123@test.io",
            "@missing-local.com",
            "missing-at-sign.com",
            "user@.invalid-domain",
            "user@com",
            "",
            "spaces not@allowed.com"
        };
        for (String e : emails) check("Email", e, isValidEmail(e));

        // ============ PHONE ============
        System.out.println("\n=== PHONE NUMBER VALIDATION ===");
        String[] phones = {
            "(555) 123-4567",
            "555-123-4567",
            "555.123.4567",
            "5551234567",
            "+1-555-123-4567",
            "+15551234567",
            "1-800-555-0199",
            "123-45-6789",        // too few digits in a group
            "(555)1234567",
            "555 123 4567"
        };
        for (String p : phones) check("Phone", p, isValidPhone(p));

        // ============ URL ============
        System.out.println("\n=== URL VALIDATION ===");
        String[] urls = {
            "https://www.example.com",
            "http://example.com/path/to/page",
            "https://api.example.com:8080/v2/resource",
            "http://localhost:3000",
            "https://example.com/search?q=java&lang=en",
            "ftp://files.example.com",    // scheme not http/https
            "://missing-scheme.com",
            "https://",
            "not-a-url",
            "http://example.com:99999"    // port too long? still digits ≤5
        };
        for (String u : urls) check("URL", u, isValidURL(u));

        // ============ IPv4 ============
        System.out.println("\n=== IPv4 VALIDATION ===");
        String[] ipv4s = {
            "192.168.1.1",
            "0.0.0.0",
            "255.255.255.255",
            "10.0.0.1",
            "172.16.254.1",
            "256.1.1.1",          // 256 > 255
            "192.168.1",          // only 3 octets
            "192.168.1.1.1",      // 5 octets
            "abc.def.ghi.jkl",
            "192.168.01.1"        // leading zero — allowed by this regex
        };
        for (String ip : ipv4s) check("IPv4", ip, isValidIPv4(ip));

        // ============ IPv6 ============
        System.out.println("\n=== IPv6 VALIDATION ===");
        String[] ipv6s = {
            "2001:0db8:85a3:0000:0000:8a2e:0370:7334",   // full
            "2001:db8:85a3::8a2e:370:7334",               // compressed
            "::1",                                         // loopback
            "::",                                          // all zeros
            "fe80::1",                                     // link-local
            "2001:db8::g123:4567",                         // 'g' invalid hex
            "12345::abcd",                                 // group >4 hex digits
            "2001:db8:85a3:0000:0000:8a2e:0370"            // only 7 groups, no ::
        };
        for (String ip : ipv6s) check("IPv6", ip, isValidIPv6(ip));

        // ============ NAMED GROUPS ============
        System.out.println("\n=== NAMED GROUP EXTRACTION (URL) ===");
        String testUrl = "https://api.example.com:8080/v2/users?active=true";
        Matcher m = URL_PATTERN.matcher(testUrl);
        if (m.matches()) {
            System.out.println("  Input : " + testUrl);
            System.out.println("  Scheme: " + m.group("scheme"));
            System.out.println("  Host  : " + m.group("host"));
            System.out.println("  Port  : " + m.group("port"));
            System.out.println("  Path  : " + m.group("path"));
        }

        // ============ find() – EXTRACT ALL MATCHES ============
        System.out.println("\n=== EXTRACT ALL EMAILS FROM TEXT (find()) ===");
        String text = "Contact alice@example.com or bob@company.org for info. "
                     + "Invalid: @nobody, good: dev.ops+ci@build-server.io";
        // Reuse a non-anchored version of the email pattern for find()
        Pattern emailFinder = Pattern.compile(
                "[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}"
        );
        Matcher finder = emailFinder.matcher(text);
        System.out.println("  Text: \"" + text + "\"");
        int count = 0;
        while (finder.find()) {
            count++;
            System.out.printf("  Match %d [%d-%d]: %s%n",
                    count, finder.start(), finder.end(), finder.group());
        }
        System.out.println("  Total emails found: " + count);

        // ============ PATTERN FLAGS ============
        System.out.println("\n=== PATTERN FLAGS DEMONSTRATION ===");

        // CASE_INSENSITIVE
        Pattern ciPattern = Pattern.compile("hello", Pattern.CASE_INSENSITIVE);
        System.out.println("  Case-insensitive 'hello' vs 'HeLLo': "
                + ciPattern.matcher("HeLLo").matches());

        // MULTILINE – ^ and $ match line boundaries
        Pattern mlPattern = Pattern.compile("^Error:.*$", Pattern.MULTILINE);
        String multiline = "Info: all good\nError: disk full\nWarning: low mem";
        Matcher mlMatcher = mlPattern.matcher(multiline);
        System.out.println("  Multiline find 'Error:' lines:");
        while (mlMatcher.find()) {
            System.out.println("    -> " + mlMatcher.group());
        }

        // DOTALL – dot matches newlines
        Pattern dsPattern = Pattern.compile("start(.*)end", Pattern.DOTALL);
        String dsInput = "start\nMiddle\nLines\nend";
        Matcher dsMatcher = dsPattern.matcher(dsInput);
        if (dsMatcher.find()) {
            System.out.println("  DOTALL capture: '"
                    + dsMatcher.group(1).replace("\n", "\\n") + "'");
        }

        // COMMENTS (verbose mode)
        Pattern commented = Pattern.compile(
                "\\d{3}  # area code  \n"
              + "-       # dash       \n"
              + "\\d{4}  # number     \n",
                Pattern.COMMENTS
        );
        System.out.println("  COMMENTS mode '555-1234': "
                + commented.matcher("555-1234").matches());

        // ============ COMMON REGEX PATTERNS REFERENCE ============
        System.out.println("\n=== QUICK REGEX REFERENCE ===");
        System.out.println("  .        Any character (except newline w/o DOTALL)");
        System.out.println("  \\d       Digit [0-9]");
        System.out.println("  \\w       Word char [A-Za-z0-9_]");
        System.out.println("  \\s       Whitespace [ \\t\\n\\r\\f]");
        System.out.println("  *        0 or more (greedy)");
        System.out.println("  +        1 or more (greedy)");
        System.out.println("  ?        0 or 1 (greedy)");
        System.out.println("  *? +? ?? Non-greedy (lazy) versions");
        System.out.println("  {n,m}    Between n and m repetitions");
        System.out.println("  ^  $     Start / end of string (or line w/ MULTILINE)");
        System.out.println("  (...)    Capturing group");
        System.out.println("  (?:...)  Non-capturing group");
        System.out.println("  (?<n>.)  Named capturing group");
        System.out.println("  (?=...)  Positive lookahead");
        System.out.println("  (?!...)  Negative lookahead");
        System.out.println("  (?<=...) Positive lookbehind");
        System.out.println("  (?<!...) Negative lookbehind");
    }
}
