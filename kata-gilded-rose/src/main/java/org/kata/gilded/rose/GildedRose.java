package org.kata.gilded.rose;

class GildedRose {
    private static final int    MIN_QUALITY      = 0;
    private static final int    MAX_QUALITY      = 50;
    private static final String SULFURAS         = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String AGED_BRIE        = "Aged Brie";
    Item[]                      items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {

            String name = item.name;

            boolean isAgedBrie = name.equals(AGED_BRIE);
            boolean isBackStagePasses = name.equals(BACKSTAGE_PASSES);
            boolean isSulfuras = name.equals(SULFURAS);

            if (isSulfuras)
                continue;

            --item.sellIn;
            boolean isSellInDaysOver = item.sellIn < 0;

            int decrementBy = 1;

            if(isSellInDaysOver) {
                decrementBy *= 2;
            }
            
            if (isAgedBrie) {
                decrementBy = -decrementBy;
            }

            if (isBackStagePasses) {
                if (isSellInDaysOver) {
                    decrementBy = -item.quality;
                } else if (item.sellIn < 5) {
                    decrementBy = 3;
                } else if (item.sellIn < 10) {
                    decrementBy = 2;
                } else {
                    decrementBy = 1;
                }

                decrementBy = -decrementBy;
            }

            decreaseQuantity(item, decrementBy);

        }
    }

    private void decreaseQuantity(Item item, int amount) {
        item.quality = Math.max(Math.min(item.quality - amount, MAX_QUALITY), MIN_QUALITY);
    }
}