package org.nand.to.tettris.vm.compiler;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nand.to.tettris.vm.compiler.VMWriter.Command;
import org.nand.to.tettris.vm.compiler.VMWriter.Segment;

public class CompilationEngine implements Closeable {
    private JackTokenizer tokenizer;
    private PrintWriter   writer;
    private VMWriter      vmWriter;
    private String        className;
    private SymbolTable   st;
    private static int    whileCount = 0;
    private static int    ifCount    = 0;

    public CompilationEngine(File inFile, File outFile, File vmFile) throws IOException {
        this(inFile, vmFile);
        writer = new PrintWriter(outFile);
    }

    public CompilationEngine(File inFile, File vmFile) throws IOException {
        tokenizer = new JackTokenizer(inFile);
        vmWriter = new VMWriter(vmFile);
        st = new SymbolTable();
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
        if (writer != null)
            writer.printf("<%s>%n", tag);
    }

    private void closeTag(String tag) {
        if (writer != null)
            writer.printf("</%s>%n", tag);
    }

    private void singleTag(String key, String value) {
        if (writer != null)
            writer.printf("<%1$s> %2$s </%1$s>%n", key, value);
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
        String symbol = String.valueOf(tokenizer.symbol());
        switch (symbol) {
        case ">":
            symbol = "&gt;";
            break;
        case "<":
            symbol = "&lt;";
            break;
        case "&":
            symbol = "&amp;";
            break;
        case "\"":
            symbol = "&quot;";
            break;
        default:
            break;
        }
        singleTag("symbol", symbol);
        tokenizer.advance();
    }

    private void integerConstant() {
        singleTag("integerConstant", String.valueOf(tokenizer.intVal()));
        tokenizer.advance();
    }

    private void stringConstant() {
        singleTag("stringConstant", tokenizer.stringVal());
        tokenizer.advance();
    }

    public void compileClass() {
        tokenizer.advance();
        openTag("class");

        keyWordTag();
        className = tokenizer.identifier();
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
                st.startSubroutine();
                compileSubroutineDec();
                break;
            default:
                break outer;
            }
        }

        symbolTag();
        closeTag("class");
    }

    private void keyOrIdTag() {
        if (tokenizer.tokenType() == TokenType.KEYWORD)
            keyWordTag();
        else
            identifierTag();
    }

    private String getKeyOrId() {
        if (tokenizer.tokenType() == TokenType.KEYWORD)
            return tokenizer.keyWord().toString().toLowerCase();
        else
            return tokenizer.identifier();
    }

    public void compileSubroutineDec() {
        openTag("subroutineDec");
        KeyWord subroutineType = tokenizer.keyWord();
        keyWordTag();
        String returnType = getKeyOrId();
        keyOrIdTag();
        String routineName = tokenizer.identifier();
        identifierTag();
        symbolTag();
        int count = compileParameterList();

        symbolTag();
        openTag("subroutineBody");
        symbolTag();
        int countLocal = compileVarDec();
        vmWriter.writeFunction(className + "." + routineName, countLocal);

        if (subroutineType == KeyWord.CONSTRUCTOR) {
            int fieldCount = st.varCount(Kind.FIELD);
            vmWriter.writePush(Segment.CONST, fieldCount);
            vmWriter.writeCall("Memory.alloc", 1);
            vmWriter.writePop(Segment.POINTER, 0);
        } else if (subroutineType == KeyWord.METHOD) {
            vmWriter.writePush(Segment.ARG, 0);
            vmWriter.writePop(Segment.POINTER, 0);
        }

        compileStatements();
        symbolTag();
        closeTag("subroutineBody");
        closeTag("subroutineDec");

    }


    public int compileVarDec() {
        int count = 0;
        while (tokenizer.keyWord() == KeyWord.VAR) {
            count++;
            openTag("varDec");
            keyWordTag();
            String type = getKeyOrId();
            keyOrIdTag();
            String name = tokenizer.identifier();
            identifierTag();
            st.define(name, type, Kind.VAR);
            while (tokenizer.symbol() == ',') {
                count++;
                symbolTag();
                name = tokenizer.identifier();
                st.define(name, type, Kind.VAR);
                identifierTag();
            }
            symbolTag();
            closeTag("varDec");
        }

        return count;

    }

    public void compileClassVarDec() {
        openTag("classVarDec");

        KeyWord kind = tokenizer.keyWord();
        keyWordTag();
        String type = getKeyOrId();
        keyOrIdTag();
        String name = tokenizer.identifier();
        identifierTag();
        st.define(name, type, Kind.of(kind));
        while (tokenizer.symbol() == ',') {
            symbolTag();
            name = tokenizer.identifier();
            st.define(name, type, Kind.of(kind));
            identifierTag();
        }
        symbolTag();

        closeTag("classVarDec");
    }

    public int compileParameterList() {
        int count = 0;
        openTag("parameterList");
        if (tokenizer.symbol() != ')') {
            String type = getKeyOrId();
            keyOrIdTag();
            String name = tokenizer.identifier();
            identifierTag();
            st.define(name, type, Kind.ARG);
            count++;
        }
        while (tokenizer.symbol() == ',') {
            symbolTag();
            String type = getKeyOrId();
            keyOrIdTag();
            String name = tokenizer.identifier();
            identifierTag();
            st.define(name, type, Kind.ARG);
            count++;
        }

        closeTag("parameterList");
        return count;

    }

    public int compileExpressionList() {
        int count = 0;
        openTag("expressionList");
        if (tokenizer.symbol() != ')') {
            compileExpression();
            count++;
        }

        while (tokenizer.symbol() == ',') {
            symbolTag();
            compileExpression();
            count++;
        }
        closeTag("expressionList");
        return count;
    }

    public void compileStatements() {
        openTag("statements");
        outer: while (tokenizer.tokenType() != TokenType.SYMBOL) {

            switch (tokenizer.keyWord()) {
            case LET:
                compileLet();
                break;
            case IF:
                compileIf();
                break;
            case WHILE:
                compileWhile();
                break;
            case DO:
                compileDo();
                break;
            case RETURN:
                compileReturn();
                break;
            default:
                break outer;
            }

        }
        closeTag("statements");
    }

    private static Map<Kind, Segment> kindSegmentMap = new HashMap<>();
    {
        kindSegmentMap.put(Kind.ARG, Segment.ARG);
        kindSegmentMap.put(Kind.STATIC, Segment.STATIC);
        kindSegmentMap.put(Kind.FIELD, Segment.THIS);
        kindSegmentMap.put(Kind.VAR, Segment.LOCAL);
    }

    public void compileLet() {
        openTag("letStatement");
        keyWordTag();
        String name = tokenizer.identifier();
        identifierTag();
        if (tokenizer.symbol() == '[') {
            symbolTag();
            compileExpression();
            symbolTag();
        }
        symbolTag();
        compileExpression();
        vmWriter.writePop(kindSegmentMap.get(st.kindOf(name)), st.indexOf(name));
        symbolTag();
        closeTag("letStatement");
    }

    public void compileIf() {
        openTag("ifStatement");
        keyWordTag();
        symbolTag();
        String l1 = "IF_TRUE" + ifCount, l2 = "IF_FALSE" + ifCount++;
        compileExpression();
        symbolTag();
        symbolTag();
        vmWriter.writeArithmetic(Command.NOT);
        vmWriter.writeIf(l1);
        compileStatements();
        symbolTag();
        vmWriter.writeGoto(l2);
        vmWriter.writeLabel(l1);
        if (tokenizer.keyWord() == KeyWord.ELSE) {

            keyWordTag();
            symbolTag();
            compileStatements();
            symbolTag();
        }
        vmWriter.writeLabel(l2);
        closeTag("ifStatement");
    }

    public void compileWhile() {
        openTag("whileStatement");
        keyWordTag();
        symbolTag();
        String l1 = "WHILE_EXP" + whileCount, l2 = "WHILE_END" + whileCount++;
        vmWriter.writeLabel(l1);
        compileExpression();
        vmWriter.writeArithmetic(Command.NOT);
        vmWriter.writeIf(l2);
        symbolTag();
        symbolTag();
        compileStatements();
        vmWriter.writeGoto(l1);
        vmWriter.writeLabel(l2);
        symbolTag();
        closeTag("whileStatement");
    }

    public void compileDo() {
        openTag("doStatement");
        keyWordTag();
        String callee = tokenizer.identifier();
        identifierTag();
        String obj = null;
        int offset = 0;
        if (tokenizer.symbol() == '.') {

            String type = st.typeOf(callee);
            if (type != null && !type.isEmpty()) {
                obj = callee;
                callee = type;
                offset = 1;
            }

            symbolTag();
            callee += "." + tokenizer.identifier();
            identifierTag();
            if (obj != null) {
                vmWriter.writePush(kindSegmentMap.get(st.kindOf(obj)), st.indexOf(obj));
            }
        } else {
            vmWriter.writePush(Segment.POINTER, 0);
            offset = 1;
            callee = className + "." + callee;
        }
        symbolTag();

        int count = compileExpressionList();
        vmWriter.writeCall(callee, count + offset);
        vmWriter.writePop(Segment.TEMP, 0);
        symbolTag();
        symbolTag();
        closeTag("doStatement");
    }

    public void compileReturn() {
        openTag("returnStatement");
        keyWordTag();
        if (tokenizer.symbol() != ';')
            compileExpression();
        else {
            vmWriter.writePush(Segment.CONST, 0);
        }
        vmWriter.writeReturn();
        symbolTag();
        closeTag("returnStatement");
    }

    private List<String> EOPS = Arrays.asList("+", "*", "-", "/", "&", "|", "<", ">", "=");

    public void compileExpression() {
        openTag("expression");
        compileTerm();
        while (EOPS.contains(String.valueOf(tokenizer.symbol()))) {
            String symbol = String.valueOf(tokenizer.symbol());
            symbolTag();
            compileTerm();
            switch (symbol) {
            case ">":
                vmWriter.writeArithmetic(Command.GT);
                break;
            case "<":
                vmWriter.writeArithmetic(Command.LT);
                break;
            case "&":
                vmWriter.writeArithmetic(Command.AND);
                break;
            case "|":
                vmWriter.writeArithmetic(Command.OR);
                break;
            case "=":
                vmWriter.writeArithmetic(Command.EQ);
                break;
            case "\"":
                vmWriter.writeCall("Math.divide", 2);
                break;
            case "*":
                vmWriter.writeCall("Math.multiply", 2);
                break;
            case "+":
                vmWriter.writeArithmetic(Command.ADD);
                break;
            case "-":
                vmWriter.writeArithmetic(Command.SUB);
                break;
            default:
                break;
            }
        }
        closeTag("expression");
    }

    public void compileTerm() {
        openTag("term");
        switch (tokenizer.tokenType()) {
        case IDENTIFIER:
            String id = tokenizer.identifier();
            identifierTag();
            switch (tokenizer.symbol()) {
            case '.':
                symbolTag();
                String obj = null;
                int offset = 0;
                String type = st.typeOf(id);
                if (type != null && !type.isEmpty()) {
                    obj = id;
                    id = type;
                    offset = 1;
                }
                String method = tokenizer.identifier();
                identifierTag();
                symbolTag();
                if (obj != null) {
                    vmWriter.writePush(kindSegmentMap.get(st.kindOf(obj)), st.indexOf(obj));
                }
                int count = compileExpressionList();
                symbolTag();
                vmWriter.writeCall(id + "." + method, count + offset);
                break;
            case '(':
                symbolTag();
                compileExpression();
                symbolTag();
                break;
            case '[':
                symbolTag();
                compileExpression();
                symbolTag();
                break;

            default:
                vmWriter.writePush(kindSegmentMap.get(st.kindOf(id)), st.indexOf(id));
                break;
            }

            break;
        case INT_CONST:
            vmWriter.writePush(Segment.CONST, tokenizer.intVal());
            integerConstant();
            break;
        case KEYWORD:
            switch (tokenizer.keyWord()) {
            case NULL:
                vmWriter.writePush(Segment.CONST, 0);
                break;
            case TRUE:
                vmWriter.writePush(Segment.CONST, 0);
                vmWriter.writeArithmetic(Command.NOT);
                break;
            case FALSE:
                vmWriter.writePush(Segment.CONST, 0);
                break;
            case THIS:
                vmWriter.writePush(Segment.POINTER, 0);
                break;
            default:
                break;
            }
            keyWordTag();
            break;
        case STRING_CONST:
            stringConstant();
            break;
        case SYMBOL:

            if (tokenizer.symbol() == '~') {
                symbolTag();
                compileTerm();
                vmWriter.writeArithmetic(Command.NOT);
            } else if (tokenizer.symbol() == '-') {

                symbolTag();
                compileTerm();
                vmWriter.writeArithmetic(Command.NEG);
            } else if (tokenizer.symbol() == '(') {
                symbolTag();
                compileExpression();
                symbolTag();
            } else {
                symbolTag();
            }
            break;
        default:
            break;

        }
        closeTag("term");
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
        vmWriter.close();
        tokenizer.close();
    }

    @Override
    public String toString() {
        switch (tokenizer.tokenType()) {
        case IDENTIFIER:
            return String.format("%s (%s) %n", tokenizer.identifier(), tokenizer.tokenType());
        case INT_CONST:
            return String.format("%s (%s) %n", tokenizer.intVal(), tokenizer.tokenType());
        case KEYWORD:
            return String.format("%s (%s) %n", tokenizer.keyWord(), tokenizer.tokenType());
        case STRING_CONST:
            return String.format("%s (%s) %n", tokenizer.stringVal(), tokenizer.tokenType());
        case SYMBOL:
            return String.format("%s (%s) %n", tokenizer.symbol(), tokenizer.tokenType());
        default:
            return String.format(" Unrecognized Type %n");

        }

    }
}
