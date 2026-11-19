/*
 * Program 24: Digit Sum Calculator
 * 
 * Problem Statement:
 *   Calculate the sum of digits of a given integer using iterative and
 *   recursive approaches. Also compute the digital root (repeatedly summing
 *   digits until a single digit is obtained). Handle negative numbers by
 *   working with the absolute value.
 * 
 * Sample Input:  12345
 * Sample Output:
 *   Sum of digits: 1 + 2 + 3 + 4 + 5 = 15
 *   Digital root:  15 → 1 + 5 = 6
 * 
 * Sample Input:  9999
 * Sample Output:
 *   Sum of digits: 9 + 9 + 9 + 9 = 36
 *   Digital root:  36 → 3 + 6 = 9
 * 
 * Explanation:
 *   - Digit Sum: Extract each digit using modulo 10, add to sum, divide by 10.
 *   - Digital Root: Repeatedly compute digit sum until result is a single digit.
 *   - Direct Formula: digitalRoot(n) = 1 + (n - 1) % 9 for n > 0 (O(1) formula).
 * 
 * Time Complexity:  O(d) where d = number of digits, O(1) for digital root formula
 * Space Complexity: O(1) iterative, O(d) recursive (call stack)
 */

import java.util.Scanner;

public class DigitSumCalculator {

    // --- Method 1: Iterative digit sum ---
    public static int digitSumIterative(int number) {
        number = Math.abs(number);
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    // --- Method 2: Recursive digit sum ---
    public static int digitSumRecursive(int number) {
        number = Math.abs(number);
        if (number < 10) return number; // Base case: single digit
        return (number % 10) + digitSumRecursive(number / 10);
    }

    // --- Method 3: Digit sum using String ---
    public static int digitSumString(int number) {
        String str = String.valueOf(Math.abs(number));
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += c - '0';
        }
        return sum;
    }

    // --- Method 4: Digit sum using Java Streams ---
    public static int digitSumStream(int number) {
        return String.valueOf(Math.abs(number))
                     .chars()
                     .map(c -> c - '0')
                     .sum();
    }

    // --- Method 5: Digital root (iterative) ---
    // Repeatedly sum digits until a single digit is obtained
    public static int digitalRootIterative(int number) {
        number = Math.abs(number);
        while (number >= 10) {
            number = digitSumIterative(number);
        }
        return number;
    }

    // --- Method 6: Digital root (recursive) ---
    public static int digitalRootRecursive(int number) {
        number = Math.abs(number);
        if (number < 10) return number;
        return digitalRootRecursive(digitSumIterative(number));
    }

    // --- Method 7: Digital root using O(1) formula ---
    // Mathematical property: digitalRoot(n) = 1 + (n - 1) % 9 for n > 0
    public static int digitalRootFormula(int number) {
        number = Math.abs(number);
        if (number == 0) return 0;
        return 1 + (number - 1) % 9;
    }

    // --- Method 8: Sum of digits raised to a power ---
    public static int digitPowerSum(int number, int power) {
        number = Math.abs(number);
        int sum = 0;
        while (number > 0) {
            int digit = number % 10;
            sum += (int) Math.pow(digit, power);
            number /= 10;
        }
        return sum;
    }

    // --- Method 9: Product of digits ---
    public static int digitProduct(int number) {
        number = Math.abs(number);
        if (number == 0) return 0;
        int product = 1;
        while (number > 0) {
            product *= number % 10;
            number /= 10;
        }
        return product;
    }

    // --- Method 10: Count specific digit occurrences ---
    public static int countDigitOccurrence(int number, int targetDigit) {
        number = Math.abs(number);
        if (number == 0 && targetDigit == 0) return 1;
        int count = 0;
        while (number > 0) {
            if (number % 10 == targetDigit) count++;
            number /= 10;
        }
        return count;
    }

    // --- Method 11: Find largest and smallest digit ---
    public static int[] findMinMaxDigit(int number) {
        number = Math.abs(number);
        int minDigit = 9, maxDigit = 0;
        while (number > 0) {
            int digit = number % 10;
            minDigit = Math.min(minDigit, digit);
            maxDigit = Math.max(maxDigit, digit);
            number /= 10;
        }
        return new int[]{minDigit, maxDigit};
    }

    // --- Method 12: Check if digit sum is divisible by number ---
    // A number is divisible by 3 if its digit sum is divisible by 3
    // A number is divisible by 9 if its digit sum is divisible by 9
    public static void divisibilityByDigitSum(int number) {
        int sum = digitSumIterative(number);
        System.out.println("  Digit sum of " + number + " = " + sum);
        System.out.println("  Divisible by 3? " + (sum % 3 == 0 ? "YES" : "NO") 
                           + " (digit sum " + (sum % 3 == 0 ? "is" : "is not") + " divisible by 3)");
        System.out.println("  Divisible by 9? " + (sum % 9 == 0 ? "YES" : "NO") 
                           + " (digit sum " + (sum % 9 == 0 ? "is" : "is not") + " divisible by 9)");
    }

    // --- Method 13: Explain digit sum step by step ---
    public static void explainDigitSum(int number) {
        int original = number;
        number = Math.abs(number);
        StringBuilder explanation = new StringBuilder();
        int sum = 0;

        int temp = number;
        boolean first = true;
        // Build the explanation from left to right
        String numStr = String.valueOf(number);
        for (char c : numStr.toCharArray()) {
            int digit = c - '0';
            sum += digit;
            if (!first) explanation.append(" + ");
            explanation.append(digit);
            first = false;
        }

        System.out.println("Digit sum of " + original + ": " + explanation + " = " + sum);
    }

    // --- Method 14: Explain digital root step by step ---
    public static void explainDigitalRoot(int number) {
        number = Math.abs(number);
        System.out.print("Digital root of " + number + ": ");
        StringBuilder steps = new StringBuilder();
        steps.append(number);

        while (number >= 10) {
            number = digitSumIterative(number);
            steps.append(" → ").append(number);
        }
        System.out.println(steps);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- Demo 1: Basic digit sum ---
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        System.out.println("\n=== Digit Sum of " + number + " ===");
        explainDigitSum(number);
        System.out.println("Iterative:  " + digitSumIterative(number));
        System.out.println("Recursive:  " + digitSumRecursive(number));
        System.out.println("String:     " + digitSumString(number));
        System.out.println("Stream:     " + digitSumStream(number));

        // --- Demo 2: Digital root ---
        System.out.println("\n=== Digital Root ===");
        explainDigitalRoot(Math.abs(number));
        System.out.println("Iterative:  " + digitalRootIterative(number));
        System.out.println("Recursive:  " + digitalRootRecursive(number));
        System.out.println("Formula:    " + digitalRootFormula(number));

        // --- Demo 3: Test with various numbers ---
        System.out.println("\n=== Digit Sum & Digital Root Table ===");
        int[] testCases = {0, 5, 10, 99, 123, 456, 999, 12345, 99999, 123456789};
        System.out.printf("%-12s %-10s %-14s %-12s%n", "Number", "Digit Sum", "Digital Root", "Digit Product");
        System.out.println("-".repeat(50));
        for (int test : testCases) {
            System.out.printf("%-12d %-10d %-14d %-12d%n", 
                              test, digitSumIterative(test), 
                              digitalRootFormula(test), digitProduct(test));
        }

        // --- Demo 4: Step-by-step digital root ---
        System.out.println("\n=== Digital Root Step-by-Step ===");
        int[] rootTests = {39, 942, 132189, 999999, 493193};
        for (int test : rootTests) {
            explainDigitalRoot(test);
        }

        // --- Demo 5: Digit power sums ---
        System.out.println("\n=== Digit Power Sums ===");
        System.out.println("Number 153:");
        System.out.println("  Sum of digits^1: " + digitPowerSum(153, 1));
        System.out.println("  Sum of digits^2: " + digitPowerSum(153, 2));
        System.out.println("  Sum of digits^3: " + digitPowerSum(153, 3) + " (Armstrong!)");

        // --- Demo 6: Min/Max digits ---
        System.out.println("\n=== Min & Max Digits ===");
        int[] minMaxTests = {12345, 90210, 55555, 918273};
        for (int test : minMaxTests) {
            int[] minMax = findMinMaxDigit(test);
            System.out.println(test + " → min digit: " + minMax[0] + ", max digit: " + minMax[1]);
        }

        // --- Demo 7: Digit occurrence count ---
        System.out.println("\n=== Digit Occurrence Count ===");
        int testNum = 1223334444;
        System.out.println("In number " + testNum + ":");
        for (int d = 0; d <= 9; d++) {
            int count = countDigitOccurrence(testNum, d);
            if (count > 0) {
                System.out.println("  Digit " + d + " appears " + count + " time(s)");
            }
        }

        // --- Demo 8: Divisibility rules ---
        System.out.println("\n=== Divisibility Rules via Digit Sum ===");
        int[] divTests = {123, 234, 999, 100, 111, 18, 27};
        for (int test : divTests) {
            divisibilityByDigitSum(test);
            System.out.println();
        }

        // --- Demo 9: Negative number handling ---
        System.out.println("=== Negative Numbers ===");
        int[] negTests = {-123, -456, -9999};
        for (int test : negTests) {
            System.out.println(test + " → digit sum: " + digitSumIterative(test) 
                               + ", digital root: " + digitalRootFormula(test));
        }

        scanner.close();
    }
}
