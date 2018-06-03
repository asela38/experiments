package org.kata.gilded.rose.pre;

import org.kata.gilded.rose.Item;

class GildedRose {
    private static final int    MIN_QUALITY      = 0;
    private static final int    MAX_QUALITY      = 50;

    Item[]                      items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {

            DegradeStrategy strategy = DegradeStrategy.to(item);

            if (strategy instanceof NonDegrade)
                continue;

            item.sellIn--;

            item.quality = strategy.getDegrade(item.sellIn).apply(item.quality);
            item.quality = Math.max(Math.min(item.quality, MAX_QUALITY), MIN_QUALITY);
        }
    }

}