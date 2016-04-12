package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.EmptyMenuException;

/**
 * Menu contains courses, manages them and allows filtering and sorting them.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Menu {

	private List<Course> courses = new ArrayList<Course>();

	/**
	 * Constructor
	 */
	public Menu() {

	}

	/**
	 * Gets a list of all courses in the menu.
	 * 
	 * @return list of courses
	 */
	public List<Course> getCourses() {
		if(this.courses.size() == 0) {
			throw new EmptyMenuException();
		}
		
		return courses;
	}
	
	/**
	 * It sets a new courses list to the menu.
	 * @param coursesList	List with Course objects.
	 */
	public void setCoursesList(List<Course> coursesList) {
		this.courses = coursesList;
	}

	/**
	 * Adds a course to menu
	 * 
	 * @param courseType
	 * @param name
	 * @param price
	 * @param calories
	 * @param description
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 */
	public void addCourse(String courseType, String name, double price, int calories, String description,
			boolean nutFree, boolean vegan, boolean vegetarian, boolean glutenFree) {

		Course newCourse = new Course(courseType, name, price, calories, description, nutFree, vegan, vegetarian,
					glutenFree);

		this.addCourse(newCourse);
	}

	/**
	 * Shortcut to add a course without specifying if food is suitable for
	 * vegetarian, vegans, gluten-free, etc.
	 * 
	 * @param courseType
	 * @param name
	 * @param price
	 * @param calories
	 * @param description
	 */
	public void addCourse(String courseType, String name, double price, int calories, String description) {
		this.addCourse(courseType, name, price, calories, description, false, false, false, false);
	}

	/**
	 * Shortcut to add a course without special information, only course type,
	 * name and price.
	 * 
	 * @param courseType
	 * @param name
	 * @param price
	 */
	public void addCourse(String courseType, String name, double price) {
		this.addCourse(courseType, name, price, 0, "", false, false, false, false);
	}

	/**
	 * Adds a course with name and price only.
	 * 
	 * @param name
	 * @param price
	 */
	public void addCourse(String name, double price) {
		this.addCourse(Course.OTHER, name, price, 0, "", false, false, false, false);
	}

	/**
	 * Adds a course with a course instance.
	 * 
	 * @param course
	 */
	public void addCourse(Course course) {
		this.courses.add(course);
	}

	/**
	 * Deletes a course from the menu.
	 * 
	 * @param course
	 */
	public void removeCourse(Course course) {
		this.courses.remove(course);
	}

	/**
	 * Deletes a course by its index in the courses list.
	 * 
	 * @param index
	 * @return returns a course instance if it has been deleted.
	 */
	public Course removeCourse(int index) {
		return this.courses.remove(index);
	}

	/**
	 * Returns an index in the courses list of a specified course instance.
	 * 
	 * @param course
	 * @return index
	 */
	public int indexOf(Course course) {
		return this.courses.indexOf(course);
	}

	/**
	 * Filters a list of courses by particular criteria and returns filtered
	 * list.
	 * 
	 * @param courses
	 *            list of courses to be filtered
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 * @return Filtered list by set criteria
	 */
	static public List<Course> filterCoursesList(List<Course> courses, boolean nutFree, boolean vegan,
			boolean vegetarian, boolean glutenFree) {
		List<Course> filteredList = new ArrayList<Course>();

		for (Course row : courses) {
			if (nutFree && !row.getNutFree()) {
				continue;
			}

			if (vegan && !row.getVegan()) {
				continue;
			}

			if (vegetarian && !row.getVegetarian()) {
				continue;
			}

			if (glutenFree && !row.getGlutenFree()) {
				continue;
			}

			filteredList.add(row);
		}

		return filteredList;
	}

	/**
	 * Shortcut function filtering a courses by their properties.
	 * 
	 * @param courses
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 * @return filtered course list by their properties
	 */
	public List<Course> filterCoursesList(boolean nutFree, boolean vegan, boolean vegetarian, boolean glutenFree) {
		return Menu.filterCoursesList(this.getCourses(), nutFree, vegan, vegetarian, glutenFree);
	}

	/**
	 * Return lists of course types included in the menu.
	 * 
	 * @param courses
	 *            List of courses you want to get types of.
	 * @return List with courses type names.
	 */
	static public List<String> getCourseTypesListOutOfCoursesList(List<Course> courses) {
		List<String> typesList = new ArrayList<String>();

		for (Course course : courses) {
			if (!typesList.contains(course.getCourseType())) {
				typesList.add(course.getCourseType());
			}
		}

		return typesList;
	}

	/**
	 * Groups courses by their type and returns a map.
	 * 
	 * @param courses
	 *            list of courses.
	 * @return a map having course type's name as a key and courses list of that
	 *         particular type as value.
	 */
	static public Map<String, List<Course>> groupByCourseType(List<Course> courses) {
		Map<String, List<Course>> groupedMap = new HashMap<String, List<Course>>();

		// Gets all of course types in our list
		List<String> typesList = Menu.getCourseTypesListOutOfCoursesList(courses);

		// Sets all of the types as keys in groupedMap and set values as empty
		// lists
		for (String type : typesList) {
			groupedMap.put(type, new ArrayList<Course>());
		}

		// Goes through all the courses and assigns them to groupedMap
		for (Course row : courses) {
			// At first gets courses of a particular type actually added to
			// groupedMap
			List<Course> coursesOfType = groupedMap.get(row.getCourseType());

			// Then it adds current row to that courses type and updates
			// groupedMap.
			coursesOfType.add(row);
			groupedMap.put(row.getCourseType(), coursesOfType);
		}

		return groupedMap;

	}

	/**
	 * Shortcut to a static method grouping courses by their types.
	 * 
	 * @return a map having course type's name as a key and courses list of that
	 *         particular type as value.
	 */
	public Map<String, List<Course>> groupByCourseType() {
		return Menu.groupByCourseType(this.getCourses());
	}
}
