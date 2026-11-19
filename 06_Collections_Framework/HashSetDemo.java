/*
 * Program 78: HashSet Demo
 *
 * Problem Statement:
 *   Demonstrate HashSet, LinkedHashSet, and TreeSet.
 *   Perform set operations: union, intersection, difference.
 *
 * Sample Output:
 *   HashSet      : [1, 2, 3, 4, 5]  (unordered)
 *   LinkedHashSet: [3, 1, 4, 1, 5]  (insertion order, no dups)
 *   TreeSet      : [1, 2, 3, 4, 5]  (sorted)
 *
 * Time Complexity:  HashSet add/remove/contains O(1) avg
 *                   TreeSet add/remove/contains O(log n)
 * Space Complexity: O(n)
 */
import java.util.*;

public class HashSetDemo {

    public static void main(String[] args) {

        System.out.println("========== HashSet (no order guaranteed) ==========");
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(5); hashSet.add(3); hashSet.add(1); hashSet.add(4); hashSet.add(2);
        hashSet.add(3); // duplicate — ignored
        System.out.println("HashSet       : " + hashSet);
        System.out.println("contains(3)   : " + hashSet.contains(3));
        System.out.println("contains(10)  : " + hashSet.contains(10));
        System.out.println("size()        : " + hashSet.size());
        hashSet.remove(3);
        System.out.println("After remove 3: " + hashSet);

        System.out.println("\n========== LinkedHashSet (insertion order) ==========");
        Set<String> linked = new LinkedHashSet<>();
        linked.add("Banana"); linked.add("Apple"); linked.add("Cherry");
        linked.add("Apple"); // duplicate — ignored
        linked.add("Date");
        System.out.println("LinkedHashSet : " + linked);
        System.out.println("Iteration order preserved: insertion order");

        System.out.println("\n========== TreeSet (sorted order) ==========");
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.addAll(Arrays.asList(8, 3, 1, 7, 5, 2, 9, 4, 6));
        System.out.println("TreeSet (asc) : " + treeSet);

        TreeSet<Integer> descTree = new TreeSet<>(Comparator.reverseOrder());
        descTree.addAll(Arrays.asList(8, 3, 1, 7, 5, 2, 9, 4, 6));
        System.out.println("TreeSet (desc): " + descTree);

        // TreeSet-specific navigation methods
        TreeSet<Integer> nav = new TreeSet<>(Arrays.asList(10, 20, 30, 40, 50));
        System.out.println("\nNavigation on " + nav);
        System.out.println("first()       : " + nav.first());
        System.out.println("last()        : " + nav.last());
        System.out.println("floor(25)     : " + nav.floor(25));   // largest <= 25
        System.out.println("ceiling(25)   : " + nav.ceiling(25)); // smallest >= 25
        System.out.println("lower(30)     : " + nav.lower(30));   // strictly < 30
        System.out.println("higher(30)    : " + nav.higher(30));  // strictly > 30
        System.out.println("headSet(30)   : " + nav.headSet(30)); // < 30
        System.out.println("tailSet(30)   : " + nav.tailSet(30)); // >= 30
        System.out.println("subSet(20,40) : " + nav.subSet(20, 40)); // [20,40)

        System.out.println("\n========== Set Operations ==========");
        Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        System.out.println("Set A: " + new TreeSet<>(setA));
        System.out.println("Set B: " + new TreeSet<>(setB));

        // Union
        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("Union (A ∪ B)         : " + new TreeSet<>(union));

        // Intersection
        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("Intersection (A ∩ B)  : " + new TreeSet<>(intersection));

        // Difference A - B
        Set<Integer> diffAB = new HashSet<>(setA);
        diffAB.removeAll(setB);
        System.out.println("Difference (A - B)    : " + new TreeSet<>(diffAB));

        // Difference B - A
        Set<Integer> diffBA = new HashSet<>(setB);
        diffBA.removeAll(setA);
        System.out.println("Difference (B - A)    : " + new TreeSet<>(diffBA));

        // Symmetric difference (A ∪ B) - (A ∩ B)
        Set<Integer> symDiff = new HashSet<>(union);
        symDiff.removeAll(intersection);
        System.out.println("Symmetric Diff (A △ B): " + new TreeSet<>(symDiff));

        // isSubset?
        Set<Integer> sub = new HashSet<>(Arrays.asList(1, 2, 3));
        System.out.println("\n{1,2,3} subset of A? " + setA.containsAll(sub));

        System.out.println("\n========== Remove Duplicates from List ==========");
        List<String> withDups = Arrays.asList("apple","banana","apple","cherry","banana","date");
        System.out.println("Original    : " + withDups);
        List<String> noDups = new ArrayList<>(new LinkedHashSet<>(withDups));
        System.out.println("No duplicates (order preserved): " + noDups);
    }
}
