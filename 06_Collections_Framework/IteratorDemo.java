/*
 * Program 82: Iterator Demo
 *
 * Problem Statement:
 *   Demonstrate Iterator, ListIterator, and Spliterator.
 *   Show ConcurrentModificationException and how to avoid it.
 *
 * Sample Output:
 *   Forward : [Alice, Bob, Charlie, Diana]
 *   Backward: [Diana, Charlie, Bob, Alice]
 *   Safe removal: [Bob, Diana]
 *
 * Time Complexity:  O(n) for iteration
 * Space Complexity: O(1) extra (iterator is lazy)
 */
import java.util.*;
import java.util.stream.StreamSupport;

public class IteratorDemo {

    public static void main(String[] args) {

        System.out.println("========== Basic Iterator ==========");
        List<String> names = new ArrayList<>(Arrays.asList("Alice","Bob","Charlie","Diana","Eve"));

        Iterator<String> it = names.iterator();
        System.out.print("hasNext/next iteration: ");
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        System.out.println("\n========== Safe Removal with Iterator ==========");
        List<String> fruits = new ArrayList<>(Arrays.asList("Apple","Banana","Cherry","Date","Elderberry"));
        System.out.println("Before removal: " + fruits);
        Iterator<String> iter = fruits.iterator();
        while (iter.hasNext()) {
            String f = iter.next();
            if (f.length() > 5) iter.remove(); // safe removal during iteration
        }
        System.out.println("After remove (len>5): " + fruits);

        System.out.println("\n========== ConcurrentModificationException (BAD way) ==========");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        try {
            for (Integer n : numbers) {
                if (n % 2 == 0) numbers.remove(n); // throws CME!
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException caught! Don't modify list during for-each.");
        }

        System.out.println("\n========== Correct Ways to Remove During Iteration ==========");
        // Way 1: Iterator.remove()
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        Iterator<Integer> it1 = list1.iterator();
        while (it1.hasNext()) {
            if (it1.next() % 2 == 0) it1.remove();
        }
        System.out.println("Way 1 (Iterator.remove): " + list1);

        // Way 2: removeIf (Java 8+)
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        list2.removeIf(n -> n % 2 == 0);
        System.out.println("Way 2 (removeIf lambda): " + list2);

        // Way 3: collect to new list
        List<Integer> list3 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        List<Integer> odds = new ArrayList<>();
        for (int n : list3) if (n % 2 != 0) odds.add(n);
        System.out.println("Way 3 (collect new list): " + odds);

        System.out.println("\n========== ListIterator (bidirectional) ==========");
        List<String> words = new ArrayList<>(Arrays.asList("Java","Python","C++","Kotlin","Swift"));

        // Forward traversal
        ListIterator<String> lit = words.listIterator();
        System.out.print("Forward : ");
        while (lit.hasNext()) System.out.print(lit.next() + " ");
        System.out.println();

        // Backward traversal (lit is now at end)
        System.out.print("Backward: ");
        while (lit.hasPrevious()) System.out.print(lit.previous() + " ");
        System.out.println();

        // Modify during iteration with ListIterator
        ListIterator<String> modIt = words.listIterator();
        while (modIt.hasNext()) {
            String w = modIt.next();
            modIt.set(w.toUpperCase()); // replace current element
        }
        System.out.println("After set(toUpper): " + words);

        // Add with ListIterator
        ListIterator<String> addIt = words.listIterator();
        addIt.next();
        addIt.add("RUST"); // adds after current position
        System.out.println("After add RUST at pos 1: " + words);

        System.out.println("\n========== Spliterator ==========");
        List<Integer> bigList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) bigList.add(i);

        Spliterator<Integer> spliterator = bigList.spliterator();
        System.out.println("estimateSize: " + spliterator.estimateSize());
        System.out.println("characteristics: " + spliterator.characteristics());

        // Split into two halves
        Spliterator<Integer> second = spliterator.trySplit();
        System.out.print("First half : ");
        spliterator.forEachRemaining(n -> System.out.print(n + " "));
        System.out.println();
        System.out.print("Second half: ");
        second.forEachRemaining(n -> System.out.print(n + " "));
        System.out.println();

        System.out.println("\n========== Custom Iterable Class ==========");
        NumberRange range = new NumberRange(1, 10, 2); // 1 to 10, step 2
        System.out.print("Range(1,10,2): ");
        for (int n : range) System.out.print(n + " ");
        System.out.println();
    }

    // Custom Iterable — iterates from start to end with a step
    static class NumberRange implements Iterable<Integer> {
        private final int start, end, step;

        NumberRange(int start, int end, int step) {
            this.start = start; this.end = end; this.step = step;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<>() {
                int current = start;
                @Override public boolean hasNext() { return current <= end; }
                @Override public Integer next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    int val = current;
                    current += step;
                    return val;
                }
            };
        }
    }
}
