package org.binary.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Developed using TDD + Tranformation Priority Premise
 * 
 * @author asela.illayapparachchi
 *
 */
public class TreePrint {

    public List<String> tree(int depth, String string) {
        List<String> list = new LinkedList<>();
        Consumer<String> add = v -> list.add(v);

        Function<Integer, Integer> sumOfPowerOf =  l -> IntStream.range(0, l ).map(v -> (int) Math.pow(2, v)).sum();
        Function<Integer, String> affixStringFn = l -> spacesOfLength( sumOfPowerOf.apply(l -1) * string.length());
        
        if (depth > 0) {
            
            String affixString = affixStringFn.apply(depth);
            add.accept(affixString + string + affixString);

            List<String> treeOneDepthLower = tree(depth - 1, string);
            for (String entry : treeOneDepthLower) {
                add.accept(entry + spacesOfLength(string.length()) + entry);
            }

        }
        return list;
    }

    private  String spacesOfLength(int i) {
        return IntStream.range(0, i).mapToObj(j -> " ").collect(Collectors.joining());
    }
}
