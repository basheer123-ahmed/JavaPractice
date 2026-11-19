/*
 * Program 95: Dynamic Programming — Knapsack, LCS, Coin Change
 *
 * Problem Statement:
 *   Solve 3 classic DP problems:
 *   1. 0/1 Knapsack — maximize value within weight capacity
 *   2. Longest Common Subsequence (LCS) — find longest common subsequence
 *   3. Coin Change — minimum coins to make an amount
 *   Each solved using memoization (top-down) and tabulation (bottom-up).
 *
 * Time Complexity:
 *   Knapsack: O(n*W) | LCS: O(m*n) | Coin Change: O(n*amount)
 * Space Complexity: O(n*W), O(m*n), O(amount)
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicProgramming {

    // ============================================================
    // 1. 0/1 KNAPSACK
    // ============================================================

    // Recursive with memoization
    static Map<String, Integer> knapsackMemo = new HashMap<>();

    static int knapsackMemo(int[] weights, int[] values, int capacity, int n) {
        if (n == 0 || capacity == 0) return 0;
        String key = n + "," + capacity;
        if (knapsackMemo.containsKey(key)) return knapsackMemo.get(key);

        int result;
        if (weights[n-1] > capacity) {
            result = knapsackMemo(weights, values, capacity, n - 1);
        } else {
            int take     = values[n-1] + knapsackMemo(weights, values, capacity - weights[n-1], n - 1);
            int notTake  = knapsackMemo(weights, values, capacity, n - 1);
            result = Math.max(take, notTake);
        }
        knapsackMemo.put(key, result);
        return result;
    }

    // Tabulation (bottom-up)
    static int knapsackDP(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n+1][capacity+1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i-1][w]; // don't take item i
                if (weights[i-1] <= w) {
                    dp[i][w] = Math.max(dp[i][w], values[i-1] + dp[i-1][w - weights[i-1]]);
                }
            }
        }
        // Reconstruct which items were taken
        System.out.print("  Items selected: ");
        int w = capacity;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                System.out.print("item" + i + "(w=" + weights[i-1] + ",v=" + values[i-1] + ") ");
                w -= weights[i-1];
            }
        }
        System.out.println();
        return dp[n][capacity];
    }

    // ============================================================
    // 2. LONGEST COMMON SUBSEQUENCE
    // ============================================================

    // Memoization
    static int[][] lcsMemo;

    static int lcsRecursive(String s1, String s2, int m, int n) {
        if (m == 0 || n == 0) return 0;
        if (lcsMemo[m][n] != -1) return lcsMemo[m][n];
        if (s1.charAt(m-1) == s2.charAt(n-1)) {
            lcsMemo[m][n] = 1 + lcsRecursive(s1, s2, m-1, n-1);
        } else {
            lcsMemo[m][n] = Math.max(lcsRecursive(s1, s2, m-1, n),
                                     lcsRecursive(s1, s2, m, n-1));
        }
        return lcsMemo[m][n];
    }

    // Tabulation + reconstruct the actual subsequence
    static String lcsDP(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m+1][n+1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else                                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
        // Backtrack to find the LCS string
        StringBuilder sb = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                sb.insert(0, s1.charAt(i-1));
                i--; j--;
            } else if (dp[i-1][j] > dp[i][j-1]) i--;
            else j--;
        }
        return sb.toString();
    }

    // ============================================================
    // 3. COIN CHANGE — Minimum coins
    // ============================================================

    // Memoization
    static int coinChangeMemo(int[] coins, int amount, int[] memo) {
        if (amount == 0) return 0;
        if (amount < 0)  return -1;
        if (memo[amount] != 0) return memo[amount];
        int minCoins = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = coinChangeMemo(coins, amount - coin, memo);
            if (res >= 0) minCoins = Math.min(minCoins, res + 1);
        }
        memo[amount] = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
        return memo[amount];
    }

    // Tabulation
    static int coinChangeDP(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // Coin Change 2 — count number of ways
    static int coinChangeWays(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) dp[i] += dp[i - coin];
        }
        return dp[amount];
    }

    public static void main(String[] args) {

        System.out.println("========== 1. 0/1 Knapsack ==========");
        int[] weights  = {1, 3, 4, 5};
        int[] values   = {1, 4, 5, 7};
        int   capacity = 7;
        System.out.println("Weights : " + Arrays.toString(weights));
        System.out.println("Values  : " + Arrays.toString(values));
        System.out.println("Capacity: " + capacity);

        knapsackMemo.clear();
        int memoResult = knapsackMemo(weights, values, capacity, weights.length);
        System.out.println("Max value (Memoization) : " + memoResult);
        System.out.print("Max value (Tabulation)  : ");
        int dpResult = knapsackDP(weights, values, capacity);
        System.out.println("  Result: " + dpResult);

        // Another test case
        System.out.println();
        int[] w2 = {2, 3, 4, 5}, v2 = {3, 4, 5, 6};
        int cap2 = 8;
        System.out.println("Weights: " + Arrays.toString(w2) + " Values: " + Arrays.toString(v2) + " Cap: " + cap2);
        System.out.print("Max value (Tabulation): ");
        System.out.println("  Result: " + knapsackDP(w2, v2, cap2));

        System.out.println("\n========== 2. Longest Common Subsequence ==========");
        String[][] pairs = {
            {"ABCBDAB", "BDCABA"},
            {"AGGTAB",  "GXTXAYB"},
            {"ABCDEF",  "ACEF"},
            {"ABC",     "ABC"},
            {"ABC",     "DEF"}
        };
        for (String[] pair : pairs) {
            String s1 = pair[0], s2 = pair[1];
            lcsMemo = new int[s1.length()+1][s2.length()+1];
            for (int[] row : lcsMemo) Arrays.fill(row, -1);
            int len  = lcsRecursive(s1, s2, s1.length(), s2.length());
            String lcs = lcsDP(s1, s2);
            System.out.printf("  LCS('%s','%s') = '%s' (len=%d)%n", s1, s2, lcs, len);
        }

        System.out.println("\n========== 3. Coin Change — Minimum Coins ==========");
        int[][] coinSets = {{1,5,6,9}, {1,2,5}, {2}, {186,419,83,408}};
        int[]   amounts  = {11,        11,       3,   6249};

        for (int t = 0; t < coinSets.length; t++) {
            int[] coins = coinSets[t];
            int amount  = amounts[t];
            int[] memo  = new int[amount + 1];
            int memoAns = coinChangeMemo(coins, amount, memo);
            int dpAns   = coinChangeDP(coins, amount);
            System.out.printf("  Coins=%s amount=%d | min=%d (memo=%d)%n",
                    Arrays.toString(coins), amount, dpAns, memoAns);
        }

        System.out.println("\n========== Coin Change — Number of Ways ==========");
        int[][] coinSets2 = {{1,2,5}, {2}, {10}};
        int[]   amounts2  = {5,       3,   10};
        for (int t = 0; t < coinSets2.length; t++) {
            System.out.printf("  Coins=%s amount=%d | ways=%d%n",
                    Arrays.toString(coinSets2[t]), amounts2[t],
                    coinChangeWays(coinSets2[t], amounts2[t]));
        }

        System.out.println("\n========== Summary Table ==========");
        System.out.println("Problem          | Recurrence                        | TC         | SC");
        System.out.println("0/1 Knapsack     | dp[i][w]=max(dp[i-1][w], ...)    | O(n*W)     | O(n*W)");
        System.out.println("LCS              | dp[i][j]=dp[i-1][j-1]+1 or max   | O(m*n)     | O(m*n)");
        System.out.println("Coin Change Min  | dp[i]=min(dp[i-coin]+1)           | O(n*amt)   | O(amt)");
        System.out.println("Coin Change Ways | dp[i]+=dp[i-coin]                 | O(n*amt)   | O(amt)");
    }
}
