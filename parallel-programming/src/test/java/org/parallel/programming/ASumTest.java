package org.parallel.programming;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

public class ASumTest {

    @Test
    public void testASum() {
        for (int i = 1; i < 100_000_000; i = (i == 1) ? (i + 1) : (i * 10)) {
            System.out.println("---------::" + i);
            int[] arr = (new Random(1)).ints(1, 100).limit(i).toArray();
            ASum aSum = new ASum(arr, 0, arr.length);

            ForkJoinPool pool = new ForkJoinPool();

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

}
