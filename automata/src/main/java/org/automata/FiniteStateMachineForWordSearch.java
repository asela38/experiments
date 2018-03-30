package org.automata;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class FiniteStateMachineForWordSearch {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(new File("sample.txt"))) {

            String searchText = "valid";

            FSM<State> fsm = new FSM<>(Arrays.asList(State.values()), State.UNMATCHED);

            fsm.addTransition(State.UNMATCHED, 'v', State.MATCHING);
            fsm.addTransition(State.MATCHING, 'a', State.MATCHING);
            fsm.addTransition(State.MATCHING, 'l', State.MATCHING);
            fsm.addTransition(State.MATCHING, 'i', State.MATCHING);
            fsm.addTransition(State.MATCHING, 'd', State.MATCHED);

            fsm.accept(State.MATCHED);

            int count = 1;
            while (!fsm.isAccepted() && scanner.hasNext()) {
                scanner.next().chars().forEach(i -> fsm.take((char) i));
                count++;
                System.out.println(fsm.current);
            }

            System.out.printf("%n at %d", count);

        }
    }

    private enum State {
        MATCHED, UNMATCHED, MATCHING;
    }

    private static class FSM<T> {
        private List<T>                   states;
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
