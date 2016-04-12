import models.Course;

/**
 * This is a class used to start the program and contains menu filled with example courses.
 * It's used for demo purposes to demonstrate how the program works.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class RestaurantMenuSelectorDemo extends RestaurantMenuSelector {
	
	/**
	 * Run the program
	 * @param args
	 */
	public static void main(String[] args) {
		new RestaurantMenuSelectorDemo();
	}
	
	/**
	 * Create instance of the program with menu filled with example data.
	 */
	public RestaurantMenuSelectorDemo() {
		super();
		this.addStarters();
		this.addMains();
		this.addFishCourses();
		this.addDrinks();
		this.addDeserts();
	}
	
	/**
	 * Add starters to the menu.
	 */
	private void addStarters() {
		this.menu.addCourse(Course.STARTER, "Gamberi", 5.99, 350000,
				"Succulent king prawns baked in garlic & chilli butter, with ciabatta", true, false, true, true);

		this.menu.addCourse(Course.STARTER, "Bruschetta", 3.99, 200000,
				"Plum tomatoes, rocket, red onion, garlic, olive oil and fresh basil on toasted ciabatta", true,
				true, true, true);

		this.menu.addCourse(Course.STARTER, "Polpette", 8.99, 500000,
				"Baked spiced pork & beef meatballs in a rich tomato sauce, topped with melting mozzarella, served with ciabatta",
				false, false, false, false);

		this.menu.addCourse(Course.STARTER, "Arancini Funghi", 7.50, 350000,
				"Mushroom risotto balls with  melting mozzarella, served with rocket leaves and a pomodoro sauce",
				true, true, true, false);
	}
	
	/**
	 * Add main courses to the menu.
	 */
	private void addMains() {
		this.menu.addCourse(Course.MAIN, "Margherita Pizza", 9.25, 650000, "Tomato, mozzarella and fresh basil",
				false, true, true, true);

		this.menu.addCourse(Course.MAIN, "Pepperoni Pizza", 12.30, 800000,
				"Spicy Italian pizza with pepperoni and hot green chillies",
				false, false, false, true);

		this.menu.addCourse(Course.MAIN, "Lamb Shank", 18.99, 1200000,
				"Lamb shank slow-cooked in a garlic, red wine & rosemary sauce, served with mashed potatoes and green beans",
				true, false, false, false);

		this.menu.addCourse(Course.MAIN, "Burger Americano", 12.99, 900000,
				"Chargrilled Aberdeen Angus beef burger, baby gem leaves, tomato, red onion and mayonnaise", false,
				false, false, false);

		this.menu.addCourse(Course.MAIN, "Carbonara", 10.99, 825000,
				"Spaghetti with crispy smoked pancetta, egg and pecorino cheese with a splash of cream", false,
				false, false, true);

		this.menu.addCourse(Course.MAIN, "Tagliatelle Pomodoro", 8.99, 500000,
				"Fresh egg tagliatelle with pomodoro sauce, fresh basil and basil oil", true, false, true, true);
	}
	
	/**
	 * Add fish courses to the menu.
	 */
	private void addFishCourses() {
		this.menu.addCourse(Course.FISH, "Cozze Alla Francese", 12.20, 900000,
				"A pot of mussles served with chips. With extra virgin olive oil, garlic, white wine, tomatoes and parsley.",
				true, false, true, false);

		this.menu.addCourse(Course.FISH, "Cozze Alla Marinara", 12.20, 900000,
				"Italian version of moules frites. A pot of mussels served with chips. With extra virgin oil, garlic, white wine, tomatoes and parsley..",
				true, false, true, false);
	}
	
	/**
	 * Add desserts to the menu.
	 */
	private void addDeserts() {
		this.menu.addCourse(Course.DESSERT, "Gelato", 5.00, 700000, "3-scoop", false, false, true, false);

		this.menu.addCourse(Course.DESSERT, "Tiramisu Mousse", 8.00, 200000,
				"Layers of coffee mousse with sweet mascarpone mousse", false, true, true, false);

		this.menu.addCourse(Course.DESSERT, "Vanilla Cheesecake", 6.00, 500000,
				"Vanilla cheesecake with mascarpone topped with chocolate tagliatelle and served with a pot of fresh cream",
				false, false, true, false);

		this.menu.addCourse(Course.DESSERT, "Pannacotta", 5.00, 500000,
				"Creamy vanilla pannacotta served with morello cherry sauce", false, false, true, false);
	}
	
	/**
	 * Add drinks to the menu
	 */
	private void addDrinks() {
		this.menu.addCourse(Course.DRINK, "Coca-cola 33cl", 3.00, 300000, "A can", false, true, true, false);
		this.menu.addCourse(Course.DRINK, "Juice 25cl", 3.00, 300000, "A glass", true, true, true, false);
		this.menu.addCourse(Course.DRINK, "Water 25cl", 2.00, 0, "A glass", true, true, true, false);
	}

}
