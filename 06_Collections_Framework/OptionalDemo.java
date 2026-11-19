/*
 * Program 85: Optional Demo
 *
 * Problem Statement:
 *   Demonstrate Java Optional class for null-safe programming:
 *   of, ofNullable, empty, isPresent, ifPresent, orElse, orElseGet,
 *   orElseThrow, map, flatMap, filter.
 *
 * Sample Output:
 *   Value present: Alice
 *   orElse: Guest
 *   Mapped length: 5
 *
 * Time Complexity:  O(1) per operation
 * Space Complexity: O(1)
 */
import java.util.*;
import java.util.stream.Stream;

public class OptionalDemo {

    // Simulated database lookup
    static Map<Integer, String> userDb = Map.of(1,"Alice", 2,"Bob", 3,"Charlie");
    static Map<String, String> emailDb = Map.of("Alice","alice@example.com","Bob","bob@example.com");

    static Optional<String> findUser(int id) {
        return Optional.ofNullable(userDb.get(id));
    }

    static Optional<String> findEmail(String name) {
        return Optional.ofNullable(emailDb.get(name));
    }

    public static void main(String[] args) {

        System.out.println("========== Creating Optionals ==========");
        Optional<String> full    = Optional.of("Alice");          // must be non-null
        Optional<String> maybe   = Optional.ofNullable(null);    // can be null
        Optional<String> present = Optional.ofNullable("Bob");
        Optional<String> empty   = Optional.empty();

        System.out.println("full    : " + full);
        System.out.println("maybe   : " + maybe);
        System.out.println("present : " + present);
        System.out.println("empty   : " + empty);

        // of(null) would throw NullPointerException — demonstrate
        try {
            Optional<String> bad = Optional.of(null);
        } catch (NullPointerException e) {
            System.out.println("Optional.of(null) throws NullPointerException as expected");
        }

        System.out.println("\n========== isPresent / isEmpty / ifPresent ==========");
        System.out.println("full.isPresent() : " + full.isPresent());
        System.out.println("empty.isPresent(): " + empty.isPresent());
        System.out.println("full.isEmpty()   : " + full.isEmpty());
        System.out.println("empty.isEmpty()  : " + empty.isEmpty());

        full.ifPresent(v  -> System.out.println("ifPresent full  : " + v));
        empty.ifPresent(v -> System.out.println("Should not print"));

        // ifPresentOrElse (Java 9+)
        full.ifPresentOrElse(
                v -> System.out.println("ifPresentOrElse: " + v),
                () -> System.out.println("Value is absent")
        );
        empty.ifPresentOrElse(
                v -> System.out.println("Should not print"),
                () -> System.out.println("ifPresentOrElse empty: value is absent")
        );

        System.out.println("\n========== orElse / orElseGet / orElseThrow ==========");
        String val1 = full.orElse("Default");
        String val2 = empty.orElse("Default");
        System.out.println("full.orElse('Default') : " + val1);
        System.out.println("empty.orElse('Default'): " + val2);

        // orElseGet — supplier called only when empty (lazy)
        String val3 = empty.orElseGet(() -> "Computed-" + System.currentTimeMillis() % 1000);
        System.out.println("empty.orElseGet        : " + val3);

        // orElseThrow
        try {
            empty.orElseThrow(() -> new RuntimeException("Value not found!"));
        } catch (RuntimeException e) {
            System.out.println("orElseThrow caught: " + e.getMessage());
        }
        String retrieved = full.orElseThrow(NoSuchElementException::new);
        System.out.println("full.orElseThrow()     : " + retrieved);

        System.out.println("\n========== map and flatMap ==========");
        // map — transform the value if present
        Optional<Integer> len = full.map(String::length);
        Optional<Integer> emptyLen = empty.map(String::length);
        System.out.println("full.map(length) : " + len);     // Optional[5]
        System.out.println("empty.map(length): " + emptyLen); // Optional.empty

        Optional<String> upper = present.map(String::toUpperCase);
        System.out.println("present.map(upper): " + upper);

        // Chain multiple maps
        Optional<String> result = Optional.ofNullable("  hello world  ")
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(s -> s.length() > 3);
        System.out.println("Chain map+filter: " + result);

        // flatMap — when function also returns Optional (avoids Optional<Optional<T>>)
        System.out.println("\n--- flatMap for chained Optional lookups ---");
        // find email of user with id 1
        Optional<String> email1 = findUser(1).flatMap(name -> findEmail(name));
        Optional<String> email4 = findUser(4).flatMap(name -> findEmail(name)); // user not found
        Optional<String> email2 = findUser(2).flatMap(name -> findEmail(name)); // no email for Bob's friend... wait Bob has email
        System.out.println("User 1 email: " + email1.orElse("not found"));
        System.out.println("User 4 email: " + email4.orElse("not found"));

        System.out.println("\n========== filter ==========");
        Optional<Integer> age = Optional.of(25);
        Optional<Integer> senior = age.filter(a -> a >= 60);
        Optional<Integer> adult  = age.filter(a -> a >= 18);
        System.out.println("age=25, filter(>=60): " + senior); // empty
        System.out.println("age=25, filter(>=18): " + adult);  // Optional[25]

        System.out.println("\n========== or (Java 9+) ==========");
        Optional<String> primaryName   = Optional.empty();
        Optional<String> fallbackName  = Optional.of("DefaultUser");
        Optional<String> resolvedName  = primaryName.or(() -> fallbackName);
        System.out.println("primaryName.or(fallback): " + resolvedName);

        System.out.println("\n========== stream (Java 9+) ==========");
        // Convert Optional to Stream — useful in Stream pipelines
        List<Optional<String>> optionals = List.of(
                Optional.of("Alice"), Optional.empty(), Optional.of("Bob"),
                Optional.empty(), Optional.of("Charlie")
        );
        List<String> presentValues = optionals.stream()
                .flatMap(Optional::stream)
                .toList();
        System.out.println("Present values from optionals: " + presentValues);

        System.out.println("\n========== Practical: Safe Division ==========");
        System.out.println("10/2 = " + safeDivide(10, 2).orElse(-1));
        System.out.println("10/0 = " + safeDivide(10, 0).orElse(-1));

        System.out.println("\n========== Practical: Safe Parse Int ==========");
        System.out.println("parseIntSafe('42')  = " + parseIntSafe("42").orElse(0));
        System.out.println("parseIntSafe('abc') = " + parseIntSafe("abc").orElse(0));
        System.out.println("parseIntSafe(null)  = " + parseIntSafe(null).orElse(0));
    }

    static Optional<Integer> safeDivide(int a, int b) {
        if (b == 0) return Optional.empty();
        return Optional.of(a / b);
    }

    static Optional<Integer> parseIntSafe(String s) {
        if (s == null) return Optional.empty();
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
