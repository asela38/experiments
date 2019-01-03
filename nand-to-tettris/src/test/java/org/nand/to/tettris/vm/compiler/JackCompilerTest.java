package org.nand.to.tettris.vm.compiler;

import org.junit.Test;

public class JackCompilerTest {

    @Test
    public void testSeven() {
        JackCompiler.main("./src/test/resources/project11/Seven");
    }

    @Test
    public void testConvertToBin() {
        JackCompiler.main("./src/test/resources/project11/ConvertToBin");
    }
}
