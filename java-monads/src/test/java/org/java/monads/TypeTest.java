package org.java.monads;

import org.junit.Test;

public class TypeTest {

    
    @Test
    public void tryOnArrayAccess() throws Exception {
        System.out.println( Try.of(new int[] {}).attempt(a -> a[0]).get()); 
        System.out.println( Try.of(new int[] {}).attempt(a -> a[0]).getOrElse(0)); 
        System.out.println( Try.of(new int[] {12}).attempt(a -> a[0]).get()); 
    }
    
    
    @Test
    public void tryOnStringToInteger() throws Exception {
        System.out.println( Try.of("2323B").attempt(Integer::parseInt).get()); 
        System.out.println( Try.of("2323B").attempt(Integer::parseInt).getOrElse(-1)); 
        System.out.println( Try.of("2323").attempt(Integer::parseInt).get()); 
    }
}
