package org.transformation.priority.premise;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CountryFromCodeKata {

    @Test
    public void countryCodeTest() throws Exception {
        assertNotNull(getCountryCode(null));
    }

    private String getCountryCode(String code) {
        return "";
    }

}
