package org.kata.gilded.rose;

import java.util.function.Function;

public interface DegradeStrategy {

    public default Function<Integer, Integer> getDegrade(int sellIn) {
        return Function.identity();
    }

    public static DegradeStrategy to(Item item) {
        DegradeStrategy strategy = null;
        switch (item.name) {
        case GildedRose.SULFURAS:
            strategy = new NonDegrade();
            break;
        case GildedRose.BACKSTAGE_PASSES:
            strategy = new Ticket();
            break;
        case GildedRose.AGED_BRIE:
            strategy = new CounterDegrade();
            break;
        default:
            strategy = new DefaultDegrade();
        }

        return strategy;
    }
}

class NonDegrade implements DegradeStrategy {}


class DefaultDegrade implements DegradeStrategy {
    @Override
    public Function<Integer, Integer> getDegrade(int sellIn) {
        return quantity -> quantity - (sellIn < 0 ? 2 : 1);
    }
}

class CounterDegrade implements DegradeStrategy {

    @Override
    public Function<Integer, Integer> getDegrade(int sellIn) {
        return quantity -> quantity + (sellIn < 0 ? 2 : 1);
    }
}

class Ticket implements DegradeStrategy {

    @Override
    public Function<Integer, Integer> getDegrade(int sellIn) {
        boolean fiveMoreDaysToShow = sellIn < 5;
        boolean tenMoreDaysToShow = sellIn < 10;
        boolean showIsOver = sellIn < 0;

        if (showIsOver)
            return i -> 0;

        return i -> {
            int upgrade = 1;
            if (fiveMoreDaysToShow)
                upgrade = 3;
            else if (tenMoreDaysToShow) {
                upgrade = 2;
            }
            return i + upgrade;
        };
    }
}
