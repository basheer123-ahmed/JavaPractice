/*
 * Program 83: Collections Utility Class Demo
 *
 * Problem Statement:
 *   Demonstrate the java.util.Collections utility class methods:
 *   sort, reverse, shuffle, min, max, frequency, binarySearch,
 *   unmodifiableList, synchronizedList, singletonList, nCopies, disjoint.
 *
 * Sample Output:
 *   Sorted    : [1, 2, 3, 4, 5]
 *   Reversed  : [5, 4, 3, 2, 1]
 *   Min=1, Max=5
 *
 * Time Complexity:  sort O(n log n), binarySearch O(log n), others O(n) or O(1)
 * Space Complexity: O(1) extra for most operations
 */
import java.util.*;

public class CollectionsUtility {

    public static void main(String[] args) {

        System.out.println("========== sort and reverse ==========");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6));
        System.out.println("Original  : " + numbers);
        Collections.sort(numbers);
        System.out.println("Sorted    : " + numbers);
        Collections.reverse(numbers);
        System.out.println("Reversed  : " + numbers);

        // Sort with custom Comparator
        List<String> words = new ArrayList<>(Arrays.asList("banana","apple","cherry","date","elderberry","fig"));
        Collections.sort(words, Comparator.comparingInt(String::length));
        System.out.println("By length : " + words);
        Collections.sort(words, Comparator.comparingInt(String::length).reversed());
        System.out.println("By len desc:" + words);

        System.out.println("\n========== shuffle ==========");
        List<Integer> deck = new ArrayList<>();
        for (int i = 1; i <= 10; i++) deck.add(i);
        System.out.println("Before shuffle: " + deck);
        Collections.shuffle(deck, new Random(42));
        System.out.println("After shuffle : " + deck);

        System.out.println("\n========== min and max ==========");
        List<Integer> vals = Arrays.asList(15, 3, 9, 1, 24, 7, 11);
        System.out.println("List  : " + vals);
        System.out.println("Min   : " + Collections.min(vals));
        System.out.println("Max   : " + Collections.max(vals));

        List<String> strs = Arrays.asList("mango","apple","zebra","cat");
        System.out.println("Min str: " + Collections.min(strs));
        System.out.println("Max str: " + Collections.max(strs));

        System.out.println("\n========== frequency ==========");
        List<String> fruits = Arrays.asList("apple","banana","apple","cherry","apple","banana");
        System.out.println("List     : " + fruits);
        System.out.println("freq apple : " + Collections.frequency(fruits, "apple"));
        System.out.println("freq banana: " + Collections.frequency(fruits, "banana"));
        System.out.println("freq grape : " + Collections.frequency(fruits, "grape"));

        System.out.println("\n========== binarySearch ==========");
        List<Integer> sorted = new ArrayList<>(Arrays.asList(1,3,5,7,9,11,13,15,17,19));
        System.out.println("Sorted list: " + sorted);
        System.out.println("binarySearch(7) : " + Collections.binarySearch(sorted, 7));  // index 3
        System.out.println("binarySearch(13): " + Collections.binarySearch(sorted, 13)); // index 6
        System.out.println("binarySearch(6) : " + Collections.binarySearch(sorted, 6));  // negative = not found

        System.out.println("\n========== fill and nCopies ==========");
        List<String> filledList = new ArrayList<>(Arrays.asList("a","b","c","d","e"));
        Collections.fill(filledList, "X");
        System.out.println("After fill('X'): " + filledList);

        List<String> copies = Collections.nCopies(5, "Java");
        System.out.println("nCopies(5,'Java'): " + copies);

        System.out.println("\n========== swap and rotate ==========");
        List<Integer> swapList = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        Collections.swap(swapList, 0, 4);
        System.out.println("After swap(0,4): " + swapList);

        List<Integer> rotList = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        Collections.rotate(rotList, 2); // rotate right by 2
        System.out.println("After rotate(2) : " + rotList); // [4,5,1,2,3]
        Collections.rotate(rotList, -1); // rotate left by 1
        System.out.println("After rotate(-1): " + rotList);

        System.out.println("\n========== disjoint ==========");
        List<Integer> setA = Arrays.asList(1, 2, 3, 4);
        List<Integer> setB = Arrays.asList(5, 6, 7, 8);
        List<Integer> setC = Arrays.asList(3, 6, 9, 12);
        System.out.println("A and B disjoint? " + Collections.disjoint(setA, setB)); // true
        System.out.println("A and C disjoint? " + Collections.disjoint(setA, setC)); // false

        System.out.println("\n========== Unmodifiable Collections ==========");
        List<String> mutableList = new ArrayList<>(Arrays.asList("a","b","c"));
        List<String> immutable = Collections.unmodifiableList(mutableList);
        System.out.println("Immutable list: " + immutable);
        try {
            immutable.add("d");
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiable list: " + e.getClass().getSimpleName());
        }

        System.out.println("\n========== Singleton Collections ==========");
        List<String> single = Collections.singletonList("OnlyOne");
        Set<Integer> singleSet = Collections.singleton(42);
        Map<String,Integer> singleMap = Collections.singletonMap("key", 100);
        System.out.println("singletonList: " + single);
        System.out.println("singleton set: " + singleSet);
        System.out.println("singletonMap : " + singleMap);

        System.out.println("\n========== synchronizedList ==========");
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
        // Thread-safe additions
        for (int i = 1; i <= 5; i++) syncList.add(i);
        System.out.println("Synchronized list: " + syncList);

        System.out.println("\n========== Practical: Sort Students ==========");
        List<String[]> students = new ArrayList<>();
        students.add(new String[]{"Alice", "92"});
        students.add(new String[]{"Bob",   "85"});
        students.add(new String[]{"Charlie","92"});
        students.add(new String[]{"Diana", "78"});

        // Sort by score descending, then by name ascending
        students.sort(Comparator.<String[], Integer>comparing(s -> -Integer.parseInt(s[1]))
                                .thenComparing(s -> s[0]));
        System.out.println("Students sorted by score desc, name asc:");
        students.forEach(s -> System.out.printf("  %-10s %s%n", s[0], s[1]));
    }
}
