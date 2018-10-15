package org.automata;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Hello world! */
public class FiniteStateMachineForWordSearch {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(new File("sample.txt"))) {

            String searchText = "litigation.";

            FSM<Integer> fsm = new FSM<>(
                    IntStream.iterate(0, i -> i++).limit(searchText.length()).boxed().collect(Collectors.toList()), 0);

            for (int i = 0; i < searchText.length(); i++)
                fsm.addTransition(i, searchText.charAt(i), i + 1);

            fsm.accept(searchText.length());

            int count = 0;
            while (!fsm.isAccepted() && scanner.hasNext()) {
                scanner.next().chars().forEach(i -> fsm.take((char) i));
                count++;
                if (fsm.current != 0)
                    System.out.println(fsm.current);
            }

            System.out.printf("%n at %d", count);

        }
    }

    @SuppressWarnings("unused")
    private enum State {
        MATCHED, UNMATCHED, MATCHING;
    }

    private static class FSM<T> {
        @SuppressWarnings("unused")
        private List<T>                   states;
        @SuppressWarnings("unused")
        private List<Character>           alphabet;
        private Map<T, Map<Character, T>> transitionFunction = new HashMap<>();
        private final T                   initial;
        private List<T>                   accept             = new LinkedList<>();
        private T                         current;

        public FSM(List<T> states, T initial) {
            this.states = states;
            this.initial = initial;
            reset();
        }

        public void addTransition(T state, Character input, T toState) {
            transitionFunction.putIfAbsent(state, new HashMap<>());
            transitionFunction.get(state).put(input, toState);
        }

        public void reset() {
            current = initial;
        }

        public boolean isAccepted() {
            return accept.contains(current);
        }

        public void accept(T accept) {
            this.accept.add(accept);
        }

        public void take(Character input) {
            Map<Character, T> map = transitionFunction.get(current);
            if (map == null) {
                reset();
                return;
            }

            T state = map.get(input);

            if (state == null) {
                reset();
                return;
            }
            current = state;
        }

    }

}
