package org.kata.gilded.rose;

class GildedRose {
	Item[] items;

	public GildedRose(Item[] items) {
		this.items = items;
	}

	public void updateQuality() {
		for (int i = 0; i < items.length; i++) {
			boolean isSulfuras = items[i].name.equals("Sulfuras, Hand of Ragnaros");
			boolean isBackStagePasses = items[i].name.equals("Backstage passes to a TAFKAL80ETC concert");
			boolean isAgedBrie = items[i].name.equals("Aged Brie");
			if (isAgedBrie) {
				if (items[i].quality < 50) {
					items[i].quality++;
				}
			}
			if (isBackStagePasses) {
				if (items[i].quality < 50) {
					items[i].quality++;
				}

				if (items[i].sellIn < 11) {
					if (items[i].quality < 50) {
						items[i].quality++;
					}
				}

				if (items[i].sellIn < 6) {
					if (items[i].quality < 50) {
						items[i].quality++;
					}
				}
			}

			if (!isAgedBrie && !isBackStagePasses && !isSulfuras) {
				if (items[i].quality > 0) {
					items[i].quality--;
				}
			}
			if (!isSulfuras) {
				items[i].sellIn--;
			}
			if (items[i].sellIn < 0) {
				if (isAgedBrie) {
					if (items[i].quality < 50) {
						items[i].quality++;
					}
				}
				if (isBackStagePasses) {
					items[i].quality = 0;
				}
				if (!isAgedBrie && !isBackStagePasses && !isSulfuras) {
					if (items[i].quality > 0) {
						items[i].quality--;
					}
				}
			}
		}
	}
}