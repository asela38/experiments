package com.asela.programming.concurrent;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LiveLocks {

    public static final long LIMIT  = 1_000_000_000_000L;
    public static int       global = 0;
    public static int       count  = 0;

    public static void main(String[] args) throws Exception {

        Thread[] ts = { new Thread(() -> {
            print("Started");
            while (global < LIMIT) {
                global++;
                count++;
            }
            print("Ended");
        }), new Thread(() -> {
            print("Started");
            while (global > -LIMIT) {
                global--;
                count++;
            }
            print("Ended");
        }), new Thread(() -> {
            while (true) {
                sleep(1);
                print("%,d", global);
            }
        }) };
        Arrays.stream(ts).forEach(t -> t.start());

        for (Thread thread : ts) {
            thread.join();
        }

        print("Locked   :: global: %,d count: %,d ", global, count);

    }

    public static void sleep(long l) {
        try {
            TimeUnit.SECONDS.sleep(2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void print(Object... args) {
        print("%s", args);
    }

    public static void print(String format, Object... args) {
        System.out.printf("[ %-20s ] - %s%n", Thread.currentThread().getName(), String.format(format, args));
    }

}
