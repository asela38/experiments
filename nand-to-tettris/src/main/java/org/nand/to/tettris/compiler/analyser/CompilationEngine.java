package org.nand.to.tettris.compiler.analyser;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CompilationEngine implements Closeable {
    private JackTokenizer tokenizer;
    private PrintWriter   writer;

    public CompilationEngine(File inFile, File outFile) throws IOException {
        tokenizer = new JackTokenizer(inFile);
        writer = new PrintWriter(outFile);
    }

    public void compileClass() {

    }

    public void compileClassVarDec() {

    }

    @Override
    public void close() throws IOException {
        writer.flush();
        writer.close();
        tokenizer.close();
    }
}
