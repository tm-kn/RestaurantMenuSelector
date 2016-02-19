import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Contains information about a course and is a base for other types of a course.
 * @author Tomasz Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Course {
	
	private String name;
	private Double price;
	private int calories;
	private String description;
	private Boolean nutFree = false;
	private Boolean vegan = false;
	private Boolean vegetarian = false;
	private Boolean glutenFree = false;
	
	/**
	 * Constructor method for a course.
	 * @param name
	 * @param price
	 * @param calories
	 * @param description
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 */
	public Course (String name, Double price, int calories, String description, Boolean nutFree, Boolean vegan, Boolean vegetarian, Boolean glutenFree) {
		this.name = name;
		this.price = price;
		this.calories = calories;
		this.description = description;
		this.nutFree = nutFree;
		this.vegan = vegan;
		this.vegetarian = vegetarian;
		this.glutenFree = glutenFree;
	}

	/**
	 * Accesses name attribute.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name attribute.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets price attribute.
	 * @return price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets price attribute.
	 * @param price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	
	/**
	 * Gets calories attribute.
	 * @return calories
	 */
	public int getCalories() {
		return calories;
	}
	
	/**
	 * Gets calories and converts them to kilocalories.
	 * @return kilocalories
	 */
	public int getKiloCalories() {
		return calories / 1000;
	}

	/**
	 * Sets calories.
	 * @param calories
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}
	
	/**
	 * Gets description attribute.
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets description attribute.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Is course nut free?
	 * @return nut free
	 */
	public Boolean getNutFree() {
		return nutFree;
	}
	
	/**
	 * Sets if course is nut free.
	 * @param nutFree
	 */
	public void setNutFree(Boolean nutFree) {
		this.nutFree = nutFree;
	}
	
	/**
	 * Is course appropriate for vegans?
	 * @return vegan meal?
	 */
	public Boolean getVegan() {
		return vegan;
	}
	
	/**
	 * Sets if course is suitable for vegans.
	 * @param vegan
	 */
	public void setVegan(Boolean vegan) {
		this.vegan = vegan;
	}
	
	/**
	 * Is course suitable for vegetarians?
	 * @return vegetarian meal?
	 */
	public Boolean getVegetarian() {
		return vegetarian;
	}
	
	/**
	 * Sets if course is suitable for vegetarians.
	 * @param vegetarian
	 */
	public void setVegetarian(Boolean vegetarian) {
		this.vegetarian = vegetarian;
	}
	
	/**
	 * Is course is gluten-free?
	 * @return gluten-free meal?
	 */
	public Boolean getGlutenFree() {
		return glutenFree;
	}
	
	/**
	 * Sets if meal is gluten-free.
	 * @param glutenFree
	 */
	public void setGlutenFree(Boolean glutenFree) {
		this.glutenFree = glutenFree;
	}
	
	/**
	 * Gets course type name of an instance. To be used on an instance only!
	 * @return course type name
	 */
	public String getCourseTypeName() {
		return Course.getCourseTypeNameOfClassCourseType(this.getClass());
	}
	
	/**
	 * Gets course type name of a class. To be used as a static method only!
	 * @return course type name
	 */
	public static String getCourseTypeNameOfClass() {
		return "Other";
	}
	
	/**
	 * Gets course type name of a provided course class.
	 * @param cls	Class which is Course or inherits from Course.
	 * @return course type name
	 */
	static public String getCourseTypeNameOfClassCourseType(Class<? extends Course> cls) {
		Method m = null;
		try {
			m = cls.getMethod("getCourseTypeNameOfClass");
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return "Other";
		}
		
		try {
			return m.invoke(new Object()).toString();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "Other";
		}
		
	}
}

