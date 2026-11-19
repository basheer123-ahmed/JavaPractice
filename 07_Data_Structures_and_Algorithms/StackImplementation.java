/*
 * Program 87: Stack Implementation
 * 
 * Problem Statement:
 *   Implement a Stack data structure using both array and linked list approaches.
 *   Support operations: push, pop, peek, isEmpty, isFull (array), size.
 *   Demonstrate practical applications:
 *     (1) Balanced parentheses checker - check if brackets are properly matched
 *     (2) Reverse a string using a stack
 * 
 * Sample Input:
 *   Push: 10, 20, 30, 40, 50
 *   Parentheses: "{[()]}", "({[}])", "((())"
 *   String: "Hello World"
 * 
 * Sample Output:
 *   Stack: [10, 20, 30, 40, 50] (top=50)
 *   "{[()]}" -> Balanced
 *   "({[}])" -> Not Balanced
 *   "((())" -> Not Balanced
 *   Reversed: "dlroW olleH"
 * 
 * Explanation:
 *   A stack follows the Last-In-First-Out (LIFO) principle. The last element
 *   pushed is the first to be popped. Array-based stacks have fixed capacity
 *   but O(1) operations. Linked-list-based stacks are dynamic but use extra
 *   memory for node pointers. Stacks are fundamental in recursion, expression
 *   evaluation, backtracking, and undo mechanisms.
 * 
 * Time Complexity: O(1) for push, pop, peek, isEmpty, isFull, size
 * Space Complexity: O(n) for storing n elements
 *   - Array: O(capacity) fixed
 *   - Linked List: O(n) dynamic
 */

public class StackImplementation {

    // ========== Array-Based Stack ==========
    static class ArrayStack {
        private int[] arr;
        private int top;
        private int capacity;

        public ArrayStack(int capacity) {
            this.capacity = capacity;
            this.arr = new int[capacity];
            this.top = -1;
        }

        public void push(int data) {
            if (isFull()) {
                System.out.println("    Stack Overflow! Cannot push " + data);
                return;
            }
            arr[++top] = data;
        }

        public int pop() {
            if (isEmpty()) {
                System.out.println("    Stack Underflow! Cannot pop.");
                return -1;
            }
            return arr[top--];
        }

        public int peek() {
            if (isEmpty()) {
                System.out.println("    Stack is empty! Nothing to peek.");
                return -1;
            }
            return arr[top];
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public boolean isFull() {
            return top == capacity - 1;
        }

        public int size() {
            return top + 1;
        }

        public void display() {
            if (isEmpty()) {
                System.out.println("    Stack: [EMPTY]");
                return;
            }
            StringBuilder sb = new StringBuilder("    Stack (bottom->top): [");
            for (int i = 0; i <= top; i++) {
                sb.append(arr[i]);
                if (i < top) sb.append(", ");
            }
            sb.append("] <- top");
            System.out.println(sb.toString());
        }
    }

    // ========== Linked-List-Based Stack ==========
    static class LinkedStack {
        private static class Node {
            int data;
            Node next;

            Node(int data) {
                this.data = data;
                this.next = null;
            }
        }

        private Node top;
        private int size;

        public LinkedStack() {
            top = null;
            size = 0;
        }

        public void push(int data) {
            Node newNode = new Node(data);
            newNode.next = top;
            top = newNode;
            size++;
        }

        public int pop() {
            if (isEmpty()) {
                System.out.println("    Stack Underflow! Cannot pop.");
                return -1;
            }
            int data = top.data;
            top = top.next;
            size--;
            return data;
        }

        public int peek() {
            if (isEmpty()) {
                System.out.println("    Stack is empty! Nothing to peek.");
                return -1;
            }
            return top.data;
        }

        public boolean isEmpty() {
            return top == null;
        }

        public int size() {
            return size;
        }

        public void display() {
            if (isEmpty()) {
                System.out.println("    Stack: [EMPTY]");
                return;
            }
            // Collect elements to show bottom-to-top
            int[] temp = new int[size];
            Node current = top;
            for (int i = size - 1; i >= 0; i--) {
                temp[i] = current.data;
                current = current.next;
            }
            StringBuilder sb = new StringBuilder("    Stack (bottom->top): [");
            for (int i = 0; i < temp.length; i++) {
                sb.append(temp[i]);
                if (i < temp.length - 1) sb.append(", ");
            }
            sb.append("] <- top");
            System.out.println(sb.toString());
        }
    }

    // ========== Generic Char Stack for Applications ==========
    static class CharStack {
        private char[] arr;
        private int top;
        private int capacity;

        public CharStack(int capacity) {
            this.capacity = capacity;
            this.arr = new char[capacity];
            this.top = -1;
        }

        public void push(char c) {
            if (top == capacity - 1) return;
            arr[++top] = c;
        }

        public char pop() {
            if (top == -1) return '\0';
            return arr[top--];
        }

        public char peek() {
            if (top == -1) return '\0';
            return arr[top];
        }

        public boolean isEmpty() {
            return top == -1;
        }
    }

    // ========== Application 1: Balanced Parentheses Checker ==========
    static boolean isBalanced(String expression) {
        CharStack stack = new CharStack(expression.length());

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // If opening bracket, push to stack
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }
            // If closing bracket, check for match
            else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) {
                    return false; // No matching opening bracket
                }
                char top = stack.pop();
                if (!isMatchingPair(top, ch)) {
                    return false; // Mismatched brackets
                }
            }
            // Ignore non-bracket characters
        }
        return stack.isEmpty(); // All brackets should be matched
    }

    static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }

    // ========== Application 2: Reverse a String Using Stack ==========
    static String reverseString(String str) {
        CharStack stack = new CharStack(str.length());

        // Push all characters
        for (int i = 0; i < str.length(); i++) {
            stack.push(str.charAt(i));
        }

        // Pop all characters
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }
        return reversed.toString();
    }

    // ========== Application 3 (Bonus): Evaluate Postfix Expression ==========
    static int evaluatePostfix(String expression) {
        ArrayStack stack = new ArrayStack(expression.length());

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == ' ') continue;

            if (Character.isDigit(ch)) {
                stack.push(ch - '0');
            } else {
                int b = stack.pop();
                int a = stack.pop();
                switch (ch) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }

    // ========== Application 4 (Bonus): Next Greater Element ==========
    static void nextGreaterElement(int[] arr) {
        ArrayStack stack = new ArrayStack(arr.length);
        int[] result = new int[arr.length];

        // Traverse from right to left
        for (int i = arr.length - 1; i >= 0; i--) {
            // Pop elements smaller than or equal to current
            while (!stack.isEmpty() && stack.peek() <= arr[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(arr[i]);
        }

        System.out.print("    Array:         [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + (i < arr.length - 1 ? ", " : ""));
        }
        System.out.println("]");
        System.out.print("    Next Greater:  [");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + (i < result.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }

    // ========== Main Method ==========
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║         STACK IMPLEMENTATION - ARRAY & LINKED LIST  ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // ===== Part 1: Array-Based Stack =====
        System.out.println("\n━━━ Part 1: Array-Based Stack ━━━");
        ArrayStack arrStack = new ArrayStack(5);

        System.out.println("  Pushing: 10, 20, 30, 40, 50");
        arrStack.push(10);
        arrStack.push(20);
        arrStack.push(30);
        arrStack.push(40);
        arrStack.push(50);
        arrStack.display();

        System.out.println("  isFull: " + arrStack.isFull());
        System.out.println("  Size: " + arrStack.size());
        System.out.println("  Peek: " + arrStack.peek());

        arrStack.push(60); // Should trigger overflow

        System.out.println("\n  Popping elements:");
        while (!arrStack.isEmpty()) {
            System.out.println("    Popped: " + arrStack.pop());
        }
        System.out.println("  isEmpty: " + arrStack.isEmpty());
        arrStack.pop(); // Should trigger underflow

        // ===== Part 2: Linked-List-Based Stack =====
        System.out.println("\n━━━ Part 2: Linked-List-Based Stack ━━━");
        LinkedStack linkStack = new LinkedStack();

        System.out.println("  Pushing: 100, 200, 300, 400, 500");
        linkStack.push(100);
        linkStack.push(200);
        linkStack.push(300);
        linkStack.push(400);
        linkStack.push(500);
        linkStack.display();

        System.out.println("  Size: " + linkStack.size());
        System.out.println("  Peek: " + linkStack.peek());

        System.out.println("\n  Popping 3 elements:");
        for (int i = 0; i < 3; i++) {
            System.out.println("    Popped: " + linkStack.pop());
        }
        linkStack.display();
        System.out.println("  Size: " + linkStack.size());

        // ===== Part 3: Balanced Parentheses Checker =====
        System.out.println("\n━━━ Part 3: Balanced Parentheses Checker ━━━");
        String[] testExpressions = {
            "{[()]}",
            "({[}])",
            "(())",
            "((())",
            "",
            "([{()}])",
            ")()",
            "{[}",
            "a * (b + c) - [d / {e + f}]",
            "((())())()"
        };

        for (String expr : testExpressions) {
            String displayExpr = expr.isEmpty() ? "(empty)" : "\"" + expr + "\"";
            String result = isBalanced(expr) ? "✓ Balanced" : "✗ Not Balanced";
            System.out.printf("    %-35s -> %s%n", displayExpr, result);
        }

        // ===== Part 4: Reverse a String =====
        System.out.println("\n━━━ Part 4: Reverse String Using Stack ━━━");
        String[] stringsToReverse = {
            "Hello World",
            "Stack",
            "abcdef",
            "racecar",
            "Java Programming"
        };

        for (String s : stringsToReverse) {
            String reversed = reverseString(s);
            System.out.printf("    \"%s\" -> \"%s\"", s, reversed);
            if (s.equals(reversed)) {
                System.out.print(" (palindrome!)");
            }
            System.out.println();
        }

        // ===== Part 5: Postfix Evaluation =====
        System.out.println("\n━━━ Part 5: Evaluate Postfix Expression ━━━");
        String[] postfixExprs = {"23+", "53*4+", "92-3*", "82/3+5*"};
        String[] infixForms = {"2+3", "5*3+4", "(9-2)*3", "(8/2+3)*5"};

        for (int i = 0; i < postfixExprs.length; i++) {
            int result = evaluatePostfix(postfixExprs[i]);
            System.out.printf("    Postfix: %-10s  Infix: %-15s  Result: %d%n",
                    "\"" + postfixExprs[i] + "\"", infixForms[i], result);
        }

        // ===== Part 6: Next Greater Element =====
        System.out.println("\n━━━ Part 6: Next Greater Element ━━━");
        int[] ngeArr = {4, 5, 2, 25, 7, 8, 6, 1};
        nextGreaterElement(ngeArr);

        System.out.println("\n━━━ Comparison: Array vs Linked List Stack ━━━");
        System.out.println("    ┌─────────────────┬──────────────┬──────────────────┐");
        System.out.println("    │ Feature         │ Array Stack  │ LinkedList Stack  │");
        System.out.println("    ├─────────────────┼──────────────┼──────────────────┤");
        System.out.println("    │ Capacity        │ Fixed        │ Dynamic          │");
        System.out.println("    │ Memory          │ Contiguous   │ Non-contiguous   │");
        System.out.println("    │ Overflow        │ Possible     │ Only by RAM      │");
        System.out.println("    │ Extra memory    │ None         │ Pointer per node │");
        System.out.println("    │ Cache friendly   │ Yes          │ No               │");
        System.out.println("    │ All ops O(1)    │ Yes          │ Yes              │");
        System.out.println("    └─────────────────┴──────────────┴──────────────────┘");

        System.out.println("\n✓ All stack operations and applications demonstrated successfully!");
    }
}
