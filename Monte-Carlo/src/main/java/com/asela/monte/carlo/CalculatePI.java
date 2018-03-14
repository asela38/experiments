package com.asela.monte.carlo;

import java.util.concurrent.ThreadLocalRandom;

public class CalculatePI {

    public static void main(String[] args) {
        long limit = 1_000_000L;
        for (int j = 0; j < 1000; j++) {
            long insideCount = 0;
            for (long i = 1; i < limit; i++) {
                // 1. Define a domain of possible inputs
                // 2. Generate inputs randomly from a probability distribution over the domain 
                double x = ThreadLocalRandom.current().nextDouble(1);
                double y = ThreadLocalRandom.current().nextDouble(1);
                
                // 3. Perform a deterministic computation over the inputs
                double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                if (d <= 1)
                    insideCount++;
                // 4. aggregate the results
            }
           
            System.out.println(insideCount * 4);
        }
   //     System.out.println(Math.PI + " : " + limit + " : " + (insideCount * 4));

    }
}
