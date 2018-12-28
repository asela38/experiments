package org.nand.to.tettris.compiler.analyser;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class JackTokenizer implements Closeable {

    public JackTokenizer(File inputFile) {

    }

    public boolean hasMoreTokens() {
        return false;
    }

    public void advance() {

    }

    public TokenType tokenType() {
        return null;
    }

    public KeyWord keyWord() {
        return null;
    }

    public char symbol() {
        return 'o';
    }

    public String identifier() {
        return null;
    }

    public int intVal() {
        return 0;
    }

    public String stringVal() {
        return null;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }
}
