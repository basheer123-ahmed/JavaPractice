/*
 * Program 84: Stream API Demo
 *
 * Problem Statement:
 *   Demonstrate Java Stream API: filter, map, reduce, collect,
 *   forEach, sorted, distinct, flatMap, groupingBy, partitioningBy, joining.
 *
 * Sample Output:
 *   Even numbers: [2, 4, 6, 8, 10]
 *   Squared     : [1, 4, 9, 16, 25]
 *   Sum         : 55
 *
 * Time Complexity:  O(n) for most single-pass operations
 * Space Complexity: O(n) for collect operations
 */
import java.util.*;
import java.util.stream.*;

public class StreamAPIDemo {

    record Person(String name, int age, String city, double salary) {}

    public static void main(String[] args) {

        System.out.println("========== Basic Stream Operations ==========");
        List<Integer> nums = List.of(1,2,3,4,5,6,7,8,9,10);

        // filter + collect
        List<Integer> evens = nums.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
        System.out.println("Evens    : " + evens);

        // map (transform)
        List<Integer> squares = nums.stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println("Squares  : " + squares);

        // reduce (aggregate)
        int sum = nums.stream().reduce(0, Integer::sum);
        System.out.println("Sum      : " + sum);
        int product = nums.stream().limit(5).reduce(1, (a, b) -> a * b);
        System.out.println("Product(1-5): " + product);

        // sorted, distinct, limit, skip
        List<Integer> mixed = Arrays.asList(5,3,8,3,1,8,2,5,9,1);
        List<Integer> uniqueSorted = mixed.stream().distinct().sorted().collect(Collectors.toList());
        System.out.println("Distinct+sorted: " + uniqueSorted);
        List<Integer> middle = nums.stream().skip(2).limit(5).collect(Collectors.toList());
        System.out.println("skip(2)+limit(5): " + middle);

        System.out.println("\n========== String Streams ==========");
        List<String> words = List.of("java","stream","api","lambda","filter","map","reduce");

        // filter by length, sort, map to upper
        List<String> result = words.stream()
                .filter(w -> w.length() > 3)
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("len>3, sorted, upper: " + result);

        // joining
        String joined = words.stream().collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Joined: " + joined);

        // count, anyMatch, allMatch, noneMatch
        long count = words.stream().filter(w -> w.contains("a")).count();
        System.out.println("Count with 'a': " + count);
        System.out.println("anyMatch len>6: " + words.stream().anyMatch(w -> w.length() > 6));
        System.out.println("allMatch len>2: " + words.stream().allMatch(w -> w.length() > 2));
        System.out.println("noneMatch 'xyz': " + words.stream().noneMatch(w -> w.equals("xyz")));

        // findFirst, findAny
        Optional<String> first = words.stream().filter(w -> w.startsWith("l")).findFirst();
        System.out.println("First starting with 'l': " + first.orElse("none"));

        // min, max
        Optional<String> shortest = words.stream().min(Comparator.comparingInt(String::length));
        Optional<String> longest  = words.stream().max(Comparator.comparingInt(String::length));
        System.out.println("Shortest: " + shortest.orElse(""));
        System.out.println("Longest : " + longest.orElse(""));

        System.out.println("\n========== flatMap ==========");
        List<List<Integer>> nested = List.of(List.of(1,2,3), List.of(4,5), List.of(6,7,8,9));
        List<Integer> flat = nested.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("Nested : " + nested);
        System.out.println("Flattened: " + flat);

        List<String> sentences = List.of("Hello World", "Java Streams", "FlatMap Demo");
        List<String> allWords = sentences.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .collect(Collectors.toList());
        System.out.println("All words: " + allWords);

        System.out.println("\n========== Collectors: groupingBy, partitioningBy ==========");
        List<Person> people = List.of(
                new Person("Alice",  28, "Delhi",   95000),
                new Person("Bob",    35, "Mumbai",  120000),
                new Person("Charlie",22, "Delhi",   65000),
                new Person("Diana",  31, "Mumbai",  110000),
                new Person("Eve",    27, "Chennai", 80000),
                new Person("Frank",  40, "Delhi",   140000)
        );

        // groupingBy city
        Map<String, List<String>> byCity = people.stream().collect(
                Collectors.groupingBy(Person::city,
                        Collectors.mapping(Person::name, Collectors.toList())));
        System.out.println("By city: " + byCity);

        // partitioningBy age >= 30
        Map<Boolean, List<String>> partitioned = people.stream().collect(
                Collectors.partitioningBy(p -> p.age() >= 30,
                        Collectors.mapping(Person::name, Collectors.toList())));
        System.out.println("Age>=30: " + partitioned.get(true));
        System.out.println("Age<30 : " + partitioned.get(false));

        // Average salary by city
        Map<String, Double> avgSalary = people.stream().collect(
                Collectors.groupingBy(Person::city, Collectors.averagingDouble(Person::salary)));
        avgSalary.forEach((city, avg) -> System.out.printf("Avg salary %s: %.0f%n", city, avg));

        System.out.println("\n========== Numeric Streams ==========");
        IntStream.rangeClosed(1, 5).forEach(i -> System.out.print(i + " "));
        System.out.println();
        System.out.println("Sum 1-100: " + IntStream.rangeClosed(1, 100).sum());
        System.out.println("Average  : " + IntStream.rangeClosed(1, 10).average().orElse(0));
        System.out.println("Max      : " + IntStream.of(3,1,4,1,5,9,2,6).max().orElse(0));

        // IntStream to boxed List
        List<Integer> range = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());
        System.out.println("Range list: " + range);

        System.out.println("\n========== Collectors.toMap ==========");
        Map<String, Double> salaryMap = people.stream().collect(
                Collectors.toMap(Person::name, Person::salary));
        System.out.println("Name->Salary: " + salaryMap);

        // Statistics
        DoubleSummaryStatistics stats = people.stream()
                .collect(Collectors.summarizingDouble(Person::salary));
        System.out.printf("Salary stats -> count:%d min:%.0f max:%.0f avg:%.0f sum:%.0f%n",
                stats.getCount(), stats.getMin(), stats.getMax(), stats.getAverage(), stats.getSum());
    }
}
