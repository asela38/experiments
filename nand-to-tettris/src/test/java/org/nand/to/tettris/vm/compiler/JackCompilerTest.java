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
    
    @Test
    public void testSquare() {
        JackCompiler.main("./src/test/resources/project11/Square");
    }

    @Test
    public void testAverage() {
        JackCompiler.main("./src/test/resources/project11/Average");
    }

    @Test
    public void testPong() {
        JackCompiler.main("./src/test/resources/project11/Pong");
    }

    @Test
    public void testPongBall() {
        JackCompiler.main("./src/test/resources/project11/Pong/Ball.jack");
    }
}
