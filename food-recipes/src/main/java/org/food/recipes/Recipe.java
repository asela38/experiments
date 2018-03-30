package org.food.recipes;

public class Recipe {

	private String name;
	private String introducer;
	
	public Recipe(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIntroducer() {
		return introducer;
	}
	
	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	@Override
	public String toString() {
		return "Recipe [name=" + name + ", introducer=" + introducer + "]";
	}
	


}
