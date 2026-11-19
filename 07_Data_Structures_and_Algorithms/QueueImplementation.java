/*
 * Program 88: Queue Implementation
 *
 * Problem Statement:
 *   Implement Queue data structure from scratch using:
 *   1. Array (Circular Queue to avoid wastage)
 *   2. Linked List
 *   Include: enqueue, dequeue, peek, isEmpty, isFull, size, display.
 *
 * Sample Input:  Enqueue: 10, 20, 30, 40, 50
 * Sample Output:
 *   Queue: [10, 20, 30, 40, 50]
 *   Dequeue: 10
 *   Queue: [20, 30, 40, 50]
 *   Peek: 20
 *
 * Time Complexity:  enqueue O(1), dequeue O(1), peek O(1)
 * Space Complexity: O(n)
 */
public class QueueImplementation {

    // ============================================================
    // 1. Circular Array Queue
    // ============================================================
    static class CircularArrayQueue {
        private int[] data;
        private int front, rear, size, capacity;

        CircularArrayQueue(int capacity) {
            this.capacity = capacity;
            data  = new int[capacity];
            front = 0;
            rear  = -1;
            size  = 0;
        }

        boolean isEmpty() { return size == 0; }
        boolean isFull()  { return size == capacity; }
        int size()        { return size; }

        void enqueue(int value) {
            if (isFull()) throw new RuntimeException("Queue is full");
            rear = (rear + 1) % capacity;
            data[rear] = value;
            size++;
        }

        int dequeue() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            int val = data[front];
            front = (front + 1) % capacity;
            size--;
            return val;
        }

        int peek() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            return data[front];
        }

        void display() {
            if (isEmpty()) { System.out.println("Queue: []"); return; }
            System.out.print("Queue (front->rear): [");
            for (int i = 0; i < size; i++) {
                System.out.print(data[(front + i) % capacity]);
                if (i < size - 1) System.out.print(", ");
            }
            System.out.println("]");
        }
    }

    // ============================================================
    // 2. Linked List Queue
    // ============================================================
    static class LinkedListQueue {
        private static class Node {
            int data;
            Node next;
            Node(int data) { this.data = data; this.next = null; }
        }

        private Node front, rear;
        private int size;

        LinkedListQueue() { front = rear = null; size = 0; }

        boolean isEmpty() { return front == null; }
        int size()        { return size; }

        void enqueue(int value) {
            Node newNode = new Node(value);
            if (rear != null) rear.next = newNode;
            rear = newNode;
            if (front == null) front = rear;
            size++;
        }

        int dequeue() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            int val = front.data;
            front = front.next;
            if (front == null) rear = null;
            size--;
            return val;
        }

        int peek() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            return front.data;
        }

        void display() {
            if (isEmpty()) { System.out.println("Queue: []"); return; }
            System.out.print("Queue (front->rear): [");
            Node curr = front;
            while (curr != null) {
                System.out.print(curr.data);
                if (curr.next != null) System.out.print(", ");
                curr = curr.next;
            }
            System.out.println("]");
        }
    }

    // ============================================================
    // MAIN
    // ============================================================
    public static void main(String[] args) {

        System.out.println("========== Circular Array Queue ==========");
        CircularArrayQueue cq = new CircularArrayQueue(5);

        cq.enqueue(10); cq.enqueue(20); cq.enqueue(30);
        cq.enqueue(40); cq.enqueue(50);
        cq.display();
        System.out.println("isFull  : " + cq.isFull());
        System.out.println("peek()  : " + cq.peek());
        System.out.println("dequeue : " + cq.dequeue());
        System.out.println("dequeue : " + cq.dequeue());
        cq.display();
        System.out.println("size()  : " + cq.size());

        // Circular wrap-around test
        cq.enqueue(60); cq.enqueue(70); // fills spots freed by dequeue
        cq.display();
        System.out.println("isFull  : " + cq.isFull());

        // Full queue — exception
        try { cq.enqueue(80); }
        catch (RuntimeException e) { System.out.println("Exception: " + e.getMessage()); }

        // Drain queue
        System.out.print("Draining: ");
        while (!cq.isEmpty()) System.out.print(cq.dequeue() + " ");
        System.out.println();

        // Empty dequeue — exception
        try { cq.dequeue(); }
        catch (RuntimeException e) { System.out.println("Exception: " + e.getMessage()); }

        System.out.println("\n========== Linked List Queue ==========");
        LinkedListQueue lq = new LinkedListQueue();

        for (int v : new int[]{100, 200, 300, 400, 500}) lq.enqueue(v);
        lq.display();
        System.out.println("peek()  : " + lq.peek());
        System.out.println("dequeue : " + lq.dequeue());
        System.out.println("dequeue : " + lq.dequeue());
        lq.display();
        lq.enqueue(600);
        lq.display();
        System.out.println("size()  : " + lq.size());

        System.out.println("\n========== Application: BFS using Queue ==========");
        // BFS level-order traversal of a simple tree
        // Tree:         1
        //             /   \
        //            2     3
        //           / \   / \
        //          4   5 6   7
        int[] tree = {-1, 1, 2, 3, 4, 5, 6, 7}; // 1-indexed

        System.out.print("BFS order: ");
        LinkedListQueue bfsQ = new LinkedListQueue();
        bfsQ.enqueue(1); // root index
        while (!bfsQ.isEmpty()) {
            int idx = bfsQ.dequeue();
            System.out.print(tree[idx] + " ");
            int left  = 2 * idx;
            int right = 2 * idx + 1;
            if (left  < tree.length) bfsQ.enqueue(left);
            if (right < tree.length) bfsQ.enqueue(right);
        }
        System.out.println();

        System.out.println("\n========== Application: Hot Potato Simulation ==========");
        // Classic hot-potato / Josephus-like game using queue
        LinkedListQueue game = new LinkedListQueue();
        String[] players = {"Alice","Bob","Charlie","Diana","Eve"};
        for (String p : players) game.enqueue(p.hashCode() % 1000); // enqueue player codes

        int passes = 3; // pass potato 3 times before eliminating
        System.out.println("Passes per round: " + passes);

        // Re-enqueue using just integers for simplicity
        CircularArrayQueue hotPotato = new CircularArrayQueue(players.length);
        for (int i = 0; i < players.length; i++) hotPotato.enqueue(i);

        int round = 1;
        while (hotPotato.size() > 1) {
            for (int i = 0; i < passes; i++) hotPotato.enqueue(hotPotato.dequeue());
            int eliminated = hotPotato.dequeue();
            System.out.println("Round " + round++ + ": " + players[eliminated] + " is eliminated");
        }
        System.out.println("Winner: " + players[hotPotato.dequeue()]);
    }
}
