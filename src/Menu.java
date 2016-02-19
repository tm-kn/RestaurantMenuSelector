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
	
	public Menu() {
		this(true);
	}
	
	public Menu(Boolean initialData) {
		if(initialData) {
			this.loadInitialData();
		}
	}
	
	private void loadInitialData() {
		this.addCourse(Course.class, "Ketchup", 2.20);
		this.addCourse(MainCourse.class, "Pizza", 5.20);
		this.addCourse(MainCourse.class, "Test pizza", 5.30);
		this.addCourse(Course.class, "Musstard", 10.30);
		this.addCourse(DessertCourse.class, "Ice cream", 10.30);
	}

	public List<Course> getCourses() {
		return courses;
	}
	
	public void addCourse(Class<? extends Course> courseType, String name, Double price, int calories, String description, Boolean nutFree, Boolean vegan, Boolean vegetarian, Boolean glutenFree) {
		Constructor<? extends Course> contructor;
		try {
			contructor = courseType.getConstructor(String.class, Double.class, int.class, String.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return;
		}
		Course newCourse;
		try {
			newCourse = contructor.newInstance(name, price, calories, description, nutFree, vegan, vegetarian, glutenFree);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return;
		}
		this.addCourse(newCourse);
	}
	
	public void addCourse(Class<? extends Course> courseType, String name, Double price, int calories, String description) {
		this.addCourse(courseType, name, price, calories, description, false, false, false, false);
	}
	
	public void addCourse(Class<? extends Course> courseType, String name, Double price) {
		this.addCourse(courseType, name, price, 0, "", false, false, false, false);
	}
	
	public void addCourse(String name, Double price) {
		this.addCourse(Course.class, name, price, 0, "", false, false, false, false);
	}
	
	public void addCourse(Course course) {
		this.courses.add(course);
	}
	
	public void deleteCourse(Course course) {
		this.courses.remove(course);
	}
	
	public void deleteCourse(int index) {
		this.courses.remove(index);
	}
	
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
