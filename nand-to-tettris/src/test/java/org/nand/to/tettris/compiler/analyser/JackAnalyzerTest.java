package org.nand.to.tettris.compiler.analyser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.nand.to.tettris.vm.compiler.JackAnalyzer;

public class JackAnalyzerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNoArgs() throws Exception {
        JackAnalyzer.main();
    }

    @Test
    public void testExist() {
        JackAnalyzer.main("./src/test/resources/Test.jack");
        assertTrue(JackAnalyzer.isExist());
        assertTrue(JackAnalyzer.isFile());

        JackAnalyzer.main("./src/test/resources/TestF");
        assertTrue(JackAnalyzer.isExist());
        assertFalse(JackAnalyzer.isFile());
    }

    @Test
    public void testNotExist() {
        JackAnalyzer.main("./src/test/resources/Test314.jack");
        assertFalse(JackAnalyzer.isExist());
        JackAnalyzer.main("./src/test/resources/Test314F");
        assertFalse(JackAnalyzer.isExist());
    }

    @Test
    public void testArray() {
        JackAnalyzer.main("./src/test/resources/ArrayTest");

    }

    @Test
    public void testSeven() {
        JackAnalyzer.main("./src/test/resources/project11/Seven");

    }

    @Test
    public void testSquareExLess() {
        JackAnalyzer.main("./src/test/resources/ExpressionLessSquare");

    }

    @Test
    public void testSquare() {
        JackAnalyzer.main("./src/test/resources/Square");

    }
}
