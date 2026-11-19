/*
 * Program 93: Graph BFS and DFS
 *
 * Problem Statement:
 *   Implement a graph using adjacency list. Perform:
 *   - BFS (Breadth-First Search) traversal
 *   - DFS (Depth-First Search) traversal
 *   - Cycle detection in undirected graph
 *   - Find connected components
 *   - Shortest path (unweighted) using BFS
 *
 * Sample Input:  Graph with 6 vertices and edges
 * Sample Output:
 *   BFS from 0: 0 1 2 3 4 5
 *   DFS from 0: 0 1 3 4 2 5
 *   Has cycle  : true
 *
 * Time Complexity:  BFS/DFS O(V + E)
 * Space Complexity: O(V + E) for adjacency list
 */
import java.util.*;

public class GraphBFSDFS {

    static class Graph {
        int vertices;
        List<List<Integer>> adj;

        Graph(int v) {
            vertices = v;
            adj = new ArrayList<>();
            for (int i = 0; i < v; i++) adj.add(new ArrayList<>());
        }

        // Undirected edge
        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Directed edge
        void addDirectedEdge(int u, int v) {
            adj.get(u).add(v);
        }

        // ============================================================
        // BFS — Breadth-First Search
        // ============================================================
        void bfs(int start) {
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            visited[start] = true;
            queue.offer(start);
            System.out.print("BFS from " + start + ": ");
            while (!queue.isEmpty()) {
                int node = queue.poll();
                System.out.print(node + " ");
                for (int neighbor : adj.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            System.out.println();
        }

        // BFS level-by-level
        void bfsLevels(int start) {
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            visited[start] = true;
            queue.offer(start);
            int level = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                System.out.print("Level " + level + ": ");
                for (int i = 0; i < size; i++) {
                    int node = queue.poll();
                    System.out.print(node + " ");
                    for (int nb : adj.get(node)) {
                        if (!visited[nb]) { visited[nb] = true; queue.offer(nb); }
                    }
                }
                System.out.println();
                level++;
            }
        }

        // ============================================================
        // DFS — Depth-First Search (iterative using stack)
        // ============================================================
        void dfsIterative(int start) {
            boolean[] visited = new boolean[vertices];
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(start);
            System.out.print("DFS (iterative) from " + start + ": ");
            while (!stack.isEmpty()) {
                int node = stack.pop();
                if (!visited[node]) {
                    visited[node] = true;
                    System.out.print(node + " ");
                    // Push neighbors in reverse to match recursive order
                    List<Integer> neighbors = adj.get(node);
                    for (int i = neighbors.size() - 1; i >= 0; i--) {
                        if (!visited[neighbors.get(i)]) stack.push(neighbors.get(i));
                    }
                }
            }
            System.out.println();
        }

        // DFS recursive
        void dfsRecursive(int node, boolean[] visited) {
            visited[node] = true;
            System.out.print(node + " ");
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) dfsRecursive(neighbor, visited);
            }
        }

        void dfs(int start) {
            boolean[] visited = new boolean[vertices];
            System.out.print("DFS (recursive) from " + start + ": ");
            dfsRecursive(start, visited);
            System.out.println();
        }

        // ============================================================
        // Cycle Detection in Undirected Graph (using DFS + parent tracking)
        // ============================================================
        boolean hasCycleUtil(int node, int parent, boolean[] visited) {
            visited[node] = true;
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    if (hasCycleUtil(neighbor, node, visited)) return true;
                } else if (neighbor != parent) {
                    return true; // back edge found = cycle
                }
            }
            return false;
        }

        boolean hasCycle() {
            boolean[] visited = new boolean[vertices];
            for (int i = 0; i < vertices; i++) {
                if (!visited[i] && hasCycleUtil(i, -1, visited)) return true;
            }
            return false;
        }

        // ============================================================
        // Connected Components
        // ============================================================
        int connectedComponents() {
            boolean[] visited = new boolean[vertices];
            int count = 0;
            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) {
                    System.out.print("  Component " + count + ": ");
                    dfsComponent(i, visited);
                    System.out.println();
                    count++;
                }
            }
            return count;
        }

        void dfsComponent(int node, boolean[] visited) {
            visited[node] = true;
            System.out.print(node + " ");
            for (int nb : adj.get(node)) {
                if (!visited[nb]) dfsComponent(nb, visited);
            }
        }

        // ============================================================
        // Shortest Path (unweighted) using BFS
        // ============================================================
        int[] shortestPath(int src) {
            int[] dist = new int[vertices];
            Arrays.fill(dist, -1);
            dist[src] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(src);
            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int nb : adj.get(node)) {
                    if (dist[nb] == -1) {
                        dist[nb] = dist[node] + 1;
                        queue.offer(nb);
                    }
                }
            }
            return dist;
        }
    }

    public static void main(String[] args) {

        System.out.println("========== Graph 1: Connected Undirected ==========");
        //  0 --- 1 --- 3
        //  |     |     |
        //  2     4 --- 5
        Graph g1 = new Graph(6);
        g1.addEdge(0, 1); g1.addEdge(0, 2);
        g1.addEdge(1, 3); g1.addEdge(1, 4);
        g1.addEdge(3, 5); g1.addEdge(4, 5);

        g1.bfs(0);
        g1.dfs(0);
        g1.dfsIterative(0);
        System.out.println("\n--- BFS Level by Level ---");
        g1.bfsLevels(0);
        System.out.println("Has Cycle: " + g1.hasCycle());

        System.out.println("\n--- Shortest paths from vertex 0 ---");
        int[] dist = g1.shortestPath(0);
        for (int i = 0; i < dist.length; i++) {
            System.out.println("  0 -> " + i + " : " + dist[i] + " edges");
        }

        System.out.println("\n========== Graph 2: Disconnected (3 components) ==========");
        Graph g2 = new Graph(8);
        g2.addEdge(0, 1); g2.addEdge(1, 2);    // component 0
        g2.addEdge(3, 4);                        // component 1
        g2.addEdge(5, 6); g2.addEdge(6, 7);    // component 2
        // vertex alone — no edges (implicit component)

        System.out.println("Connected components:");
        int cc = g2.connectedComponents();
        System.out.println("Total components: " + cc);

        System.out.println("\n========== Graph 3: Cycle Detection ==========");
        Graph cyclic = new Graph(5);
        cyclic.addEdge(0, 1); cyclic.addEdge(1, 2);
        cyclic.addEdge(2, 3); cyclic.addEdge(3, 0); // cycle!
        cyclic.addEdge(3, 4);
        cyclic.bfs(0);
        System.out.println("Has Cycle: " + cyclic.hasCycle()); // true

        Graph acyclic = new Graph(5);
        acyclic.addEdge(0, 1); acyclic.addEdge(1, 2);
        acyclic.addEdge(2, 3); acyclic.addEdge(3, 4); // tree — no cycle
        acyclic.bfs(0);
        System.out.println("Has Cycle: " + acyclic.hasCycle()); // false

        System.out.println("\n========== Graph 4: Social Network Shortest Path ==========");
        // 0=Alice, 1=Bob, 2=Charlie, 3=Diana, 4=Eve, 5=Frank
        Graph social = new Graph(6);
        social.addEdge(0, 1); social.addEdge(0, 2);
        social.addEdge(1, 3); social.addEdge(2, 3);
        social.addEdge(3, 4); social.addEdge(4, 5);
        String[] names = {"Alice","Bob","Charlie","Diana","Eve","Frank"};
        int[] d = social.shortestPath(0);
        System.out.println("Degrees of separation from Alice:");
        for (int i = 1; i < names.length; i++) {
            System.out.println("  Alice -> " + names[i] + ": " + d[i] + " degrees");
        }
    }
}
