/*
 * Program 77: LinkedList Demo
 *
 * Problem Statement:
 *   Demonstrate Java's LinkedList used both as a List and as a Deque.
 *   Show all key operations and compare with ArrayList.
 *
 * Sample Output:
 *   LinkedList: [Alice, Bob, Charlie, Diana]
 *   First: Alice  |  Last: Diana
 *   After poll: [Bob, Charlie, Diana]
 *
 * Time Complexity:  Add/remove at ends O(1), access by index O(n)
 * Space Complexity: O(n)
 */
import java.util.*;

public class LinkedListDemo {

    public static void main(String[] args) {

        System.out.println("========== LinkedList as List ==========");
        LinkedList<String> names = new LinkedList<>();

        // Adding elements
        names.add("Bob");
        names.add("Charlie");
        names.addFirst("Alice");   // adds at front
        names.addLast("Diana");    // adds at back
        names.add(2, "Eve");       // insert at index 2
        System.out.println("After additions: " + names);

        // Accessing elements
        System.out.println("First element  : " + names.getFirst());
        System.out.println("Last element   : " + names.getLast());
        System.out.println("Element at [2] : " + names.get(2));

        // Updating
        names.set(2, "Emma");
        System.out.println("After set[2]   : " + names);

        // Removing
        names.removeFirst();
        System.out.println("After removeFirst: " + names);
        names.removeLast();
        System.out.println("After removeLast : " + names);
        names.remove("Emma");
        System.out.println("After remove Emma: " + names);

        // Iteration
        System.out.println("\n--- Forward iteration ---");
        for (String name : names) System.out.print(name + " ");
        System.out.println();

        System.out.println("--- ListIterator (reverse) ---");
        ListIterator<String> lit = names.listIterator(names.size());
        while (lit.hasPrevious()) System.out.print(lit.previous() + " ");
        System.out.println();

        System.out.println("\n========== LinkedList as Deque (Double-Ended Queue) ==========");
        Deque<Integer> deque = new LinkedList<>();
        deque.offerFirst(2);
        deque.offerFirst(1);
        deque.offerLast(3);
        deque.offerLast(4);
        System.out.println("Deque          : " + deque);
        System.out.println("peekFirst()    : " + deque.peekFirst());
        System.out.println("peekLast()     : " + deque.peekLast());
        System.out.println("pollFirst()    : " + deque.pollFirst());
        System.out.println("pollLast()     : " + deque.pollLast());
        System.out.println("After polls    : " + deque);

        System.out.println("\n========== LinkedList as Stack ==========");
        Deque<String> stack = new LinkedList<>();
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        System.out.println("Stack (top first): " + stack);
        System.out.println("pop(): " + stack.pop());
        System.out.println("peek(): " + stack.peek());
        System.out.println("After pop  : " + stack);

        System.out.println("\n========== LinkedList as Queue ==========");
        Queue<String> queue = new LinkedList<>();
        queue.offer("Task-1");
        queue.offer("Task-2");
        queue.offer("Task-3");
        System.out.println("Queue: " + queue);
        System.out.println("poll(): " + queue.poll());
        System.out.println("peek(): " + queue.peek());
        System.out.println("After poll: " + queue);

        System.out.println("\n========== Performance Comparison ==========");
        int N = 100_000;

        // ArrayList — slow at front insertions
        List<Integer> arrayList = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) arrayList.add(0, i);
        long alTime = System.nanoTime() - start;

        // LinkedList — fast at front insertions
        LinkedList<Integer> linkedList = new LinkedList<>();
        start = System.nanoTime();
        for (int i = 0; i < N; i++) linkedList.addFirst(i);
        long llTime = System.nanoTime() - start;

        System.out.printf("ArrayList  addFirst x%d: %,d ns%n", N, alTime);
        System.out.printf("LinkedList addFirst x%d: %,d ns%n", N, llTime);
        System.out.println("LinkedList is faster for front insertions: " + (llTime < alTime));

        System.out.println("\n========== Useful LinkedList Methods ==========");
        LinkedList<Integer> ll = new LinkedList<>(Arrays.asList(10, 20, 30, 40, 50));
        System.out.println("List       : " + ll);
        System.out.println("contains(30): " + ll.contains(30));
        System.out.println("indexOf(40) : " + ll.indexOf(40));
        System.out.println("size()      : " + ll.size());
        System.out.println("toArray()   : " + Arrays.toString(ll.toArray()));
        Collections.sort(ll, Comparator.reverseOrder());
        System.out.println("Sorted desc : " + ll);
    }
}
