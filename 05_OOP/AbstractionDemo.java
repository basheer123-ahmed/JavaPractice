/*
 * Program 64: Abstraction Demo
 * 
 * Problem Statement:
 *   Demonstrate abstract classes and interfaces. Create an abstract Vehicle
 *   class and a Drivable interface, implemented by Car and Bike classes.
 *   Show how abstraction hides implementation details while exposing
 *   essential functionality.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Abstract Class: Vehicle ===
 *   Car [Toyota Camry] started. Engine: V6
 *   Car [Toyota Camry] accelerating... Speed: 60 km/h
 *   Car [Toyota Camry] fuel efficiency: 15.0 km/l
 *   Bike [Harley Davidson] started. Engine: V-Twin
 *   Bike [Harley Davidson] accelerating... Speed: 80 km/h
 *   === Interface: Drivable ===
 *   Driving Car [Toyota Camry] on road...
 *   Driving Bike [Harley Davidson] on road...
 *   ...
 * 
 * Explanation:
 *   Abstraction hides complex implementation details and shows only essential
 *   features. Java provides two mechanisms:
 *   
 *   1. Abstract Classes:
 *      - Declared with 'abstract' keyword
 *      - Can have abstract methods (no body) AND concrete methods (with body)
 *      - Can have constructors, fields, static methods
 *      - Cannot be instantiated directly
 *      - Child class MUST implement all abstract methods (or be abstract too)
 *   
 *   2. Interfaces:
 *      - Pure abstraction contract (before Java 8)
 *      - Can have default methods (Java 8+), static methods, private methods (Java 9+)
 *      - All fields are public static final (constants)
 *      - A class can implement multiple interfaces
 *      - Supports multiple inheritance of behavior
 * 
 * Time Complexity: O(1) for each operation
 * Space Complexity: O(1) for each vehicle object
 */

// =================== INTERFACE DEFINITIONS ===================

// Interface 1: Drivable - contract for anything that can be driven
interface Drivable {
    // Abstract methods (implicitly public abstract)
    void drive();
    void steer(String direction);
    void brake();

    // Default method (Java 8+) - provides default implementation
    default void honk() {
        System.out.println("  Beep! Beep!");
    }

    // Static method (Java 8+) - belongs to interface, not implementing class
    static String getDrivingRules() {
        return "Follow traffic rules. Drive on the correct side of the road.";
    }
}

// Interface 2: Maintainable - contract for things needing maintenance
interface Maintainable {
    void service();
    void checkOil();

    default void scheduleService(int months) {
        System.out.println("  Service scheduled in " + months + " months.");
    }
}

// Interface 3: Electric - for electric vehicles (demonstrates multiple interfaces)
interface Electric {
    void charge();
    int getBatteryLevel();

    default void displayBatteryStatus() {
        int level = getBatteryLevel();
        String status = level > 50 ? "Good" : level > 20 ? "Low" : "Critical";
        System.out.println("  Battery: " + level + "% [" + status + "]");
    }
}

// =================== ABSTRACT CLASS ===================

// Abstract class: Vehicle - cannot be instantiated
abstract class Vehicle implements Drivable, Maintainable {
    // Fields (can have any access modifier, unlike interfaces)
    protected String brand;
    protected String model;
    protected int year;
    protected double speed;
    protected String engineType;

    // Constructor - abstract classes CAN have constructors
    public Vehicle(String brand, String model, int year, String engineType) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engineType = engineType;
        this.speed = 0;
    }

    // Abstract methods - subclasses MUST implement these
    public abstract void startEngine();
    public abstract void stopEngine();
    public abstract double fuelEfficiency();

    // Concrete method - shared by all vehicles
    public void accelerate(double amount) {
        speed += amount;
        System.out.println("  " + getFullName() + " accelerating... Speed: " + speed + " km/h");
    }

    public void decelerate(double amount) {
        speed = Math.max(0, speed - amount);
        System.out.println("  " + getFullName() + " decelerating... Speed: " + speed + " km/h");
    }

    public String getFullName() {
        return brand + " " + model;
    }

    // Implementing Drivable interface methods (partially - can leave some for subclasses)
    @Override
    public void brake() {
        speed = 0;
        System.out.println("  " + getFullName() + " brakes applied. Stopped.");
    }

    // Implementing Maintainable interface methods
    @Override
    public void checkOil() {
        System.out.println("  " + getFullName() + " oil level: OK");
    }

    @Override
    public String toString() {
        return String.format("%s (%d) - Engine: %s, Speed: %.0f km/h",
                             getFullName(), year, engineType, speed);
    }
}

// =================== CONCRETE CLASSES ===================

// Car extends abstract Vehicle
class Car extends Vehicle {
    private int numDoors;
    private boolean isAutomatic;

    public Car(String brand, String model, int year, String engineType,
               int numDoors, boolean isAutomatic) {
        super(brand, model, year, engineType);
        this.numDoors = numDoors;
        this.isAutomatic = isAutomatic;
    }

    // Implementing abstract methods from Vehicle
    @Override
    public void startEngine() {
        System.out.println("  Car [" + getFullName() + "] started. Engine: " + engineType);
        if (isAutomatic) {
            System.out.println("    Automatic transmission engaged.");
        } else {
            System.out.println("    Manual transmission - shift to first gear.");
        }
    }

    @Override
    public void stopEngine() {
        speed = 0;
        System.out.println("  Car [" + getFullName() + "] engine stopped.");
    }

    @Override
    public double fuelEfficiency() {
        return 15.0; // km per liter
    }

    // Implementing remaining Drivable methods
    @Override
    public void drive() {
        System.out.println("  Driving Car [" + getFullName() + "] on road...");
    }

    @Override
    public void steer(String direction) {
        System.out.println("  Car [" + getFullName() + "] steering " + direction + " (power steering)");
    }

    // Implementing Maintainable
    @Override
    public void service() {
        System.out.println("  Car [" + getFullName() + "] full service: oil, brakes, tires, filters.");
    }

    // Override default honk
    @Override
    public void honk() {
        System.out.println("  Car [" + getFullName() + "]: HONK! HONK! 🚗");
    }
}

// Bike extends abstract Vehicle
class Bike extends Vehicle {
    private boolean hasGear;
    private String bikeType; // Sport, Cruiser, Touring

    public Bike(String brand, String model, int year, String engineType,
                boolean hasGear, String bikeType) {
        super(brand, model, year, engineType);
        this.hasGear = hasGear;
        this.bikeType = bikeType;
    }

    @Override
    public void startEngine() {
        System.out.println("  Bike [" + getFullName() + "] kicked started. Engine: " + engineType);
        System.out.println("    Type: " + bikeType + ", Gears: " + (hasGear ? "Yes" : "No"));
    }

    @Override
    public void stopEngine() {
        speed = 0;
        System.out.println("  Bike [" + getFullName() + "] engine stopped.");
    }

    @Override
    public double fuelEfficiency() {
        return 35.0; // km per liter (bikes are more efficient)
    }

    @Override
    public void drive() {
        System.out.println("  Riding Bike [" + getFullName() + "] on road...");
    }

    @Override
    public void steer(String direction) {
        System.out.println("  Bike [" + getFullName() + "] leaning " + direction);
    }

    @Override
    public void service() {
        System.out.println("  Bike [" + getFullName() + "] service: chain, oil, brakes.");
    }

    public void wheelie() {
        System.out.println("  Bike [" + getFullName() + "] does a wheelie! 🏍️");
    }
}

// ElectricCar - implements Electric interface too (multiple interfaces)
class ElectricCar extends Vehicle implements Electric {
    private int batteryLevel;

    public ElectricCar(String brand, String model, int year) {
        super(brand, model, year, "Electric Motor");
        this.batteryLevel = 100;
    }

    @Override
    public void startEngine() {
        System.out.println("  EV [" + getFullName() + "] silently started. Battery: " + batteryLevel + "%");
    }

    @Override
    public void stopEngine() {
        speed = 0;
        System.out.println("  EV [" + getFullName() + "] stopped. Regenerative braking recovered energy.");
    }

    @Override
    public double fuelEfficiency() {
        return 6.0; // km per kWh
    }

    @Override
    public void drive() {
        batteryLevel -= 5; // Driving uses battery
        System.out.println("  Driving EV [" + getFullName() + "]. Battery: " + batteryLevel + "%");
    }

    @Override
    public void steer(String direction) {
        System.out.println("  EV [" + getFullName() + "] steering " + direction);
    }

    @Override
    public void service() {
        System.out.println("  EV [" + getFullName() + "] service: battery check, software update.");
    }

    // Electric interface methods
    @Override
    public void charge() {
        batteryLevel = 100;
        System.out.println("  EV [" + getFullName() + "] fully charged! 🔋");
    }

    @Override
    public int getBatteryLevel() {
        return batteryLevel;
    }
}

public class AbstractionDemo {
    // Method that works with ANY Drivable - doesn't care about concrete type
    public static void testDrive(Drivable vehicle) {
        vehicle.drive();
        vehicle.steer("left");
        vehicle.honk();
        vehicle.brake();
    }

    // Method that works with ANY Vehicle - demonstrates abstract class as type
    public static void displayVehicleInfo(Vehicle vehicle) {
        System.out.println("  Vehicle: " + vehicle);
        System.out.printf("  Fuel Efficiency: %.1f km/unit%n", vehicle.fuelEfficiency());
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 64: Abstraction Demo              ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // Cannot instantiate abstract class
        // Vehicle v = new Vehicle(...); // Compile error!

        // 1. Creating concrete objects
        System.out.println("=== Creating Vehicles ===");
        Car car = new Car("Toyota", "Camry", 2024, "V6", 4, true);
        Bike bike = new Bike("Harley", "Davidson", 2023, "V-Twin", true, "Cruiser");
        ElectricCar ev = new ElectricCar("Tesla", "Model 3", 2024);

        // 2. Abstract class methods
        System.out.println("\n=== Abstract Class: Starting Engines ===");
        car.startEngine();
        bike.startEngine();
        ev.startEngine();

        // 3. Concrete methods from abstract class
        System.out.println("\n=== Concrete Methods (from Abstract Class) ===");
        car.accelerate(60);
        car.decelerate(20);
        bike.accelerate(80);

        // 4. Polymorphism through abstract class
        System.out.println("\n=== Polymorphism via Abstract Class ===");
        Vehicle[] vehicles = { car, bike, ev };
        for (Vehicle v : vehicles) {
            displayVehicleInfo(v);
            System.out.println();
        }

        // 5. Interface-based polymorphism
        System.out.println("=== Interface: Drivable Polymorphism ===");
        System.out.println("--- Testing Car ---");
        testDrive(car);
        System.out.println("--- Testing Bike ---");
        testDrive(bike);
        System.out.println("--- Testing Electric Car ---");
        testDrive(ev);

        // 6. Multiple interface implementation
        System.out.println("\n=== Multiple Interfaces: Electric + Drivable + Maintainable ===");
        ev.drive();
        ev.drive();
        ev.displayBatteryStatus(); // From Electric interface default method
        ev.charge();
        ev.displayBatteryStatus();

        // 7. Interface static method
        System.out.println("\n=== Interface Static Method ===");
        System.out.println("  " + Drivable.getDrivingRules());

        // 8. Maintainable interface
        System.out.println("\n=== Maintainable Interface ===");
        for (Vehicle v : vehicles) {
            v.service();
            v.checkOil();
            v.scheduleService(6); // Default method
            System.out.println();
        }

        // 9. Interface as type for reference
        System.out.println("=== Interface as Reference Type ===");
        Drivable[] drivables = { car, bike, ev };
        for (Drivable d : drivables) {
            d.drive();
            // d.startEngine(); // ERROR: Drivable doesn't know about startEngine
        }

        // 10. Electric interface reference
        System.out.println("\n=== Electric Interface Reference ===");
        Electric electricVehicle = ev; // Interface reference
        electricVehicle.charge();
        electricVehicle.displayBatteryStatus();
        // electricVehicle.drive(); // ERROR: Electric doesn't know about drive

        // Summary
        System.out.println("\n=== Abstract Class vs Interface Summary ===");
        System.out.println("  ┌───────────────────┬────────────────────┬────────────────────┐");
        System.out.println("  │ Feature           │ Abstract Class     │ Interface          │");
        System.out.println("  ├───────────────────┼────────────────────┼────────────────────┤");
        System.out.println("  │ Methods           │ Abstract+Concrete  │ Abstract+Default   │");
        System.out.println("  │ Fields            │ Any type           │ public static final│");
        System.out.println("  │ Constructor       │ Yes                │ No                 │");
        System.out.println("  │ Multiple Inherit  │ No                 │ Yes                │");
        System.out.println("  │ Access Modifiers  │ Any                │ public (methods)   │");
        System.out.println("  │ When to use       │ IS-A + shared code │ CAN-DO contract    │");
        System.out.println("  └───────────────────┴────────────────────┴────────────────────┘");
    }
}
