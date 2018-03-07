package org.binary.tree;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

/** Abandoned 
 *  Case of how not to do coding
 * @author asela.illayapparachc
 *
 */
public class TreePrintFailed {
    
    private static final Logger LOG = Logger.getGlobal();
   
    /* Failed */
    // Notes; This is an cumbersome effert with no real value
    public void print() {
        System.out.println("              %s              ");
        System.out.println("      %s              %s      ");
        System.out.println("              %s      %s      %s      %s  ");
        System.out.println("      %s              %s              %s  %s  %s  %s  %s  %s");
        System.out.println("  %s      %s      %s      %s      %s      %s          ");
        System.out.println("%s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %s      ");
        
    }
    
    /* Failed */
    // Stream are not good to develop an algorithm 
    public void print2() {
        Function<Integer, Integer> log2 =  value -> (int) Math.round( Math.log(value) / Math.log(2) );  
        Function<Integer, Integer> pow2 =  value -> (int) Math.pow(2, value);
        int start = (int) Math.pow(2, 4);
        int rows = log2.apply(start);
        int wordLength = 2;
        LOG.info("# Rows " +  rows);
        IntStream.iterate(start, i -> i / 2)
                 .limit(rows)
                // .map(i -> i / 2)
                 .mapToObj(length -> 
                        IntStream.range(0, pow2.apply(rows - log2.apply(length)))
                                 .mapToObj(i -> "%" + length)
                                 .collect(Collectors.joining(stringWithLength( wordLength * pow2.apply(log2.apply((length-1)/2) + 1) ) , stringWithLength(length)  , stringWithLength(length/2))))
                .forEach(System.out::println);
                          
                     
    }
    
    @Test
    public void testPrint2() throws Exception {
        print2();
    }
    
    @Test
    public void findingExponent() throws Exception {
        assertThat(Math.round(Math.log(32) / Math.log(2)), is(5L));
        assertThat(Math.round(Math.log(625) / Math.log(5)), is(4L));
    }
    
    @Test
    public void stringWithLengthTest() throws Exception {
       verifyLength(1, 1);
       verifyLength(2, 2);
       verifyLength(-1, 0);
       verifyLength(10, 10);
       verifyLength(-10, 0);
         
    }

    private void verifyLength(int requestLength, int actualLength) {
        assertThat( stringWithLength(requestLength).length(), is(actualLength));
    }
    

    private String stringWithLength(int i) {
        return IntStream.range(0, i).mapToObj(j -> " ").collect(Collectors.joining());
    }
}
