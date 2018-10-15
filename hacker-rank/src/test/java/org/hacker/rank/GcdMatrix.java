package org.hacker.rank;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

import org.junit.Test;

public class GcdMatrix {

    private static final String FILE_PATH = "C:\\Users\\asela.illayapparachc\\git\\experiments\\hacker-rank\\src\\main\\resources\\%s.txt";
    private static final String INPUT     = String.format(FILE_PATH, "gcd-matrix-%s");
    private static final String INPUT_1   = String.format(INPUT, "1");

    @Test
    public void testFile() throws Exception {
        try (Scanner scan = new Scanner(new File(INPUT_1))) {
            while (scan.hasNextLine())
                System.out.println(scan.nextLine());
        }
    }

    @Test
    public void testAttacks() throws Exception {
        process(new FileInputStream(INPUT_1));
        process(new FileInputStream("C:\\Asela\\mydocs\\Coursera\\gcd-06.txt"));

    }

    public void process(InputStream in) {
        Scanner scanner = new Scanner(in);

        String[] nmq = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nmq[0]);

        int m = Integer.parseInt(nmq[1]);

        int q = Integer.parseInt(nmq[2]);

        nmq = null;

        int[] a = new int[n];

        String[] aItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int aItem = Integer.parseInt(aItems[i]);
            a[i] = aItem;
        }
        aItems = null;

        int[] b = new int[m];

        String[] bItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < m; i++) {
            int bItem = Integer.parseInt(bItems[i]);
            b[i] = bItem;
        }

        bItems = null;

        for (int qItr = 0; qItr < q; qItr++) {
            String[] r1C1R2C2 = scanner.nextLine().split(" ");

            int r1 = Integer.parseInt(r1C1R2C2[0]);

            int c1 = Integer.parseInt(r1C1R2C2[1]);

            int r2 = Integer.parseInt(r1C1R2C2[2]);

            int c2 = Integer.parseInt(r1C1R2C2[3]);

            HashSet<Integer> set = new HashSet<>();
            for (int i = r1; i <= r2; i++)
                for (int j = c1; j <= c2; j++)
                    set.add(gcd(a[i], b[j]));
            System.out.println(set.size());
        }

        scanner.close();
    }

//    Map<String, Integer> cache = new HashMap<>();

    private int gcd(int i, int j) {
        String key = key(i, j);
     //   if (cache.containsKey(key)) {
    //        return cache.get(key);
    //    }
        int gcd = gcd_0(i, j);
    //    cache.put(key, gcd);
        return gcd;
    }

    public int gcd_0(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private String key(int i, int j) {
        return Integer.max(i, j) + "-" + Integer.min(i, j);
    }

}
