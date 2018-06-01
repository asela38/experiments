package org.first.child;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.apache.commons.lang3.time.StopWatch;

public class PairSumAlgorithmCheck {

    private static final int N = 500_000;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("--Execution Completed--")));
    }

    private enum Job {
        GENERATE_INPUT_FILE, DOUBLE_FOR_LOOP_SOLUTION, HASH_SET_BASED_SOLUTION
    }

    public static void main(String[] args) {
        Job job = Job.HASH_SET_BASED_SOLUTION;

        switch (job) {
        case GENERATE_INPUT_FILE:
            generate();
            break;
        case DOUBLE_FOR_LOOP_SOLUTION:
            for (int i = 1000; i < N / 2; i += 1000) {
                StopWatch watch = StopWatch.createStarted();
                int ans = doubleForLoopSolution(i);
                watch.stop();
                System.out.printf("%s , %s , %s %n", i, watch.getTime(), ans);
            }
            break;
        case HASH_SET_BASED_SOLUTION:
            for (int i = 1000; i < N / 2; i += 1000) {
                
                StopWatch watch = StopWatch.createStarted();
                int ans = hashSetBasedSolution(i);
                watch.stop();
                System.out.printf("%s , %s , %s %n", i, watch.getTime(), ans);
                break;
            }
            StopWatch watch = StopWatch.createStarted();
            int ans = hashSetBasedSolution(N);
            System.out.printf("%s , %s , %s %n", N, watch.getTime(), ans);
            break;
        }

    }

    private static int hashSetBasedSolution(int d) {
        try (Scanner scanner = new Scanner(new File("pairs_in.txt"))) {
            int n = scanner.nextInt();
            n = d == 0 ? n : d;
            int s = scanner.nextInt();
            Integer[] arr = new Integer[n];
            for (int i = 0; i < n; i++)
                arr[i] = scanner.nextInt();

            int count = 0;
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if (set.add(arr[i]))
                    if (arr[i] * 2 == s)
                        count++;
            }

            arr = set.toArray(new Integer[0]);
            for (int i = 0; i < arr.length; i++) {
                if (set.contains(s - arr[i])) {
                    set.remove(arr[i]);
                    set.remove(s - arr[i]);
                    count++;
                }
            }

            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception Aborted");
        }
    }

    private static int doubleForLoopSolution(Integer d) {
        try (Scanner scanner = new Scanner(new File("pairs_in.txt"))) {
            int n = scanner.nextInt();
            n = d == 0 ? n : d;
            int s = scanner.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++)
                arr[i] = scanner.nextInt();

            Set<Integer> set = new HashSet<>();
            int sameCount = 0;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i; j < n; j++) {
                    if (arr[i] + arr[j] == s) {
                        set.add(arr[i]);
                        set.add(arr[j]);
                        if (arr[i] == arr[j])
                            sameCount++;
                    }
                }
            }

            return (set.size() - sameCount) / 2 + sameCount;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Exception Aborted");
        }
    }

    private static void generate() {
        try (PrintStream out = new PrintStream(new File("pairs_in.txt"))) {
            Supplier<Integer> random = () -> ThreadLocalRandom.current().nextInt(N / 2);
            out.println(N + " " + random.get());
            IntStream.generate(random::get).limit(N).mapToObj(Integer::toString).map(x -> x + " ").forEach(out::print);

            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
