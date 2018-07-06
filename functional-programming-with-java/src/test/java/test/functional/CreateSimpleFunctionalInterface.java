package test.functional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateSimpleFunctionalInterface {

    private static Logger LOG = LoggerFactory.getLogger(CreateSimpleFunctionalInterface.class);
    
    @FunctionalInterface
    private interface IntegerFunction {
       Integer operate(Integer a, Integer b);
    }
    
    @Test
    public void testIntegerFunction() {
        IntegerFunction add = (a,b) -> a + b; 
        int x = 0, y = 0;
        LOG.info("Add Function result {} + {} = {} ", x = 10, y = 180,  add.operate(x, y));
    }
}
