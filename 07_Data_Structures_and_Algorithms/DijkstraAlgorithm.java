/*
 * Program 94: Dijkstra's Algorithm
 *
 * Problem Statement:
 *   Implement Dijkstra's shortest path algorithm on a weighted directed graph
 *   using a priority queue. Print shortest distances and paths from source.
 *
 * Sample Input:  Weighted graph with 6 nodes
 * Sample Output:
 *   Shortest distances from vertex 0:
 *   Vertex 0 -> 0  (path: 0)
 *   Vertex 1 -> 4  (path: 0 -> 1)
 *   Vertex 5 -> 11 (path: 0 -> 1 -> 5)
 *
 * Time Complexity:  O((V + E) log V) with binary heap priority queue
 * Space Complexity: O(V + E)
 */
import java.util.*;

public class DijkstraAlgorithm {

    static class Edge {
        int destination, weight;
        Edge(int dest, int weight) { this.destination = dest; this.weight = weight; }
    }

    static class Graph {
        int vertices;
        List<List<Edge>> adj;

        Graph(int v) {
            vertices = v;
            adj = new ArrayList<>();
            for (int i = 0; i < v; i++) adj.add(new ArrayList<>());
        }

        void addEdge(int src, int dest, int weight) {
            adj.get(src).add(new Edge(dest, weight));
        }

        void addUndirectedEdge(int u, int v, int weight) {
            adj.get(u).add(new Edge(v, weight));
            adj.get(v).add(new Edge(u, weight));
        }

        // ============================================================
        // Dijkstra's Algorithm
        // ============================================================
        int[] dijkstra(int src, int[] parent) {
            int[] dist = new int[vertices];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[src] = 0;

            // Min-heap: [distance, vertex]
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
            pq.offer(new int[]{0, src});

            while (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int d = curr[0], u = curr[1];

                if (d > dist[u]) continue; // stale entry — skip

                for (Edge edge : adj.get(u)) {
                    int v = edge.destination;
                    int newDist = dist[u] + edge.weight;
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        parent[v] = u;
                        pq.offer(new int[]{newDist, v});
                    }
                }
            }
            return dist;
        }

        // Reconstruct path from src to dest
        List<Integer> getPath(int[] parent, int src, int dest) {
            List<Integer> path = new ArrayList<>();
            if (parent[dest] == -1 && dest != src) return path; // unreachable
            for (int v = dest; v != -1; v = parent[v]) path.add(0, v);
            return path;
        }

        void printResults(int src, String[] labels) {
            int[] parent = new int[vertices];
            Arrays.fill(parent, -1);
            int[] dist = dijkstra(src, parent);

            System.out.printf("Shortest paths from '%s' (vertex %d):%n",
                    labels != null ? labels[src] : String.valueOf(src), src);

            for (int v = 0; v < vertices; v++) {
                String label = labels != null ? labels[v] : String.valueOf(v);
                List<Integer> path = getPath(parent, src, v);
                String pathStr;
                if (labels != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < path.size(); i++) {
                        if (i > 0) sb.append(" -> ");
                        sb.append(labels[path.get(i)]);
                    }
                    pathStr = sb.toString();
                } else {
                    pathStr = path.toString().replace("[","").replace("]","").replace(", "," -> ");
                }
                System.out.printf("  To %-10s : dist=%3s  path: %s%n",
                        label,
                        dist[v] == Integer.MAX_VALUE ? "INF" : dist[v],
                        dist[v] == Integer.MAX_VALUE ? "unreachable" : pathStr);
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("========== Example 1: Classic Weighted Graph ==========");
        //   0 --4-> 1 --8-> 2
        //   |       |       |
        //   8       11      7
        //   |       |       |
        //   7 <-9-- 6 <-2-- 3
        //   |   \
        //  14    1
        //   |     \
        //   5 --10-> 4
        Graph g1 = new Graph(7);
        g1.addUndirectedEdge(0, 1, 4);
        g1.addUndirectedEdge(0, 7, 8);
        g1.addUndirectedEdge(1, 2, 8);
        g1.addUndirectedEdge(1, 7, 11);
        g1.addUndirectedEdge(2, 3, 7);
        g1.addUndirectedEdge(2, 5, 4);
        g1.addUndirectedEdge(2, 8, 2);
        g1.addUndirectedEdge(3, 4, 9);
        g1.addUndirectedEdge(3, 5, 14);
        g1.addUndirectedEdge(4, 5, 10);
        g1.addUndirectedEdge(5, 6, 2);
        g1.addUndirectedEdge(6, 7, 1);
        g1.addUndirectedEdge(6, 8, 6);
        g1.addUndirectedEdge(7, 8, 7);
        g1.printResults(0, null);

        System.out.println("\n========== Example 2: City Road Network ==========");
        // Cities: 0=Delhi, 1=Agra, 2=Jaipur, 3=Mumbai, 4=Pune, 5=Bangalore
        Graph cities = new Graph(6);
        String[] cityNames = {"Delhi","Agra","Jaipur","Mumbai","Pune","Bangalore"};
        cities.addUndirectedEdge(0, 1, 200);  // Delhi-Agra
        cities.addUndirectedEdge(0, 2, 280);  // Delhi-Jaipur
        cities.addUndirectedEdge(1, 3, 1200); // Agra-Mumbai
        cities.addUndirectedEdge(2, 3, 1150); // Jaipur-Mumbai
        cities.addUndirectedEdge(3, 4, 150);  // Mumbai-Pune
        cities.addUndirectedEdge(4, 5, 840);  // Pune-Bangalore
        cities.addUndirectedEdge(3, 5, 980);  // Mumbai-Bangalore
        cities.printResults(0, cityNames);

        System.out.println("\n========== Example 3: Directed Weighted Graph with unreachable node ==========");
        Graph g3 = new Graph(5);
        g3.addEdge(0, 1, 10);
        g3.addEdge(0, 2, 3);
        g3.addEdge(1, 3, 2);
        g3.addEdge(2, 1, 4);
        g3.addEdge(2, 3, 8);
        g3.addEdge(2, 4, 2);
        g3.addEdge(3, 4, 5);
        // vertex 4 has no outgoing edges
        g3.printResults(0, null);

        System.out.println("\n========== All-Pairs Shortest Paths from every vertex ==========");
        Graph g4 = new Graph(4);
        g4.addUndirectedEdge(0, 1, 2);
        g4.addUndirectedEdge(0, 2, 6);
        g4.addUndirectedEdge(1, 2, 3);
        g4.addUndirectedEdge(1, 3, 8);
        g4.addUndirectedEdge(2, 3, 7);

        System.out.println("Distance matrix:");
        System.out.print("     ");
        for (int j = 0; j < 4; j++) System.out.printf("%5d", j);
        System.out.println();
        for (int i = 0; i < 4; i++) {
            int[] p = new int[4]; Arrays.fill(p, -1);
            int[] d = g4.dijkstra(i, p);
            System.out.printf("[%d]  ", i);
            for (int j = 0; j < 4; j++) System.out.printf("%5d", d[j]);
            System.out.println();
        }
    }
}
