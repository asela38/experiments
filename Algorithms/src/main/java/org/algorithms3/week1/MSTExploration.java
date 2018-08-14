package org.algorithms3.week1;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

public class MSTExploration {

    private static final String TEST_FILE_LOCATION = "C:\\Users\\asela.illayapparachc\\git\\stanford-algs\\testCases\\course3\\assignment1SchedulingAndMST\\question3\\input_random_";
    private static final String _1_10_FILE         = TEST_FILE_LOCATION + "1_10.txt";

    @Test
    public void testFile() throws Exception {
        try(BufferedReader reader = new BufferedReader(new FileReader(_1_10_FILE))) {
            reader.lines().forEach(System.out::println);
        }
    }
}
