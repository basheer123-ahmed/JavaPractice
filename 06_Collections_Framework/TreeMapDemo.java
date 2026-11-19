/*
 * Program 80: TreeMap Demo
 *
 * Problem Statement:
 *   Demonstrate TreeMap: natural ordering, custom Comparator,
 *   navigation methods (floorKey, ceilingKey, subMap, headMap, tailMap).
 *
 * Sample Output:
 *   TreeMap (natural): {Alice=90, Bob=85, Charlie=92}
 *   firstKey: Alice | lastKey: Eve
 *
 * Time Complexity:  get/put/remove O(log n)
 * Space Complexity: O(n)
 */
import java.util.*;

public class TreeMapDemo {

    public static void main(String[] args) {

        System.out.println("========== TreeMap — Natural (Alphabetical) Order ==========");
        TreeMap<String, Integer> phoneBook = new TreeMap<>();
        phoneBook.put("Charlie", 9876543210L > 0 ? 9876 : 0);
        phoneBook.put("Alice",   1234);
        phoneBook.put("Eve",     5555);
        phoneBook.put("Bob",     2222);
        phoneBook.put("Diana",   3333);
        System.out.println("PhoneBook    : " + phoneBook);
        System.out.println("firstKey()   : " + phoneBook.firstKey());
        System.out.println("lastKey()    : " + phoneBook.lastKey());
        System.out.println("firstEntry() : " + phoneBook.firstEntry());
        System.out.println("lastEntry()  : " + phoneBook.lastEntry());

        System.out.println("\n--- Navigation ---");
        System.out.println("floorKey('Bob')  : " + phoneBook.floorKey("Bob"));   // <= Bob
        System.out.println("ceilingKey('Bob'): " + phoneBook.ceilingKey("Bob")); // >= Bob
        System.out.println("lowerKey('Bob')  : " + phoneBook.lowerKey("Bob"));   // < Bob
        System.out.println("higherKey('Bob') : " + phoneBook.higherKey("Bob"));  // > Bob

        System.out.println("\n--- Sub-maps ---");
        System.out.println("headMap(<Diana)  : " + phoneBook.headMap("Diana"));         // < Diana
        System.out.println("tailMap(>=Diana) : " + phoneBook.tailMap("Diana"));         // >= Diana
        System.out.println("subMap(Bob,Eve)  : " + phoneBook.subMap("Bob", "Eve"));     // [Bob, Eve)

        System.out.println("\n========== TreeMap — Reverse (Descending) Order ==========");
        TreeMap<Integer, String> descMap = new TreeMap<>(Comparator.reverseOrder());
        descMap.put(50, "Fifty");
        descMap.put(10, "Ten");
        descMap.put(30, "Thirty");
        descMap.put(20, "Twenty");
        descMap.put(40, "Forty");
        System.out.println("Descending : " + descMap);
        System.out.println("pollFirstEntry(): " + descMap.pollFirstEntry()); // removes highest
        System.out.println("After poll : " + descMap);

        System.out.println("\n========== TreeMap — Custom Comparator (by string length) ==========");
        TreeMap<String, String> byLength = new TreeMap<>(
            Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        byLength.put("fig",         "small fruit");
        byLength.put("apple",       "red fruit");
        byLength.put("elderberry",  "small dark berry");
        byLength.put("kiwi",        "green fruit");
        byLength.put("banana",      "yellow fruit");
        byLength.forEach((k, v) -> System.out.printf("  %-12s -> %s%n", k, v));

        System.out.println("\n========== Practical: Grade Distribution ==========");
        // Count how many students fall in each score bracket using TreeMap
        int[] studentScores = {55, 72, 88, 91, 63, 78, 95, 84, 47, 69, 90, 82};
        TreeMap<String, Integer> gradeDist = new TreeMap<>();
        for (int score : studentScores) {
            String grade;
            if      (score >= 90) grade = "A (90-100)";
            else if (score >= 80) grade = "B (80-89)";
            else if (score >= 70) grade = "C (70-79)";
            else if (score >= 60) grade = "D (60-69)";
            else                  grade = "F (<60)";
            gradeDist.merge(grade, 1, Integer::sum);
        }
        System.out.println("Grade distribution (sorted):");
        gradeDist.forEach((grade, count) ->
            System.out.printf("  %-15s: %s%n", grade, "*".repeat(count) + " (" + count + ")"));

        System.out.println("\n========== Practical: Dictionary Lookup ==========");
        TreeMap<String, String> dict = new TreeMap<>();
        dict.put("algorithm",    "step-by-step procedure for calculations");
        dict.put("binary",       "relating to base-2 number system");
        dict.put("compiler",     "translates source code to machine code");
        dict.put("debugger",     "tool to find and fix bugs");
        dict.put("encapsulation","bundling data and methods together");

        System.out.println("All words: " + dict.keySet());
        System.out.println("Words starting with 'b'-'c': " + dict.subMap("b", "d").keySet());
        System.out.println("Look up 'compiler': " + dict.get("compiler"));
        System.out.println("Words up to 'compiler': " + dict.headMap("compiler", true).keySet());
    }
}
