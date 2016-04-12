package models;
import java.util.Arrays;

import exceptions.InvalidCourseTypeException;

/**
 * Contains information about a course and is a base for other types of a
 * course.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Course {
	public final static String MAIN = "Main";
	public final static String DESSERT = "Dessert";
	public final static String DRINK = "Drink";
	public final static String FISH = "Fish";
	public final static String STARTER = "Starter";
	public final static String OTHER = "Other";
	public final static String[] COURSE_TYPES = {MAIN, DESSERT, DRINK, FISH, STARTER, OTHER};
	
	private String courseType;
	private String name;
	private double price;
	private int calories;
	private String description;
	private boolean nutFree = false;
	private boolean vegan = false;
	private boolean vegetarian = false;
	private boolean glutenFree = false;

	/**
	 * Constructor method for a course.
	 * 
	 * @param name
	 * @param price
	 * @param calories
	 * @param description
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 */
	public Course(String courseType, String name, double price, int calories, String description, boolean nutFree, boolean vegan,
			boolean vegetarian, boolean glutenFree) {
		this.setCourseType(courseType);
		this.setName(name);
		this.setPrice(price);
		this.setCalories(calories);
		this.setDescription(description);
		this.setNutFree(nutFree);
		this.setVegan(vegan);
		this.setVegetarian(vegetarian);
		this.setGlutenFree(glutenFree);
	}

	/**
	 * Accesses name attribute.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name attribute.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		if(name == null) {
			throw new NullPointerException();
		}
		
		if(name.length() < 1) {
			throw new IllegalArgumentException("Name must constist from at least 1 character.");
		}
		this.name = name;
	}

	/**
	 * Gets price attribute.
	 * 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets price attribute.
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		if(price < 0.0) {
			throw new IllegalArgumentException("Price cannot be less than zero.");
		}
		
		this.price = price;
	}

	/**
	 * Gets calories attribute.
	 * 
	 * @return calories
	 */
	public int getCalories() {
		return this.calories;
	}

	/**
	 * Gets calories and converts them to kilocalories.
	 * 
	 * @return kilocalories
	 */
	public int getKiloCalories() {
		return this.calories / 1000;
	}

	/**
	 * Sets calories.
	 * 
	 * @param calories
	 */
	public void setCalories(int calories) {
		if(calories < 0) {
			throw new IllegalArgumentException("Calories cannot be less than zero");
		}
		
		this.calories = calories;
	}

	/**
	 * Gets description attribute.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description attribute.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Is course nut free?
	 * 
	 * @return nut free
	 */
	public boolean getNutFree() {
		return nutFree;
	}

	/**
	 * Sets if course is nut free.
	 * 
	 * @param nutFree
	 */
	public void setNutFree(boolean nutFree) {
		this.nutFree = nutFree;
	}

	/**
	 * Is course appropriate for vegans?
	 * 
	 * @return vegan meal?
	 */
	public boolean getVegan() {
		return vegan;
	}

	/**
	 * Sets if course is suitable for vegans.
	 * 
	 * @param vegan
	 */
	public void setVegan(boolean vegan) {
		this.vegan = vegan;
	}

	/**
	 * Is course suitable for vegetarians?
	 * 
	 * @return vegetarian meal?
	 */
	public boolean getVegetarian() {
		return vegetarian;
	}

	/**
	 * Sets if course is suitable for vegetarians.
	 * 
	 * @param vegetarian
	 */
	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

	/**
	 * Is course is gluten-free?
	 * 
	 * @return gluten-free meal?
	 */
	public boolean getGlutenFree() {
		return glutenFree;
	}

	/**
	 * Sets if meal is gluten-free.
	 * 
	 * @param glutenFree
	 */
	public void setGlutenFree(boolean glutenFree) {
		this.glutenFree = glutenFree;
	}
	
	/**
	 * Accessor for course type
	 * @return courseType
	 */
	public String getCourseType() {
		return courseType;
	}

	/**
	 * Mutator for course type
	 * Needs to be compatible with COURSE_TYPES.
	 * @param courseType type of course
	 * @throws InvalidCourseTypeException 
	 */
	public void setCourseType(String courseType) {
		if(!Arrays.asList(Course.COURSE_TYPES).contains(courseType)) {
			throw new InvalidCourseTypeException();
		}
		
		this.courseType = courseType;
	}
	
	public String toString() {
		return this.name;
	}
}
