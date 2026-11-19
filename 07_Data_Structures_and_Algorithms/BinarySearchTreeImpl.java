/*
 * Program 89: Binary Search Tree Implementation
 *
 * Problem Statement:
 *   Implement a Binary Search Tree (BST) with:
 *   insert, search, delete, inorder/preorder/postorder traversal,
 *   find min/max, height, check if valid BST, level-order traversal.
 *
 * Sample Input:  Insert: 50, 30, 70, 20, 40, 60, 80
 * Sample Output:
 *   Inorder   : 20 30 40 50 60 70 80
 *   Preorder  : 50 30 20 40 70 60 80
 *   Postorder : 20 40 30 60 80 70 50
 *   Height    : 3
 *
 * Time Complexity:  Average O(log n) for insert/search/delete, O(n) worst case
 * Space Complexity: O(n) for tree, O(h) for recursion stack
 */
import java.util.*;

public class BinarySearchTreeImpl {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static class BST {
        Node root;

        // INSERT
        Node insert(Node node, int value) {
            if (node == null) return new Node(value);
            if (value < node.data)      node.left  = insert(node.left,  value);
            else if (value > node.data) node.right = insert(node.right, value);
            // duplicate values ignored
            return node;
        }
        void insert(int value) { root = insert(root, value); }

        // SEARCH
        boolean search(Node node, int value) {
            if (node == null) return false;
            if (value == node.data)      return true;
            if (value < node.data)       return search(node.left,  value);
            else                         return search(node.right, value);
        }
        boolean search(int value) { return search(root, value); }

        // DELETE
        Node delete(Node node, int value) {
            if (node == null) return null;
            if (value < node.data) {
                node.left  = delete(node.left,  value);
            } else if (value > node.data) {
                node.right = delete(node.right, value);
            } else {
                // Node found — 3 cases:
                if (node.left == null)  return node.right; // no left child
                if (node.right == null) return node.left;  // no right child
                // Two children: replace with inorder successor (smallest in right subtree)
                Node successor = findMin(node.right);
                node.data  = successor.data;
                node.right = delete(node.right, successor.data);
            }
            return node;
        }
        void delete(int value) { root = delete(root, value); }

        // FIND MIN / MAX
        Node findMin(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }
        Node findMax(Node node) {
            while (node.right != null) node = node.right;
            return node;
        }
        int findMin() { return root == null ? -1 : findMin(root).data; }
        int findMax() { return root == null ? -1 : findMax(root).data; }

        // TRAVERSALS
        void inorder(Node node)   {
            if (node == null) return;
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }
        void preorder(Node node)  {
            if (node == null) return;
            System.out.print(node.data + " ");
            preorder(node.left);
            preorder(node.right);
        }
        void postorder(Node node) {
            if (node == null) return;
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.data + " ");
        }

        // LEVEL ORDER (BFS)
        void levelOrder() {
            if (root == null) return;
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    Node curr = queue.poll();
                    System.out.print(curr.data + " ");
                    if (curr.left  != null) queue.offer(curr.left);
                    if (curr.right != null) queue.offer(curr.right);
                }
                System.out.print("| ");
            }
            System.out.println();
        }

        // HEIGHT
        int height(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }
        int height() { return height(root); }

        // COUNT NODES
        int countNodes(Node node) {
            if (node == null) return 0;
            return 1 + countNodes(node.left) + countNodes(node.right);
        }
        int size() { return countNodes(root); }

        // VALID BST CHECK
        boolean isValidBST(Node node, long min, long max) {
            if (node == null) return true;
            if (node.data <= min || node.data >= max) return false;
            return isValidBST(node.left, min, node.data)
                && isValidBST(node.right, node.data, max);
        }
        boolean isValidBST() { return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE); }

        // LOWEST COMMON ANCESTOR
        int lca(Node node, int a, int b) {
            if (node == null) return -1;
            if (a < node.data && b < node.data) return lca(node.left,  a, b);
            if (a > node.data && b > node.data) return lca(node.right, a, b);
            return node.data; // split point = LCA
        }
        int lca(int a, int b) { return lca(root, a, b); }

        // KTH SMALLEST
        int[] count = {0};
        int kthSmallest(Node node, int k) {
            if (node == null) return -1;
            int left = kthSmallest(node.left, k);
            if (left != -1) return left;
            count[0]++;
            if (count[0] == k) return node.data;
            return kthSmallest(node.right, k);
        }
        int kthSmallest(int k) { count[0] = 0; return kthSmallest(root, k); }
    }

    public static void main(String[] args) {
        BST bst = new BST();
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        for (int v : values) bst.insert(v);

        System.out.println("Inserted: " + Arrays.toString(values));
        System.out.println("\n========== Traversals ==========");
        System.out.print("Inorder   : "); bst.inorder(bst.root);   System.out.println();
        System.out.print("Preorder  : "); bst.preorder(bst.root);  System.out.println();
        System.out.print("Postorder : "); bst.postorder(bst.root); System.out.println();
        System.out.print("LevelOrder: "); bst.levelOrder();

        System.out.println("\n========== Properties ==========");
        System.out.println("Min    : " + bst.findMin());
        System.out.println("Max    : " + bst.findMax());
        System.out.println("Height : " + bst.height());
        System.out.println("Size   : " + bst.size());
        System.out.println("isValidBST: " + bst.isValidBST());

        System.out.println("\n========== Search ==========");
        System.out.println("Search 40: " + bst.search(40));
        System.out.println("Search 55: " + bst.search(55));

        System.out.println("\n========== LCA ==========");
        System.out.println("LCA(20, 45): " + bst.lca(20, 45));
        System.out.println("LCA(60, 80): " + bst.lca(60, 80));
        System.out.println("LCA(10, 70): " + bst.lca(10, 70));

        System.out.println("\n========== Kth Smallest ==========");
        for (int k = 1; k <= 5; k++) {
            BST bst2 = new BST();
            for (int v : values) bst2.insert(v);
            System.out.println(k + "th smallest: " + bst2.kthSmallest(k));
        }

        System.out.println("\n========== Delete ==========");
        System.out.print("Before delete 30: "); bst.inorder(bst.root); System.out.println();
        bst.delete(30); // node with two children
        System.out.print("After  delete 30: "); bst.inorder(bst.root); System.out.println();
        bst.delete(10); // leaf node
        System.out.print("After  delete 10: "); bst.inorder(bst.root); System.out.println();
        bst.delete(70); // node with two children
        System.out.print("After  delete 70: "); bst.inorder(bst.root); System.out.println();
        System.out.println("isValidBST after deletes: " + bst.isValidBST());
    }
}
