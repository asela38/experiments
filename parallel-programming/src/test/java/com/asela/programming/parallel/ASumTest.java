package com.asela.programming.parallel;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.asela.programming.parallel.ASum;
import com.asela.programming.parallel.ASumRecursive;

import edu.rice.pcdp.PCDP;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ASumTest {

    private static final Random RANDOM = new Random(1);

    @Test
    public void testFork() throws Exception {
        int[] arr = RANDOM.ints(1, 100).limit(100).toArray();
        ASum aSum = new ASum(arr, 0, arr.length);
        aSum.fork();
        aSum.join();
        System.out.println(aSum.getSUM());
    }

    @Test
    public void testASum() {
        for (int i = 1; i < 200_000_000; i = (i == 1) ? (i + 1) : (i * 10)) {
            System.out.printf("---------::%,d%n", i);
            int[] arr = RANDOM.ints(1, 100).limit(i).toArray();
            ASum aSum = new ASum(arr, 0, arr.length);

            ForkJoinPool pool = new ForkJoinPool(2);

            long ts = System.currentTimeMillis();
            long fs = Runtime.getRuntime().freeMemory();
            pool.invoke(aSum);
            long t1 = System.currentTimeMillis() - ts;
            long f1 = fs - Runtime.getRuntime().freeMemory();

            System.out.println(aSum.getSUM());

            ASumRecursive aSumR = new ASumRecursive(arr, 0, arr.length);

            ts = System.currentTimeMillis();
            fs = Runtime.getRuntime().freeMemory();
            pool.invoke(aSumR);
            long t2 = System.currentTimeMillis() - ts;
            long f2 = fs - Runtime.getRuntime().freeMemory();

            System.out.println(aSum.getSUM());
            System.out.printf("t1: %5s, t2= %5s %n", t1, t2);
            System.out.printf("f1: %5s, f2= %5s %n", f1, f2);
        }
    }

    private static long sum1 = 0, sum2 = 0;

    @Test
    public void testParallism() {
        for (int i = 1; i < 200_000_000; i = (i == 1) ? (i + 1) : (i * 10)) {
            System.out.printf("---------::%,d%n", i);

            int[] arr = RANDOM.ints(1, 100).limit(i).toArray();
            sum1 = 0;
            sum2 = 0;

            long ts = System.currentTimeMillis();

            int limit = i;
            PCDP.finish(() -> {
                PCDP.async(() -> {
                    for (int j = 0; j < limit / 2; j++) {
                        sum1 += arr[j];
                    }
                });

                for (int j = limit / 2; j < limit; j++) {
                    sum2 += arr[j];
                }
            });
            long t1 = System.currentTimeMillis() - ts;

            System.out.printf("%,d%n", sum1 + sum2);

            sum1 = 0;
            sum2 = 0;

            ts = System.currentTimeMillis();

            @SuppressWarnings("serial")
            RecursiveAction ra = new RecursiveAction() {

                @Override
                protected void compute() {
                    for (int j = 0; j < limit / 2; j++) {
                        sum1 += arr[j];
                    }

                }
            };

            ra.fork();

            for (int j = i / 2; j < i; j++) {
                sum2 += arr[j];
            }

            ra.join();

            long t2 = System.currentTimeMillis() - ts;

            System.out.printf("%,d%n", sum1 + sum2);
            System.out.printf("t1: %5s, t2= %5s [%s] %n", t1, t2, Arrays.stream(arr).sum());

        }
    }

    @Test
    public void isolatedTestOnPCDP() throws Exception {

        int[] arr = RANDOM.ints(1, 10).limit(100_000_000).toArray();

        System.out.printf("---------::%,d (%,d)%n", arr.length, Arrays.stream(arr).sum());

        sum1 = 0;
        sum2 = 0;

        PCDP.finish(() -> {
            PCDP.async(() -> {
                for (int j = 0; j < arr.length / 2; j++) {
                    sum1 += arr[j];
                }
                System.out.println("Done!");
            });

            for (int j = arr.length / 2; j < arr.length; j++) {
                sum2 += arr[j];
            }
            System.out.println("Done!!");
        });

        System.out.printf("%,d%n", sum1 + sum2);

    }

}
