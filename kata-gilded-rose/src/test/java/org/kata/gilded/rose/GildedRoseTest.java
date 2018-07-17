package org.kata.gilded.rose;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.approvaltests.Approvals;
import org.junit.Test;

public class GildedRoseTest {

	@Test
	public void approvalVerifyAll() {
		Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20), //
				new Item("Aged Brie", 2, 0), //
				new Item("Elixir of the Mongoose", 5, 7), //
				new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
				new Item("Sulfuras, Hand of Ragnaros", -1, 80),
				new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
				new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
				new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
				// this conjured item does not work properly yet
				new Item("Conjured Mana Cake", 3, 6) };

		GildedRose app = new GildedRose(items);

		int days = 50;

		List<Item> itemList = new LinkedList<>();
		for (int i = 0; i < days; i++) {
			System.out.println("-------- day " + i + " --------");
			System.out.println("name, sellIn, quality");
			for (Item item : items) {
				System.out.println(item);
			}
			System.out.println();
			app.updateQuality();
			Arrays.stream(items).map(item -> new Item(item.name, item.sellIn, item.quality)).forEach(itemList::add);
		}

		Approvals.verifyAll("items", itemList.toArray(new Item[0]));
	}

}