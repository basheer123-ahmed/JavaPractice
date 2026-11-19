/*
 * Program 76: ArrayList Demonstration
 * 
 * Problem Statement:
 *   Demonstrate ArrayList operations: add, remove, get, set, contains, indexOf,
 *   sort, and multiple iteration techniques (for, for-each, iterator, listIterator,
 *   stream). Show dynamic resizing behavior and common usage patterns.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Basic Operations ===
 *   After adds: [Apple, Banana, Cherry, Date, Elderberry]
 *   get(1): Banana
 *   set(1, Blueberry): [Apple, Blueberry, Cherry, Date, Elderberry]
 *   contains("Cherry"): true
 *   indexOf("Date"): 3
 *   remove("Cherry"): [Apple, Blueberry, Date, Elderberry]
 *   remove(0): [Blueberry, Date, Elderberry]
 *   ...
 * 
 * Explanation:
 *   ArrayList is a resizable array implementation of the List interface. It provides
 *   O(1) random access by index, O(1) amortized appends, but O(n) insertions and
 *   removals at arbitrary positions because elements must be shifted. Internally,
 *   it maintains an Object[] that grows by ~50% when capacity is exceeded. This
 *   program covers CRUD operations, searching, sorting, subList views, and all
 *   major iteration mechanisms including Java 8+ Streams.
 * 
 * Time Complexity:
 *   - add (end): O(1) amortized
 *   - add (index): O(n)
 *   - get / set: O(1)
 *   - remove / contains / indexOf: O(n)
 *   - sort: O(n log n)
 * Space Complexity: O(n)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class ArrayListDemo {

    public static void main(String[] args) {
        // =====================================================================
        // 1. CREATION
        // =====================================================================
        System.out.println("=== 1. Creation ===");

        // Default initial capacity (10)
        ArrayList<String> fruits = new ArrayList<>();

        // With specified initial capacity (avoids early resizing)
        ArrayList<String> preAllocated = new ArrayList<>(100);
        System.out.println("Pre-allocated list size (not capacity): " + preAllocated.size());

        // From another collection
        List<String> seed = Arrays.asList("X", "Y", "Z");
        ArrayList<String> fromCollection = new ArrayList<>(seed);
        System.out.println("From collection: " + fromCollection);

        // Using List.of (immutable) then copying
        ArrayList<Integer> numbers = new ArrayList<>(List.of(10, 20, 30, 40, 50));
        System.out.println("From List.of: " + numbers);

        // =====================================================================
        // 2. BASIC OPERATIONS: add, get, set, remove
        // =====================================================================
        System.out.println("\n=== 2. Basic Operations ===");

        fruits.add("Apple");        // append
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        fruits.add("Elderberry");
        System.out.println("After adds: " + fruits);

        // get - O(1)
        System.out.println("get(1): " + fruits.get(1));

        // set - O(1), returns old value
        String old = fruits.set(1, "Blueberry");
        System.out.println("set(1, \"Blueberry\") replaced: " + old);
        System.out.println("After set: " + fruits);

        // add at index - O(n) shifts elements right
        fruits.add(2, "Cranberry");
        System.out.println("After add(2, \"Cranberry\"): " + fruits);

        // addAll
        fruits.addAll(Arrays.asList("Fig", "Grape"));
        System.out.println("After addAll: " + fruits);

        // remove by object - O(n), removes first occurrence
        boolean removed = fruits.remove("Cherry");
        System.out.println("remove(\"Cherry\"): " + removed + " -> " + fruits);

        // remove by index - O(n), returns removed element
        String removedElement = fruits.remove(0);
        System.out.println("remove(0) returned: " + removedElement + " -> " + fruits);

        // =====================================================================
        // 3. SEARCH OPERATIONS: contains, indexOf, lastIndexOf
        // =====================================================================
        System.out.println("\n=== 3. Search Operations ===");

        fruits.add("Date"); // add duplicate
        System.out.println("List: " + fruits);
        System.out.println("contains(\"Date\"): " + fruits.contains("Date"));
        System.out.println("contains(\"Mango\"): " + fruits.contains("Mango"));
        System.out.println("indexOf(\"Date\"): " + fruits.indexOf("Date"));
        System.out.println("lastIndexOf(\"Date\"): " + fruits.lastIndexOf("Date"));
        System.out.println("indexOf(\"Mango\"): " + fruits.indexOf("Mango")); // -1
        System.out.println("isEmpty(): " + fruits.isEmpty());
        System.out.println("size(): " + fruits.size());

        // =====================================================================
        // 4. SORTING
        // =====================================================================
        System.out.println("\n=== 4. Sorting ===");

        ArrayList<String> sortable = new ArrayList<>(Arrays.asList("Banana", "Apple", "Date", "Cherry"));

        // Natural order (Comparable)
        Collections.sort(sortable);
        System.out.println("Natural sort: " + sortable);

        // Reverse order
        sortable.sort(Comparator.reverseOrder());
        System.out.println("Reverse sort: " + sortable);

        // Sort by string length
        sortable.sort(Comparator.comparingInt(String::length));
        System.out.println("Sort by length: " + sortable);

        // Sort by length descending, then alphabetically
        sortable.sort(Comparator.comparingInt(String::length).reversed()
                                .thenComparing(Comparator.naturalOrder()));
        System.out.println("Sort by length desc, then alpha: " + sortable);

        // =====================================================================
        // 5. ITERATION TECHNIQUES
        // =====================================================================
        System.out.println("\n=== 5. Iteration Techniques ===");
        ArrayList<String> items = new ArrayList<>(Arrays.asList("Red", "Green", "Blue", "Yellow"));

        // 5a. Classic for loop (index-based)
        System.out.print("Classic for: ");
        for (int i = 0; i < items.size(); i++) {
            System.out.print(items.get(i) + " ");
        }
        System.out.println();

        // 5b. Enhanced for-each loop
        System.out.print("For-each: ");
        for (String item : items) {
            System.out.print(item + " ");
        }
        System.out.println();

        // 5c. Iterator
        System.out.print("Iterator: ");
        Iterator<String> it = items.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        // 5d. ListIterator (bidirectional, can modify)
        System.out.print("ListIterator (reverse): ");
        ListIterator<String> lit = items.listIterator(items.size()); // start at end
        while (lit.hasPrevious()) {
            System.out.print(lit.previous() + " ");
        }
        System.out.println();

        // ListIterator - modify during traversal
        ListIterator<String> modIter = items.listIterator();
        while (modIter.hasNext()) {
            String val = modIter.next();
            if (val.equals("Green")) {
                modIter.set("Lime"); // replace current
            }
        }
        System.out.println("After ListIterator set: " + items);

        // 5e. forEach method (Java 8+)
        System.out.print("forEach: ");
        items.forEach(item -> System.out.print(item + " "));
        System.out.println();

        // 5f. Stream API
        System.out.print("Stream: ");
        items.stream().forEach(item -> System.out.print(item + " "));
        System.out.println();

        // Stream with operations
        String joined = items.stream()
                             .filter(s -> s.length() > 3)
                             .map(String::toUpperCase)
                             .collect(Collectors.joining(", "));
        System.out.println("Stream filtered & mapped: " + joined);

        // =====================================================================
        // 6. DYNAMIC RESIZING DEMONSTRATION
        // =====================================================================
        System.out.println("\n=== 6. Dynamic Resizing ===");
        System.out.println("ArrayList internally uses an Object[] array.");
        System.out.println("Default capacity = 10. Grows by ~50% when full.");
        System.out.println("Capacity sequence: 10 -> 15 -> 22 -> 33 -> 49 -> ...\n");

        ArrayList<Integer> dynamicList = new ArrayList<>(); // capacity 10
        System.out.println("Adding 20 elements to demonstrate auto-resizing:");
        for (int i = 1; i <= 20; i++) {
            dynamicList.add(i);
            System.out.println("  Added " + i + ", size = " + dynamicList.size());
        }
        System.out.println("Final list: " + dynamicList);

        // trimToSize and ensureCapacity
        dynamicList.trimToSize(); // shrinks internal array to current size
        System.out.println("After trimToSize(), internal array matches size.");
        dynamicList.ensureCapacity(100); // pre-allocate
        System.out.println("After ensureCapacity(100), ready for growth.");

        // =====================================================================
        // 7. SUBLIST, CLEAR, TOARRAY
        // =====================================================================
        System.out.println("\n=== 7. SubList, Clear, ToArray ===");

        ArrayList<String> colors = new ArrayList<>(
            Arrays.asList("Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet")
        );

        // subList returns a VIEW (changes reflect in original)
        List<String> warm = colors.subList(0, 3);
        System.out.println("SubList (0,3): " + warm);
        warm.set(0, "Crimson");
        System.out.println("After modifying subList, original: " + colors);

        // toArray
        Object[] arr1 = colors.toArray();
        System.out.println("toArray(): " + Arrays.toString(arr1));

        String[] arr2 = colors.toArray(new String[0]);
        System.out.println("toArray(String[]): " + Arrays.toString(arr2));

        // clear
        ArrayList<String> temp = new ArrayList<>(colors);
        temp.clear();
        System.out.println("After clear(): " + temp + ", size = " + temp.size());

        // =====================================================================
        // 8. COMMON PATTERNS
        // =====================================================================
        System.out.println("\n=== 8. Common Patterns ===");

        // Remove duplicates preserving order
        ArrayList<Integer> withDups = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3));
        ArrayList<Integer> unique = withDups.stream()
                                            .distinct()
                                            .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("Original: " + withDups);
        System.out.println("Deduplicated: " + unique);

        // Safe removal during iteration
        ArrayList<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        nums.removeIf(n -> n % 2 == 0); // remove evens
        System.out.println("After removeIf (evens removed): " + nums);

        // replaceAll
        ArrayList<String> words = new ArrayList<>(Arrays.asList("hello", "world", "java"));
        words.replaceAll(String::toUpperCase);
        System.out.println("After replaceAll toUpperCase: " + words);

        // Copying a list (3 ways)
        ArrayList<String> copy1 = new ArrayList<>(words);                    // constructor
        ArrayList<String> copy2 = new ArrayList<>();
        Collections.copy(                                                     // Collections.copy
            (copy2 = new ArrayList<>(Collections.nCopies(words.size(), null))),
            words
        );
        @SuppressWarnings("unchecked")
        ArrayList<String> copy3 = (ArrayList<String>) words.clone();          // clone
        System.out.println("Copy1: " + copy1);
        System.out.println("Copy2: " + copy2);
        System.out.println("Copy3: " + copy3);

        // =====================================================================
        // 9. ARRAYLIST vs ARRAYS
        // =====================================================================
        System.out.println("\n=== 9. ArrayList vs Array ===");
        System.out.println("+---------------------+-----------------+------------------+");
        System.out.println("| Feature             | Array           | ArrayList        |");
        System.out.println("+---------------------+-----------------+------------------+");
        System.out.println("| Size                | Fixed           | Dynamic          |");
        System.out.println("| Primitives          | Yes             | No (use wrapper) |");
        System.out.println("| Type safety         | Yes             | Yes (generics)   |");
        System.out.println("| Performance         | Faster          | Slight overhead  |");
        System.out.println("| Utility methods     | Arrays class    | Rich API         |");
        System.out.println("| Multi-dimensional   | Yes             | List<List<T>>    |");
        System.out.println("+---------------------+-----------------+------------------+");

        System.out.println("\n=== Program Complete ===");
    }
}
