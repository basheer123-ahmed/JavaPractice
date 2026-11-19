/*
 * Program 29: Number System Converter
 * 
 * Problem Statement:
 *   Convert numbers between four number systems:
 *   - Decimal (base 10)
 *   - Binary (base 2)
 *   - Octal (base 8)
 *   - Hexadecimal (base 16)
 *   Implement conversions both manually (using loops/math) and using built-in Java methods.
 *   Support conversions in all directions (any base to any other base).
 * 
 * Sample Input:  Decimal 255
 * Sample Output:
 *   Decimal:     255
 *   Binary:      11111111
 *   Octal:       377
 *   Hexadecimal: FF
 * 
 * Explanation:
 *   Decimal to other bases: repeatedly divide by the target base and collect remainders.
 *   Other bases to decimal: multiply each digit by the base raised to its positional power.
 *   For hexadecimal, digits 10-15 are represented as A-F.
 *   The manual implementations mirror what Integer.parseInt() and Integer.toString() do internally.
 * 
 * Time Complexity: O(log_b(n)) where b is the base and n is the number
 * Space Complexity: O(log_b(n)) for storing the converted digits
 */

import java.util.Scanner;

public class NumberSystemConverter {

    // ==================== DECIMAL TO OTHER BASES (Manual) ====================

    /**
     * Converts a decimal number to its binary string representation.
     * Algorithm: Repeatedly divide by 2, collect remainders in reverse order.
     */
    public static String decimalToBinary(int decimal) {
        if (decimal == 0) return "0";

        boolean negative = decimal < 0;
        int num = Math.abs(decimal);

        StringBuilder binary = new StringBuilder();
        while (num > 0) {
            binary.append(num % 2);
            num /= 2;
        }

        if (negative) binary.append('-');
        return binary.reverse().toString();
    }

    /**
     * Converts a decimal number to its octal string representation.
     */
    public static String decimalToOctal(int decimal) {
        if (decimal == 0) return "0";

        boolean negative = decimal < 0;
        int num = Math.abs(decimal);

        StringBuilder octal = new StringBuilder();
        while (num > 0) {
            octal.append(num % 8);
            num /= 8;
        }

        if (negative) octal.append('-');
        return octal.reverse().toString();
    }

    /**
     * Converts a decimal number to its hexadecimal string representation.
     */
    public static String decimalToHex(int decimal) {
        if (decimal == 0) return "0";

        boolean negative = decimal < 0;
        int num = Math.abs(decimal);

        char[] hexChars = "0123456789ABCDEF".toCharArray();
        StringBuilder hex = new StringBuilder();

        while (num > 0) {
            hex.append(hexChars[num % 16]);
            num /= 16;
        }

        if (negative) hex.append('-');
        return hex.reverse().toString();
    }

    /**
     * Generic decimal to any base (2-36) converter.
     */
    public static String decimalToBase(int decimal, int base) {
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36");
        }
        if (decimal == 0) return "0";

        boolean negative = decimal < 0;
        int num = Math.abs(decimal);

        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();

        while (num > 0) {
            result.append(digits.charAt(num % base));
            num /= base;
        }

        if (negative) result.append('-');
        return result.reverse().toString();
    }

    // ==================== OTHER BASES TO DECIMAL (Manual) ====================

    /**
     * Converts a binary string to its decimal value.
     */
    public static int binaryToDecimal(String binary) {
        int decimal = 0;
        int power = 0;

        for (int i = binary.length() - 1; i >= 0; i--) {
            char c = binary.charAt(i);
            if (c == '-') {
                decimal = -decimal;
                break;
            }
            if (c == '1') {
                decimal += (1 << power); // 2^power using bit shift
            }
            power++;
        }

        return decimal;
    }

    /**
     * Converts an octal string to its decimal value.
     */
    public static int octalToDecimal(String octal) {
        int decimal = 0;
        int power = 1; // 8^0 = 1

        for (int i = octal.length() - 1; i >= 0; i--) {
            char c = octal.charAt(i);
            if (c == '-') {
                decimal = -decimal;
                break;
            }
            decimal += (c - '0') * power;
            power *= 8;
        }

        return decimal;
    }

    /**
     * Converts a hexadecimal string to its decimal value.
     */
    public static int hexToDecimal(String hex) {
        int decimal = 0;
        int power = 1; // 16^0 = 1

        String upperHex = hex.toUpperCase();
        for (int i = upperHex.length() - 1; i >= 0; i--) {
            char c = upperHex.charAt(i);
            if (c == '-') {
                decimal = -decimal;
                break;
            }

            int digitValue;
            if (c >= '0' && c <= '9') {
                digitValue = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                digitValue = c - 'A' + 10;
            } else {
                throw new IllegalArgumentException("Invalid hex character: " + c);
            }

            decimal += digitValue * power;
            power *= 16;
        }

        return decimal;
    }

    /**
     * Generic any-base to decimal converter.
     */
    public static int baseToDecimal(String number, int base) {
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36");
        }

        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String upper = number.toUpperCase();
        int decimal = 0;
        int power = 1;

        for (int i = upper.length() - 1; i >= 0; i--) {
            char c = upper.charAt(i);
            if (c == '-') {
                decimal = -decimal;
                break;
            }
            int digitValue = digits.indexOf(c);
            if (digitValue < 0 || digitValue >= base) {
                throw new IllegalArgumentException(
                    "Invalid digit '" + c + "' for base " + base);
            }
            decimal += digitValue * power;
            power *= base;
        }

        return decimal;
    }

    // ==================== DIRECT CONVERSIONS ====================

    /**
     * Binary to Octal: group binary digits in sets of 3 from right.
     */
    public static String binaryToOctal(String binary) {
        // Pad to multiple of 3
        int padding = (3 - binary.length() % 3) % 3;
        String padded = "0".repeat(padding) + binary;

        StringBuilder octal = new StringBuilder();
        for (int i = 0; i < padded.length(); i += 3) {
            String group = padded.substring(i, i + 3);
            int value = Integer.parseInt(group, 2);
            octal.append(value);
        }

        // Remove leading zeros
        String result = octal.toString().replaceFirst("^0+", "");
        return result.isEmpty() ? "0" : result;
    }

    /**
     * Binary to Hexadecimal: group binary digits in sets of 4 from right.
     */
    public static String binaryToHex(String binary) {
        // Pad to multiple of 4
        int padding = (4 - binary.length() % 4) % 4;
        String padded = "0".repeat(padding) + binary;

        String hexChars = "0123456789ABCDEF";
        StringBuilder hex = new StringBuilder();

        for (int i = 0; i < padded.length(); i += 4) {
            String group = padded.substring(i, i + 4);
            int value = Integer.parseInt(group, 2);
            hex.append(hexChars.charAt(value));
        }

        String result = hex.toString().replaceFirst("^0+", "");
        return result.isEmpty() ? "0" : result;
    }

    // ==================== STEP-BY-STEP TRACER ====================

    /**
     * Shows step-by-step decimal to binary conversion.
     */
    public static void traceDecimalToBinary(int decimal) {
        System.out.printf("  Converting %d (decimal) to binary:%n", decimal);
        int num = decimal;
        StringBuilder result = new StringBuilder();
        int step = 1;

        while (num > 0) {
            int remainder = num % 2;
            int quotient = num / 2;
            System.out.printf("    Step %d: %d / 2 = %d, remainder = %d%n",
                    step, num, quotient, remainder);
            result.insert(0, remainder);
            num = quotient;
            step++;
        }

        System.out.printf("  Reading remainders bottom-to-top: %s%n", result);
    }

    /**
     * Shows step-by-step binary to decimal conversion.
     */
    public static void traceBinaryToDecimal(String binary) {
        System.out.printf("  Converting %s (binary) to decimal:%n", binary);
        StringBuilder expression = new StringBuilder("  ");
        int decimal = 0;

        for (int i = 0; i < binary.length(); i++) {
            int bit = binary.charAt(i) - '0';
            int power = binary.length() - 1 - i;
            int value = bit * (1 << power);

            if (i > 0) expression.append(" + ");
            expression.append(String.format("%d×2^%d(%d)", bit, power, value));
            decimal += value;
        }

        System.out.println(expression);
        System.out.printf("  = %d%n", decimal);
    }

    // ==================== DISPLAY HELPER ====================

    /**
     * Displays a number in all four bases.
     */
    public static void displayAllBases(int decimal) {
        System.out.printf("  Decimal:      %d%n", decimal);
        System.out.printf("  Binary:       %s%n", decimalToBinary(decimal));
        System.out.printf("  Octal:        %s%n", decimalToOctal(decimal));
        System.out.printf("  Hexadecimal:  %s%n", decimalToHex(decimal));
        System.out.printf("  (Java built-in) Binary: %s, Octal: %s, Hex: %s%n",
                Integer.toBinaryString(decimal),
                Integer.toOctalString(decimal),
                Integer.toHexString(decimal).toUpperCase());
    }

    public static void main(String[] args) {
        System.out.println("=== Program 29: Number System Converter ===\n");

        // ---- Full conversion display ----
        System.out.println("--- Number in All Bases ---");
        int[] testNums = {0, 1, 7, 8, 10, 15, 16, 42, 100, 127, 128, 255, 256, 1000, 9999};

        System.out.printf("  %-8s %-20s %-10s %-8s%n", "Decimal", "Binary", "Octal", "Hex");
        System.out.println("  " + "─".repeat(50));

        for (int num : testNums) {
            System.out.printf("  %-8d %-20s %-10s %-8s%n",
                    num,
                    decimalToBinary(num),
                    decimalToOctal(num),
                    decimalToHex(num));
        }

        // ---- Step-by-step traces ----
        System.out.println("\n--- Step-by-Step: Decimal 42 → Binary ---");
        traceDecimalToBinary(42);

        System.out.println("\n--- Step-by-Step: Binary 10101010 → Decimal ---");
        traceBinaryToDecimal("10101010");

        // ---- Conversion verification ----
        System.out.println("\n--- Conversion Verification (manual vs built-in) ---");
        int[] verifyNums = {42, 255, 1000, 65535};
        boolean allPassed = true;

        for (int num : verifyNums) {
            String manualBin = decimalToBinary(num);
            String builtinBin = Integer.toBinaryString(num);
            String manualOct = decimalToOctal(num);
            String builtinOct = Integer.toOctalString(num);
            String manualHex = decimalToHex(num);
            String builtinHex = Integer.toHexString(num).toUpperCase();

            boolean binOk = manualBin.equals(builtinBin);
            boolean octOk = manualOct.equals(builtinOct);
            boolean hexOk = manualHex.equals(builtinHex);

            // Reverse conversion check
            int fromBin = binaryToDecimal(manualBin);
            int fromOct = octalToDecimal(manualOct);
            int fromHex = hexToDecimal(manualHex);
            boolean reverseOk = (fromBin == num && fromOct == num && fromHex == num);

            boolean passed = binOk && octOk && hexOk && reverseOk;
            if (!passed) allPassed = false;

            System.out.printf("  %d: Binary %s, Octal %s, Hex %s, Reverse %s -> %s%n",
                    num,
                    binOk ? "✓" : "✗",
                    octOk ? "✓" : "✗",
                    hexOk ? "✓" : "✗",
                    reverseOk ? "✓" : "✗",
                    passed ? "PASS" : "FAIL");
        }
        System.out.println("  Overall: " + (allPassed ? "ALL TESTS PASSED ✓" : "SOME TESTS FAILED ✗"));

        // ---- Direct binary conversions ----
        System.out.println("\n--- Direct Binary → Octal / Hex Conversions ---");
        String[] binaryNums = {"101010", "11111111", "1010101010101010", "110110"};
        for (String bin : binaryNums) {
            System.out.printf("  Binary: %-20s  Octal: %-8s  Hex: %-6s  Decimal: %d%n",
                    bin, binaryToOctal(bin), binaryToHex(bin), binaryToDecimal(bin));
        }

        // ---- Generic base conversion ----
        System.out.println("\n--- Generic Base Conversion (Base 2 to 36) ---");
        int number = 1000;
        int[] bases = {2, 3, 5, 7, 8, 10, 12, 16, 20, 36};
        for (int base : bases) {
            String converted = decimalToBase(number, base);
            int backToDecimal = baseToDecimal(converted, base);
            System.out.printf("  %d in base %-2d = %-15s (back to decimal: %d) %s%n",
                    number, base, converted, backToDecimal,
                    backToDecimal == number ? "✓" : "✗");
        }

        // ---- Powers of 2 (useful reference) ----
        System.out.println("\n--- Powers of 2 Reference Table ---");
        System.out.printf("  %-6s %-8s %-20s %-8s %-6s%n", "2^n", "Decimal", "Binary", "Octal", "Hex");
        System.out.println("  " + "─".repeat(52));
        for (int i = 0; i <= 16; i++) {
            int val = 1 << i;
            System.out.printf("  2^%-4d %-8d %-20s %-8s %-6s%n",
                    i, val, decimalToBinary(val), decimalToOctal(val), decimalToHex(val));
        }

        // ---- Common bit patterns ----
        System.out.println("\n--- Common Bit Patterns ---");
        int[] commonPatterns = {0x00, 0x0F, 0xF0, 0xFF, 0x7F, 0x80, 0xFFFF, 0xDEAD, 0xCAFE};
        String[] labels = {"0x00", "0x0F", "0xF0", "0xFF", "0x7F", "0x80", "0xFFFF", "0xDEAD", "0xCAFE"};
        for (int i = 0; i < commonPatterns.length; i++) {
            System.out.printf("  %-8s = decimal %-6d = binary %s%n",
                    labels[i], commonPatterns[i], decimalToBinary(commonPatterns[i]));
        }

        // ---- Interactive mode ----
        System.out.println("\n--- Interactive Mode ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a decimal number: ");
        if (scanner.hasNextInt()) {
            int userNum = scanner.nextInt();
            System.out.println();
            displayAllBases(userNum);
        } else {
            System.out.println("  Invalid input.");
        }
        scanner.close();
    }
}
