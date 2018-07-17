package org.algorithms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class GradeSchoolMultiplication {

    @Test
    public void test_multiplesOf11() {
        verify("1", "1", "1");
        verify("11", "22", "242");
        verify("11", "222", "2442");
        verify("111", "22", "2442");
        verify("111", "22", "2442");
        verify("111", "222", "24642");
        verify("1111", "2222", "2468642");
        verify("11111", "22222", "246908642");
    }

    @Test
    public void test_trivial() {
        verify("1", "2", "2");
        verify("9", "9", "81");
    }
   //8539734222673567065463550869546574495034888535765114961879601127067743044893204848617875072216249073013374895871952806582723184 
    @Test
    public void test_trivial_bigInteger() {
        verify("99999999999999999999", "99999999999999999999", new BigInteger("99999999999999999999").pow(2).toString());
        verify("9999999999999999999999999999999999999999", "9999999999999999999999999999999999999999", new BigInteger("9999999999999999999999999999999999999999").pow(2).toString());
        System.out.println(multiply("3141592653589793238462643383279502884197169399375105820974944592", "2718281828459045235360287471352662497757247093699959574966967627"));
    }


    @Test
    public void test_randomlyOnValuesLessThan10000() {

        Stream.generate(() -> ThreadLocalRandom.current().ints(1, 10000 - 1).limit(2).mapToObj(String::valueOf)
                .collect(Collectors.toList()).toArray(new String[0])).limit(1000)
                .forEach(arr -> verify(arr[0], arr[1],
                        String.valueOf(Integer.parseInt(arr[0]) * Integer.parseInt(arr[1]))));

    }

    private void verify(String number1, String number2, String answer) {
        assertThat(multiply(number1, number2), is(answer));
    }

    private String multiply(String number1, String number2) {
        
        System.out.println(number1 + "/" + number2);
        char[] n1 = reverse(number1.toCharArray());
        char[] n2 = reverse(number2.toCharArray());
        int[][] an = new int[n1.length][n2.length + 1];
        for (int i1 = 0; i1 < n1.length; i1++) {
            int carry = 0;
            for (int i2 = 0; i2 < n2.length; i2++) {
                an[i1][i2] = asNumber(n1[i1]) * asNumber(n2[i2]) + carry;
                carry = an[i1][i2] / 10;
                an[i1][i2] %= 10;
            }
            an[i1][n2.length] = carry;
        }

        Arrays.stream(an).map(Arrays::toString).forEach(System.out::println);

        int carry = 0;
        String answer = "";
        for (int i = 0; i < n1.length + n2.length + 1; i++) {
            int ans = 0;
            for (int j = 0; j < an.length; j++) {
                if (i - j >= 0 && i - j < n2.length + 1)
                    ans += an[j][i - j];
            }
            ans += carry;
            carry = ans / 10;
            ans %= 10;
            answer = ans + answer;
        }

        System.out.println(answer);

        return answer.replaceAll("^0*", "");
    }
    
    
    
    //karatsuba Multiplication
    private String kMultiply(String number1, String number2) {
        
        int maxLength = Math.max(number1.length(), number2.length());
        if(maxLength == 1) {
            return String.valueOf(Integer.parseInt(number1) * Integer.parseInt(number2));
        }
        maxLength += maxLength%2;
        
        String n1 = String.format("%0"+maxLength+"s", number1).replace(" ", "0");
        String n2 = String.format("%0"+maxLength+"s", number2).replace(" ", "0");
        
        int partSize = maxLength>>1;
        
        String a = n1.substring(0, partSize);
        String b = n1.substring(partSize);
        String c = n2.substring(0, partSize);
        String d = n2.substring(partSize);
        
        String s1 = kMultiply(a, c);
        String s2 = kMultiply(b, d);
        String s3 = kMultiply(new BigInteger(a).add(new BigInteger(b)).toString(), new BigInteger(c).add(new BigInteger(d)).toString());
        String s4 = new BigInteger(s3).subtract(new BigInteger(s2)).subtract(new BigInteger(s1)).toString();
        
        BigInteger p1 = new BigInteger("10").pow(partSize*partSize).multiply(new BigInteger(s1));
        BigInteger p2 = new BigInteger("10").pow(partSize).multiply(new BigInteger(s4));
        
        
        return new BigInteger(s2).add(p1).add(p2).toString();
    }

    private char[] reverse(char[] array) {
        char[] newArray = new char[array.length];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[array.length - i - 1];
        return newArray;
    }

    private int asNumber(char c) {
        return c - '0';
    }
}
