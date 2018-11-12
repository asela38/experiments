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
    public static void main(String[] args) {

        if (args == null || args.length < 1)
            throw new IllegalArgumentException("File Name should be specified");

        List<String> readAllLines = getAsmLines(args);

        Map<String, Integer> symbolTable = new HashMap<>();

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

        for (int i = 0; i < pc.get(); i++) {
            System.out.println(i + "-" + code.get(i));
        }

        System.out.println(symbolTable);

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
