package org.kata.gilded.rose;

class GildedRose {
    private static final int    MIN_QUALITY      = 0;
    private static final int    MAX_QUALITY      = 50;
    static final String SULFURAS         = "Sulfuras, Hand of Ragnaros";
    static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    static final String AGED_BRIE        = "Aged Brie";
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