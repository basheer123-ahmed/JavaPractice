/*
 * Program 79: HashMap Demo
 *
 * Problem Statement:
 *   Demonstrate Java HashMap operations: put, get, remove,
 *   containsKey/Value, iteration, merge, compute, getOrDefault.
 *
 * Sample Output:
 *   {Alice=90, Bob=85, Charlie=92}
 *   Get Alice: 90
 *   Word frequencies: {java=3, is=1, fun=2}
 *
 * Time Complexity:  get/put/remove O(1) average, O(n) worst case
 * Space Complexity: O(n)
 */
import java.util.*;
import java.util.stream.Collectors;

public class HashMapDemo {

    public static void main(String[] args) {

        System.out.println("========== Basic HashMap Operations ==========");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice",   90);
        scores.put("Bob",     85);
        scores.put("Charlie", 92);
        scores.put("Diana",   88);
        scores.put("Eve",     95);
        System.out.println("Scores       : " + scores);
        System.out.println("Get Alice    : " + scores.get("Alice"));
        System.out.println("Get Frank    : " + scores.get("Frank")); // null
        System.out.println("getOrDefault : " + scores.getOrDefault("Frank", 0));
        System.out.println("containsKey  : " + scores.containsKey("Bob"));
        System.out.println("containsValue: " + scores.containsValue(92));
        System.out.println("size()       : " + scores.size());

        // Update
        scores.put("Bob", 90);    // overwrite
        scores.putIfAbsent("Frank", 78); // only if absent
        System.out.println("After updates: " + scores);

        // Remove
        scores.remove("Eve");
        System.out.println("After remove Eve: " + scores);

        System.out.println("\n========== Iterating HashMap ==========");
        // 1. entrySet
        System.out.println("--- entrySet ---");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.printf("  %-10s -> %d%n", entry.getKey(), entry.getValue());
        }
        // 2. keySet
        System.out.println("--- keySet ---");
        for (String key : scores.keySet()) System.out.print(key + " ");
        System.out.println();
        // 3. values
        System.out.println("--- values ---");
        for (int val : scores.values()) System.out.print(val + " ");
        System.out.println();
        // 4. forEach (lambda)
        System.out.println("--- forEach ---");
        scores.forEach((k, v) -> System.out.println("  " + k + " scored " + v));

        System.out.println("\n========== Word Frequency Counter ==========");
        String text = "java is great java is fun programming in java is fun";
        Map<String, Integer> freq = new HashMap<>();
        for (String word : text.split(" ")) {
            freq.merge(word, 1, Integer::sum);  // merge: insert or add
        }
        // Sort by frequency descending
        freq.entrySet().stream()
            .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
            .forEach(e -> System.out.printf("  %-15s: %d%n", e.getKey(), e.getValue()));

        System.out.println("\n========== compute / computeIfAbsent / computeIfPresent ==========");
        Map<String, List<String>> groups = new HashMap<>();
        // computeIfAbsent: create list if key absent, then add
        groups.computeIfAbsent("fruits",  k -> new ArrayList<>()).add("Apple");
        groups.computeIfAbsent("fruits",  k -> new ArrayList<>()).add("Banana");
        groups.computeIfAbsent("veggies", k -> new ArrayList<>()).add("Carrot");
        System.out.println("Groups: " + groups);

        Map<String, Integer> counter = new HashMap<>();
        String[] words = {"cat","dog","cat","bird","dog","cat"};
        for (String w : words) counter.compute(w, (k, v) -> v == null ? 1 : v + 1);
        System.out.println("Counter: " + counter);

        System.out.println("\n========== merge() ==========");
        Map<String, Integer> m1 = new HashMap<>(Map.of("a", 1, "b", 2));
        Map<String, Integer> m2 = Map.of("b", 3, "c", 4);
        m2.forEach((k, v) -> m1.merge(k, v, Integer::sum));
        System.out.println("Merged map: " + m1); // a=1, b=5, c=4

        System.out.println("\n========== Sort HashMap by Key / Value ==========");
        Map<String, Integer> unsorted = new HashMap<>(Map.of("Banana",3,"Apple",5,"Cherry",1,"Date",4));
        System.out.println("Sorted by key  : " + new TreeMap<>(unsorted));
        // Sort by value
        unsorted.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(e -> System.out.println("  " + e.getKey() + " = " + e.getValue()));

        System.out.println("\n========== Group Students by Grade ==========");
        Map<String, Integer> students = new LinkedHashMap<>();
        students.put("Alice",92); students.put("Bob",75); students.put("Charlie",85);
        students.put("Diana",91); students.put("Eve",60);  students.put("Frank",78);

        Map<String, List<String>> byGrade = students.entrySet().stream().collect(
            Collectors.groupingBy(
                e -> {
                    int s = e.getValue();
                    if (s >= 90) return "A";
                    else if (s >= 80) return "B";
                    else if (s >= 70) return "C";
                    else return "D";
                },
                Collectors.mapping(Map.Entry::getKey, Collectors.toList())
            )
        );
        byGrade.forEach((grade, list) -> System.out.println("Grade " + grade + ": " + list));
    }
}
