package org.transformation.priority.premise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

public class CountryFromCodeKata {

    private void verifyCountryAndCode(String country, String code) {
        assertThat(getCountryCode(country), is(code));
    }
    
    @Test
    public void countryCodeTest() throws Exception {
        verifyCountryAndCode("Australia", "AU");
        verifyCountryAndCode("Canada", "CA");
        verifyCountryAndCode("Sri Lanka", "SL");
        verifyCountryAndCode("Viet Nam", "VN");
        
    }

    private String getCountryCode(String country) {
        String code = "";
        if(country.matches(".* .*")) {
            code = Arrays.stream(country.split(" ")).map(s -> s.substring(0, 1)).collect(Collectors.joining());
        } else {            
            code = country.substring(0,2);
        }
        code = code.toUpperCase();
        return code;
    }

}
