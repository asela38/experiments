package com.asela.jvm.investingation;

/** -verbose:jni
 * 
 * @author asela.illayapparachc */
public class JNIExecutionTest {

    // private static AtomicInteger ai = new AtomicInteger();
    public static void main(String[] args) {
        int i = 10;
        int j = 11;
        System.out.println(i + j);
        System.out.println(Math.sin(i));
        // System.out.println(ai.incrementAndGet());

        A b = new B();
        b.hello();
    }
}

class A {

    public void hello() {
        System.out.println(A.class + " hello!");
    }
}

class B extends A {

    public void hello() {
        System.out.println(B.class + " hello!");
    }
}