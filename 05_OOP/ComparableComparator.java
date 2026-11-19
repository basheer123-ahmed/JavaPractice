/*
 * Program 71: ComparableComparator
 * 
 * Problem Statement:
 *   Implement Comparable (natural ordering by name) and Comparator (sort by age,
 *   salary) for an Employee class. Demonstrate Collections.sort with both natural
 *   ordering and custom comparators, including chained comparators and reverse ordering.
 * 
 * Sample Input:  (No user input required — uses hardcoded employee data)
 * Sample Output:
 *   === Natural Ordering (Comparable - by Name) ===
 *   Employee{name='Alice', age=30, salary=75000.00}
 *   Employee{name='Bob', age=25, salary=60000.00}
 *   Employee{name='Charlie', age=35, salary=90000.00}
 *   Employee{name='Diana', age=28, salary=72000.00}
 *   Employee{name='Eve', age=30, salary=85000.00}
 *   
 *   === Sort by Age (Comparator) ===
 *   Employee{name='Bob', age=25, salary=60000.00}
 *   ...
 *   (and further sections for salary, chained, and reversed ordering)
 * 
 * Explanation:
 *   - Comparable<T> defines the "natural" ordering via compareTo(). An object
 *     that implements Comparable can be sorted by Collections.sort() or Arrays.sort()
 *     without supplying an external comparator.
 *   - Comparator<T> is a separate object that defines an alternative ordering via
 *     compare(). Multiple comparators can exist for the same class.
 *   - Java 8+ Comparator has static/default helper methods: comparing(), thenComparing(),
 *     reversed(), naturalOrder(), etc., enabling fluent chaining.
 *   - Collections.sort is stable — equal elements keep their relative order.
 * 
 * Time Complexity:  O(n log n) for each sort (TimSort)
 * Space Complexity: O(n) for the list of employees; O(log n) auxiliary for TimSort
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// ----- Employee class implementing Comparable (natural ordering by name) -----
class Employee implements Comparable<Employee> {
    private String name;
    private int age;
    private double salary;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    // Getters
    public String getName()   { return name; }
    public int    getAge()    { return age; }
    public double getSalary() { return salary; }

    // Natural ordering: alphabetical by name (case-insensitive)
    @Override
    public int compareTo(Employee other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', age=%d, salary=%.2f}", name, age, salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee emp = (Employee) o;
        return age == emp.age
            && Double.compare(emp.salary, salary) == 0
            && name.equalsIgnoreCase(emp.name);
    }

    @Override
    public int hashCode() {
        int result = name.toLowerCase().hashCode();
        result = 31 * result + Integer.hashCode(age);
        result = 31 * result + Double.hashCode(salary);
        return result;
    }
}

// ----- Traditional Comparator classes (pre-Java-8 style) -----
class AgeComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return Integer.compare(e1.getAge(), e2.getAge());
    }
}

class SalaryComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return Double.compare(e1.getSalary(), e2.getSalary());
    }
}

// ----- Main driver -----
public class ComparableComparator {

    // Helper to print a list
    private static void printEmployees(List<Employee> list) {
        for (Employee e : list) {
            System.out.println("  " + e);
        }
    }

    // Build a fresh copy every time so sorts don't interfere with each other
    private static List<Employee> sampleEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Charlie", 35, 90000.00));
        employees.add(new Employee("Alice",   30, 75000.00));
        employees.add(new Employee("Eve",     30, 85000.00));
        employees.add(new Employee("Bob",     25, 60000.00));
        employees.add(new Employee("Diana",   28, 72000.00));
        employees.add(new Employee("Alice",   27, 68000.00)); // duplicate name, different age/salary
        return employees;
    }

    public static void main(String[] args) {

        // ---- 1. Natural ordering via Comparable (by name) ----
        System.out.println("=== 1. Natural Ordering (Comparable - by Name) ===");
        List<Employee> list1 = sampleEmployees();
        Collections.sort(list1);                       // uses compareTo()
        printEmployees(list1);

        // ---- 2. Sort by Age using traditional Comparator class ----
        System.out.println("\n=== 2. Sort by Age (Traditional Comparator Class) ===");
        List<Employee> list2 = sampleEmployees();
        Collections.sort(list2, new AgeComparator());
        printEmployees(list2);

        // ---- 3. Sort by Salary using traditional Comparator class ----
        System.out.println("\n=== 3. Sort by Salary (Traditional Comparator Class) ===");
        List<Employee> list3 = sampleEmployees();
        Collections.sort(list3, new SalaryComparator());
        printEmployees(list3);

        // ---- 4. Sort by Age using anonymous inner class ----
        System.out.println("\n=== 4. Sort by Age (Anonymous Inner Class) ===");
        List<Employee> list4 = sampleEmployees();
        Collections.sort(list4, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        printEmployees(list4);

        // ---- 5. Sort by Salary using lambda expression ----
        System.out.println("\n=== 5. Sort by Salary (Lambda Expression) ===");
        List<Employee> list5 = sampleEmployees();
        list5.sort((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        printEmployees(list5);

        // ---- 6. Sort by Salary using Comparator.comparing (method reference) ----
        System.out.println("\n=== 6. Sort by Salary (Comparator.comparing + Method Reference) ===");
        List<Employee> list6 = sampleEmployees();
        list6.sort(Comparator.comparingDouble(Employee::getSalary));
        printEmployees(list6);

        // ---- 7. Chained Comparator: sort by Age, then by Salary, then by Name ----
        System.out.println("\n=== 7. Chained Comparator (Age → Salary → Name) ===");
        List<Employee> list7 = sampleEmployees();
        Comparator<Employee> chained = Comparator
                .comparingInt(Employee::getAge)
                .thenComparingDouble(Employee::getSalary)
                .thenComparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
        list7.sort(chained);
        printEmployees(list7);

        // ---- 8. Reverse ordering ----
        System.out.println("\n=== 8. Reverse Natural Ordering (Descending by Name) ===");
        List<Employee> list8 = sampleEmployees();
        list8.sort(Comparator.reverseOrder());         // reverses compareTo()
        printEmployees(list8);

        System.out.println("\n=== 9. Reverse Salary (Highest First) ===");
        List<Employee> list9 = sampleEmployees();
        list9.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
        printEmployees(list9);

        // ---- 10. Using Collections.min / max with Comparator ----
        System.out.println("\n=== 10. Min/Max using Comparators ===");
        List<Employee> all = sampleEmployees();
        Employee youngest   = Collections.min(all, Comparator.comparingInt(Employee::getAge));
        Employee oldest     = Collections.max(all, Comparator.comparingInt(Employee::getAge));
        Employee lowestPaid = Collections.min(all, Comparator.comparingDouble(Employee::getSalary));
        Employee highestPaid= Collections.max(all, Comparator.comparingDouble(Employee::getSalary));
        System.out.println("  Youngest    : " + youngest);
        System.out.println("  Oldest      : " + oldest);
        System.out.println("  Lowest Paid : " + lowestPaid);
        System.out.println("  Highest Paid: " + highestPaid);

        // ---- 11. Null-safe comparator ----
        System.out.println("\n=== 11. Null-Safe Comparator (nullsFirst) ===");
        List<Employee> list11 = new ArrayList<>(sampleEmployees());
        list11.add(null);
        list11.add(0, null);
        list11.sort(Comparator.nullsFirst(Comparator.comparing(Employee::getName,
                                          String.CASE_INSENSITIVE_ORDER)));
        for (Employee e : list11) {
            System.out.println("  " + e);   // null prints as "null"
        }

        System.out.println("\n=== Interview Tips ===");
        System.out.println("  • Comparable defines ONE natural ordering inside the class.");
        System.out.println("  • Comparator defines MANY external orderings outside the class.");
        System.out.println("  • Always use Integer.compare / Double.compare to avoid overflow.");
        System.out.println("  • Comparator.comparing().thenComparing() enables fluent chaining.");
        System.out.println("  • Use nullsFirst() / nullsLast() for null-safe sorting.");
        System.out.println("  • compareTo() contract: sgn(x.compareTo(y)) == -sgn(y.compareTo(x)).");
        System.out.println("  • Strongly recommended: compareTo consistency with equals().");
    }
}
