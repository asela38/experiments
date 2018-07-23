package org.Algorithms;

public class FastInverseSquareRoot {

    public static void main(String[] args) {

        for (int i = 2; i < 100; i++)
            System.out.printf("%f%n", 1 / Math.sqrt(i) - invSqrt(i));
    }

    public static double invSqrt(double x) {
        double xhalf = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x *= (1.5d - xhalf * x * x);
        return x;
    }
}
