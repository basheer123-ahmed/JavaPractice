/*
 * Program 11: Command-Line Arguments Processor
 * 
 * Problem Statement:
 *   Write a Java program that processes command-line arguments passed to the main method.
 *   The program should:
 *   1. Display the total count of arguments
 *   2. Print each argument with its index
 *   3. Concatenate all arguments into a single string
 *   4. Check if a specific target argument exists among the provided arguments
 *   5. Find the longest and shortest arguments
 *   6. Reverse the order of arguments
 * 
 * Sample Input (run as):
 *   java CommandLineArgs Hello World Java Programming Fun
 * 
 * Sample Output:
 *   ============================================
 *       COMMAND-LINE ARGUMENTS PROCESSOR
 *   ============================================
 *   
 *   [1] Total number of arguments: 5
 *   
 *   [2] Arguments with indices:
 *       args[0] = "Hello"
 *       args[1] = "World"
 *       args[2] = "Java"
 *       args[3] = "Programming"
 *       args[4] = "Fun"
 *   
 *   [3] Concatenated (space-separated): Hello World Java Programming Fun
 *       Concatenated (no separator):    HelloWorldJavaProgrammingFun
 *       Concatenated (dash-separated):  Hello-World-Java-Programming-Fun
 *   
 *   [4] Argument search:
 *       Searching for "Java"... FOUND at index 2
 *       Searching for "Python"... NOT FOUND
 *   
 *   [5] Longest argument:  "Programming" (length: 11)
 *       Shortest argument: "Fun" (length: 3)
 *   
 *   [6] Arguments in reverse order:
 *       Fun Programming Java World Hello
 * 
 * Explanation:
 *   - The main method receives a String[] args parameter that contains all
 *     command-line arguments passed when running the program.
 *   - args.length gives the count of arguments (0 if none provided).
 *   - Arguments are indexed starting from 0 (args[0] is the first argument).
 *   - Unlike C/C++, args[0] is NOT the program name in Java — it is the
 *     first actual argument provided by the user.
 *   - We demonstrate iteration, search, concatenation, and analysis of
 *     the arguments array using fundamental loop and string operations.
 * 
 * Time Complexity: O(n * m) where n = number of arguments, m = average argument length
 *                  (due to string concatenation and search operations)
 * Space Complexity: O(n * m) for storing concatenated strings
 */

public class CommandLineArgs {

    /**
     * Searches for a target string in the arguments array.
     * Returns the index if found, -1 otherwise.
     */
    public static int findArgument(String[] args, String target) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Concatenates all arguments using a specified delimiter.
     */
    public static String concatenateArgs(String[] args, String delimiter) {
        if (args.length == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    /**
     * Finds the longest string in the array.
     */
    public static String findLongest(String[] args) {
        if (args.length == 0) return "";
        String longest = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i].length() > longest.length()) {
                longest = args[i];
            }
        }
        return longest;
    }

    /**
     * Finds the shortest string in the array.
     */
    public static String findShortest(String[] args) {
        if (args.length == 0) return "";
        String shortest = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i].length() < shortest.length()) {
                shortest = args[i];
            }
        }
        return shortest;
    }

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("    COMMAND-LINE ARGUMENTS PROCESSOR");
        System.out.println("============================================");

        // --- [1] Count of arguments ---
        System.out.println("\n[1] Total number of arguments: " + args.length);

        if (args.length == 0) {
            System.out.println("\n    No arguments provided!");
            System.out.println("    Usage: java CommandLineArgs arg1 arg2 arg3 ...");
            System.out.println("\n--- Running demo with sample arguments ---\n");

            // Use sample arguments for demonstration
            args = new String[]{"Hello", "World", "Java", "Programming", "Fun"};
            System.out.println("    Demo arguments: Hello World Java Programming Fun");
            System.out.println("    Total demo arguments: " + args.length);
        }

        // --- [2] Display each argument with its index ---
        System.out.println("\n[2] Arguments with indices:");
        for (int i = 0; i < args.length; i++) {
            System.out.printf("    args[%d] = \"%s\"%n", i, args[i]);
        }

        // --- [3] Concatenation with different delimiters ---
        System.out.println("\n[3] Concatenated (space-separated): " + concatenateArgs(args, " "));
        System.out.println("    Concatenated (no separator):    " + concatenateArgs(args, ""));
        System.out.println("    Concatenated (dash-separated):  " + concatenateArgs(args, "-"));

        // --- [4] Search for specific arguments ---
        System.out.println("\n[4] Argument search:");

        String[] searchTargets = {"Java", "Python"};
        for (String target : searchTargets) {
            int index = findArgument(args, target);
            if (index != -1) {
                System.out.printf("    Searching for \"%s\"... FOUND at index %d%n", target, index);
            } else {
                System.out.printf("    Searching for \"%s\"... NOT FOUND%n", target);
            }
        }

        // --- [5] Longest and shortest arguments ---
        System.out.println("\n[5] Longest argument:  \"" + findLongest(args)
                + "\" (length: " + findLongest(args).length() + ")");
        System.out.println("    Shortest argument: \"" + findShortest(args)
                + "\" (length: " + findShortest(args).length() + ")");

        // --- [6] Arguments in reverse order ---
        System.out.println("\n[6] Arguments in reverse order:");
        System.out.print("    ");
        for (int i = args.length - 1; i >= 0; i--) {
            System.out.print(args[i]);
            if (i > 0) {
                System.out.print(" ");
            }
        }
        System.out.println();

        // --- Bonus: Numeric argument detection ---
        System.out.println("\n[Bonus] Numeric argument detection:");
        int numericCount = 0;
        for (String arg : args) {
            try {
                double num = Double.parseDouble(arg);
                System.out.printf("    \"%s\" is a number (value: %.2f)%n", arg, num);
                numericCount++;
            } catch (NumberFormatException e) {
                System.out.printf("    \"%s\" is NOT a number%n", arg);
            }
        }
        System.out.println("    Total numeric arguments: " + numericCount + " out of " + args.length);

        System.out.println("\n============================================");
        System.out.println("    Program completed successfully!");
        System.out.println("============================================");
    }
}
