/*
 * Program 03: Arithmetic Operations
 * 
 * Problem Statement:
 *   Build a calculator that takes two numbers as input from the user and
 *   performs all five arithmetic operations: addition (+), subtraction (-),
 *   multiplication (*), division (/), and modulus (%). Handle edge cases
 *   like division by zero.
 * 
 * Sample Input:
 *   Enter first number: 25
 *   Enter second number: 7
 * 
 * Sample Output:
 *   ========================================
 *          ARITHMETIC CALCULATOR
 *   ========================================
 *   First Number  (a): 25.0
 *   Second Number (b): 7.0
 *   ----------------------------------------
 *   Addition       : 25.0 + 7.0 = 32.0
 *   Subtraction    : 25.0 - 7.0 = 18.0
 *   Multiplication : 25.0 * 7.0 = 175.0
 *   Division       : 25.0 / 7.0 = 3.5714285714285716
 *   Modulus        : 25.0 % 7.0 = 4.0
 *   ----------------------------------------
 *   Integer Division: 25 / 7 = 3 (quotient), remainder = 4
 *   ========================================
 * 
 * Explanation:
 *   - Addition (+): Sums two numbers.
 *   - Subtraction (-): Finds the difference.
 *   - Multiplication (*): Finds the product.
 *   - Division (/): Divides; for integers this truncates toward zero.
 *   - Modulus (%): Returns the remainder after division.
 *   - Division by zero is checked and handled to prevent ArithmeticException (int)
 *     or Infinity/NaN results (double).
 *   - Scanner is used to read user input from the console.
 * 
 * Time Complexity:  O(1) - constant time arithmetic operations
 * Space Complexity: O(1) - fixed number of variables
 */

import java.util.Scanner;

public class ArithmeticOperations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("       ARITHMETIC CALCULATOR");
        System.out.println("========================================");

        // Read two numbers from user
        System.out.print("Enter first number: ");
        double a = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double b = scanner.nextDouble();

        System.out.println();
        System.out.println("First Number  (a): " + a);
        System.out.println("Second Number (b): " + b);
        System.out.println("----------------------------------------");

        // Addition
        double sum = a + b;
        System.out.println("Addition       : " + a + " + " + b + " = " + sum);

        // Subtraction
        double difference = a - b;
        System.out.println("Subtraction    : " + a + " - " + b + " = " + difference);

        // Multiplication
        double product = a * b;
        System.out.println("Multiplication : " + a + " * " + b + " = " + product);

        // Division (with zero check)
        if (b != 0) {
            double quotient = a / b;
            System.out.println("Division       : " + a + " / " + b + " = " + quotient);
        } else {
            System.out.println("Division       : " + a + " / " + b + " = UNDEFINED (division by zero!)");
        }

        // Modulus (with zero check)
        if (b != 0) {
            double modulus = a % b;
            System.out.println("Modulus        : " + a + " % " + b + " = " + modulus);
        } else {
            System.out.println("Modulus        : " + a + " % " + b + " = UNDEFINED (division by zero!)");
        }

        System.out.println("----------------------------------------");

        // Integer division demonstration
        int intA = (int) a;
        int intB = (int) b;
        if (intB != 0) {
            System.out.println("Integer Division: " + intA + " / " + intB + " = " 
                + (intA / intB) + " (quotient), remainder = " + (intA % intB));
        } else {
            System.out.println("Integer Division: Cannot divide by zero!");
        }

        System.out.println();

        // --- Increment / Decrement Demo ---
        System.out.println("--- Increment / Decrement Operators ---");
        int x = 10;
        System.out.println("x = " + x);
        System.out.println("x++ (post-increment, returns then increments): " + x++);
        System.out.println("x is now: " + x);
        System.out.println("++x (pre-increment, increments then returns):  " + (++x));
        System.out.println("x is now: " + x);
        System.out.println("x-- (post-decrement, returns then decrements): " + x--);
        System.out.println("x is now: " + x);
        System.out.println("--x (pre-decrement, decrements then returns):  " + (--x));
        System.out.println("x is now: " + x);
        System.out.println();

        // --- Compound Assignment Operators ---
        System.out.println("--- Compound Assignment Operators ---");
        int y = 20;
        System.out.println("y = " + y);
        y += 5;
        System.out.println("y += 5  -> y = " + y);
        y -= 3;
        System.out.println("y -= 3  -> y = " + y);
        y *= 2;
        System.out.println("y *= 2  -> y = " + y);
        y /= 4;
        System.out.println("y /= 4  -> y = " + y);
        y %= 3;
        System.out.println("y %= 3  -> y = " + y);

        System.out.println();

        // --- Math class useful methods ---
        System.out.println("--- Useful Math Class Methods ---");
        System.out.println("Math.abs(-15)       = " + Math.abs(-15));
        System.out.println("Math.max(10, 20)    = " + Math.max(10, 20));
        System.out.println("Math.min(10, 20)    = " + Math.min(10, 20));
        System.out.println("Math.pow(2, 10)     = " + Math.pow(2, 10));
        System.out.println("Math.sqrt(144)      = " + Math.sqrt(144));
        System.out.println("Math.ceil(4.3)      = " + Math.ceil(4.3));
        System.out.println("Math.floor(4.7)     = " + Math.floor(4.7));
        System.out.println("Math.round(4.5)     = " + Math.round(4.5));

        System.out.println("========================================");
        System.out.println("   CALCULATOR DEMO COMPLETED");
        System.out.println("========================================");

        scanner.close();
    }
}
