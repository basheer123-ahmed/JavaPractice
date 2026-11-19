/*
 * Program 63: Polymorphism Demo
 * 
 * Problem Statement:
 *   Demonstrate compile-time polymorphism (method overloading) and runtime
 *   polymorphism (method overriding) with a Shape hierarchy. Show how the
 *   same method name can behave differently based on parameters or object type.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Compile-Time Polymorphism (Method Overloading) ===
 *   add(int, int): 30
 *   add(int, int, int): 60
 *   add(double, double): 30.5
 *   add(String, String): HelloWorld
 *   === Runtime Polymorphism (Method Overriding) ===
 *   Drawing Circle: radius=5.0 at (0,0)
 *   Area of Circle: 78.54
 *   Drawing Rectangle: width=4.0, height=6.0 at (0,0)
 *   Area of Rectangle: 24.00
 *   Drawing Triangle: base=3.0, height=8.0 at (0,0)
 *   Area of Triangle: 12.00
 *   ...
 * 
 * Explanation:
 *   Polymorphism means "many forms". In Java, it manifests in two ways:
 *   
 *   1. Compile-time (Static) Polymorphism - Method Overloading:
 *      - Same method name with different parameter lists
 *      - Resolved at compile time based on method signature
 *      - Can differ in: number of params, type of params, order of params
 *   
 *   2. Runtime (Dynamic) Polymorphism - Method Overriding:
 *      - Child class provides specific implementation of parent's method
 *      - Resolved at runtime based on actual object type (not reference type)
 *      - Requires inheritance and @Override annotation
 *      - Enables programming to interfaces/abstract types
 * 
 * Time Complexity: O(1) for each shape operation
 * Space Complexity: O(n) for array of n shapes
 */

// ====================== RUNTIME POLYMORPHISM ======================

// Base class for Shape hierarchy
abstract class Shape {
    protected String color;
    protected double x, y; // position

    public Shape(String color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Shape(String color) {
        this(color, 0, 0);
    }

    // Abstract methods - MUST be overridden by subclasses
    public abstract double area();
    public abstract double perimeter();
    public abstract void draw();

    // Concrete method - CAN be overridden
    public String describe() {
        return String.format("[%s shape at (%.0f,%.0f)]", color, x, y);
    }

    @Override
    public String toString() {
        return String.format("%s → Area: %.2f, Perimeter: %.2f", describe(), area(), perimeter());
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius, String color) {
        super(color);
        this.radius = radius;
    }

    public Circle(double radius, String color, double x, double y) {
        super(color, x, y);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void draw() {
        System.out.printf("  Drawing Circle: radius=%.1f at (%.0f,%.0f) [%s]%n", radius, x, y, color);
    }

    @Override
    public String describe() {
        return String.format("[%s Circle, r=%.1f]", color, radius);
    }
}

class Rectangle extends Shape {
    protected double width, height;

    public Rectangle(double width, double height, String color) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public void draw() {
        System.out.printf("  Drawing Rectangle: %.1f x %.1f at (%.0f,%.0f) [%s]%n",
                          width, height, x, y, color);
    }

    @Override
    public String describe() {
        return String.format("[%s Rectangle, %.1f×%.1f]", color, width, height);
    }
}

class Square extends Rectangle {
    public Square(double side, String color) {
        super(side, side, color);
    }

    @Override
    public void draw() {
        System.out.printf("  Drawing Square: side=%.1f at (%.0f,%.0f) [%s]%n",
                          width, x, y, color);
    }

    @Override
    public String describe() {
        return String.format("[%s Square, side=%.1f]", color, width);
    }
}

class Triangle extends Shape {
    private double base, height, sideA, sideB;

    // Constructor for right triangle (base, height known, sides computed)
    public Triangle(double base, double height, String color) {
        super(color);
        this.base = base;
        this.height = height;
        // Compute hypotenuse for a right triangle
        double hypotenuse = Math.sqrt(base * base + height * height);
        this.sideA = height;
        this.sideB = hypotenuse;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }

    @Override
    public double perimeter() {
        return base + sideA + sideB;
    }

    @Override
    public void draw() {
        System.out.printf("  Drawing Triangle: base=%.1f, height=%.1f at (%.0f,%.0f) [%s]%n",
                          base, height, x, y, color);
    }

    @Override
    public String describe() {
        return String.format("[%s Triangle, base=%.1f, h=%.1f]", color, base, height);
    }
}

// ====================== COMPILE-TIME POLYMORPHISM ======================

class MathOperations {
    // Overloaded add methods - same name, different parameter lists

    // add(int, int)
    public int add(int a, int b) {
        System.out.print("  add(int, int): ");
        return a + b;
    }

    // add(int, int, int) - different number of parameters
    public int add(int a, int b, int c) {
        System.out.print("  add(int, int, int): ");
        return a + b + c;
    }

    // add(double, double) - different parameter types
    public double add(double a, double b) {
        System.out.print("  add(double, double): ");
        return a + b;
    }

    // add(String, String) - different parameter types (concatenation)
    public String add(String a, String b) {
        System.out.print("  add(String, String): ");
        return a + b;
    }

    // Overloaded multiply methods
    public int multiply(int a, int b) {
        return a * b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    // Varargs overload
    public int add(int... numbers) {
        System.out.print("  add(int...): ");
        int sum = 0;
        for (int n : numbers) sum += n;
        return sum;
    }
}

// Demonstrate overloading in constructors
class Point {
    private double x, y, z;

    // No-arg constructor
    public Point() {
        this(0, 0, 0);
        System.out.println("  Point() → origin");
    }

    // 2D point
    public Point(double x, double y) {
        this(x, y, 0);
        System.out.println("  Point(x, y) → 2D point");
    }

    // 3D point
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        System.out.println("  Point(x, y, z) → 3D point");
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f, %.1f)", x, y, z);
    }
}

public class PolymorphismDemo {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 63: Polymorphism Demo             ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // =================== COMPILE-TIME POLYMORPHISM ===================
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  COMPILE-TIME POLYMORPHISM (Method Overloading)");
        System.out.println("═══════════════════════════════════════════════\n");

        MathOperations math = new MathOperations();

        System.out.println("--- Overloaded add() methods ---");
        System.out.println(math.add(10, 20));           // add(int, int)
        System.out.println(math.add(10, 20, 30));       // add(int, int, int)
        System.out.println(math.add(10.5, 20.0));       // add(double, double)
        System.out.println(math.add("Hello", "World")); // add(String, String)

        System.out.println("\n--- Varargs Overload ---");
        System.out.println(math.add(1, 2, 3, 4, 5));   // add(int...)

        System.out.println("\n--- Overloaded Constructors ---");
        Point p1 = new Point();              // No-arg
        System.out.println("  p1 = " + p1);
        Point p2 = new Point(3.0, 4.0);     // 2D
        System.out.println("  p2 = " + p2);
        Point p3 = new Point(1.0, 2.0, 3.0); // 3D
        System.out.println("  p3 = " + p3);

        // =================== RUNTIME POLYMORPHISM ===================
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  RUNTIME POLYMORPHISM (Method Overriding)");
        System.out.println("═══════════════════════════════════════════════\n");

        // Create specific shape objects
        System.out.println("--- Creating Shapes ---");
        Circle circle = new Circle(5.0, "Red");
        Rectangle rect = new Rectangle(4.0, 6.0, "Blue");
        Triangle tri = new Triangle(3.0, 8.0, "Green");
        Square square = new Square(5.0, "Yellow");

        // Draw each shape
        System.out.println("\n--- Drawing Individual Shapes ---");
        circle.draw();
        System.out.printf("  Area: %.2f, Perimeter: %.2f%n", circle.area(), circle.perimeter());
        rect.draw();
        System.out.printf("  Area: %.2f, Perimeter: %.2f%n", rect.area(), rect.perimeter());
        tri.draw();
        System.out.printf("  Area: %.2f, Perimeter: %.2f%n", tri.area(), tri.perimeter());
        square.draw();
        System.out.printf("  Area: %.2f, Perimeter: %.2f%n", square.area(), square.perimeter());

        // POLYMORPHISM IN ACTION: Parent reference, child objects
        System.out.println("\n--- Polymorphic Array (Parent reference → Child objects) ---");
        Shape[] shapes = { circle, rect, tri, square,
                          new Circle(3.0, "Purple"),
                          new Rectangle(10.0, 2.0, "Orange") };

        for (Shape shape : shapes) {
            shape.draw();  // Calls the OVERRIDDEN method based on actual object type
            System.out.println("    " + shape); // Calls overridden toString → describe
        }

        // Calculate total area - polymorphism makes this clean
        System.out.println("\n--- Total Area Calculation (Polymorphism Power) ---");
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.area(); // Each shape calculates its own area
        }
        System.out.printf("  Total area of all shapes: %.2f%n", totalArea);

        // Type checking with instanceof
        System.out.println("\n--- Type Checking and Downcasting ---");
        for (Shape shape : shapes) {
            if (shape instanceof Square s) {        // Pattern matching (Java 16+)
                System.out.println("  Found a Square: " + s.describe());
            } else if (shape instanceof Rectangle r) {
                System.out.println("  Found a Rectangle: " + r.describe());
            } else if (shape instanceof Circle c) {
                System.out.println("  Found a Circle: " + c.describe());
            } else if (shape instanceof Triangle t) {
                System.out.println("  Found a Triangle: " + t.describe());
            }
        }

        // Covariant return types (bonus concept)
        System.out.println("\n--- Key Polymorphism Rules ---");
        System.out.println("  1. Method overloading = compile-time (static binding)");
        System.out.println("  2. Method overriding = runtime (dynamic binding)");
        System.out.println("  3. Overloading: same name, different params");
        System.out.println("  4. Overriding: same name AND same params in subclass");
        System.out.println("  5. @Override annotation catches mistakes at compile time");
        System.out.println("  6. 'final' methods cannot be overridden");
        System.out.println("  7. 'static' methods are hidden, not overridden");
        System.out.println("  8. Overriding method can have covariant return type");
        System.out.println("  9. Overriding method cannot reduce visibility");
        System.out.println("  10. Overriding method cannot throw broader checked exceptions");
    }
}
