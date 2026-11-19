/*
 * Program 09: Bitwise Operations
 * 
 * Problem Statement:
 *   Demonstrate all bitwise operators in Java:
 *   - AND (&), OR (|), XOR (^), NOT (~)
 *   - Left Shift (<<), Right Shift (>>), Unsigned Right Shift (>>>)
 *   Show binary representations and practical use cases for each operator.
 * 
 * Sample Input:  (none - uses hardcoded demonstration values)
 * Sample Output:
 *   ============================================================
 *            BITWISE OPERATIONS DEMONSTRATION
 *   ============================================================
 *   Using a = 12 (binary: 00001100)
 *   Using b =  10 (binary: 00001010)
 *   
 *   --- Bitwise AND (&) ---
 *   12 & 10 = 8
 *   00001100 & 00001010 = 00001000
 *   Rule: 1 & 1 = 1, all others = 0
 *   
 *   --- Bitwise OR (|) ---
 *   12 | 10 = 14
 *   00001100 | 00001010 = 00001110
 *   Rule: 0 | 0 = 0, all others = 1
 *   
 *   --- Bitwise XOR (^) ---
 *   12 ^ 10 = 6
 *   00001100 ^ 00001010 = 00000110
 *   Rule: same bits = 0, different bits = 1
 *   
 *   --- Bitwise NOT (~) ---
 *   ~12 = -13
 *   ~00001100 = 11110011 (in 32-bit: 11111111111111111111111111110011)
 *   Rule: flips all bits (0->1, 1->0)
 *   
 *   --- Left Shift (<<) ---
 *   12 << 2 = 48
 *   Equivalent to: 12 * 2^2 = 12 * 4 = 48
 *   
 *   --- Right Shift (>>) ---
 *   12 >> 2 = 3
 *   Equivalent to: 12 / 2^2 = 12 / 4 = 3
 *   
 *   --- Unsigned Right Shift (>>>) ---
 *   -1 >>> 1 = 2147483647
 *   Fills with 0 from the left (ignores sign bit)
 *   ============================================================
 * 
 * Explanation:
 *   Bitwise operators work directly on the binary representation of integers.
 *   They are extremely fast (single CPU instruction) and commonly used in:
 *   - Systems programming and embedded systems
 *   - Graphics programming (color manipulation)
 *   - Networking (IP address/subnet masks)
 *   - Competitive programming (bit manipulation tricks)
 *   - Cryptography and hash functions
 * 
 * Time Complexity:  O(1) - all bitwise operations are constant time
 * Space Complexity: O(1) - fixed number of variables
 */

public class BitwiseOperations {

    /**
     * Formats an integer as a padded binary string of given width.
     */
    public static String toBinary(int num, int bits) {
        String binary = Integer.toBinaryString(num);
        if (binary.length() > bits) {
            // For negative numbers, take the last 'bits' characters
            return binary.substring(binary.length() - bits);
        }
        // Pad with leading zeros
        return String.format("%" + bits + "s", binary).replace(' ', '0');
    }

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("         BITWISE OPERATIONS DEMONSTRATION");
        System.out.println("============================================================");

        int a = 12;  // 00001100
        int b = 10;  // 00001010

        System.out.println("Using a = " + a + " (binary: " + toBinary(a, 8) + ")");
        System.out.println("Using b = " + b + " (binary: " + toBinary(b, 8) + ")");
        System.out.println();

        // ============================================================
        // 1. Bitwise AND (&)
        // ============================================================
        System.out.println("--- 1. Bitwise AND (&) ---");
        int andResult = a & b;
        System.out.println(a + " & " + b + " = " + andResult);
        System.out.println(toBinary(a, 8) + " & " + toBinary(b, 8) + " = " + toBinary(andResult, 8));
        System.out.println("Rule: 1 & 1 = 1, all other combinations = 0");
        System.out.println("Use case: Check if a bit is set, mask specific bits");
        System.out.println("Example: Check if number is even -> (n & 1) == 0");
        System.out.println("  12 & 1 = " + (12 & 1) + " (even)");
        System.out.println("  13 & 1 = " + (13 & 1) + " (odd)");
        System.out.println();

        // ============================================================
        // 2. Bitwise OR (|)
        // ============================================================
        System.out.println("--- 2. Bitwise OR (|) ---");
        int orResult = a | b;
        System.out.println(a + " | " + b + " = " + orResult);
        System.out.println(toBinary(a, 8) + " | " + toBinary(b, 8) + " = " + toBinary(orResult, 8));
        System.out.println("Rule: 0 | 0 = 0, all other combinations = 1");
        System.out.println("Use case: Set specific bits, combine flags");
        System.out.println();

        // ============================================================
        // 3. Bitwise XOR (^)
        // ============================================================
        System.out.println("--- 3. Bitwise XOR (^) ---");
        int xorResult = a ^ b;
        System.out.println(a + " ^ " + b + " = " + xorResult);
        System.out.println(toBinary(a, 8) + " ^ " + toBinary(b, 8) + " = " + toBinary(xorResult, 8));
        System.out.println("Rule: same bits = 0, different bits = 1");
        System.out.println("Use case: Toggle bits, swap without temp, find unique element");
        System.out.println("Property: a ^ a = 0, a ^ 0 = a");
        System.out.println("  " + a + " ^ " + a + " = " + (a ^ a));
        System.out.println("  " + a + " ^ 0 = " + (a ^ 0));
        System.out.println();

        // ============================================================
        // 4. Bitwise NOT (~)
        // ============================================================
        System.out.println("--- 4. Bitwise NOT / Complement (~) ---");
        int notResult = ~a;
        System.out.println("~" + a + " = " + notResult);
        System.out.println("~" + toBinary(a, 8) + " = " + toBinary(notResult, 8) 
            + " (8-bit view)");
        System.out.println("Full 32-bit: " + toBinary(notResult, 32));
        System.out.println("Rule: flips all bits (0 -> 1, 1 -> 0)");
        System.out.println("Formula: ~n = -(n+1)");
        System.out.println("  ~0 = " + (~0) + ", ~(-1) = " + (~(-1)));
        System.out.println();

        // ============================================================
        // 5. Left Shift (<<)
        // ============================================================
        System.out.println("--- 5. Left Shift (<<) ---");
        System.out.println("Shifts bits to the left, fills with 0 on the right.");
        System.out.println("Equivalent to multiplying by 2^n.");
        System.out.println();
        for (int shift = 0; shift <= 4; shift++) {
            int result = a << shift;
            System.out.printf("%d << %d = %-6d  |  %s -> %s  |  = %d × 2^%d%n",
                    a, shift, result, toBinary(a, 8), toBinary(result, 12),
                    a, shift);
        }
        System.out.println();

        // ============================================================
        // 6. Right Shift (>>) - Signed / Arithmetic
        // ============================================================
        System.out.println("--- 6. Signed Right Shift (>>) ---");
        System.out.println("Shifts bits to the right, preserves the sign bit.");
        System.out.println("Equivalent to dividing by 2^n (rounds toward negative infinity).");
        System.out.println();

        int posNum = 48;
        for (int shift = 0; shift <= 4; shift++) {
            int result = posNum >> shift;
            System.out.printf("%d >> %d = %-6d  |  %s -> %s%n",
                    posNum, shift, result, toBinary(posNum, 8), toBinary(result, 8));
        }
        System.out.println();

        int negNum = -16;
        System.out.println("With negative number (" + negNum + "):");
        for (int shift = 0; shift <= 3; shift++) {
            int result = negNum >> shift;
            System.out.printf("%d >> %d = %-6d  |  %s -> %s%n",
                    negNum, shift, result, toBinary(negNum, 32), toBinary(result, 32));
        }
        System.out.println("Sign bit is preserved (1s fill from the left for negatives)");
        System.out.println();

        // ============================================================
        // 7. Unsigned Right Shift (>>>)
        // ============================================================
        System.out.println("--- 7. Unsigned Right Shift (>>>) ---");
        System.out.println("Shifts bits to the right, always fills with 0 from the left.");
        System.out.println("Treats the number as unsigned (ignores sign bit).");
        System.out.println();
        System.out.println("-1 in binary (32-bit): " + toBinary(-1, 32));
        System.out.println("-1 >>> 1 = " + (-1 >>> 1));
        System.out.println("Binary: " + toBinary(-1 >>> 1, 32));
        System.out.println();
        System.out.println("Comparison >> vs >>> with -16:");
        System.out.println("-16 >> 2  = " + (-16 >> 2) + "  (sign preserved)");
        System.out.println("-16 >>> 2 = " + (-16 >>> 2) + "  (0-filled from left)");
        System.out.println();

        // ============================================================
        // PRACTICAL BIT MANIPULATION TRICKS
        // ============================================================
        System.out.println("============================================================");
        System.out.println("     PRACTICAL BIT MANIPULATION TRICKS");
        System.out.println("============================================================");
        System.out.println();

        // Check if a number is a power of 2
        System.out.println("--- Is Power of 2? ---");
        int[] testNums = {1, 2, 4, 6, 8, 16, 15, 32, 64};
        for (int n : testNums) {
            boolean isPow2 = (n > 0) && ((n & (n - 1)) == 0);
            System.out.printf("  %3d: n & (n-1) = %3d -> %s%n", n, n & (n - 1),
                    isPow2 ? "YES (power of 2)" : "NO");
        }
        System.out.println();

        // Count set bits (Brian Kernighan's algorithm)
        System.out.println("--- Count Set Bits (1s) ---");
        int num = 29;  // 11101 -> 4 set bits
        int count = 0;
        int temp = num;
        while (temp != 0) {
            temp = temp & (temp - 1);  // Clears the lowest set bit
            count++;
        }
        System.out.println(num + " (" + toBinary(num, 8) + ") has " + count + " set bits");
        System.out.println("Integer.bitCount(" + num + ") = " + Integer.bitCount(num));
        System.out.println();

        // Get, Set, Clear, Toggle a specific bit
        System.out.println("--- Get/Set/Clear/Toggle Bit ---");
        int value = 0b00001100;  // 12
        int pos = 1; // bit position (0-indexed from right)
        System.out.println("value = " + value + " (" + toBinary(value, 8) + "), position = " + pos);
        
        // Get bit
        int getBit = (value >> pos) & 1;
        System.out.println("Get bit at pos " + pos + ":    " + getBit);
        
        // Set bit (force to 1)
        int setBit = value | (1 << pos);
        System.out.println("Set bit at pos " + pos + ":    " + setBit + " (" + toBinary(setBit, 8) + ")");
        
        // Clear bit (force to 0)
        int clearBit = value & ~(1 << pos);
        System.out.println("Clear bit at pos " + pos + ":  " + clearBit + " (" + toBinary(clearBit, 8) + ")");
        
        // Toggle bit (flip)
        int toggleBit = value ^ (1 << pos);
        System.out.println("Toggle bit at pos " + pos + ": " + toggleBit + " (" + toBinary(toggleBit, 8) + ")");
        System.out.println();

        // Using bit shifts for multiplication/division
        System.out.println("--- Fast Multiply/Divide with Shifts ---");
        int x = 7;
        System.out.println(x + " * 2  = " + x + " << 1 = " + (x << 1));
        System.out.println(x + " * 4  = " + x + " << 2 = " + (x << 2));
        System.out.println(x + " * 8  = " + x + " << 3 = " + (x << 3));
        System.out.println("20 / 2 = 20 >> 1 = " + (20 >> 1));
        System.out.println("20 / 4 = 20 >> 2 = " + (20 >> 2));

        System.out.println();
        System.out.println("============================================================");
        System.out.println("       BITWISE OPERATIONS DEMO COMPLETED");
        System.out.println("============================================================");
    }
}
