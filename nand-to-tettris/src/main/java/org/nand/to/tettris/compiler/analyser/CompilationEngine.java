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

        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            switch (tokenizer.tokenType()) {
            case IDENTIFIER:
                System.out.printf("%s (%s) %n", tokenizer.identifier(), tokenizer.tokenType());
                break;
            case INT_CONST:
                System.out.printf("%s (%s) %n", tokenizer.intVal(), tokenizer.tokenType());
                break;
            case KEYWORD:
                System.out.printf("%s (%s) %n", tokenizer.keyWord(), tokenizer.tokenType());
                break;
            case STRING_CONST:
                System.out.printf("%s (%s) %n", tokenizer.stringVal(), tokenizer.tokenType());
                break;
            case SYMBOL:
                System.out.printf("%s (%s) %n", tokenizer.symbol(), tokenizer.tokenType());
                break;
            default:
                System.out.printf(" Unrecognized Type %n");
                break;

            }

        }
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
