/*
 * Program 13: Relational and Logical Operators
 * 
 * Problem Statement:
 *   Write a Java program that demonstrates all relational operators (==, !=, >, <, >=, <=)
 *   and logical operators (&&, ||, !) with practical, real-world examples including:
 *   1. Basic relational comparisons between numbers
 *   2. Voting eligibility check (age >= 18)
 *   3. Loan approval (income, credit score, employment combined with &&, ||)
 *   4. Grade range validation using relational + logical operators
 *   5. Triangle validity checker
 *   6. Leap year determination
 *   7. Short-circuit evaluation demonstration
 *   8. Bitwise vs. Logical operator difference (&, | vs. &&, ||)
 * 
 * Sample Input:
 *   (No user input required — all examples use hardcoded values for clarity)
 * 
 * Sample Output:
 *   === RELATIONAL OPERATORS ===
 *   10 == 20 : false
 *   10 != 20 : true
 *   10 > 20  : false
 *   10 < 20  : true
 *   10 >= 10 : true
 *   10 <= 5  : false
 *   
 *   === VOTING ELIGIBILITY ===
 *   Age: 17 -> Eligible to vote? false
 *   Age: 18 -> Eligible to vote? true
 *   Age: 25 -> Eligible to vote? true
 *   
 *   === LOAN APPROVAL ===
 *   Income: 55000, Credit: 720, Employed: true -> Approved? true
 *   ...
 * 
 * Explanation:
 *   - Relational operators compare two values and return a boolean (true/false).
 *   - Logical operators combine boolean expressions:
 *       && (AND): true only if BOTH operands are true
 *       || (OR):  true if AT LEAST ONE operand is true
 *       !  (NOT): inverts the boolean value
 *   - Short-circuit evaluation: && stops at first false; || stops at first true.
 *     This is important for avoiding NullPointerExceptions and optimizing conditions.
 *   - Bitwise & and | always evaluate both sides (no short-circuit).
 * 
 * Time Complexity: O(1) — all operations are constant time comparisons
 * Space Complexity: O(1) — only a fixed number of boolean/int variables used
 */

public class RelationalLogicalOperators {

    // ==================== Helper Methods ====================

    /**
     * Checks voting eligibility based on age.
     */
    public static boolean isEligibleToVote(int age) {
        return age >= 18; // Relational operator: >=
    }

    /**
     * Checks loan approval based on multiple criteria using logical operators.
     * Approved if: (income >= 50000 AND creditScore >= 700) OR hasCollateral
     */
    public static boolean isLoanApproved(double income, int creditScore,
                                         boolean isEmployed, boolean hasCollateral) {
        // Combining relational and logical operators
        boolean meetsIncome = income >= 50000;        // relational >=
        boolean meetsCredit = creditScore >= 700;      // relational >=
        boolean basicEligible = meetsIncome && meetsCredit && isEmployed;  // logical &&

        // Approved if basic criteria met OR has collateral with decent credit
        return basicEligible || (hasCollateral && creditScore >= 600);  // logical ||, &&
    }

    /**
     * Determines letter grade from numeric score using relational operators.
     */
    public static char getGrade(int score) {
        if (score >= 90 && score <= 100) return 'A';      // AND: both conditions must be true
        else if (score >= 80 && score < 90) return 'B';
        else if (score >= 70 && score < 80) return 'C';
        else if (score >= 60 && score < 70) return 'D';
        else if (score >= 0 && score < 60) return 'F';
        else return 'X'; // Invalid score
    }

    /**
     * Checks if three sides can form a valid triangle.
     * Triangle Inequality Theorem: sum of any two sides > third side.
     */
    public static boolean isValidTriangle(double a, double b, double c) {
        return (a + b > c) && (b + c > a) && (a + c > b);  // All three must be true
    }

    /**
     * Determines if a year is a leap year.
     * Rule: divisible by 4 AND (not divisible by 100 OR divisible by 400)
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }

    // ==================== Main Method ====================

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║     RELATIONAL & LOGICAL OPERATORS DEMONSTRATION      ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        // ============================================================
        // SECTION 1: Relational Operators
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 1: RELATIONAL OPERATORS");
        System.out.println("═══════════════════════════════════════");

        int a = 10, b = 20;
        System.out.println("\nComparing a = " + a + " and b = " + b + ":");
        System.out.println("  a == b  : " + (a == b)  + "  (Equal to)");
        System.out.println("  a != b  : " + (a != b)  + "  (Not equal to)");
        System.out.println("  a > b   : " + (a > b)   + "  (Greater than)");
        System.out.println("  a < b   : " + (a < b)   + "  (Less than)");
        System.out.println("  a >= b  : " + (a >= b)  + "  (Greater than or equal to)");
        System.out.println("  a <= b  : " + (a <= b)  + "  (Less than or equal to)");

        // Edge case: comparing equal values
        int c = 10;
        System.out.println("\nComparing a = " + a + " and c = " + c + " (equal values):");
        System.out.println("  a == c  : " + (a == c));
        System.out.println("  a >= c  : " + (a >= c) + "  (true because a equals c)");
        System.out.println("  a <= c  : " + (a <= c) + "  (true because a equals c)");
        System.out.println("  a > c   : " + (a > c)  + "  (false because a equals c, not greater)");

        // ============================================================
        // SECTION 2: Logical Operators Truth Table
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 2: LOGICAL OPERATORS TRUTH TABLE");
        System.out.println("═══════════════════════════════════════");

        boolean[] values = {true, false};
        System.out.println("\n  P     | Q     | P && Q | P || Q | !P    | !Q");
        System.out.println("  ------|-------|--------|--------|-------|------");
        for (boolean p : values) {
            for (boolean q : values) {
                System.out.printf("  %-5s | %-5s | %-6s | %-6s | %-5s | %-5s%n",
                        p, q, (p && q), (p || q), (!p), (!q));
            }
        }

        // ============================================================
        // SECTION 3: Voting Eligibility Check
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 3: VOTING ELIGIBILITY CHECK");
        System.out.println("═══════════════════════════════════════");

        int[] ages = {15, 17, 18, 21, 65};
        for (int age : ages) {
            boolean eligible = isEligibleToVote(age);
            System.out.printf("  Age: %-3d -> Eligible to vote? %-5s %s%n",
                    age, eligible, eligible ? "✓ Can vote!" : "✗ Too young");
        }

        // ============================================================
        // SECTION 4: Loan Approval (Complex Conditions)
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 4: LOAN APPROVAL SYSTEM");
        System.out.println("═══════════════════════════════════════");
        System.out.println("  Rule: (income >= 50K AND credit >= 700 AND employed)");
        System.out.println("        OR (hasCollateral AND credit >= 600)\n");

        // Test cases: income, creditScore, employed, hasCollateral
        Object[][] loanCases = {
                {55000.0, 720, true,  false},
                {40000.0, 750, true,  false},
                {55000.0, 650, true,  false},
                {30000.0, 620, false, true},
                {25000.0, 500, false, false},
                {80000.0, 800, true,  true},
        };

        for (Object[] lc : loanCases) {
            double income = (double) lc[0];
            int credit = (int) lc[1];
            boolean employed = (boolean) lc[2];
            boolean collateral = (boolean) lc[3];
            boolean approved = isLoanApproved(income, credit, employed, collateral);

            System.out.printf("  Income: $%,.0f, Credit: %d, Employed: %-5s, Collateral: %-5s -> %s%n",
                    income, credit, employed, collateral,
                    approved ? "APPROVED ✓" : "DENIED ✗");
        }

        // ============================================================
        // SECTION 5: Grade Range Validation
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 5: GRADE RANGE VALIDATION");
        System.out.println("═══════════════════════════════════════");

        int[] scores = {95, 87, 73, 65, 42, 100, 0, -5};
        for (int score : scores) {
            char grade = getGrade(score);
            boolean isValid = score >= 0 && score <= 100;  // range check with &&
            boolean isPassing = isValid && score >= 60;     // chaining conditions

            System.out.printf("  Score: %3d -> Grade: %c | Valid: %-5s | Passing: %-5s%n",
                    score, grade, isValid, isPassing);
        }

        // ============================================================
        // SECTION 6: Triangle Validity Checker
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 6: TRIANGLE VALIDITY CHECKER");
        System.out.println("═══════════════════════════════════════");
        System.out.println("  Triangle Inequality: sum of any two sides > third side\n");

        double[][] triangles = {{3, 4, 5}, {1, 2, 3}, {5, 5, 5}, {1, 1, 10}, {7, 10, 5}};
        for (double[] t : triangles) {
            boolean valid = isValidTriangle(t[0], t[1], t[2]);
            System.out.printf("  Sides (%.0f, %.0f, %.0f): %s%n",
                    t[0], t[1], t[2], valid ? "VALID triangle ✓" : "INVALID triangle ✗");
            if (valid) {
                // Check type of triangle using relational operators
                boolean equilateral = (t[0] == t[1]) && (t[1] == t[2]);
                boolean isosceles = (t[0] == t[1]) || (t[1] == t[2]) || (t[0] == t[2]);
                String type = equilateral ? "Equilateral" : (isosceles ? "Isosceles" : "Scalene");
                System.out.println("    Type: " + type);
            }
        }

        // ============================================================
        // SECTION 7: Leap Year Determination
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 7: LEAP YEAR DETERMINATION");
        System.out.println("═══════════════════════════════════════");
        System.out.println("  Rule: (year%4==0) AND (year%100!=0 OR year%400==0)\n");

        int[] years = {2000, 1900, 2024, 2023, 2100, 1600, 2025};
        for (int year : years) {
            boolean leap = isLeapYear(year);
            System.out.printf("  %d: %s", year, leap ? "LEAP YEAR  ✓" : "NOT leap   ✗");
            // Show why
            System.out.printf("  [4:%s, 100:%s, 400:%s]%n",
                    year % 4 == 0 ? "Y" : "N",
                    year % 100 == 0 ? "Y" : "N",
                    year % 400 == 0 ? "Y" : "N");
        }

        // ============================================================
        // SECTION 8: Short-Circuit Evaluation
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 8: SHORT-CIRCUIT EVALUATION");
        System.out.println("═══════════════════════════════════════");

        System.out.println("\n  Short-circuit AND (&&):");
        System.out.println("  If first operand is FALSE, second is NOT evaluated.");

        int x = 0;
        // Without short-circuit, this would throw ArithmeticException
        boolean safeCheck = (x != 0) && (10 / x > 1);
        System.out.println("  x = 0: (x != 0) && (10/x > 1) = " + safeCheck);
        System.out.println("  10/x was NOT evaluated because (x != 0) was false!");

        System.out.println("\n  Short-circuit OR (||):");
        System.out.println("  If first operand is TRUE, second is NOT evaluated.");

        boolean hasDefault = true;
        boolean checkExpensive = hasDefault || expensiveCheck();
        System.out.println("  hasDefault || expensiveCheck() = " + checkExpensive);
        System.out.println("  expensiveCheck() was NOT called because hasDefault was true!");

        // Demonstrate the difference with non-short-circuit operators
        System.out.println("\n  Bitwise vs. Logical Operators:");
        System.out.println("  &  (bitwise AND): Always evaluates BOTH sides");
        System.out.println("  && (logical AND): Short-circuits on first false");
        System.out.println("  |  (bitwise OR):  Always evaluates BOTH sides");
        System.out.println("  || (logical OR):  Short-circuits on first true");

        // Safe null check example
        System.out.println("\n  Practical use — Safe null check:");
        String str = null;
        boolean isSafe = (str != null) && (str.length() > 0);
        System.out.println("  str = null: (str != null) && (str.length() > 0) = " + isSafe);
        System.out.println("  str.length() was NOT called — NullPointerException avoided!");

        // ============================================================
        // SECTION 9: Combining Everything — Age Category Classifier
        // ============================================================
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  SECTION 9: AGE CATEGORY CLASSIFIER");
        System.out.println("═══════════════════════════════════════");

        int[] testAges = {0, 5, 12, 13, 17, 18, 25, 59, 60, 85};
        for (int age : testAges) {
            String category;
            if (age >= 0 && age <= 4) {
                category = "Infant/Toddler";
            } else if (age >= 5 && age <= 12) {
                category = "Child";
            } else if (age >= 13 && age <= 17) {
                category = "Teenager";
            } else if (age >= 18 && age <= 59) {
                category = "Adult";
            } else if (age >= 60) {
                category = "Senior";
            } else {
                category = "Invalid";
            }

            boolean canVote = age >= 18;
            boolean canDrive = age >= 16;
            boolean canDrink = age >= 21;

            System.out.printf("  Age %-3d: %-15s | Vote:%-5s | Drive:%-5s | Drink:%-5s%n",
                    age, category, canVote, canDrive, canDrink);
        }

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║           All demonstrations completed!                ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }

    /**
     * Simulates an expensive computation to demonstrate short-circuit evaluation.
     */
    public static boolean expensiveCheck() {
        System.out.println("  [expensiveCheck() was called — this should NOT appear!]");
        return true;
    }
}
