/*
 * Program 08: Compound Interest Calculator
 * 
 * Problem Statement:
 *   Calculate Compound Interest with different compounding frequencies.
 *   Formula: A = P × (1 + R/(n×100))^(n×T)
 *   Where:
 *     A = Final amount
 *     P = Principal (initial investment)
 *     R = Annual interest rate (percentage)
 *     n = Number of times interest is compounded per year
 *     T = Time in years
 *   CI = A - P
 *
 *   Support compounding frequencies: Annually, Semi-Annually, Quarterly,
 *   Monthly, Daily, and Continuously.
 * 
 * Sample Input:
 *   Enter Principal Amount (P): 10000
 *   Enter Annual Interest Rate (R) in %: 8
 *   Enter Time Period (T) in years: 5
 * 
 * Sample Output:
 *   ========================================
 *     COMPOUND INTEREST CALCULATOR
 *   ========================================
 *   Principal: $10,000.00 | Rate: 8.00% | Time: 5 years
 *   ----------------------------------------
 *   Compounding       |  Final Amount  |   Interest
 *   ----------------------------------------
 *   Annually    (n=1) | $14,693.28     | $4,693.28
 *   Semi-Annual (n=2) | $14,802.44     | $4,802.44
 *   Quarterly   (n=4) | $14,859.47     | $4,859.47
 *   Monthly    (n=12) | $14,898.46     | $4,898.46
 *   Daily     (n=365) | $14,918.25     | $4,918.25
 *   Continuously      | $14,918.25     | $4,918.25
 *   ----------------------------------------
 *   Simple Interest    | $14,000.00     | $4,000.00
 *   ========================================
 * 
 * Explanation:
 *   Compound interest earns "interest on interest" - each period's interest
 *   is added to the principal before computing the next period's interest.
 *   More frequent compounding leads to higher returns. The limit of infinitely
 *   frequent compounding is continuous compounding: A = P × e^(R×T/100).
 *   The difference between compound and simple interest grows significantly
 *   over longer time periods (the "power of compounding").
 * 
 * Time Complexity:  O(T) for year-by-year breakdown
 * Space Complexity: O(1) - constant extra space
 */

import java.util.Scanner;

public class CompoundInterestCalculator {

    /**
     * Calculates compound interest with discrete compounding.
     * A = P * (1 + r/(n*100))^(n*t)
     */
    public static double calculateCompoundAmount(double principal, double rate, double time, int compoundsPerYear) {
        return principal * Math.pow(1 + rate / (compoundsPerYear * 100.0), compoundsPerYear * time);
    }

    /**
     * Calculates continuously compounded amount.
     * A = P * e^(r*t/100)
     */
    public static double calculateContinuousAmount(double principal, double rate, double time) {
        return principal * Math.exp(rate * time / 100.0);
    }

    /**
     * Calculates simple interest amount for comparison.
     * A = P * (1 + r*t/100)
     */
    public static double calculateSimpleAmount(double principal, double rate, double time) {
        return principal * (1 + rate * time / 100.0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("   COMPOUND INTEREST CALCULATOR");
        System.out.println("========================================");

        // Input
        System.out.print("Enter Principal Amount (P): ");
        double principal = scanner.nextDouble();

        System.out.print("Enter Annual Interest Rate (R) in %: ");
        double rate = scanner.nextDouble();

        System.out.print("Enter Time Period (T) in years: ");
        double time = scanner.nextDouble();

        // Validate inputs
        if (principal <= 0 || rate < 0 || time <= 0) {
            System.out.println("Error: Please enter positive values for principal and time, non-negative rate.");
            scanner.close();
            return;
        }

        System.out.println();
        System.out.printf("Principal: $%,.2f | Rate: %.2f%% | Time: %.0f years%n", principal, rate, time);
        System.out.println("============================================================");

        // Calculate for different compounding frequencies
        String[] labels = {"Annually    (n=1) ", "Semi-Annual (n=2) ", "Quarterly   (n=4) ",
                           "Monthly    (n=12) ", "Daily     (n=365) "};
        int[] frequencies = {1, 2, 4, 12, 365};

        System.out.printf("%-22s | %14s | %14s%n", "Compounding", "Final Amount", "Interest");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < frequencies.length; i++) {
            double amount = calculateCompoundAmount(principal, rate, time, frequencies[i]);
            double interest = amount - principal;
            System.out.printf("%-22s | $%,13.2f | $%,13.2f%n", labels[i], amount, interest);
        }

        // Continuous compounding
        double continuousAmount = calculateContinuousAmount(principal, rate, time);
        double continuousInterest = continuousAmount - principal;
        System.out.printf("%-22s | $%,13.2f | $%,13.2f%n", "Continuously         ", continuousAmount, continuousInterest);

        System.out.println("------------------------------------------------------------");

        // Simple interest for comparison
        double simpleAmount = calculateSimpleAmount(principal, rate, time);
        double simpleInterest = simpleAmount - principal;
        System.out.printf("%-22s | $%,13.2f | $%,13.2f%n", "Simple Interest      ", simpleAmount, simpleInterest);

        System.out.println("============================================================");

        // Year-by-year growth (using annual compounding)
        System.out.println();
        System.out.println("--- Year-by-Year Growth (Annual Compounding) ---");
        System.out.printf("%-6s | %14s | %14s | %14s%n", "Year", "Balance", "Interest", "Cumulative CI");
        System.out.println("------------------------------------------------------------");

        double balance = principal;
        double totalInterest = 0;

        for (int year = 1; year <= (int) time; year++) {
            double yearInterest = balance * rate / 100.0;
            balance += yearInterest;
            totalInterest += yearInterest;
            System.out.printf("%-6d | $%,13.2f | $%,13.2f | $%,13.2f%n",
                    year, balance, yearInterest, totalInterest);
        }

        // The Power of Compounding - comparison over time
        System.out.println();
        System.out.println("--- The Power of Compounding Over Time ---");
        System.out.printf("%-8s | %16s | %16s | %10s%n", "Years", "Simple Interest", "Compound (Annual)", "Difference");
        System.out.println("------------------------------------------------------------");

        int[] years = {1, 5, 10, 20, 30, 50};
        for (int y : years) {
            double si = calculateSimpleAmount(principal, rate, y);
            double ci = calculateCompoundAmount(principal, rate, y, 1);
            double diff = ci - si;
            System.out.printf("%-8d | $%,15.2f | $%,15.2f | $%,9.2f%n", y, si, ci, diff);
        }

        // Rule of 72
        System.out.println();
        System.out.println("--- Rule of 72 ---");
        double doublingTime = 72.0 / rate;
        System.out.printf("At %.2f%% interest, your money approximately doubles in %.1f years.%n", rate, doublingTime);
        double actualDoubling = Math.log(2) / Math.log(1 + rate / 100.0);
        System.out.printf("Exact doubling time: %.2f years%n", actualDoubling);

        System.out.println();
        System.out.println("========================================");
        System.out.println("  COMPOUND INTEREST CALC COMPLETED");
        System.out.println("========================================");

        scanner.close();
    }
}
