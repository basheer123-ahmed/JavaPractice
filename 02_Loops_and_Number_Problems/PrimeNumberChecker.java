/*
 * Program 21: Prime Number Checker
 * 
 * Problem Statement:
 *   Check if a given number is prime using optimized trial division (checking
 *   only up to the square root). Also find all prime numbers in a range using
 *   the Sieve of Eratosthenes algorithm — one of the most efficient methods
 *   for generating all primes up to a given limit.
 * 
 * Sample Input:  29
 * Sample Output: 29 is a prime number.
 * 
 * Sample Input:  100
 * Sample Output (Sieve): Primes up to 100: 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97
 * 
 * Explanation:
 *   - A prime number is divisible only by 1 and itself.
 *   - Trial Division: We only check divisors up to sqrt(n) because if n has a
 *     factor larger than sqrt(n), it must also have one smaller than sqrt(n).
 *   - Sieve of Eratosthenes: Mark composite numbers by iterating through
 *     primes and marking their multiples. Runs in O(n log log n).
 * 
 * Time Complexity:  O(√n) for single check, O(n log log n) for Sieve
 * Space Complexity: O(1) for single check, O(n) for Sieve
 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class PrimeNumberChecker {

    // --- Method 1: Basic primality check (naive) ---
    public static boolean isPrimeNaive(int n) {
        if (n <= 1) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // --- Method 2: Optimized trial division (up to sqrt(n)) ---
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;             // 2 and 3 are prime
        if (n % 2 == 0 || n % 3 == 0) return false; // Eliminate multiples of 2 and 3

        // Check for factors of the form 6k ± 1 up to sqrt(n)
        // All primes > 3 are of the form 6k ± 1
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    // --- Method 3: Sieve of Eratosthenes ---
    public static boolean[] sieveOfEratosthenes(int limit) {
        boolean[] isPrime = new boolean[limit + 1];

        // Initialize: assume all numbers >= 2 are prime
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }

        // Mark composites: for each prime p, mark its multiples starting from p*p
        for (int p = 2; p * p <= limit; p++) {
            if (isPrime[p]) {
                for (int multiple = p * p; multiple <= limit; multiple += p) {
                    isPrime[multiple] = false;
                }
            }
        }

        return isPrime;
    }

    // --- Method 4: Get list of primes up to limit ---
    public static List<Integer> getPrimesUpTo(int limit) {
        boolean[] sieve = sieveOfEratosthenes(limit);
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (sieve[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    // --- Method 5: Find primes in a range [start, end] ---
    public static List<Integer> getPrimesInRange(int start, int end) {
        boolean[] sieve = sieveOfEratosthenes(end);
        List<Integer> primes = new ArrayList<>();
        for (int i = Math.max(2, start); i <= end; i++) {
            if (sieve[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    // --- Method 6: Count primes up to n (Prime Counting Function) ---
    public static int countPrimes(int n) {
        boolean[] sieve = sieveOfEratosthenes(n);
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (sieve[i]) count++;
        }
        return count;
    }

    // --- Method 7: Find the next prime after n ---
    public static int nextPrime(int n) {
        int candidate = n + 1;
        while (!isPrime(candidate)) {
            candidate++;
        }
        return candidate;
    }

    // --- Method 8: Prime factorization ---
    public static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        if (n <= 1) return factors;

        // Extract factor 2
        while (n % 2 == 0) {
            factors.add(2);
            n /= 2;
        }

        // Check odd factors from 3 to sqrt(n)
        for (int i = 3; i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }

        // If n is still > 1, then it's a prime factor
        if (n > 1) {
            factors.add(n);
        }

        return factors;
    }

    // --- Method 9: Check if two numbers are co-prime (GCD == 1) ---
    public static boolean areCoPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // --- Method 10: Goldbach's Conjecture verification ---
    // Every even number > 2 can be expressed as the sum of two primes
    public static void goldbachPartition(int n) {
        if (n <= 2 || n % 2 != 0) {
            System.out.println("Goldbach's conjecture applies to even numbers > 2.");
            return;
        }
        for (int i = 2; i <= n / 2; i++) {
            if (isPrime(i) && isPrime(n - i)) {
                System.out.println(n + " = " + i + " + " + (n - i));
                return; // Found one partition
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Check if a number is prime ---
        System.out.print("Enter a number to check if it's prime: ");
        int number = scanner.nextInt();

        System.out.println("\n=== Primality Check for " + number + " ===");
        System.out.println("Naive method:     " + (isPrimeNaive(number) ? "PRIME" : "NOT PRIME"));
        System.out.println("Optimized method: " + (isPrime(number) ? "PRIME" : "NOT PRIME"));

        if (!isPrime(number) && number > 1) {
            System.out.println("Prime factors:    " + primeFactors(number));
        }
        System.out.println("Next prime after " + number + ": " + nextPrime(number));

        // --- Demo 2: Sieve of Eratosthenes ---
        System.out.print("\nEnter upper limit for Sieve of Eratosthenes: ");
        int limit = scanner.nextInt();
        List<Integer> primes = getPrimesUpTo(limit);
        System.out.println("\n=== Primes up to " + limit + " (Sieve of Eratosthenes) ===");
        System.out.println(primes);
        System.out.println("Count: " + primes.size());

        // --- Demo 3: Primes in a range ---
        System.out.println("\n=== Primes in Various Ranges ===");
        int[][] ranges = {{1, 50}, {50, 100}, {100, 200}, {900, 1000}};
        for (int[] range : ranges) {
            List<Integer> rangePrimes = getPrimesInRange(range[0], range[1]);
            System.out.println("Primes in [" + range[0] + ", " + range[1] + "]: " 
                               + rangePrimes.size() + " primes");
            System.out.println("  " + rangePrimes);
        }

        // --- Demo 4: Prime factorization ---
        System.out.println("\n=== Prime Factorization ===");
        int[] factorizationTests = {12, 36, 60, 97, 100, 360, 1000};
        for (int test : factorizationTests) {
            List<Integer> factors = primeFactors(test);
            System.out.println(test + " = " + formatFactors(factors));
        }

        // --- Demo 5: Prime counting function ---
        System.out.println("\n=== Prime Counting Function π(n) ===");
        int[] countTests = {10, 100, 1000, 10000, 100000};
        for (int n : countTests) {
            System.out.println("π(" + n + ") = " + countPrimes(n));
        }

        // --- Demo 6: Goldbach's Conjecture verification ---
        System.out.println("\n=== Goldbach's Conjecture (even numbers as sum of two primes) ===");
        for (int even = 4; even <= 50; even += 2) {
            goldbachPartition(even);
        }

        // --- Demo 7: Performance comparison ---
        System.out.println("\n=== Performance: Naive vs. Optimized ===");
        int[] perfTests = {7919, 104729, 1299709};
        for (int test : perfTests) {
            long start1 = System.nanoTime();
            boolean r1 = isPrimeNaive(test);
            long time1 = System.nanoTime() - start1;

            long start2 = System.nanoTime();
            boolean r2 = isPrime(test);
            long time2 = System.nanoTime() - start2;

            System.out.printf("%d: Naive=%dns, Optimized=%dns (Speedup: %.1fx)%n",
                              test, time1, time2, (double) time1 / time2);
        }

        // --- Demo 8: Twin primes (primes that differ by 2) ---
        System.out.println("\n=== Twin Primes up to 200 ===");
        boolean[] sieve = sieveOfEratosthenes(200);
        for (int i = 2; i <= 198; i++) {
            if (sieve[i] && sieve[i + 2]) {
                System.out.print("(" + i + ", " + (i + 2) + ") ");
            }
        }
        System.out.println();

        scanner.close();
    }

    // Helper to format prime factors as multiplication
    private static String formatFactors(List<Integer> factors) {
        if (factors.isEmpty()) return "1";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < factors.size(); i++) {
            if (i > 0) sb.append(" × ");
            sb.append(factors.get(i));
        }
        return sb.toString();
    }
}
