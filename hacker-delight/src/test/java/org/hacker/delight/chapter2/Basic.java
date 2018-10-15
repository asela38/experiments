package org.hacker.delight.chapter2;

import org.junit.Test;

public class Basic {

    @Test
    public void basic() throws Exception {
        int y = 0;
        for (int x = 0; x < 100; x++)
            System.out.printf(" %3s(%3s) | %6s(%6s) is powerOfTwo %s %n", x, y = turnOffLastBit(x),
                    Integer.toBinaryString(x), Integer.toBinaryString(y), powerOfTwo(x));
    }

    boolean powerOfTwo(int x) {
        return turnOffLastBit(x) == 0;
    }

    private int turnOffLastBit(int x) {
        return x & (x - 1);
    }

}
