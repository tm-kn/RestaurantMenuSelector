package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidAmountPaidException;
import exceptions.InvalidNumberOfCoursesOrderedException;
import exceptions.InvalidNumberOfDinersException;
import exceptions.InvalidOrderStatusException;
import exceptions.TableHasNotBeenChosenException;
import models.Course;
import models.Diner;
import models.Menu;
import models.Order;
import models.Table;

/**
 * Unit test testing Order class.
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class OrderTestCase {
	
	private Order order;
	private Menu menu;
	
	/**
	 * Set up an example menu instance and an empty order.
	 */
	@Before
	public void setUp() {
		Course course1 = new Course(
				Course.MAIN,
				"Test course",
				5.50,
				123052,
				"Lorem ipsum dolor sit amet, sea duis veri ea, mea ad movet persequeris, mundi putent voluptatum sea ei. " + 
				"At homero alterum mei, ut mel noluisse percipit. An enim admodum duo.",
				false, false, false, false);
		
		Course course2 = new Course(
				Course.FISH,
				"Test fish course",
				1.00,
				25312,
				"Lorem ipsum dolor sit amet, sea duis veri ea, mea ad movet persequeris, mundi putent voluptatum sea ei. " + 
				"At homero alterum mei, ut mel noluisse percipit. An enim admodum duo.",
				false, true, false, false);
		
		Course course3 = new Course(
				Course.DESSERT,
				"Test dessert",
				15.99,
				12925,
				"Lorem ipsum dolor sit amet, sea duis veri ea, mea ad movet persequeris, mundi putent voluptatum sea ei. " + 
				"At homero alterum mei, ut mel noluisse percipit. An enim admodum duo.",
				false, false, true, false);
		
		Course course4 = new Course(
				Course.FISH,
				"Test fish course 2",
				0.99,
				25312,
				"Lorem ipsum dolor sit amet, sea duis veri ea, mea ad movet persequeris, mundi putent voluptatum sea ei. " + 
				"At homero alterum mei, ut mel noluisse percipit. An enim admodum duo.",
				false, true, false, false);
		
		this.menu = new Menu();
		
		this.menu.addCourse(course1);
		this.menu.addCourse(course2);
		this.menu.addCourse(course3);
		this.menu.addCourse(course4);
		
		this.order = new Order();
	}
	
	/**
	 * Test if getting total price for order works properly.
	 */
	@Test
	public void testGetTotalPrice() {
		Diner diner = this.order.getDiners().get(0);
		diner.addCourse(this.menu.getCourses().get(0));
		diner.addCourse(this.menu.getCourses().get(0));
		diner.addCourse(this.menu.getCourses().get(0));
		
		assertEquals(16.50, this.order.getTotalPrice(), 0.01);	
	}
	
	/**
	 * Test if getting total price for order with two different diners work properly.
	 */
	@Test
	public void testGetTotalPriceForOrderWithTwoDiners() {
		this.order.addDiner(new Diner());
		
		Diner diner = this.order.getDiners().get(0);
		Diner diner2 = this.order.getDiners().get(1);
		
		diner.addCourse(this.menu.getCourses().get(0));
		diner.addCourse(this.menu.getCourses().get(1));
		
		diner2.addCourse(this.menu.getCourses().get(3));
		diner2.addCourse(this.menu.getCourses().get(2));
		
		assertEquals(23.48, this.order.getTotalPrice(), 0.01);
	}
	
	/**
	 * Test counting calories.
	 */
	@Test
	public void testGetTotalCalories() {
		this.order.addDiner(new Diner());
		
		Diner diner = this.order.getDiners().get(0);
		Diner diner2 = this.order.getDiners().get(1);
		
		diner.addCourse(this.menu.getCourses().get(0));
		diner.addCourse(this.menu.getCourses().get(1));
		
		diner2.addCourse(this.menu.getCourses().get(3));
		diner2.addCourse(this.menu.getCourses().get(2));
		
		assertEquals(186601, this.order.getTotalCalories());
	}
	
	/**
	 * Test getting total kilocalories.
	 */
	@Test
	public void testGetTotalKiloCalories() {
		this.order.addDiner(new Diner());
		
		Diner diner = this.order.getDiners().get(0);
		Diner diner2 = this.order.getDiners().get(1);
		
		diner.addCourse(this.menu.getCourses().get(0));
		diner.addCourse(this.menu.getCourses().get(1));
		
		diner2.addCourse(this.menu.getCourses().get(3));
		diner2.addCourse(this.menu.getCourses().get(2));
		
		// 186601/1000 = 186
		assertEquals(186, this.order.getTotalKiloCalories());
	}
	
	/**
	 * Test getting change.
	 * @throws InvalidOrderStatusException 
	 * @throws InvalidAmountPaidException 
	 * @throws InvalidNumberOfCoursesOrderedException 
	 * @throws InvalidNumberOfDinersException 
	 * @throws TableHasNotBeenChosenException 
	 */
	@Test
	public void testGetChange() throws TableHasNotBeenChosenException, InvalidNumberOfDinersException, InvalidNumberOfCoursesOrderedException, InvalidAmountPaidException, InvalidOrderStatusException {
		this.order.setTable(new Table(5));
		Diner diner = this.order.getDiners().get(0);
		diner.addCourse(this.menu.getCourses().get(0));
		diner.addCourse(this.menu.getCourses().get(1));
		this.order.pay(10.05);
		assertEquals(3.55, this.order.getChangeAmount(), 0.01);
	}

}
