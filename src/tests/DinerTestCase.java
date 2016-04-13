package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.Course;
import models.Diner;
import models.Menu;

/**
 * Test Diner class' methods
 * 
 * @author TJ Knapik <u1562595@unimail.hud.ac.uk>
 *
 */
public class DinerTestCase {
	
	private Diner diner;
	private Menu menu;
	
	/**
	 * Set up a basic menu object with example courses and an empty diner object for testing.
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
		
		this.diner = new Diner();
	}
	
	/**
	 * Test calculating total price.
	 */
	@Test
	public void testGetTotalPrice() {
		this.diner.addCourse(this.menu.getCourses().get(0));
		this.diner.addCourse(this.menu.getCourses().get(2));
		
		assertEquals(21.49, this.diner.getTotalPrice(), 0.01);
	}
	
	/**
	 * Test calculating total calories.
	 */
	@Test
	public void testGetTotalCalories() {
		this.diner.addCourse(this.menu.getCourses().get(0));
		this.diner.addCourse(this.menu.getCourses().get(2));
		
		assertEquals(135977, this.diner.getTotalCalories());
	}
	
	/**
	 * Test calculating total kilocalories.
	 */
	@Test
	public void testGetTotalKiloCalories() {
		this.diner.addCourse(this.menu.getCourses().get(2));
		this.diner.addCourse(this.menu.getCourses().get(3));
		
		assertEquals(38, this.diner.getTotalKiloCalories());
	}

}
