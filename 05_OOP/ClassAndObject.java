/*
 * Program 61: Class and Object
 * 
 * Problem Statement:
 *   Create a Student class with fields (name, rollNo, marks), constructors
 *   (default, parameterized, copy), getters/setters, toString(), and
 *   demonstrate object creation, modification, and display.
 * 
 * Sample Input: (No user input required)
 * Sample Output:
 *   === Default Constructor ===
 *   Student{name='Unknown', rollNo=0, marks=0.0}
 *   === Parameterized Constructor ===
 *   Student{name='Alice', rollNo=101, marks=92.5}
 *   Student{name='Bob', rollNo=102, marks=88.0}
 *   === Copy Constructor ===
 *   Student{name='Alice', rollNo=101, marks=92.5}
 *   === Getters ===
 *   Name: Alice, Roll No: 101, Marks: 92.5
 *   === Setters with modification ===
 *   Student{name='Alice Johnson', rollNo=101, marks=95.0}
 *   === Object Comparison ===
 *   s1 equals s4 (copy)? true
 *   s1 == s4 (reference)? false
 *   === Array of Objects ===
 *   ...
 * 
 * Explanation:
 *   A class is a blueprint for objects. It defines the state (fields) and
 *   behavior (methods) of objects. This program demonstrates:
 *   - Default constructor: initializes fields with default values
 *   - Parameterized constructor: initializes fields with given values
 *   - Copy constructor: creates a new object as a copy of another
 *   - Getters/Setters: encapsulated access to private fields
 *   - toString(): human-readable string representation
 *   - equals() and hashCode(): logical equality comparison
 *   - Object arrays and iteration
 * 
 * Time Complexity: O(n) for iterating through array of n objects
 * Space Complexity: O(n) for storing n Student objects
 */

import java.util.Arrays;
import java.util.Objects;

class Student {
    // Fields (instance variables)
    private String name;
    private int rollNo;
    private double marks;

    // Static field to track total students created
    private static int totalStudents = 0;

    // 1. Default Constructor
    public Student() {
        this.name = "Unknown";
        this.rollNo = 0;
        this.marks = 0.0;
        totalStudents++;
    }

    // 2. Parameterized Constructor
    public Student(String name, int rollNo, double marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
        totalStudents++;
    }

    // 3. Copy Constructor
    public Student(Student other) {
        this.name = other.name;
        this.rollNo = other.rollNo;
        this.marks = other.marks;
        totalStudents++;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public double getMarks() {
        return marks;
    }

    // Setters with validation
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Invalid name!");
        }
    }

    public void setRollNo(int rollNo) {
        if (rollNo > 0) {
            this.rollNo = rollNo;
        } else {
            System.out.println("Roll number must be positive!");
        }
    }

    public void setMarks(double marks) {
        if (marks >= 0 && marks <= 100) {
            this.marks = marks;
        } else {
            System.out.println("Marks must be between 0 and 100!");
        }
    }

    // Grade calculation method
    public char getGrade() {
        if (marks >= 90) return 'A';
        if (marks >= 80) return 'B';
        if (marks >= 70) return 'C';
        if (marks >= 60) return 'D';
        return 'F';
    }

    // Static method
    public static int getTotalStudents() {
        return totalStudents;
    }

    // toString() override
    @Override
    public String toString() {
        return "Student{name='" + name + "', rollNo=" + rollNo + ", marks=" + marks + "}";
    }

    // equals() override - two students are equal if same rollNo and name
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return rollNo == student.rollNo &&
               Double.compare(student.marks, marks) == 0 &&
               Objects.equals(name, student.name);
    }

    // hashCode() override - must override when equals is overridden
    @Override
    public int hashCode() {
        return Objects.hash(name, rollNo, marks);
    }
}

public class ClassAndObject {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Program 61: Class and Object              ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        // 1. Default Constructor
        System.out.println("=== Default Constructor ===");
        Student s1Default = new Student();
        System.out.println(s1Default);

        // 2. Parameterized Constructor
        System.out.println("\n=== Parameterized Constructor ===");
        Student s1 = new Student("Alice", 101, 92.5);
        Student s2 = new Student("Bob", 102, 88.0);
        Student s3 = new Student("Charlie", 103, 75.0);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        // 3. Copy Constructor
        System.out.println("\n=== Copy Constructor ===");
        Student s4 = new Student(s1);  // Copy of s1
        System.out.println("Original: " + s1);
        System.out.println("Copy:     " + s4);

        // 4. Getters
        System.out.println("\n=== Getters ===");
        System.out.println("Name: " + s1.getName());
        System.out.println("Roll No: " + s1.getRollNo());
        System.out.println("Marks: " + s1.getMarks());
        System.out.println("Grade: " + s1.getGrade());

        // 5. Setters with modification
        System.out.println("\n=== Setters with Modification ===");
        s1.setName("Alice Johnson");
        s1.setMarks(95.0);
        System.out.println("After modification: " + s1);

        // 6. Setter validation
        System.out.println("\n=== Setter Validation ===");
        s1.setMarks(-10);    // Invalid
        s1.setMarks(150);    // Invalid
        s1.setRollNo(-5);    // Invalid
        s1.setName("");       // Invalid
        System.out.println("After invalid attempts: " + s1); // Should be unchanged

        // 7. Object Comparison
        System.out.println("\n=== Object Comparison ===");
        Student s5 = new Student("Alice Johnson", 101, 95.0);
        System.out.println("s1: " + s1);
        System.out.println("s5: " + s5);
        System.out.println("s1.equals(s5)? " + s1.equals(s5));           // true (same values)
        System.out.println("s1 == s5?       " + (s1 == s5));             // false (different references)
        System.out.println("s1.equals(s4)?  " + s1.equals(s4));          // false (s1 was modified)
        System.out.println("s1 == s1?       " + (s1 == s1));             // true (same reference)

        // 8. Array of Objects
        System.out.println("\n=== Array of Objects ===");
        Student[] classroom = {s1, s2, s3, s4, s5};
        System.out.println("Classroom roster:");
        for (int i = 0; i < classroom.length; i++) {
            System.out.println("  [" + i + "] " + classroom[i] + " → Grade: " + classroom[i].getGrade());
        }

        // 9. Find topper
        System.out.println("\n=== Find Topper ===");
        Student topper = classroom[0];
        for (Student s : classroom) {
            if (s.getMarks() > topper.getMarks()) {
                topper = s;
            }
        }
        System.out.println("Topper: " + topper);

        // 10. Static member
        System.out.println("\n=== Static Member ===");
        System.out.println("Total students created: " + Student.getTotalStudents());

        // 11. Null reference handling
        System.out.println("\n=== Null Reference ===");
        Student sNull = null;
        System.out.println("s1.equals(null)? " + s1.equals(sNull));
        // System.out.println(sNull.getName()); // Would throw NullPointerException!
        System.out.println("Avoided calling method on null reference (would throw NullPointerException)");
    }
}
