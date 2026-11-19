/*
 * Program 73: Generic Class Demo
 *
 * Problem Statement:
 *   Demonstrate Java Generics: generic classes, generic methods,
 *   bounded type parameters, and wildcards.
 *
 * Sample Output:
 *   Box contains: Hello
 *   Box contains: 42
 *   Max of 10 and 20 = 20
 *   Max of Apple and Mango = Mango
 *
 * Time Complexity:  O(1) for most operations
 * Space Complexity: O(1)
 */
import java.util.ArrayList;
import java.util.List;

public class GenericClassDemo {

    // Generic class with one type parameter
    static class Box<T> {
        private T value;

        public Box(T value) { this.value = value; }

        public T getValue() { return value; }

        public void setValue(T value) { this.value = value; }

        @Override
        public String toString() { return "Box[" + value + "]"; }
    }

    // Generic class with two type parameters
    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) { this.key = key; this.value = value; }

        public K getKey()   { return key; }
        public V getValue() { return value; }

        @Override
        public String toString() { return "(" + key + ", " + value + ")"; }
    }

    // Generic method — bounded type parameter (T must implement Comparable)
    static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    // Generic method — finds the minimum in a list
    static <T extends Comparable<T>> T findMin(List<T> list) {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException("List is empty");
        T min = list.get(0);
        for (T item : list) {
            if (item.compareTo(min) < 0) min = item;
        }
        return min;
    }

    // Wildcard: upper bounded — accepts List<Number> or its subtypes
    static double sumOfList(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) sum += n.doubleValue();
        return sum;
    }

    // Wildcard: lower bounded — accepts List<Integer> or its supertypes
    static void addNumbers(List<? super Integer> list, int count) {
        for (int i = 1; i <= count; i++) list.add(i);
    }

    public static void main(String[] args) {
        System.out.println("=== Generic Box ===");
        Box<String>  strBox = new Box<>("Hello");
        Box<Integer> intBox = new Box<>(42);
        Box<Double>  dblBox = new Box<>(3.14);
        System.out.println("Box contains: " + strBox.getValue());
        System.out.println("Box contains: " + intBox.getValue());
        System.out.println("Box contains: " + dblBox.getValue());

        System.out.println("\n=== Generic Pair ===");
        Pair<String, Integer> p1 = new Pair<>("Age", 25);
        Pair<String, String>  p2 = new Pair<>("Name", "Alice");
        System.out.println("Pair 1: " + p1);
        System.out.println("Pair 2: " + p2);

        System.out.println("\n=== Generic Method (bounded) ===");
        System.out.println("Max of 10 and 20   = " + max(10, 20));
        System.out.println("Max of Apple,Mango = " + max("Apple", "Mango"));
        System.out.println("Max of 3.14, 2.71  = " + max(3.14, 2.71));

        System.out.println("\n=== findMin on List ===");
        List<Integer> nums = List.of(5, 3, 8, 1, 9, 2);
        System.out.println("Min of " + nums + " = " + findMin(nums));

        System.out.println("\n=== Wildcard: upper bounded ===");
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        List<Double>  doubles  = List.of(1.1, 2.2, 3.3);
        System.out.println("Sum of integers: " + sumOfList(integers));
        System.out.println("Sum of doubles : " + sumOfList(doubles));

        System.out.println("\n=== Wildcard: lower bounded ===");
        List<Number> numberList = new ArrayList<>();
        addNumbers(numberList, 5);
        System.out.println("Added numbers: " + numberList);
    }
}
