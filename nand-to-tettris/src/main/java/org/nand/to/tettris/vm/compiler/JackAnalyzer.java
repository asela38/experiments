package org.nand.to.tettris.vm.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JackAnalyzer {

    public enum Patterns {

        JACK_FILE(".*\\/?([\\w0-9]+).jack"), DIR_NAME(".*\\/?([\\w0-9]+)");

        private Pattern pattern;

        private Patterns(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        public Matcher getMatcher(String string) {
            return pattern.matcher(string);
        }

    }

    private static boolean isFile  = true;
    private static boolean isExist = true;

    public static void main(String... args) {
        if (args.length < 1)
            throw new IllegalArgumentException("File Name or directory name should be provided.");

        File inFile = new File(args[0]);
        isExist = inFile.exists();

        if (isExist) {

            Matcher jackFileMatcher = Patterns.JACK_FILE.getMatcher(args[0]);
            isFile = jackFileMatcher.matches();

            if (isFile) {

                File outFile = getOutFile(inFile);
                File vmFile = getVmFile(inFile);
                try (CompilationEngine compilationEngine = new CompilationEngine(inFile, outFile, vmFile)) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Files.walk(inFile.toPath())
                            .filter(f -> Patterns.JACK_FILE.getMatcher(f.getFileName().toString()).matches())
                            .forEach(f -> {

                                File outFile = getOutFile(f.toFile());
                                File vmFile = getVmFile(f.toFile());
                                try (
                                        CompilationEngine compilationEngine = new CompilationEngine(f.toFile(),
                                                outFile, vmFile)) {

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private static File getOutFile(File inFile) {
        return new File(inFile.getParentFile(),
                inFile.getName().replaceAll(".*\\./", "").replaceAll("\\.jack", "") + ".xml");
    }

    private static File getVmFile(File inFile) {
        return new File(inFile.getParentFile(),
                inFile.getName().replaceAll(".*\\./", "").replaceAll("\\.jack", "") + ".vm");
    }

    public static boolean isFile() {
        return isFile;
    }

    public static boolean isExist() {
        return isExist;
    }

}
