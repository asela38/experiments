package com.asela.monte.carlo;

import java.util.concurrent.ThreadLocalRandom;

public class RandomWalkProblem {

    /**
     * What is the longest random walk you can take
     * so that on average you will end up 4 blocks or fewer from home?
     */
    
    public static void main(String[] args) {
        
        int limit = 10000, walkLength = 100;
        for(int j = 0 ; j < walkLength; j++) {
            int closerThanCount = 0;
            for(int i = 0; i < limit; i++) {
                if(walk(j) <= 4) closerThanCount++;
            }
            System.out.printf("Average for walk of %s is %s %n" , j, ((double)closerThanCount / limit * 100));
     //       System.out.println(closerThanCount);
        }
    }
    
    public static int  walk(int stepCount) {
        int x = 0, y = 0;
        int[][] outComes = new int[][] { new int[] {0, 1}, new int[] {1, 0},  new int[] {0, -1}, new int[] {-1, 0} };
        for(int i = 0 ; i < stepCount; i++) {
            int[] dicision = outComes[ThreadLocalRandom.current().nextInt(0, 4)];
            x +=dicision[0];
            y +=dicision[1];
        }
        
        return Math.abs(x) + Math.abs(y); 
    }
}
