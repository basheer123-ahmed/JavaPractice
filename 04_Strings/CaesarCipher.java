/*
 * Program 55: Caesar Cipher
 * 
 * Problem Statement:
 *   Encrypt and decrypt text using the Caesar cipher with a given shift value.
 *   - Encryption: shift each letter forward by the shift value.
 *   - Decryption: shift each letter backward by the shift value.
 *   - Preserve case (upper/lower), leave non-alphabetic characters unchanged.
 *   - Handle shifts > 26 and negative shifts.
 * 
 * Sample Input:  text = "Hello, World!", shift = 3
 * Sample Output:
 *   Encrypted: "Khoor, Zruog!"
 *   Decrypted: "Hello, World!"
 * 
 * Explanation:
 *   Each letter is shifted by 'k' positions in the alphabet (mod 26).
 *   H(7) + 3 = K(10), e(4) + 3 = h(7), l(11) + 3 = o(14), etc.
 *   Decryption is simply encryption with shift = (26 - k).
 *   Non-letter characters (comma, space, exclamation) are kept as-is.
 * 
 * Time Complexity: O(n) where n = length of the text
 * Space Complexity: O(n) for the resulting string
 */

public class CaesarCipher {

    // =========================================================================
    // Encrypt: shift each letter forward by 'shift' positions
    // =========================================================================
    public static String encrypt(String plaintext, int shift) {
        // Normalize shift to 0-25 range
        shift = ((shift % 26) + 26) % 26;

        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);

            if (Character.isUpperCase(c)) {
                char shifted = (char) ('A' + (c - 'A' + shift) % 26);
                encrypted.append(shifted);
            } else if (Character.isLowerCase(c)) {
                char shifted = (char) ('a' + (c - 'a' + shift) % 26);
                encrypted.append(shifted);
            } else {
                // Non-alphabetic character: keep unchanged
                encrypted.append(c);
            }
        }

        return encrypted.toString();
    }

    // =========================================================================
    // Decrypt: shift each letter backward by 'shift' positions
    //   Decryption is just encryption with the inverse shift.
    // =========================================================================
    public static String decrypt(String ciphertext, int shift) {
        return encrypt(ciphertext, 26 - (shift % 26));
    }

    // =========================================================================
    // Brute Force Decrypt: try all 26 possible shifts (useful for cracking)
    // =========================================================================
    public static String[] bruteForceDecrypt(String ciphertext) {
        String[] results = new String[26];
        for (int shift = 0; shift < 26; shift++) {
            results[shift] = decrypt(ciphertext, shift);
        }
        return results;
    }

    // =========================================================================
    // Encrypt with a keyword (Vigenère-like extension, bonus)
    //   Each character of the key determines the shift for the corresponding
    //   character of the plaintext.
    // =========================================================================
    public static String encryptWithKeyword(String plaintext, String keyword) {
        StringBuilder encrypted = new StringBuilder();
        String key = keyword.toLowerCase();
        int keyIndex = 0;

        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);

            if (Character.isLetter(c)) {
                int shift = key.charAt(keyIndex % key.length()) - 'a';
                if (Character.isUpperCase(c)) {
                    encrypted.append((char) ('A' + (c - 'A' + shift) % 26));
                } else {
                    encrypted.append((char) ('a' + (c - 'a' + shift) % 26));
                }
                keyIndex++;
            } else {
                encrypted.append(c);
            }
        }

        return encrypted.toString();
    }

    public static String decryptWithKeyword(String ciphertext, String keyword) {
        StringBuilder decrypted = new StringBuilder();
        String key = keyword.toLowerCase();
        int keyIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);

            if (Character.isLetter(c)) {
                int shift = key.charAt(keyIndex % key.length()) - 'a';
                if (Character.isUpperCase(c)) {
                    decrypted.append((char) ('A' + (c - 'A' - shift + 26) % 26));
                } else {
                    decrypted.append((char) ('a' + (c - 'a' - shift + 26) % 26));
                }
                keyIndex++;
            } else {
                decrypted.append(c);
            }
        }

        return decrypted.toString();
    }

    // =========================================================================
    // ROT13: Special case of Caesar cipher with shift = 13
    //   Applying ROT13 twice returns the original text.
    // =========================================================================
    public static String rot13(String text) {
        return encrypt(text, 13);
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      Caesar Cipher                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        // =====================================================================
        // Basic Encryption / Decryption Tests
        // =====================================================================
        System.out.println("=== Basic Caesar Cipher Tests ===");
        System.out.println();

        String[][] tests = {
            {"Hello, World!", "3"},
            {"ATTACK AT DAWN", "7"},
            {"The quick brown fox jumps over the lazy dog.", "13"},
            {"abc XYZ", "1"},
            {"Java Programming!", "5"},
            {"Hello", "0"},         // No shift
            {"Hello", "26"},        // Full rotation (same as 0)
            {"Hello", "-3"},        // Negative shift
            {"12345!@#", "5"},      // No letters to shift
        };

        System.out.printf("%-40s %-6s %-40s %-40s %-8s%n",
                "Plaintext", "Shift", "Encrypted", "Decrypted", "Match?");
        System.out.println("-".repeat(140));

        for (String[] test : tests) {
            String plaintext = test[0];
            int shift = Integer.parseInt(test[1]);

            String encrypted = encrypt(plaintext, shift);
            String decrypted = decrypt(encrypted, shift);
            boolean match = plaintext.equals(decrypted);

            System.out.printf("%-40s %-6d %-40s %-40s %-8s%n",
                    "\"" + plaintext + "\"", shift,
                    "\"" + encrypted + "\"",
                    "\"" + decrypted + "\"",
                    match ? "✓" : "✗");
        }

        // =====================================================================
        // Step-by-Step Encryption Trace
        // =====================================================================
        System.out.println();
        System.out.println("=== Step-by-Step Encryption Trace ===");
        traceEncryption("Hello, World!", 3);

        // =====================================================================
        // Brute Force Decryption (Cracking)
        // =====================================================================
        System.out.println();
        System.out.println("=== Brute Force Decryption (all 26 shifts) ===");
        String ciphertext = encrypt("MEET ME AT THE PARK", 7);
        System.out.println("Ciphertext: \"" + ciphertext + "\"");
        System.out.println();

        String[] allDecryptions = bruteForceDecrypt(ciphertext);
        for (int i = 0; i < 26; i++) {
            String marker = (i == 7) ? " <-- CORRECT" : "";
            System.out.printf("  Shift %2d: \"%s\"%s%n", i, allDecryptions[i], marker);
        }

        // =====================================================================
        // ROT13 Demo
        // =====================================================================
        System.out.println();
        System.out.println("=== ROT13 (Shift 13) ===");
        String msg = "Hello World";
        String rot = rot13(msg);
        String unrot = rot13(rot);
        System.out.println("Original  : \"" + msg + "\"");
        System.out.println("ROT13     : \"" + rot + "\"");
        System.out.println("ROT13 x2  : \"" + unrot + "\"  (back to original)");

        // =====================================================================
        // Vigenère Cipher (Keyword-based) Demo
        // =====================================================================
        System.out.println();
        System.out.println("=== Vigenère Cipher (Keyword-based) ===");
        String vigPlain = "HELLO WORLD";
        String keyword = "KEY";
        String vigEncrypted = encryptWithKeyword(vigPlain, keyword);
        String vigDecrypted = decryptWithKeyword(vigEncrypted, keyword);
        System.out.println("Plaintext : \"" + vigPlain + "\"");
        System.out.println("Keyword   : \"" + keyword + "\"");
        System.out.println("Encrypted : \"" + vigEncrypted + "\"");
        System.out.println("Decrypted : \"" + vigDecrypted + "\"");

        // =====================================================================
        // Full Alphabet Mapping for shift = 3
        // =====================================================================
        System.out.println();
        System.out.println("=== Alphabet Mapping (Shift = 3) ===");
        System.out.print("Plain   : ");
        for (char c = 'A'; c <= 'Z'; c++) System.out.print(c + " ");
        System.out.println();
        System.out.print("Cipher  : ");
        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print((char)('A' + (c - 'A' + 3) % 26) + " ");
        }
        System.out.println();
    }

    /**
     * Visual trace of encryption showing each character transformation.
     */
    private static void traceEncryption(String plaintext, int shift) {
        System.out.println("Plaintext: \"" + plaintext + "\", Shift: " + shift);
        System.out.println();
        System.out.printf("  %-6s %-6s %-20s %-6s%n", "Index", "Char", "Transformation", "Result");
        System.out.println("  " + "-".repeat(45));

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            char encrypted;
            String transformation;

            if (Character.isUpperCase(c)) {
                encrypted = (char) ('A' + (c - 'A' + shift) % 26);
                transformation = String.format("'%c'(%d) + %d = '%c'(%d)",
                        c, c - 'A', shift, encrypted, encrypted - 'A');
            } else if (Character.isLowerCase(c)) {
                encrypted = (char) ('a' + (c - 'a' + shift) % 26);
                transformation = String.format("'%c'(%d) + %d = '%c'(%d)",
                        c, c - 'a', shift, encrypted, encrypted - 'a');
            } else {
                encrypted = c;
                transformation = "(non-alpha, unchanged)";
            }

            result.append(encrypted);
            System.out.printf("  %-6d %-6c %-20s %-6c%n", i, c, transformation, encrypted);
        }

        System.out.println();
        System.out.println("  Encrypted: \"" + result.toString() + "\"");
    }
}
