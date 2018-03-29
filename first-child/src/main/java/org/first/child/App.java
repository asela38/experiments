package org.first.child;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Hello world!
 *
 */
public class App 
{
    static Function<Integer, Integer> factorial ;
    static {
        factorial = i -> {
            if(i < 2) return  1;
            else return i * factorial.apply(i-1);    
        };
    }
    
    UnaryOperator<Long> f = i -> i < 2 ? 1 : i * this.f.apply(i);
    
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    
        
        System.out.println(factorial.apply(12));
            
        
    }
    
    
}
