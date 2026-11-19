/*
 * Program 72: Inner Class Demo
 *
 * Problem Statement:
 *   Demonstrate all four types of inner classes in Java:
 *   1. Regular (non-static) inner class
 *   2. Static nested class
 *   3. Local inner class (defined inside a method)
 *   4. Anonymous inner class
 *
 * Sample Output:
 *   [Regular Inner] Engine started for car: Tesla Model S
 *   [Static Nested] Helper utility: HELLO WORLD
 *   [Local Inner]   Local processor result: 100
 *   [Anonymous]     Area of circle: 78.53981633974483
 *
 * Explanation:
 *   Inner classes help logically group classes, increase encapsulation,
 *   and can lead to more readable and maintainable code.
 *
 * Time Complexity:  O(1)
 * Space Complexity: O(1)
 */
public class InnerClassDemo {

    private String brand;

    public InnerClassDemo(String brand) {
        this.brand = brand;
    }

    // 1. Regular (non-static) inner class — has access to outer class instance members
    class Engine {
        private int horsepower;

        Engine(int horsepower) {
            this.horsepower = horsepower;
        }

        void start() {
            // Accesses outer class field 'brand' directly
            System.out.println("[Regular Inner] Engine started for car: " + brand
                    + " | Horsepower: " + horsepower);
        }
    }

    // 2. Static nested class — does NOT have access to outer class instance members
    static class Helper {
        static String toUpperCase(String text) {
            return text.toUpperCase();
        }

        void printInfo() {
            System.out.println("[Static Nested] Helper utility: " + toUpperCase("hello world"));
        }
    }

    // Method demonstrating local inner class
    void processNumber(int n) {
        // 3. Local inner class — defined inside a method
        class LocalProcessor {
            int square(int x) {
                return x * x;
            }
        }
        LocalProcessor lp = new LocalProcessor();
        System.out.println("[Local Inner]   Local processor result: " + lp.square(n));
    }

    // Functional interface for anonymous class demo
    interface Shape {
        double area();
    }

    public static void main(String[] args) {
        // --- Regular inner class ---
        InnerClassDemo car = new InnerClassDemo("Tesla Model S");
        InnerClassDemo.Engine engine = car.new Engine(450);
        engine.start();

        // --- Static nested class ---
        Helper helper = new Helper();
        helper.printInfo();

        // --- Local inner class ---
        car.processNumber(10);

        // --- Anonymous inner class ---
        // An anonymous class implements the Shape interface on-the-fly
        double radius = 5.0;
        Shape circle = new Shape() {
            @Override
            public double area() {
                return Math.PI * radius * radius;
            }
        };
        System.out.println("[Anonymous]     Area of circle: " + circle.area());

        // Another anonymous class example — Runnable
        System.out.println("\n--- Anonymous Runnable ---");
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("[Anonymous Runnable] Task executed on thread: "
                        + Thread.currentThread().getName());
            }
        };
        task.run();

        // Static nested class static method
        System.out.println("\n--- Static nested class static method ---");
        System.out.println("[Static Nested] " + Helper.toUpperCase("java inner classes"));
    }
}
