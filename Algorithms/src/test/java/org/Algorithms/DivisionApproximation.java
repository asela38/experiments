package org.Algorithms;

import org.junit.Test;

public class DivisionApproximation {

    
    @Test
    public void divideBy3Test() throws Exception {
        for(int i = 0; i < 1000; i++) {
            System.out.println( ( i / 3) - ( ( i >> 1 ) + ( i >> 2) + (i >> 3 ) + (i >> 4) + (i >> 5)) );
        }
        
    }
}
