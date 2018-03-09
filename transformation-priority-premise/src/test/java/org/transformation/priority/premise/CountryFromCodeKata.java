package org.transformation.priority.premise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CountryFromCodeKata {

    private void verifyCountryAndCode(String country, String code) {
        assertThat(getCountryCode(country), is(code));
    }
    
    @Test
    public void countryCodeTest() throws Exception {
        verifyCountryAndCode("Australia", "AU");
    }

    private String getCountryCode(String country) {
        String code = country.substring(0,2);
        code = code.toUpperCase();
        return code;
    }

}
