/*
 * Program 62: Inheritance Demo
 * 
 * Problem Statement:
 *   Demonstrate single, multilevel, and hierarchical inheritance with an
 *   Animal->Dog->Puppy (multilevel) and Animal->Cat (hierarchical) hierarchy.
 *   Use super keyword to access parent class members and constructors.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Single Inheritance: Animal -> Dog ===
 *   [Animal Constructor] Creating: Buddy (Dog breed)
 *   [Dog Constructor] Breed: Labrador
 *   Buddy says: Woof! Woof!
 *   Buddy fetches the ball!
 *   === Multilevel Inheritance: Animal -> Dog -> Puppy ===
 *   [Animal Constructor] Creating: Tiny (Dog breed)
 *   [Dog Constructor] Breed: Beagle
 *   [Puppy Constructor] Toy: Teddy Bear
 *   Tiny says: Yip! Yip!
 *   Tiny plays with Teddy Bear
 *   === Hierarchical Inheritance: Animal -> Cat ===
 *   [Animal Constructor] Creating: Whiskers (Persian)
 *   [Cat Constructor] Indoor: true
 *   Whiskers says: Meow!
 *   Whiskers purrs contentedly...
 *   ...
 * 
 * Explanation:
 *   Inheritance allows a class to acquire properties and methods of another class.
 *   - Single Inheritance: One child class extends one parent (Animal -> Dog)
 *   - Multilevel Inheritance: Chain of inheritance (Animal -> Dog -> Puppy)
 *   - Hierarchical Inheritance: Multiple classes extend same parent (Dog, Cat extend Animal)
 *   - The 'super' keyword is used to:
 *     1. Call parent constructor: super(args)
 *     2. Access parent methods: super.method()
 *     3. Access parent fields: super.field
 *   - Java does NOT support multiple inheritance with classes (uses interfaces instead)
 *   - Constructor chaining: child constructor calls parent constructor first
 * 
 * Time Complexity: O(1) for each method call
 * Space Complexity: O(1) for object creation
 */

// Base class (Parent / Superclass)
class Animal {
    protected String name;
    protected int age;
    protected String species;

    // Constructor
    public Animal(String name, int age, String species) {
        this.name = name;
        this.age = age;
        this.species = species;
        System.out.println("  [Animal Constructor] Creating: " + name + " (" + species + ")");
    }

    // Methods
    public void eat() {
        System.out.println("  " + name + " is eating.");
    }

    public void sleep() {
        System.out.println("  " + name + " is sleeping. Zzz...");
    }

    public String makeSound() {
        return name + " makes a generic sound.";
    }

    public void displayInfo() {
        System.out.println("  Name: " + name + ", Age: " + age + ", Species: " + species);
    }

    @Override
    public String toString() {
        return "Animal{name='" + name + "', age=" + age + ", species='" + species + "'}";
    }
}

// Single Inheritance: Dog extends Animal
class Dog extends Animal {
    private String breed;
    private boolean isTrained;

    public Dog(String name, int age, String breed, boolean isTrained) {
        // 'super' calls parent constructor - MUST be first statement
        super(name, age, "Dog breed");
        this.breed = breed;
        this.isTrained = isTrained;
        System.out.println("  [Dog Constructor] Breed: " + breed);
    }

    // Method overriding - provides specific implementation
    @Override
    public String makeSound() {
        return name + " says: Woof! Woof!";
    }

    // Dog-specific method
    public void fetch() {
        System.out.println("  " + name + " fetches the ball!");
    }

    // Using super to call parent method
    public void showFullInfo() {
        super.displayInfo(); // Call parent's displayInfo
        System.out.println("  Breed: " + breed + ", Trained: " + isTrained);
    }

    @Override
    public String toString() {
        return "Dog{name='" + name + "', breed='" + breed + "', trained=" + isTrained + "}";
    }
}

// Multilevel Inheritance: Puppy extends Dog extends Animal
class Puppy extends Dog {
    private String favoriteToy;
    private int monthsOld;

    public Puppy(String name, int monthsOld, String breed, String favoriteToy) {
        // Calls Dog's constructor which calls Animal's constructor
        super(name, monthsOld / 12, breed, false); // Puppies aren't trained yet
        this.favoriteToy = favoriteToy;
        this.monthsOld = monthsOld;
        System.out.println("  [Puppy Constructor] Toy: " + favoriteToy);
    }

    // Override again at the third level
    @Override
    public String makeSound() {
        return name + " says: Yip! Yip!";
    }

    public void play() {
        System.out.println("  " + name + " plays with " + favoriteToy);
    }

    // Demonstrate calling methods from different levels
    public void fullRoutine() {
        System.out.println("  --- " + name + "'s Daily Routine ---");
        super.eat();             // Animal's eat (inherited through Dog)
        play();                  // Puppy's own method
        System.out.println("  " + makeSound()); // Puppy's overridden makeSound
        super.sleep();           // Animal's sleep (inherited through Dog)
    }

    @Override
    public String toString() {
        return "Puppy{name='" + name + "', monthsOld=" + monthsOld +
               "', toy='" + favoriteToy + "'}";
    }
}

// Hierarchical Inheritance: Cat also extends Animal (sibling of Dog)
class Cat extends Animal {
    private boolean isIndoor;
    private String furColor;

    public Cat(String name, int age, String furColor, boolean isIndoor) {
        super(name, age, "Cat breed");
        this.furColor = furColor;
        this.isIndoor = isIndoor;
        System.out.println("  [Cat Constructor] Indoor: " + isIndoor);
    }

    @Override
    public String makeSound() {
        return name + " says: Meow!";
    }

    public void purr() {
        System.out.println("  " + name + " purrs contentedly...");
    }

    public void scratch() {
        System.out.println("  " + name + " scratches the furniture!");
    }

    @Override
    public String toString() {
        return "Cat{name='" + name + "', furColor='" + furColor + "', indoor=" + isIndoor + "}";
    }
}

// Another hierarchical branch: Bird extends Animal
class Bird extends Animal {
    private double wingspan;
    private boolean canFly;

    public Bird(String name, int age, double wingspan, boolean canFly) {
        super(name, age, "Bird species");
        this.wingspan = wingspan;
        this.canFly = canFly;
        System.out.println("  [Bird Constructor] Wingspan: " + wingspan + "m, Flies: " + canFly);
    }

    @Override
    public String makeSound() {
        return name + " says: Tweet! Tweet!";
    }

    public void fly() {
        if (canFly) {
            System.out.println("  " + name + " soars through the sky! (wingspan: " + wingspan + "m)");
        } else {
            System.out.println("  " + name + " flaps but cannot fly.");
        }
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 62: Inheritance Demo              ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // 1. Single Inheritance: Animal -> Dog
        System.out.println("=== Single Inheritance: Animal -> Dog ===");
        Dog dog = new Dog("Buddy", 5, "Labrador", true);
        System.out.println("  " + dog.makeSound());
        dog.fetch();
        dog.eat();      // Inherited from Animal
        dog.showFullInfo();

        // 2. Multilevel Inheritance: Animal -> Dog -> Puppy
        System.out.println("\n=== Multilevel Inheritance: Animal -> Dog -> Puppy ===");
        Puppy puppy = new Puppy("Tiny", 4, "Beagle", "Teddy Bear");
        System.out.println("  " + puppy.makeSound());
        puppy.play();
        puppy.fetch();  // Inherited from Dog
        puppy.eat();    // Inherited from Animal (through Dog)
        System.out.println();
        puppy.fullRoutine();

        // 3. Hierarchical Inheritance: Animal -> Cat
        System.out.println("\n=== Hierarchical Inheritance: Animal -> Cat ===");
        Cat cat = new Cat("Whiskers", 3, "Orange", true);
        System.out.println("  " + cat.makeSound());
        cat.purr();
        cat.scratch();
        cat.eat();      // Inherited from Animal

        // 4. Another Hierarchical branch: Animal -> Bird
        System.out.println("\n=== Hierarchical Inheritance: Animal -> Bird ===");
        Bird eagle = new Bird("Eagle", 10, 2.0, true);
        System.out.println("  " + eagle.makeSound());
        eagle.fly();
        eagle.eat();

        Bird penguin = new Bird("Penguin", 5, 0.5, false);
        System.out.println("  " + penguin.makeSound());
        penguin.fly();

        // 5. Polymorphic reference - parent reference to child object
        System.out.println("\n=== Polymorphism via Inheritance ===");
        Animal[] zoo = { dog, cat, puppy, eagle, penguin };
        System.out.println("  All animals in the zoo:");
        for (Animal animal : zoo) {
            System.out.println("    " + animal.makeSound()); // Calls overridden version
        }

        // 6. instanceof check
        System.out.println("\n=== instanceof Checks ===");
        for (Animal animal : zoo) {
            System.out.print("  " + animal.name + " → ");
            System.out.print("Animal: " + (animal instanceof Animal) + ", ");
            System.out.print("Dog: " + (animal instanceof Dog) + ", ");
            System.out.print("Cat: " + (animal instanceof Cat) + ", ");
            System.out.print("Puppy: " + (animal instanceof Puppy) + ", ");
            System.out.println("Bird: " + (animal instanceof Bird));
        }

        // 7. Constructor chaining demonstration
        System.out.println("\n=== Constructor Chaining Order ===");
        System.out.println("Creating a Puppy triggers constructors in order:");
        System.out.println("  Animal -> Dog -> Puppy");
        Puppy demo = new Puppy("ChainDemo", 6, "Poodle", "Bone");

        // 8. The 'super' keyword summary
        System.out.println("\n=== Summary: 'super' Keyword Uses ===");
        System.out.println("  1. super() - Call parent constructor (must be first line)");
        System.out.println("  2. super.method() - Call parent's version of overridden method");
        System.out.println("  3. super.field - Access parent's field (if not private)");
        System.out.println("\n  Note: Java does NOT support multiple class inheritance.");
        System.out.println("  Use interfaces for multiple inheritance of behavior.");
    }
}
