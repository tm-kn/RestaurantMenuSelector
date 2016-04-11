import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.InvalidAmountPaidException;
import exceptions.InvalidNumberOfCoursesOrderedException;
import exceptions.InvalidNumberOfDinersException;
import exceptions.InvalidOrderStatusException;
import exceptions.TableHasNotBeenChosenException;

/**
 * Order in a restaurant. Orders are assigned to tables.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Order {
	
	final static int ORDERING = 1;
	final static int ORDERED = 2;
	final static int IN_PREPARATION = 3;
	final static int SERVED = 4;
	
	private int number, status;
	private List<Diner> diners;
	private Table table;
	private double amountPaid;
	private Date paidAt;

	/**
	 * Constructs an Order instance.
	 */
	public Order() {
		this.number = System.identityHashCode(this);
		this.status = Order.ORDERING;
		this.amountPaid = 0.0;
		
		// Order must have at least one order to be valid, so let's add one
		this.diners = new ArrayList<Diner>();
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
	public double getTotalPrice() {
		double totalPrice = 0.0;

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
	 * Check if order is being ordered now
	 * @return boolean
	 */
	public boolean isBeingOrdered() {
		return this.status == Order.ORDERING;
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
	public boolean isServed() {
		return this.status == Order.SERVED;
	}
	
	/**
	 * Check if order contain any course
	 * @return
	 */
	public boolean doesOrderContainAnyCourse() {
		for(Diner diner : this.diners) {
			if(diner.getCourses().size() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check if the order can be paid for. It's meant to be used with GUI to check if elements such as buttons should be activated.
	 * @return boolean
	 * @throws InvalidOrderStatusException 
	 */
	public boolean isReadyToPay() {
		
		try {
			this.validateOrderBeforePayment();
		} catch (TableHasNotBeenChosenException | InvalidNumberOfDinersException
				| InvalidNumberOfCoursesOrderedException | InvalidOrderStatusException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Calculate the change
	 * @return change for order
	 */
	public double getChangeAmount() {
		double change = this.amountPaid - this.getTotalPrice();
		
		if(change < 0.0) {
			System.err.println("Change has been calculated to be less than zero. Returning zero.");
			return 0.0;
		}
		
		return change;
	}
	
	/**
	 * Validate if the order is ready to be paid for.
	 * @throws TableHasNotBeenChosenException
	 * @throws InvalidNumberOfDinersException
	 * @throws InvalidNumberOfCoursesOrderedException
	 * @throws InvalidOrderStatusException
	 */
	private void validateOrderBeforePayment() throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidOrderStatusException {		
		if(!this.isBeingOrdered()) {
			throw new InvalidOrderStatusException();
		}
		
		if(this.table == null) {
			throw new TableHasNotBeenChosenException();
		}
		
		if(this.diners.size() == 0) {
			throw new InvalidNumberOfDinersException();
		}
		
		if(!this.doesOrderContainAnyCourse()) {
			throw new InvalidNumberOfCoursesOrderedException();
		}
	}
	
	/**
	 * Check if the payment has been successful
	 * @throws InvalidAmountPaidException
	 */
	private void validatePayment() throws InvalidAmountPaidException {
		if(this.amountPaid < this.getTotalPrice()) {
			throw new InvalidAmountPaidException();
		}
	}
	
	/**
	 * Pay for the order
	 * @param amount	amount which has been paid by user
	 * @throws TableHasNotBeenChosenException
	 * @throws InvalidNumberOfDinersException
	 * @throws InvalidNumberOfCoursesOrderedException
	 * @throws InvalidAmountPaidException
	 * @throws InvalidOrderStatusException
	 */
	public void pay(Double amount) throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidAmountPaidException, InvalidOrderStatusException {
		// Check if order meets all the criteria to be paid for
		this.validateOrderBeforePayment();
		
		// Set the amount that has been paid
		this.amountPaid = amount;
		
		// Check if the given amount is valid.
		this.validatePayment();
		
		// Set status of order to be ordered - it has been sent to the kitchen
		this.setStatus(Order.ORDERED);
		
		// Update the date when the order has been paid for
		this.paidAt = new Date();
		
		// Prints a receipt for the order
		this.printReceipt();
	}

	/**
	 * Print receipt for the order
	 */
	private void printReceipt() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		System.out.println("RECEIPT");
		System.out.println(dateFormat.format(this.getPaidAt()));
		System.out.println("Order #" + this.getNumber());
		System.out.println("Table number: " + this.getTable().getNumber());
		System.out.println("--------------------");
		
		int i = 1;
		for(Diner diner : this.getDiners()) {
			System.out.println("-- DINER " + i + " --");
			
			for(Course course : diner.getCourses()) {
				System.out.println(course.getName() + " - " + course.getKiloCalories() + "kcal - £" + "" + course.getPrice());
			}
			
			System.out.println();
			System.out.println("Total cost for diner " + i + ": £" + OrderScreen.DECIMAL_FORMAT.format(diner.getTotalPrice()));
			System.out.println("Total kilocalories for diner " + i + ": " + diner.getTotalKiloCalories() + "kcal");
			System.out.println();
			
			i++;
		}
		
		System.out.println("--------------------");
		System.out.println("Total: £" + OrderScreen.DECIMAL_FORMAT.format(this.getTotalPrice()));
		System.out.println("Paid: £" + OrderScreen.DECIMAL_FORMAT.format(this.getAmountPaid()));
		System.out.println("Change: £" + OrderScreen.DECIMAL_FORMAT.format(this.getChangeAmount()));
		System.out.println("--------------------");
		System.out.println("Thank you for your custom");
	}

	/**
	 * Accessor for amount paid attribute
	 * @return amount paid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}

	/**
	 * Accessor for paid at attribute
	 * @return time order has been paid
	 */
	public Date getPaidAt() {
		return paidAt;
	}
}
