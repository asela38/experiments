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

        // debugTokenizer();

        compileClass();
    }

    private void debugTokenizer() {
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

    private void openTag(String tag) {
        writer.printf("<%s>%n", tag);
    }

    private void closeTag(String tag) {
        writer.printf("</%s>%n", tag);
    }

    private void singleTag(String key, String value) {
        writer.printf("<%1$s>%2$s</%1$s>%n", key, value);
    }

    private void keyWordTag() {
        singleTag("keyword", tokenizer.keyWord().name().toLowerCase());
        tokenizer.advance();
    }

    private void identifierTag() {
        singleTag("identifier", tokenizer.identifier());
        tokenizer.advance();
    }

    private void symbolTag() {
        singleTag("symbol", String.valueOf(tokenizer.symbol()));
        tokenizer.advance();
    }

    public void compileClass() {
        tokenizer.advance();
        openTag("class");

        keyWordTag();
        identifierTag();
        symbolTag();

        outer: while (tokenizer.tokenType() == TokenType.KEYWORD) {
            switch (tokenizer.keyWord()) {

            case STATIC:
            case FIELD:
                compileClassVarDec();
                break;
            case FUNCTION:
            case METHOD:
            case CONSTRUCTOR:
                compileSubroutineDec();
                break;
            default:
                break outer;
            }
        }

        symbolTag();
        closeTag("class");
    }

    public void compileClassVarDec() {

    }

    public void compileSubroutineDec() {
        openTag("subroutineDec");
        keyWordTag();
        keyWordTag();
        identifierTag();
        symbolTag();
        compileParameterList();
        symbolTag();
        compileSubroutineBody();
        closeTag("subroutineDec");

    }

    public void compileParameterList() {
        openTag("parameterList");
        closeTag("parameterList");

    }

    public void compileSubroutineBody() {
        openTag("subroutineBody");
        symbolTag();
        compileVarDec();
        closeTag("subroutineBody");
    }

    public void compileVarDec() {
        while (tokenizer.keyWord() == KeyWord.VAR) {

            openTag("varDec");
            keyWordTag();
            identifierTag();
            identifierTag();
            while (tokenizer.symbol() == ',') {
                symbolTag();
                identifierTag();
            }
            symbolTag();
            closeTag("varDec");
        }

    }

    public void compileStatements() {
        openTag("statements");
        closeTag("statements");
    }

    public void compileLet() {
        openTag("letStatement");
        closeTag("letStatement");
    }

    public void compileIf() {
        openTag("ifStatement");
        closeTag("ifStatement");
    }

    public void compileWhile() {
        openTag("whileStatement");
        closeTag("whileStatement");
    }

    public void compileDo() {
        openTag("doStatement");
        closeTag("doStatement");
    }

    public void compileReturn() {
        openTag("returnStatement");
        closeTag("returnStatement");
    }

    public void compileExpression() {
        openTag("expression");
        closeTag("expression");
    }

    public void compileTerm() {
        openTag("term");
        closeTag("term");
    }

    public void compileExpressionList() {
        openTag("expressionList");
        closeTag("expressionList");
    }

    @Override
    public void close() throws IOException {
        writer.flush();
        writer.close();
        tokenizer.close();
    }
}
