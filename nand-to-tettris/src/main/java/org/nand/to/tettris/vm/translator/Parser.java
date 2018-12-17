package org.nand.to.tettris.vm.translator;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements Closeable {

    private Scanner     scanner;
    private String      current;
    private String      arg1;
    private int         arg2;
    private CommandType commandType;
    private Pattern     popOpPattern        = Pattern.compile("pop (\\S+) (\\d+)");
    private Pattern     pushOpPattern       = Pattern.compile("push (\\S+) (\\d+)");
    private Pattern     arithmeticOpPattern = Pattern.compile("(\\S+)");
    private Pattern     labelOpPattern      = Pattern.compile("label (\\S+)");
    private Pattern     ifGotoOpPattern     = Pattern.compile("if-goto (\\S+)");

    public Parser(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        System.out.printf("[%s] opens [%s] for read. %n", this.getClass().getName(), file.getAbsolutePath());
        scanner = new Scanner(file);
    }

    public boolean hasMoreCommands() {
        return scanner.hasNextLine();
    }

    public String current() {
        return current;
    }

    public CommandType commandType() {
        return commandType;
    }

    public String arg1() {
        return arg1;
    }

    public int arg2() {
        return arg2;
    }

    public boolean advance() {
        if (hasMoreCommands()) {
            current = scanner.nextLine();
            System.out.printf("[%s]%n", current);
            // Clean the line (remove comments and trim the line)
            current = current.replaceAll("//.*$", " ").trim();

            Matcher popOpMatcher = popOpPattern.matcher(current);
            Matcher pushOpMatcher = pushOpPattern.matcher(current);
            Matcher arithmeticOpMatcher = arithmeticOpPattern.matcher(current);
            Matcher labelOpMatcher = labelOpPattern.matcher(current);
            Matcher ifGotoOpMatcher = ifGotoOpPattern.matcher(current);

            if (current.isEmpty()) {
                System.out.println("Empty Line" + current);
                advance();
            } else if (arithmeticOpMatcher.matches()) {
                commandType = CommandType.C_ARITHMETIC;
                arg1 = arithmeticOpMatcher.group(1);
            } else if (popOpMatcher.matches()) {
                commandType = CommandType.C_POP;
                arg1 = popOpMatcher.group(1);
                arg2 = Integer.parseInt(popOpMatcher.group(2));
            } else if (pushOpMatcher.matches()) {
                commandType = CommandType.C_PUSH;
                arg1 = pushOpMatcher.group(1);
                arg2 = Integer.parseInt(pushOpMatcher.group(2));
            } else if (labelOpMatcher.matches()) {
                commandType = CommandType.C_LABEL;
                arg1 = labelOpMatcher.group(1);
            } else if (ifGotoOpMatcher.matches()) {
                commandType = CommandType.C_IF;
                arg1 = ifGotoOpMatcher.group(1);
            } else {
                System.out.println("Non matching Line: " + current);
                advance();
            }
            return true;
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
