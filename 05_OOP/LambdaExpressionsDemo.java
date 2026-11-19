/*
 * Program 74: Lambda Expressions Demo
 *
 * Problem Statement:
 *   Demonstrate Java lambda expressions with built-in functional interfaces
 *   (Predicate, Function, Consumer, Supplier, BiFunction) and method references.
 *
 * Sample Output:
 *   Is 15 even? false
 *   Filtered evens: [2, 4, 6, 8, 10]
 *   Squared: [1, 4, 9, 16, 25]
 *
 * Time Complexity:  O(n) for stream operations
 * Space Complexity: O(n)
 */
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class LambdaExpressionsDemo {

    // Custom functional interface
    @FunctionalInterface
    interface MathOperation {
        int operate(int a, int b);
    }

    // Another custom functional interface
    @FunctionalInterface
    interface StringTransformer {
        String transform(String s);
    }

    public static void main(String[] args) {

        System.out.println("=== Custom Functional Interface (Lambda) ===");
        MathOperation add      = (a, b) -> a + b;
        MathOperation subtract = (a, b) -> a - b;
        MathOperation multiply = (a, b) -> a * b;
        MathOperation divide   = (a, b) -> b != 0 ? a / b : 0;
        System.out.println("10 + 3  = " + add.operate(10, 3));
        System.out.println("10 - 3  = " + subtract.operate(10, 3));
        System.out.println("10 * 3  = " + multiply.operate(10, 3));
        System.out.println("10 / 3  = " + divide.operate(10, 3));

        System.out.println("\n=== Predicate<T> ===");
        Predicate<Integer> isEven     = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<String>  isLong     = s -> s.length() > 5;
        System.out.println("Is 15 even?        " + isEven.test(15));
        System.out.println("Is 10 even?        " + isEven.test(10));
        System.out.println("Is -5 positive?    " + isPositive.test(-5));
        // Combining predicates
        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        System.out.println("Is 8 even+positive? " + isEvenAndPositive.test(8));
        System.out.println("Is -4 even+positive?" + isEvenAndPositive.test(-4));
        System.out.println("'Hello' is long?   " + isLong.test("Hello"));
        System.out.println("'JavaLang' is long?" + isLong.test("JavaLang"));

        System.out.println("\n=== Function<T, R> ===");
        Function<String, Integer> strLength  = String::length;
        Function<Integer, String> intToStr   = i -> "Number: " + i;
        Function<String, String>  toUpper    = String::toUpperCase;
        // andThen: compose functions
        Function<String, String> pipeline = toUpper.andThen(s -> s + "!");
        System.out.println("Length of 'Java': "  + strLength.apply("Java"));
        System.out.println("intToStr(42): "       + intToStr.apply(42));
        System.out.println("Pipeline('hello'): "  + pipeline.apply("hello"));

        System.out.println("\n=== Consumer<T> ===");
        Consumer<String>  printer  = System.out::println;
        Consumer<Integer> doubler  = n -> System.out.println(n + " doubled = " + (n * 2));
        Consumer<String>  combined = printer.andThen(s -> System.out.println("Length: " + s.length()));
        printer.accept("Hello Consumer!");
        doubler.accept(7);
        combined.accept("Lambda");

        System.out.println("\n=== Supplier<T> ===");
        Supplier<String>  greeting   = () -> "Good Morning!";
        Supplier<Double>  randomVal  = Math::random;
        Supplier<List<String>> listSupplier = ArrayList::new;
        System.out.println(greeting.get());
        System.out.println("Random: " + randomVal.get());
        List<String> newList = listSupplier.get();
        newList.add("from supplier");
        System.out.println("List from supplier: " + newList);

        System.out.println("\n=== BiFunction<T,U,R> ===");
        BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
        BiFunction<Integer, Integer, Integer> power = (base, exp) -> (int) Math.pow(base, exp);
        System.out.println("'ha' repeated 3x: " + repeat.apply("ha", 3));
        System.out.println("2^8 = " + power.apply(2, 8));

        System.out.println("\n=== Method References ===");
        // Static method reference
        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println("parseInt('123') = " + parseInt.apply("123"));
        // Instance method reference (unbound)
        Function<String, String> trim = String::trim;
        System.out.println("trim('  hi  ') = '" + trim.apply("  hi  ") + "'");
        // Constructor reference
        Function<String, StringBuilder> sbConstructor = StringBuilder::new;
        StringBuilder sb = sbConstructor.apply("Built via constructor ref");
        System.out.println("StringBuilder: " + sb);

        System.out.println("\n=== Lambda with Collections ===");
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6, 10);
        // Filter evens
        List<Integer> evens = numbers.stream().filter(isEven).sorted().collect(Collectors.toList());
        System.out.println("Filtered evens: " + evens);
        // Square each
        List<Integer> squares = numbers.stream()
                .sorted()
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println("Squared (sorted): " + squares);
        // Sort strings by length then alphabetically
        List<String> words = Arrays.asList("banana","apple","cherry","date","elderberry","fig");
        words.sort(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()));
        System.out.println("Sorted words: " + words);

        System.out.println("\n=== Custom StringTransformer Lambdas ===");
        StringTransformer shout    = s -> s.toUpperCase() + "!!!";
        StringTransformer whisper  = s -> s.toLowerCase() + "...";
        StringTransformer reverse  = s -> new StringBuilder(s).reverse().toString();
        System.out.println(shout.transform("hello"));
        System.out.println(whisper.transform("HELLO"));
        System.out.println(reverse.transform("Lambda"));
    }
}
