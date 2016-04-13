package models;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a diner and it's assigned to an order.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Diner {

	private List<Course> courses;

	/**
	 * Constructs a Diner object.
	 */
	public Diner() {
		this.courses = new ArrayList<Course>();
	}

	/**
	 * Gets total price for a diner.
	 * 
	 * @return total price for a diner
	 */
	public Double getTotalPrice() {
		Double totalPrice = 0.0;

		for (Course course : this.courses) {
			totalPrice += course.getPrice();
		}

		return totalPrice;
	}

	/**
	 * Gets total calories number for a diner.
	 * 
	 * @return calories number
	 */
	public int getTotalCalories() {
		int calories = 0;

		for (Course course : this.courses) {
			calories += course.getCalories();
		}

		return calories;
	}

	/**
	 * Converts calories to kilocalories
	 * 
	 * @return kilocalories for a diner
	 */
	public int getTotalKiloCalories() {
		return this.getTotalCalories() / 1000;
	}

	/**
	 * Adds course to the list.
	 * 
	 * @param course
	 *            Course's instance
	 * @return true if it has been added.
	 */
	public Boolean addCourse(Course course) {
		return this.courses.add(course);
	}

	/**
	 * Deletes course from the list.
	 * 
	 * @param course
	 *            Course's instance
	 * @return true if it has been removed.
	 */
	public Boolean deleteCourse(Course course) {
		return this.courses.remove(course);
	}

	/**
	 * Delete course from the list.
	 * 
	 * @param index
	 * @return Course's instance that has been deleted
	 */
	public Course deleteCourse(int index) {
		return this.courses.remove(index);
	}

	/**
	 * Gets courses for a diner.
	 * 
	 * @return list of courses;
	 */
	public List<Course> getCourses() {
		return this.courses;
	}
}
