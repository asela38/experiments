package org.nand.to.tettris.vm.translator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("No file specified");
        System.out.println(Arrays.toString(args));
        final Pattern filePattern = Pattern.compile("(.*).vm");
        Matcher fileMatcher = filePattern.matcher(args[0]);
        String[] fileNames;

        if (fileMatcher.matches())
            fileNames = new String[] { fileMatcher.group(1) };
        else if (Files.isDirectory(Paths.get(args[0]))) {
            try (Stream<Path> walk = Files.walk(Paths.get(args[0]))) {
                fileNames = walk.filter(Files::isRegularFile)
                        .map(f -> f.getFileName().toFile().getName())
                        .filter(file -> filePattern.matcher(file).matches())
                        .toArray(String[]::new);
                System.out.printf("Files %s found in %s %n", Arrays.toString(fileNames), args[0]);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot read the folder");
            }

        } else {
            throw new IllegalArgumentException(
                    "File Name doesnt match the format: " + filePattern.pattern() + " or a directory");
        }

        try (CodeWriter writer = new CodeWriter(String.format("%s.asm", args[0]))) {

            if (fileNames.length > 1) {
                // // function Sys.init 0
                // writer.writeFunction("Sys.init", 0);
                // // push constant 4000 // test THIS and THAT context save
                // writer.writePush("constant", 4000);
                // // pop pointer 0
                // writer.writePop("pointer", 0);
                // // push constant 5000
                // writer.writePush("constant", 5000);
                // // pop pointer 1
                // writer.writePop("pointer", 1);
                // // call Sys.main 0
                // writer.writeCall("Sys.main", 0);
                // // pop temp 1
                // writer.writePop("temp", 1);
                // // label LOOP
                // writer.writeLabel("LOOP");
                // // goto LOOP
                // writer.writeGoto("LOOP");
                writer.writeInit();
            }

            for (String fileName : fileNames) {
                writer.setSubFileName(fileName);
                try (Parser parser = new Parser(
                        String.format("%s.vm", String.format("%s/%s", args[0], fileName.split("\\.")[0])))) {
                    writer.writeComment("File Name: " + fileName);
                    while (parser.hasMoreCommands()) {
                        parser.advance();
                        writer.writeComment(parser.current());
                        switch (parser.commandType()) {
                        case C_ARITHMETIC:
                            writer.writeArithmetic(parser.arg1());
                            break;
                        case C_CALL:
                            writer.writeCall(parser.arg1(), parser.arg2());
                            break;
                        case C_FUNCTION:
                            writer.writeFunction(parser.arg1(), parser.arg2());
                            break;
                        case C_GOTO:
                            writer.writeGoto(parser.arg1());
                            break;
                        case C_IF:
                            writer.writeIf(parser.arg1());
                            break;
                        case C_LABEL:
                            writer.writeLabel(parser.arg1());
                            break;
                        case C_POP:
                            writer.writePop(parser.arg1(), parser.arg2());
                            break;
                        case C_PUSH:
                            writer.writePush(parser.arg1(), parser.arg2());
                            break;
                        case C_RETURN:
                            writer.writeReturn();
                            break;
                        default:
                            break;

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
