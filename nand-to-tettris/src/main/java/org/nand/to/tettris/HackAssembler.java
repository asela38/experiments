package org.nand.to.tettris;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/** Hello world! */
public class HackAssembler {
    public static void main(String[] args) {

        if (args == null || args.length < 1)
            throw new IllegalArgumentException("File Name should be specified");

        List<String> readAllLines = getAsmLines(args);

        // clean empty lines and comments
        readAllLines.stream()
                .map(line -> line.replaceAll("\\s*", "")) // remove all spaces
                .filter(line -> !line.isEmpty())
                .filter(line -> !line.startsWith("//"))
                .map(line -> line.replace("//.*", ""))
                .forEach(System.out::println);

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
