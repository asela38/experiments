package org.Algorithms.statistics;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestMeanMedianMood {

    @Test
    public void testVisuallyMeanMedian() throws Exception {
        printMeanMedianAndMode(Arrays.asList(4), 4, 4, 4, 4, 4);
        printMeanMedianAndMode(Arrays.asList(3, 4), 3.5, 3, 3.5, 4, 3);
        printMeanMedianAndMode(Arrays.asList(1, 3, 4), 8D / 3, 1, 3, 4, 1);
        printMeanMedianAndMode(Arrays.asList(1, 2, 3, 4, 4), 14D / 5, 1.5, 3, 4, 4);
        printMeanMedianAndMode(Arrays.asList(1, 2, 3, 4, 5), 3, 1.5, 3, 4.5, 1);
        printMeanMedianAndMode(Arrays.asList(1, 2, 3, 4, 4, 5), 19D / 6, 2, 3.5, 4, 4);
        printMeanMedianAndMode(Arrays.asList(1, 2, 3, 3, 4, 5), 3, 2, 3, 4, 3);
        printMeanMedianAndMode(Arrays.asList(1, 2, 3, 3, 5, 5), 19D / 6, 2, 3, 5, 3);
        printMeanMedianAndMode(Arrays.asList(4, 17, 7, 14, 18, 12, 3, 16, 10, 4, 4, 12), 121D / 12, 4, 11, 15, 4);
    }

    private void printMeanMedianAndMode(List<Integer> list, double expectedMean, double expectedQ1, double expectedQ2,
            double expectedQ3, Integer expectedMode) {
        System.out.println(String.format("%20s", " ").replace(" ", ";"));
        System.out.println("Original List : " + list.stream().sorted().collect(Collectors.toList()));
        // mean
        double mean = list.stream().mapToInt(Integer::intValue).average().getAsDouble();
        System.out.println("Mean  : " + mean + " (Average)");

        assertThat(mean, is(expectedMean));

        // Q1
        double q1 = list.stream().sorted().skip(Math.max(0, ((list.size() / 2 + 1) / 2) - 1))
                .limit(1 + (1 + list.size() / 2) % 2).mapToInt(Integer::intValue).average().getAsDouble();
        System.out.println("Q1    : " + q1 + "");
        assertThat(q1, is(expectedQ1));

        // median
        double median = list.stream().sorted().skip(Math.max(0, ((list.size() + 1) / 2) - 1))
                .limit(1 + (1 + list.size()) % 2).mapToInt(Integer::intValue).average().getAsDouble();
        System.out.println("Median: " + median + " (Q2)");
        assertThat(median, is(expectedQ2));

        // Q2
        double q3 = list.stream().sorted()
                .skip(Math.max(0, ((list.size() + 1) / 2) ))
                .skip(Math.max(0, ((list.size() / 2 + 1) / 2) - 1))
                .limit(1 + (1 + list.size() / 2) % 2)
                .mapToInt(Integer::intValue).average().orElse(list.get(0));
        System.out.println("Q3    : " + q3 + "");
        assertThat(q3, is(expectedQ3));

        // mode
        Integer mode = list.stream()
                .collect(Collectors.groupingBy(i -> i, () -> new TreeMap<Integer, Long>(), Collectors.counting()))
                .entrySet().stream().sorted((a, b) -> {
                    if (!a.getValue().equals(b.getValue()))
                        return b.getValue().compareTo(a.getValue());
                    return a.getKey().compareTo(b.getKey());
                }).findFirst().get().getKey();
        System.out.println("Mode  : " + mode + " (Highest Frequence)");
        assertThat(mode, is(expectedMode));
    }
}
