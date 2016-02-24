import java.util.ArrayList;
import java.util.List;

/**
 * Order in a restaurant. Orders are assigned to tables.
 * @author Tomasz Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Order {
	
	private int number;
	private List<Diner> diners = new ArrayList<Diner>();
	private Table table;
	
	/**
	 * Constructs an Order instance.
	 */
	public Order() {
		this.number = System.identityHashCode(this);
		this.addDiner(new Diner());
	}
	
	/**
	 * Construct an Order instance with table instance already set.
	 * @param table	Table's instance
	 */
	public Order(Table table) {
		this();
		this.setTable(table);
	}
	
	
	/**
	 * Gets diners list.
	 * @return diners list
	 */
	public List<Diner> getDiners() {
		return this.diners;
	}
	
	/**
	 * Adds diner to the list.
	 * @param diner
	 * @return true if it has been added
	 */
	public Boolean addDiner(Diner diner) {
		return this.diners.add(diner);
	}
	
	/**
	 * Updates table of the order. It also updates order list in Table's instance.
	 * @param table
	 */
	public void setTable(Table table) {
		if(this.table != null) {
			this.table.removeOrder(this);
		}
		
		this.table = table;
		this.table.addOrder(this);
	}
	
	/**
	 * String representation of the instance.
	 */
	public String toString() {
		return "Order no. " + this.number;
	}
	
	/**
	 * Gets total price.
	 * It calls getTotalPrice on diners' instances.
	 * @return total price for an order
	 */
	public Double getTotalPrice() {
		Double totalPrice = 0.0;
		
		for(Diner diner : this.diners) {
			totalPrice += diner.getTotalPrice();
		}
		
		return totalPrice;
	}
	
	/**
	 * Gets total calories
	 * @return total calories for an order
	 */
	public int getTotalCalories() {
		int totalCalories = 0;
		
		for(Diner diner : this.diners) {
			totalCalories += diner.getTotalCalories();
		}
		
		return totalCalories;
	}
	
	/**
	 * Converts total calories to kilocalories.
	 * @return total kilocalories for an order
	 */
	public int getTotalKiloCalories() {
		return this.getTotalCalories() / 1000;
	}
	
	/**
	 * Gets order number
	 * @return order number
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Gets a table assigned to an order.
	 * @return a Table object.
	 */
	public Table getTable() {
		return this.table;
	}
}
