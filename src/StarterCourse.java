/**
 * Starter is type of a course.
 * @author Tomasz Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class StarterCourse extends Course {
	
	/**
	 * Constructor inherited from the superclass.
	 * @param name
	 * @param price
	 * @param calories
	 * @param description
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 */
	public StarterCourse(String name, Double price, int calories, String description, Boolean nutFree, Boolean vegan,
			Boolean vegetarian, Boolean glutenFree) {
		super(name, price, calories, description, nutFree, vegan, vegetarian, glutenFree);
	}
	
	/**
	 * Gets a course type name.
	 * @return
	 */
	public static String getCourseTypeNameOfClass() {
		return "Starters";
	}

}
