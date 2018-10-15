package org.algorithms2.week3;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class HashExploration {

    @Test
    public void testIntegerAsString() throws Exception {
        int limit = 1000;
        Map<Integer, Long> collect = IntStream.iterate(1, i -> i + 1).limit(limit).peek(System.out::println)
                .mapToObj(Integer::toString).peek(System.out::println).map(String::hashCode).map(Integer::intValue)
                .map(i -> i % limit).peek(System.out::println)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(collect);
        Map<Long, Long> collect2 = collect.values().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(collect2);
        System.out.println(collect2.values().stream().mapToLong(Long::longValue).sum());
    }

    @Test
    public void testHashMapBasic() throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();

        @SuppressWarnings("rawtypes")
        Class<? extends HashMap> class1 = hashMap.getClass();
        Field field = class1.getDeclaredField("threshold");

        Field[] declaredFields = class1.getDeclaredFields();
        for (Field field2 : declaredFields) {
            System.out.println(field2.toString());
        }

        field.setAccessible(true);
        Object object = field.get(hashMap);
        System.out.println(object);
    }

    private void analyzeBuckets(HashMap<?, ?> hashMap) throws NoSuchFieldException, IllegalAccessException {
        Class<?> class1 = hashMap.getClass();
        
        Field field = class1.getDeclaredField("table");
        field.setAccessible(true);
        Object[] objects = (Object[]) field.get(hashMap);

        System.out.printf("Total Buckets : %s Used : %s ", Arrays.stream(objects).count(), Arrays.stream(objects).filter(Objects::nonNull).count());
    }
    
    @Test
    public void testHashMap_Integer() throws Exception {
        HashMap<Integer, String> hashMap = new HashMap<>();
        IntStream.iterate(1, i -> i + 1).limit(10000).mapToObj(Integer::toString)
                .forEach(i -> hashMap.put(Integer.valueOf(i), i));

        analyzeBuckets(hashMap); 
    }
    
    @Test
    public void testHashMap_Integer_random() throws Exception {
        HashMap<Integer, String> hashMap = new HashMap<>();
        ThreadLocalRandom.current().ints().limit(10000).mapToObj(Integer::toString)
                .forEach(i -> hashMap.put(Integer.valueOf(i), i));

        analyzeBuckets(hashMap); 
    }
    // Total Buckets : 16384 Used : 7538 
    // Total Buckets : 16384 Used : 7428 
    
    @Test
    public void testHashMap_Integer_random_range() throws Exception {
        HashMap<Integer, String> hashMap = new HashMap<>();
        ThreadLocalRandom.current().ints(0,200000).limit(10000).mapToObj(Integer::toString)
                .forEach(i -> hashMap.put(Integer.valueOf(i), i));

        analyzeBuckets(hashMap); 
    }
    
    @Test
    public void testHashMap_String_incrementing() throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        IntStream intStream = IntStream.iterate(1, i -> i + 1);
        intStream.limit(10000).mapToObj(Integer::toString).forEach(i -> hashMap.put(i, i));
        analyzeBuckets(hashMap); 
    }
    // Total Buckets : 16384 Used : 6765 
    
    @Test
    public void testHashMap_String_random() throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        IntStream intStream =ThreadLocalRandom.current().ints();
        intStream.limit(10000).mapToObj(Integer::toString).forEach(i -> hashMap.put(i, i));
        analyzeBuckets(hashMap); 
    }
    // Total Buckets : 16384 Used : 7486 
    // Total Buckets : 16384 Used : 7531 



}
