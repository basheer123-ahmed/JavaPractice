/*
 * Program 15: Area Calculator — Method Overloading Demonstration
 * 
 * Problem Statement:
 *   Write a Java program that calculates the area of various geometric shapes using
 *   method overloading. The program should include overloaded methods named "calculateArea"
 *   that accept different parameters to compute areas for:
 *   1. Circle — given radius (one double parameter)
 *   2. Rectangle — given length and width (two double parameters)
 *   3. Triangle — given base and height (two double + a marker parameter)
 *   4. Square — given side (one int parameter — different from circle's double)
 *   5. Trapezoid — given two parallel sides and height (three double parameters)
 *   
 *   Demonstrate how the compiler selects the correct method based on parameter
 *   types and counts (compile-time polymorphism / static binding).
 * 
 * Sample Input:
 *   (No user input required — uses hardcoded values for demonstration)
 * 
 * Sample Output:
 *   === AREA CALCULATOR — METHOD OVERLOADING ===
 *   
 *   [1] CIRCLE (radius = 7.0)
 *       Area = π × r² = 3.14159 × 7.0² = 153.94 sq units
 *   
 *   [2] RECTANGLE (length = 12.5, width = 8.0)
 *       Area = length × width = 12.5 × 8.0 = 100.00 sq units
 *   
 *   [3] TRIANGLE (base = 10.0, height = 6.0)
 *       Area = 0.5 × base × height = 0.5 × 10.0 × 6.0 = 30.00 sq units
 *   
 *   [4] SQUARE (side = 9)
 *       Area = side² = 9² = 81.00 sq units
 *   
 *   [5] TRAPEZOID (a = 10.0, b = 6.0, height = 4.0)
 *       Area = 0.5 × (a + b) × h = 0.5 × (10.0 + 6.0) × 4.0 = 32.00 sq units
 * 
 * Explanation:
 *   Method overloading is a form of compile-time polymorphism (static polymorphism).
 *   Multiple methods can have the SAME name but MUST differ in:
 *     - Number of parameters, OR
 *     - Types of parameters, OR
 *     - Order of parameter types
 *   
 *   The compiler determines which method to call based on the arguments provided at
 *   the call site. This is resolved at COMPILE TIME, not runtime.
 *   
 *   NOTE: Return type alone does NOT make methods overloaded. If two methods have
 *   the same name and same parameter list but different return types, the code will
 *   NOT compile (ambiguity error).
 *   
 *   In this program:
 *     calculateArea(double radius)                -> Circle  (1 double)
 *     calculateArea(double length, double width)   -> Rectangle (2 doubles)
 *     calculateArea(double base, double h, boolean isTriangle) -> Triangle (2 doubles + bool)
 *     calculateArea(int side)                      -> Square  (1 int — differs from circle's double)
 *     calculateArea(double a, double b, double h)  -> Trapezoid (3 doubles)
 * 
 * Time Complexity: O(1) — each area calculation is a constant-time arithmetic operation
 * Space Complexity: O(1) — only a fixed number of variables used
 */

public class AreaCalculator {

    // ==================== OVERLOADED METHODS ====================

    /**
     * Overload 1: Calculate area of a CIRCLE.
     * Parameter: double radius (one double parameter)
     * Formula: π × r²
     */
    public static double calculateArea(double radius) {
        if (radius < 0) {
            System.out.println("    ⚠ Warning: Radius cannot be negative. Using absolute value.");
            radius = Math.abs(radius);
        }
        return Math.PI * radius * radius;
    }

    /**
     * Overload 2: Calculate area of a RECTANGLE.
     * Parameters: double length, double width (two double parameters)
     * Formula: length × width
     * 
     * Compiler distinguishes from circle (1 param) and trapezoid (3 params)
     * by the NUMBER of parameters.
     */
    public static double calculateArea(double length, double width) {
        if (length < 0 || width < 0) {
            System.out.println("    ⚠ Warning: Dimensions cannot be negative. Using absolute values.");
            length = Math.abs(length);
            width = Math.abs(width);
        }
        return length * width;
    }

    /**
     * Overload 3: Calculate area of a TRIANGLE.
     * Parameters: double base, double height, boolean isTriangle
     * Formula: 0.5 × base × height
     * 
     * The boolean marker parameter differentiates this from the Rectangle overload
     * (which also takes two doubles) and the Trapezoid (which takes three doubles).
     * This is a common technique when parameter counts/types would otherwise clash.
     */
    public static double calculateArea(double base, double height, boolean isTriangle) {
        if (base < 0 || height < 0) {
            System.out.println("    ⚠ Warning: Dimensions cannot be negative. Using absolute values.");
            base = Math.abs(base);
            height = Math.abs(height);
        }
        return 0.5 * base * height;
    }

    /**
     * Overload 4: Calculate area of a SQUARE.
     * Parameter: int side (one int parameter — differs from circle's double)
     * Formula: side²
     * 
     * Key distinction: This takes an INT, while the circle takes a DOUBLE.
     * The compiler uses TYPE MATCHING to pick the right method:
     *   calculateArea(5)   -> calls this method (int)
     *   calculateArea(5.0) -> calls circle method (double)
     */
    public static double calculateArea(int side) {
        if (side < 0) {
            System.out.println("    ⚠ Warning: Side cannot be negative. Using absolute value.");
            side = Math.abs(side);
        }
        return (double) side * side;
    }

    /**
     * Overload 5: Calculate area of a TRAPEZOID.
     * Parameters: double parallelSideA, double parallelSideB, double height (three doubles)
     * Formula: 0.5 × (a + b) × h
     * 
     * Distinguished from triangle (2 doubles + boolean) by parameter TYPES.
     */
    public static double calculateArea(double parallelSideA, double parallelSideB, double height) {
        if (parallelSideA < 0 || parallelSideB < 0 || height < 0) {
            System.out.println("    ⚠ Warning: Dimensions cannot be negative. Using absolute values.");
            parallelSideA = Math.abs(parallelSideA);
            parallelSideB = Math.abs(parallelSideB);
            height = Math.abs(height);
        }
        return 0.5 * (parallelSideA + parallelSideB) * height;
    }

    // ==================== BONUS: Perimeter Methods (also overloaded) ====================

    /**
     * Perimeter of a circle (circumference).
     */
    public static double calculatePerimeter(double radius) {
        return 2 * Math.PI * radius;
    }

    /**
     * Perimeter of a rectangle.
     */
    public static double calculatePerimeter(double length, double width) {
        return 2 * (length + width);
    }

    /**
     * Perimeter of a square.
     */
    public static double calculatePerimeter(int side) {
        return 4.0 * side;
    }

    // ==================== DISPLAY HELPER ====================

    /**
     * Prints a formatted shape card with area and perimeter details.
     */
    public static void printShapeCard(String shapeName, String formula,
                                       String values, double area, double perimeter) {
        System.out.println("    ┌─────────────────────────────────────────────┐");
        System.out.printf("    │  Shape: %-36s│%n", shapeName);
        System.out.printf("    │  Formula: %-33s│%n", formula);
        System.out.printf("    │  Values: %-34s│%n", values);
        System.out.printf("    │  Area: %-27.4f sq units │%n", area);
        if (perimeter >= 0) {
            System.out.printf("    │  Perimeter: %-26.4f units │%n", perimeter);
        }
        System.out.println("    └─────────────────────────────────────────────┘");
    }

    // ==================== MAIN METHOD ====================

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   AREA CALCULATOR — METHOD OVERLOADING DEMONSTRATION  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");

        // ============================================================
        // Explain Method Overloading
        // ============================================================
        System.out.println("\n  What is Method Overloading?");
        System.out.println("  ─────────────────────────────");
        System.out.println("  • Same method name, different parameter lists");
        System.out.println("  • Compile-time polymorphism (static binding)");
        System.out.println("  • Compiler picks the right method based on arguments");
        System.out.println("  • Methods MUST differ in: number, types, or order of params");
        System.out.println("  • Return type alone does NOT distinguish overloaded methods");

        // ============================================================
        // [1] Circle
        // ============================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("[1] CIRCLE — calculateArea(double radius)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        double radius = 7.0;
        double circleArea = calculateArea(radius);     // Calls Overload 1
        double circlePerimeter = calculatePerimeter(radius);

        System.out.println("    Call: calculateArea(7.0)");
        System.out.println("    Matched: calculateArea(double) → CIRCLE");
        printShapeCard("CIRCLE", "π × r²",
                "r = " + radius, circleArea, circlePerimeter);

        // ============================================================
        // [2] Rectangle
        // ============================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("[2] RECTANGLE — calculateArea(double length, double width)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        double length = 12.5, width = 8.0;
        double rectArea = calculateArea(length, width);  // Calls Overload 2
        double rectPerimeter = calculatePerimeter(length, width);

        System.out.println("    Call: calculateArea(12.5, 8.0)");
        System.out.println("    Matched: calculateArea(double, double) → RECTANGLE");
        printShapeCard("RECTANGLE", "length × width",
                "l = " + length + ", w = " + width, rectArea, rectPerimeter);

        // ============================================================
        // [3] Triangle
        // ============================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("[3] TRIANGLE — calculateArea(double base, double h, boolean flag)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        double base = 10.0, height = 6.0;
        double triArea = calculateArea(base, height, true);  // Calls Overload 3

        System.out.println("    Call: calculateArea(10.0, 6.0, true)");
        System.out.println("    Matched: calculateArea(double, double, boolean) → TRIANGLE");
        System.out.println("    Note: The boolean parameter distinguishes this from rectangle.");
        printShapeCard("TRIANGLE", "0.5 × base × height",
                "b = " + base + ", h = " + height, triArea, -1);

        // ============================================================
        // [4] Square
        // ============================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("[4] SQUARE — calculateArea(int side)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int side = 9;
        double sqArea = calculateArea(side);            // Calls Overload 4
        double sqPerimeter = calculatePerimeter(side);

        System.out.println("    Call: calculateArea(9)");
        System.out.println("    Matched: calculateArea(int) → SQUARE");
        System.out.println("    Key: int param matches square; double param matches circle.");
        printShapeCard("SQUARE", "side²",
                "side = " + side, sqArea, sqPerimeter);

        // ============================================================
        // [5] Trapezoid
        // ============================================================
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("[5] TRAPEZOID — calculateArea(double sideA, double sideB, double h)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        double sideA = 10.0, sideB = 6.0, trapHeight = 4.0;
        double trapArea = calculateArea(sideA, sideB, trapHeight);  // Calls Overload 5

        System.out.println("    Call: calculateArea(10.0, 6.0, 4.0)");
        System.out.println("    Matched: calculateArea(double, double, double) → TRAPEZOID");
        System.out.println("    Key: Three doubles matches trapezoid (not triangle which has boolean).");
        printShapeCard("TRAPEZOID", "0.5 × (a + b) × h",
                "a = " + sideA + ", b = " + sideB + ", h = " + trapHeight, trapArea, -1);

        // ============================================================
        // Method Resolution Summary Table
        // ============================================================
        System.out.println("\n════════════════════════════════════════════════════");
        System.out.println("  METHOD OVERLOADING RESOLUTION SUMMARY");
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("  ┌──────────────────────────────────┬────────────┐");
        System.out.println("  │ Method Signature                 │ Shape      │");
        System.out.println("  ├──────────────────────────────────┼────────────┤");
        System.out.println("  │ calculateArea(double)            │ Circle     │");
        System.out.println("  │ calculateArea(double, double)    │ Rectangle  │");
        System.out.println("  │ calculateArea(double, double,    │ Triangle   │");
        System.out.println("  │               boolean)           │            │");
        System.out.println("  │ calculateArea(int)               │ Square     │");
        System.out.println("  │ calculateArea(double, double,    │ Trapezoid  │");
        System.out.println("  │               double)            │            │");
        System.out.println("  └──────────────────────────────────┴────────────┘");

        // ============================================================
        // Type Promotion Pitfall
        // ============================================================
        System.out.println("\n════════════════════════════════════════════════════");
        System.out.println("  ⚠ TYPE PROMOTION PITFALL");
        System.out.println("════════════════════════════════════════════════════");
        System.out.println("  What happens with: calculateArea(5)?");
        System.out.println("  → 5 is int → matches calculateArea(int) → SQUARE");
        System.out.println("  What happens with: calculateArea(5.0)?");
        System.out.println("  → 5.0 is double → matches calculateArea(double) → CIRCLE");
        System.out.println();

        System.out.println("  Demonstration:");
        System.out.printf("    calculateArea(5)   = %.4f (Square: 5² = 25)%n",
                calculateArea(5));
        System.out.printf("    calculateArea(5.0) = %.4f (Circle: π×5² ≈ 78.5398)%n",
                calculateArea(5.0));

        System.out.println("\n  If no int overload existed, int 5 would be PROMOTED to");
        System.out.println("  double 5.0 (widening conversion) and call the circle method.");
        System.out.println("  Java follows: byte → short → int → long → float → double");

        // ============================================================
        // Comparison Table of All Areas
        // ============================================================
        System.out.println("\n════════════════════════════════════════════════════");
        System.out.println("  COMPARISON OF ALL CALCULATED AREAS");
        System.out.println("════════════════════════════════════════════════════");
        System.out.printf("    Circle    (r=%.1f)         : %10.4f sq units%n", radius, circleArea);
        System.out.printf("    Rectangle (%.1f × %.1f)    : %10.4f sq units%n", length, width, rectArea);
        System.out.printf("    Triangle  (b=%.1f, h=%.1f) : %10.4f sq units%n", base, height, triArea);
        System.out.printf("    Square    (s=%d)            : %10.4f sq units%n", side, sqArea);
        System.out.printf("    Trapezoid (%.1f,%.1f,h=%.1f): %10.4f sq units%n",
                sideA, sideB, trapHeight, trapArea);

        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║           All calculations completed!                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
}
