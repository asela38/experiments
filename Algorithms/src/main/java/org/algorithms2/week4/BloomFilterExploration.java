package org.algorithms2.week4;

import java.util.Arrays;

import org.junit.Test;

public class BloomFilterExploration {
    
    @Test
    public void testName() throws Exception {
        byte[] bytes = "1".getBytes();
        System.out.println(Arrays.toString(bytes));
        
        System.out.println(Integer.toBinaryString("1".hashCode()));
    }

}
