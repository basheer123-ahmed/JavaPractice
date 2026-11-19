/*
 * Program 20: Armstrong Number (Narcissistic Number)
 * 
 * Problem Statement:
 *   Check if a given number is an Armstrong (Narcissistic) number. An Armstrong
 *   number of n digits is a number where the sum of each digit raised to the
 *   power of n equals the number itself.
 *   Examples: 153 = 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153
 *             9474 = 9^4 + 4^4 + 7^4 + 4^4 = 6561 + 256 + 2401 + 256 = 9474
 *   Also find all Armstrong numbers within a given range.
 * 
 * Sample Input:  153
 * Sample Output: 153 is an Armstrong number.
 *   Explanation: 153 has 3 digits: 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153
 * 
 * Sample Input:  123
 * Sample Output: 123 is NOT an Armstrong number.
 *   Explanation: 123 has 3 digits: 1^3 + 2^3 + 3^3 = 1 + 8 + 27 = 36 ≠ 123
 * 
 * Time Complexity:  O(d) per check where d is the number of digits
 * Space Complexity: O(1)
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ArmstrongNumber {

    // --- Method 1: Check if a number is Armstrong using Math.pow ---
    public static boolean isArmstrong(int number) {
        if (number < 0) return false;

        int original = number;
        int numDigits = countDigits(number);
        int sum = 0;

        while (number > 0) {
            int digit = number % 10;
            sum += (int) Math.pow(digit, numDigits);
            number /= 10;
        }

        return sum == original;
    }

    // --- Method 2: Check Armstrong without Math.pow (manual exponentiation) ---
    public static boolean isArmstrongManual(int number) {
        if (number < 0) return false;

        int original = number;
        int numDigits = countDigits(number);
        int sum = 0;

        while (number > 0) {
            int digit = number % 10;
            sum += power(digit, numDigits);
            number /= 10;
        }

        return sum == original;
    }

    // Manual power function to avoid floating-point issues with Math.pow
    private static int power(int base, int exp) {
        int result = 1;
        for (int i = 0; i < exp; i++) {
            result *= base;
        }
        return result;
    }

    // Count the number of digits in a number
    private static int countDigits(int number) {
        if (number == 0) return 1;
        int count = 0;
        while (number > 0) {
            count++;
            number /= 10;
        }
        return count;
    }

    // --- Method 3: Check Armstrong using String for digit count ---
    public static boolean isArmstrongString(int number) {
        if (number < 0) return false;

        String str = String.valueOf(number);
        int numDigits = str.length();
        int sum = 0;

        for (char c : str.toCharArray()) {
            int digit = c - '0';
            sum += power(digit, numDigits);
        }

        return sum == number;
    }

    // --- Method 4: Find all Armstrong numbers in a range ---
    public static List<Integer> findArmstrongInRange(int start, int end) {
        List<Integer> armstrongNumbers = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isArmstrong(i)) {
                armstrongNumbers.add(i);
            }
        }
        return armstrongNumbers;
    }

    // --- Method 5: Explain the Armstrong check step by step ---
    public static void explainArmstrong(int number) {
        if (number < 0) {
            System.out.println("Negative numbers cannot be Armstrong numbers.");
            return;
        }

        int original = number;
        int numDigits = countDigits(number);
        int sum = 0;

        System.out.println("Checking if " + original + " is an Armstrong number:");
        System.out.println("Number of digits: " + numDigits);
        System.out.print("Calculation: ");

        StringBuilder calculation = new StringBuilder();
        int temp = number;
        boolean first = true;

        while (temp > 0) {
            int digit = temp % 10;
            int powered = power(digit, numDigits);
            sum += powered;

            // Build calculation string (digits are extracted right to left)
            String part = digit + "^" + numDigits + "(" + powered + ")";
            if (first) {
                calculation.insert(0, part);
                first = false;
            } else {
                calculation.insert(0, part + " + ");
            }

            temp /= 10;
        }

        System.out.println(calculation + " = " + sum);
        System.out.println(sum + (sum == original ? " == " : " != ") + original);
        System.out.println("Result: " + original + " is " 
                           + (sum == original ? "" : "NOT ") + "an Armstrong number.\n");
    }

    // --- Method 6: Find Armstrong numbers by digit count ---
    public static void findArmstrongByDigitCount(int digits) {
        int start = (digits == 1) ? 0 : power(10, digits - 1);
        int end = power(10, digits) - 1;

        System.out.println(digits + "-digit Armstrong numbers:");
        List<Integer> results = findArmstrongInRange(start, end);
        if (results.isEmpty()) {
            System.out.println("  None found.");
        } else {
            for (int num : results) {
                System.out.print("  " + num);
            }
            System.out.println();
        }
    }

    // --- Method 7: Check if a number is a perfect digital invariant ---
    // Generalized: sum of digits each raised to power p equals the number
    public static boolean isNarcissisticForPower(int number, int p) {
        if (number < 0) return false;
        int original = number;
        long sum = 0;
        while (number > 0) {
            int digit = number % 10;
            sum += power(digit, p);
            number /= 10;
        }
        return sum == original;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Check a user-entered number ---
        System.out.print("Enter a number to check if it's an Armstrong number: ");
        int number = scanner.nextInt();

        System.out.println("\n=== Armstrong Number Check ===");
        explainArmstrong(number);

        System.out.println("Using Math.pow:        " + (isArmstrong(number) ? "YES" : "NO"));
        System.out.println("Using manual power:    " + (isArmstrongManual(number) ? "YES" : "NO"));
        System.out.println("Using String approach: " + (isArmstrongString(number) ? "YES" : "NO"));

        // --- Demo 2: Test with known Armstrong numbers ---
        System.out.println("\n=== Known Armstrong Numbers ===");
        int[] knownArmstrong = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
                                 153, 370, 371, 407, 
                                 1634, 8208, 9474};
        for (int num : knownArmstrong) {
            System.out.println(num + " -> " + (isArmstrong(num) ? "Armstrong ✓" : "Not Armstrong ✗"));
        }

        // --- Demo 3: Explain step by step for key examples ---
        System.out.println("\n=== Step-by-Step Explanations ===");
        explainArmstrong(153);
        explainArmstrong(9474);
        explainArmstrong(123);
        explainArmstrong(407);

        // --- Demo 4: Find Armstrong numbers in a range ---
        System.out.println("\n=== Armstrong Numbers in Ranges ===");
        System.out.print("Enter start of range: ");
        int start = scanner.nextInt();
        System.out.print("Enter end of range: ");
        int end = scanner.nextInt();
        List<Integer> found = findArmstrongInRange(start, end);
        System.out.println("Armstrong numbers between " + start + " and " + end + ": " + found);

        // --- Demo 5: Armstrong numbers by digit count ---
        System.out.println("\n=== Armstrong Numbers by Digit Count ===");
        for (int d = 1; d <= 7; d++) {
            findArmstrongByDigitCount(d);
        }

        // --- Demo 6: Non-Armstrong numbers with their sums ---
        System.out.println("\n=== Comparison Table (100-120) ===");
        System.out.printf("%-8s %-8s %-30s %-10s%n", "Number", "Digits", "Calculation", "Armstrong?");
        System.out.println("-".repeat(60));
        for (int i = 100; i <= 120; i++) {
            int digits = countDigits(i);
            int sum = 0;
            int temp = i;
            StringBuilder calc = new StringBuilder();
            while (temp > 0) {
                int digit = temp % 10;
                sum += power(digit, digits);
                if (calc.length() > 0) calc.insert(0, " + ");
                calc.insert(0, digit + "^" + digits);
                temp /= 10;
            }
            calc.append(" = ").append(sum);
            System.out.printf("%-8d %-8d %-30s %-10s%n", i, digits, calc.toString(), 
                              (sum == i) ? "YES" : "NO");
        }

        scanner.close();
    }
}
