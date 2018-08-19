package org.hacker.rank;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

public class QueenAttack {

    private static final String FILE_PATH = "C:\\Users\\asela.illayapparachc\\git\\experiments\\hacker-rank\\src\\main\\resources\\%s.txt";
    private static final String INPUT_1   = String.format(FILE_PATH, "input-queen-attack-1");
    private static final String INPUT_2   = String.format(FILE_PATH, "input-queen-attack-2");
    private static final String INPUT_3   = String.format(FILE_PATH, "input-queen-attack-3");
    private static final String INPUT_4   = String.format(FILE_PATH, "input-queen-attack-4");

    @Test
    public void testFile() throws Exception {
        try (Scanner scan = new Scanner(new File(INPUT_1))) {
            while (scan.hasNextLine())
                System.out.println(scan.nextLine());
        }
    }

    @Test
    public void testAttacks() throws Exception {
        assertThat(process(new FileInputStream(INPUT_1)), is(10L));
        assertThat(process(new FileInputStream(INPUT_2)), is(0L));
        assertThat(process(new FileInputStream(INPUT_3)), is(9L));
        assertThat(process(new FileInputStream(INPUT_4)), is(27L));
    }

    public long process(InputStream in) {
        try (Scanner scanner = new Scanner(in)) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            int[] q = new int[] { scanner.nextInt() - 1, scanner.nextInt() - 1 };
            HashMap<String, Boolean> blocks = new HashMap<>();
            for (int i = 0; i < k; i++)
                blocks.put((scanner.nextInt() - 1) + "-" + (scanner.nextInt() - 1), Boolean.TRUE);

            int p = 0, start = 1;
            // steps to North
            for (int i = start; q[0] + i < n; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0] + i, q[1])))
                    break;

            // steps to South
            for (int i = start; q[0] - i > -1; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0] - i, q[1])))
                    break;

            // steps to East
            for (int i = start; q[1] + i < n; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0], q[1] + i)))
                    break;

            // steps to West
            for (int i = start; q[1] - i > -1; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0], q[1] - i)))
                    break;

            // steps to North-East
            for (int i = start; q[0] + i < n && q[1] + i < n; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0] + i, q[1] + i)))
                    break;

            // steps to South-West
            for (int i = start; q[0] - i > -1 && q[1] - i > -1; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0] - i, q[1] - i)))
                    break;

            // steps to South-East
            for (int i = start; q[1] + i < n && q[0] - i > -1; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0] - i, q[1] + i)))
                    break;

            // steps to North-West
            for (int i = start; q[1] - i > -1 && q[0] + i < n; i++, p++)
                if (blocks.containsKey(String.format("%s-%s", q[0] + i, q[1] - i)))
                    break;
            return p;
        }
    }

}
