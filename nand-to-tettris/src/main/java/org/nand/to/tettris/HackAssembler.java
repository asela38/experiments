package org.nand.to.tettris;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Hello world! */
public class HackAssembler {

    private static Map<String, Integer> symbolTable    = new HashMap<>();
    private static Integer              vc             = 16;
    private static Map<String, String>  destinationMap = new HashMap<>();
    private static Map<String, String>  jumpMap        = new HashMap<>();
    private static Map<String, String>  computationMap = new HashMap<>();

    static {
        for (int i = 0; i < 16; i++)
            symbolTable.put("R" + i, i);

        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("LOOP", 4);
        symbolTable.put("STOP", 18);
        symbolTable.put("END", 22);

        jumpMap.put(null, "000");
        jumpMap.put("", "000");
        jumpMap.put("JGT", "001");
        jumpMap.put("JEQ", "010");
        jumpMap.put("JGE", "011");
        jumpMap.put("JLT", "100");
        jumpMap.put("JNE", "101");
        jumpMap.put("JLE", "110");
        jumpMap.put("JMP", "111");

        destinationMap.put(null, "000");
        destinationMap.put("", "000");
        destinationMap.put("M", "001");
        destinationMap.put("D", "010");
        destinationMap.put("MD", "011");
        destinationMap.put("A", "100");
        destinationMap.put("AM", "101");
        destinationMap.put("AD", "110");
        destinationMap.put("AMD", "111");

        computationMap.put("0", "0101010");
        computationMap.put("1", "0111111");
        computationMap.put("-1", "0111010");
        computationMap.put("D", "0001100");
        computationMap.put("A", "0110000");
        computationMap.put("!D", "0001101");
        computationMap.put("!A", "0110001");
        computationMap.put("-D", "0001111");
        computationMap.put("-A", "0110011");
        computationMap.put("D+1", "0011111");
        computationMap.put("A+1", "0110111");
        computationMap.put("D-1", "0001110");
        computationMap.put("A-1", "0110010");
        computationMap.put("D+A", "0000010");
        computationMap.put("D-A", "0010011");
        computationMap.put("A-D", "0000111");
        computationMap.put("D&A", "0000000");
        computationMap.put("D|A", "0010101");

        computationMap.put("M", "1110000");
        computationMap.put("!M", "1110001");
        computationMap.put("-M", "1110011");
        computationMap.put("M+1", "1110111");
        computationMap.put("M-1", "1110010");
        computationMap.put("D+M", "1000010");
        computationMap.put("D-M", "1010011");
        computationMap.put("M-D", "1000111");
        computationMap.put("D&M", "1000000");
        computationMap.put("D|M", "1010101");
    }

    public static void main(String[] args) {

        if (args == null || args.length < 1)
            throw new IllegalArgumentException("File Name should be specified");

        List<String> readAllLines = getAsmLines(args);

        Map<Integer, String> code = eliminateLables(readAllLines);

        for (int i = 0; i < code.size(); i++) {
            String instruction = code.get(i);
            String machineCode = parse(instruction);
            // System.out.printf("%s - // %s%n", machineCode, instruction);
            System.out.printf("%s%n", machineCode);
        }

        // System.out.println(symbolTable);

    }

    private static String parse(String instruction) {
        // A instruction
        if (instruction.startsWith("@")) {
            String address = instruction.substring(1);

            Integer value;
            char firstLetter = address.charAt(0);
            if (('a' <= firstLetter && firstLetter <= 'z') || ('A' <= firstLetter && firstLetter <= 'Z')) {
                if (!symbolTable.containsKey(address)) {
                    symbolTable.put(address, vc++);
                }
                value = symbolTable.get(address);
            } else {
                value = Integer.parseInt(address);
            }

            return String.format("0%15s", Integer.toBinaryString(value)).replace(" ", "0");
        } else { // C instruction
            String dist = "000", comp = "", jmp = "000";
            if (instruction.contains("=")) {
                String[] split = instruction.split("=");
                dist = destinationMap.get(split[0]);
                instruction = split[1];
            }

            if (instruction.contains(";")) {
                String[] split = instruction.split(";");
                jmp = jumpMap.get(split[1]);
                instruction = split[0];
            }

            comp = computationMap.get(instruction);

            return String.format("111%s%s%s", comp, dist, jmp);

        }
    }

    private static Map<Integer, String> eliminateLables(List<String> readAllLines) {
        Pattern label = Pattern.compile("\\((.*)\\)");
        // clean empty lines and comments

        AtomicInteger pc = new AtomicInteger(0);
        Map<Integer, String> code = new HashMap<>();
        readAllLines.stream()
                .map(line -> line.replaceAll("\\s*", "")) // remove all spaces
                .filter(line -> !line.isEmpty())
                .filter(line -> !line.startsWith("//"))
                .map(line -> line.replaceAll("//.*", ""))
                .filter(line -> {
                    Matcher labelMatcher = label.matcher(line);

                    if (labelMatcher.matches()) {
                        String symbol = labelMatcher.group(1);
                        symbolTable.put(symbol, pc.get());
                        return false;
                    }
                    return true;

                })
                .forEach(line -> code.put(pc.getAndIncrement(), line));
        return code;
    }

    private static List<String> getAsmLines(String[] args) {
        File file = new File(args[0]);
        try {
            return Files.readAllLines(Paths.get(file.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
