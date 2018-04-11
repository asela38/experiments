package org.google.code.jam.y2017;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class AlphabetCake {

    static {
        try {
            System.setIn(new FileInputStream(new File("alphabet-cake-input-small.txt")));
            System.setOut(new PrintStream(new File("alphabet-cake-output-small.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int t = scanner.nextInt();
            for (int k = 1; k <= t; k++) {
                int r = scanner.nextInt();
                int c = scanner.nextInt();
                String[] grid = new String[r];
                String prev = null;
                for (int i = 0; i < r; i++) {
                    grid[i] = scanner.next();
                    if (grid[i].replaceAll("\\?", "").length() == 0) {
                        if (prev != null) {
                            grid[i] = prev;
                        }
                    } else {
                        char[] charArray = grid[i].toCharArray();
                        int a = -1, aa = -1;

                        boolean found = false;
                        char p = (char) 0;
                        while (++a < charArray.length) {
                            if (charArray[a] != '?') {

                                p = charArray[a];
                                if (!found) {
                                    found = true;
                                    aa = a;
                                }
                            } else {
                                if (p != (char) 0) {
                                    charArray[a] = p;
                                }
                            }
                        }

                        if (found && charArray[0] == '?') {
                            for (int b = 0; b < aa; b++) {
                                charArray[b] = charArray[aa];
                            }
                        }
                        grid[i] = String.valueOf(charArray);
                        prev = grid[i];
                    }

                }
                prev = null;
                for (int i = r - 1; i >= 0; i--) {
                    if (grid[i].replaceAll("\\?", "").length() == 0) {
                        if (prev != null) {
                            grid[i] = prev;
                        }
                    } else {
                        prev = grid[i];
                    }

                }

                System.out.printf("Case #%d:%n", k);
                for (int i = 0; i < r; i++) {
                    System.out.println(String.valueOf(grid[i]));
                }
            }

        }
    }
}
