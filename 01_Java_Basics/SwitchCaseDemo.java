/*
 * Program 12: Switch-Case Demo — Menu-Driven Multi-Tool
 * 
 * Problem Statement:
 *   Write a menu-driven Java program using switch-case that combines three utilities:
 *   1. Day of Week Finder — Given a number (1-7), display the corresponding day name,
 *      whether it's a weekday or weekend, and its position in the week.
 *   2. Month Days Finder — Given a month number (1-12) and a year, display the
 *      month name, number of days (accounting for leap years), and the quarter.
 *   3. Simple Calculator — Perform basic arithmetic (+, -, *, /, %) on two numbers
 *      with proper error handling for division by zero.
 *   The program should loop until the user chooses to exit.
 * 
 * Sample Input:
 *   Choice: 1
 *   Day number: 3
 *   ---
 *   Choice: 2
 *   Month: 2, Year: 2024
 *   ---
 *   Choice: 3
 *   First number: 15, Operator: /, Second number: 4
 *   ---
 *   Choice: 4 (Exit)
 * 
 * Sample Output:
 *   === DAY OF WEEK FINDER ===
 *   Day 3 is: WEDNESDAY
 *   Type: Weekday
 *   Position: 3rd day of the week (Mid-week)
 *   
 *   === MONTH DAYS FINDER ===
 *   Month 2 is: FEBRUARY
 *   Year 2024 is a Leap Year
 *   Number of days: 29
 *   Quarter: Q1 (January - March)
 *   
 *   === SIMPLE CALCULATOR ===
 *   15.0 / 4.0 = 3.75
 *   
 *   Goodbye! Thank you for using the Multi-Tool.
 * 
 * Explanation:
 *   - The switch statement evaluates an expression and executes the matching case block.
 *   - Each case must end with 'break' to prevent fall-through (executing subsequent cases).
 *   - The 'default' case handles values that don't match any defined case.
 *   - Java 14+ enhanced switch (arrow syntax) is also demonstrated as a comparison.
 *   - A do-while loop keeps the menu running until the user selects exit.
 *   - Leap year rule: divisible by 4, but not by 100 unless also divisible by 400.
 * 
 * Time Complexity: O(1) per operation — each switch lookup is constant time
 * Space Complexity: O(1) — only a fixed number of variables used
 */

import java.util.Scanner;

public class SwitchCaseDemo {

    // ===================== UTILITY 1: Day of Week Finder =====================
    public static void dayOfWeekFinder(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       DAY OF WEEK FINDER             ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.print("Enter day number (1-7, where 1=Monday): ");
        int day = scanner.nextInt();

        String dayName;
        String dayType;
        String position;

        switch (day) {
            case 1:
                dayName = "MONDAY";
                dayType = "Weekday";
                position = "1st day of the week (Start of work week)";
                break;
            case 2:
                dayName = "TUESDAY";
                dayType = "Weekday";
                position = "2nd day of the week";
                break;
            case 3:
                dayName = "WEDNESDAY";
                dayType = "Weekday";
                position = "3rd day of the week (Mid-week)";
                break;
            case 4:
                dayName = "THURSDAY";
                dayType = "Weekday";
                position = "4th day of the week";
                break;
            case 5:
                dayName = "FRIDAY";
                dayType = "Weekday";
                position = "5th day of the week (TGIF!)";
                break;
            case 6:
                dayName = "SATURDAY";
                dayType = "Weekend";
                position = "6th day of the week (Weekend begins)";
                break;
            case 7:
                dayName = "SUNDAY";
                dayType = "Weekend";
                position = "7th day of the week (Rest day)";
                break;
            default:
                System.out.println("ERROR: Invalid day number! Please enter 1-7.");
                return;
        }

        System.out.println("\nDay " + day + " is: " + dayName);
        System.out.println("Type: " + dayType);
        System.out.println("Position: " + position);

        // Bonus: Enhanced switch expression (Java 14+) — same logic, cleaner syntax
        System.out.println("\n--- Enhanced Switch (Java 14+) equivalent ---");
        String enhancedResult = switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            case 5 -> "Friday";
            case 6, 7 -> (day == 6 ? "Saturday" : "Sunday") + " (Weekend!)";
            default -> "Invalid";
        };
        System.out.println("Enhanced switch result: " + enhancedResult);
    }

    // ===================== UTILITY 2: Month Days Finder =====================
    public static void monthDaysFinder(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        MONTH DAYS FINDER             ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.print("Enter month number (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();

        String monthName;
        int numberOfDays;
        String quarter;
        String season;

        // Determine month name using switch
        switch (month) {
            case 1:  monthName = "JANUARY";   break;
            case 2:  monthName = "FEBRUARY";  break;
            case 3:  monthName = "MARCH";     break;
            case 4:  monthName = "APRIL";     break;
            case 5:  monthName = "MAY";       break;
            case 6:  monthName = "JUNE";      break;
            case 7:  monthName = "JULY";      break;
            case 8:  monthName = "AUGUST";    break;
            case 9:  monthName = "SEPTEMBER"; break;
            case 10: monthName = "OCTOBER";   break;
            case 11: monthName = "NOVEMBER";  break;
            case 12: monthName = "DECEMBER";  break;
            default:
                System.out.println("ERROR: Invalid month number! Please enter 1-12.");
                return;
        }

        // Determine number of days using switch with fall-through grouping
        switch (month) {
            case 4: case 6: case 9: case 11:
                // April, June, September, November — 30 days
                numberOfDays = 30;
                break;
            case 2:
                // February — check for leap year
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                numberOfDays = isLeapYear ? 29 : 28;
                System.out.println("Year " + year + " is " +
                        (isLeapYear ? "a Leap Year" : "NOT a Leap Year"));
                break;
            default:
                // January, March, May, July, August, October, December — 31 days
                numberOfDays = 31;
                break;
        }

        // Determine quarter using switch
        switch (month) {
            case 1: case 2: case 3:
                quarter = "Q1 (January - March)";
                break;
            case 4: case 5: case 6:
                quarter = "Q2 (April - June)";
                break;
            case 7: case 8: case 9:
                quarter = "Q3 (July - September)";
                break;
            default:
                quarter = "Q4 (October - December)";
                break;
        }

        // Determine season (Northern Hemisphere)
        season = switch (month) {
            case 12, 1, 2 -> "Winter";
            case 3, 4, 5  -> "Spring";
            case 6, 7, 8  -> "Summer";
            case 9, 10, 11 -> "Autumn/Fall";
            default -> "Unknown";
        };

        System.out.println("\nMonth " + month + " is: " + monthName);
        System.out.println("Number of days: " + numberOfDays);
        System.out.println("Quarter: " + quarter);
        System.out.println("Season (Northern Hemisphere): " + season);
    }

    // ===================== UTILITY 3: Simple Calculator =====================
    public static void simpleCalculator(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        SIMPLE CALCULATOR             ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        System.out.print("Enter operator (+, -, *, /, %): ");
        char operator = scanner.next().charAt(0);

        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        double result;
        String operation;

        switch (operator) {
            case '+':
                result = num1 + num2;
                operation = "Addition";
                break;
            case '-':
                result = num1 - num2;
                operation = "Subtraction";
                break;
            case '*':
                result = num1 * num2;
                operation = "Multiplication";
                break;
            case '/':
                if (num2 == 0) {
                    System.out.println("\nERROR: Division by zero is undefined!");
                    System.out.println("Mathematical rule: Any number divided by 0 is undefined.");
                    return;
                }
                result = num1 / num2;
                operation = "Division";
                break;
            case '%':
                if (num2 == 0) {
                    System.out.println("\nERROR: Modulo by zero is undefined!");
                    return;
                }
                result = num1 % num2;
                operation = "Modulus (Remainder)";
                break;
            default:
                System.out.println("\nERROR: Invalid operator '" + operator + "'!");
                System.out.println("Valid operators are: +, -, *, /, %");
                return;
        }

        System.out.println("\nOperation: " + operation);
        System.out.printf("%.2f %c %.2f = %.2f%n", num1, operator, num2, result);

        // Show additional info for division
        if (operator == '/') {
            System.out.printf("Integer division: %d / %d = %d (quotient)%n",
                    (int) num1, (int) num2, (int) num1 / (int) num2);
            System.out.printf("Remainder: %d %% %d = %d%n",
                    (int) num1, (int) num2, (int) num1 % (int) num2);
        }
    }

    // ===================== UTILITY 4: Grade Classifier (Bonus) =====================
    public static void gradeClassifier(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        GRADE CLASSIFIER              ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.print("Enter percentage score (0-100): ");
        int score = scanner.nextInt();

        if (score < 0 || score > 100) {
            System.out.println("ERROR: Score must be between 0 and 100!");
            return;
        }

        // Using switch on score/10 to group ranges
        // This is a classic interview trick: integer division groups scores into buckets
        String grade;
        String remark;

        switch (score / 10) {
            case 10:  // 100
            case 9:   // 90-99
                grade = "A+";
                remark = "Outstanding! Excellent performance.";
                break;
            case 8:   // 80-89
                grade = "A";
                remark = "Very Good! Well above average.";
                break;
            case 7:   // 70-79
                grade = "B";
                remark = "Good. Above average performance.";
                break;
            case 6:   // 60-69
                grade = "C";
                remark = "Average. Meets expectations.";
                break;
            case 5:   // 50-59
                grade = "D";
                remark = "Below Average. Needs improvement.";
                break;
            default:  // 0-49
                grade = "F";
                remark = "Fail. Significant improvement needed.";
                break;
        }

        System.out.println("\nScore: " + score + "%");
        System.out.println("Grade: " + grade);
        System.out.println("Remark: " + remark);
        System.out.println("\nNote: switch(score/10) groups scores into buckets of 10.");
        System.out.println("      e.g., scores 70-79 all give score/10 = 7 -> Grade B");
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   SWITCH-CASE DEMO — MENU-DRIVEN MULTI-TOOL ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        do {
            System.out.println("\n┌──────────────── MAIN MENU ────────────────┐");
            System.out.println("│  1. Day of Week Finder                    │");
            System.out.println("│  2. Month Days Finder                     │");
            System.out.println("│  3. Simple Calculator                     │");
            System.out.println("│  4. Grade Classifier (Bonus)              │");
            System.out.println("│  5. Exit                                  │");
            System.out.println("└───────────────────────────────────────────┘");

            System.out.print("Enter your choice (1-5): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    dayOfWeekFinder(scanner);
                    break;
                case 2:
                    monthDaysFinder(scanner);
                    break;
                case 3:
                    simpleCalculator(scanner);
                    break;
                case 4:
                    gradeClassifier(scanner);
                    break;
                case 5:
                    System.out.println("\n╔══════════════════════════════════════════════╗");
                    System.out.println("║  Goodbye! Thank you for using the Multi-Tool ║");
                    System.out.println("╚══════════════════════════════════════════════╝");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please enter a number between 1 and 5.");
                    break;
            }

        } while (choice != 5);

        scanner.close();
    }
}
