/*
 * Program 81: Queue and Deque Demo
 *
 * Problem Statement:
 *   Demonstrate PriorityQueue (min-heap and max-heap),
 *   and ArrayDeque used as both a stack and a queue.
 *
 * Sample Output:
 *   Min-heap poll order: 1 2 3 4 5
 *   Max-heap poll order: 5 4 3 2 1
 *
 * Time Complexity:  PriorityQueue offer/poll O(log n), peek O(1)
 *                   ArrayDeque push/pop/offer/poll O(1) amortized
 * Space Complexity: O(n)
 */
import java.util.*;

public class QueueAndDequeDemo {

    // Task class for priority queue demo
    static class Task implements Comparable<Task> {
        String name;
        int priority; // lower number = higher priority

        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(Task other) {
            return Integer.compare(this.priority, other.priority);
        }

        @Override
        public String toString() {
            return name + "(p=" + priority + ")";
        }
    }

    public static void main(String[] args) {

        System.out.println("========== PriorityQueue — Min-Heap (default) ==========");
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(5); minHeap.offer(1); minHeap.offer(3);
        minHeap.offer(2); minHeap.offer(4);
        System.out.println("MinHeap peek(): " + minHeap.peek());
        System.out.print("Poll order (ascending): ");
        while (!minHeap.isEmpty()) System.out.print(minHeap.poll() + " ");
        System.out.println();

        System.out.println("\n========== PriorityQueue — Max-Heap ==========");
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.offer(5); maxHeap.offer(1); maxHeap.offer(3);
        maxHeap.offer(2); maxHeap.offer(4);
        System.out.println("MaxHeap peek(): " + maxHeap.peek());
        System.out.print("Poll order (descending): ");
        while (!maxHeap.isEmpty()) System.out.print(maxHeap.poll() + " ");
        System.out.println();

        System.out.println("\n========== PriorityQueue — Custom Task Scheduler ==========");
        PriorityQueue<Task> taskQueue = new PriorityQueue<>();
        taskQueue.offer(new Task("SendEmail",    3));
        taskQueue.offer(new Task("FixCrashBug",  1));
        taskQueue.offer(new Task("WriteTests",   4));
        taskQueue.offer(new Task("CodeReview",   2));
        taskQueue.offer(new Task("UpdateDocs",   5));
        System.out.println("Processing tasks by priority:");
        while (!taskQueue.isEmpty()) {
            Task t = taskQueue.poll();
            System.out.printf("  Executing: %-15s (priority %d)%n", t.name, t.priority);
        }

        System.out.println("\n========== PriorityQueue — Top K Elements ==========");
        int[] nums = {7, 10, 4, 3, 20, 15, 8, 1, 6, 12};
        int k = 3;
        // Min-heap of size k to find top K largest elements
        PriorityQueue<Integer> kLargest = new PriorityQueue<>();
        for (int n : nums) {
            kLargest.offer(n);
            if (kLargest.size() > k) kLargest.poll();
        }
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Top " + k + " largest: " + new ArrayList<>(kLargest));

        System.out.println("\n========== ArrayDeque as Queue (FIFO) ==========");
        Deque<String> queue = new ArrayDeque<>();
        queue.offer("Customer-1");
        queue.offer("Customer-2");
        queue.offer("Customer-3");
        System.out.println("Queue     : " + queue);
        System.out.println("peek()    : " + queue.peek());
        System.out.println("poll()    : " + queue.poll());
        System.out.println("After poll: " + queue);

        System.out.println("\n========== ArrayDeque as Stack (LIFO) ==========");
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Page-1");
        stack.push("Page-2");
        stack.push("Page-3");
        System.out.println("Stack (top first): " + stack);
        System.out.println("peek()           : " + stack.peek());
        System.out.println("pop()            : " + stack.pop());
        System.out.println("After pop        : " + stack);

        System.out.println("\n========== ArrayDeque as Double-Ended Queue ==========");
        Deque<Integer> deque = new ArrayDeque<>();
        deque.offerFirst(2); deque.offerFirst(1);
        deque.offerLast(3);  deque.offerLast(4);
        System.out.println("Deque          : " + deque);
        System.out.println("peekFirst()    : " + deque.peekFirst());
        System.out.println("peekLast()     : " + deque.peekLast());
        System.out.println("pollFirst()    : " + deque.pollFirst());
        System.out.println("pollLast()     : " + deque.pollLast());
        System.out.println("After both poll: " + deque);

        System.out.println("\n========== Sliding Window Maximum using Deque ==========");
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int windowSize = 3;
        System.out.println("Array : " + Arrays.toString(arr) + "  window=" + windowSize);
        int[] result = slidingWindowMax(arr, windowSize);
        System.out.println("Max of each window: " + Arrays.toString(result));
    }

    static int[] slidingWindowMax(int[] arr, int k) {
        int n = arr.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; i++) {
            // Remove elements outside the window
            while (!dq.isEmpty() && dq.peekFirst() < i - k + 1) dq.pollFirst();
            // Remove elements smaller than current from the back
            while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) result[i - k + 1] = arr[dq.peekFirst()];
        }
        return result;
    }
}
