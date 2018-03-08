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

    enum DegradeStrategy {
        NON_DEGRADE() {
            @Override
            int getDegradationAmount() {
                return 0;
            }
        },
        DEFAULT_DEGRADE() {
            @Override
            int getDegradationAmount() {
                boolean isSellInDaysOver = sellIn < 0;
                int degradeBy = 1;

                if (isSellInDaysOver) {
                    degradeBy *= 2;
                }
                return degradeBy;
            }
        },
        COUNTER_DEGRADE() {
            @Override
            int getDegradationAmount() {
                boolean isSellInDaysOver = sellIn < 0;
                int upgradeBy = 1;

                if (isSellInDaysOver) {
                    upgradeBy *= 2;
                }
                return -upgradeBy;
            }
        },
        TICKET() {
            @Override
            int getDegradationAmount() {
                boolean fiveMoreDaysToShow = sellIn < 5;
                boolean tenMoreDaysToShow = sellIn < 10;
                boolean showIsOver = sellIn < 0;

                int upgradeBy = 1;

                if (showIsOver)
                    upgradeBy = Integer.MIN_VALUE;
                else if (fiveMoreDaysToShow)
                    upgradeBy = 3;
                else if (tenMoreDaysToShow) {
                    upgradeBy = 2;
                }

                return -upgradeBy;

            }
        };

        protected int sellIn;

        abstract int getDegradationAmount();

        static DegradeStrategy to(Item item) {
            DegradeStrategy strategy = DegradeStrategy.DEFAULT_DEGRADE;
            switch (item.name) {
            case SULFURAS:
                strategy = DegradeStrategy.NON_DEGRADE;
                break;
            case BACKSTAGE_PASSES:
                strategy = DegradeStrategy.TICKET;
                break;
            case AGED_BRIE:
                strategy = DegradeStrategy.COUNTER_DEGRADE;
                break;
            default:
                strategy = DegradeStrategy.DEFAULT_DEGRADE;
            }

       
            return strategy;
        }
        
        public void setSellIn(int sellIn) {
            this.sellIn = sellIn;
        }

    }

    public void updateQuality() {
        for (Item item : items) {

            DegradeStrategy strategy = DegradeStrategy.to(item);
            
            if (strategy == DegradeStrategy.NON_DEGRADE)
                continue;
            
            strategy.setSellIn(--item.sellIn);
            
            decreaseQuantity(item, strategy.getDegradationAmount());

        }
    }

    private void decreaseQuantity(Item item, int amount) {
        item.quality = Math.max(Math.min(item.quality - amount, MAX_QUALITY), MIN_QUALITY);
    }
}