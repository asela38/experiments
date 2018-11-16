package com.asela.programming.concurrent;

public class UnStructuredLocks {

    public static final int LIMIT  = 100000;
    public static int       global = 0;

    public static void main(String[] args) throws Exception {

        unlockedVersion();

        print("Unlocked :: global: %,d Expected: %,d ", global, LIMIT * 2);

        lockedVersion();

        print("Locked   :: global: %,d Expected: %,d ", global, LIMIT * 2);

        print("Done!");

    }

    public static void lockedVersion() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            print("Started");
            for (int i = 0; i < LIMIT; i++) {
                synchronized (System.out) {
                    global++;
                }
            }
            print("Ended");
        }), t2 = new Thread(() -> {
            print("Started");
            for (int i = 0; i < LIMIT; i++) {
                synchronized (System.out) {
                    global++;
                }
            }
            print("Ended");
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void unlockedVersion() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            print("Started");
            for (int i = 0; i < LIMIT; i++) {
                global++;
            }
            print("Ended");
        }), t2 = new Thread(() -> {
            print("Started");
            for (int i = 0; i < LIMIT; i++) {
                global++;
            }
            print("Ended");
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void print(Object... args) {
        print("%s", args);
    }

    public static void print(String format, Object... args) {
        System.out.printf("[ %-20s ] - %s%n", Thread.currentThread().getName(), String.format(format, args));
    }

}
