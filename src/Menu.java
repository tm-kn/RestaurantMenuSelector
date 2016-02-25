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
	
	static Menu instance;
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
	
	public static Menu getInstance() {
		if(Menu.instance == null) {
			Menu.instance = new Menu();
		}
		
		return Menu.instance;
	}
	
	/**
	 * Loads initial courses to our menu.
	 */
	private void loadInitialData() {
		try {
			// Starters
			this.addCourse(
				StarterCourse.class, "Gamberi", 5.99, 350000,
				"Succulent king prawns baked in garlic & chilli butter, with ciabatta",
				true, false, true, true);
			
			this.addCourse(
				StarterCourse.class, "Bruschetta", 3.99, 200000,
				"Plum tomatoes, rocket, red onion, garlic, olive oil and fresh basil on toasted ciabatta",
				true, true, true, true);
			
			this.addCourse(
				StarterCourse.class, "Polpette", 8.99, 500000,
				"Baked spiced pork & beef meatballs in a rich tomato sauce, topped with melting mozzarella, served with ciabatta",
				false, false, false, false);
			
			this.addCourse(
				StarterCourse.class, "Arancini Funghi", 7.50, 350000,
				"Mushroom risotto balls with  melting mozzarella, served with rocket leaves and a pomodoro sauce",
				true, true, true, false);
			
			// Main courses
			this.addCourse(
				MainCourse.class, "Margherita Pizza", 9.25, 650000,
				"Tomato, mozzarella and fresh basil",
				false, true, true, true);
		
			this.addCourse(
				MainCourse.class, "Pepperoni Pizza", 12.30, 800000,
				"Spicy Italian pVanilla cheesecake with mascarpone topped with chocolate tagliatelle and served with a pot of fresh creamepperoni and hot green chillies",
				false, false, false, true);
			
			this.addCourse(
					MainCourse.class, "Lamb Shank", 18.99, 1200000,
					"Lamb shank slow-cooked in a garlic, red wine & rosemary sauce, served with mashed potatoes and green beans",
					true, false, false, false);
			
			this.addCourse(
					MainCourse.class, "Burger Americano", 12.99, 900000,
					"Chargrilled Aberdeen Angus beef burger, baby gem leaves, tomato, red onion and mayonnaise",
					false, false, false, false);
			
			this.addCourse(
					MainCourse.class, "Carbonara", 10.99, 825000,
					"Spaghetti with crispy smoked pancetta, egg and pecorino cheese with a splash of cream",
					false, false, false, true);
			
			this.addCourse(
					MainCourse.class, "Tagliatelle Pomodoro", 8.99, 500000,
					"Fresh egg tagliatelle with pomodoro sauce, fresh basil and basil oil",
					true, false, true, true);
			
			
			// Fish courses
			this.addCourse(
				FishCourse.class, "Cozze Alla Francese", 12.20, 900000,
				"A pot of mussles served with chips. With extra virgin olive oil, garlic, white wine, tomatoes and parsley.",
				true, false, true, false);
			
			this.addCourse(
				FishCourse.class, "Cozze Alla Marinara", 12.20, 900000,
				"Italian version of moules frites. A pot of mussels served with chips. With extra virgin oil, garlic, white wine, tomatoes and parsley..",
				true, false, true, false);
		
			
			// Dessert
			this.addCourse(Dessert.class, "Gelato", 5.00, 700000, "3-scoop", false, false, true, false);
			
			this.addCourse(
				Dessert.class, "Tiramisu Mousse", 8.00, 200000, "Layers of coffee mousse with sweet mascarpone mousse",
				false, true, true, false);
			
			this.addCourse(
				Dessert.class, "Vanilla Cheesecake", 6.00, 500000,
				"Vanilla cheesecake with mascarpone topped with chocolate tagliatelle and served with a pot of fresh cream",
				false, false, true, false);
			
			this.addCourse(
				Dessert.class, "Pannacotta", 5.00, 500000,
				"Creamy vanilla pannacotta served with morello cherry sauce",
				false, false, true, false);
			
			// Drinks
			this.addCourse(Drink.class, "Coca-cola 33cl", 3.00, 300000, "A can", false, true, true, false);
			this.addCourse(Drink.class, "Juice 25cl", 3.00, 300000, "A glass", true, true, true, false);
			this.addCourse(Drink.class, "Water 25cl", 2.00, 0, "A glass", true, true, true, false);
			
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
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
	 * @throws Exception 
	 */
	public Boolean addCourse(Class<?> courseType, String name, Double price, int calories, String description, Boolean nutFree, Boolean vegan, Boolean vegetarian, Boolean glutenFree) throws Exception {
		
		// Define a variable to store a constructor.
		Constructor<?> contructor;
		
		// Try to get a constructor from class specified by user
		try {
			contructor = courseType.getConstructor(String.class, Double.class, int.class, String.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		// Specify a variable for a new instance of a course
		Course newCourse;
		
		// Try to create an instance of a course
		try {
			newCourse = (Course) contructor.newInstance(name, price, calories, description, nutFree, vegan, vegetarian, glutenFree);
		} catch (Exception e) {
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
	public Boolean addCourse(Class<?> courseType, String name, Double price, int calories, String description) {
		try {
			return this.addCourse(courseType, name, price, calories, description, false, false, false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Shortcut to add a course without special information, only course type, name and price.
	 * @param courseType
	 * @param name
	 * @param price
	 * @return true if has been added
	 */
	public Boolean addCourse(Class<?> courseType, String name, Double price) {
		try {
			return this.addCourse(courseType, name, price, 0, "", false, false, false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Adds a course only.
	 * @param name
	 * @param price
	 * @return true if has been added
	 */
	public Boolean addCourse(String name, Double price) {
		try {
			return this.addCourse(Course.class, name, price, 0, "", false, false, false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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
	static public List<Class<?>> getCourseTypesListOutOfCoursesList(List<?> courses) {
		List<Class<?>> typesList = new ArrayList<Class<?>>();
		
		for (Object row : courses) {
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
	static public Map<Class<?>, List<Course>> groupByCourseType(List<Course> courses) {
		Map<Class<?>, List<Course>> groupedMap = new HashMap<Class<?>, List<Course>>();
		
		// Gets all of course types in our list
		List<Class<?>> typesList = Menu.getCourseTypesListOutOfCoursesList(courses);
		
		// Sets all of the types as keys in groupedMap and set values as empty lists
		for (Class<?> typeRow: typesList) {
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
	public Map<Class<?>, List<Course>> groupByCourseType() {
		return Menu.groupByCourseType(this.courses);
	}
}
