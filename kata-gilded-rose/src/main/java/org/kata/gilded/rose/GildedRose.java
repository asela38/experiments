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
            boolean isQualityDegradingItem = !(isAgedBrie || isBackStagePasses);
            boolean isTicket = isBackStagePasses;
            
            int decrementBy = 1;

            if (isSellInDaysOver) {
                decrementBy *= 2;
            }

            if (isTicket) {

                boolean lessThan5DaysToShow = item.sellIn < 5;
                boolean lessThan10DaysToShow = item.sellIn < 10;
                boolean showIsOver = isSellInDaysOver;

                if (showIsOver)
                    decrementBy = -item.quality;
                else if (lessThan5DaysToShow)
                    decrementBy = 3;
                else if (lessThan10DaysToShow) {
                    decrementBy = 2;
                } else {
                    decrementBy = 1;
                }

            }
            
            if (!isQualityDegradingItem) {
                decrementBy = -decrementBy;
            }

            decreaseQuantity(item, decrementBy);

        }
    }

    private void decreaseQuantity(Item item, int amount) {
        item.quality = Math.max(Math.min(item.quality - amount, MAX_QUALITY), MIN_QUALITY);
    }
}