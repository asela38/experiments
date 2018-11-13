package org.parallel.programming;

import java.util.concurrent.RecursiveAction;

public class ASumRecursive extends RecursiveAction {

    private static final long serialVersionUID = 7740788022850573009L;
    private int[]             A;
    private int               LO, HI, SUM;

    public ASumRecursive(int[] a, int lO, int hI) {
        super();
        A = a;
        LO = lO;
        HI = hI;
    }

    @Override
    protected void compute() {

        // System.out.printf("LO: %5s, HI: %5s [%s]%n", LO, HI, Thread.currentThread().getName());
        SUM = 0;
        if (LO + 1 == HI)
            SUM += A[LO];
        else if (LO + 1 > HI)
            SUM += 0;
        else {
            int mid = (LO + HI) >> 1;
            ASumRecursive lowHalf = new ASumRecursive(A, LO, mid), highHalf = new ASumRecursive(A, mid, HI);
            invokeAll(lowHalf, highHalf);
            SUM += lowHalf.getSUM() + highHalf.getSUM();
        }

    }

    public int getSUM() {
        return SUM;
    }

}
