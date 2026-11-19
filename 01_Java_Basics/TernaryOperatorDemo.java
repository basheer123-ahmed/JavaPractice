/*
 * Program 10: Ternary Operator Demo
 * 
 * Problem Statement:
 *   Demonstrate the ternary (conditional) operator in Java with multiple
 *   practical examples:
 *   1. Finding the maximum of two numbers
 *   2. Checking if a number is even or odd
 *   3. Assigning letter grades based on a score
 *   4. Absolute value calculation
 *   5. Eligibility checks
 *   6. Nested ternary for maximum of three numbers
 * 
 * Sample Input:
 *   Enter a number: 42
 *   Enter another number: 17
 *   Enter your score (0-100): 85
 *   Enter your age: 20
 * 
 * Sample Output:
 *   ========================================
 *      TERNARY OPERATOR DEMONSTRATION
 *   ========================================
 *   
 *   --- 1. Max of Two Numbers ---
 *   a = 42, b = 17
 *   Maximum: 42
 *   
 *   --- 2. Even or Odd Check ---
 *   42 is Even
 *   17 is Odd
 *   
 *   --- 3. Grade Assignment ---
 *   Score: 85 -> Grade: B
 *   
 *   --- 4. Absolute Value ---
 *   Absolute value of -15 is 15
 *   
 *   --- 5. Voting Eligibility ---
 *   Age 20: Eligible to vote
 *   ========================================
 * 
 * Explanation:
 *   The ternary operator is a shorthand for if-else:
 *     Syntax: condition ? valueIfTrue : valueIfFalse
 *   
 *   It is an EXPRESSION (returns a value), not a statement, making it useful
 *   for inline assignments and return values. Nested ternary operators are
 *   possible but should be used sparingly for readability.
 *   
 *   Equivalent:
 *     int max = (a > b) ? a : b;
 *     // Same as:
 *     int max;
 *     if (a > b) { max = a; } else { max = b; }
 * 
 * Time Complexity:  O(1) - all operations are constant time
 * Space Complexity: O(1) - fixed number of variables
 */

import java.util.Scanner;

public class TernaryOperatorDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("   TERNARY OPERATOR DEMONSTRATION");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Syntax: condition ? valueIfTrue : valueIfFalse");
        System.out.println();

        // --- Get user input ---
        System.out.print("Enter first number (a): ");
        int a = scanner.nextInt();

        System.out.print("Enter second number (b): ");
        int b = scanner.nextInt();

        System.out.print("Enter your score (0-100): ");
        int score = scanner.nextInt();

        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        System.out.println();

        // ============================================================
        // 1. Maximum of Two Numbers
        // ============================================================
        System.out.println("--- 1. Maximum of Two Numbers ---");
        int max = (a > b) ? a : b;
        int min = (a < b) ? a : b;
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Maximum: (" + a + " > " + b + ") ? " + a + " : " + b + " = " + max);
        System.out.println("Minimum: (" + a + " < " + b + ") ? " + a + " : " + b + " = " + min);
        System.out.println();

        // ============================================================
        // 2. Even or Odd Check
        // ============================================================
        System.out.println("--- 2. Even or Odd Check ---");
        String resultA = (a % 2 == 0) ? "Even" : "Odd";
        String resultB = (b % 2 == 0) ? "Even" : "Odd";
        System.out.println(a + " is " + resultA);
        System.out.println(b + " is " + resultB);
        System.out.println("Expression: (num % 2 == 0) ? \"Even\" : \"Odd\"");
        System.out.println();

        // ============================================================
        // 3. Grade Assignment (Nested Ternary)
        // ============================================================
        System.out.println("--- 3. Grade Assignment (Nested Ternary) ---");
        String grade = (score >= 90) ? "A" :
                        (score >= 80) ? "B" :
                        (score >= 70) ? "C" :
                        (score >= 60) ? "D" : "F";
        System.out.println("Score: " + score + " -> Grade: " + grade);
        System.out.println();
        System.out.println("Equivalent nested ternary:");
        System.out.println("  (score >= 90) ? \"A\" :");
        System.out.println("  (score >= 80) ? \"B\" :");
        System.out.println("  (score >= 70) ? \"C\" :");
        System.out.println("  (score >= 60) ? \"D\" : \"F\"");
        System.out.println();
        System.out.println("Note: Deeply nested ternary hurts readability.");
        System.out.println("      Prefer if-else or switch for complex conditions.");
        System.out.println();

        // ============================================================
        // 4. Absolute Value
        // ============================================================
        System.out.println("--- 4. Absolute Value ---");
        int negNum = -15;
        int absValue = (negNum < 0) ? -negNum : negNum;
        System.out.println("Number: " + negNum);
        System.out.println("Absolute value: (" + negNum + " < 0) ? " + (-negNum) + " : " + negNum + " = " + absValue);
        System.out.println("Math.abs(" + negNum + ") = " + Math.abs(negNum) + " (built-in)");
        System.out.println();

        // ============================================================
        // 5. Voting Eligibility
        // ============================================================
        System.out.println("--- 5. Voting Eligibility ---");
        String eligibility = (age >= 18) ? "Eligible to vote" : "Not eligible to vote";
        System.out.println("Age " + age + ": " + eligibility);
        System.out.println();

        // ============================================================
        // 6. Positive / Negative / Zero
        // ============================================================
        System.out.println("--- 6. Sign Check (Nested Ternary) ---");
        int[] testValues = {-5, 0, 7};
        for (int val : testValues) {
            String sign = (val > 0) ? "Positive" : (val < 0) ? "Negative" : "Zero";
            System.out.println(val + " is " + sign);
        }
        System.out.println();

        // ============================================================
        // 7. Max of Three Numbers (Nested Ternary)
        // ============================================================
        System.out.println("--- 7. Maximum of Three Numbers ---");
        int c = a + b;  // Use a third number for demo
        int maxOfThree = (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
        System.out.println("a = " + a + ", b = " + b + ", c = " + c);
        System.out.println("Maximum of three: " + maxOfThree);
        System.out.println("Expression: (a>b) ? ((a>c) ? a : c) : ((b>c) ? b : c)");
        System.out.println();

        // ============================================================
        // 8. Leap Year Check
        // ============================================================
        System.out.println("--- 8. Leap Year Check ---");
        int year = 2024;
        boolean isLeap = (year % 400 == 0) ? true :
                          (year % 100 == 0) ? false :
                          (year % 4 == 0) ? true : false;
        System.out.println(year + " is " + (isLeap ? "a Leap Year" : "NOT a Leap Year"));
        year = 1900;
        isLeap = (year % 400 == 0) ? true :
                 (year % 100 == 0) ? false :
                 (year % 4 == 0) ? true : false;
        System.out.println(year + " is " + (isLeap ? "a Leap Year" : "NOT a Leap Year"));
        year = 2000;
        isLeap = (year % 400 == 0) ? true :
                 (year % 100 == 0) ? false :
                 (year % 4 == 0) ? true : false;
        System.out.println(year + " is " + (isLeap ? "a Leap Year" : "NOT a Leap Year"));
        System.out.println();

        // ============================================================
        // 9. Ternary vs If-Else Comparison
        // ============================================================
        System.out.println("--- 9. Ternary vs If-Else ---");
        System.out.println();
        System.out.println("TERNARY (1 line):");
        System.out.println("  String status = (age >= 18) ? \"Adult\" : \"Minor\";");
        System.out.println();
        System.out.println("IF-ELSE (5 lines):");
        System.out.println("  String status;");
        System.out.println("  if (age >= 18) {");
        System.out.println("      status = \"Adult\";");
        System.out.println("  } else {");
        System.out.println("      status = \"Minor\";");
        System.out.println("  }");
        System.out.println();
        System.out.println("WHEN TO USE TERNARY:");
        System.out.println("  ✓ Simple two-way decisions");
        System.out.println("  ✓ Inline variable initialization");
        System.out.println("  ✓ Return values in methods");
        System.out.println("  ✗ Complex logic with side effects");
        System.out.println("  ✗ Deeply nested conditions (use if-else instead)");

        System.out.println();
        System.out.println("========================================");
        System.out.println("  TERNARY OPERATOR DEMO COMPLETED");
        System.out.println("========================================");

        scanner.close();
    }
}
