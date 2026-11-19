/*
 * Program 06: Temperature Converter
 * 
 * Problem Statement:
 *   Create a menu-driven temperature converter that converts between:
 *   - Celsius (°C)
 *   - Fahrenheit (°F)
 *   - Kelvin (K)
 *   The program should loop until the user chooses to exit.
 * 
 * Sample Input:
 *   Choose conversion:
 *   1
 *   Enter temperature in Celsius: 100
 *   
 * Sample Output:
 *   ========================================
 *      TEMPERATURE CONVERTER
 *   ========================================
 *   Choose conversion:
 *   1. Celsius    -> Fahrenheit
 *   2. Celsius    -> Kelvin
 *   3. Fahrenheit -> Celsius
 *   4. Fahrenheit -> Kelvin
 *   5. Kelvin     -> Celsius
 *   6. Kelvin     -> Fahrenheit
 *   7. Convert one value to ALL units
 *   0. Exit
 *   ----------------------------------------
 *   Enter your choice: 1
 *   Enter temperature in Celsius: 100
 *   Result: 100.00 °C = 212.00 °F
 *   
 *   Enter your choice: 7
 *   Enter temperature value: 0
 *   Enter unit (C/F/K): C
 *   0.00 °C = 32.00 °F = 273.15 K
 * 
 * Explanation:
 *   Conversion Formulas:
 *   - °F = (°C × 9/5) + 32
 *   - °C = (°F − 32) × 5/9
 *   - K  = °C + 273.15
 *   - °C = K − 273.15
 *   - °F = (K − 273.15) × 9/5 + 32
 *   - K  = (°F − 32) × 5/9 + 273.15
 * 
 * Time Complexity:  O(1) per conversion
 * Space Complexity: O(1) - fixed number of variables
 */

import java.util.Scanner;

public class TemperatureConverter {

    // --- Conversion Methods ---

    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    public static double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public static double fahrenheitToKelvin(double fahrenheit) {
        return celsiusToKelvin(fahrenheitToCelsius(fahrenheit));
    }

    public static double kelvinToFahrenheit(double kelvin) {
        return celsiusToFahrenheit(kelvinToCelsius(kelvin));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println("     TEMPERATURE CONVERTER");
        System.out.println("========================================");

        while (running) {
            // Display menu
            System.out.println();
            System.out.println("Choose conversion:");
            System.out.println("1. Celsius    -> Fahrenheit");
            System.out.println("2. Celsius    -> Kelvin");
            System.out.println("3. Fahrenheit -> Celsius");
            System.out.println("4. Fahrenheit -> Kelvin");
            System.out.println("5. Kelvin     -> Celsius");
            System.out.println("6. Kelvin     -> Fahrenheit");
            System.out.println("7. Convert one value to ALL units");
            System.out.println("0. Exit");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            double temp, result;

            switch (choice) {
                case 1:
                    System.out.print("Enter temperature in Celsius: ");
                    temp = scanner.nextDouble();
                    result = celsiusToFahrenheit(temp);
                    System.out.printf("Result: %.2f °C = %.2f °F%n", temp, result);
                    break;

                case 2:
                    System.out.print("Enter temperature in Celsius: ");
                    temp = scanner.nextDouble();
                    result = celsiusToKelvin(temp);
                    System.out.printf("Result: %.2f °C = %.2f K%n", temp, result);
                    break;

                case 3:
                    System.out.print("Enter temperature in Fahrenheit: ");
                    temp = scanner.nextDouble();
                    result = fahrenheitToCelsius(temp);
                    System.out.printf("Result: %.2f °F = %.2f °C%n", temp, result);
                    break;

                case 4:
                    System.out.print("Enter temperature in Fahrenheit: ");
                    temp = scanner.nextDouble();
                    result = fahrenheitToKelvin(temp);
                    System.out.printf("Result: %.2f °F = %.2f K%n", temp, result);
                    break;

                case 5:
                    System.out.print("Enter temperature in Kelvin: ");
                    temp = scanner.nextDouble();
                    if (temp < 0) {
                        System.out.println("Warning: Kelvin cannot be negative (absolute zero = 0 K).");
                    }
                    result = kelvinToCelsius(temp);
                    System.out.printf("Result: %.2f K = %.2f °C%n", temp, result);
                    break;

                case 6:
                    System.out.print("Enter temperature in Kelvin: ");
                    temp = scanner.nextDouble();
                    if (temp < 0) {
                        System.out.println("Warning: Kelvin cannot be negative (absolute zero = 0 K).");
                    }
                    result = kelvinToFahrenheit(temp);
                    System.out.printf("Result: %.2f K = %.2f °F%n", temp, result);
                    break;

                case 7:
                    System.out.print("Enter temperature value: ");
                    temp = scanner.nextDouble();
                    System.out.print("Enter unit (C/F/K): ");
                    String unit = scanner.next().toUpperCase();
                    convertToAll(temp, unit);
                    break;

                case 0:
                    running = false;
                    System.out.println("Thank you for using Temperature Converter!");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter 0-7.");
            }
        }

        // --- Display reference table ---
        System.out.println();
        System.out.println("--- Common Temperature Reference Points ---");
        System.out.println("+---------------------+----------+----------+----------+");
        System.out.println("| Description         |    °C    |    °F    |     K    |");
        System.out.println("+---------------------+----------+----------+----------+");
        System.out.printf("| Absolute Zero        | %7.2f  | %7.2f  | %7.2f  |%n", -273.15, -459.67, 0.0);
        System.out.printf("| Water Freezes        | %7.2f  | %7.2f  | %7.2f  |%n", 0.0, 32.0, 273.15);
        System.out.printf("| Room Temperature     | %7.2f  | %7.2f  | %7.2f  |%n", 22.0, 71.6, 295.15);
        System.out.printf("| Body Temperature     | %7.2f  | %7.2f  | %7.2f  |%n", 37.0, 98.6, 310.15);
        System.out.printf("| Water Boils          | %7.2f  | %7.2f  | %7.2f  |%n", 100.0, 212.0, 373.15);
        System.out.println("+---------------------+----------+----------+----------+");

        System.out.println();
        System.out.println("========================================");
        System.out.println("   TEMPERATURE CONVERTER COMPLETED");
        System.out.println("========================================");

        scanner.close();
    }

    /**
     * Converts a temperature value from the given unit to all three units.
     */
    private static void convertToAll(double temp, String unit) {
        double celsius, fahrenheit, kelvin;

        switch (unit) {
            case "C":
                celsius = temp;
                fahrenheit = celsiusToFahrenheit(temp);
                kelvin = celsiusToKelvin(temp);
                break;
            case "F":
                celsius = fahrenheitToCelsius(temp);
                fahrenheit = temp;
                kelvin = fahrenheitToKelvin(temp);
                break;
            case "K":
                celsius = kelvinToCelsius(temp);
                fahrenheit = kelvinToFahrenheit(temp);
                kelvin = temp;
                if (temp < 0) {
                    System.out.println("Warning: Kelvin cannot be negative!");
                }
                break;
            default:
                System.out.println("Invalid unit! Use C, F, or K.");
                return;
        }

        System.out.printf("%.2f °C = %.2f °F = %.2f K%n", celsius, fahrenheit, kelvin);
    }
}
