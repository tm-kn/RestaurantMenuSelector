package tests;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidAmountPaidException;
import exceptions.InvalidNumberOfCoursesOrderedException;
import exceptions.InvalidNumberOfDinersException;
import exceptions.InvalidOrderStatusException;
import exceptions.TableHasNotBeenChosenException;
import models.Course;
import models.Menu;
import models.Order;
import models.Table;

/**
 * Test exceptions used in Order class.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class OrderExceptionsTestCase {
	
	private Order order;
	private Menu menu;
	
	/**
	 * Set up an example menu instance and an empty order.
	 */
	@Before
	public void before() {
		this.menu = new Menu();
		
		Course course1 = new Course(
				Course.MAIN,
				"Test course",
				5.50,
				20000,
				"Lorem ipsum dolor sit amet, sea duis veri ea, mea ad movet persequeris, mundi putent voluptatum sea ei. " + 
				"At homero alterum mei, ut mel noluisse percipit. An enim admodum duo.",
				false, false, false, false);
		
		this.menu.addCourse(course1);
		
		this.order = new Order();
	}
	
	/**
	 * Check if order throws TableHasNotBeenChosenException where the table is not chosen while trying to pay.
	 * 
	 * @throws TableHasNotBeenChosenException
	 * @throws InvalidNumberOfDinersException
	 * @throws InvalidNumberOfCoursesOrderedException
	 * @throws InvalidAmountPaidException
	 * @throws InvalidOrderStatusException
	 */
	@Test(expected=TableHasNotBeenChosenException.class)
	public void testTableHasNotBeenChosenException() throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidAmountPaidException, InvalidOrderStatusException {
		this.order.pay(2.20);
	}
	
	/**
	 * Check if deleting the only diner in an order instance throws InvalidNumberOfDinersException.
	 * 
	 * @throws InvalidNumberOfDinersException
	 */
	@Test(expected=InvalidNumberOfDinersException.class)
	public void testInvalidNumberOfDinersException() throws InvalidNumberOfDinersException {
		this.order.deleteDiner(this.order.getDiners().get(0));
	}
	
	/**
	 * Check if paying for an order with no courses throws InvalidNumberOfCoursesOrderedException.
	 * 
	 * @throws TableHasNotBeenChosenException
	 * @throws InvalidNumberOfDinersException
	 * @throws InvalidNumberOfCoursesOrderedException
	 * @throws InvalidAmountPaidException
	 * @throws InvalidOrderStatusException
	 */
	@Test(expected=InvalidNumberOfCoursesOrderedException.class)
	public void testInvalidNumberOfCoursesOrderedException() throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidAmountPaidException, InvalidOrderStatusException {
		this.order.setTable(new Table(1));
		this.order.pay(2.20);
	}
	
	/**
	 * Check if paying less than it's due raises InvalidAmountPaidException.
	 * 
	 * @throws TableHasNotBeenChosenException
	 * @throws InvalidNumberOfDinersException
	 * @throws InvalidNumberOfCoursesOrderedException
	 * @throws InvalidAmountPaidException
	 * @throws InvalidOrderStatusException
	 */
	@Test(expected=InvalidAmountPaidException.class)
	public void testPayLessThanAmountDue() throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidAmountPaidException, InvalidOrderStatusException {
		this.order.setTable(new Table(1));
		this.order.getDiners().get(0).addCourse(this.menu.getCourses().get(0));
		this.order.pay(this.order.getTotalPrice() - 0.01);
	}
	
	/**
	 * Check if order raises InvalidAmountPaidException when it is paid with amount same as due amount 
	 * 
	 * @throws TableHasNotBeenChosenException
	 * @throws InvalidNumberOfDinersException
	 * @throws InvalidNumberOfCoursesOrderedException
	 * @throws InvalidOrderStatusException
	 */
	@Test
	public void testPayResultingInNoChange() throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidOrderStatusException {
		this.order.setTable(new Table(1));
		this.order.getDiners().get(0).addCourse(this.menu.getCourses().get(0));
		
		try {
			this.order.pay(this.order.getTotalPrice());
		} catch (InvalidAmountPaidException e) {
			fail("Paid the same amount as total price is and it thrown InvalidAmountPaidException() exception.");
		}
		
	}
	
	/**
	 * Check if order raises InvalidAmountPaidException when the amount paid is higher than amount due.
	 * 
	 * @throws TableHasNotBeenChosenException
	 * @throws InvalidNumberOfDinersException
	 * @throws InvalidNumberOfCoursesOrderedException
	 * @throws InvalidOrderStatusException
	 */
	@Test
	public void testPayWithMoreThanDueAmount() throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidOrderStatusException {
		this.order.setTable(new Table(1));
		this.order.getDiners().get(0).addCourse(this.menu.getCourses().get(0));
		
		try {
			this.order.pay(this.order.getTotalPrice() + 0.02);
		} catch (InvalidAmountPaidException e) {
			fail("Paid more than the amount is due and InvalidAmountPaidException has been thrown.");
		}
	}
	
	/**
	 * Test setting not valid order status.
	 * @throws InvalidOrderStatusException 
	 */
	@Test(expected=InvalidOrderStatusException.class)
	public void testSetNotValidOrderStatus() throws InvalidOrderStatusException {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt();
		
		while(Arrays.asList(Order.ALLOWED_STATUSES).contains(randomInt)) {
			randomInt = randomGenerator.nextInt();
		}
		
		this.order.setStatus(randomInt);
		
	}
}
