package org.kata.gilded.rose;

public interface DegradeStrategy {

    public default int getDegradationAmount(int sellIn, int quality) {
        boolean isSellInDaysOver = sellIn < 0;
        int degradeBy = 1;

        if (isSellInDaysOver) {
            degradeBy *= 2;
        }
        return degradeBy;
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

class NonDegrade implements DegradeStrategy {

    @Override
    public int getDegradationAmount(int sellIn, int quality) {
        return 0;
    }
}

class DefaultDegrade implements DegradeStrategy {}

class CounterDegrade implements DegradeStrategy {

    @Override
    public int getDegradationAmount(int sellIn, int quality) {
        return -DegradeStrategy.super.getDegradationAmount(sellIn, quality);
    }
}

class Ticket implements DegradeStrategy {

    @Override
    public int getDegradationAmount(int sellIn, int quality) {
        boolean fiveMoreDaysToShow = sellIn < 5;
        boolean tenMoreDaysToShow = sellIn < 10;
        boolean showIsOver = sellIn < 0;

        int upgradeBy = 1;

        if (showIsOver)
            upgradeBy = -quality;
        else if (fiveMoreDaysToShow)
            upgradeBy = 3;
        else if (tenMoreDaysToShow) {
            upgradeBy = 2;
        }

        return -upgradeBy;
    }
}
