package models;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import exceptions.InvalidAmountPaidException;
import exceptions.InvalidNumberOfCoursesOrderedException;
import exceptions.InvalidNumberOfDinersException;
import exceptions.InvalidOrderStatusException;
import exceptions.TableHasNotBeenChosenException;
import gui.OrderScreen;

/**
 * Order in a restaurant. Orders are assigned to tables.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class Order {
	
	public final static int ORDERING = 1;
	public final static int ORDERED = 2;
	public final static int IN_PREPARATION = 3;
	public final static int SERVED = 4;
	public final static Integer[] ALLOWED_STATUSES = new Integer[] {ORDERING, ORDERED, IN_PREPARATION, SERVED};
	
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
	 * @throws InvalidNumberOfDinersException 
	 */
	public void deleteDiner(Diner diner) throws InvalidNumberOfDinersException {
		if(this.diners.size() < 2) {
			throw new InvalidNumberOfDinersException();
		}
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
	 * @throws InvalidOrderStatusException 
	 */
	public void setStatus(int status) throws InvalidOrderStatusException {
		if (!Arrays.asList(Order.ALLOWED_STATUSES).contains(status)) {
			throw new InvalidOrderStatusException();
		}
		
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
	}

	/**
	 * Get string containing a receipt
	 */
	public String getReceipt() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String receiptContent = "\n";
		receiptContent += "RECEIPT\n";
		receiptContent += dateFormat.format(this.getPaidAt()) + "\n";
		receiptContent += "Order #" + this.getNumber() + "\n";
		receiptContent += "Table number: " + this.getTable().getNumber() + "\n";
		receiptContent += "--------------------\n";
		
		int i = 1;
		for(Diner diner : this.getDiners()) {
			receiptContent += "-- DINER " + i + " --\n";
			
			for(Course course : diner.getCourses()) {
				receiptContent += course.getName() + " - " + course.getKiloCalories() + "kcal - £" + "" + course.getPrice() + "\n";
			}
			
			receiptContent += "\n";
			receiptContent += "Total cost for diner " + i + ": £" + OrderScreen.DECIMAL_FORMAT.format(diner.getTotalPrice()) + "\n";
			receiptContent += "Total kilocalories for diner " + i + ": " + diner.getTotalKiloCalories() + "kcal\n";
			System.out.println();
			
			i++;
		}
		
		receiptContent += "--------------------\n";
		receiptContent += "Total: £" + OrderScreen.DECIMAL_FORMAT.format(this.getTotalPrice()) + "\n";
		receiptContent += "Paid: £" + OrderScreen.DECIMAL_FORMAT.format(this.getAmountPaid()) + "\n";
		receiptContent += "Change: £" + OrderScreen.DECIMAL_FORMAT.format(this.getChangeAmount()) + "\n";
		receiptContent += "--------------------\n";
		receiptContent += "Thank you for your custom\n";
		return receiptContent;
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
