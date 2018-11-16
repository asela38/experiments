package com.asela.programming.concurrent;

import java.util.concurrent.TimeUnit;

public class SimpleTest {

    public static void main(String[] args) throws Exception {

        final Thread main = Thread.currentThread();

        Thread t1 = new Thread(() -> {

            print("Started");
            try {
                TimeUnit.SECONDS.sleep(5);

                // Dead Lock which is not detecting
                main.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print("Ended");
        });
        t1.start();

        t1.join();

        print("Done!");

    }

    public static void print(Object... args) {
        print("%s", args);
    }

    public static void print(String format, Object... args) {
        System.out.printf("[ %-20s ] - %s%n", Thread.currentThread().getName(), String.format(format, args));
    }

}
