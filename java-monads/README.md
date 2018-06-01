## Exploring Monads In Java

Few Unit test on exploring unit tests on Optionals

``` java
	
	 // Encoding to base64 encrypted password
    public String encodePassword3(String password) throws Exception {
        return Optional.ofNullable(password)
                        .map(String::getBytes)
                        .map(MessageDigest.getInstance("SHA1")::digest)
                        .map(Base64.getEncoder()::encodeToString)
                        .orElseThrow(IllegalArgumentException::new);
    }
```

and Streams

``` java
	
    // Finding PI with Random Numbers
    @Test
    public void testStreamTwo() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        long sampleSize = 1_000_000_000L;
        long count = findInsideCircleCount(sampleSize);
        stopWatch.stop();
        System.out.printf("Original: %s Computed: %s , Time: %s %n",
                Math.PI,
                4D * count / sampleSize,
                stopWatch);
        System.out.println(Math.PI);
       
    }

    private long findInsideCircleCount(long sampleSize) {
        return  ThreadLocalRandom.current().doubles(0,1)
            .limit(sampleSize)
            .parallel()
            .map(d -> Math.pow(d, 2D))
            .map(d -> d + Math.pow(ThreadLocalRandom.current().nextDouble(0D,1D), 2D))
            .map(Math::sqrt)
            .filter(d -> d < 1D)
            .count();
    }
```


## Reference

[Functor And Monad Examples in Plain Java](https://dzone.com/articles/functor-and-monad-examples-in-plain-java)

[Monads Demystified](http://blog.reverberate.org/2015/08/monads-demystified.html)