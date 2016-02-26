import java.util.ArrayList;
import java.util.List;

/**
 * Order in a restaurant. Orders are assigned to tables.
 * 
 * @author Tomasz Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Order {
	
	final static int ORDERING = 1;
	final static int ORDERED = 2;
	final static int IN_PREPARATION = 3;
	final static int SERVED = 4;
	private int number;
	private List<Diner> diners = new ArrayList<Diner>();
	private Table table;
	private int status;

	/**
	 * Constructs an Order instance.
	 */
	public Order() {
		this.number = System.identityHashCode(this);
		this.status = Order.ORDERING;
		this.addDiner(new Diner());
	}

	/**
	 * Construct an Order instance with table instance already set.
	 * 
	 * @param table
	 *            Table's instance
	 */
	public Order(Table table) {
		this();
		this.setTable(table);
	}

	/**
	 * Gets diners list.
	 * 
	 * @return diners list
	 */
	public List<Diner> getDiners() {
		return this.diners;
	}

	/**
	 * Adds diner to the list.
	 * 
	 * @param diner
	 * @return true if it has been added
	 */
	public Boolean addDiner(Diner diner) {
		return this.diners.add(diner);
	}

	/**
	 * Removes a diner from the list
	 * 
	 * @param diner
	 *            you want to delete
	 */
	public void deleteDiner(Diner diner) {
		this.diners.remove(diner);
	}

	/**
	 * Updates table of the order. It also updates order list in Table's
	 * instance.
	 * 
	 * @param table
	 */
	public void setTable(Table table) {
		if (this.table != null) {
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
	 * Gets total price. It calls getTotalPrice on diners' instances.
	 * 
	 * @return total price for an order
	 */
	public Double getTotalPrice() {
		Double totalPrice = 0.0;

		for (Diner diner : this.diners) {
			totalPrice += diner.getTotalPrice();
		}

		return totalPrice;
	}

	/**
	 * Gets total calories
	 * 
	 * @return total calories for an order
	 */
	public int getTotalCalories() {
		int totalCalories = 0;

		for (Diner diner : this.diners) {
			totalCalories += diner.getTotalCalories();
		}

		return totalCalories;
	}

	/**
	 * Converts total calories to kilocalories.
	 * 
	 * @return total kilocalories for an order
	 */
	public int getTotalKiloCalories() {
		return this.getTotalCalories() / 1000;
	}

	/**
	 * Gets order number
	 * 
	 * @return order number
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Gets a table assigned to an order.
	 * 
	 * @return a Table object.
	 */
	public Table getTable() {
		return this.table;
	}

	/**
	 * Gets a status of the order.
	 * @return returns integer of the status. Statuses are defined through constants on the class
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status of the order
	 * @param status	use constants on Order class
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Checks if the status is ordered
	 * @return boolean
	 */
	public boolean isOrdered() {
		return this.status == Order.ORDERED;
	}
	
	/**
	 * Checks if the status is in preparation
	 * @return boolean
	 */
	public boolean isInPreparation() {
		return this.status == Order.IN_PREPARATION;
	}
	
	/**
	 * Checks if the order has been already served
	 * @return boolean
	 */
	public boolean isServer() {
		return this.status == Order.SERVED;
	}
	
	public boolean doesOrderContainAnyCourse() {
		for(Diner diner : this.diners) {
			if(diner.getCourses().size() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if the order can be paid for.
	 * @return boolean
	 */
	public boolean isReadyToPay() {
		
		// If table has been chosen
		if(this.table == null) {
			return false;
		}
		
		// If there's at least one diner
		if(this.diners.size() == 0) {
			return false;
		}
		
		// If there's at least one item ordered
		if(!this.doesOrderContainAnyCourse()) {
			return false;
		}
		
		return true;
	}
}
