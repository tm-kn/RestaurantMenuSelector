package models;
import java.util.ArrayList;
import java.util.List;

/**
 * Table in a restaurant.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Table {

	private int number;
	private List<Order> orders = new ArrayList<Order>();

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * Constructor for a Table class.
	 * 
	 * @param number
	 *            table number
	 */
	public Table(int number) {
		this.number = number;
	}

	/**
	 * Removes order from order's list.
	 * 
	 * @param order
	 *            instance of an Order
	 * @return true if it has been removed.
	 */
	public Boolean removeOrder(Order order) {
		return this.orders.remove(order);
	}

	/**
	 * Adds an order to the list.
	 * 
	 * @param order
	 *            instance of an Order class
	 * @return true if it has been removed
	 */
	public Boolean addOrder(Order order) {
		return this.orders.add(order);
	}

	/**
	 * String representation of a table.
	 */
	public String toString() {
		return "Table no. " + this.number;
	}

}
