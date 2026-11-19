/*
 * Program 70: Enum Demo
 * 
 * Problem Statement:
 *   Create enums with fields, constructors, and methods. Demonstrate enums with
 *   abstract methods, EnumSet, EnumMap, and switch statements with enums.
 *   Show advanced enum features like implementing interfaces.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Basic Enum ===
 *   Today is WEDNESDAY (Midweek - hang in there!)
 *   === Enum with Fields and Methods ===
 *   Mercury: 3.303e+23 kg, 2.4397e6 m, gravity=3.70 m/s²
 *   === Enum with Abstract Methods ===
 *   ADD: 10 + 5 = 15.0
 *   MULTIPLY: 10 * 5 = 50.0
 *   === Switch with Enum ===
 *   Priority HIGH: Handle immediately!
 *   === EnumSet ===
 *   Weekend days: [SATURDAY, SUNDAY]
 *   === EnumMap ===
 *   Schedule: {MONDAY=Work, SATURDAY=Fun, SUNDAY=Rest}
 *   ...
 * 
 * Explanation:
 *   Enums in Java are special classes that represent a fixed set of constants.
 *   Unlike simple constants, Java enums can have:
 *   - Fields and constructors (always private)
 *   - Methods (concrete and abstract)
 *   - Interface implementations
 *   
 *   Key features:
 *   - Type-safe: compiler prevents invalid values
 *   - Can have behavior (methods) unlike C/C++ enums
 *   - Each constant is a singleton instance
 *   - Support switch statements
 *   - EnumSet and EnumMap provide optimized collections
 *   - values() returns all constants, valueOf() converts String
 *   - ordinal() returns position (0-based)
 *   - Enums implicitly extend java.lang.Enum (cannot extend other classes)
 *   - Enums can implement interfaces
 * 
 * Time Complexity: O(1) for enum operations, O(n) for iteration
 * Space Complexity: O(n) for n enum constants
 */

import java.util.EnumSet;
import java.util.EnumMap;
import java.util.Map;

// =================== 1. BASIC ENUM ===================

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    // Method in basic enum
    public boolean isWeekend() {
        return this == SATURDAY || this == SUNDAY;
    }

    public boolean isWeekday() {
        return !isWeekend();
    }

    public String getMotivation() {
        switch (this) {
            case MONDAY:    return "Start strong! 💪";
            case TUESDAY:   return "Keep the momentum!";
            case WEDNESDAY: return "Midweek - hang in there!";
            case THURSDAY:  return "Almost there!";
            case FRIDAY:    return "TGIF! 🎉";
            case SATURDAY:  return "Enjoy your weekend! ☀️";
            case SUNDAY:    return "Rest and recharge 😴";
            default:        return "";
        }
    }
}

// =================== 2. ENUM WITH FIELDS, CONSTRUCTOR, METHODS ===================

enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6),
    MARS   (6.421e+23, 3.3972e6),
    JUPITER(1.9e+27,   7.1492e7),
    SATURN (5.688e+26, 6.0268e7),
    URANUS (8.686e+25, 2.5559e7),
    NEPTUNE(1.024e+26, 2.4746e7);

    // Fields
    private final double mass;    // in kilograms
    private final double radius;  // in meters

    // Gravitational constant
    private static final double G = 6.67300E-11;

    // Constructor (always private for enums)
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    // Methods
    public double getMass() { return mass; }
    public double getRadius() { return radius; }

    // Calculated property
    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }

    public double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }

    @Override
    public String toString() {
        return String.format("%-8s mass=%.3e kg, radius=%.4e m, gravity=%.2f m/s²",
                             name() + ":", mass, radius, surfaceGravity());
    }
}

// =================== 3. ENUM WITH ABSTRACT METHODS ===================

enum MathOperation {
    ADD("+") {
        @Override
        public double apply(double a, double b) {
            return a + b;
        }
    },
    SUBTRACT("-") {
        @Override
        public double apply(double a, double b) {
            return a - b;
        }
    },
    MULTIPLY("*") {
        @Override
        public double apply(double a, double b) {
            return a * b;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double a, double b) {
            if (b == 0) throw new ArithmeticException("Division by zero");
            return a / b;
        }
    },
    POWER("^") {
        @Override
        public double apply(double a, double b) {
            return Math.pow(a, b);
        }
    },
    MODULO("%") {
        @Override
        public double apply(double a, double b) {
            return a % b;
        }
    };

    private final String symbol;

    MathOperation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() { return symbol; }

    // Abstract method - each constant MUST implement this
    public abstract double apply(double a, double b);

    @Override
    public String toString() {
        return name() + " (" + symbol + ")";
    }
}

// =================== 4. ENUM IMPLEMENTING INTERFACE ===================

interface Describable {
    String describe();
    int getPriorityLevel();
}

enum Priority implements Describable, Comparable<Priority> {
    LOW("Low priority", 1, "📗") {
        @Override
        public String getAction() { return "Handle when convenient"; }
    },
    MEDIUM("Medium priority", 2, "📙") {
        @Override
        public String getAction() { return "Handle within the day"; }
    },
    HIGH("High priority", 3, "📕") {
        @Override
        public String getAction() { return "Handle immediately!"; }
    },
    CRITICAL("Critical priority", 4, "🔴") {
        @Override
        public String getAction() { return "DROP EVERYTHING! Fix now!"; }
    };

    private final String description;
    private final int level;
    private final String icon;

    Priority(String description, int level, String icon) {
        this.description = description;
        this.level = level;
        this.icon = icon;
    }

    // Interface method implementations
    @Override
    public String describe() {
        return icon + " " + description;
    }

    @Override
    public int getPriorityLevel() {
        return level;
    }

    // Abstract method each constant implements
    public abstract String getAction();

    public boolean isUrgent() {
        return level >= 3;
    }
}

// =================== 5. ENUM FOR STATE MACHINE ===================

enum TrafficLight {
    RED(30) {
        @Override
        public TrafficLight next() { return GREEN; }
        @Override
        public String instruction() { return "STOP! 🔴"; }
    },
    GREEN(45) {
        @Override
        public TrafficLight next() { return YELLOW; }
        @Override
        public String instruction() { return "GO! 🟢"; }
    },
    YELLOW(5) {
        @Override
        public TrafficLight next() { return RED; }
        @Override
        public String instruction() { return "CAUTION! 🟡"; }
    };

    private final int durationSeconds;

    TrafficLight(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public int getDuration() { return durationSeconds; }
    public abstract TrafficLight next();
    public abstract String instruction();
}

// =================== 6. SEASON ENUM (Simpler example) ===================

enum Season {
    SPRING("March-May", 15),
    SUMMER("June-August", 30),
    AUTUMN("September-November", 15),
    WINTER("December-February", 0);

    private final String months;
    private final int avgTempCelsius;

    Season(String months, int avgTemp) {
        this.months = months;
        this.avgTempCelsius = avgTemp;
    }

    public String getMonths() { return months; }
    public int getAvgTemp() { return avgTempCelsius; }
    public int getAvgTempF() { return avgTempCelsius * 9 / 5 + 32; }
}

public class EnumDemo {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 70: Enum Demo                     ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // 1. Basic Enum
        System.out.println("=== 1. Basic Enum ===");
        Day today = Day.WEDNESDAY;
        System.out.println("  Today is " + today + " (" + today.getMotivation() + ")");
        System.out.println("  Is weekend? " + today.isWeekend());
        System.out.println("  Ordinal: " + today.ordinal());

        System.out.println("\n  All days:");
        for (Day d : Day.values()) {
            System.out.printf("    %-9s ordinal=%d, weekend=%b, %s%n",
                              d, d.ordinal(), d.isWeekend(), d.getMotivation());
        }

        // valueOf - String to Enum
        Day friday = Day.valueOf("FRIDAY");
        System.out.println("\n  Day.valueOf(\"FRIDAY\") = " + friday);

        // 2. Enum with Fields and Methods
        System.out.println("\n=== 2. Enum with Fields and Methods (Planet) ===");
        double earthWeight = 75.0; // kg
        double mass = earthWeight / Planet.EARTH.surfaceGravity();
        System.out.printf("  Your weight on Earth: %.1f kg%n", earthWeight);
        System.out.println("  Your weight on other planets:");
        for (Planet p : Planet.values()) {
            System.out.printf("    %s → Weight: %.2f kg%n", p, p.surfaceWeight(mass));
        }

        // 3. Enum with Abstract Methods (MathOperation)
        System.out.println("\n=== 3. Enum with Abstract Methods (MathOperation) ===");
        double a = 10, b = 3;
        for (MathOperation op : MathOperation.values()) {
            System.out.printf("  %s: %.0f %s %.0f = %.2f%n",
                              op.name(), a, op.getSymbol(), b, op.apply(a, b));
        }

        // 4. Enum implementing Interface
        System.out.println("\n=== 4. Enum Implementing Interface (Priority) ===");
        for (Priority p : Priority.values()) {
            System.out.printf("  %s → Action: %s, Urgent: %b%n",
                              p.describe(), p.getAction(), p.isUrgent());
        }

        // Using as interface type
        Describable desc = Priority.HIGH;
        System.out.println("\n  As Describable: " + desc.describe() + " (level " + desc.getPriorityLevel() + ")");

        // 5. Switch with Enum
        System.out.println("\n=== 5. Switch with Enum ===");
        Priority taskPriority = Priority.HIGH;
        switch (taskPriority) {
            case LOW:
                System.out.println("  Task is low priority - add to backlog.");
                break;
            case MEDIUM:
                System.out.println("  Task is medium priority - schedule for today.");
                break;
            case HIGH:
                System.out.println("  Task is high priority - handle immediately!");
                break;
            case CRITICAL:
                System.out.println("  CRITICAL! All hands on deck!");
                break;
        }

        // Season switch
        Season currentSeason = Season.SUMMER;
        String activity = switch (currentSeason) {
            case SPRING -> "Gardening 🌷";
            case SUMMER -> "Swimming 🏊";
            case AUTUMN -> "Hiking 🍂";
            case WINTER -> "Skiing ⛷️";
        };
        System.out.println("  Season: " + currentSeason + " → Activity: " + activity);

        // 6. EnumSet
        System.out.println("\n=== 6. EnumSet (Optimized Set for Enums) ===");

        // Create EnumSets
        EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        EnumSet<Day> weekdays = EnumSet.complementOf(weekend);
        EnumSet<Day> allDays = EnumSet.allOf(Day.class);
        EnumSet<Day> noDays = EnumSet.noneOf(Day.class);
        EnumSet<Day> earlyWeek = EnumSet.range(Day.MONDAY, Day.WEDNESDAY);

        System.out.println("  Weekend:    " + weekend);
        System.out.println("  Weekdays:   " + weekdays);
        System.out.println("  All days:   " + allDays);
        System.out.println("  No days:    " + noDays);
        System.out.println("  Mon-Wed:    " + earlyWeek);

        // EnumSet operations
        System.out.println("\n  Set Operations:");
        System.out.println("  weekend.contains(SUNDAY): " + weekend.contains(Day.SUNDAY));
        System.out.println("  weekend.contains(MONDAY): " + weekend.contains(Day.MONDAY));

        EnumSet<Priority> urgentPriorities = EnumSet.of(Priority.HIGH, Priority.CRITICAL);
        System.out.println("  Urgent priorities: " + urgentPriorities);

        // 7. EnumMap
        System.out.println("\n=== 7. EnumMap (Optimized Map with Enum Keys) ===");

        EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
        schedule.put(Day.MONDAY, "Team Meeting + Sprint Planning");
        schedule.put(Day.TUESDAY, "Deep Work - Coding");
        schedule.put(Day.WEDNESDAY, "Code Review + 1:1s");
        schedule.put(Day.THURSDAY, "Deep Work - Coding");
        schedule.put(Day.FRIDAY, "Demo + Retrospective");
        schedule.put(Day.SATURDAY, "Personal Projects");
        schedule.put(Day.SUNDAY, "Rest & Recharge");

        System.out.println("  Weekly Schedule:");
        for (Map.Entry<Day, String> entry : schedule.entrySet()) {
            String dayType = entry.getKey().isWeekend() ? "🏖️" : "💼";
            System.out.printf("    %s %-9s → %s%n", dayType, entry.getKey(), entry.getValue());
        }

        // EnumMap for Season temperatures
        EnumMap<Season, String> seasonClothing = new EnumMap<>(Season.class);
        seasonClothing.put(Season.SPRING, "Light jacket");
        seasonClothing.put(Season.SUMMER, "T-shirt & shorts");
        seasonClothing.put(Season.AUTUMN, "Sweater");
        seasonClothing.put(Season.WINTER, "Heavy coat");

        System.out.println("\n  Season Clothing Guide:");
        for (Season s : Season.values()) {
            System.out.printf("    %-6s (%s, avg %d°C/%d°F) → Wear: %s%n",
                              s, s.getMonths(), s.getAvgTemp(), s.getAvgTempF(),
                              seasonClothing.get(s));
        }

        // 8. Traffic Light State Machine
        System.out.println("\n=== 8. Enum as State Machine (TrafficLight) ===");
        TrafficLight light = TrafficLight.RED;
        System.out.println("  Simulating traffic light cycle:");
        for (int i = 0; i < 6; i++) {
            System.out.printf("    %s %s (duration: %ds) → next: %s%n",
                              light.instruction(), light, light.getDuration(), light.next());
            light = light.next();
        }

        // 9. Comparing Enums
        System.out.println("\n=== 9. Comparing Enums ===");
        Day d1 = Day.MONDAY;
        Day d2 = Day.FRIDAY;
        Day d3 = Day.MONDAY;

        System.out.println("  d1 == d3 (both MONDAY)?  " + (d1 == d3));        // true
        System.out.println("  d1 == d2 (MON vs FRI)?   " + (d1 == d2));        // false
        System.out.println("  d1.equals(d3)?           " + d1.equals(d3));      // true
        System.out.println("  d1.compareTo(d2)?        " + d1.compareTo(d2));   // negative (MON < FRI)
        System.out.println("  Note: Use == for enum comparison (not equals). It's safe and preferred.");

        // 10. Enum utility methods
        System.out.println("\n=== 10. Enum Utility Methods ===");
        System.out.println("  Day.values().length: " + Day.values().length);
        System.out.println("  Day.valueOf(\"MONDAY\"): " + Day.valueOf("MONDAY"));
        System.out.println("  Day.MONDAY.name(): " + Day.MONDAY.name());
        System.out.println("  Day.MONDAY.ordinal(): " + Day.MONDAY.ordinal());
        System.out.println("  Day.MONDAY.getClass(): " + Day.MONDAY.getClass());
        System.out.println("  Day.MONDAY.getDeclaringClass(): " + Day.MONDAY.getDeclaringClass());

        // Safe valueOf with try-catch
        try {
            Day invalid = Day.valueOf("FUNDAY");
        } catch (IllegalArgumentException e) {
            System.out.println("  Day.valueOf(\"FUNDAY\"): " + e.getMessage());
        }

        // Summary
        System.out.println("\n=== Enum Feature Summary ===");
        System.out.println("  ┌─────────────────────────────────────────────────┐");
        System.out.println("  │ Feature               │ Example                │");
        System.out.println("  ├─────────────────────────────────────────────────┤");
        System.out.println("  │ Basic constants        │ enum Day { MON, TUE }  │");
        System.out.println("  │ Fields + Constructor   │ Planet(mass, radius)   │");
        System.out.println("  │ Methods                │ day.isWeekend()        │");
        System.out.println("  │ Abstract methods       │ MathOperation.apply()  │");
        System.out.println("  │ Interface impl         │ Priority → Describable │");
        System.out.println("  │ Switch statements      │ switch(priority) {}    │");
        System.out.println("  │ EnumSet                │ EnumSet.of(SAT, SUN)   │");
        System.out.println("  │ EnumMap                │ EnumMap<Day, String>   │");
        System.out.println("  │ State machine          │ TrafficLight.next()    │");
        System.out.println("  │ values()               │ Day.values()           │");
        System.out.println("  │ valueOf(String)        │ Day.valueOf(\"MONDAY\")  │");
        System.out.println("  │ ordinal()              │ MONDAY.ordinal() → 0  │");
        System.out.println("  │ Singleton per constant │ == comparison is safe  │");
        System.out.println("  └─────────────────────────────────────────────────┘");
    }
}
