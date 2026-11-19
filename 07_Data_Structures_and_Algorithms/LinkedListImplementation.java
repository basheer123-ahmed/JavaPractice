/*
 * Program 86: Singly Linked List Implementation
 * 
 * Problem Statement:
 *   Implement a singly linked list from scratch with the following operations:
 *   - Insert at beginning, end, and at a given position
 *   - Delete from beginning, end, and by value
 *   - Search for an element
 *   - Reverse the linked list
 *   - Display all elements
 *   - Get the size of the list
 * 
 * Sample Input:  (Demonstrated programmatically)
 *   Insert 10 at beginning, Insert 20 at end, Insert 15 at position 1
 *   Delete from beginning, Delete by value 15, Reverse
 * 
 * Sample Output:
 *   After insertions: 10 -> 15 -> 20 -> NULL
 *   After deleting from beginning: 15 -> 20 -> NULL
 *   After deleting value 15: 20 -> NULL
 *   After more insertions and reverse: 50 -> 40 -> 30 -> 20 -> NULL
 * 
 * Explanation:
 *   A singly linked list is a linear data structure where each element (node)
 *   contains data and a reference (pointer) to the next node. Unlike arrays,
 *   linked lists do not store elements in contiguous memory, allowing efficient
 *   insertions and deletions without shifting elements. The trade-off is that
 *   random access is not possible — you must traverse from the head.
 * 
 * Time Complexity:
 *   - Insert at beginning: O(1)
 *   - Insert at end: O(n) [O(1) if tail pointer maintained]
 *   - Insert at position: O(n)
 *   - Delete from beginning: O(1)
 *   - Delete from end: O(n)
 *   - Delete by value: O(n)
 *   - Search: O(n)
 *   - Reverse: O(n)
 *   - Display: O(n)
 *   - Size: O(1) [maintained as a counter]
 * Space Complexity: O(n) for storing n elements
 */

public class LinkedListImplementation {

    // ========== Node Definition ==========
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // ========== Singly Linked List ==========
    static class SinglyLinkedList {
        private Node head;
        private int size;

        public SinglyLinkedList() {
            head = null;
            size = 0;
        }

        // --- Insert at the beginning ---
        public void insertAtBeginning(int data) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
            size++;
            System.out.println("  Inserted " + data + " at beginning.");
        }

        // --- Insert at the end ---
        public void insertAtEnd(int data) {
            Node newNode = new Node(data);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
            System.out.println("  Inserted " + data + " at end.");
        }

        // --- Insert at a specific position (0-indexed) ---
        public void insertAtPosition(int data, int position) {
            if (position < 0 || position > size) {
                System.out.println("  Error: Position " + position + " is out of bounds (size=" + size + ").");
                return;
            }
            if (position == 0) {
                insertAtBeginning(data);
                return;
            }
            Node newNode = new Node(data);
            Node current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
            size++;
            System.out.println("  Inserted " + data + " at position " + position + ".");
        }

        // --- Delete from the beginning ---
        public int deleteFromBeginning() {
            if (head == null) {
                System.out.println("  Error: List is empty, cannot delete.");
                return -1;
            }
            int data = head.data;
            head = head.next;
            size--;
            System.out.println("  Deleted " + data + " from beginning.");
            return data;
        }

        // --- Delete from the end ---
        public int deleteFromEnd() {
            if (head == null) {
                System.out.println("  Error: List is empty, cannot delete.");
                return -1;
            }
            if (head.next == null) {
                int data = head.data;
                head = null;
                size--;
                System.out.println("  Deleted " + data + " from end.");
                return data;
            }
            Node current = head;
            while (current.next.next != null) {
                current = current.next;
            }
            int data = current.next.data;
            current.next = null;
            size--;
            System.out.println("  Deleted " + data + " from end.");
            return data;
        }

        // --- Delete by value (first occurrence) ---
        public boolean deleteByValue(int value) {
            if (head == null) {
                System.out.println("  Error: List is empty, cannot delete.");
                return false;
            }
            if (head.data == value) {
                head = head.next;
                size--;
                System.out.println("  Deleted node with value " + value + ".");
                return true;
            }
            Node current = head;
            while (current.next != null) {
                if (current.next.data == value) {
                    current.next = current.next.next;
                    size--;
                    System.out.println("  Deleted node with value " + value + ".");
                    return true;
                }
                current = current.next;
            }
            System.out.println("  Value " + value + " not found in the list.");
            return false;
        }

        // --- Search for an element ---
        public int search(int value) {
            Node current = head;
            int index = 0;
            while (current != null) {
                if (current.data == value) {
                    return index;
                }
                current = current.next;
                index++;
            }
            return -1; // Not found
        }

        // --- Reverse the linked list (iterative) ---
        public void reverse() {
            Node prev = null;
            Node current = head;
            Node next;

            while (current != null) {
                next = current.next;   // Save next
                current.next = prev;   // Reverse the link
                prev = current;        // Move prev forward
                current = next;        // Move current forward
            }
            head = prev;
            System.out.println("  List reversed.");
        }

        // --- Reverse the linked list (recursive) ---
        public void reverseRecursive() {
            head = reverseRecursiveHelper(head);
            System.out.println("  List reversed (recursively).");
        }

        private Node reverseRecursiveHelper(Node node) {
            if (node == null || node.next == null) {
                return node;
            }
            Node newHead = reverseRecursiveHelper(node.next);
            node.next.next = node;
            node.next = null;
            return newHead;
        }

        // --- Display the list ---
        public void display() {
            if (head == null) {
                System.out.println("  List: [EMPTY]");
                return;
            }
            StringBuilder sb = new StringBuilder("  List: ");
            Node current = head;
            while (current != null) {
                sb.append(current.data);
                if (current.next != null) {
                    sb.append(" -> ");
                }
                current = current.next;
            }
            sb.append(" -> NULL");
            System.out.println(sb.toString());
        }

        // --- Get size ---
        public int size() {
            return size;
        }

        // --- Check if empty ---
        public boolean isEmpty() {
            return head == null;
        }

        // --- Get element at index ---
        public int get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
            }
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }

        // --- Detect cycle (Floyd's algorithm) ---
        public boolean hasCycle() {
            if (head == null) return false;
            Node slow = head;
            Node fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) return true;
            }
            return false;
        }

        // --- Find middle element ---
        public int findMiddle() {
            if (head == null) {
                throw new RuntimeException("List is empty");
            }
            Node slow = head;
            Node fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            return slow.data;
        }

        // --- Remove duplicates from sorted list ---
        public void removeDuplicatesSorted() {
            Node current = head;
            while (current != null && current.next != null) {
                if (current.data == current.next.data) {
                    current.next = current.next.next;
                    size--;
                } else {
                    current = current.next;
                }
            }
            System.out.println("  Duplicates removed (sorted list).");
        }

        // --- Get Nth node from end ---
        public int getNthFromEnd(int n) {
            if (n <= 0 || n > size) {
                throw new IllegalArgumentException("Invalid position: " + n);
            }
            Node first = head;
            Node second = head;
            // Move first pointer n steps ahead
            for (int i = 0; i < n; i++) {
                first = first.next;
            }
            // Move both until first reaches end
            while (first != null) {
                first = first.next;
                second = second.next;
            }
            return second.data;
        }
    }

    // ========== Main Method ==========
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     SINGLY LINKED LIST - COMPLETE IMPLEMENTATION    ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        SinglyLinkedList list = new SinglyLinkedList();

        // --- Test 1: Insert Operations ---
        System.out.println("\n--- Test 1: Insert Operations ---");
        list.insertAtBeginning(10);
        list.display();

        list.insertAtEnd(30);
        list.display();

        list.insertAtEnd(40);
        list.display();

        list.insertAtBeginning(5);
        list.display();

        list.insertAtPosition(20, 2);  // Insert 20 at index 2
        list.display();

        list.insertAtPosition(25, 3);  // Insert 25 at index 3
        list.display();

        System.out.println("  Size: " + list.size());

        // --- Test 2: Search Operations ---
        System.out.println("\n--- Test 2: Search Operations ---");
        int[] searchValues = {20, 40, 99};
        for (int val : searchValues) {
            int index = list.search(val);
            if (index != -1) {
                System.out.println("  Found " + val + " at index " + index);
            } else {
                System.out.println("  " + val + " not found in the list");
            }
        }

        // --- Test 3: Delete Operations ---
        System.out.println("\n--- Test 3: Delete Operations ---");
        System.out.println("  Current list:");
        list.display();

        list.deleteFromBeginning();
        list.display();

        list.deleteFromEnd();
        list.display();

        list.deleteByValue(25);
        list.display();

        list.deleteByValue(99);  // Value not in list
        System.out.println("  Size: " + list.size());

        // --- Test 4: Reverse Operations ---
        System.out.println("\n--- Test 4: Reverse Operations ---");
        // Rebuild a list for reversal demo
        SinglyLinkedList list2 = new SinglyLinkedList();
        for (int i = 1; i <= 5; i++) {
            list2.insertAtEnd(i * 10);
        }
        System.out.println("  Original:");
        list2.display();

        list2.reverse();
        System.out.println("  After iterative reverse:");
        list2.display();

        list2.reverseRecursive();
        System.out.println("  After recursive reverse (back to original):");
        list2.display();

        // --- Test 5: Utility Operations ---
        System.out.println("\n--- Test 5: Utility Operations ---");
        SinglyLinkedList list3 = new SinglyLinkedList();
        int[] values = {10, 20, 30, 40, 50, 60, 70};
        for (int v : values) {
            list3.insertAtEnd(v);
        }
        list3.display();
        System.out.println("  Middle element: " + list3.findMiddle());
        System.out.println("  2nd from end: " + list3.getNthFromEnd(2));
        System.out.println("  3rd from end: " + list3.getNthFromEnd(3));
        System.out.println("  Has cycle: " + list3.hasCycle());
        System.out.println("  Element at index 3: " + list3.get(3));

        // --- Test 6: Remove Duplicates from Sorted List ---
        System.out.println("\n--- Test 6: Remove Duplicates (Sorted List) ---");
        SinglyLinkedList list4 = new SinglyLinkedList();
        int[] sortedWithDups = {1, 1, 2, 3, 3, 3, 4, 5, 5};
        for (int v : sortedWithDups) {
            list4.insertAtEnd(v);
        }
        System.out.println("  Before:");
        list4.display();
        list4.removeDuplicatesSorted();
        System.out.println("  After:");
        list4.display();

        // --- Test 7: Edge Cases ---
        System.out.println("\n--- Test 7: Edge Cases ---");
        SinglyLinkedList emptyList = new SinglyLinkedList();
        System.out.println("  Is empty list empty? " + emptyList.isEmpty());
        emptyList.deleteFromBeginning();
        emptyList.deleteFromEnd();
        emptyList.deleteByValue(5);
        emptyList.display();

        emptyList.insertAtEnd(42);
        System.out.println("  Is single-element list empty? " + emptyList.isEmpty());
        System.out.println("  Middle of single element: " + emptyList.findMiddle());
        emptyList.deleteFromEnd();
        emptyList.display();

        System.out.println("\n✓ All linked list operations demonstrated successfully!");
    }
}
