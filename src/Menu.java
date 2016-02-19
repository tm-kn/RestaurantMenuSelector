import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Menu contains courses, manages them and allows filtering and sorting them.
 * @author Tomasz Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Menu {
	
	private List<Course> courses = new ArrayList<Course>();
	
	/**
	 * Constructor method loads initial data.
	 */
	public Menu() {
		this(true);
	}
	
	/**
	 * Constructor which enables a choice if initial data will be loaded.
	 * @param initialData
	 */
	public Menu(Boolean initialData) {
		if(initialData) {
			this.loadInitialData();
		}
	}
	
	/**
	 * Loads initial courses to our menu.
	 */
	private void loadInitialData() {
		this.addCourse(Course.class, "Ketchup", 2.20);
		this.addCourse(MainCourse.class, "Pizza", 5.20);
		this.addCourse(MainCourse.class, "Test pizza", 5.30);
		this.addCourse(Course.class, "Musstard", 10.30);
		this.addCourse(Dessert.class, "Ice cream", 10.30);
	}

	/**
	 * Gets a list of all courses in the menu.
	 * @return list of courses
	 */
	public List<Course> getCourses() {
		return courses;
	}
	
	/**
	 * Adds a course to menu
	 * @param courseType	Class of a type you want to use as a course type, e.g. MainCourse.class.
	 * @param name	
	 * @param price
	 * @param calories
	 * @param description
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 * @return true if has been added
	 */
	public Boolean addCourse(Class<? extends Course> courseType, String name, Double price, int calories, String description, Boolean nutFree, Boolean vegan, Boolean vegetarian, Boolean glutenFree) {
		
		// Define a variable to store a constructor of any class which is Course or inherits from Course
		Constructor<? extends Course> contructor;
		
		// Try to get a constructor from class specified by user
		try {
			contructor = courseType.getConstructor(String.class, Double.class, int.class, String.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return false;
		}
		
		// Specify a variable for a new instance of a course
		Course newCourse;
		
		// Try to create an instance of a course
		try {
			newCourse = contructor.newInstance(name, price, calories, description, nutFree, vegan, vegetarian, glutenFree);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
		
		return this.addCourse(newCourse);
	}
	
	/**
	 * Shortcut to add a course without specifying if food is suitable for vegetarian, vegans, gluten-free, etc.
	 * @param courseType
	 * @param name
	 * @param price
	 * @param calories
	 * @param description
	 * @return true if has been added
	 */
	public Boolean addCourse(Class<? extends Course> courseType, String name, Double price, int calories, String description) {
		return this.addCourse(courseType, name, price, calories, description, false, false, false, false);
	}
	
	/**
	 * Shortcut to add a course without special information, only course type, name and price.
	 * @param courseType
	 * @param name
	 * @param price
	 * @return true if has been added
	 */
	public Boolean addCourse(Class<? extends Course> courseType, String name, Double price) {
		return this.addCourse(courseType, name, price, 0, "", false, false, false, false);
	}
	
	/**
	 * Adds a course only.
	 * @param name
	 * @param price
	 * @return true if has been added
	 */
	public Boolean addCourse(String name, Double price) {
		return this.addCourse(Course.class, name, price, 0, "", false, false, false, false);
	}
	
	/**
	 * Adds a course with a course instance.
	 * @param course
	 * @return true if has been added
	 */
	public Boolean addCourse(Course course) {
		return this.courses.add(course);
	}
	
	/**
	 * Deletes a course from the menu.
	 * @param course
	 * @return true if has been removed
	 */
	public Boolean deleteCourse(Course course) {
		return this.courses.remove(course);
	}
	
	/**
	 * Deletes a course by its index in the courses list.
	 * @param index
	 * @return returns a course instance if it has been deleted.
	 */
	public Course deleteCourse(int index) {
		return this.courses.remove(index);
	}
	
	/**
	 * Returns an index in the courses list of a specified course instance.
	 * @param course
	 * @return index
	 */
	public int indexOf(Course course) {
		return this.courses.indexOf(course);
	}
	
	/**
	 * Filters a list of courses by particular criteria and returns filtered list.
	 * @param courses	list of courses to be filtered
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 * @return Filtered list by set criteria
	 */
	static public List<Course> filterCoursesList(List<Course> courses, Boolean nutFree, Boolean vegan, Boolean vegetarian, Boolean glutenFree) {
		List<Course> filteredList = new ArrayList<Course>();
		
		for(Course row: courses) {
			if(nutFree && !row.getNutFree()) {
				continue;
			}
			
			if(vegan && !row.getVegan()) {
				continue;
			}
			
			if(vegetarian && !row.getVegetarian()) {
				continue;
			}
			
			if(glutenFree && !row.getGlutenFree()) {
				continue;
			}
			
			filteredList.add(row);
		}
		
		return filteredList;
	}
	
	/**
	 * Shortcut function filtering a courses by their properties.
	 * @param courses
	 * @param nutFree
	 * @param vegan
	 * @param vegetarian
	 * @param glutenFree
	 * @return
	 */
	public List<Course> filterCoursesList(Boolean nutFree, Boolean vegan, Boolean vegetarian, Boolean glutenFree) {
		return Menu.filterCoursesList(this.courses, nutFree, vegan, vegetarian, glutenFree);
	}
	
	/**
	 * Return lists of different classes which are or might inherit from Course and are supplied in a parameter.
	 * @param courses List of courses you want to get types of.
	 * @return List with courses types.
	 */
	static public List<Class<? extends Course>> getCourseTypesListOutOfCoursesList(List<? extends Course> courses) {
		List<Class<? extends Course>> typesList = new ArrayList<Class<? extends Course>>();
		
		for (Course row : courses) {
			// Checks if typesList does not contain a class of this particular course.
			if(!typesList.contains(row.getClass())) {
				typesList.add(row.getClass());
			}
		}
		
		return typesList;
	}
	
	/**
	 * Groups courses by their type and returns a map.
	 * @param courses list of courses.
	 * @return a map having course type as a key and courses list of that particular type as value.
	 */
	static public Map<Class<? extends Course>, List<Course>> groupByCourseType(List<Course> courses) {
		Map<Class<? extends Course>, List<Course>> groupedMap = new HashMap<Class<? extends Course>, List<Course>>();
		
		// Gets all of course types in our list
		List<Class<? extends Course>> typesList = Menu.getCourseTypesListOutOfCoursesList(courses);
		
		// Sets all of the types as keys in groupedMap and set values as empty lists
		for (Class<? extends Course> typeRow: typesList) {
			groupedMap.put(typeRow, new ArrayList<Course>());
		}
		
		// Goes through all the courses and assigns them to groupedMap
		for(Course row : courses) {
			// At first gets courses of a particular type actually added to groupedMap
			List<Course> coursesOfType = groupedMap.get(row.getClass());
			
			// Then it adds current row to that courses type and updates groupedMap.
			coursesOfType.add(row);
			groupedMap.put(row.getClass(), coursesOfType);
		}
		
		return groupedMap;
		
	}
	
	/**
	 * Shortcut to a static method grouping courses by their types.
	 * @return a map having course type as a key and courses list of that particular type as value.
	 */
	public Map<Class<? extends Course>, List<Course>> groupByCourseType() {
		return Menu.groupByCourseType(this.courses);
	}
}
