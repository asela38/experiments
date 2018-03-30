package org.byzantine.fault.tolerance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * At testing understood it's wrong
 * Issues: not developed with TDD
 *         Went on with Functional approach make it complicated to reason about
 *
 */
@Deprecated 
public class Byzantine {

    private static ThreadLocalRandom current = ThreadLocalRandom.current();

    private enum Action {
        ATTACK, RETREAT;

        public static Action random() {
            Action[] values = Action.values();
            return values[ThreadLocalRandom.current().nextInt(0, values.length)];
        }

        public static Action pick(Map<Action, Integer> counts) {
            Integer retreatCount = counts.getOrDefault(Action.RETREAT, 0);
            Integer attackCount = counts.getOrDefault(Action.ATTACK, 0);
            if (attackCount > retreatCount)
                return Action.ATTACK;
            return Action.RETREAT;
        }
    }

    private enum Behavior {
        LOYAL, TRAITOR;
    }

    private static class General {
        final Integer id;
        Action        action;
        Behavior      behavior       = Behavior.LOYAL;
        List<Action>  actionMessages = new LinkedList<>();

        public General(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public Action getAction() {
            return action;
        }

        public void updateActionToMajority() {
    //        System.out.printf("%n id %d , %s", id, actionMessages);
            Map<Action, Integer> counts = actionMessages.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, g -> 1, Integer::sum)));
     //       System.out.printf("%n id %d Counts: %s", id, counts);
            action = Action.pick(counts);
        }
    }

    public static void main(String[] args) {

        int generalCount = 15;
        int simulationCount = 10;

        for (AtomicInteger t = new AtomicInteger(1); t.get() < 2; t.incrementAndGet()) {
            long consensusCount = IntStream.iterate(0, i -> i + 1).limit(simulationCount).boxed().map(i -> {

                Map<Integer, General> generalMap = IntStream.generate(current::nextInt).limit(generalCount).boxed()
                        .map(General::new).collect(Collectors.toMap(General::getId, Function.identity()));

                // Set Random Initial Decision to Generals
                generalMap.values().stream().forEach(g -> g.action = Action.random());

                // Distribute individual Action across others
                generalMap.values().stream()
                        .map(general -> general.behavior == Behavior.TRAITOR ? Action.random() : general.getAction())
                        .forEach(action -> generalMap.values().stream()
                                .forEach(general -> general.actionMessages.add(action)));

                generalMap.values().stream().limit(t.get()).forEach(g -> g.behavior = Behavior.TRAITOR);

                //What is the majority of the loyal's
                Action beforeConsensus = getLoyalConsensus(generalMap);
                
                // Update Action
                generalMap.values().stream().forEach(g -> g.updateActionToMajority());

                // Is all local Generals Have the same Action
                Action afterConsensus = getLoyalConsensus(generalMap);

                System.out.printf("%nBefore : %s, After : %s", beforeConsensus, afterConsensus);

                return beforeConsensus == afterConsensus;

            }).filter(a -> a == true).count();

            System.out.printf("%n traiter ratio %d/%d, consensus ratio %d/%d", t.get(), generalCount, consensusCount,
                   simulationCount - t.get() );
        }

    }

    private static Action getLoyalConsensus(Map<Integer, General> generalMap) {
        Map<Action, Integer> counts = generalMap.values().stream().filter(g -> g.behavior == Behavior.LOYAL)
                .collect(Collectors.groupingBy(General::getAction, Collectors.reducing(0, g -> 1, Integer::sum)));

        System.out.printf("%nCounts : %s", counts);
        return Action.pick(counts);

    }

}
