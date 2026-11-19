/*
 * Program 69: Object Cloning Demo
 * 
 * Problem Statement:
 *   Demonstrate shallow copy vs deep copy. Implement the Cloneable interface.
 *   Show the difference when mutable fields are involved and how shallow
 *   cloning can lead to unintended shared references.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Shallow Clone ===
 *   Original: Student{name='Alice', address=Address{city='NYC', zip='10001'}}
 *   Clone:    Student{name='Alice', address=Address{city='NYC', zip='10001'}}
 *   After modifying clone's address city to 'Boston':
 *   Original: Student{name='Alice', address=Address{city='Boston', zip='10001'}}
 *   Clone:    Student{name='Alice Clone', address=Address{city='Boston', zip='10001'}}
 *   ⚠ PROBLEM: Original's address also changed! (shared reference)
 *   
 *   === Deep Clone ===
 *   After modifying deep clone's address:
 *   Original unchanged: Address{city='Boston', zip='10001'}
 *   Deep clone changed:  Address{city='Chicago', zip='60601'}
 *   ✓ Original is NOT affected (independent copy)
 *   ...
 * 
 * Explanation:
 *   Object cloning creates a copy of an existing object.
 *   
 *   Shallow Copy (Object.clone()):
 *   - Copies all field values as-is
 *   - Primitive fields: copies the value (independent)
 *   - Reference fields: copies the reference (SHARED object!)
 *   - Changes to mutable referenced objects affect BOTH original and clone
 *   
 *   Deep Copy:
 *   - Creates completely independent copies of all objects
 *   - Reference fields: recursively clones the referenced objects
 *   - Changes to the clone do NOT affect the original
 *   
 *   Alternatives to clone():
 *   - Copy constructor
 *   - Serialization/deserialization
 *   - Manual field-by-field copy
 * 
 * Time Complexity: O(n) where n is the number of fields to clone
 * Space Complexity: O(n) for the cloned objects
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// =================== SUPPORTING CLASSES ===================

// Mutable class - will demonstrate the shallow copy problem
class Address implements Cloneable, Serializable {
    private String street;
    private String city;
    private String zipCode;

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    // Copy constructor
    public Address(Address other) {
        this.street = other.street;
        this.city = other.city;
        this.zipCode = other.zipCode;
    }

    // Getters and Setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    @Override
    protected Address clone() throws CloneNotSupportedException {
        return (Address) super.clone(); // Strings are immutable, so shallow is fine here
    }

    @Override
    public String toString() {
        return "Address{street='" + street + "', city='" + city + "', zip='" + zipCode + "'}";
    }
}

// Class with mutable list field
class CourseList implements Cloneable, Serializable {
    private List<String> courses;

    public CourseList() {
        this.courses = new ArrayList<>();
    }

    public CourseList(CourseList other) {
        this.courses = new ArrayList<>(other.courses); // Deep copy of list
    }

    public void addCourse(String course) {
        courses.add(course);
    }

    public void removeCourse(String course) {
        courses.remove(course);
    }

    public List<String> getCourses() {
        return courses;
    }

    // Shallow clone - list reference is shared!
    @Override
    protected CourseList clone() throws CloneNotSupportedException {
        return (CourseList) super.clone();
    }

    // Deep clone - creates new list
    public CourseList deepClone() {
        return new CourseList(this);
    }

    @Override
    public String toString() {
        return "Courses" + courses;
    }
}

// =================== SHALLOW CLONE CLASS ===================

class StudentShallow implements Cloneable {
    private String name;        // Immutable (String) - safe for shallow copy
    private int age;            // Primitive - safe for shallow copy
    private Address address;    // Mutable object - DANGEROUS for shallow copy!
    private CourseList courses;  // Mutable object with collection

    public StudentShallow(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.courses = new CourseList();
    }

    // Shallow clone using Object.clone()
    @Override
    public StudentShallow clone() throws CloneNotSupportedException {
        return (StudentShallow) super.clone();
        // WARNING: 'address' and 'courses' are NOT cloned!
        // They share the SAME reference with original
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public CourseList getCourses() { return courses; }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", " + address + ", " + courses + "}";
    }
}

// =================== DEEP CLONE CLASS ===================

class StudentDeep implements Cloneable, Serializable {
    private String name;
    private int age;
    private Address address;
    private CourseList courses;

    public StudentDeep(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.courses = new CourseList();
    }

    // Method 1: Deep clone by manually cloning each mutable field
    @Override
    public StudentDeep clone() throws CloneNotSupportedException {
        StudentDeep cloned = (StudentDeep) super.clone();
        cloned.address = this.address.clone();      // Deep clone address
        cloned.courses = this.courses.deepClone();   // Deep clone courses
        return cloned;
    }

    // Method 2: Deep clone via copy constructor
    public StudentDeep(StudentDeep other) {
        this.name = other.name;
        this.age = other.age;
        this.address = new Address(other.address);         // New address object
        this.courses = new CourseList(other.courses);       // New courses object
    }

    // Method 3: Deep clone via serialization (most thorough)
    @SuppressWarnings("unchecked")
    public StudentDeep deepCloneViaSerialization() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (StudentDeep) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deep clone via serialization failed", e);
        }
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public Address getAddress() { return address; }
    public CourseList getCourses() { return courses; }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", " + address + ", " + courses + "}";
    }
}

public class ObjectCloningDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 69: Object Cloning Demo           ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // =================== SHALLOW CLONE ===================
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  SHALLOW CLONE (Object.clone())");
        System.out.println("═══════════════════════════════════════════════════\n");

        // Create original
        Address addr1 = new Address("123 Main St", "New York", "10001");
        StudentShallow original = new StudentShallow("Alice", 20, addr1);
        original.getCourses().addCourse("Math");
        original.getCourses().addCourse("Physics");

        System.out.println("--- Before Cloning ---");
        System.out.println("  Original: " + original);

        // Shallow clone
        StudentShallow shallowClone = original.clone();
        System.out.println("  Clone:    " + shallowClone);

        // Check references
        System.out.println("\n--- Reference Checks ---");
        System.out.println("  original == shallowClone?           " + (original == shallowClone));
        System.out.println("  original.address == clone.address?  " + (original.getAddress() == shallowClone.getAddress()));
        System.out.println("  original.courses == clone.courses?  " + (original.getCourses() == shallowClone.getCourses()));
        System.out.println("  ⚠ Address and Courses are SHARED references!");

        // Modify clone's primitive and String fields
        System.out.println("\n--- Modify Clone's Primitives/Strings ---");
        shallowClone.setName("Alice Clone");
        shallowClone.setAge(21);
        System.out.println("  Original name: " + original.getName() + ", age: " + original.getAge());
        System.out.println("  Clone name:    " + shallowClone.getName() + ", age: " + shallowClone.getAge());
        System.out.println("  ✓ Primitives and Strings are independent (OK)");

        // THE PROBLEM: Modify clone's mutable Address
        System.out.println("\n--- ⚠ Modify Clone's Mutable Address ---");
        shallowClone.getAddress().setCity("Boston");
        shallowClone.getAddress().setZipCode("02101");
        System.out.println("  Original: " + original.getAddress());
        System.out.println("  Clone:    " + shallowClone.getAddress());
        System.out.println("  ⚠ PROBLEM: Original's address ALSO changed!");
        System.out.println("    Both point to the SAME Address object.");

        // THE PROBLEM: Modify clone's mutable Courses list
        System.out.println("\n--- ⚠ Modify Clone's Mutable Course List ---");
        shallowClone.getCourses().addCourse("Chemistry");
        System.out.println("  Original courses: " + original.getCourses());
        System.out.println("  Clone courses:    " + shallowClone.getCourses());
        System.out.println("  ⚠ PROBLEM: Original's courses ALSO has Chemistry!");

        // =================== DEEP CLONE ===================
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("  DEEP CLONE (Manual + Copy Constructor + Serialization)");
        System.out.println("═══════════════════════════════════════════════════\n");

        // Create fresh original
        Address addr2 = new Address("456 Oak Ave", "San Francisco", "94102");
        StudentDeep deepOriginal = new StudentDeep("Bob", 22, addr2);
        deepOriginal.getCourses().addCourse("CS101");
        deepOriginal.getCourses().addCourse("CS201");

        System.out.println("--- Before Cloning ---");
        System.out.println("  Original: " + deepOriginal);

        // Method 1: Deep clone via clone() override
        System.out.println("\n--- Method 1: Deep Clone via clone() ---");
        StudentDeep deepClone1 = deepOriginal.clone();
        System.out.println("  Clone: " + deepClone1);
        System.out.println("  original.address == clone.address?  " + (deepOriginal.getAddress() == deepClone1.getAddress()));
        System.out.println("  ✓ Addresses are DIFFERENT objects");

        deepClone1.getAddress().setCity("Los Angeles");
        deepClone1.getCourses().addCourse("CS301");
        System.out.println("  After modifying clone:");
        System.out.println("  Original: " + deepOriginal);
        System.out.println("  Clone:    " + deepClone1);
        System.out.println("  ✓ Original is NOT affected!");

        // Method 2: Deep clone via copy constructor
        System.out.println("\n--- Method 2: Deep Clone via Copy Constructor ---");
        StudentDeep deepClone2 = new StudentDeep(deepOriginal);
        deepClone2.getAddress().setCity("Chicago");
        deepClone2.setName("Bob Copy");
        System.out.println("  Original: " + deepOriginal);
        System.out.println("  Clone:    " + deepClone2);
        System.out.println("  ✓ Original is NOT affected!");

        // Method 3: Deep clone via serialization
        System.out.println("\n--- Method 3: Deep Clone via Serialization ---");
        StudentDeep deepClone3 = deepOriginal.deepCloneViaSerialization();
        deepClone3.getAddress().setCity("Seattle");
        deepClone3.getCourses().addCourse("CS401");
        deepClone3.setName("Bob Serialized");
        System.out.println("  Original: " + deepOriginal);
        System.out.println("  Clone:    " + deepClone3);
        System.out.println("  ✓ Original is NOT affected!");

        // =================== VISUAL COMPARISON ===================
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("  VISUAL: SHALLOW vs DEEP CLONE");
        System.out.println("═══════════════════════════════════════════════════\n");

        System.out.println("  SHALLOW CLONE:");
        System.out.println("  ┌──────────┐     ┌──────────┐");
        System.out.println("  │ Original │     │  Clone   │");
        System.out.println("  │ name=\"A\" │     │ name=\"B\" │  ← Strings are immutable (OK)");
        System.out.println("  │ age=20   │     │ age=21   │  ← Primitives copied (OK)");
        System.out.println("  │ addr ────┼──┐  │ addr ────┼──┐");
        System.out.println("  └──────────┘  │  └──────────┘  │");
        System.out.println("                └──►┌────────┐◄──┘");
        System.out.println("                    │ Address│  ← SHARED! Changes affect both!");
        System.out.println("                    │ city=? │");
        System.out.println("                    └────────┘\n");

        System.out.println("  DEEP CLONE:");
        System.out.println("  ┌──────────┐     ┌──────────┐");
        System.out.println("  │ Original │     │  Clone   │");
        System.out.println("  │ name=\"A\" │     │ name=\"B\" │");
        System.out.println("  │ age=20   │     │ age=21   │");
        System.out.println("  │ addr ────┼──┐  │ addr ────┼──┐");
        System.out.println("  └──────────┘  │  └──────────┘  │");
        System.out.println("                ▼                 ▼");
        System.out.println("           ┌────────┐       ┌────────┐");
        System.out.println("           │ Address│       │ Address│  ← INDEPENDENT copies!");
        System.out.println("           │ city=NY│       │ city=LA│");
        System.out.println("           └────────┘       └────────┘");

        // =================== WITHOUT CLONEABLE ===================
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("  WHAT HAPPENS WITHOUT CLONEABLE?");
        System.out.println("═══════════════════════════════════════════════════\n");

        // If a class doesn't implement Cloneable, clone() throws CloneNotSupportedException
        System.out.println("  If a class doesn't implement Cloneable:");
        System.out.println("  clone() throws CloneNotSupportedException at runtime.");
        System.out.println("  Cloneable is a 'marker interface' - no methods, just a flag.");

        // Summary
        System.out.println("\n═══════════════════════════════════════════════════");
        System.out.println("  CLONING STRATEGY COMPARISON");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("  ┌───────────────────┬──────────┬───────┬──────────┐");
        System.out.println("  │ Method            │ Deep?    │ Speed │ Notes    │");
        System.out.println("  ├───────────────────┼──────────┼───────┼──────────┤");
        System.out.println("  │ Object.clone()    │ Shallow  │ Fast  │ Fragile  │");
        System.out.println("  │ Override clone()  │ Deep     │ Fast  │ Manual   │");
        System.out.println("  │ Copy Constructor  │ Deep     │ Fast  │ Preferred│");
        System.out.println("  │ Serialization     │ Deep     │ Slow  │ Thorough │");
        System.out.println("  └───────────────────┴──────────┴───────┴──────────┘");
        System.out.println("\n  Effective Java Recommendation:");
        System.out.println("  Prefer copy constructors or factory methods over clone().");
    }
}
