/*
 * Program 27: Perfect Number
 * 
 * Problem Statement:
 *   A perfect number is a positive integer that is equal to the sum of its proper
 *   divisors (all divisors excluding the number itself).
 *   - Check if a given number is a perfect number.
 *   - Find all perfect numbers up to N.
 *   - Also explore related concepts: abundant and deficient numbers.
 * 
 * Sample Input:  6, 28, 12, N=10000
 * Sample Output:
 *   6 is a Perfect Number (1 + 2 + 3 = 6)
 *   28 is a Perfect Number (1 + 2 + 4 + 7 + 14 = 28)
 *   12 is NOT a Perfect Number (1 + 2 + 3 + 4 + 6 = 16)
 *   Perfect numbers up to 10000: [6, 28, 496, 8128]
 * 
 * Explanation:
 *   To find divisors efficiently, we only iterate up to sqrt(n). For each divisor
 *   i found, both i and n/i are divisors. This reduces the time from O(n) to
 *   O(sqrt(n)) per check. Known even perfect numbers follow the Euclid-Euler
 *   formula: 2^(p-1) * (2^p - 1), where (2^p - 1) is a Mersenne prime.
 * 
 * Time Complexity: O(sqrt(n)) for single check, O(N * sqrt(N)) for finding all up to N
 * Space Complexity: O(sqrt(n)) for storing divisors, O(k) for result list where k = count of perfect numbers
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PerfectNumber {

    /**
     * Checks if a number is perfect using the O(sqrt(n)) method.
     * Returns the sum of proper divisors.
     */
    public static int sumOfProperDivisors(int n) {
        if (n <= 1) return 0;

        int sum = 1; // 1 is always a proper divisor for n > 1
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                sum += i;
                if (i != n / i) { // Avoid adding sqrt(n) twice
                    sum += n / i;
                }
            }
        }
        return sum;
    }

    /**
     * Checks if a number is a perfect number.
     */
    public static boolean isPerfect(int n) {
        if (n <= 1) return false;
        return sumOfProperDivisors(n) == n;
    }

    /**
     * Gets the list of all proper divisors of n.
     */
    public static List<Integer> getProperDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        if (n <= 1) return divisors;

        divisors.add(1);
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i != n / i) {
                    divisors.add(n / i);
                }
            }
        }
        divisors.sort(Integer::compareTo);
        return divisors;
    }

    /**
     * Finds all perfect numbers up to the given limit.
     */
    public static List<Integer> findAllPerfectNumbers(int limit) {
        List<Integer> perfects = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPerfect(i)) {
                perfects.add(i);
            }
        }
        return perfects;
    }

    /**
     * Uses the Euclid-Euler theorem to generate known even perfect numbers.
     * A number is perfect if it can be written as 2^(p-1) * (2^p - 1),
     * where (2^p - 1) is a Mersenne prime.
     */
    public static List<Long> generatePerfectNumbersEuclidEuler(int maxP) {
        List<Long> perfects = new ArrayList<>();
        for (int p = 2; p <= maxP; p++) {
            long mersenneCandidate = (1L << p) - 1; // 2^p - 1
            if (isPrimeLong(mersenneCandidate)) {
                long perfectNum = (1L << (p - 1)) * mersenneCandidate; // 2^(p-1) * (2^p - 1)
                perfects.add(perfectNum);
            }
        }
        return perfects;
    }

    /**
     * Simple primality test for long values.
     */
    private static boolean isPrimeLong(long n) {
        if (n < 2) return false;
        if (n < 4) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    /**
     * Classifies a number as Perfect, Abundant, or Deficient.
     *   - Perfect:   sum of proper divisors == n
     *   - Abundant:  sum of proper divisors > n
     *   - Deficient: sum of proper divisors < n
     */
    public static String classify(int n) {
        if (n <= 0) return "Invalid (must be positive)";
        if (n == 1) return "Deficient";

        int sum = sumOfProperDivisors(n);
        if (sum == n) return "Perfect";
        else if (sum > n) return "Abundant";
        else return "Deficient";
    }

    public static void main(String[] args) {
        System.out.println("=== Program 27: Perfect Number ===\n");

        // ---- Single number checks ----
        System.out.println("--- Checking Individual Numbers ---");
        int[] testNums = {1, 6, 10, 12, 28, 100, 496, 8128};

        for (int num : testNums) {
            List<Integer> divisors = getProperDivisors(num);
            int sum = sumOfProperDivisors(num);
            boolean perfect = isPerfect(num);

            System.out.printf("  %d: divisors = %s, sum = %d -> %s%n",
                    num, divisors, sum, perfect ? "PERFECT ✓" : "NOT perfect");
        }

        // ---- Find all perfect numbers up to N ----
        System.out.println("\n--- Finding All Perfect Numbers up to 10000 ---");
        List<Integer> perfects = findAllPerfectNumbers(10000);
        System.out.println("  Perfect numbers: " + perfects);

        // ---- Verify each perfect number ----
        System.out.println("\n--- Verification of Each Perfect Number ---");
        for (int p : perfects) {
            List<Integer> divs = getProperDivisors(p);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < divs.size(); i++) {
                if (i > 0) sb.append(" + ");
                sb.append(divs.get(i));
            }
            sb.append(" = ").append(sumOfProperDivisors(p));
            System.out.printf("  %d = %s%n", p, sb);
        }

        // ---- Euclid-Euler theorem ----
        System.out.println("\n--- Euclid-Euler Theorem: Generating Perfect Numbers ---");
        System.out.println("  Formula: 2^(p-1) * (2^p - 1), where (2^p - 1) is Mersenne prime");
        List<Long> euclidPerfects = generatePerfectNumbersEuclidEuler(31);
        for (int i = 0; i < euclidPerfects.size(); i++) {
            System.out.printf("  #%d: %,d%n", i + 1, euclidPerfects.get(i));
        }

        // ---- Number classification ----
        System.out.println("\n--- Number Classification (Perfect / Abundant / Deficient) ---");
        int[] classifyNums = {1, 2, 3, 4, 5, 6, 10, 12, 18, 20, 24, 28, 30};
        for (int num : classifyNums) {
            int sum = sumOfProperDivisors(num);
            System.out.printf("  %3d: sum of divisors = %3d -> %s%n", num, sum, classify(num));
        }

        // ---- Statistics ----
        System.out.println("\n--- Statistics up to 1000 ---");
        int perfectCount = 0, abundantCount = 0, deficientCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String type = classify(i);
            switch (type) {
                case "Perfect":   perfectCount++;   break;
                case "Abundant":  abundantCount++;  break;
                case "Deficient": deficientCount++; break;
            }
        }
        System.out.printf("  Perfect:   %d%n", perfectCount);
        System.out.printf("  Abundant:  %d%n", abundantCount);
        System.out.printf("  Deficient: %d%n", deficientCount);

        // ---- Interactive mode ----
        System.out.println("\n--- Interactive Mode ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a positive integer to check: ");
        if (scanner.hasNextInt()) {
            int userNum = scanner.nextInt();
            if (userNum > 0) {
                List<Integer> divs = getProperDivisors(userNum);
                int sum = sumOfProperDivisors(userNum);
                System.out.println("  Proper divisors: " + divs);
                System.out.println("  Sum of divisors: " + sum);
                System.out.println("  Classification:  " + classify(userNum));
                System.out.println("  Is Perfect?      " + isPerfect(userNum));
            } else {
                System.out.println("  Please enter a positive integer.");
            }
        }
        scanner.close();
    }
}
