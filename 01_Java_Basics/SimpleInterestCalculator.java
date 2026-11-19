/*
 * Program 07: Simple Interest Calculator
 * 
 * Problem Statement:
 *   Calculate Simple Interest given the principal amount (P), annual rate of
 *   interest (R), and time period in years (T).
 *   Formula: SI = (P × R × T) / 100
 *   Also calculate the total amount: A = P + SI
 * 
 * Sample Input:
 *   Enter Principal Amount (P): 10000
 *   Enter Annual Interest Rate (R) in %: 8.5
 *   Enter Time Period (T) in years: 3
 * 
 * Sample Output:
 *   ========================================
 *      SIMPLE INTEREST CALCULATOR
 *   ========================================
 *   Principal Amount (P) : $10,000.00
 *   Annual Rate (R)      : 8.50%
 *   Time Period (T)      : 3.0 years
 *   ----------------------------------------
 *   Simple Interest (SI) : $2,550.00
 *   Total Amount (A=P+SI): $12,550.00
 *   ========================================
 *   
 *   --- Year-by-Year Breakdown ---
 *   Year 1: Interest = $850.00  | Cumulative = $850.00   | Balance = $10,850.00
 *   Year 2: Interest = $850.00  | Cumulative = $1,700.00 | Balance = $11,700.00
 *   Year 3: Interest = $850.00  | Cumulative = $2,550.00 | Balance = $12,550.00
 * 
 * Explanation:
 *   Simple Interest (SI) is calculated as SI = (P * R * T) / 100, where:
 *   - P = Principal (initial amount of money)
 *   - R = Annual interest rate (as a percentage)
 *   - T = Time period (in years)
 *   Unlike compound interest, simple interest is computed only on the original
 *   principal. The interest earned each year remains constant.
 * 
 * Time Complexity:  O(T) - iterates through each year for the breakdown
 * Space Complexity: O(1) - constant extra space
 */

import java.util.Scanner;

public class SimpleInterestCalculator {

    /**
     * Calculates Simple Interest.
     * @param principal The initial amount of money
     * @param rate      Annual interest rate (percentage)
     * @param time      Time period in years
     * @return The simple interest amount
     */
    public static double calculateSimpleInterest(double principal, double rate, double time) {
        return (principal * rate * time) / 100.0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("    SIMPLE INTEREST CALCULATOR");
        System.out.println("========================================");

        // Input
        System.out.print("Enter Principal Amount (P): ");
        double principal = scanner.nextDouble();

        System.out.print("Enter Annual Interest Rate (R) in %: ");
        double rate = scanner.nextDouble();

        System.out.print("Enter Time Period (T) in years: ");
        double time = scanner.nextDouble();

        // Validate inputs
        if (principal < 0) {
            System.out.println("Error: Principal amount cannot be negative.");
            scanner.close();
            return;
        }
        if (rate < 0) {
            System.out.println("Error: Interest rate cannot be negative.");
            scanner.close();
            return;
        }
        if (time < 0) {
            System.out.println("Error: Time period cannot be negative.");
            scanner.close();
            return;
        }

        // Calculate
        double simpleInterest = calculateSimpleInterest(principal, rate, time);
        double totalAmount = principal + simpleInterest;

        // Display results
        System.out.println();
        System.out.printf("Principal Amount (P) : $%,.2f%n", principal);
        System.out.printf("Annual Rate (R)      : %.2f%%%n", rate);
        System.out.printf("Time Period (T)      : %.1f years%n", time);
        System.out.println("----------------------------------------");
        System.out.printf("Simple Interest (SI) : $%,.2f%n", simpleInterest);
        System.out.printf("Total Amount (A=P+SI): $%,.2f%n", totalAmount);
        System.out.println("========================================");

        // Year-by-year breakdown
        System.out.println();
        System.out.println("--- Year-by-Year Breakdown ---");
        double yearlyInterest = (principal * rate) / 100.0;
        double cumulativeInterest = 0;
        int fullYears = (int) Math.ceil(time);

        for (int year = 1; year <= fullYears; year++) {
            double interestThisYear;
            if (year <= (int) time) {
                interestThisYear = yearlyInterest;
            } else {
                // Partial year (fractional part)
                double fractionalPart = time - (int) time;
                interestThisYear = yearlyInterest * fractionalPart;
            }
            cumulativeInterest += interestThisYear;
            double balance = principal + cumulativeInterest;
            System.out.printf("Year %d: Interest = $%,.2f  | Cumulative = $%,.2f | Balance = $%,.2f%n",
                    year, interestThisYear, cumulativeInterest, balance);
        }

        // --- Comparison Table for Different Rates ---
        System.out.println();
        System.out.println("--- Comparison: Different Rates (same P and T) ---");
        System.out.println("+--------+----------------+----------------+");
        System.out.println("|  Rate  |    Interest     |  Total Amount  |");
        System.out.println("+--------+----------------+----------------+");
        double[] rates = {5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 12.0, 15.0};
        for (double r : rates) {
            double si = calculateSimpleInterest(principal, r, time);
            System.out.printf("| %5.1f%% | $%,13.2f | $%,13.2f |%n", r, si, principal + si);
        }
        System.out.println("+--------+----------------+----------------+");

        System.out.println();
        System.out.println("========================================");
        System.out.println("  SIMPLE INTEREST CALCULATOR COMPLETED");
        System.out.println("========================================");

        scanner.close();
    }
}
