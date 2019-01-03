package org.nand.to.tettris.vm.compiler;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class VMWriter implements Closeable {

    public enum Segment {
        CONST("constant"), ARG("argument"), LOCAL("local"), STATIC("static"), THIS("this"), THAT("that"), POINTER(
                "pointer"), TEMP("temp");

        private String name;

        private Segment(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Command {
        ADD("add"), SUB("sub"), NEG("neg"), EQ("eq"), GT("gt"), LT("lt"), AND(
                "and"), OR("or"), NOT("not");

        private String name;

        private Command(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private PrintWriter writer;

    public VMWriter(File vmFile) throws IOException {
        writer = new PrintWriter(vmFile);
    }

    public void writePush(Segment segment, int index) {
        writer.printf("push %s %d %n", segment.getName(), index);
    }

    public void writePop(Segment segment, int index) {
        writer.printf("pop %s %d %n", segment.getName(), index);
    }

    public void writeArithmetic(Command command) {
        writer.printf("%s %n", command.getName());
    }

    public void writeLabel(String label) {
        writer.printf("label %s %n", label);
    }

    public void writeIf(String label) {
        writer.printf("if-goto %s %n", label);
    }

    public void writeGoto(String label) {
        writer.printf("goto %s %n", label);
    }

    public void writeCall(String name, int nArgs) {
        writer.printf("call %s %d %n", name, nArgs);
    }

    public void writeFunction(String name, int nLocals) {
        writer.printf("function %s %d %n", name, nLocals);
    }

    public void writeReturn() {
        writer.printf("return %n");
    }

    @Override
    public void close() throws IOException {
        writer.flush();
        writer.close();
    }

}
