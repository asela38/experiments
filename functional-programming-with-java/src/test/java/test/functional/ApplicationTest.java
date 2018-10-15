package test.functional;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

public class ApplicationTest {

    @Test
    public void trivial() throws Exception {
        System.out.println("Hello, World");
    }

    @Test
    public void testName() throws Exception {
        Function<Integer, Function<Integer, Integer>> add = a -> b -> a + b;

        Function<Integer, Integer> add6 = add.apply(6);
        Function<Integer, Integer> add5 = add.apply(5);

        Predicate<Integer> is5 = i -> i == 5;
        Function<Integer, Boolean> _is5 = i -> i == 5;

        assertEquals(is5.test(5), _is5.apply(5));

        Consumer<String> print = s -> System.out.println(s);

        Consumer<String> _print = System.out::println;
        _print.accept("Hello");

        Supplier<String> supplyHello = () -> "Hello";

        Supplier<String> createIntArray = String::new;
        String string = createIntArray.get();
        System.out.println(string);

        print.accept(supplyHello.get());

        Function<Integer, Integer> add11 = add6.compose(add5);

        System.out.println(add11.apply(19));
        System.out.println(add11.apply(119));

    }

}
