package org.food.recipes;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class RecipeTest {

	@Test
	public void testName() throws Exception {
		Recipe recipe = new Recipe("Fish Curry");
		recipe.setIntroducer("Annoma's Kitchen");
		System.out.println(recipe);
	}
}
