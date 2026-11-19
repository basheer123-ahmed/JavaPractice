/*
 * Program 68: This and Super Keyword
 * 
 * Problem Statement:
 *   Demonstrate 'this' keyword usage (constructor chaining, returning current
 *   object, passing as parameter, referring to instance variable) and 'super'
 *   keyword usage (access parent members, constructor call, method call).
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === 'this' for Instance Variables ===
 *   Person{name='Alice', age=25}
 *   === 'this()' for Constructor Chaining ===
 *   this(name) called → this(name, 0) called → this(name, age, "Unknown") called
 *   === 'this' as Return Value (Method Chaining) ===
 *   Builder result: Product{name='Laptop', price=999.99, category='Electronics'}
 *   === 'super' for Parent Constructor ===
 *   Parent constructor → Child constructor
 *   === 'super' for Parent Method ===
 *   Parent greet: Hello! + Child greet: Hi there!
 *   ...
 * 
 * Explanation:
 *   'this' keyword refers to the current object instance:
 *   1. this.field - distinguishes instance variable from parameter
 *   2. this() - calls another constructor in the same class (chaining)
 *   3. return this - returns current object (enables method chaining / fluent API)
 *   4. this as parameter - passes current object to another method
 *   5. this.method() - explicitly calls current object's method
 *   
 *   'super' keyword refers to the parent class:
 *   1. super() - calls parent constructor (must be first statement)
 *   2. super.method() - calls parent's version of overridden method
 *   3. super.field - accesses parent's field (if accessible)
 * 
 * Time Complexity: O(1) for each keyword demonstration
 * Space Complexity: O(1) for each object
 */

import java.util.ArrayList;
import java.util.List;

// =================== 'THIS' KEYWORD DEMONSTRATIONS ===================

// 1. 'this' to refer to instance variables
class Person {
    private String name;
    private int age;

    // 'this' distinguishes instance variable from parameter with same name
    public Person(String name, int age) {
        this.name = name;  // this.name = instance variable, name = parameter
        this.age = age;    // Without 'this', it would be self-assignment of param
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

// 2. 'this()' for constructor chaining
class Student68 {
    private String name;
    private int age;
    private String department;
    private double gpa;

    // Constructor 1: All fields
    public Student68(String name, int age, String department, double gpa) {
        // Cannot call this() AND super() both - only one allowed, must be first line
        this.name = name;
        this.age = age;
        this.department = department;
        this.gpa = gpa;
        System.out.println("    → Full constructor called (name, age, dept, gpa)");
    }

    // Constructor 2: Chains to Constructor 1
    public Student68(String name, int age, String department) {
        this(name, age, department, 0.0); // Calls Constructor 1
        System.out.println("    → 3-param constructor called (name, age, dept)");
    }

    // Constructor 3: Chains to Constructor 2
    public Student68(String name, int age) {
        this(name, age, "Undeclared"); // Calls Constructor 2
        System.out.println("    → 2-param constructor called (name, age)");
    }

    // Constructor 4: Chains to Constructor 3
    public Student68(String name) {
        this(name, 18); // Calls Constructor 3
        System.out.println("    → 1-param constructor called (name)");
    }

    // Constructor 5: Default chains to Constructor 4
    public Student68() {
        this("Unknown"); // Calls Constructor 4
        System.out.println("    → Default constructor called ()");
    }

    @Override
    public String toString() {
        return String.format("Student{name='%s', age=%d, dept='%s', gpa=%.1f}",
                             name, age, department, gpa);
    }
}

// 3. 'this' as return value (Method Chaining / Fluent API / Builder Pattern)
class ProductBuilder {
    private String name;
    private double price;
    private String category;
    private String description;
    private boolean inStock;

    // Each setter returns 'this' for chaining
    public ProductBuilder setName(String name) {
        this.name = name;
        return this; // Returns current object
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setInStock(boolean inStock) {
        this.inStock = inStock;
        return this;
    }

    public String build() {
        return String.format("Product{name='%s', price=%.2f, category='%s', desc='%s', inStock=%b}",
                             name, price, category, description, inStock);
    }
}

// 4. 'this' as a method parameter
class EventSource {
    private String name;
    private List<EventListener68> listeners = new ArrayList<>();

    public EventSource(String name) {
        this.name = name;
    }

    public void addListener(EventListener68 listener) {
        listeners.add(listener);
    }

    public void fireEvent(String event) {
        System.out.println("  [" + name + "] Firing event: " + event);
        for (EventListener68 listener : listeners) {
            listener.onEvent(this, event); // Passing 'this' as argument
        }
    }

    public String getName() { return name; }
}

class EventListener68 {
    private String listenerName;

    public EventListener68(String listenerName) {
        this.listenerName = listenerName;
    }

    // Receives the source object (via 'this' from the caller)
    public void onEvent(EventSource source, String event) {
        System.out.println("    [" + listenerName + "] Received '" + event + "' from " + source.getName());
    }
}

// =================== 'SUPER' KEYWORD DEMONSTRATIONS ===================

// Parent class for 'super' demos
class Animal68 {
    protected String name;
    protected String species;
    protected int age;

    public Animal68() {
        System.out.println("    → Animal default constructor (no-arg)");
        this.name = "Unknown";
        this.species = "Unknown";
        this.age = 0;
    }

    public Animal68(String name, String species, int age) {
        System.out.println("    → Animal parameterized constructor (name, species, age)");
        this.name = name;
        this.species = species;
        this.age = age;
    }

    public String greet() {
        return "Hello, I'm " + name + " the " + species + "!";
    }

    public void showDetails() {
        System.out.println("  [Animal] Name: " + name + ", Species: " + species + ", Age: " + age);
    }

    public String getType() {
        return "Animal";
    }
}

// 5. 'super()' to call parent constructor
class Dog68 extends Animal68 {
    private String breed;
    private boolean isTrained;

    // super() calls parent constructor
    public Dog68(String name, int age, String breed, boolean isTrained) {
        super(name, "Dog", age); // MUST be first statement in constructor
        this.breed = breed;
        this.isTrained = isTrained;
        System.out.println("    → Dog constructor (name, age, breed, isTrained)");
    }

    // If we don't call super() explicitly, Java auto-calls super() (no-arg)
    public Dog68(String name) {
        // super() is called implicitly here if not written
        // But Animal has no-arg constructor, so it works
        super(); // Could omit this, but explicit is clearer
        this.name = name; // Setting inherited field after super()
        this.breed = "Mixed";
        this.isTrained = false;
        System.out.println("    → Dog single-arg constructor");
    }

    // 6. super.method() to call parent's overridden method
    @Override
    public String greet() {
        String parentGreet = super.greet(); // Call parent's version
        return parentGreet + " I'm a " + breed + " dog!";
    }

    // 7. super.field to access parent's field
    @Override
    public void showDetails() {
        super.showDetails(); // Call parent's showDetails first
        System.out.println("  [Dog]    Breed: " + breed + ", Trained: " + isTrained);
    }

    @Override
    public String getType() {
        return super.getType() + " → Dog"; // Builds on parent's type
    }
}

// Multilevel: GuideDog extends Dog68
class GuideDog extends Dog68 {
    private String handler;

    public GuideDog(String name, int age, String breed, String handler) {
        super(name, age, breed, true); // Calls Dog68 → Animal68
        this.handler = handler;
        System.out.println("    → GuideDog constructor");
    }

    @Override
    public String greet() {
        return super.greet() + " I'm a guide dog for " + handler + ".";
    }

    @Override
    public void showDetails() {
        super.showDetails(); // Calls Dog68 → Animal68
        System.out.println("  [Guide]  Handler: " + handler);
    }

    @Override
    public String getType() {
        return super.getType() + " → GuideDog";
    }
}

// =================== COMBINING THIS AND SUPER ===================

class Base {
    protected int value;

    public Base(int value) {
        this.value = value;
        System.out.println("    Base constructor: value=" + value);
    }

    public void display() {
        System.out.println("  Base.display(): value=" + value);
    }
}

class Middle extends Base {
    protected int middleValue;

    public Middle(int value, int middleValue) {
        super(value);  // 'super' calls Base constructor
        this.middleValue = middleValue;
        System.out.println("    Middle constructor: middleValue=" + middleValue);
    }

    // Constructor chaining within Middle
    public Middle(int value) {
        this(value, value * 2);  // 'this' chains to other Middle constructor
        System.out.println("    Middle single-arg constructor");
    }

    @Override
    public void display() {
        super.display();  // 'super' calls Base.display()
        System.out.println("  Middle.display(): middleValue=" + middleValue);
    }
}

class Child extends Middle {
    private int childValue;

    public Child(int value, int middleValue, int childValue) {
        super(value, middleValue);  // 'super' calls Middle constructor
        this.childValue = childValue;
        System.out.println("    Child constructor: childValue=" + childValue);
    }

    @Override
    public void display() {
        super.display();  // Calls Middle.display() which calls Base.display()
        System.out.println("  Child.display(): childValue=" + childValue);
    }
}

public class ThisAndSuperKeyword {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 68: This and Super Keyword        ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // =================== THIS KEYWORD ===================
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  'THIS' KEYWORD");
        System.out.println("═══════════════════════════════════════════\n");

        // 1. this to refer to instance variables
        System.out.println("--- 1. this.field (Distinguish instance from parameter) ---");
        Person p = new Person("Alice", 25);
        System.out.println("  " + p);

        // 2. this() for constructor chaining
        System.out.println("\n--- 2. this() Constructor Chaining ---");
        System.out.println("  Creating with no args (chains through all constructors):");
        Student68 s1 = new Student68();
        System.out.println("  Result: " + s1);

        System.out.println("\n  Creating with name only:");
        Student68 s2 = new Student68("Bob");
        System.out.println("  Result: " + s2);

        System.out.println("\n  Creating with all args (direct, no chaining):");
        Student68 s3 = new Student68("Charlie", 22, "CS", 3.8);
        System.out.println("  Result: " + s3);

        // 3. this as return value (Method Chaining / Builder)
        System.out.println("\n--- 3. return this (Method Chaining / Builder) ---");
        String product = new ProductBuilder()
            .setName("Laptop")
            .setPrice(999.99)
            .setCategory("Electronics")
            .setDescription("High performance laptop")
            .setInStock(true)
            .build();
        System.out.println("  " + product);

        // Another chained build
        String product2 = new ProductBuilder()
            .setName("Book")
            .setPrice(19.99)
            .setCategory("Education")
            .setDescription("Java Programming")
            .setInStock(true)
            .build();
        System.out.println("  " + product2);

        // 4. this as method parameter
        System.out.println("\n--- 4. this as Method Parameter ---");
        EventSource button = new EventSource("SubmitButton");
        button.addListener(new EventListener68("FormHandler"));
        button.addListener(new EventListener68("Logger"));
        button.fireEvent("CLICK");
        button.fireEvent("DOUBLE_CLICK");

        // =================== SUPER KEYWORD ===================
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("  'SUPER' KEYWORD");
        System.out.println("═══════════════════════════════════════════\n");

        // 5. super() - Parent Constructor
        System.out.println("--- 5. super() Call Parent Constructor ---");
        System.out.println("  Creating Dog with parameterized constructor:");
        Dog68 dog1 = new Dog68("Buddy", 5, "Labrador", true);

        System.out.println("\n  Creating Dog with single-arg (triggers implicit super()):");
        Dog68 dog2 = new Dog68("Rex");

        // 6. super.method() - Call Parent's Method
        System.out.println("\n--- 6. super.method() Call Parent's Overridden Method ---");
        System.out.println("  Dog greet (combines parent + own):");
        System.out.println("  " + dog1.greet());

        // 7. super.field - Access Parent's Field
        System.out.println("\n--- 7. super.showDetails() Chain ---");
        dog1.showDetails(); // Calls both Animal's and Dog's showDetails

        // 8. Multilevel super chain
        System.out.println("\n--- 8. Multilevel super Chain (GuideDog → Dog → Animal) ---");
        System.out.println("  Constructor chain:");
        GuideDog guide = new GuideDog("Rex", 3, "German Shepherd", "John");

        System.out.println("\n  Greet chain:");
        System.out.println("  " + guide.greet());

        System.out.println("\n  Show details chain:");
        guide.showDetails();

        System.out.println("\n  Type chain:");
        System.out.println("  " + guide.getType());

        // =================== COMBINING THIS AND SUPER ===================
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("  COMBINING 'THIS' AND 'SUPER'");
        System.out.println("═══════════════════════════════════════════\n");

        System.out.println("--- Constructor chain: Base → Middle → Child ---");
        Child child = new Child(10, 20, 30);

        System.out.println("\n--- Display chain (super.display() at each level) ---");
        child.display();

        System.out.println("\n--- Middle with this() chaining ---");
        Middle mid = new Middle(5); // Uses this(5, 10) internally

        // Summary
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("  SUMMARY");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  ┌─────────────┬──────────────────────────────────────────┐");
        System.out.println("  │ this.field   │ Refer to current object's field         │");
        System.out.println("  │ this()       │ Call another constructor (same class)   │");
        System.out.println("  │ return this  │ Return current object (method chaining) │");
        System.out.println("  │ this as param│ Pass current object to another method   │");
        System.out.println("  ├─────────────┼──────────────────────────────────────────┤");
        System.out.println("  │ super.field  │ Access parent's field                   │");
        System.out.println("  │ super()      │ Call parent constructor (must be 1st)   │");
        System.out.println("  │ super.method │ Call parent's overridden method         │");
        System.out.println("  ├─────────────┼──────────────────────────────────────────┤");
        System.out.println("  │ RULE         │ this() and super() cannot both be first │");
        System.out.println("  │ RULE         │ Both must be first statement if used    │");
        System.out.println("  └─────────────┴──────────────────────────────────────────┘");
    }
}
