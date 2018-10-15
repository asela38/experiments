package org.Algorithms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;

public class InversionCounting {

    @Test
    public void testInversions() throws Exception {
        assertThat(inversions("1").f, is(0L));
    }

    @Test
    public void testInversionsInTwo2() throws Exception {

        String numbers = "6,5,4,3,2,1";
        Pair<Long, long[]> pair = inversions(numbers);

        System.out.println(pair.s.length);
        long[] sorted = Arrays.stream(numbers.split(",")).mapToLong(Long::parseLong).toArray();
        Arrays.sort(sorted);
        System.out.println(Arrays.equals(sorted, pair.s));
        for (int i = 0; i < sorted.length; i++) {
            System.out.printf("(%s, %s) -> %s %n", sorted[i], pair.s[i], sorted[i] == pair.s[i]);
        }
        
        System.out.println("Answer = " + pair.f);

    }

    @Test
    public void testInversionsInTwo() throws Exception {
        assertThat(inversions("1,2").f, is(0L));
        Consumer<long[]> print = a -> System.out.println(Arrays.toString(a));
        print.accept(inversions("2,1").s);
        assertThat(inversions("2,1").f, is(1L));
        assertThat(inversions("1,3,5,2,4,6").f, is(3L));
        assertThat(inversions("6,5,4,3,2,1").f, is(15L));

        List<String> lines = Files.readAllLines(new File("C:\\Asela\\mydocs\\Coursera\\IntegerArray.txt").toPath());
        Pair<Long, long[]> pair = inversions(lines.stream().collect(Collectors.joining(",")));

        System.out.println(pair.s.length);
        long[] sorted = lines.stream().mapToLong(Long::parseLong).toArray();
        Arrays.sort(sorted);
        System.out.println(Arrays.equals(sorted, pair.s));
        for (int i = 0; i < sorted.length; i++) {
            System.out.printf("(%s, %s) -> %s %n", sorted[i], pair.s[i], sorted[i] == pair.s[i]);
        }
        
        System.out.println("Answer = " + pair.f);

    }

    private Pair<Long, long[]> inversions(String string) {
        return inversions(Arrays.stream(string.split(",")).mapToLong(Long::parseLong).toArray());
    }

    private static class Pair<F, S> {
        F f;
        S s;

        static <FT, ST> Pair<FT, ST> of(FT f, ST s) {
            Pair<FT, ST> pair = new Pair<>();
            pair.f = f;
            pair.s = s;
            return pair;
        }
    }

    private Pair<Long, long[]> inversions(long[] array) {
        if (array.length < 2)
            return Pair.of(0L, array);
        long[] a1 = Arrays.copyOfRange(array, 0, array.length / 2);
        long[] a2 = Arrays.copyOfRange(array, array.length / 2, array.length);
        Pair<Long, long[]> ia1 = inversions(a1), ia2 = inversions(a2), isi = splitInversions(ia1.s, ia2.s);
        return Pair.of(ia1.f + ia2.f + isi.f, isi.s);
    }

    private Pair<Long, long[]> splitInversions(long[] a1, long[] a2) {
        long[] merged = new long[a1.length + a2.length];
        long c = 0L;
        for (int k = 0, i = 0, j = 0; k < merged.length; k++) {
            if (i >= a1.length) {
                merged[k] = a2[j++];
            } else if (j >= a2.length) {
                merged[k] = a1[i++];
            } else if (a1[i] < a2[j]) {
                merged[k] = a1[i++];
            } else {
                c += a1.length - i;
                merged[k] = a2[j++];
            }
        }
        // System.out.println(c);
        return Pair.of(c, merged);
    }

}
