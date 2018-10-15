package org.Algorithms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Roman {

    @Test
    public void testForOne() throws Exception {
        assertThat(roman(1), is("I"));
        assertThat(roman(2), is("II"));
        assertThat(roman(3), is("III"));
        assertThat(roman(4), is("IV"));
        assertThat(roman(5), is("V"));
        assertThat(roman(6), is("VI"));
        assertThat(roman(7), is("VII"));
        assertThat(roman(8), is("VIII"));
        assertThat(roman(9), is("IX"));
        assertThat(roman(10), is("X"));

    }

    private String roman(int i) {
        String result = String.format("%" + i + "s", " ").replaceAll(" ", "I");
        result = result.replaceAll("(\\s*)IIII$", "\1IV").replaceAll(" ", "");
        result = result.replaceAll("IVI", "V").replaceAll(" ", "");
        return result.replaceAll(" ", "");
    }

}
