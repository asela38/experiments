***Step 1:***

To implement it first I implemented the signature for method and make testing scenarios easy to produce

Design Decisions
1. Use Strings to represent numbers so number of any size can be test easily 

``` java

    public class GradeSchoolMultiplication {

		@Test
		public void test_trivial() {
			verify("1", "1", "1");
		}

		private void verify(String number1, String number2, String answer) {
			assertThat(multiply(number1, number2), is(answer));
		}

		private String multiply(String number1, String number2) {
			return "1";
		}
		
    }


``` 

***Step 2:***

Then implemented basics covert two numbers to char arrays and reverse them. Because multiplication (as well as addition) is happening from right to left. [If asked why?, with basic deduction it can be conclude that it's because significance of the digits increase from right to left. However if we ask another why? it could have been from left to right, if that's the way people used there's no significance difference for it's utility. Here if we take into note that Arabic also write form right to left. That may be the reason behind numbers have this representation of building up from right to left. :D so in Arabic you don't have to left justify string columns in a table and right justify numbers, they just have to do right justify all ]

``` java

    private String multiply(String number1, String number2) {
        
        System.out.println(number1 + "/" + number2);
        char[] n1 = reverse(number1.toCharArray());
        char[] n2 = reverse(number2.toCharArray());
     
        return "1";
    }

    private char[] reverse(char[] array) {
        char[] newArray = new char[array.length];
        for (int i = 0; i < array.length; i++)
            newArray[i] = array[array.length - i - 1];
        return newArray;
    }

``` 

***Step 3:***

Then I take digits form form first number and multiply second number with it an put it into an array[ this itself is a O(n*n) operation]

``` java

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

```

created small utility to take int value of char

``` java

    private int asNumber(char c) {
        return c - '0';
    }

```

***Step 4:****

Then add those together by shifty each number based on order of the digit

``` java

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


```


***Step 5:***

Then return answer by remove all leading zeros with a regex

``` java

    return answer.replaceAll("^0*", "");


``` 

***Step 6:***

Which not actually last step I create few test incrementally. For the ease of verification first I used test case with sequence of 1's

``` java


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


```

***Step 7:***

Then I used random two numbers between 1-10000 and carry out random tests

``` java

    @Test
    public void test_randomlyOnValuesLessThan10000() {

        Stream.generate(() -> ThreadLocalRandom.current().ints(1, 10000 - 1).limit(2).mapToObj(String::valueOf)
                .collect(Collectors.toList()).toArray(new String[0])).limit(1000)
                .forEach(arr -> verify(arr[0], arr[1],
                        String.valueOf(Integer.parseInt(arr[0]) * Integer.parseInt(arr[1]))));

    }

```

Finally did some testing with big numbers with big integers

``` java

    @Test
    public void test_trivial_bigInteger() {
        verify("99999999999999999999", "99999999999999999999", new BigInteger("99999999999999999999").pow(2).toString());
        verify("9999999999999999999999999999999999999999", "9999999999999999999999999999999999999999", new BigInteger("9999999999999999999999999999999999999999").pow(2).toString());
    }


``` 
