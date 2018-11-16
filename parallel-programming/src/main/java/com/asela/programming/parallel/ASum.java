package com.asela.programming.parallel;

import java.util.concurrent.RecursiveAction;

public class ASum extends RecursiveAction {

    private static final long serialVersionUID = -7874650548827195237L;
    private int[]             A;
    private int               LO, HI, SUM;

    public ASum(int[] a, int lO, int hI) {
        super();
        A = a;
        LO = lO;
        HI = hI;
    }

    @Override
    protected void compute() {
        SUM = 0;
        for (int i = LO; i < HI; i++)
            SUM += A[i];

    }

    public int getSUM() {
        return SUM;
    }

}
