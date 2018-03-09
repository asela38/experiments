package org.transformation.priority.premise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CountryFromCodeKata {

    @Test
    public void countryCodeTest() throws Exception {
        assertThat(getCountryCode("Australia"), is("AU"));
    }

    private String getCountryCode(String country) {
        String code = country.substring(0,2);
        code = code.toUpperCase();
        return code;
    }

}
