package org.nand.to.tettris.vm.translator;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeWriter implements Closeable {

    private static final String STATIC        = "static";
    private static final String CONSTANT      = "constant";
    private static final String THAT          = "that";
    private static final String TEMP          = "temp";
    private static final String POINTER       = "pointer";
    private static final String THIS          = "this";
    private static final String ARGUMENT      = "argument";
    private static final String LOCAL         = "local";
    private Writer              writer;
    private String              fileName;
    Pattern                     filePattern   = Pattern.compile(".*([A-Za-z0-9]+).asm");
    private static int          compCounter   = 0;
    private static int          returnCounter = 0;

    public CodeWriter(String fileName) throws FileNotFoundException {
        System.out.println(fileName + " " + filePattern.pattern());
        Matcher matcher = filePattern.matcher(fileName);
        System.out.println(matcher.matches());
        this.fileName = matcher.group(1);
        File file = new File(fileName);
        System.out.printf("[%s] opens [%s] for write. n", this.getClass().getName(), file.getAbsolutePath());
        writer = new PrintWriter(file);
    }

    private void wl(String line, Object... args) throws IOException {
        writer.write("\t" + String.format(line + "%n", args));
    }

    public void writeArithmetic(String command) throws IOException {
        switch (command) {
        case "add":
            wl("@SP");
            wl("M=M-1");
            wl("A=M");
            wl("D=M");
            wl("A=A-1");
            wl("M=D+M");
            break;
        case "sub":
            wl("@SP");
            wl("M=M-1");
            wl("A=M");
            wl("D=M");
            wl("A=A-1");
            wl("M=D-M");
            wl("M=-M");
            break;
        case "neg":
            wl("@SP");
            wl("A=M-1");
            wl("M=-M");
            break;
        case "eq": {
            String label = String.format("COMPARIZON.%d", compCounter++);
            wl("@SP");
            wl("M=M-1");
            wl("M=M-1");
            wl("A=M+1");
            wl("D=M");
            wl("A=A-1");
            wl("D=D-M");

            wl("@%s", label);
            wl("D;JNE");
            wl("D=-1");
            wl("@%s.END", label);
            wl("0;JMP");
            wl("(%s)", label);
            wl("D=0");
            wl("(%s.END)", label);

            pushD();
        }
            break;
        case "gt": {
            String label = String.format("COMPARIZON.%d", compCounter++);
            wl("@SP");
            wl("M=M-1");
            wl("M=M-1");
            wl("A=M+1");
            wl("D=M");
            wl("A=A-1");
            wl("D=D-M");

            wl("@%s", label);
            wl("D;JGE");
            wl("D=-1");
            wl("@%s.END", label);
            wl("0;JMP");
            wl("(%s)", label);
            wl("D=0");
            wl("(%s.END)", label);

            pushD();
        }
            break;
        case "lt": {
            String label = String.format("COMPARIZON.%d", compCounter++);
            wl("@SP");
            wl("M=M-1");
            wl("M=M-1");
            wl("A=M+1");
            wl("D=M");
            wl("A=A-1");
            wl("D=D-M");

            wl("@%s", label);
            wl("D;JLE");
            wl("D=-1");
            wl("@%s.END", label);
            wl("0;JMP");
            wl("(%s)", label);
            wl("D=0");
            wl("(%s.END)", label);

            pushD();
        }
            break;
        case "and":
            wl("@SP");
            wl("M=M-1");
            wl("A=M");
            wl("D=M");
            wl("A=A-1");
            wl("M=D&M");
            break;
        case "or":
            wl("@SP");
            wl("M=M-1");
            wl("A=M");
            wl("D=M");
            wl("A=A-1");
            wl("M=D|M");
            break;
        case "not":
            wl("@SP");
            wl("A=M-1");
            wl("M=!M");
            break;
        default:
            writeComment("!! Error: Operation " + command + " Unregonized");
        }

    }

    public void writePush(String segment, int index) throws IOException {
        switch (segment) {
        case LOCAL:
            pushOnSegment("LCL", index);
            break;
        case ARGUMENT:
            pushOnSegment("ARG", index);
            break;
        case THIS:
            pushOnSegment("THIS", index);
            break;
        case THAT:
            pushOnSegment("THAT", index);
            break;
        case CONSTANT:
            wl("@" + index + " // *sp = i");
            wl("D=A");

            pushD();
            break;
        case STATIC:
            wl(String.format("@%s.%d // *sp = %d", fileName, index, index));
            wl("D=M");

            pushD();
            break;
        case TEMP:
            wl(String.format("@%d // -- addr = LCL + %d", index, index));
            wl("D=A");
            wl("@5");
            wl("A=D+A // *sp = *addr\"");
            wl("D=M");

            pushD();
            break;
        case POINTER:
            String term = index == 0 ? "THIS" : "THAT";
            wl(String.format("@%s // *sp = %s", term, term));
            wl("D=M");

            pushD();
            break;
        default:
            writeComment("!! Error: Segment " + segment + " Unregonized");
        }

    }

    public void writePop(String segment, int index) throws IOException {
        switch (segment) {
        case LOCAL:
            popOnSegment("LCL", index);
            break;
        case ARGUMENT:
            popOnSegment("ARG", index);
            break;
        case THIS:
            popOnSegment("THIS", index);
            break;
        case THAT:
            popOnSegment("THAT", index);
            break;
        case CONSTANT:
            break;
        case STATIC:

            popD();

            wl(String.format("@%s.%d", fileName, index));
            wl("M=D");

            break;
        case TEMP:
            wl(String.format("@%d // addr = LCL + %d", index, index));
            wl("D=A");
            wl("@5");
            wl("D=A+D");
            wl("@addr");
            wl("M=D");

            popD();

            wl("@addr");
            wl("A=M");
            wl("M=D");
            break;
        case POINTER:
            popD();

            String term = index == 0 ? "THIS" : "THAT";
            wl(String.format("@%s // *sp = %s", term, term));
            wl("M=D");
            break;
        default:
            writeComment("!! Error: Segment " + segment + " Unregonized");
        }
    }

    private void popOnSegment(String segment, int index) throws IOException {
        wl(String.format("@%d // addr = LCL + %d", index, index));
        wl("D=A");
        wl("@" + segment);
        wl("D=M+D");
        wl("@addr");
        wl("M=D");

        popD();

        wl("@addr");
        wl("A=M");
        wl("M=D");
    }

    private void popD() throws IOException {
        wl("@SP // *addr = *(SP--)");
        wl("M=M-1");
        wl("A=M");
        wl("D=M");
    }

    private void pushOnSegment(String segment, int index) throws IOException {
        wl(String.format("@%d // -- addr = LCL + %d", index, index));
        wl("D=A");
        wl("@" + segment);
        wl("A=D+M // *sp = *addr\"");
        wl("D=M");

        pushD();
    }

    private void pushD() throws IOException {
        wl("@SP // Push D to Stack");
        wl("A=M");
        wl("M=D");
        wl("@SP // SP++");
        wl("M=M+1");
    }

    public void writeComment(String comment) throws IOException {
        writer.write(String.format("%n// %s %n", comment));
    }

    public void writeLabel(String label) throws IOException {
        writer.write(String.format("(%s)%n", label));
    }

    /** @throws IOException
     * @see java.io.Writer#close() */
    @Override
    public void close() throws IOException {
        writer.close();
    }

    public void writeIf(String label) throws Exception {
        popD();
        wl("@%s", label);
        wl("D;JNE");
    }

    public void writeGoto(String label) throws Exception {
        wl("@%s", label);
        wl("0;JMP");
    }

    public void writeCall(String arg1, int arg2) throws Exception {

        writeComment("call for " + arg1);
        String returnLabel = String.format("%s$ret.%d", fileName, returnCounter++);

        wl("@%s", returnLabel);
        wl("D=A");
        pushD();

        pushAddressToStack("LCL");
        pushAddressToStack("ARG");
        pushAddressToStack("THIS");
        pushAddressToStack("THAT");

        // ARG = SP - 5 - nArgs

        wl("@%d", arg2 + 5);
        wl("D=A");
        wl("@SP");
        wl("D=M-D");
        wl("@ARG");
        wl("M=D");

        // LCL = SP
        wl("@SP");
        wl("D=M");
        wl("@LCL");
        wl("M=D");

        // goto function name
        wl("@%s", arg1);
        wl("0;JMP");

        writeLabel(returnLabel);
    }

    private void pushAddressToStack(String returnLabel) throws IOException {
        wl("@%s", returnLabel);
        wl("D=M");
        pushD();
    }

    public void writeFunction(String arg1, int arg2) throws Exception {
        writeLabel(arg1);
        for (int i = 0; i < arg2; i++) {
            wl("@%s", "0");
            wl("D=A");
            pushD();
        }

    }

    public void writeReturn() throws Exception {
        writeComment("return");

        // endFrame = LCL
        wl("@LCL");
        wl("D=M");
        wl("@endFrame");
        wl("M=D");

        // retAddr = *(endFrame - 5)
        wl("@5");
        wl("A=D-A");
        wl("D=M");
        wl("@retAddr");
        wl("M=D");

        // *ARG = POP()
        popD();
        wl("@ARG");
        wl("A=M");
        wl("M=D");

        // SP = ARG + 1;
        wl("D=A+1");
        wl("@SP");
        wl("M=D");

        // LCL = *(endFrame – 4) // restores LCL of the caller
        wl("@4");
        wl("D=A");
        wl("@endFrame");
        wl("A=M-D");
        wl("D=M");
        wl("@LCL");
        wl("M=D");

        // ARG = *(endFrame – 3) // restores ARG of the caller
        wl("@3");
        wl("D=A");
        wl("@endFrame");
        wl("A=M-D");
        wl("D=M");
        wl("@ARG");
        wl("M=D");

        // THIS = *(endFrame – 2) // restores THIS of the caller
        wl("@2");
        wl("D=A");
        wl("@endFrame");
        wl("A=M-D");
        wl("D=M");
        wl("@THIS");
        wl("M=D");

        // THAT = *(endFrame – 1) // restores THAT of the caller
        wl("@1");
        wl("D=A");
        wl("@endFrame");
        wl("A=M-D");
        wl("D=M");
        wl("@THAT");
        wl("M=D");

        // goto retAddr // goes to the caller’s return address
        wl("@retAddr");
        wl("A=M");
        wl("0;JMP");

    }

    public void writeInit() throws Exception {
        wl("@256");
        wl("D=A");
        wl("@SP");
        wl("M=D");
        writeCall("Sys.init", 0);

    }

}
