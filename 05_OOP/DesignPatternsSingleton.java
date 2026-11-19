/*
 * Program 75: Design Patterns - Singleton, Factory, Builder
 *
 * Problem Statement:
 *   Implement three classic Gang-of-Four design patterns:
 *   1. Singleton  — ensures only one instance exists (thread-safe)
 *   2. Factory    — creates objects without specifying exact class
 *   3. Builder    — constructs complex objects step by step
 *
 * Sample Output:
 *   [Singleton] Same instance? true
 *   [Factory]   Dog says: Woof!
 *   [Builder]   Person{name='Alice', age=30, email='alice@example.com'}
 *
 * Time Complexity:  O(1) per pattern operation
 * Space Complexity: O(1)
 */
public class DesignPatternsSingleton {

    // ============================================================
    // 1. SINGLETON PATTERN — Thread-safe (double-checked locking)
    // ============================================================
    static class DatabaseConnection {
        private static volatile DatabaseConnection instance;
        private String connectionUrl;
        private int connectionCount = 0;

        private DatabaseConnection() {
            this.connectionUrl = "jdbc:mysql://localhost:3306/mydb";
            System.out.println("[Singleton] DatabaseConnection created.");
        }

        public static DatabaseConnection getInstance() {
            if (instance == null) {
                synchronized (DatabaseConnection.class) {
                    if (instance == null) {
                        instance = new DatabaseConnection();
                    }
                }
            }
            return instance;
        }

        public void connect() {
            connectionCount++;
            System.out.println("[Singleton] Connected to: " + connectionUrl
                    + " | Total connections: " + connectionCount);
        }

        public String getUrl() { return connectionUrl; }
    }

    // ============================================================
    // 2. FACTORY PATTERN
    // ============================================================
    interface Animal {
        void speak();
        String getType();
    }

    static class Dog implements Animal {
        @Override public void speak()      { System.out.println("[Factory] Dog says: Woof!"); }
        @Override public String getType()  { return "Dog"; }
    }

    static class Cat implements Animal {
        @Override public void speak()      { System.out.println("[Factory] Cat says: Meow!"); }
        @Override public String getType()  { return "Cat"; }
    }

    static class Bird implements Animal {
        @Override public void speak()      { System.out.println("[Factory] Bird says: Tweet!"); }
        @Override public String getType()  { return "Bird"; }
    }

    // The Factory — centralises object creation
    static class AnimalFactory {
        public static Animal createAnimal(String type) {
            return switch (type.toLowerCase()) {
                case "dog"  -> new Dog();
                case "cat"  -> new Cat();
                case "bird" -> new Bird();
                default     -> throw new IllegalArgumentException("Unknown animal: " + type);
            };
        }
    }

    // ============================================================
    // 3. BUILDER PATTERN
    // ============================================================
    static class Person {
        // Required fields
        private final String name;
        private final int age;
        // Optional fields
        private final String email;
        private final String phone;
        private final String address;

        private Person(Builder builder) {
            this.name    = builder.name;
            this.age     = builder.age;
            this.email   = builder.email;
            this.phone   = builder.phone;
            this.address = builder.address;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age
                    + (email   != null ? ", email='"   + email   + "'" : "")
                    + (phone   != null ? ", phone='"   + phone   + "'" : "")
                    + (address != null ? ", address='" + address + "'" : "")
                    + "}";
        }

        // Static inner Builder class
        static class Builder {
            private final String name;  // required
            private final int age;      // required
            private String email;
            private String phone;
            private String address;

            public Builder(String name, int age) {
                this.name = name;
                this.age  = age;
            }

            public Builder email(String email)     { this.email   = email;   return this; }
            public Builder phone(String phone)     { this.phone   = phone;   return this; }
            public Builder address(String address) { this.address = address; return this; }

            public Person build() {
                if (name == null || name.isBlank()) throw new IllegalStateException("Name is required");
                if (age < 0 || age > 150)           throw new IllegalStateException("Invalid age");
                return new Person(this);
            }
        }
    }

    // ============================================================
    // MAIN
    // ============================================================
    public static void main(String[] args) {

        System.out.println("========== 1. SINGLETON PATTERN ==========");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        DatabaseConnection db3 = DatabaseConnection.getInstance();
        db1.connect();
        db2.connect();
        System.out.println("[Singleton] db1 == db2? " + (db1 == db2));
        System.out.println("[Singleton] db1 == db3? " + (db1 == db3));
        System.out.println("[Singleton] URL: " + db3.getUrl());

        System.out.println("\n========== 2. FACTORY PATTERN ==========");
        String[] animalTypes = {"dog", "cat", "bird"};
        for (String type : animalTypes) {
            Animal animal = AnimalFactory.createAnimal(type);
            animal.speak();
            System.out.println("[Factory]  Type: " + animal.getType());
        }
        try {
            AnimalFactory.createAnimal("fish");
        } catch (IllegalArgumentException e) {
            System.out.println("[Factory]  Error caught: " + e.getMessage());
        }

        System.out.println("\n========== 3. BUILDER PATTERN ==========");
        // Full person
        Person alice = new Person.Builder("Alice", 30)
                .email("alice@example.com")
                .phone("+1-555-0101")
                .address("123 Main St, Springfield")
                .build();
        System.out.println("[Builder] " + alice);

        // Minimal person
        Person bob = new Person.Builder("Bob", 25)
                .email("bob@example.com")
                .build();
        System.out.println("[Builder] " + bob);

        // Only required fields
        Person charlie = new Person.Builder("Charlie", 40).build();
        System.out.println("[Builder] " + charlie);

        // Invalid build — age out of range
        try {
            Person invalid = new Person.Builder("X", -1).build();
        } catch (IllegalStateException e) {
            System.out.println("[Builder] Error caught: " + e.getMessage());
        }
    }
}
